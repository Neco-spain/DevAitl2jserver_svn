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
package com.l2jserver.gameserver.features.data;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.L2CharPosition;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

public class _1_DrakeLeader extends AbstractNpcAI
{
	private static final int DRAKE_LEADER = 22848;
	private L2Npc drakeleader1;
	private L2Npc drakeleader2;
	private L2Npc drakeleader3;
	private L2Npc drakeleader4;
	private L2Npc drakeleader5;
	private L2Npc drakeleader6;
	private static final L2CharPosition drakeleader1_1 = new L2CharPosition(145452, 115611, -3725, 0);
	private static final L2CharPosition drakeleader1_2 = new L2CharPosition(145109, 114475, -3725, 0);
	private static final L2CharPosition drakeleader1_3 = new L2CharPosition(145597, 112768, -3725, 0);
	private static final L2CharPosition drakeleader1_4 = new L2CharPosition(146762, 112800, -3725, 0);
	private static final L2CharPosition drakeleader1_5 = new L2CharPosition(148644, 112478, -3725, 0);
	private static final L2CharPosition drakeleader1_6 = new L2CharPosition(148950, 113645, -3725, 0);
	private static final L2CharPosition drakeleader1_7 = new L2CharPosition(149138, 114907, -3725, 0);
	private static final L2CharPosition drakeleader1_8 = new L2CharPosition(148422, 115838, -3725, 0);
	private static final L2CharPosition drakeleader1_9 = new L2CharPosition(147331, 116621, -3704, 0);
	private static final L2CharPosition drakeleader1_10 = new L2CharPosition(146355, 116327, -3725, 0);
	private static final L2CharPosition drakeleader1_11 = new L2CharPosition(146096, 115898, -3725, 0);
	
	private static final L2CharPosition drakeleader2_1 = new L2CharPosition(148644, 112478, -3725, 0);
	private static final L2CharPosition drakeleader2_2 = new L2CharPosition(148950, 113645, -3725, 0);
	private static final L2CharPosition drakeleader2_3 = new L2CharPosition(149138, 114907, -3725, 0);
	private static final L2CharPosition drakeleader2_4 = new L2CharPosition(148422, 115838, -3725, 0);
	private static final L2CharPosition drakeleader2_5 = new L2CharPosition(147331, 116621, -3704, 0);
	private static final L2CharPosition drakeleader2_6 = new L2CharPosition(146355, 116327, -3725, 0);
	private static final L2CharPosition drakeleader2_7 = new L2CharPosition(146096, 115898, -3725, 0);
	private static final L2CharPosition drakeleader2_8 = new L2CharPosition(145452, 115611, -3725, 0);
	private static final L2CharPosition drakeleader2_9 = new L2CharPosition(145109, 114475, -3725, 0);
	private static final L2CharPosition drakeleader2_10 = new L2CharPosition(145597, 112768, -3725, 0);
	private static final L2CharPosition drakeleader2_11 = new L2CharPosition(146762, 112800, -3725, 0);
	
	private static final L2CharPosition drakeleader3_1 = new L2CharPosition(147331, 116621, -3704, 0);
	private static final L2CharPosition drakeleader3_2 = new L2CharPosition(146355, 116327, -3725, 0);
	private static final L2CharPosition drakeleader3_3 = new L2CharPosition(146096, 115898, -3725, 0);
	private static final L2CharPosition drakeleader3_4 = new L2CharPosition(145452, 115611, -3725, 0);
	private static final L2CharPosition drakeleader3_5 = new L2CharPosition(145109, 114475, -3725, 0);
	private static final L2CharPosition drakeleader3_6 = new L2CharPosition(145597, 112768, -3725, 0);
	private static final L2CharPosition drakeleader3_7 = new L2CharPosition(146762, 112800, -3725, 0);
	private static final L2CharPosition drakeleader3_8 = new L2CharPosition(148644, 112478, -3725, 0);
	private static final L2CharPosition drakeleader3_9 = new L2CharPosition(148950, 113645, -3725, 0);
	private static final L2CharPosition drakeleader3_10 = new L2CharPosition(149138, 114907, -3725, 0);
	private static final L2CharPosition drakeleader3_11 = new L2CharPosition(148422, 115838, -3725, 0);
	
	private static final L2CharPosition drakeleader4_1 = new L2CharPosition(148579, 111993, -3710, 0);
	private static final L2CharPosition drakeleader4_2 = new L2CharPosition(148931, 113094, -3725, 0);
	private static final L2CharPosition drakeleader4_3 = new L2CharPosition(149276, 114105, -3725, 0);
	private static final L2CharPosition drakeleader4_4 = new L2CharPosition(148909, 115547, -3725, 0);
	private static final L2CharPosition drakeleader4_5 = new L2CharPosition(147495, 116305, -3711, 0);
	private static final L2CharPosition drakeleader4_6 = new L2CharPosition(146110, 116156, -3725, 0);
	private static final L2CharPosition drakeleader4_7 = new L2CharPosition(145191, 114990, -3725, 0);
	private static final L2CharPosition drakeleader4_8 = new L2CharPosition(145025, 113670, -3725, 0);
	private static final L2CharPosition drakeleader4_9 = new L2CharPosition(145868, 112717, -3725, 0);
	private static final L2CharPosition drakeleader4_10 = new L2CharPosition(146762, 112951, -3725, 0);
	private static final L2CharPosition drakeleader4_11 = new L2CharPosition(147641, 112101, -3725, 0);
	
	private static final L2CharPosition drakeleader5_1 = new L2CharPosition(147495, 116305, -3711, 0);
	private static final L2CharPosition drakeleader5_2 = new L2CharPosition(146110, 116156, -3725, 0);
	private static final L2CharPosition drakeleader5_3 = new L2CharPosition(145191, 114990, -3725, 0);
	private static final L2CharPosition drakeleader5_4 = new L2CharPosition(145025, 113670, -3725, 0);
	private static final L2CharPosition drakeleader5_5 = new L2CharPosition(145868, 112717, -3725, 0);
	private static final L2CharPosition drakeleader5_6 = new L2CharPosition(146762, 112951, -3725, 0);
	private static final L2CharPosition drakeleader5_7 = new L2CharPosition(147641, 112101, -3725, 0);
	private static final L2CharPosition drakeleader5_8 = new L2CharPosition(148579, 111993, -3710, 0);
	private static final L2CharPosition drakeleader5_9 = new L2CharPosition(148931, 113094, -3725, 0);
	private static final L2CharPosition drakeleader5_10 = new L2CharPosition(149276, 114105, -3725, 0);
	private static final L2CharPosition drakeleader5_11 = new L2CharPosition(148909, 115547, -3725, 0);
	
	private static final L2CharPosition drakeleader6_1 = new L2CharPosition(145868, 112717, -3725, 0);
	private static final L2CharPosition drakeleader6_2 = new L2CharPosition(146762, 112951, -3725, 0);
	private static final L2CharPosition drakeleader6_3 = new L2CharPosition(147641, 112101, -3725, 0);
	private static final L2CharPosition drakeleader6_4 = new L2CharPosition(148579, 111993, -3710, 0);
	private static final L2CharPosition drakeleader6_5 = new L2CharPosition(148931, 113094, -3725, 0);
	private static final L2CharPosition drakeleader6_6 = new L2CharPosition(149276, 114105, -3725, 0);
	private static final L2CharPosition drakeleader6_7 = new L2CharPosition(148909, 115547, -3725, 0);
	private static final L2CharPosition drakeleader6_8 = new L2CharPosition(147495, 116305, -3711, 0);
	private static final L2CharPosition drakeleader6_9 = new L2CharPosition(146110, 116156, -3725, 0);
	private static final L2CharPosition drakeleader6_10 = new L2CharPosition(145191, 114990, -3725, 0);
	private static final L2CharPosition drakeleader6_11 = new L2CharPosition(145025, 113670, -3725, 0);
	
	public _1_DrakeLeader(int questId, String name, String descr)
	{
		super(name, descr);
		
		addKillId(DRAKE_LEADER);
		addAggroRangeEnterId(DRAKE_LEADER);
		
		startQuestTimer("drakeleader1_spawn", 1800, null, null);
		startQuestTimer("drakeleader2_spawn", 1800, null, null);
		startQuestTimer("drakeleader3_spawn", 1800, null, null);
		startQuestTimer("drakeleader4_spawn", 1800, null, null);
		startQuestTimer("drakeleader5_spawn", 1800, null, null);
		startQuestTimer("drakeleader6_spawn", 1800, null, null);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if ((event.equalsIgnoreCase("drakeleader1_spawn")) && (drakeleader1 == null))
		{
			drakeleader1 = addSpawn(DRAKE_LEADER, 145452, 115611, -3725, 0, false, 0);
			drakeleader1.setIsNoRndWalk(true);
			drakeleader1.setRunning();
			startQuestTimer("move_1", 9000, drakeleader1, null);
		}
		else if ((event.equalsIgnoreCase("move_new")) && (drakeleader1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_new", 9000, drakeleader1, null);
			}
			else
			{
				drakeleader1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader1_1);
				drakeleader1.setRunning();
				startQuestTimer("move_1", 9000, drakeleader1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_1")) && (drakeleader1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_1", 9000, drakeleader1, null);
			}
			else
			{
				if (!drakeleader1.isInsideRadius(drakeleader1_1.x, drakeleader1_1.y, drakeleader1_1.z, 100, true, false))
				{
					drakeleader1.teleToLocation(drakeleader1_1.x, drakeleader1_1.y, drakeleader1_1.z);
				}
				drakeleader1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader1_2);
				drakeleader1.setRunning();
				startQuestTimer("move_2", 9000, drakeleader1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_2")) && (drakeleader1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_2", 9000, drakeleader1, null);
			}
			else
			{
				drakeleader1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader1_3);
				drakeleader1.setRunning();
				startQuestTimer("move_3", 9000, drakeleader1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_3")) && (drakeleader1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_3", 9000, drakeleader1, null);
			}
			else
			{
				drakeleader1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader1_4);
				drakeleader1.setRunning();
				startQuestTimer("move_4", 9000, drakeleader1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_4")) && (drakeleader1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_4", 9000, drakeleader1, null);
			}
			else
			{
				drakeleader1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader1_5);
				drakeleader1.setRunning();
				startQuestTimer("move_5", 9000, drakeleader1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_5")) && (drakeleader1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_5", 9000, drakeleader1, null);
			}
			else
			{
				drakeleader1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader1_6);
				drakeleader1.setRunning();
				startQuestTimer("move_6", 9000, drakeleader1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_6")) && (drakeleader1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_6", 9000, drakeleader1, null);
			}
			else
			{
				drakeleader1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader1_7);
				drakeleader1.setRunning();
				startQuestTimer("move_7", 9000, drakeleader1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_7")) && (drakeleader1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_7", 9000, drakeleader1, null);
			}
			else
			{
				drakeleader1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader1_8);
				drakeleader1.setRunning();
				startQuestTimer("move_8", 9000, drakeleader1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_8")) && (drakeleader1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_8", 9000, drakeleader1, null);
			}
			else
			{
				drakeleader1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader1_9);
				drakeleader1.setRunning();
				startQuestTimer("move_9", 9000, drakeleader1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_9")) && (drakeleader1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_9", 9000, drakeleader1, null);
			}
			else
			{
				drakeleader1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader1_10);
				drakeleader1.setRunning();
				startQuestTimer("move_10", 9000, drakeleader1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_10")) && (drakeleader1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_10", 9000, drakeleader1, null);
			}
			else
			{
				drakeleader1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader1_11);
				drakeleader1.setRunning();
				startQuestTimer("move_new", 4000, drakeleader1, null);
			}
		}
		else if ((event.equalsIgnoreCase("drakeleader2_spawn")) && (drakeleader2 == null))
		{
			drakeleader2 = addSpawn(DRAKE_LEADER, 148644, 112478, -3725, 0, false, 0);
			drakeleader2.setIsNoRndWalk(true);
			drakeleader2.setRunning();
			startQuestTimer("move_1", 9000, drakeleader2, null);
		}
		else if ((event.equalsIgnoreCase("move_new2")) && (drakeleader2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_new2", 9000, drakeleader2, null);
			}
			else
			{
				drakeleader2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader2_1);
				drakeleader2.setRunning();
				startQuestTimer("move_11", 9000, drakeleader2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_11")) && (drakeleader2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_11", 9000, drakeleader2, null);
			}
			else
			{
				if (!drakeleader2.isInsideRadius(drakeleader2_1.x, drakeleader2_1.y, drakeleader2_1.z, 100, true, false))
				{
					drakeleader2.teleToLocation(drakeleader2_1.x, drakeleader2_1.y, drakeleader2_1.z);
				}
				drakeleader2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader2_2);
				drakeleader2.setRunning();
				startQuestTimer("move_12", 9000, drakeleader2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_12")) && (drakeleader2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_12", 9000, drakeleader2, null);
			}
			else
			{
				drakeleader2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader2_3);
				drakeleader2.setRunning();
				startQuestTimer("move_13", 9000, drakeleader2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_13")) && (drakeleader2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_13", 9000, drakeleader2, null);
			}
			else
			{
				drakeleader2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader2_4);
				drakeleader2.setRunning();
				startQuestTimer("move_14", 9000, drakeleader2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_14")) && (drakeleader2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_14", 9000, drakeleader2, null);
			}
			else
			{
				drakeleader2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader2_5);
				drakeleader2.setRunning();
				startQuestTimer("move_15", 9000, drakeleader2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_15")) && (drakeleader2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_15", 9000, drakeleader2, null);
			}
			else
			{
				drakeleader2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader2_6);
				drakeleader2.setRunning();
				startQuestTimer("move_16", 9000, drakeleader2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_16")) && (drakeleader2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_16", 9000, drakeleader2, null);
			}
			else
			{
				drakeleader2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader2_7);
				drakeleader2.setRunning();
				startQuestTimer("move_17", 9000, drakeleader2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_17")) && (drakeleader2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_17", 9000, drakeleader2, null);
			}
			else
			{
				drakeleader2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader2_8);
				drakeleader2.setRunning();
				startQuestTimer("move_18", 9000, drakeleader2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_18")) && (drakeleader2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_18", 9000, drakeleader2, null);
			}
			else
			{
				drakeleader2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader2_9);
				drakeleader2.setRunning();
				startQuestTimer("move_19", 9000, drakeleader2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_19")) && (drakeleader2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_19", 9000, drakeleader2, null);
			}
			else
			{
				drakeleader2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader2_10);
				drakeleader2.setRunning();
				startQuestTimer("move_20", 9000, drakeleader2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_20")) && (drakeleader2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_20", 9000, drakeleader2, null);
			}
			else
			{
				drakeleader2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader2_11);
				drakeleader2.setRunning();
				startQuestTimer("move_new2", 4000, drakeleader2, null);
			}
		}
		else if ((event.equalsIgnoreCase("drakeleader3_spawn")) && (drakeleader3 == null))
		{
			drakeleader3 = addSpawn(DRAKE_LEADER, 147331, 116621, -3704, 0, false, 0);
			drakeleader3.setIsNoRndWalk(true);
			drakeleader3.setRunning();
			startQuestTimer("move_21", 9000, drakeleader3, null);
		}
		else if ((event.equalsIgnoreCase("move_new3")) && (drakeleader3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_new3", 9000, drakeleader3, null);
			}
			else
			{
				drakeleader3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader3_1);
				drakeleader3.setRunning();
				startQuestTimer("move_21", 9000, drakeleader3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_21")) && (drakeleader3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_21", 9000, drakeleader3, null);
			}
			else
			{
				if (!drakeleader3.isInsideRadius(drakeleader3_1.x, drakeleader3_1.y, drakeleader3_1.z, 100, true, false))
				{
					drakeleader3.teleToLocation(drakeleader3_1.x, drakeleader3_1.y, drakeleader3_1.z);
				}
				drakeleader3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader3_2);
				drakeleader3.setRunning();
				startQuestTimer("move_22", 9000, drakeleader3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_22")) && (drakeleader3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_22", 9000, drakeleader3, null);
			}
			else
			{
				drakeleader3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader3_3);
				drakeleader3.setRunning();
				startQuestTimer("move_23", 9000, drakeleader3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_23")) && (drakeleader3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_23", 9000, drakeleader3, null);
			}
			else
			{
				drakeleader3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader3_4);
				drakeleader3.setRunning();
				startQuestTimer("move_24", 9000, drakeleader3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_24")) && (drakeleader3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_24", 9000, drakeleader3, null);
			}
			else
			{
				drakeleader3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader3_5);
				drakeleader3.setRunning();
				startQuestTimer("move_25", 9000, drakeleader3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_25")) && (drakeleader3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_25", 9000, drakeleader3, null);
			}
			else
			{
				drakeleader3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader3_6);
				drakeleader3.setRunning();
				startQuestTimer("move_26", 9000, drakeleader3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_26")) && (drakeleader3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_26", 9000, drakeleader3, null);
			}
			else
			{
				drakeleader3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader3_7);
				drakeleader3.setRunning();
				startQuestTimer("move_27", 9000, drakeleader3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_27")) && (drakeleader3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_27", 9000, drakeleader3, null);
			}
			else
			{
				drakeleader3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader3_8);
				drakeleader3.setRunning();
				startQuestTimer("move_28", 9000, drakeleader3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_28")) && (drakeleader3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_28", 9000, drakeleader3, null);
			}
			else
			{
				drakeleader3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader3_9);
				drakeleader3.setRunning();
				startQuestTimer("move_29", 9000, drakeleader3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_29")) && (drakeleader3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_29", 9000, drakeleader3, null);
			}
			else
			{
				drakeleader3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader3_10);
				drakeleader3.setRunning();
				startQuestTimer("move_30", 9000, drakeleader3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_30")) && (drakeleader3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_30", 9000, drakeleader3, null);
			}
			else
			{
				drakeleader3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader3_11);
				drakeleader3.setRunning();
				startQuestTimer("move_new3", 4000, drakeleader3, null);
			}
		}
		else if ((event.equalsIgnoreCase("drakeleader4_spawn")) && (drakeleader4 == null))
		{
			drakeleader4 = addSpawn(DRAKE_LEADER, 148579, 111993, -3710, 0, false, 0);
			drakeleader4.setIsNoRndWalk(true);
			drakeleader4.setRunning();
			startQuestTimer("move_31", 9000, drakeleader4, null);
		}
		else if ((event.equalsIgnoreCase("move_new4")) && (drakeleader4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_new4", 9000, drakeleader4, null);
			}
			else
			{
				drakeleader4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader4_1);
				drakeleader4.setRunning();
				startQuestTimer("move_31", 9000, drakeleader4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_31")) && (drakeleader4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_31", 9000, drakeleader4, null);
			}
			else
			{
				if (!drakeleader4.isInsideRadius(drakeleader4_1.x, drakeleader4_1.y, drakeleader4_1.z, 100, true, false))
				{
					drakeleader4.teleToLocation(drakeleader4_1.x, drakeleader4_1.y, drakeleader4_1.z);
				}
				drakeleader4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader4_2);
				drakeleader4.setRunning();
				startQuestTimer("move_32", 9000, drakeleader4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_32")) && (drakeleader4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_32", 9000, drakeleader4, null);
			}
			else
			{
				drakeleader4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader4_3);
				drakeleader4.setRunning();
				startQuestTimer("move_33", 9000, drakeleader4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_33")) && (drakeleader4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_33", 9000, drakeleader4, null);
			}
			else
			{
				drakeleader4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader4_4);
				drakeleader4.setRunning();
				startQuestTimer("move_34", 9000, drakeleader4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_34")) && (drakeleader4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_34", 9000, drakeleader4, null);
			}
			else
			{
				drakeleader4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader4_5);
				drakeleader4.setRunning();
				startQuestTimer("move_35", 9000, drakeleader4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_35")) && (drakeleader4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_35", 9000, drakeleader4, null);
			}
			else
			{
				drakeleader4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader4_6);
				drakeleader4.setRunning();
				startQuestTimer("move_36", 9000, drakeleader4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_36")) && (drakeleader4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_36", 9000, drakeleader4, null);
			}
			else
			{
				drakeleader4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader4_7);
				drakeleader4.setRunning();
				startQuestTimer("move_37", 9000, drakeleader4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_37")) && (drakeleader4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_37", 9000, drakeleader4, null);
			}
			else
			{
				drakeleader4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader4_8);
				drakeleader4.setRunning();
				startQuestTimer("move_38", 9000, drakeleader4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_38")) && (drakeleader4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_38", 9000, drakeleader4, null);
			}
			else
			{
				drakeleader4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader4_9);
				drakeleader4.setRunning();
				startQuestTimer("move_39", 9000, drakeleader4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_39")) && (drakeleader4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_39", 9000, drakeleader4, null);
			}
			else
			{
				drakeleader4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader4_10);
				drakeleader4.setRunning();
				startQuestTimer("move_40", 9000, drakeleader4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_40")) && (drakeleader4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_40", 9000, drakeleader4, null);
			}
			else
			{
				drakeleader4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader4_11);
				drakeleader4.setRunning();
				startQuestTimer("move_new4", 4000, drakeleader4, null);
			}
		}
		else if ((event.equalsIgnoreCase("drakeleader5_spawn")) && (drakeleader5 == null))
		{
			drakeleader5 = addSpawn(DRAKE_LEADER, 147495, 116305, -3711, 0, false, 0);
			drakeleader5.setIsNoRndWalk(true);
			drakeleader5.setRunning();
			startQuestTimer("move_41", 9000, drakeleader5, null);
		}
		else if ((event.equalsIgnoreCase("move_new5")) && (drakeleader5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_new5", 9000, drakeleader5, null);
			}
			else
			{
				drakeleader5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader5_1);
				drakeleader5.setRunning();
				startQuestTimer("move_41", 9000, drakeleader5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_41")) && (drakeleader5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_41", 9000, drakeleader5, null);
			}
			else
			{
				if (!drakeleader5.isInsideRadius(drakeleader5_1.x, drakeleader5_1.y, drakeleader5_1.z, 100, true, false))
				{
					drakeleader5.teleToLocation(drakeleader5_1.x, drakeleader5_1.y, drakeleader5_1.z);
				}
				drakeleader5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader5_2);
				drakeleader5.setRunning();
				startQuestTimer("move_42", 9000, drakeleader5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_42")) && (drakeleader5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_42", 9000, drakeleader5, null);
			}
			else
			{
				drakeleader5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader5_3);
				drakeleader5.setRunning();
				startQuestTimer("move_43", 9000, drakeleader5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_43")) && (drakeleader5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_43", 9000, drakeleader5, null);
			}
			else
			{
				drakeleader5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader5_4);
				drakeleader5.setRunning();
				startQuestTimer("move_44", 9000, drakeleader5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_44")) && (drakeleader5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_44", 9000, drakeleader5, null);
			}
			else
			{
				drakeleader5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader5_5);
				drakeleader5.setRunning();
				startQuestTimer("move_45", 9000, drakeleader5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_45")) && (drakeleader5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_45", 9000, drakeleader5, null);
			}
			else
			{
				drakeleader5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader5_6);
				drakeleader5.setRunning();
				startQuestTimer("move_46", 9000, drakeleader5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_46")) && (drakeleader5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_46", 9000, drakeleader5, null);
			}
			else
			{
				drakeleader5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader5_7);
				drakeleader5.setRunning();
				startQuestTimer("move_47", 9000, drakeleader5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_47")) && (drakeleader5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_47", 9000, drakeleader5, null);
			}
			else
			{
				drakeleader5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader5_8);
				drakeleader5.setRunning();
				startQuestTimer("move_48", 9000, drakeleader5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_48")) && (drakeleader5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_48", 9000, drakeleader5, null);
			}
			else
			{
				drakeleader5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader5_9);
				drakeleader5.setRunning();
				startQuestTimer("move_49", 9000, drakeleader5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_49")) && (drakeleader5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_49", 9000, drakeleader5, null);
			}
			else
			{
				drakeleader5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader5_10);
				drakeleader5.setRunning();
				startQuestTimer("move_50", 9000, drakeleader5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_50")) && (drakeleader5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_50", 9000, drakeleader5, null);
			}
			else
			{
				drakeleader5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader5_11);
				drakeleader5.setRunning();
				startQuestTimer("move_new5", 4000, drakeleader5, null);
			}
		}
		else if ((event.equalsIgnoreCase("drakeleader6_spawn")) && (drakeleader6 == null))
		{
			drakeleader6 = addSpawn(DRAKE_LEADER, 145868, 112717, -3725, 0, false, 0);
			drakeleader6.setIsNoRndWalk(true);
			drakeleader6.setRunning();
			startQuestTimer("move_51", 9000, drakeleader6, null);
		}
		else if ((event.equalsIgnoreCase("move_new6")) && (drakeleader6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_new6", 9000, drakeleader6, null);
			}
			else
			{
				drakeleader6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader6_1);
				drakeleader6.setRunning();
				startQuestTimer("move_51", 9000, drakeleader6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_51")) && (drakeleader6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_51", 9000, drakeleader6, null);
			}
			else
			{
				if (!drakeleader6.isInsideRadius(drakeleader6_1.x, drakeleader6_1.y, drakeleader6_1.z, 100, true, false))
				{
					drakeleader6.teleToLocation(drakeleader6_1.x, drakeleader6_1.y, drakeleader6_1.z);
				}
				drakeleader6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader6_2);
				drakeleader6.setRunning();
				startQuestTimer("move_52", 9000, drakeleader6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_52")) && (drakeleader6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_52", 9000, drakeleader6, null);
			}
			else
			{
				drakeleader6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader6_3);
				drakeleader6.setRunning();
				startQuestTimer("move_53", 9000, drakeleader6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_53")) && (drakeleader6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_53", 9000, drakeleader6, null);
			}
			else
			{
				drakeleader6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader6_4);
				drakeleader6.setRunning();
				startQuestTimer("move_54", 9000, drakeleader6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_54")) && (drakeleader6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_54", 9000, drakeleader6, null);
			}
			else
			{
				drakeleader6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader6_5);
				drakeleader6.setRunning();
				startQuestTimer("move_55", 9000, drakeleader6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_55")) && (drakeleader6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_55", 9000, drakeleader6, null);
			}
			else
			{
				drakeleader6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader6_6);
				drakeleader6.setRunning();
				startQuestTimer("move_56", 9000, drakeleader6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_56")) && (drakeleader6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_56", 9000, drakeleader6, null);
			}
			else
			{
				drakeleader6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader6_7);
				drakeleader6.setRunning();
				startQuestTimer("move_57", 9000, drakeleader6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_57")) && (drakeleader6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_57", 9000, drakeleader6, null);
			}
			else
			{
				drakeleader6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader6_8);
				drakeleader6.setRunning();
				startQuestTimer("move_58", 9000, drakeleader6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_58")) && (drakeleader6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_58", 9000, drakeleader6, null);
			}
			else
			{
				drakeleader6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader6_9);
				drakeleader6.setRunning();
				startQuestTimer("move_59", 9000, drakeleader6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_59")) && (drakeleader6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_59", 9000, drakeleader6, null);
			}
			else
			{
				drakeleader6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader6_10);
				drakeleader6.setRunning();
				startQuestTimer("move_60", 9000, drakeleader6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_60")) && (drakeleader6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_60", 9000, drakeleader6, null);
			}
			else
			{
				drakeleader6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakeleader6_11);
				drakeleader6.setRunning();
				startQuestTimer("move_new6", 4000, drakeleader6, null);
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
		if (npc.getNpcId() == DRAKE_LEADER)
		{
			if (npc == drakeleader1)
			{
				startQuestTimer("drakeleader1_spawn", 300000, null, null);
				drakeleader1 = null;
			}
			
			if (npc == drakeleader2)
			{
				startQuestTimer("drakeleader2_spawn", 300000, null, null);
				drakeleader2 = null;
			}
			
			if (npc == drakeleader3)
			{
				startQuestTimer("drakeleader3_spawn", 300000, null, null);
				drakeleader3 = null;
			}
			
			if (npc == drakeleader4)
			{
				startQuestTimer("drakeleader4_spawn", 300000, null, null);
				drakeleader4 = null;
			}
			
			if (npc == drakeleader5)
			{
				startQuestTimer("drakeleader5_spawn", 300000, null, null);
				drakeleader5 = null;
			}
			
			if (npc == drakeleader6)
			{
				startQuestTimer("drakeleader6_spawn", 300000, null, null);
				drakeleader6 = null;
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
		new _1_DrakeLeader(-1, "DrakeLeader", "data");
	}
}