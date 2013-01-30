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

public class DrakeWarrior extends AbstractNpcAI
{
	private static final int DRAKE_WARRIOR = 22849;
	private L2Npc drakewarrior1;
	private L2Npc drakewarrior2;
	private L2Npc drakewarrior3;
	private L2Npc drakewarrior4;
	private L2Npc drakewarrior5;
	private L2Npc drakewarrior6;
	private static final L2CharPosition drakewarrior1_1 = new L2CharPosition(149292, 113881, -3725, 0);
	private static final L2CharPosition drakewarrior1_2 = new L2CharPosition(148952, 115264, -3725, 0);
	private static final L2CharPosition drakewarrior1_3 = new L2CharPosition(148087, 116152, -3725, 0);
	private static final L2CharPosition drakewarrior1_4 = new L2CharPosition(147339, 116027, -3725, 0);
	private static final L2CharPosition drakewarrior1_5 = new L2CharPosition(146439, 116247, -3725, 0);
	private static final L2CharPosition drakewarrior1_6 = new L2CharPosition(145501, 115897, -3725, 0);
	private static final L2CharPosition drakewarrior1_7 = new L2CharPosition(145121, 114863, -3725, 0);
	private static final L2CharPosition drakewarrior1_8 = new L2CharPosition(145177, 113352, -3725, 0);
	private static final L2CharPosition drakewarrior1_9 = new L2CharPosition(146074, 112690, -3725, 0);
	private static final L2CharPosition drakewarrior1_10 = new L2CharPosition(147155, 112905, -3725, 0);
	private static final L2CharPosition drakewarrior1_11 = new L2CharPosition(148134, 112679, -3725, 0);
	private static final L2CharPosition drakewarrior1_12 = new L2CharPosition(148905, 112887, -3725, 0);
	
	private static final L2CharPosition drakewarrior2_1 = new L2CharPosition(146439, 116247, -3725, 0);
	private static final L2CharPosition drakewarrior2_2 = new L2CharPosition(145501, 115897, -3725, 0);
	private static final L2CharPosition drakewarrior2_3 = new L2CharPosition(145121, 114863, -3725, 0);
	private static final L2CharPosition drakewarrior2_4 = new L2CharPosition(145177, 113352, -3725, 0);
	private static final L2CharPosition drakewarrior2_5 = new L2CharPosition(146074, 112690, -3725, 0);
	private static final L2CharPosition drakewarrior2_6 = new L2CharPosition(147155, 112905, -3725, 0);
	private static final L2CharPosition drakewarrior2_7 = new L2CharPosition(148134, 112679, -3725, 0);
	private static final L2CharPosition drakewarrior2_8 = new L2CharPosition(148905, 112887, -3725, 0);
	private static final L2CharPosition drakewarrior2_9 = new L2CharPosition(149292, 113881, -3725, 0);
	private static final L2CharPosition drakewarrior2_10 = new L2CharPosition(148952, 115264, -3725, 0);
	private static final L2CharPosition drakewarrior2_11 = new L2CharPosition(148087, 116152, -3725, 0);
	private static final L2CharPosition drakewarrior2_12 = new L2CharPosition(147339, 116027, -3725, 0);
	
	private static final L2CharPosition drakewarrior3_1 = new L2CharPosition(146074, 112690, -3725, 0);
	private static final L2CharPosition drakewarrior3_2 = new L2CharPosition(147155, 112905, -3725, 0);
	private static final L2CharPosition drakewarrior3_3 = new L2CharPosition(148134, 112679, -3725, 0);
	private static final L2CharPosition drakewarrior3_4 = new L2CharPosition(148905, 112887, -3725, 0);
	private static final L2CharPosition drakewarrior3_5 = new L2CharPosition(149292, 113881, -3725, 0);
	private static final L2CharPosition drakewarrior3_6 = new L2CharPosition(148952, 115264, -3725, 0);
	private static final L2CharPosition drakewarrior3_7 = new L2CharPosition(148087, 116152, -3725, 0);
	private static final L2CharPosition drakewarrior3_8 = new L2CharPosition(147339, 116027, -3725, 0);
	private static final L2CharPosition drakewarrior3_9 = new L2CharPosition(146439, 116247, -3725, 0);
	private static final L2CharPosition drakewarrior3_10 = new L2CharPosition(145501, 115897, -3725, 0);
	private static final L2CharPosition drakewarrior3_11 = new L2CharPosition(145121, 114863, -3725, 0);
	private static final L2CharPosition drakewarrior3_12 = new L2CharPosition(145177, 113352, -3725, 0);
	
	private static final L2CharPosition drakewarrior4_1 = new L2CharPosition(144877, 114966, -3725, 0);
	private static final L2CharPosition drakewarrior4_2 = new L2CharPosition(145063, 113865, -3725, 0);
	private static final L2CharPosition drakewarrior4_3 = new L2CharPosition(145680, 112840, -3725, 0);
	private static final L2CharPosition drakewarrior4_4 = new L2CharPosition(146766, 112688, -3725, 0);
	private static final L2CharPosition drakewarrior4_5 = new L2CharPosition(148000, 112039, -3725, 0);
	private static final L2CharPosition drakewarrior4_6 = new L2CharPosition(148694, 112319, -3725, 0);
	private static final L2CharPosition drakewarrior4_7 = new L2CharPosition(148852, 113264, -3725, 0);
	private static final L2CharPosition drakewarrior4_8 = new L2CharPosition(149161, 114305, -3725, 0);
	private static final L2CharPosition drakewarrior4_9 = new L2CharPosition(148896, 115364, -3725, 0);
	private static final L2CharPosition drakewarrior4_10 = new L2CharPosition(148031, 115980, -3725, 0);
	private static final L2CharPosition drakewarrior4_11 = new L2CharPosition(146716, 116319, -3725, 0);
	private static final L2CharPosition drakewarrior4_12 = new L2CharPosition(146031, 115749, -3725, 0);
	
	private static final L2CharPosition drakewarrior5_1 = new L2CharPosition(148000, 112039, -3725, 0);
	private static final L2CharPosition drakewarrior5_2 = new L2CharPosition(148694, 112319, -3725, 0);
	private static final L2CharPosition drakewarrior5_3 = new L2CharPosition(148852, 113264, -3725, 0);
	private static final L2CharPosition drakewarrior5_4 = new L2CharPosition(149161, 114305, -3725, 0);
	private static final L2CharPosition drakewarrior5_5 = new L2CharPosition(148896, 115364, -3725, 0);
	private static final L2CharPosition drakewarrior5_6 = new L2CharPosition(148031, 115980, -3725, 0);
	private static final L2CharPosition drakewarrior5_7 = new L2CharPosition(146716, 116319, -3725, 0);
	private static final L2CharPosition drakewarrior5_8 = new L2CharPosition(146031, 115749, -3725, 0);
	private static final L2CharPosition drakewarrior5_9 = new L2CharPosition(144877, 114966, -3725, 0);
	private static final L2CharPosition drakewarrior5_10 = new L2CharPosition(145063, 113865, -3725, 0);
	private static final L2CharPosition drakewarrior5_11 = new L2CharPosition(145680, 112840, -3725, 0);
	private static final L2CharPosition drakewarrior5_12 = new L2CharPosition(146766, 112688, -3725, 0);
	
	private static final L2CharPosition drakewarrior6_1 = new L2CharPosition(148896, 115364, -3725, 0);
	private static final L2CharPosition drakewarrior6_2 = new L2CharPosition(148031, 115980, -3725, 0);
	private static final L2CharPosition drakewarrior6_3 = new L2CharPosition(146716, 116319, -3725, 0);
	private static final L2CharPosition drakewarrior6_4 = new L2CharPosition(146031, 115749, -3725, 0);
	private static final L2CharPosition drakewarrior6_5 = new L2CharPosition(144877, 114966, -3725, 0);
	private static final L2CharPosition drakewarrior6_6 = new L2CharPosition(145063, 113865, -3725, 0);
	private static final L2CharPosition drakewarrior6_7 = new L2CharPosition(145680, 112840, -3725, 0);
	private static final L2CharPosition drakewarrior6_8 = new L2CharPosition(146766, 112688, -3725, 0);
	private static final L2CharPosition drakewarrior6_9 = new L2CharPosition(148000, 112039, -3725, 0);
	private static final L2CharPosition drakewarrior6_10 = new L2CharPosition(148694, 112319, -3725, 0);
	private static final L2CharPosition drakewarrior6_11 = new L2CharPosition(148852, 113264, -3725, 0);
	private static final L2CharPosition drakewarrior6_12 = new L2CharPosition(149161, 114305, -3725, 0);
	
	public DrakeWarrior(int questId, String name, String descr)
	{
		super(name, descr);
		
		addKillId(DRAKE_WARRIOR);
		addAggroRangeEnterId(DRAKE_WARRIOR);
		
		startQuestTimer("drakewarrior1_spawn", 7000, null, null);
		startQuestTimer("drakewarrior2_spawn", 7000, null, null);
		startQuestTimer("drakewarrior3_spawn", 7000, null, null);
		startQuestTimer("drakewarrior4_spawn", 7000, null, null);
		startQuestTimer("drakewarrior5_spawn", 7000, null, null);
		startQuestTimer("drakewarrior6_spawn", 7000, null, null);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if ((event.equalsIgnoreCase("drakewarrior1_spawn")) && (drakewarrior1 == null))
		{
			drakewarrior1 = addSpawn(DRAKE_WARRIOR, 149292, 113881, -3725, 0, false, 0L);
			drakewarrior1.setIsNoRndWalk(true);
			drakewarrior1.setRunning();
			startQuestTimer("move_1", 7000, drakewarrior1, null);
		}
		else if ((event.equalsIgnoreCase("move_new")) && (drakewarrior1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_new", 7000, drakewarrior1, null);
			}
			else
			{
				drakewarrior1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior1_1);
				drakewarrior1.setRunning();
				startQuestTimer("move_1", 7000, drakewarrior1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_1")) && (drakewarrior1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_1", 7000, drakewarrior1, null);
			}
			else
			{
				if (!drakewarrior1.isInsideRadius(drakewarrior1_1.x, drakewarrior1_1.y, drakewarrior1_1.z, 100, true, false))
					drakewarrior1.teleToLocation(drakewarrior1_1.x, drakewarrior1_1.y, drakewarrior1_1.z);
				drakewarrior1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior1_2);
				drakewarrior1.setRunning();
				startQuestTimer("move_2", 7000, drakewarrior1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_2")) && (drakewarrior1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_2", 7000, drakewarrior1, null);
			}
			else
			{
				drakewarrior1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior1_3);
				drakewarrior1.setRunning();
				startQuestTimer("move_3", 7000, drakewarrior1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_3")) && (drakewarrior1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_3", 7000, drakewarrior1, null);
			}
			else
			{
				drakewarrior1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior1_4);
				drakewarrior1.setRunning();
				startQuestTimer("move_4", 7000, drakewarrior1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_4")) && (drakewarrior1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_4", 7000, drakewarrior1, null);
			}
			else
			{
				drakewarrior1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior1_5);
				drakewarrior1.setRunning();
				startQuestTimer("move_5", 7000, drakewarrior1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_5")) && (drakewarrior1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_5", 7000, drakewarrior1, null);
			}
			else
			{
				drakewarrior1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior1_6);
				drakewarrior1.setRunning();
				startQuestTimer("move_6", 7000, drakewarrior1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_6")) && (drakewarrior1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_6", 7000, drakewarrior1, null);
			}
			else
			{
				drakewarrior1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior1_7);
				drakewarrior1.setRunning();
				startQuestTimer("move_7", 7000, drakewarrior1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_7")) && (drakewarrior1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_7", 7000, drakewarrior1, null);
			}
			else
			{
				drakewarrior1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior1_8);
				drakewarrior1.setRunning();
				startQuestTimer("move_8", 7000, drakewarrior1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_8")) && (drakewarrior1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_8", 7000, drakewarrior1, null);
			}
			else
			{
				drakewarrior1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior1_9);
				drakewarrior1.setRunning();
				startQuestTimer("move_9", 7000, drakewarrior1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_9")) && (drakewarrior1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_9", 7000, drakewarrior1, null);
			}
			else
			{
				drakewarrior1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior1_10);
				drakewarrior1.setRunning();
				startQuestTimer("move_10", 7000, drakewarrior1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_10")) && (drakewarrior1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_10", 7000, drakewarrior1, null);
			}
			else
			{
				drakewarrior1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior1_11);
				drakewarrior1.setRunning();
				startQuestTimer("move_11", 7000, drakewarrior1, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_11")) && (drakewarrior1 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_11", 7000, drakewarrior1, null);
			}
			else
			{
				drakewarrior1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior1_12);
				drakewarrior1.setRunning();
				startQuestTimer("move_new", 7000, drakewarrior1, null);
			}
		}
		else if ((event.equalsIgnoreCase("drakewarrior2_spawn")) && (drakewarrior2 == null))
		{
			drakewarrior2 = addSpawn(DRAKE_WARRIOR, 146439, 116247, -3725, 0, false, 0L);
			drakewarrior2.setIsNoRndWalk(true);
			drakewarrior2.setRunning();
			startQuestTimer("move_12", 7000, drakewarrior2, null);
		}
		else if ((event.equalsIgnoreCase("move_new2")) && (drakewarrior2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_new2", 7000, drakewarrior2, null);
			}
			else
			{
				drakewarrior2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior2_1);
				drakewarrior2.setRunning();
				startQuestTimer("move_12", 7000, drakewarrior2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_12")) && (drakewarrior2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_12", 7000, drakewarrior2, null);
			}
			else
			{
				if (!drakewarrior2.isInsideRadius(drakewarrior2_1.x, drakewarrior2_1.y, drakewarrior2_1.z, 100, true, false))
					drakewarrior2.teleToLocation(drakewarrior2_1.x, drakewarrior2_1.y, drakewarrior2_1.z);
				drakewarrior2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior2_2);
				drakewarrior2.setRunning();
				startQuestTimer("move_13", 7000, drakewarrior2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_13")) && (drakewarrior2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_13", 7000, drakewarrior2, null);
			}
			else
			{
				drakewarrior2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior2_3);
				drakewarrior2.setRunning();
				startQuestTimer("move_14", 7000, drakewarrior2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_14")) && (drakewarrior2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_14", 7000, drakewarrior2, null);
			}
			else
			{
				drakewarrior2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior2_4);
				drakewarrior2.setRunning();
				startQuestTimer("move_15", 7000, drakewarrior2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_15")) && (drakewarrior2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_15", 7000, drakewarrior2, null);
			}
			else
			{
				drakewarrior2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior2_5);
				drakewarrior2.setRunning();
				startQuestTimer("move_16", 7000, drakewarrior2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_16")) && (drakewarrior2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_16", 7000, drakewarrior2, null);
			}
			else
			{
				drakewarrior2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior2_6);
				drakewarrior2.setRunning();
				startQuestTimer("move_17", 7000, drakewarrior2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_17")) && (drakewarrior2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_17", 7000, drakewarrior2, null);
			}
			else
			{
				drakewarrior2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior2_7);
				drakewarrior2.setRunning();
				startQuestTimer("move_18", 7000, drakewarrior2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_18")) && (drakewarrior2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_18", 7000, drakewarrior2, null);
			}
			else
			{
				drakewarrior2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior2_8);
				drakewarrior2.setRunning();
				startQuestTimer("move_19", 7000, drakewarrior2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_19")) && (drakewarrior2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_19", 7000, drakewarrior2, null);
			}
			else
			{
				drakewarrior2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior2_9);
				drakewarrior2.setRunning();
				startQuestTimer("move_20", 7000, drakewarrior2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_20")) && (drakewarrior2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_20", 7000, drakewarrior2, null);
			}
			else
			{
				drakewarrior2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior2_10);
				drakewarrior2.setRunning();
				startQuestTimer("move_21", 7000, drakewarrior2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_21")) && (drakewarrior2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_21", 7000, drakewarrior2, null);
			}
			else
			{
				drakewarrior2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior2_11);
				drakewarrior2.setRunning();
				startQuestTimer("move_22", 7000, drakewarrior2, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_22")) && (drakewarrior2 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_22", 7000, drakewarrior2, null);
			}
			else
			{
				drakewarrior2.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior2_12);
				drakewarrior2.setRunning();
				startQuestTimer("move_new2", 7000, drakewarrior2, null);
			}
		}
		else if ((event.equalsIgnoreCase("drakewarrior3_spawn")) && (drakewarrior3 == null))
		{
			drakewarrior3 = addSpawn(DRAKE_WARRIOR, 146074, 112690, -3725, 0, false, 0L);
			drakewarrior3.setIsNoRndWalk(true);
			drakewarrior3.setRunning();
			startQuestTimer("move_23", 7000, drakewarrior3, null);
		}
		else if ((event.equalsIgnoreCase("move_new3")) && (drakewarrior3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_new3", 7000, drakewarrior3, null);
			}
			else
			{
				drakewarrior3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior3_1);
				drakewarrior3.setRunning();
				startQuestTimer("move_23", 7000, drakewarrior3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_23")) && (drakewarrior3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_23", 7000, drakewarrior3, null);
			}
			else
			{
				if (!drakewarrior3.isInsideRadius(drakewarrior3_1.x, drakewarrior3_1.y, drakewarrior3_1.z, 100, true, false))
					drakewarrior3.teleToLocation(drakewarrior3_1.x, drakewarrior3_1.y, drakewarrior3_1.z);
				drakewarrior3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior3_2);
				drakewarrior3.setRunning();
				startQuestTimer("move_24", 7000, drakewarrior3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_24")) && (drakewarrior3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_24", 7000, drakewarrior3, null);
			}
			else
			{
				drakewarrior3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior3_3);
				drakewarrior3.setRunning();
				startQuestTimer("move_25", 7000, drakewarrior3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_25")) && (drakewarrior3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_25", 7000, drakewarrior3, null);
			}
			else
			{
				drakewarrior3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior3_4);
				drakewarrior3.setRunning();
				startQuestTimer("move_26", 7000, drakewarrior3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_26")) && (drakewarrior3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_26", 7000, drakewarrior3, null);
			}
			else
			{
				drakewarrior3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior3_5);
				drakewarrior3.setRunning();
				startQuestTimer("move_27", 7000, drakewarrior3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_27")) && (drakewarrior3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_27", 7000, drakewarrior3, null);
			}
			else
			{
				drakewarrior3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior3_6);
				drakewarrior3.setRunning();
				startQuestTimer("move_28", 7000, drakewarrior3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_28")) && (drakewarrior3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_28", 7000, drakewarrior3, null);
			}
			else
			{
				drakewarrior3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior3_7);
				drakewarrior3.setRunning();
				startQuestTimer("move_29", 7000, drakewarrior3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_29")) && (drakewarrior3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_29", 7000, drakewarrior3, null);
			}
			else
			{
				drakewarrior3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior3_8);
				drakewarrior3.setRunning();
				startQuestTimer("move_30", 7000, drakewarrior3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_30")) && (drakewarrior3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_30", 7000, drakewarrior3, null);
			}
			else
			{
				drakewarrior3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior3_9);
				drakewarrior3.setRunning();
				startQuestTimer("move_31", 7000, drakewarrior3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_31")) && (drakewarrior3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_31", 7000, drakewarrior3, null);
			}
			else
			{
				drakewarrior3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior3_10);
				drakewarrior3.setRunning();
				startQuestTimer("move_32", 7000, drakewarrior3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_32")) && (drakewarrior3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_32", 7000, drakewarrior3, null);
			}
			else
			{
				drakewarrior3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior3_11);
				drakewarrior3.setRunning();
				startQuestTimer("move_33", 7000, drakewarrior3, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_33")) && (drakewarrior3 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_33", 7000, drakewarrior3, null);
			}
			else
			{
				drakewarrior3.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior3_12);
				drakewarrior3.setRunning();
				startQuestTimer("move_new3", 7000, drakewarrior3, null);
			}
		}
		else if ((event.equalsIgnoreCase("drakewarrior4_spawn")) && (drakewarrior4 == null))
		{
			drakewarrior4 = addSpawn(DRAKE_WARRIOR, 144877, 114966, -3725, 0, false, 0L);
			drakewarrior4.setIsNoRndWalk(true);
			drakewarrior4.setRunning();
			startQuestTimer("move_34", 7000, drakewarrior4, null);
		}
		else if ((event.equalsIgnoreCase("move_new4")) && (drakewarrior4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_new4", 7000, drakewarrior4, null);
			}
			else
			{
				drakewarrior4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior4_1);
				drakewarrior4.setRunning();
				startQuestTimer("move_34", 7000, drakewarrior4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_34")) && (drakewarrior4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_34", 7000, drakewarrior4, null);
			}
			else
			{
				if (!drakewarrior4.isInsideRadius(drakewarrior4_1.x, drakewarrior4_1.y, drakewarrior4_1.z, 100, true, false))
					drakewarrior4.teleToLocation(drakewarrior4_1.x, drakewarrior4_1.y, drakewarrior4_1.z);
				drakewarrior4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior4_2);
				drakewarrior4.setRunning();
				startQuestTimer("move_35", 7000, drakewarrior4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_35")) && (drakewarrior4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_35", 7000, drakewarrior4, null);
			}
			else
			{
				drakewarrior4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior4_3);
				drakewarrior4.setRunning();
				startQuestTimer("move_36", 7000, drakewarrior4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_36")) && (drakewarrior4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_36", 7000, drakewarrior4, null);
			}
			else
			{
				drakewarrior4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior4_4);
				drakewarrior4.setRunning();
				startQuestTimer("move_37", 7000, drakewarrior4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_37")) && (drakewarrior4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_37", 7000, drakewarrior4, null);
			}
			else
			{
				drakewarrior4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior4_5);
				drakewarrior4.setRunning();
				startQuestTimer("move_38", 7000, drakewarrior4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_38")) && (drakewarrior4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_38", 7000, drakewarrior4, null);
			}
			else
			{
				drakewarrior4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior4_6);
				drakewarrior4.setRunning();
				startQuestTimer("move_39", 7000, drakewarrior4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_39")) && (drakewarrior4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_39", 7000, drakewarrior4, null);
			}
			else
			{
				drakewarrior4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior4_7);
				drakewarrior4.setRunning();
				startQuestTimer("move_40", 7000, drakewarrior4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_40")) && (drakewarrior4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_40", 7000, drakewarrior4, null);
			}
			else
			{
				drakewarrior4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior4_8);
				drakewarrior4.setRunning();
				startQuestTimer("move_41", 7000, drakewarrior4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_41")) && (drakewarrior4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_41", 7000, drakewarrior4, null);
			}
			else
			{
				drakewarrior4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior4_9);
				drakewarrior4.setRunning();
				startQuestTimer("move_42", 7000, drakewarrior4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_42")) && (drakewarrior4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_42", 7000, drakewarrior4, null);
			}
			else
			{
				drakewarrior4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior4_10);
				drakewarrior4.setRunning();
				startQuestTimer("move_43", 7000, drakewarrior4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_43")) && (drakewarrior4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_43", 7000, drakewarrior4, null);
			}
			else
			{
				drakewarrior4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior4_11);
				drakewarrior4.setRunning();
				startQuestTimer("move_44", 7000, drakewarrior4, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_44")) && (drakewarrior4 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_44", 7000, drakewarrior4, null);
			}
			else
			{
				drakewarrior4.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior4_12);
				drakewarrior4.setRunning();
				startQuestTimer("move_new4", 7000, drakewarrior4, null);
			}
		}
		else if ((event.equalsIgnoreCase("drakewarrior5_spawn")) && (drakewarrior5 == null))
		{
			drakewarrior5 = addSpawn(DRAKE_WARRIOR, 148000, 112039, -3725, 0, false, 0L);
			drakewarrior5.setIsNoRndWalk(true);
			drakewarrior5.setRunning();
			startQuestTimer("move_45", 7000, drakewarrior5, null);
		}
		else if ((event.equalsIgnoreCase("move_new5")) && (drakewarrior5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_new5", 7000, drakewarrior5, null);
			}
			else
			{
				drakewarrior5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior5_1);
				drakewarrior5.setRunning();
				startQuestTimer("move_45", 7000, drakewarrior5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_45")) && (drakewarrior5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_45", 7000, drakewarrior5, null);
			}
			else
			{
				if (!drakewarrior5.isInsideRadius(drakewarrior5_1.x, drakewarrior5_1.y, drakewarrior5_1.z, 100, true, false))
					drakewarrior5.teleToLocation(drakewarrior5_1.x, drakewarrior5_1.y, drakewarrior5_1.z);
				drakewarrior5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior5_2);
				drakewarrior5.setRunning();
				startQuestTimer("move_46", 7000, drakewarrior5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_46")) && (drakewarrior5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_46", 7000, drakewarrior5, null);
			}
			else
			{
				drakewarrior5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior5_3);
				drakewarrior5.setRunning();
				startQuestTimer("move_47", 7000, drakewarrior5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_47")) && (drakewarrior5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_47", 7000, drakewarrior5, null);
			}
			else
			{
				drakewarrior5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior5_4);
				drakewarrior5.setRunning();
				startQuestTimer("move_48", 7000, drakewarrior5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_48")) && (drakewarrior5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_48", 7000, drakewarrior5, null);
			}
			else
			{
				drakewarrior5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior5_5);
				drakewarrior5.setRunning();
				startQuestTimer("move_49", 7000, drakewarrior5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_49")) && (drakewarrior5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_49", 7000, drakewarrior5, null);
			}
			else
			{
				drakewarrior5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior5_6);
				drakewarrior5.setRunning();
				startQuestTimer("move_50", 7000, drakewarrior5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_50")) && (drakewarrior5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_50", 7000, drakewarrior5, null);
			}
			else
			{
				drakewarrior5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior5_7);
				drakewarrior5.setRunning();
				startQuestTimer("move_51", 7000, drakewarrior5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_51")) && (drakewarrior5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_51", 7000, drakewarrior5, null);
			}
			else
			{
				drakewarrior5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior5_8);
				drakewarrior5.setRunning();
				startQuestTimer("move_52", 7000, drakewarrior5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_52")) && (drakewarrior5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_52", 7000, drakewarrior5, null);
			}
			else
			{
				drakewarrior5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior5_9);
				drakewarrior5.setRunning();
				startQuestTimer("move_53", 7000, drakewarrior5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_53")) && (drakewarrior5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_53", 7000, drakewarrior5, null);
			}
			else
			{
				drakewarrior5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior5_10);
				drakewarrior5.setRunning();
				startQuestTimer("move_54", 7000, drakewarrior5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_54")) && (drakewarrior5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_54", 7000, drakewarrior5, null);
			}
			else
			{
				drakewarrior5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior5_11);
				drakewarrior5.setRunning();
				startQuestTimer("move_55", 7000, drakewarrior5, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_55")) && (drakewarrior5 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_55", 7000, drakewarrior5, null);
			}
			else
			{
				drakewarrior5.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior5_12);
				drakewarrior5.setRunning();
				startQuestTimer("move_new5", 7000, drakewarrior5, null);
			}
		}
		else if ((event.equalsIgnoreCase("drakewarrior6_spawn")) && (drakewarrior6 == null))
		{
			drakewarrior6 = addSpawn(DRAKE_WARRIOR, 148896, 115364, -3725, 0, false, 0L);
			drakewarrior6.setIsNoRndWalk(true);
			drakewarrior6.setRunning();
			startQuestTimer("move_56", 7000, drakewarrior6, null);
		}
		else if ((event.equalsIgnoreCase("move_new6")) && (drakewarrior6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_new6", 7000, drakewarrior6, null);
			}
			else
			{
				drakewarrior6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior6_1);
				drakewarrior6.setRunning();
				startQuestTimer("move_56", 7000, drakewarrior6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_56")) && (drakewarrior6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_56", 7000, drakewarrior6, null);
			}
			else
			{
				if (!drakewarrior6.isInsideRadius(drakewarrior6_1.x, drakewarrior6_1.y, drakewarrior6_1.z, 100, true, false))
					drakewarrior6.teleToLocation(drakewarrior6_1.x, drakewarrior6_1.y, drakewarrior6_1.z);
				drakewarrior6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior6_2);
				drakewarrior6.setRunning();
				startQuestTimer("move_57", 7000, drakewarrior6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_57")) && (drakewarrior6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_57", 7000, drakewarrior6, null);
			}
			else
			{
				drakewarrior6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior6_3);
				drakewarrior6.setRunning();
				startQuestTimer("move_58", 7000, drakewarrior6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_58")) && (drakewarrior6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_58", 7000, drakewarrior6, null);
			}
			else
			{
				drakewarrior6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior6_4);
				drakewarrior6.setRunning();
				startQuestTimer("move_59", 7000, drakewarrior6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_59")) && (drakewarrior6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_59", 7000, drakewarrior6, null);
			}
			else
			{
				drakewarrior6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior6_5);
				drakewarrior6.setRunning();
				startQuestTimer("move_60", 7000, drakewarrior6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_60")) && (drakewarrior6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_60", 7000, drakewarrior6, null);
			}
			else
			{
				drakewarrior6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior6_6);
				drakewarrior6.setRunning();
				startQuestTimer("move_61", 7000, drakewarrior6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_61")) && (drakewarrior6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_61", 7000, drakewarrior6, null);
			}
			else
			{
				drakewarrior6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior6_7);
				drakewarrior6.setRunning();
				startQuestTimer("move_62", 7000, drakewarrior6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_62")) && (drakewarrior6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_62", 7000, drakewarrior6, null);
			}
			else
			{
				drakewarrior6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior6_8);
				drakewarrior6.setRunning();
				startQuestTimer("move_63", 7000, drakewarrior6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_63")) && (drakewarrior6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_63", 7000, drakewarrior6, null);
			}
			else
			{
				drakewarrior6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior6_9);
				drakewarrior6.setRunning();
				startQuestTimer("move_64", 7000, drakewarrior6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_64")) && (drakewarrior6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_64", 7000, drakewarrior6, null);
			}
			else
			{
				drakewarrior6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior6_10);
				drakewarrior6.setRunning();
				startQuestTimer("move_65", 7000, drakewarrior6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_65")) && (drakewarrior6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_65", 7000, drakewarrior6, null);
			}
			else
			{
				drakewarrior6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior6_11);
				drakewarrior6.setRunning();
				startQuestTimer("move_66", 7000, drakewarrior6, null);
			}
		}
		else if ((event.equalsIgnoreCase("move_66")) && (drakewarrior6 != null))
		{
			if ((npc.isCastingNow()) || (npc.isAttackingNow()) || (npc.isInCombat()))
			{
				startQuestTimer("move_66", 7000, drakewarrior6, null);
			}
			else
			{
				drakewarrior6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, drakewarrior6_12);
				drakewarrior6.setRunning();
				startQuestTimer("move_new6", 7000, drakewarrior6, null);
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
		if (npc.getNpcId() == DRAKE_WARRIOR)
		{
			if (npc == drakewarrior1)
			{
				startQuestTimer("drakewarrior1_spawn", 300000, null, null);
				drakewarrior1 = null;
			}
			
			if (npc == drakewarrior2)
			{
				startQuestTimer("drakewarrior2_spawn", 300000, null, null);
				drakewarrior2 = null;
			}
			
			if (npc == drakewarrior3)
			{
				startQuestTimer("drakewarrior3_spawn", 300000, null, null);
				drakewarrior3 = null;
			}
			
			if (npc == drakewarrior4)
			{
				startQuestTimer("drakewarrior4_spawn", 300000, null, null);
				drakewarrior4 = null;
			}
			
			if (npc == drakewarrior5)
			{
				startQuestTimer("drakewarrior5_spawn", 300000, null, null);
				drakewarrior5 = null;
			}
			
			if (npc == drakewarrior6)
			{
				startQuestTimer("drakewarrior6_spawn", 300000, null, null);
				drakewarrior6 = null;
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
		new DrakeWarrior(-1, "DrakeWarrior", "group_template");
	}
}
