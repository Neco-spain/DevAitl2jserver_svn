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
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.MagicSkillUse;
import com.l2jserver.gameserver.datatables.SkillTable;

public class Voiced_VoteRewardMe implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
			"rewardme"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{

			if(activeChar.isInOlympiadMode() || activeChar.getOlympiadGameId() != -1)
			{
				activeChar.sendMessage("You can't use that command inside Olympiad");
				return false;
			}

			if(activeChar.getInventory().getItemByItemId(Config.VOTE_ITEM_ID).getCount() >= Config.VOTE_ITEM_A)
			{
				activeChar.getInventory().destroyItemByItemId("Consume", Config.VOTE_ITEM_ID, Config.VOTE_ITEM_A, activeChar, null);
				activeChar.sendMessage(Config.VOTE_ITEM_A + " " + Config.VOTE_ITEM_NAME + "(s) have been consumed.");
				MagicSkillUse mgc = new MagicSkillUse(activeChar, activeChar, Config.VOTE_BUFF_ID, Config.VOTE_BUFF_LVL, 1, 0);
				SkillTable.getInstance().getInfo(Config.VOTE_BUFF_ID, Config.VOTE_BUFF_LVL).getEffects(activeChar,activeChar);
				activeChar.broadcastPacket(mgc);
				activeChar.sendMessage("You have been blessed with the effects of the Vote Buff!");	
			}
			else
			{			
				activeChar.sendMessage("You don't have enough " + Config.VOTE_ITEM_NAME + "(s) in order to get rewarded!");
			}
			return true;	
	}

	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
}