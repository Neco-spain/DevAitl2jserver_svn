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

public class _1_DrakeScout extends AbstractNpcAI
{
	private static final int DRAKE_SCOUT = 22850;
	private L2Npc drakescout1;
	private L2Npc drakescout2;
	private L2Npc drakescout3;
	private L2Npc drakescout4;
	private L2Npc drakescout5;
	private L2Npc drakescout6;
	private static final L2CharPosition drakescout1_1 = new L2CharPosition(144937, 114885, -3725, 0);
	private static final L2CharPosition drakescout1_2 = new L2CharPosition(145071, 113495, -3725, 0);
	private static final L2CharPosition drakescout1_3 = new L2CharPosition(145815, 112778, -3725, 0);
	private static final L2CharPosition drakescout1_4 = new L2CharPosition(146710, 112392, -3725, 0);
	private static final L2CharPosition drakescout1_5 = new L2CharPosition(147766, 112466, -3725, 0);
	private static final L2CharPosition drakescout1_6 = new L2CharPosition(149019, 113299, -3725, 0);
	private static final L2CharPosition drakescout1_7 = new L2CharPosition(149321, 114526, -3725, 0);
	private static final L2CharPosition drakescout1_8 = new L2CharPosition(148788, 115785, -3725, 0);
	private static final L2CharPosition drakescout1_9 = new L2CharPosition(147606, 116500, -3704, 0);
	private static final L2CharPosition drakescout1_10 = new L2CharPosition(146563, 116116, -3725, 0);
	private static final L2CharPosition drakescout1_11 = new L2CharPosition(145817, 115812, -3697, 0);
	private static final L2CharPosition drakescout1_12 = new L2CharPosition(145457, 115249, -3725, 0);
	
	private static final L2CharPosition drakescout2_1 = new L2CharPosition(147766, 112466, -3725, 0);
	private static final L2CharPosition drakescout2_2 = new L2CharPosition(149019, 113299, -3725, 0);
	private static final L2CharPosition drakescout2_3 = new L2CharPosition(149321, 114526, -3725, 0);
	private static final L2CharPosition drakescout2_4 = new L2CharPosition(148788, 115785, -3725, 0);
	private static final L2CharPosition drakescout2_5 = new L2CharPosition(147606, 116500, -3704, 0);
	private static final L2CharPosition drakescout2_6 = new L2CharPosition(146563, 116116, -3725, 0);
	private static final L2CharPosition drakescout2_7 = new L2CharPosition(145817, 115812, -3697, 0);
	private static final L2CharPosition drakescout2_8 = new L2CharPosition(145457, 115249, -3725, 0);
	private static final L2CharPosition drakescout2_9 = new L2CharPosition(144937, 114885, -3725, 0);
	private static final L2CharPosition drakescout2_10 = new L2CharPosition(145071, 113495, -3725, 0);
	private static final L2CharPosition drakescout2_11 = new L2CharPosition(145815, 112778, -3725, 0);
	private static final L2CharPosition drakescout2_12 = new L2CharPosition(146710, 112392, -3725, 0);
	
	private static final L2CharPosition drakescout3_1 = new L2CharPosition(147606, 116500, -3704, 0);
	private static final L2CharPosition drakescout3_2 = new L2CharPosition(146563, 116116, -3725, 0);
	private static final L2CharPosition drakescout3_3 = new L2CharPosition(145817, 115812, -3697, 0);
	private static final L2CharPosition drakescout3_4 = new L2CharPosition(145457, 115249, -3725, 0);
	private static final L2CharPosition drakescout3_5 = new L2CharPosition(144937, 114885, -3725, 0);
	private static final L2CharPosition drakescout3_6 = new L2CharPosition(145071, 113495, -3725, 0);
	private static final L2CharPosition drakescout3_7 = new L2CharPosition(145815, 112778, -3725, 0);
	private static final L2CharPosition drakescout3_8 = new L2CharPosition(146710, 112392, -3725, 0);
	private static final L2CharPosition drakescout3_9 = new L2CharPosition(147766, 112466, -3725, 0);
	private static final L2CharPosition drakescout3_10 = new L2CharPosition(149019, 113299, -3725, 0);
	private static final L2CharPosition drakescout3_11 = new L2CharPosition(149321, 114526, -3725, 0);
	private static final L2CharPosition drakescout3_12 = new L2CharPosition(148788, 115785, -3725, 0);
	
	private static final L2CharPosition drakescout4_1 = new L2CharPosition(144767, 114327, -3719, 0);
	private static final L2CharPosition drakescout4_2 = new L2CharPosition(145257, 113427, -3725, 0);
	private static final L2CharPosition drakescout4_3 = new L2CharPosition(145945, 112702, -3725, 0);
	private static final L2CharPosition drakescout4_4 = new L2CharPosition(147311, 112476, -3725, 0);
	private static final L2CharPosition drakescout4_5 = new L2CharPosition(148287, 112057, -3725, 0);
	private static final L2CharPosition drakescout4_6 = new L2CharPosition(148913, 113315, -3725, 0);
	private static final L2CharPosition drakescout4_7 = new L2CharPosition(149102, 114690, -3725, 0);
	private static final L2CharPosition drakescout4_8 = new L2CharPosition(148838, 115555, -3725, 0);
	private static final L2CharPosition drakescout4_9 = new L2CharPosition(147709, 116165, -3725, 0);
	private static final L2CharPosition drakescout4_10 = new L2CharPosition(146585, 116263, -3725, 0);
	private static final L2CharPosition drakescout4_11 = new L2CharPosition(145644, 115434, -3725, 0);
	private static final L2CharPosition drakescout4_12 = new L2CharPosition(144910, 115093, -3725, 0);
	
	private static final L2CharPosition drakescout5_1 = new L2CharPosition(148287, 112057, -3725, 0);
	private static final L2CharPosition drakescout5_2 = new L2CharPosition(148913, 113315, -3725, 0);
	private static final L2CharPosition drakescout5_3 = new L2CharPosition(149102, 114690, -3725, 0);
	private static final L2CharPosition drakescout5_4 = new L2CharPosition(148838, 115555, -3725, 0);
	private static final L2CharPosition drakescout5_5 = new L2CharPosition(147709, 116165, -3725, 0);
	private static final L2CharPosition drakescout5_6 = new L2CharPosition(146585, 116263, -3725, 0);
	private static final L2CharPosition drakescout5_7 = new L2CharPosition(145644, 115434, -3725, 0);
	private static final L2CharPosition drakescout5_8 = new L2CharPosition(144910, 115093, -3725, 0);
	private static final L2CharPosition drakescout5_9 = new L2CharPosition(144767, 114327, -3719, 0);
	private static final L2CharPosition drakescout5_10 = new L2CharPosition(145257, 113427, -3725, 0);
	private static final L2CharPosition drakescout5_11 = new L2CharPosition(145945, 112702, -3725, 0);
	private static final L2CharPosition drakescout5_12 = new L2CharPosition(147311, 112476, -3725, 0);
	
	private static final L2CharPosition drakescout6_1 = new L2CharPosition(147709, 116165, -3725, 0);
	private static final L2CharPosition drakescout6_2 = new L2CharPosition(146585, 116263, -3725, 0);
	private static final L2CharPosition drakescout6_3 = new L2CharPosition(145644, 115434, -3725, 0);
	private static final L2CharPosition drakescout6_4 = new L2CharPosition(144910, 115093, -3725, 0);
	private static final L2CharPosition drakescout6_5 = new L2CharPosition(144767, 114327, -3719, 0);
	private static final L2CharPosition drakescout6_6 = new L2CharPosition(145257, 113427, -3725, 0);
	private static final L2CharPosition drakescout6_7 = new L2CharPosition(145945, 112702, -3725, 0);
	private static final L2CharPosition drakescout6_8 = new L2CharPosition(147311, 112476, -3725, 0);
	private static final L2CharPosition drakescout6_9 = new L2CharPosition(148287, 112057, -3725, 0);
	private static final L2CharPosition drakescout6_10 = new L2CharPosition(148913, 113315, -3725, 0);
	private static final L2CharPosition drakescout6_11 = new L2CharPosition(149102, 114690, -3725, 0);
	private static final L2CharPosition drakescout6_12 = new L2CharPosition(148838, 115555, -3725, 0);
	
	public _1_DrakeScout(int questId, String name, String descr)
	{
		super(name, descr);
		
		addKillId(DRAKE_SCOUT);
		addAggroRangeEnterId(DRAKE_SCOUT);
		
		startQuestTimer("drakescout1_spawn", 1000, null, null);
		startQuestTimer("drakescout2_spawn", 2000, null, null);
		startQuestTimer("drakescout3_spawn", 3000, null, null);
		startQuestTimer("drakescout4_spawn", 14000, null, null);
		startQuestTimer("drakescout5_spawn", 15000, null, null);
		startQuestTimer("drakescout6_spawn", 16000, null, null);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if ((event.equalsIgnoreCase("drakescout1_spawn")) && (drakescout1 == null))
		{
			drakescout1 = addSpawn(DRAKE_SCOUT, 144937, 114885, -3725, 0, false, 0);
			drakescout1.setIsNoRndWalk(true);
			drakescout1.setRunning();
			startQuestTimer("move_1", 7000, drakescout1, null);
		}
		else if ((event.equalsIgnoreCase("move_new")) && (drakescout1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_new", 7000, drakescout1, null);
			}
			else
			{
				drakescout1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout1_1);
				drakescout1.setRunning();
				startQuestTimer("move_1", 7000, drakescout1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_1")) && (drakescout1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_1", 7000, drakescout1, null);
			}
			else
			{
				if (!drakescout1.isInsideRadius(drakescout1_1.x, drakescout1_1.y, drakescout1_1.z, 100, true, false))
				{
					drakescout1.teleToLocation(drakescout1_1.x, drakescout1_1.y, drakescout1_1.z);
				}
				drakescout1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout1_2);
				drakescout1.setRunning();
				startQuestTimer("move_2", 7000, drakescout1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_2")) && (drakescout1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_2", 7000, drakescout1, null);
			}
			else
			{
				drakescout1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout1_3);
				drakescout1.setRunning();
				startQuestTimer("move_3", 7000, drakescout1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_3")) && (drakescout1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_3", 7000, drakescout1, null);
			}
			else
			{
				drakescout1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout1_4);
				drakescout1.setRunning();
				startQuestTimer("move_4", 7000, drakescout1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_4")) && (drakescout1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_4", 7000, drakescout1, null);
			}
			else
			{
				drakescout1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout1_5);
				drakescout1.setRunning();
				startQuestTimer("move_5", 7000, drakescout1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_5")) && (drakescout1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_5", 7000, drakescout1, null);
			}
			else
			{
				drakescout1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout1_6);
				drakescout1.setRunning();
				startQuestTimer("move_6", 7000, drakescout1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_6")) && (drakescout1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_6", 7000, drakescout1, null);
			}
			else
			{
				drakescout1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout1_7);
				drakescout1.setRunning();
				startQuestTimer("move_7", 7000, drakescout1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_7")) && (drakescout1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_7", 7000, drakescout1, null);
			}
			else
			{
				drakescout1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout1_8);
				drakescout1.setRunning();
				startQuestTimer("move_8", 7000, drakescout1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_8")) && (drakescout1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_8", 7000, drakescout1, null);
			}
			else
			{
				drakescout1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout1_9);
				drakescout1.setRunning();
				startQuestTimer("move_9", 7000, drakescout1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_9")) && (drakescout1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_9", 7000, drakescout1, null);
			}
			else
			{
				drakescout1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout1_10);
				drakescout1.setRunning();
				startQuestTimer("move_10", 7000, drakescout1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_10")) && (drakescout1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_10", 7000, drakescout1, null);
			}
			else
			{
				drakescout1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout1_11);
				drakescout1.setRunning();
				startQuestTimer("move_11", 4000, drakescout1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_11")) && (drakescout1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_11", 7000, drakescout1, null);
			}
			else
			{
				drakescout1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout1_12);
				drakescout1.setRunning();
				startQuestTimer("move_new", 7000, drakescout1, null);
			}
		}
		else if ((event.equalsIgnoreCase("drakescout2_spawn")) && (drakescout2 == null))
		{
			drakescout2 = addSpawn(DRAKE_SCOUT, 147766, 112466, -3725, 0, false, 0);
			drakescout2.setIsNoRndWalk(true);
			drakescout2.setRunning();
			startQuestTimer("move_12", 7000, drakescout2, null);
		}
		else if ((event.equalsIgnoreCase("move_new2")) && (drakescout2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_new2", 7000, drakescout2, null);
			}
			else
			{
				drakescout2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout2_1);
				drakescout2.setRunning();
				startQuestTimer("move_12", 7000, drakescout2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_12")) && (drakescout2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_12", 7000, drakescout2, null);
			}
			else
			{
				if (!drakescout2.isInsideRadius(drakescout2_1.x, drakescout2_1.y, drakescout2_1.z, 100, true, false))
				{
					drakescout2.teleToLocation(drakescout2_1.x, drakescout2_1.y, drakescout2_1.z);
				}
				drakescout2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout2_2);
				drakescout2.setRunning();
				startQuestTimer("move_13", 7000, drakescout2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_13")) && (drakescout2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_13", 7000, drakescout2, null);
			}
			else
			{
				drakescout2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout2_3);
				drakescout2.setRunning();
				startQuestTimer("move_14", 7000, drakescout2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_14")) && (drakescout2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_14", 7000, drakescout2, null);
			}
			else
			{
				drakescout2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout2_4);
				drakescout2.setRunning();
				startQuestTimer("move_15", 7000, drakescout2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_15")) && (drakescout2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_15", 7000, drakescout2, null);
			}
			else
			{
				drakescout2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout2_5);
				drakescout2.setRunning();
				startQuestTimer("move_16", 7000, drakescout2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_16")) && (drakescout2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_16", 7000, drakescout2, null);
			}
			else
			{
				drakescout2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout2_6);
				drakescout2.setRunning();
				startQuestTimer("move_17", 7000, drakescout2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_17")) && (drakescout2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_17", 7000, drakescout2, null);
			}
			else
			{
				drakescout2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout2_7);
				drakescout2.setRunning();
				startQuestTimer("move_18", 7000, drakescout2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_18")) && (drakescout2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_18", 7000, drakescout2, null);
			}
			else
			{
				drakescout2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout2_8);
				drakescout2.setRunning();
				startQuestTimer("move_19", 7000, drakescout2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_19")) && (drakescout2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_19", 7000, drakescout2, null);
			}
			else
			{
				drakescout2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout2_9);
				drakescout2.setRunning();
				startQuestTimer("move_20", 7000, drakescout2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_20")) && (drakescout2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_20", 7000, drakescout2, null);
			}
			else
			{
				drakescout2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout2_10);
				drakescout2.setRunning();
				startQuestTimer("move_21", 7000, drakescout2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_21")) && (drakescout2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_21", 7000, drakescout2, null);
			}
			else
			{
				drakescout2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout2_11);
				drakescout2.setRunning();
				startQuestTimer("move_22", 4000, drakescout2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_22")) && (drakescout2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_22", 7000, drakescout2, null);
			}
			else
			{
				drakescout2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout2_12);
				drakescout2.setRunning();
				startQuestTimer("move_new2", 7000, drakescout2, null);
			}
		}
		else if ((event.equalsIgnoreCase("drakescout3_spawn")) && (drakescout3 == null))
		{
			drakescout3 = addSpawn(DRAKE_SCOUT, 147606, 116500, -3704, 0, false, 0);
			drakescout3.setIsNoRndWalk(true);
			drakescout3.setRunning();
			startQuestTimer("move_23", 7000, drakescout3, null);
		}
		else if ((event.equalsIgnoreCase("move_new3")) && (drakescout3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_new3", 7000, drakescout3, null);
			}
			else
			{
				drakescout3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout3_1);
				drakescout3.setRunning();
				startQuestTimer("move_23", 7000, drakescout3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_23")) && (drakescout3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_23", 7000, drakescout3, null);
			}
			else
			{
				if (!drakescout3.isInsideRadius(drakescout3_1.x, drakescout3_1.y, drakescout3_1.z, 100, true, false))
				{
					drakescout3.teleToLocation(drakescout3_1.x, drakescout3_1.y, drakescout3_1.z);
				}
				drakescout3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout3_2);
				drakescout3.setRunning();
				startQuestTimer("move_24", 7000, drakescout3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_24")) && (drakescout3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_24", 7000, drakescout3, null);
			}
			else
			{
				drakescout3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout3_3);
				drakescout3.setRunning();
				startQuestTimer("move_25", 7000, drakescout3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_25")) && (drakescout3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_25", 7000, drakescout3, null);
			}
			else
			{
				drakescout3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout3_4);
				drakescout3.setRunning();
				startQuestTimer("move_26", 7000, drakescout3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_26")) && (drakescout3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_26", 7000, drakescout3, null);
			}
			else
			{
				drakescout3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout3_5);
				drakescout3.setRunning();
				startQuestTimer("move_27", 7000, drakescout3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_27")) && (drakescout3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_27", 7000, drakescout3, null);
			}
			else
			{
				drakescout3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout3_6);
				drakescout3.setRunning();
				startQuestTimer("move_28", 7000, drakescout3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_28")) && (drakescout3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_28", 7000, drakescout3, null);
			}
			else
			{
				drakescout3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout3_7);
				drakescout3.setRunning();
				startQuestTimer("move_29", 7000, drakescout3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_29")) && (drakescout3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_29", 7000, drakescout3, null);
			}
			else
			{
				drakescout3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout3_8);
				drakescout3.setRunning();
				startQuestTimer("move_30", 7000, drakescout3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_30")) && (drakescout3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_30", 7000, drakescout3, null);
			}
			else
			{
				drakescout3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout3_9);
				drakescout3.setRunning();
				startQuestTimer("move_31", 7000, drakescout3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_31")) && (drakescout3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_31", 7000, drakescout3, null);
			}
			else
			{
				drakescout3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout3_10);
				drakescout3.setRunning();
				startQuestTimer("move_32", 7000, drakescout3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_32")) && (drakescout3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_32", 7000, drakescout3, null);
			}
			else
			{
				drakescout3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout3_11);
				drakescout3.setRunning();
				startQuestTimer("move_33", 4000, drakescout3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_33")) && (drakescout3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_33", 7000, drakescout3, null);
			}
			else
			{
				drakescout3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout3_12);
				drakescout3.setRunning();
				startQuestTimer("move_new3", 7000, drakescout3, null);
			}
		}
		else if ((event.equalsIgnoreCase("drakescout4_spawn")) && (drakescout4 == null))
		{
			drakescout4 = addSpawn(DRAKE_SCOUT, 144767, 114327, -3719, 0, false, 0);
			drakescout4.setIsNoRndWalk(true);
			drakescout4.setRunning();
			startQuestTimer("move_34", 7000, drakescout4, null);
		}
		else if ((event.equalsIgnoreCase("move_new4")) && (drakescout4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_new4", 7000, drakescout4, null);
			}
			else
			{
				drakescout4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout4_1);
				drakescout4.setRunning();
				startQuestTimer("move_34", 7000, drakescout4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_34")) && (drakescout4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_34", 7000, drakescout4, null);
			}
			else
			{
				if (!drakescout4.isInsideRadius(drakescout4_1.x, drakescout4_1.y, drakescout4_1.z, 100, true, false))
				{
					drakescout4.teleToLocation(drakescout4_1.x, drakescout4_1.y, drakescout4_1.z);
				}
				drakescout4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout4_2);
				drakescout4.setRunning();
				startQuestTimer("move_35", 7000, drakescout4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_35")) && (drakescout4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_35", 7000, drakescout4, null);
			}
			else
			{
				drakescout4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout4_3);
				drakescout4.setRunning();
				startQuestTimer("move_36", 7000, drakescout4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_36")) && (drakescout4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_36", 7000, drakescout4, null);
			}
			else
			{
				drakescout4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout4_4);
				drakescout4.setRunning();
				startQuestTimer("move_37", 7000, drakescout4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_37")) && (drakescout4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_37", 7000, drakescout4, null);
			}
			else
			{
				drakescout4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout4_5);
				drakescout4.setRunning();
				startQuestTimer("move_38", 7000, drakescout4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_38")) && (drakescout4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_38", 7000, drakescout4, null);
			}
			else
			{
				drakescout4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout4_6);
				drakescout4.setRunning();
				startQuestTimer("move_39", 7000, drakescout4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_39")) && (drakescout4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_39", 7000, drakescout4, null);
			}
			else
			{
				drakescout4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout4_7);
				drakescout4.setRunning();
				startQuestTimer("move_40", 7000, drakescout4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_40")) && (drakescout4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_40", 7000, drakescout4, null);
			}
			else
			{
				drakescout4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout4_8);
				drakescout4.setRunning();
				startQuestTimer("move_41", 7000, drakescout4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_41")) && (drakescout4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_41", 7000, drakescout4, null);
			}
			else
			{
				drakescout4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout4_9);
				drakescout4.setRunning();
				startQuestTimer("move_42", 7000, drakescout4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_42")) && (drakescout4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_42", 7000, drakescout4, null);
			}
			else
			{
				drakescout4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout4_10);
				drakescout4.setRunning();
				startQuestTimer("move_43", 7000, drakescout4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_43")) && (drakescout4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_43", 7000, drakescout4, null);
			}
			else
			{
				drakescout4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout4_11);
				drakescout4.setRunning();
				startQuestTimer("move_44", 4000, drakescout4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_44")) && (drakescout4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_44", 7000, drakescout4, null);
			}
			else
			{
				drakescout4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout4_12);
				drakescout4.setRunning();
				startQuestTimer("move_new4", 7000, drakescout4, null);
			}
		}
		else if ((event.equalsIgnoreCase("drakescout5_spawn")) && (drakescout5 == null))
		{
			drakescout5 = addSpawn(DRAKE_SCOUT, 147766, 112466, -3725, 0, false, 0);
			drakescout5.setIsNoRndWalk(true);
			drakescout5.setRunning();
			startQuestTimer("move_45", 7000, drakescout5, null);
		}
		else if ((event.equalsIgnoreCase("move_new5")) && (drakescout5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_new5", 7000, drakescout5, null);
			}
			else
			{
				drakescout5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout5_1);
				drakescout5.setRunning();
				startQuestTimer("move_45", 7000, drakescout5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_45")) && (drakescout5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_45", 7000, drakescout5, null);
			}
			else
			{
				if (!drakescout5.isInsideRadius(drakescout5_1.x, drakescout5_1.y, drakescout5_1.z, 100, true, false))
				{
					drakescout5.teleToLocation(drakescout5_1.x, drakescout5_1.y, drakescout5_1.z);
				}
				drakescout5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout5_2);
				drakescout5.setRunning();
				startQuestTimer("move_46", 7000, drakescout5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_46")) && (drakescout5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_46", 7000, drakescout5, null);
			}
			else
			{
				drakescout5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout5_3);
				drakescout5.setRunning();
				startQuestTimer("move_47", 7000, drakescout5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_47")) && (drakescout5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_47", 7000, drakescout5, null);
			}
			else
			{
				drakescout5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout5_4);
				drakescout5.setRunning();
				startQuestTimer("move_48", 7000, drakescout5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_48")) && (drakescout5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_48", 7000, drakescout5, null);
			}
			else
			{
				drakescout5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout5_5);
				drakescout5.setRunning();
				startQuestTimer("move_49", 7000, drakescout5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_49")) && (drakescout5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_49", 7000, drakescout5, null);
			}
			else
			{
				drakescout5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout5_6);
				drakescout5.setRunning();
				startQuestTimer("move_50", 7000, drakescout5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_50")) && (drakescout5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_50", 7000, drakescout5, null);
			}
			else
			{
				drakescout5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout5_7);
				drakescout5.setRunning();
				startQuestTimer("move_51", 7000, drakescout5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_51")) && (drakescout5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_51", 7000, drakescout5, null);
			}
			else
			{
				drakescout5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout5_8);
				drakescout5.setRunning();
				startQuestTimer("move_52", 7000, drakescout5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_52")) && (drakescout5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_52", 7000, drakescout5, null);
			}
			else
			{
				drakescout5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout5_9);
				drakescout5.setRunning();
				startQuestTimer("move_53", 7000, drakescout5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_53")) && (drakescout5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_53", 7000, drakescout5, null);
			}
			else
			{
				drakescout5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout5_10);
				drakescout5.setRunning();
				startQuestTimer("move_54", 7000, drakescout5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_54")) && (drakescout5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_54", 7000, drakescout5, null);
			}
			else
			{
				drakescout5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout5_11);
				drakescout5.setRunning();
				startQuestTimer("move_55", 4000, drakescout5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_55")) && (drakescout5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_55", 7000, drakescout5, null);
			}
			else
			{
				drakescout5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout5_12);
				drakescout5.setRunning();
				startQuestTimer("move_new5", 7000, drakescout5, null);
			}
		}
		else if ((event.equalsIgnoreCase("drakescout6_spawn")) && (drakescout6 == null))
		{
			drakescout6 = addSpawn(DRAKE_SCOUT, 147606, 116500, -3704, 0, false, 0);
			drakescout6.setIsNoRndWalk(true);
			drakescout6.setRunning();
			startQuestTimer("move_56", 7000, drakescout6, null);
		}
		else if ((event.equalsIgnoreCase("move_new6")) && (drakescout6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_new6", 7000, drakescout6, null);
			}
			else
			{
				drakescout6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout6_1);
				drakescout6.setRunning();
				startQuestTimer("move_56", 7000, drakescout6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_56")) && (drakescout6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_56", 7000, drakescout6, null);
			}
			else
			{
				if (!drakescout6.isInsideRadius(drakescout6_1.x, drakescout6_1.y, drakescout6_1.z, 100, true, false))
				{
					drakescout6.teleToLocation(drakescout6_1.x, drakescout6_1.y, drakescout6_1.z);
				}
				drakescout6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout6_2);
				drakescout6.setRunning();
				startQuestTimer("move_57", 7000, drakescout6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_57")) && (drakescout6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_57", 7000, drakescout6, null);
			}
			else
			{
				drakescout6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout6_3);
				drakescout6.setRunning();
				startQuestTimer("move_58", 7000, drakescout6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_58")) && (drakescout6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_58", 7000, drakescout6, null);
			}
			else
			{
				drakescout6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout6_4);
				drakescout6.setRunning();
				startQuestTimer("move_59", 7000, drakescout6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_59")) && (drakescout6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_59", 7000, drakescout6, null);
			}
			else
			{
				drakescout6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout6_5);
				drakescout6.setRunning();
				startQuestTimer("move_60", 7000, drakescout6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_60")) && (drakescout6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_60", 7000, drakescout6, null);
			}
			else
			{
				drakescout6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout6_6);
				drakescout6.setRunning();
				startQuestTimer("move_61", 7000, drakescout6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_61")) && (drakescout6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_61", 7000, drakescout6, null);
			}
			else
			{
				drakescout6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout6_7);
				drakescout6.setRunning();
				startQuestTimer("move_62", 7000, drakescout6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_62")) && (drakescout6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_62", 7000, drakescout6, null);
			}
			else
			{
				drakescout6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout6_8);
				drakescout6.setRunning();
				startQuestTimer("move_63", 7000, drakescout6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_63")) && (drakescout6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_63", 7000, drakescout6, null);
			}
			else
			{
				drakescout6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout6_9);
				drakescout6.setRunning();
				startQuestTimer("move_64", 7000, drakescout6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_64")) && (drakescout6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_64", 7000, drakescout6, null);
			}
			else
			{
				drakescout6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout6_10);
				drakescout6.setRunning();
				startQuestTimer("move_65", 7000, drakescout6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_65")) && (drakescout6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_65", 7000, drakescout6, null);
			}
			else
			{
				drakescout6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout6_11);
				drakescout6.setRunning();
				startQuestTimer("move_66", 4000, drakescout6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_66")) && (drakescout6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_66", 7000, drakescout6, null);
			}
			else
			{
				drakescout6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakescout6_12);
				drakescout6.setRunning();
				startQuestTimer("move_new6", 7000, drakescout6, null);
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
		if (npc.getNpcId() == DRAKE_SCOUT)
		{
			if (npc == drakescout1)
			{
				startQuestTimer("drakescout1_spawn", 300000, null, null);
				drakescout1 = null;
			}
			
			if (npc == drakescout2)
			{
				startQuestTimer("drakescout2_spawn", 300000, null, null);
				drakescout2 = null;
			}
			
			if (npc == drakescout3)
			{
				startQuestTimer("drakescout3_spawn", 300000, null, null);
				drakescout3 = null;
			}
			
			if (npc == drakescout4)
			{
				startQuestTimer("drakescout4_spawn", 300000, null, null);
				drakescout4 = null;
			}
			
			if (npc == drakescout5)
			{
				startQuestTimer("drakescout5_spawn", 300000, null, null);
				drakescout5 = null;
			}
			
			if (npc == drakescout6)
			{
				startQuestTimer("drakescout6_spawn", 300000, null, null);
				drakescout6 = null;
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
		new _1_DrakeScout(-1, "DrakeScout", "data");
	}
}
