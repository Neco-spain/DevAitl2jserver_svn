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
package handlers.voicedcommandhandlers;

import com.l2jserver.Config;
import com.l2jserver.gameserver.handler.IVoicedCommandHandler;
import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

public class Voiced_EvenlyDistributeItems implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"evenly"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String params)
	{
		if (!Config.EVENLY_DISTRIBUTED_ITEMS)
		{
			activeChar.sendMessage("This mod is not enabled.");
			return true;
		}
		
		if (Config.EVENLY_DISTRIBUTED_ITEMS_FORCED)
		{
			activeChar.sendMessage("This mod is enabled but you cannot change its status.");
			return true;
		}
		
		if (VOICED_COMMANDS[0].equalsIgnoreCase(command))
		{
			if (activeChar.hasEvenlyDistributedLoot())
			{
				activeChar.setEvenlyDistributedLoot(false);
				final SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1);
				sm.addString("Evenly distribution of party loot disabled.");
				// If has party and is the leader announce it's party members.
				final L2Party party = activeChar.getParty();
				if ((party != null) && (party.getLeaderObjectId() == activeChar.getObjectId()))
				{
					party.broadcastPacket(sm);
				}
				else
				{
					activeChar.sendPacket(sm);
				}
			}
			else
			{
				activeChar.setEvenlyDistributedLoot(true);
				final SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1);
				sm.addString("Evenly distribution of party loot enabled.");
				final L2Party party = activeChar.getParty();
				if (party != null)
				{
					party.broadcastPacket(sm);
					
					if (Config.EVENLY_DISTRIBUTED_ITEMS_SEND_LIST && (Config.EVENLY_DISTRIBUTED_ITEMS_CACHED_HTML != null))
					{
						party.broadcastPacket(Config.EVENLY_DISTRIBUTED_ITEMS_CACHED_HTML);
					}
				}
				else
				{
					activeChar.sendPacket(sm);
				}
			}
		}
		return true;
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
}