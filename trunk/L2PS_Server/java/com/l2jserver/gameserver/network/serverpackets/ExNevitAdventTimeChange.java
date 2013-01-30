package com.l2jserver.gameserver.network.serverpackets;

public class ExNevitAdventTimeChange extends L2GameServerPacket
{
	private int _time;
	private boolean _aktivni;
	
	public ExNevitAdventTimeChange(int cas, boolean aktivni)
	{
		_time = cas;
		_aktivni = aktivni;
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0xFE);
		writeH(0xE1);
		writeC(_aktivni ? 1 : 0);
		writeD(_time);
	}
}