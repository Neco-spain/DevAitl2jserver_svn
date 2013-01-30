/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.l2jserver.gameserver.instancemanager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import com.l2jserver.Config;
import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.ExBrPremiumState;

import gnu.trove.iterator.TIntLongIterator;
import gnu.trove.map.hash.TIntLongHashMap;

public class ExpirableServicesManager
{
	protected final Map<ServiceType, TIntLongHashMap> _holder; // store service maps
	protected final Future<?> _task; // expiration checkup task
	
	/**
	 * Contains possible service types
	 */
	public enum ServiceType
	{
		PREMIUM;
	}
	
	ExpirableServicesManager()
	{
		_holder = new HashMap<>();
		for (ServiceType type : ServiceType.values())
		{
			_holder.put(type, new TIntLongHashMap());
		}
		
		_task = ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new CheckExpirationTask(), 1000, Config.EXPIRATION_CHECK_INTERVAL);
	}
	
	/**
	 * Register service for player in expiration date holder
	 * @param type type of service
	 * @param player player to register
	 * @param expirationDate date of service expiration
	 */
	public synchronized void registerService(ServiceType type, L2PcInstance player, long expirationDate)
	{
		_holder.get(type).put(player.getObjectId(), expirationDate);
	}
	
	/**
	 * @param type type of service
	 * @param player to check
	 * @return expiration date of given service type for player, if service is registered for player, 0 otherwise
	 */
	public synchronized long getExpirationDate(ServiceType type, L2PcInstance player)
	{
		return hasService(type, player) ? _holder.get(type).get(player.getObjectId()) : 0;
	}
	
	/**
	 * @param type type of service
	 * @param player to check
	 * @return {@code true} if given service type has unlimited time for given player, {@code false} otherwise
	 */
	public boolean isServiceUnlimited(ServiceType type, L2PcInstance player)
	{
		return (_holder.get(type).contains(player.getObjectId()) && (_holder.get(type).get(player.getObjectId()) == -1));
	}
	
	/**
	 * @param type type of service
	 * @param player to check
	 * @return {@code true} if given service type is expirate at logout (not storing in database) for given player, {@code false} otherwise
	 */
	public boolean isServiceTemporary(ServiceType type, L2PcInstance player)
	{
		return (_holder.get(type).contains(player.getObjectId()) && (_holder.get(type).get(player.getObjectId()) == 0));
	}
	
	/**
	 * @param type type of service
	 * @param player to check
	 * @return {@code true} if given service type is registered for given player, {@code false} otherwise
	 */
	public boolean hasService(ServiceType type, L2PcInstance player)
	{
		return _holder.get(type).contains(player.getObjectId());
	}
	
	/**
	 * Send message and premium state packet to player with given id, when service of given type is expiring
	 * @param type type of service
	 * @param playerId objectId of player to send info
	 */
	void expireService(ServiceType type, int playerId)
	{
		L2PcInstance player = L2World.getInstance().getPlayer(playerId);
		if ((player != null) && player.isOnline())
		{
			switch (type)
			{
				case PREMIUM:
					player.sendMessage("Premium Service is expired");
					if (Config.SHOW_PREMIUM_STATUS)
					{
						player.sendPacket(new ExBrPremiumState(player.getObjectId(), 0));
					}
					break;
			}
		}
	}
	
	/**
	 * Unregister service of given type for given player
	 * @param type type of service
	 * @param player to process
	 */
	public synchronized void expireService(ServiceType type, L2PcInstance player)
	{
		_holder.get(type).remove(player.getObjectId());
		expireService(type, player.getObjectId()); // show appropriate things
	}
	
	protected class CheckExpirationTask implements Runnable
	{
		@Override
		public synchronized void run()
		{
			for (ServiceType type : _holder.keySet())
			{
				for (TIntLongIterator it = _holder.get(type).iterator(); it.hasNext();)
				{
					it.advance();
					if ((it.value() > 0) && (it.value() <= System.currentTimeMillis())) // Do not touch unlimited and temporary services
					{
						expireService(type, it.key());
						it.remove();
					}
				}
			}
		}
	}
	
	public static final ExpirableServicesManager getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final ExpirableServicesManager _instance = new ExpirableServicesManager();
	}
}