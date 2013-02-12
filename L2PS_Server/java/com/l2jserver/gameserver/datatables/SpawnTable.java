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
package com.l2jserver.gameserver.datatables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;

import com.l2jserver.Config;
import com.l2jserver.L2DatabaseFactory;
import com.l2jserver.gameserver.instancemanager.DayNightSpawnManager;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.templates.L2NpcTemplate;

/**
 * @author Nightmare
 */
public class SpawnTable
{
	private static final Logger _log = Logger.getLogger(SpawnTable.class.getName());
	
	private final FastSet<L2Spawn> _spawntable = new FastSet<>();
	private final FastMap<Integer, L2Spawn> _spawngetnpc = new FastMap<Integer, L2Spawn>().shared();
	private int _npcSpawnCount;
	private int _customSpawnCount;
	
	protected SpawnTable()
	{
		_spawntable.shared();
		if (!Config.ALT_DEV_NO_SPAWNS)
		{
			fillSpawnTable();
		}
	}
	
	public FastSet<L2Spawn> getSpawnTable()
	{
		return _spawntable;
	}
	
	private void fillSpawnTable()
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection();
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT spawn_name, count, npc_templateid, locx, locy, locz, heading, respawn_delay, loc_id, periodOfDay FROM spawnlist"))
		{
			L2Spawn spawnDat;
			L2NpcTemplate template1;
			while (rs.next())
			{
				template1 = NpcTable.getInstance().getTemplate(rs.getInt("npc_templateid"));
				if (template1 != null)
				{
					if (template1.isType("L2SiegeGuard"))
					{
						// Don't spawn
					}
					else if (template1.isType("L2RaidBoss"))
					{
						// Don't spawn raidboss
					}
					else if (!Config.ALLOW_CLASS_MASTERS && template1.isType("L2ClassMaster"))
					{
						// Dont' spawn class masters
					}
					else
					{
						spawnDat = new L2Spawn(template1);
						spawnDat.setAmount(rs.getInt("count"));
						spawnDat.setLocx(rs.getInt("locx"));
						spawnDat.setLocy(rs.getInt("locy"));
						spawnDat.setLocz(rs.getInt("locz"));
						spawnDat.setHeading(rs.getInt("heading"));
						spawnDat.setRespawnDelay(rs.getInt("respawn_delay"));
						String spawnName = rs.getString("spawn_name");
						if ((spawnName != null) && !spawnName.isEmpty())
						{
							spawnDat.setName(spawnName);
						}
						int loc_id = rs.getInt("loc_id");
						spawnDat.setLocation(loc_id);
						
						switch (rs.getInt("periodOfDay"))
						{
							case 0: // default
								_npcSpawnCount += spawnDat.init();
								break;
							case 1: // Day
								DayNightSpawnManager.getInstance().addDayCreature(spawnDat);
								_npcSpawnCount++;
								break;
							case 2: // Night
								DayNightSpawnManager.getInstance().addNightCreature(spawnDat);
								_npcSpawnCount++;
								break;
						}
						
						_spawntable.add(spawnDat);
					}
				}
				else
				{
					_log.warning(getClass().getSimpleName() + ": Data missing in NPC table for ID: " + rs.getInt("npc_templateid") + ".");
				}
			}
		}
		catch (Exception e)
		{
			// problem with initializing spawn, go to next one
			_log.log(Level.WARNING, getClass().getSimpleName() + ": Spawn could not be initialized: " + e.getMessage(), e);
		}
		
		_log.info(getClass().getSimpleName() + ": Loaded " + _spawntable.size() + " npc spawns.");
		
		if (Config.CUSTOM_SPAWNLIST_TABLE)
		{
			try (Connection con = L2DatabaseFactory.getInstance().getConnection();
				Statement ps = con.createStatement();
				ResultSet rs = ps.executeQuery("SELECT spawn_name, count, npc_templateid, locx, locy, locz, heading, respawn_delay, loc_id, periodOfDay FROM custom_spawnlist"))
			{
				L2Spawn spawnDat;
				L2NpcTemplate template1;
				while (rs.next())
				{
					template1 = NpcTable.getInstance().getTemplate(rs.getInt("npc_templateid"));
					if (template1 != null)
					{
						if (template1.isType("L2SiegeGuard"))
						{
							// Don't spawn
						}
						else if (template1.isType("L2RaidBoss"))
						{
							// Don't spawn raidboss
						}
						else if (!Config.ALLOW_CLASS_MASTERS && template1.isType("L2ClassMaster"))
						{
							// Dont' spawn class masters
						}
						else
						{
							spawnDat = new L2Spawn(template1);
							spawnDat.setAmount(rs.getInt("count"));
							spawnDat.setLocx(rs.getInt("locx"));
							spawnDat.setLocy(rs.getInt("locy"));
							spawnDat.setLocz(rs.getInt("locz"));
							spawnDat.setHeading(rs.getInt("heading"));
							spawnDat.setRespawnDelay(rs.getInt("respawn_delay"));
							spawnDat.setCustom(true);
							String spawnName = rs.getString("spawn_name");
							if ((spawnName != null) && !spawnName.isEmpty())
							{
								spawnDat.setName(spawnName);
							}
							
							int loc_id = rs.getInt("loc_id");
							spawnDat.setLocation(loc_id);
							
							switch (rs.getInt("periodOfDay"))
							{
								case 0: // default
									_customSpawnCount += spawnDat.init();
									break;
								case 1: // Day
									DayNightSpawnManager.getInstance().addDayCreature(spawnDat);
									_customSpawnCount++;
									break;
								case 2: // Night
									DayNightSpawnManager.getInstance().addNightCreature(spawnDat);
									_customSpawnCount++;
									break;
							}
							
							_spawntable.add(spawnDat);
						}
					}
					else
					{
						_log.warning(getClass().getSimpleName() + ": Data missing in NPC table for ID: " + rs.getInt("npc_templateid") + ".");
					}
				}
			}
			catch (Exception e)
			{
				// problem with initializing spawn, go to next one
				_log.log(Level.WARNING, "CustomSpawnTable: Spawn could not be initialized: " + e.getMessage(), e);
			}
			_log.info(getClass().getSimpleName() + ": Loaded " + _customSpawnCount + " custom npc spawns.");
			
		}
		
		if (Config.DEBUG)
		{
			_log.fine(getClass().getSimpleName() + ": Spawning completed, total number of NPCs in the world: " + (_npcSpawnCount + _customSpawnCount));
		}
		
	}
	
	public void addNewSpawn(L2Spawn spawn, boolean storeInDb)
	{
		_spawntable.add(spawn);
		
		if (storeInDb)
		{
			String spawnTable;
			if (spawn.isCustom() && Config.CUSTOM_SPAWNLIST_TABLE)
			{
				spawnTable = "custom_spawnlist";
			}
			else
			{
				spawnTable = "spawnlist";
			}
			
			try (Connection con = L2DatabaseFactory.getInstance().getConnection();
				PreparedStatement insert = con.prepareStatement("INSERT INTO " + spawnTable + "(count,npc_templateid,locx,locy,locz,heading,respawn_delay,loc_id) values(?,?,?,?,?,?,?,?)"))
			{
				insert.setInt(1, spawn.getAmount());
				insert.setInt(2, spawn.getNpcid());
				insert.setInt(3, spawn.getLocx());
				insert.setInt(4, spawn.getLocy());
				insert.setInt(5, spawn.getLocz());
				insert.setInt(6, spawn.getHeading());
				insert.setInt(7, spawn.getRespawnDelay() / 1000);
				insert.setInt(8, spawn.getLocation());
				insert.execute();
			}
			catch (Exception e)
			{
				// problem with storing spawn
				_log.log(Level.WARNING, getClass().getSimpleName() + ": Could not store spawn in the DB:" + e.getMessage(), e);
			}
		}
	}
	
	public void deleteSpawn(L2Spawn spawn, boolean updateDb)
	{
		if (!_spawntable.remove(spawn))
		{
			return;
		}
		
		if (updateDb)
		{
			try (Connection con = L2DatabaseFactory.getInstance().getConnection();
				PreparedStatement delete = con.prepareStatement("DELETE FROM " + (spawn.isCustom() ? "custom_spawnlist" : "spawnlist") + " WHERE locx=? AND locy=? AND locz=? AND npc_templateid=? AND heading=?"))
			{
				delete.setInt(1, spawn.getLocx());
				delete.setInt(2, spawn.getLocy());
				delete.setInt(3, spawn.getLocz());
				delete.setInt(4, spawn.getNpcid());
				delete.setInt(5, spawn.getHeading());
				delete.execute();
			}
			catch (Exception e)
			{
				// problem with deleting spawn
				_log.log(Level.WARNING, getClass().getSimpleName() + ": Spawn " + spawn + " could not be removed from DB: " + e.getMessage(), e);
			}
		}
	}
	
	// just wrapper
	public void reloadAll()
	{
		fillSpawnTable();
	}
	
	/**
	 * Get all the spawn of a NPC.
	 * @param activeChar
	 * @param npcId
	 * @param teleportIndex
	 * @param showposition
	 */
	public void findNPCInstances(L2PcInstance activeChar, int npcId, int teleportIndex, boolean showposition)
	{
		int index = 0;
		for (L2Spawn spawn : _spawntable)
		{
			
			if (npcId == spawn.getNpcid())
			{
				index++;
				L2Npc _npc = spawn.getLastSpawn();
				if (teleportIndex > -1)
				{
					if (teleportIndex == index)
					{
						if (showposition && (_npc != null))
						{
							activeChar.teleToLocation(_npc.getX(), _npc.getY(), _npc.getZ(), true);
						}
						else
						{
							activeChar.teleToLocation(spawn.getLocx(), spawn.getLocy(), spawn.getLocz(), true);
						}
					}
				}
				else
				{
					if (showposition && (_npc != null))
					{
						activeChar.sendMessage(index + " - " + spawn.getTemplate().getName() + " (" + spawn + "): " + _npc.getX() + " " + _npc.getY() + " " + _npc.getZ());
					}
					else
					{
						activeChar.sendMessage(index + " - " + spawn.getTemplate().getName() + " (" + spawn + "): " + spawn.getLocx() + " " + spawn.getLocy() + " " + spawn.getLocz());
					}
				}
			}
		}
		
		if (index == 0)
		{
			activeChar.sendMessage(getClass().getSimpleName() + ": No current spawns found.");
		}
	}
	
	public static SpawnTable getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final SpawnTable _instance = new SpawnTable();
	}
	
	public ArrayList<L2Npc> findNpces(int npcId)
	{
		ArrayList<L2Npc> npces = new ArrayList<>();
		
		for (L2Spawn spawn : _spawntable)
		{
			if ((npcId == spawn.getNpcid()) && (spawn.getLastSpawn() != null))
			{
				npces.add(spawn.getLastSpawn());
			}
		}
		
		return npces;
	}
	
	public FastList<L2Spawn> getSpawnsByNpcId(int npcId)
	{
		FastList<L2Spawn> spawns = new FastList<>();
		for (L2Spawn spawn : getSpawntable().values())
		{
			if ((spawn.getNpc() == null) || (spawn.getNpc().getNpcId() != npcId))
			{
				continue;
			}
			
			spawns.add(spawn);
		}
		return spawns;
	}
	
	public FastMap<Integer, L2Spawn> getSpawntable()
	{
		return _spawngetnpc;
	}
	
	public Collection<L2Spawn> getAllSpawns()
	{
		return _spawntable;
	}
}
