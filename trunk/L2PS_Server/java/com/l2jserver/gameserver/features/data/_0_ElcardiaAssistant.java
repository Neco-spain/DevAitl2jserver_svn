package com.l2jserver.gameserver.features.data;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.network.serverpackets.MagicSkillUse;

public class _0_ElcardiaAssistant extends AbstractNpcAI
{
	private static final int ELCARDIA = 32785;
	
	public _0_ElcardiaAssistant(int questId, String name, String descr)
	{
		super(name, descr);
		addSpawnId(ELCARDIA);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if ((event.equalsIgnoreCase("check_buff")))
		{
			cancelQuestTimer("check_buff", npc, null);
			npc.setTarget(player);
			if (player.getCurrentHp() < (player.getMaxHp() * 0.8D))
			{
				npc.broadcastPacket(new MagicSkillUse(npc, player, 6724, 1, 1500, 0)); // heal
			}
			if (player.getCurrentMp() < (player.getMaxMp() * 0.5D))
			{
				npc.broadcastPacket(new MagicSkillUse(npc, player, 6728, 1, 6000, 0)); // recharge
			}
			if (player.isInCombat())
			{
				npc.broadcastPacket(new MagicSkillUse(npc, player, 6725, 1, 1000, 0)); // blessBlood
			}
			L2Effect vampRage = player.getFirstEffect(6727);
			if (vampRage == null)
			{
				npc.broadcastPacket(new MagicSkillUse(npc, player, 6727, 1, 500, 0)); // vampRage
			}
			L2Effect holyResist = player.getFirstEffect(6729);
			if (holyResist == null)
			{
				npc.broadcastPacket(new MagicSkillUse(npc, player, 6729, 1, 500, 0)); // holyResist
			}
			
			startQuestTimer("check_buff", 30000, npc, null);
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		startQuestTimer("check", 2000, npc, null);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new _0_ElcardiaAssistant(-1, _0_ElcardiaAssistant.class.getSimpleName(), "data");
	}
}