package com.l2jserver.gameserver.features.data;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.util.Rnd;

public class _0_FemaleSpikedStakato extends AbstractNpcAI
{
	private static final int MALE_SPIKED_STAKATO = 22621;
	private static final int SPIKED_STAKATO_GUARD = 22619;
	
	public _0_FemaleSpikedStakato(int questId, String name, String descr)
	{
		super(name, descr);
		
		addKillId(MALE_SPIKED_STAKATO);
		addKillId(SPIKED_STAKATO_GUARD);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		L2Npc couple = getCouple(npc);
		if ((couple != null) && (!couple.isDead()))
		{
			for (int i = 0; i < 3; i++)
			{
				L2Attackable guard = (L2Attackable) addSpawn(SPIKED_STAKATO_GUARD, npc.getX() + Rnd.get(10, 50), npc.getY() + Rnd.get(10, 50), npc.getZ(), 0, false, 240000, true);
				guard.setRunning();
				guard.addDamageHate(killer, 1, 99999);
				guard.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, killer);
			}
		}
		return super.onKill(npc, killer, isPet);
	}
	
	public L2Npc getCouple(L2Npc couple)
	{
		if (((L2MonsterInstance) couple).getMinionList().getSpawnedMinions().size() > 0)
		{
			return ((L2MonsterInstance) couple).getMinionList().getSpawnedMinions().get(0);
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new _0_FemaleSpikedStakato(-1, "FemaleSpikedStakato", "data");
	}
}