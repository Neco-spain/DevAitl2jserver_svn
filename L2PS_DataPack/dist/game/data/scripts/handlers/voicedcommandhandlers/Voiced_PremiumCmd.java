/* This program is free software: you can redistribute it and/or modify it under
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
import com.l2jserver.gameserver.instancemanager.ExpirableServicesManager;
import com.l2jserver.gameserver.instancemanager.ExpirableServicesManager.ServiceType;
import com.l2jserver.gameserver.handler.BypassHandler;
import com.l2jserver.gameserver.handler.IBypassHandler;
import com.l2jserver.gameserver.handler.IVoicedCommandHandler;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.util.Util;

import java.util.Date;

public class Voiced_PremiumCmd implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"mypremium",
		"addpremium"
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
		    	activeChar.sendMessage("You have an infinite premium service");
				}
				else if (ExpirableServicesManager.getInstance().isServiceTemporary(ServiceType.PREMIUM, activeChar))
				{
					activeChar.sendMessage("Premium service is activated before the reload of the game");
				}
				else
				{
					activeChar.sendMessage("Premium service is activated before " + Util.formatDate(new Date(ExpirableServicesManager.getInstance().getExpirationDate(ServiceType.PREMIUM, activeChar)), "dd.MM.yyyy HH:mm"));
				}
		  }
		  else
		  {
		    activeChar.sendMessage("Premium service is not activated");
		  }
    }
		
		else if (command.equalsIgnoreCase("addpremium"))
		{
			if (!Config.PREMIUM_ALLOW_VOICED)
			{
				activeChar.sendMessage("Suppressed");
			}
			else
			{
				IBypassHandler handler = BypassHandler.getInstance().getHandler("premiumShowList");
				handler.useBypass("premiumShowList", activeChar, null);
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