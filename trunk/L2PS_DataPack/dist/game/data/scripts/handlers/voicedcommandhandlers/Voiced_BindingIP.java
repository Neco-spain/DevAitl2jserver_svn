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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import com.l2jserver.L2DatabaseFactory;
import com.l2jserver.gameserver.handler.IVoicedCommandHandler;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ConfirmDlg;

/**
 * Author RobikBobik
 */
public class Voiced_BindingIP implements IVoicedCommandHandler
{
	private static final String[] _voicedCommands =
	{
		"bind_ip",
		"bind_process"
	};
	
	@Override
	public boolean useVoicedCommand(final String command, final L2PcInstance activeChar, final String param)
	{
		String userIP = null;
		Connection con = null;
		
		try
		{
			
			con = L2DatabaseFactory.getInstance().getConnection();
			
			if (command.equalsIgnoreCase("bind_ip"))
			{
				
				String lastIP = null;
				final PreparedStatement statement = con.prepareStatement("SELECT lastIP, userIP FROM accounts WHERE login=?");
				statement.setString(1, activeChar.getAccountName());
				final ResultSet rset = statement.executeQuery();
				if (rset.next())
				{
					lastIP = rset.getString("lastIP");
					userIP = rset.getString("userIP");
				}
				rset.close();
				statement.close();
				
				ConfirmDlg dlg = new ConfirmDlg(SystemMessageId.S1);
				if (userIP == null)
				{
					activeChar.setVoiceConfirmCmd("bind_process,on " + lastIP);
					dlg.addString("Are you sure, for bind your account for ip " + lastIP);
				}
				else
				{
					activeChar.setVoiceConfirmCmd("bind_process,off");
					dlg.addString("Are you sure, for unbind ip your account ?");
				}
				activeChar.sendPacket(dlg);
				
			}
			else if (command.equalsIgnoreCase("bind_process"))
			{
				int ipUpdated = 0;
				final StringTokenizer st = new StringTokenizer(param);
				String mode = null;
				
				if (st.hasMoreTokens())
				{
					mode = st.nextToken();
				}
				if (mode.equalsIgnoreCase("on") && st.hasMoreTokens())
				{
					userIP = st.nextToken();
				}
				
				final PreparedStatement ps = con.prepareStatement("UPDATE accounts SET userIP=? WHERE login=?");
				ps.setString(1, (mode.equalsIgnoreCase("on") ? userIP : null));
				ps.setString(2, activeChar.getAccountName());
				ipUpdated = ps.executeUpdate();
				ps.close();
				
				_log.info("Character " + activeChar.getName() + " has " + (mode.equalsIgnoreCase("on") ? " bind account ip " + userIP : " unbind account on ip"));
				
				if (ipUpdated > 0)
				{
					activeChar.sendMessage("Your account bind change successfully!");
				}
				else
				{
					activeChar.sendMessage("Your account bind change troubles!");
				}
			}
			
		}
		catch (SQLException e1)
		{
			_log.info(e1.getMessage());
		}
		
		return true;
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return _voicedCommands;
	}
	
}
