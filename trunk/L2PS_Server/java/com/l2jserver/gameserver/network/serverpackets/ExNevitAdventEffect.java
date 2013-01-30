package com.l2jserver.gameserver.network.serverpackets;

public class ExNevitAdventEffect extends L2GameServerPacket
{
	private int _cas;
	
	public ExNevitAdventEffect(int cas)
	{
		_cas = cas;
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0xFE);
		writeH(0xE0);
		writeD(_cas);
	}
}