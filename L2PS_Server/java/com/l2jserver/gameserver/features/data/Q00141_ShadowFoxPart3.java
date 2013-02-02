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

import com.l2jserver.gameserver.instancemanager.QuestManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00141_ShadowFoxPart3 extends Quest
{
	private static final int NATOOLS = 30894;
	private static final int REPORT = 10350;
	private static final int[] NPC =
	{
		20791,
		20792,
		20135
	};
	
	public Q00141_ShadowFoxPart3(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addFirstTalkId(NATOOLS);
		addTalkId(NATOOLS);
		for (int mob : NPC)
		{
			addKillId(mob);
		}
		
		questItemIds = new int[]
		{
			REPORT
		};
	}
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		if (event.equalsIgnoreCase("30894-02.htm"))
		{
			st.set("cond", "1");
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30894-04.htm"))
		{
			st.set("cond", "2");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("30894-15.htm"))
		{
			st.set("cond", "4");
			st.unset("talk");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("30894-18.htm"))
		{
			st.playSound("ItemSound.quest_middle");
			st.unset("talk");
			st.exitQuest(false);
			st.giveReward(57, 88888);
			if ((st.getPlayer().getLevel() >= 37) && (st.getPlayer().getLevel() <= 42))
			{
				st.addExpAndSp(278005, 17058);
			}
			QuestState qs = player.getQuestState("Q00998_FallenAngelSelect");
			if (qs == null)
			{
				Quest q = QuestManager.getInstance().getQuest("Q00998_FallenAngelSelect");
				if (q != null)
				{
					qs = q.newQuestState(player);
				}
			}
			qs.setState(State.STARTED);
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
		final int id = st.getState();
		final int cond = st.getInt("cond");
		final int talk = st.getInt("talk");
		if (id == State.CREATED)
		{
			return htmltext;
		}
		if (id == State.COMPLETED)
		{
			htmltext = getAlreadyCompletedMsg(player);
		}
		else if (id == State.STARTED)
		{
			if (cond == 0)
			{
				if (player.getLevel() >= 37)
				{
					htmltext = "30894-01.htm";
				}
				else
				{
					htmltext = "30894-00.htm";
					st.exitQuest(true);
				}
			}
			else if (cond == 1)
			{
				htmltext = "30894-02.htm";
			}
			else if (cond == 2)
			{
				htmltext = "30894-05.htm";
			}
			else if (cond == 3)
			{
				if (cond == talk)
				{
					htmltext = "30894-07.htm";
				}
				else
				{
					htmltext = "30894-06.htm";
					st.takeItems(REPORT, -1);
					st.set("talk", "1");
				}
			}
			else if (cond == 4)
			{
				htmltext = "30894-16.htm";
			}
		}
		
		return htmltext;
	}
	
	@Override
	public final String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		if (st.getState() != State.STARTED)
		{
			return null;
		}
		
		if ((st.getInt("cond") == 2) && (st.getRandom(100) <= 80) && (st.getQuestItemsCount(REPORT) < 30))
		{
			st.giveItems(REPORT, 1);
			if (st.getQuestItemsCount(REPORT) >= 30)
			{
				st.set("cond", "3");
				st.playSound("ItemSound.quest_middle");
			}
			else
			{
				st.playSound("ItemSound.quest_itemget");
			}
		}
		return null;
	}
	
	@Override
	public final String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		
		if (st == null)
		{
			QuestState qs = player.getQuestState("Q00140_ShadowFoxPart2");
			st = this.newQuestState(player);
			
			if (qs != null)
			{
				if ((qs.getState() == State.COMPLETED) && (st.getState() == State.CREATED))
				{
					st.setState(State.STARTED);
				}
			}
		}
		else if ((st.getState() == State.COMPLETED) && (player.getLevel() >= 38))
		{
			QuestState qs2 = player.getQuestState("Q00998_FallenAngelSelect");
			QuestState qs3 = player.getQuestState("Q00142_FallenAngelRequestOfDawn");
			QuestState qs4 = player.getQuestState("Q00143_FallenAngelRequestOfDusk");
			if (qs2 != null)
			{
				if ((qs2.getState() == State.COMPLETED) && ((qs3 == null) || (qs4 == null)))
				{
					qs2.setState(State.STARTED);
				}
			}
		}
		npc.showChatWindow(player);
		return null;
		
	}
	
	public static void main(String[] args)
	{
		new Q00141_ShadowFoxPart3(141, Q00141_ShadowFoxPart3.class.getName(), "Shadow Fox - 3");
	}
}
