package group_template;

import npc.AbstractNpcAI;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.util.Rnd;

public class NecromancerOfTheValley extends AbstractNpcAI
{	
	public NecromancerOfTheValley(int questId, String name, String descr)
	{
		super(name, descr);
		addKillId(22858);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		if (Rnd.get(100) < 30)
		{
			L2Character attacker = isPet ? killer.getSummon() : killer;
			// Exploding Orc Ghost
			L2Attackable explodingOrc = (L2Attackable) addSpawn(22818, npc.getX(), npc.getY(), npc.getZ() + 10, npc.getHeading(), false, 0, true);
			explodingOrc.setRunning();
			explodingOrc.addDamageHate(attacker, 0, 500);
			explodingOrc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
			
			// Wrathful Orc Ghost
			L2Attackable  wrathfulOrc = (L2Attackable) addSpawn(22819, npc.getX(), npc.getY(), npc.getZ() + 10, npc.getHeading(), false, 0, false);
			wrathfulOrc.setRunning();
			wrathfulOrc.addDamageHate(attacker, 0, 500);
			wrathfulOrc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
			
		}
		return super.onKill(npc, killer, isPet);
	}
	
	public static void main(String[] args)
	{
		new NecromancerOfTheValley(-1, "NecromancerOfTheValley", "group_template");
	}
}