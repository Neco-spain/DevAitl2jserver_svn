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

import com.l2jserver.gameserver.handler.IVoicedCommandHandler;
import com.l2jserver.gameserver.instancemanager.CastleManager;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;

public class Voiced_TeleToCl implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"teletocl"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		if (command.equalsIgnoreCase("teletocl"))
		{
			
			if (activeChar.getClan() == null)
			{
				return false;
			}
			
			L2PcInstance leader;
			leader = (L2PcInstance) L2World.getInstance().findObject(activeChar.getClan().getLeaderId());
			
			if (leader == null)
			{
				activeChar.sendPacket(new ExShowScreenMessage("You can use this server now, because clan leader is offline.", 5));
				return false;
			}
			else if (leader.isInJail())
			{
				activeChar.sendPacket(new ExShowScreenMessage("You can use this server now, because clan leader is in jail.", 5));
				return false;
			}
			else if (leader.isInOlympiadMode())
			{
				activeChar.sendPacket(new ExShowScreenMessage("You can use this server now, because clan leader is in olympiad.", 5));
				return false;
			}
			else if (leader.isInDuel())
			{
				activeChar.sendPacket(new ExShowScreenMessage("You can use this server now, because clan leader is in duel.", 5));
				return false;
			}
			else if (leader.isFestivalParticipant())
			{
				activeChar.sendPacket(new ExShowScreenMessage("You can use this server now, because clan leader is in register list for Festival.", 5));
				return false;
			}
			else if (leader.isInParty() && leader.getParty().isInDimensionalRift())
			{
				activeChar.sendPacket(new ExShowScreenMessage("You can use this server now, because clan leader is in Rift.", 5));
				return false;
			}
			else if (leader.inObserverMode())
			{
				activeChar.sendPacket(new ExShowScreenMessage("You can use this server now, because clan leader is in register list for olympiad.", 5));
			}
			else if ((leader.getClan() != null) && (CastleManager.getInstance().getCastleByOwner(leader.getClan()) != null) && CastleManager.getInstance().getCastleByOwner(leader.getClan()).getSiege().getIsInProgress())
			{
				activeChar.sendPacket(new ExShowScreenMessage("You can use this server now, because clan leader is in siege.", 5));
				return false;
			}
			
			else if (activeChar.isInJail())
			{
				activeChar.sendPacket(new ExShowScreenMessage("You are in jail.", 5));
				return false;
			}
			else if (activeChar.isInOlympiadMode())
			{
				activeChar.sendPacket(new ExShowScreenMessage("You are in olympiad.", 5));
				return false;
			}
			else if (activeChar.isInDuel())
			{
				activeChar.sendPacket(new ExShowScreenMessage("You are in duel.", 5));
				return false;
			}
			else if (activeChar.inObserverMode())
			{
				activeChar.sendPacket(new ExShowScreenMessage("You are in register list for olympiad.", 5));
			}
			else if ((activeChar.getClan() != null) && (CastleManager.getInstance().getCastleByOwner(activeChar.getClan()) != null) && CastleManager.getInstance().getCastleByOwner(activeChar.getClan()).getSiege().getIsInProgress())
			{
				activeChar.sendPacket(new ExShowScreenMessage("You are in siege.", 5));
				return false;
			}
			else if (activeChar.isFestivalParticipant())
			{
				activeChar.sendPacket(new ExShowScreenMessage("You are in Festival.", 5));
				return false;
			}
			else if (activeChar.isInParty() && activeChar.getParty().isInDimensionalRift())
			{
				activeChar.sendPacket(new ExShowScreenMessage("You are in Rift.", 5));
				return false;
			}
			else if (activeChar == leader())
			{
				activeChar.sendPacket(new ExShowScreenMessage("You cant use this server for now.", 5));
				return false;
			}
			if (activeChar.getInventory().getItemByItemId(57) == null)
			{
				activeChar.sendMessage("You dont have 1000 adena in you inventory");
				return false;
			}
			int leaderx;
			int leadery;
			int leaderz;
			
			leaderx = leader.getX();
			leadery = leader.getY();
			leaderz = leader.getZ();
			
			activeChar.teleToLocation(leaderx, leadery, leaderz);
			activeChar.sendMessage("You are teleported to you clan leader");
			activeChar.getInventory().destroyItemByItemId("RessSystem", 57, 1000, activeChar, activeChar.getTarget());
			activeChar.sendMessage("1000 Adena deducted from you invetory");
		}
		
		else
		{
			activeChar.sendMessage("Command is not allowed");
		}
		
		return true;
	}
	
	public L2PcInstance leader()
	{
		return null;
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
	
}