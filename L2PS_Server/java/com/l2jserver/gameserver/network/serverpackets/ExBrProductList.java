package com.l2jserver.gameserver.network.serverpackets;

import java.util.Map;

import com.l2jserver.gameserver.datatables.PrimeShopTable;

public class ExBrProductList extends L2GameServerPacket
{
	private final Map<Integer, PrimeShopTable.PrimeShopItem> primeList;
	
	public ExBrProductList()
	{
		this.primeList = PrimeShopTable.getInstance().getPrimeItems();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected final void writeImpl()
	{
		writeC(254);
		writeH(214);
		writeH(this.primeList.size());
		writeH(0);
		
		for (Map.Entry entrySet : this.primeList.entrySet())
		{
			PrimeShopTable.PrimeShopItem item = (PrimeShopTable.PrimeShopItem) entrySet.getValue();
			
			writeD(((Integer) entrySet.getKey()).intValue());
			writeH(item.getPrimeItemCat());
			writeD(item.getPrimeItemPrice());
			writeD(item.getPrimeType());
			writeD(0);
			writeD(0);
			writeC(0);
			writeC(0);
			writeC(0);
			writeC(0);
			writeC(0);
			writeD(0);
			writeD(0);
		}
	}
	
	public String getType()
	{
		return "[S] FE:D6 ExBRProductList";
	}
}