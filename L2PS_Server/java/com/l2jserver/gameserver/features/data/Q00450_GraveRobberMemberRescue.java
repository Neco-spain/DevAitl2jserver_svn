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

import java.util.Calendar;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.L2CharPosition;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00450_GraveRobberMemberRescue extends Quest
{
	private static final int KANEMIKA = 32650;
	private static final int WARRIORNPC = 32651;
	private static final int WARRIORMOB = 22741;
	private static final int EVIDENCEOFMIGRATION = 14876;
	private static final int RESET_HOUR = 6;
	private static final int RESET_MIN = 30;
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		if (event.equalsIgnoreCase("32650-05.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		return htmltext;
	}
	
	@Override
	public final String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = Quest.getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if (npcId == KANEMIKA)
		{
			if (cond == 0)
			{
				String reset = st.get("reset");
				long remain = 0;
				if (reset != null)
				{
					try
					{
						remain = Long.parseLong(reset) - System.currentTimeMillis();
					}
					catch (NumberFormatException nfe)
					{
					}
				}
				
				if (remain <= 0)
				{
					if (player.getLevel() >= 80)
					{
						htmltext = "32650-01.htm";
					}
					else
					{
						htmltext = "32650-00.htm";
						st.exitQuest(true);
					}
				}
				else
				{
					htmltext = "32650-09.htm";
				}
			}
			else if (cond == 1)
			{
				if (st.getQuestItemsCount(EVIDENCEOFMIGRATION) >= 1)
				{
					htmltext = "32650-07.htm";
				}
				else
				{
					htmltext = "32650-06.htm";
				}
			}
			else if ((cond == 2) && (st.getQuestItemsCount(EVIDENCEOFMIGRATION) == 10))
			{
				htmltext = "32650-08.htm";
				st.giveReward(57, 65000);
				st.takeItems(EVIDENCEOFMIGRATION, 10);
				st.setState(State.COMPLETED);
				st.unset("cond");
				st.exitQuest(false);
				st.playSound("ItemSound.quest_finish");
				Calendar reset = Calendar.getInstance();
				reset.set(Calendar.MINUTE, RESET_MIN);
				// if time is >= RESET_HOUR - roll to the next day
				if (reset.get(Calendar.HOUR_OF_DAY) >= RESET_HOUR)
				{
					reset.add(Calendar.DATE, 1);
				}
				reset.set(Calendar.HOUR_OF_DAY, RESET_HOUR);
				st.set("reset", String.valueOf(reset.getTimeInMillis()));
			}
		}
		else if ((cond == 1) && (npcId == WARRIORNPC))
		{
			if (st.getRandom(100) < 50)
			{
				htmltext = "32651-01.htm";
				st.giveItems(EVIDENCEOFMIGRATION, 1);
				st.playSound("ItemSound.quest_itemget");
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new L2CharPosition(npc.getX() + 100, npc.getY() + 100, npc.getZ(), 0));
				npc.deleteMe();
				if (st.getQuestItemsCount(EVIDENCEOFMIGRATION) == 10)
				{
					st.set("cond", "2");
					st.playSound("ItemSound.quest_middle");
				}
			}
			else
			{
				htmltext = "";
				player.sendPacket(new ExShowScreenMessage("The grave robber warrior has been filled with dark energy and is attacking you!", 4000));
				L2MonsterInstance warrior = (L2MonsterInstance) st.addSpawn(WARRIORMOB, npc.getX(), npc.getY(), npc.getZ(), npc.getHeading(), true, 600000);
				warrior.setRunning();
				warrior.addDamageHate(player, 0, 999);
				warrior.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
				if (st.getRandom(100) < 50)
				{
					npc.broadcastNpcSay("...Grunt... oh...");
					npc.deleteMe();
				}
				else
				{
					npc.broadcastNpcSay("Grunt... What's... wrong with me...");
					npc.deleteMe();
				}
			}
		}
		return htmltext;
	}
	
	public Q00450_GraveRobberMemberRescue(int questId, String name, String descr)
	{
		super(questId, name, descr);
		questItemIds = new int[]
		{
			EVIDENCEOFMIGRATION
		};
		
		addStartNpc(KANEMIKA);
		addTalkId(KANEMIKA);
		addTalkId(WARRIORNPC);
	}
	
	public static void main(String[] args)
	{
		new Q00450_GraveRobberMemberRescue(450, Q00450_GraveRobberMemberRescue.class.getSimpleName(), "Grave Robber Member Rescue");
	}
}
