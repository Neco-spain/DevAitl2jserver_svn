package com.l2jserver.gameserver.network.serverpackets;

import com.l2jserver.gameserver.datatables.AdventTable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

public class ExNevitAdventPointInfoPacket extends L2GameServerPacket
{
	private int _points;
	
	public ExNevitAdventPointInfoPacket(L2PcInstance player)
	{
		_points = AdventTable.getInstance().getAdventPoints(player.getObjectId());
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0xFE);
		writeH(0xDF);
		writeD(_points); // 72 = 1%, max 7200 = 100%
	}
}