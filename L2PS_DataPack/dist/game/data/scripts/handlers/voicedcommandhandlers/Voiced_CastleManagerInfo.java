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
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.Castle;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.network.serverpackets.SiegeInfo;

public class Voiced_CastleManagerInfo implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"castlemanager",
		"siege_gludio",
		"siege_dion",
		"siege_giran",
		"siege_oren",
		"siege_aden",
		"siege_innadril",
		"siege_goddard",
		"siege_rune",
		"siege_schuttgart"
	};

    @Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
    {
    	if (command.startsWith("castlemanager")) {
    		sendHtml(activeChar);
    	}
    	
    	if (command.startsWith("siege_")) {
    		if (activeChar.getClan() != null && !activeChar.isClanLeader()) {
    			activeChar.sendPacket(SystemMessageId.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
    			return false;
    		}
    		
			int castleId = 0;
			
			if (command.startsWith("siege_gludio"))
				castleId = 1;
			else if (command.startsWith("siege_dion"))
				castleId = 2;
			else if (command.startsWith("siege_giran"))
				castleId = 3;
			else if (command.startsWith("siege_oren"))
				castleId = 4;
			else if (command.startsWith("siege_aden"))
				castleId = 5;
			else if (command.startsWith("siege_innadril"))
				castleId = 6;
			else if (command.startsWith("siege_goddard"))
				castleId = 7;
			else if (command.startsWith("siege_rune"))
				castleId = 8;
			else if (command.startsWith("siege_schuttgart"))
				castleId = 9;
			
			Castle castle = CastleManager.getInstance().getCastleById(castleId);
			if(castle != null && castleId != 0)
				activeChar.sendPacket(new SiegeInfo(castle));
		}
        return true;
    }
    
    private void sendHtml(L2PcInstance activeChar)
	{
		String htmFile = "data/html/mods/CastleManager.htm";
		
		NpcHtmlMessage msg = new NpcHtmlMessage(5);
		msg.setFile(activeChar.getHtmlPrefix(), htmFile);
		activeChar.sendPacket(msg);
	}
    
    @Override
	public String[] getVoicedCommandList()
    {
        return VOICED_COMMANDS;
    }
}