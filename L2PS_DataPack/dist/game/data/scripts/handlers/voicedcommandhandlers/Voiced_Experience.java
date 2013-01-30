package handlers.voicedcommandhandlers;

import com.l2jserver.gameserver.handler.IVoicedCommandHandler;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.CreatureSay;
import com.l2jserver.gameserver.network.clientpackets.Say2;

public class Voiced_Experience implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{ 
		"allow_xp_sp" 
	};

	@Override
	public boolean useVoicedCommand(String command, L2PcInstance player, String params)
	{
		if (command.equalsIgnoreCase("allow_xp_sp"))
		{
			if (player != null)
			{
				if (player.canGetExpAndSp() == true)
				{
					player.setCanGetExpAndSp(false);
					player.sendPacket(new CreatureSay(0, Say2.PARTY, "Server", "Exp and Sp Disabled."));
				}
				else
				{
					player.setCanGetExpAndSp(true);
					player.sendPacket(new CreatureSay(0, Say2.PARTY, "Server", "Exp and Sp Enabled."));
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