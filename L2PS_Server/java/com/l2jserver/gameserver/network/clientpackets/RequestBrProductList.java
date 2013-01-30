package com.l2jserver.gameserver.network.clientpackets;

import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.ExBrProductList;

public final class RequestBrProductList extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
	}
	
	@Override
	protected void runImpl()
	{
		L2PcInstance player = getClient().getActiveChar();
		if (player != null)
		{
			player.sendPacket(new ExBrProductList());
		}
	}
	
	@Override
	public String getType()
	{
		return "[C] D0:8A RequestBRProductList";
	}
}