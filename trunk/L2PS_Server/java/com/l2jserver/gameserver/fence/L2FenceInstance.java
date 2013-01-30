package com.l2jserver.gameserver.fence;

import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/*
 * Fixed By L2PSTeam
 */
public final class L2FenceInstance extends L2Object
{
	private final int _type;
	private final int _width;
	private final int _height;
	
	public L2FenceInstance(int objectId, int type, int width, int height)
	{
		super(objectId);
		_type = type;
		_width = width;
		_height = height;
	}
	
	@Override
	public void sendInfo(L2PcInstance activeChar)
	{
		activeChar.sendPacket(new ExColosseumFenceInfoPacket(this));
	}
	
	public int getType()
	{
		return _type;
	}
	
	public int getWidth()
	{
		return _width;
	}
	
	public int getHeight()
	{
		return _height;
	}
	
	@Override
	public boolean isAutoAttackable(L2Character attacker)
	{
		return false;
	}
	
}