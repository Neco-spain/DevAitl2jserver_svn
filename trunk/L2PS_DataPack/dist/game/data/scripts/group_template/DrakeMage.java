/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package group_template;

import npc.AbstractNpcAI;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.L2CharPosition;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

public class DrakeMage extends AbstractNpcAI
{
	private static final int DRAKE_MAGE = 22851;
	private L2Npc drakemage1;
	private L2Npc drakemage2;
	private L2Npc drakemage3;
	private L2Npc drakemage4;
	private L2Npc drakemage5;
	private L2Npc drakemage6;
	private static final L2CharPosition drakemage1_1 = new L2CharPosition(145180, 113285, -3725, 0);
	private static final L2CharPosition drakemage1_2 = new L2CharPosition(145890, 112583, -3725, 0);
	private static final L2CharPosition drakemage1_3 = new L2CharPosition(147249, 112436, -3725, 0);
	private static final L2CharPosition drakemage1_4 = new L2CharPosition(148885, 113022, -3725, 0);
	private static final L2CharPosition drakemage1_5 = new L2CharPosition(149144, 114033, -3725, 0);
	private static final L2CharPosition drakemage1_6 = new L2CharPosition(149074, 115274, -3725, 0);
	private static final L2CharPosition drakemage1_7 = new L2CharPosition(148226, 115923, -3725, 0);
	private static final L2CharPosition drakemage1_8 = new L2CharPosition(146786, 116347, -3725, 0);
	private static final L2CharPosition drakemage1_9 = new L2CharPosition(145571, 115830, -3725, 0);
	private static final L2CharPosition drakemage1_10 = new L2CharPosition(145080, 114976, -3725, 0);
	private static final L2CharPosition drakemage1_11 = new L2CharPosition(144484, 114552, -3725, 0);
	private static final L2CharPosition drakemage1_12 = new L2CharPosition(145128, 113925, -3725, 0);
	
	private static final L2CharPosition drakemage2_1 = new L2CharPosition(149144, 114033, -3725, 0);
	private static final L2CharPosition drakemage2_2 = new L2CharPosition(149074, 115274, -3725, 0);
	private static final L2CharPosition drakemage2_3 = new L2CharPosition(148226, 115923, -3725, 0);
	private static final L2CharPosition drakemage2_4 = new L2CharPosition(146786, 116347, -3725, 0);
	private static final L2CharPosition drakemage2_5 = new L2CharPosition(145571, 115830, -3725, 0);
	private static final L2CharPosition drakemage2_6 = new L2CharPosition(145080, 114976, -3725, 0);
	private static final L2CharPosition drakemage2_7 = new L2CharPosition(144484, 114552, -3725, 0);
	private static final L2CharPosition drakemage2_8 = new L2CharPosition(145128, 113925, -3725, 0);
	private static final L2CharPosition drakemage2_9 = new L2CharPosition(145180, 113285, -3725, 0);
	private static final L2CharPosition drakemage2_10 = new L2CharPosition(145890, 112583, -3725, 0);
	private static final L2CharPosition drakemage2_11 = new L2CharPosition(147249, 112436, -3725, 0);
	private static final L2CharPosition drakemage2_12 = new L2CharPosition(148885, 113022, -3725, 0);
	
	private static final L2CharPosition drakemage3_1 = new L2CharPosition(145571, 115830, -3725, 0);
	private static final L2CharPosition drakemage3_2 = new L2CharPosition(145080, 114976, -3725, 0);
	private static final L2CharPosition drakemage3_3 = new L2CharPosition(144484, 114552, -3725, 0);
	private static final L2CharPosition drakemage3_4 = new L2CharPosition(145128, 113925, -3725, 0);
	private static final L2CharPosition drakemage3_5 = new L2CharPosition(145180, 113285, -3725, 0);
	private static final L2CharPosition drakemage3_6 = new L2CharPosition(145890, 112583, -3725, 0);
	private static final L2CharPosition drakemage3_7 = new L2CharPosition(147249, 112436, -3725, 0);
	private static final L2CharPosition drakemage3_8 = new L2CharPosition(148885, 113022, -3725, 0);
	private static final L2CharPosition drakemage3_9 = new L2CharPosition(149144, 114033, -3725, 0);
	private static final L2CharPosition drakemage3_10 = new L2CharPosition(149074, 115274, -3725, 0);
	private static final L2CharPosition drakemage3_11 = new L2CharPosition(148226, 115923, -3725, 0);
	private static final L2CharPosition drakemage3_12 = new L2CharPosition(146786, 116347, -3725, 0);
	
	private static final L2CharPosition drakemage4_1 = new L2CharPosition(144868, 114504, -3725, 0);
	private static final L2CharPosition drakemage4_2 = new L2CharPosition(145085, 113825, -3725, 0);
	private static final L2CharPosition drakemage4_3 = new L2CharPosition(145656, 112956, -3725, 0);
	private static final L2CharPosition drakemage4_4 = new L2CharPosition(146602, 112187, -3647, 0);
	private static final L2CharPosition drakemage4_5 = new L2CharPosition(148011, 112145, -3725, 0);
	private static final L2CharPosition drakemage4_6 = new L2CharPosition(148781, 112726, -3725, 0);
	private static final L2CharPosition drakemage4_7 = new L2CharPosition(149137, 113789, -3725, 0);
	private static final L2CharPosition drakemage4_8 = new L2CharPosition(149312, 114978, -3725, 0);
	private static final L2CharPosition drakemage4_9 = new L2CharPosition(148314, 115972, -3725, 0);
	private static final L2CharPosition drakemage4_10 = new L2CharPosition(147086, 116425, -3708, 0);
	private static final L2CharPosition drakemage4_11 = new L2CharPosition(145557, 115653, -3725, 0);
	private static final L2CharPosition drakemage4_12 = new L2CharPosition(145150, 114855, -3725, 0);
	
	private static final L2CharPosition drakemage5_1 = new L2CharPosition(148011, 112145, -3725, 0);
	private static final L2CharPosition drakemage5_2 = new L2CharPosition(148781, 112726, -3725, 0);
	private static final L2CharPosition drakemage5_3 = new L2CharPosition(149137, 113789, -3725, 0);
	private static final L2CharPosition drakemage5_4 = new L2CharPosition(149312, 114978, -3725, 0);
	private static final L2CharPosition drakemage5_5 = new L2CharPosition(148314, 115972, -3725, 0);
	private static final L2CharPosition drakemage5_6 = new L2CharPosition(147086, 116425, -3708, 0);
	private static final L2CharPosition drakemage5_7 = new L2CharPosition(145557, 115653, -3725, 0);
	private static final L2CharPosition drakemage5_8 = new L2CharPosition(145150, 114855, -3725, 0);
	private static final L2CharPosition drakemage5_9 = new L2CharPosition(144868, 114504, -3725, 0);
	private static final L2CharPosition drakemage5_10 = new L2CharPosition(145085, 113825, -3725, 0);
	private static final L2CharPosition drakemage5_11 = new L2CharPosition(145656, 112956, -3725, 0);
	private static final L2CharPosition drakemage5_12 = new L2CharPosition(146602, 112187, -3647, 0);
	
	private static final L2CharPosition drakemage6_1 = new L2CharPosition(148314, 115972, -3725, 0);
	private static final L2CharPosition drakemage6_2 = new L2CharPosition(147086, 116425, -3708, 0);
	private static final L2CharPosition drakemage6_3 = new L2CharPosition(145557, 115653, -3725, 0);
	private static final L2CharPosition drakemage6_4 = new L2CharPosition(145150, 114855, -3725, 0);
	private static final L2CharPosition drakemage6_5 = new L2CharPosition(144868, 114504, -3725, 0);
	private static final L2CharPosition drakemage6_6 = new L2CharPosition(145085, 113825, -3725, 0);
	private static final L2CharPosition drakemage6_7 = new L2CharPosition(145656, 112956, -3725, 0);
	private static final L2CharPosition drakemage6_8 = new L2CharPosition(146602, 112187, -3647, 0);
	private static final L2CharPosition drakemage6_9 = new L2CharPosition(148011, 112145, -3725, 0);
	private static final L2CharPosition drakemage6_10 = new L2CharPosition(148781, 112726, -3725, 0);
	private static final L2CharPosition drakemage6_11 = new L2CharPosition(149137, 113789, -3725, 0);
	private static final L2CharPosition drakemage6_12 = new L2CharPosition(149312, 114978, -3725, 0);
	
	public DrakeMage(int questId, String name, String descr)
	{
		super(name, descr);
		
		addKillId(22851);
		addAggroRangeEnterId(22851);
		
		startQuestTimer("drakemage1_spawn", 7000, null, null);
		startQuestTimer("drakemage2_spawn", 7000, null, null);
		startQuestTimer("drakemage3_spawn", 7000, null, null);
		startQuestTimer("drakemage4_spawn", 7000, null, null);
		startQuestTimer("drakemage5_spawn", 7000, null, null);
		startQuestTimer("drakemage6_spawn", 7000, null, null);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if ((event.equalsIgnoreCase("drakemage1_spawn")) && (drakemage1 == null))
		{
			drakemage1 = addSpawn(22851, 145180, 113285, -3725, 0, false, 0);
			drakemage1.setIsNoRndWalk(true);
			drakemage1.setRunning();
			startQuestTimer("trasa_1", 7000, drakemage1, null);
		}
		else if ((event.equalsIgnoreCase("trasa_nova")) && (drakemage1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_nova", 7000, drakemage1, null);
			}
			else
			{
				drakemage1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage1_1);
				drakemage1.setRunning();
				startQuestTimer("trasa_1", 7000, drakemage1, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_1")) && (drakemage1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_1", 7000, drakemage1, null);
			}
			else
			{
				if (!drakemage1.isInsideRadius(drakemage1_1.x, drakemage1_1.y, drakemage1_1.z, 100, true, false))
					drakemage1.teleToLocation(drakemage1_1.x, drakemage1_1.y, drakemage1_1.z);
				drakemage1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage1_2);
				drakemage1.setRunning();
				startQuestTimer("trasa_2", 7000, drakemage1, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_2")) && (drakemage1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_2", 7000, drakemage1, null);
			}
			else
			{
				drakemage1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage1_3);
				drakemage1.setRunning();
				startQuestTimer("trasa_3", 7000, drakemage1, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_3")) && (drakemage1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_3", 7000, drakemage1, null);
			}
			else
			{
				drakemage1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage1_4);
				drakemage1.setRunning();
				startQuestTimer("trasa_4", 7000, drakemage1, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_4")) && (drakemage1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_4", 7000, drakemage1, null);
			}
			else
			{
				drakemage1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage1_5);
				drakemage1.setRunning();
				startQuestTimer("trasa_5", 7000, drakemage1, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_5")) && (drakemage1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_5", 7000, drakemage1, null);
			}
			else
			{
				drakemage1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage1_6);
				drakemage1.setRunning();
				startQuestTimer("trasa_6", 7000, drakemage1, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_6")) && (drakemage1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_6", 7000, drakemage1, null);
			}
			else
			{
				drakemage1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage1_7);
				drakemage1.setRunning();
				startQuestTimer("trasa_7", 7000, drakemage1, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_7")) && (drakemage1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_7", 7000, drakemage1, null);
			}
			else
			{
				drakemage1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage1_8);
				drakemage1.setRunning();
				startQuestTimer("trasa_8", 7000, drakemage1, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_8")) && (drakemage1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_8", 7000, drakemage1, null);
			}
			else
			{
				drakemage1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage1_9);
				drakemage1.setRunning();
				startQuestTimer("trasa_9", 7000, drakemage1, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_9")) && (drakemage1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_9", 7000, drakemage1, null);
			}
			else
			{
				drakemage1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage1_10);
				drakemage1.setRunning();
				startQuestTimer("trasa_10", 7000, drakemage1, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_10")) && (drakemage1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_10", 7000, drakemage1, null);
			}
			else
			{
				drakemage1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage1_11);
				drakemage1.setRunning();
				startQuestTimer("trasa_11", 7000, drakemage1, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_11")) && (drakemage1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_11", 7000, drakemage1, null);
			}
			else
			{
				drakemage1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage1_12);
				drakemage1.setRunning();
				startQuestTimer("trasa_nova", 7000, drakemage1, null);
			}
		}
		else if ((event.equalsIgnoreCase("drakemage2_spawn")) && (drakemage2 == null))
		{
			drakemage2 = addSpawn(22851, 149144, 114033, -3725, 0, false, 0);
			drakemage2.setIsNoRndWalk(true);
			drakemage2.setRunning();
			startQuestTimer("trasa_12", 7000, drakemage2, null);
		}
		else if ((event.equalsIgnoreCase("trasa_nova2")) && (drakemage2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_nova2", 7000, drakemage2, null);
			}
			else
			{
				drakemage2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage2_1);
				drakemage2.setRunning();
				startQuestTimer("trasa_12", 7000, drakemage2, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_12")) && (drakemage2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_12", 7000, drakemage2, null);
			}
			else
			{
				if (!drakemage2.isInsideRadius(drakemage2_1.x, drakemage2_1.y, drakemage2_1.z, 100, true, false))
					drakemage2.teleToLocation(drakemage2_1.x, drakemage2_1.y, drakemage2_1.z);
				drakemage2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage2_2);
				drakemage2.setRunning();
				startQuestTimer("trasa_13", 7000, drakemage2, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_13")) && (drakemage2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_13", 7000, drakemage2, null);
			}
			else
			{
				drakemage2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage2_3);
				drakemage2.setRunning();
				startQuestTimer("trasa_14", 7000, drakemage2, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_14")) && (drakemage2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_14", 7000, drakemage2, null);
			}
			else
			{
				drakemage2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage2_4);
				drakemage2.setRunning();
				startQuestTimer("trasa_15", 7000, drakemage2, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_15")) && (drakemage2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_15", 7000, drakemage2, null);
			}
			else
			{
				drakemage2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage2_5);
				drakemage2.setRunning();
				startQuestTimer("trasa_16", 7000, drakemage2, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_16")) && (drakemage2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_16", 7000, drakemage2, null);
			}
			else
			{
				drakemage2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage2_6);
				drakemage2.setRunning();
				startQuestTimer("trasa_17", 7000, drakemage2, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_17")) && (drakemage2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_17", 7000, drakemage2, null);
			}
			else
			{
				drakemage2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage2_7);
				drakemage2.setRunning();
				startQuestTimer("trasa_18", 7000, drakemage2, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_18")) && (drakemage2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_18", 7000, drakemage2, null);
			}
			else
			{
				drakemage2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage2_8);
				drakemage2.setRunning();
				startQuestTimer("trasa_19", 7000, drakemage2, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_19")) && (drakemage2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_19", 7000, drakemage2, null);
			}
			else
			{
				drakemage2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage2_9);
				drakemage2.setRunning();
				startQuestTimer("trasa_20", 7000, drakemage2, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_20")) && (drakemage2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_20", 7000, drakemage2, null);
			}
			else
			{
				drakemage2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage2_10);
				drakemage2.setRunning();
				startQuestTimer("trasa_21", 7000, drakemage2, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_21")) && (drakemage2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_21", 7000, drakemage2, null);
			}
			else
			{
				drakemage2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage2_11);
				drakemage2.setRunning();
				startQuestTimer("trasa_22", 7000, drakemage2, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_22")) && (drakemage2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_22", 7000, drakemage2, null);
			}
			else
			{
				drakemage2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage2_12);
				drakemage2.setRunning();
				startQuestTimer("trasa_nova2", 7000, drakemage2, null);
			}
		}
		else if ((event.equalsIgnoreCase("drakemage3_spawn")) && (drakemage3 == null))
		{
			drakemage3 = addSpawn(DRAKE_MAGE, 145571, 115830, -3725, 0, false, 0);
			drakemage3.setIsNoRndWalk(true);
			drakemage3.setRunning();
			startQuestTimer("trasa_23", 7000, drakemage3, null);
		}
		else if ((event.equalsIgnoreCase("trasa_nova3")) && (drakemage3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_nova3", 7000, drakemage3, null);
			}
			else
			{
				drakemage3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage3_1);
				drakemage3.setRunning();
				startQuestTimer("trasa_23", 7000, drakemage3, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_23")) && (drakemage3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_23", 7000, drakemage3, null);
			}
			else
			{
				if (!drakemage3.isInsideRadius(drakemage3_1.x, drakemage3_1.y, drakemage3_1.z, 100, true, false))
					drakemage3.teleToLocation(drakemage3_1.x, drakemage3_1.y, drakemage3_1.z);
				drakemage3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage3_2);
				drakemage3.setRunning();
				startQuestTimer("trasa_24", 7000, drakemage3, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_24")) && (drakemage3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_24", 7000, drakemage3, null);
			}
			else
			{
				drakemage3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage3_3);
				drakemage3.setRunning();
				startQuestTimer("trasa_25", 7000, drakemage3, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_25")) && (drakemage3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_25", 7000, drakemage3, null);
			}
			else
			{
				drakemage3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage3_4);
				drakemage3.setRunning();
				startQuestTimer("trasa_26", 7000, drakemage3, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_26")) && (drakemage3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_26", 7000, drakemage3, null);
			}
			else
			{
				drakemage3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage3_5);
				drakemage3.setRunning();
				startQuestTimer("trasa_27", 7000, drakemage3, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_27")) && (drakemage3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_27", 7000, drakemage3, null);
			}
			else
			{
				drakemage3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage3_6);
				drakemage3.setRunning();
				startQuestTimer("trasa_28", 7000, drakemage3, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_28")) && (drakemage3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_28", 7000, drakemage3, null);
			}
			else
			{
				drakemage3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage3_7);
				drakemage3.setRunning();
				startQuestTimer("trasa_29", 7000, drakemage3, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_29")) && (drakemage3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_29", 7000, drakemage3, null);
			}
			else
			{
				drakemage3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage3_8);
				drakemage3.setRunning();
				startQuestTimer("trasa_30", 7000, drakemage3, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_30")) && (drakemage3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_30", 7000, drakemage3, null);
			}
			else
			{
				drakemage3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage3_9);
				drakemage3.setRunning();
				startQuestTimer("trasa_31", 7000, drakemage3, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_31")) && (drakemage3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_31", 7000, drakemage3, null);
			}
			else
			{
				drakemage3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage3_10);
				drakemage3.setRunning();
				startQuestTimer("trasa_32", 7000, drakemage3, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_32")) && (drakemage3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_32", 7000, drakemage3, null);
			}
			else
			{
				drakemage3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage3_11);
				drakemage3.setRunning();
				startQuestTimer("trasa_33", 7000, drakemage3, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_33")) && (drakemage3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_33", 7000, drakemage3, null);
			}
			else
			{
				drakemage3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage3_12);
				drakemage3.setRunning();
				startQuestTimer("trasa_nova3", 7000, drakemage3, null);
			}
		}
		else if ((event.equalsIgnoreCase("drakemage4_spawn")) && (drakemage4 == null))
		{
			drakemage4 = addSpawn(DRAKE_MAGE, 144868, 114504, -3725, 0, false, 0);
			drakemage4.setIsNoRndWalk(true);
			drakemage4.setRunning();
			startQuestTimer("trasa_34", 7000, drakemage4, null);
		}
		else if ((event.equalsIgnoreCase("trasa_nova4")) && (drakemage4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_nova4", 7000, drakemage4, null);
			}
			else
			{
				drakemage4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage4_1);
				drakemage4.setRunning();
				startQuestTimer("trasa_34", 7000, drakemage4, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_34")) && (drakemage4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_34", 7000, drakemage4, null);
			}
			else
			{
				if (!drakemage4.isInsideRadius(drakemage4_1.x, drakemage4_1.y, drakemage4_1.z, 100, true, false))
					drakemage4.teleToLocation(drakemage4_1.x, drakemage4_1.y, drakemage4_1.z);
				drakemage4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage4_2);
				drakemage4.setRunning();
				startQuestTimer("trasa_35", 7000, drakemage4, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_35")) && (drakemage4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_35", 7000, drakemage4, null);
			}
			else
			{
				drakemage4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage4_3);
				drakemage4.setRunning();
				startQuestTimer("trasa_36", 7000, drakemage4, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_36")) && (drakemage4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_36", 7000, drakemage4, null);
			}
			else
			{
				drakemage4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage4_4);
				drakemage4.setRunning();
				startQuestTimer("trasa_37", 7000, drakemage4, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_37")) && (drakemage4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_37", 7000, drakemage4, null);
			}
			else
			{
				drakemage4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage4_5);
				drakemage4.setRunning();
				startQuestTimer("trasa_38", 7000, drakemage4, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_38")) && (drakemage4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_38", 7000, drakemage4, null);
			}
			else
			{
				drakemage4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage4_6);
				drakemage4.setRunning();
				startQuestTimer("trasa_39", 7000, drakemage4, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_39")) && (drakemage4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_39", 7000, drakemage4, null);
			}
			else
			{
				drakemage4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage4_7);
				drakemage4.setRunning();
				startQuestTimer("trasa_40", 7000, drakemage4, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_40")) && (drakemage4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_40", 7000, drakemage4, null);
			}
			else
			{
				drakemage4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage4_8);
				drakemage4.setRunning();
				startQuestTimer("trasa_41", 7000, drakemage4, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_41")) && (drakemage4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_41", 7000, drakemage4, null);
			}
			else
			{
				drakemage4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage4_9);
				drakemage4.setRunning();
				startQuestTimer("trasa_42", 7000, drakemage4, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_42")) && (drakemage4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_42", 7000, drakemage4, null);
			}
			else
			{
				drakemage4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage4_10);
				drakemage4.setRunning();
				startQuestTimer("trasa_43", 7000, drakemage4, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_43")) && (drakemage4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_43", 7000, drakemage4, null);
			}
			else
			{
				drakemage4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage4_11);
				drakemage4.setRunning();
				startQuestTimer("trasa_44", 7000, drakemage4, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_44")) && (drakemage4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_44", 7000, drakemage4, null);
			}
			else
			{
				drakemage4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage4_12);
				drakemage4.setRunning();
				startQuestTimer("trasa_nova4", 7000, drakemage4, null);
			}
		}
		else if ((event.equalsIgnoreCase("drakemage5_spawn")) && (drakemage5 == null))
		{
			drakemage5 = addSpawn(DRAKE_MAGE, 148011, 112145, -3725, 0, false, 0);
			drakemage5.setIsNoRndWalk(true);
			drakemage5.setRunning();
			startQuestTimer("trasa_45", 7000, drakemage5, null);
		}
		else if ((event.equalsIgnoreCase("trasa_nova5")) && (drakemage5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_nova5", 7000, drakemage5, null);
			}
			else
			{
				drakemage5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage5_1);
				drakemage5.setRunning();
				startQuestTimer("trasa_45", 7000, drakemage5, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_45")) && (drakemage5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_45", 7000, drakemage5, null);
			}
			else
			{
				if (!drakemage5.isInsideRadius(drakemage5_1.x, drakemage5_1.y, drakemage5_1.z, 100, true, false))
					drakemage5.teleToLocation(drakemage5_1.x, drakemage5_1.y, drakemage5_1.z);
				drakemage5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage5_2);
				drakemage5.setRunning();
				startQuestTimer("trasa_46", 7000, drakemage5, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_46")) && (drakemage5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_46", 7000, drakemage5, null);
			}
			else
			{
				drakemage5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage5_3);
				drakemage5.setRunning();
				startQuestTimer("trasa_47", 7000, drakemage5, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_47")) && (drakemage5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_47", 7000, drakemage5, null);
			}
			else
			{
				drakemage5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage5_4);
				drakemage5.setRunning();
				startQuestTimer("trasa_48", 7000, drakemage5, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_48")) && (drakemage5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_48", 7000, drakemage5, null);
			}
			else
			{
				drakemage5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage5_5);
				drakemage5.setRunning();
				startQuestTimer("trasa_49", 7000, drakemage5, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_49")) && (drakemage5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_49", 7000, drakemage5, null);
			}
			else
			{
				drakemage5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage5_6);
				drakemage5.setRunning();
				startQuestTimer("trasa_50", 7000, drakemage5, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_50")) && (drakemage5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_50", 7000, drakemage5, null);
			}
			else
			{
				drakemage5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage5_7);
				drakemage5.setRunning();
				startQuestTimer("trasa_51", 7000, drakemage5, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_51")) && (drakemage5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_51", 7000, drakemage5, null);
			}
			else
			{
				drakemage5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage5_8);
				drakemage5.setRunning();
				startQuestTimer("trasa_52", 7000, drakemage5, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_52")) && (drakemage5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_52", 7000, drakemage5, null);
			}
			else
			{
				drakemage5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage5_9);
				drakemage5.setRunning();
				startQuestTimer("trasa_53", 7000, drakemage5, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_53")) && (drakemage5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_53", 7000, drakemage5, null);
			}
			else
			{
				drakemage5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage5_10);
				drakemage5.setRunning();
				startQuestTimer("trasa_54", 7000, drakemage5, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_54")) && (drakemage5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_54", 7000, drakemage5, null);
			}
			else
			{
				drakemage5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage5_11);
				drakemage5.setRunning();
				startQuestTimer("trasa_55", 7000, drakemage5, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_55")) && (drakemage5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_55", 7000, drakemage5, null);
			}
			else
			{
				drakemage5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage5_12);
				drakemage5.setRunning();
				startQuestTimer("trasa_nova5", 7000, drakemage5, null);
			}
		}
		else if ((event.equalsIgnoreCase("drakemage6_spawn")) && (drakemage6 == null))
		{
			drakemage6 = addSpawn(DRAKE_MAGE, 148314, 115972, -3725, 0, false, 0);
			drakemage6.setIsNoRndWalk(true);
			drakemage6.setRunning();
			startQuestTimer("trasa_56", 7000, drakemage6, null);
		}
		else if ((event.equalsIgnoreCase("trasa_nova6")) && (drakemage6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_nova6", 7000, drakemage6, null);
			}
			else
			{
				drakemage6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage6_1);
				drakemage6.setRunning();
				startQuestTimer("trasa_56", 7000, drakemage6, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_56")) && (drakemage6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_56", 7000, drakemage6, null);
			}
			else
			{
				if (!drakemage6.isInsideRadius(drakemage6_1.x, drakemage6_1.y, drakemage6_1.z, 100, true, false))
					drakemage6.teleToLocation(drakemage6_1.x, drakemage6_1.y, drakemage6_1.z);
				drakemage6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage6_2);
				drakemage6.setRunning();
				startQuestTimer("trasa_57", 7000, drakemage6, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_57")) && (drakemage6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_57", 7000, drakemage6, null);
			}
			else
			{
				drakemage6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage6_3);
				drakemage6.setRunning();
				startQuestTimer("trasa_58", 7000, drakemage6, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_58")) && (drakemage6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_58", 7000, drakemage6, null);
			}
			else
			{
				drakemage6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage6_4);
				drakemage6.setRunning();
				startQuestTimer("trasa_59", 7000, drakemage6, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_59")) && (drakemage6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_59", 7000, drakemage6, null);
			}
			else
			{
				drakemage6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage6_5);
				drakemage6.setRunning();
				startQuestTimer("trasa_60", 7000, drakemage6, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_60")) && (drakemage6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_60", 7000, drakemage6, null);
			}
			else
			{
				drakemage6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage6_6);
				drakemage6.setRunning();
				startQuestTimer("trasa_61", 7000, drakemage6, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_61")) && (drakemage6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_61", 7000, drakemage6, null);
			}
			else
			{
				drakemage6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage6_7);
				drakemage6.setRunning();
				startQuestTimer("trasa_62", 7000, drakemage6, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_62")) && (drakemage6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_62", 7000, drakemage6, null);
			}
			else
			{
				drakemage6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage6_8);
				drakemage6.setRunning();
				startQuestTimer("trasa_63", 7000, drakemage6, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_63")) && (drakemage6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_63", 7000, drakemage6, null);
			}
			else
			{
				drakemage6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage6_9);
				drakemage6.setRunning();
				startQuestTimer("trasa_64", 7000, drakemage6, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_64")) && (drakemage6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_64", 7000, drakemage6, null);
			}
			else
			{
				drakemage6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage6_10);
				drakemage6.setRunning();
				startQuestTimer("trasa_65", 7000, drakemage6, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_65")) && (drakemage6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_65", 7000, drakemage6, null);
			}
			else
			{
				drakemage6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage6_11);
				drakemage6.setRunning();
				startQuestTimer("trasa_66", 7000, drakemage6, null);
			}
		}
		else if ((event.equalsIgnoreCase("trasa_66")) && (drakemage6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("trasa_66", 7000, drakemage6, null);
			}
			else
			{
				drakemage6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakemage6_12);
				drakemage6.setRunning();
				startQuestTimer("trasa_nova6", 7000, drakemage6, null);
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		if (npc == null)
		{
			return super.onKill(npc, killer, isPet);
		}
		if (npc.getNpcId() == DRAKE_MAGE)
		{
			if (npc == drakemage1)
			{
				startQuestTimer("drakemage1_spawn", 300000, null, null);
				drakemage1 = null;
			}
			
			if (npc == drakemage2)
			{
				startQuestTimer("drakemage2_spawn", 300000, null, null);
				drakemage2 = null;
			}
			
			if (npc == drakemage3)
			{
				startQuestTimer("drakemage3_spawn", 300000, null, null);
				drakemage3 = null;
			}
			
			if (npc == drakemage4)
			{
				startQuestTimer("drakemage4_spawn", 300000, null, null);
				drakemage4 = null;
			}
			
			if (npc == drakemage5)
			{
				startQuestTimer("drakemage5_spawn", 300000, null, null);
				drakemage5 = null;
			}
			
			if (npc == drakemage6)
			{
				startQuestTimer("drakemage6_spawn", 300000, null, null);
				drakemage6 = null;
			}
		}
		return super.onKill(npc, killer, isPet);
	}
	
	@Override
	public String onAggroRangeEnter(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		if ((!npc.isCastingNow()) && (!npc.isAttackingNow()) && (!npc.isInCombat()) && (!player.isDead()))
		{
			((L2Attackable) npc).addDamageHate(player, 0, 999);
			((L2Attackable) npc).getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
		}
		return super.onAggroRangeEnter(npc, player, isPet);
	}
	
	public static void main(String[] args)
	{
		new DrakeMage(-1, "DrakeMage", "group_template");
	}
}