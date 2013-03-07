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
import com.l2jserver.gameserver.datatables.PremiumTable;
import com.l2jserver.gameserver.instancemanager.ExpirableServicesManager;
import com.l2jserver.gameserver.instancemanager.ExpirableServicesManager.ServiceType;
import com.l2jserver.gameserver.handler.IVoicedCommandHandler;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.network.DialogId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ConfirmDlg;
import com.l2jserver.gameserver.util.Util;

import java.util.Date;

public class Voiced_Premium implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"mypremium",
		"addpremium_1d",
		"addpremium_1w"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		if (command.equalsIgnoreCase("mypremium"))
   	{		
		  if (activeChar.hasPremium())
		  {
		    if (ExpirableServicesManager.getInstance().isServiceUnlimited(ServiceType.PREMIUM, activeChar))
		    {
		    	activeChar.sendMessage("You have unlimited premium service");
				}
				else if (ExpirableServicesManager.getInstance().isServiceTemporary(ServiceType.PREMIUM, activeChar))
				{
					activeChar.sendMessage("Your premium service is active until logout");
				}
				else
				{
					activeChar.sendMessage("Premium service is active until " + Util.formatDate(new Date(ExpirableServicesManager.getInstance().getExpirationDate(ServiceType.PREMIUM, activeChar)), "dd.MM.yyyy HH:mm"));
				}
		  }
		  else
		  {
		    activeChar.sendMessage("Premium service is not active");
		  }
    }
	else if (command.startsWith("addpremium"))
	{
		if (!Config.PREMIUM_ALLOW_VOICED)
		{
			activeChar.sendMessage("Command is forbidden");
		}
		else
		{
			String[] cmdSplit = command.split("_");
			if (cmdSplit.length != 2)
			{
				activeChar.sendMessage("Invalid parameter");
			}
			else
			{
				ItemHolder payItem = PremiumTable.getPrice(cmdSplit[1]);
				if (payItem == null)
				{
					activeChar.sendMessage("Invalid parameter");
				}
				else
				{
					if (payItem.getCount() > 0)
					{
						ConfirmDlg confirmation = new ConfirmDlg(SystemMessageId.S1.getId()).addString("It will cost " + ". Are you agreed?");
						activeChar.sendPacket(confirmation);
						activeChar.setDialogId(DialogId.PREMIUM);
						PremiumTable.getInstance().addRequest(activeChar, cmdSplit[1]);
					}
					else
					{
						PremiumTable.givePremium(activeChar, cmdSplit[1], activeChar);
					}
				}				
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