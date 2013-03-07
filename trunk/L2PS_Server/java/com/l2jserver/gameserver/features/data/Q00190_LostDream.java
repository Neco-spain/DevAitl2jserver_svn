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

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public final class Q00190_LostDream extends Quest
{
	private static int Kusto = 30512;
	private static int Nikola = 30621;
	private static int Lorain = 30673;
	private static int Juris = 30113;
	
	public Q00190_LostDream(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addTalkId(Kusto);
		addTalkId(Lorain);
		addTalkId(Nikola);
		addTalkId(Juris);
		addFirstTalkId(Kusto);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		if (event.equalsIgnoreCase("30512-02.htm"))
		{
			st.set("cond", "1");
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30512-05.htm"))
		{
			st.playSound("ItemSound.quest_middle");
			st.set("cond", "3");
		}
		else if (event.equalsIgnoreCase("30113-03.htm"))
		{
			st.playSound("ItemSound.quest_middle");
			st.set("cond", "2");
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		
		switch (st.getState())
		{
			case State.STARTED:
				if (npcId == Kusto)
				{
					if (cond == 0)
					{
						if (player.getLevel() < 42)
						{
							htmltext = "30512-00.htm";
						}
						else
						{
							htmltext = "30512-01.htm";
						}
					}
					else if (cond == 1)
					{
						htmltext = "30512-03.htm";
					}
					else if (cond == 2)
					{
						htmltext = "30512-04.htm";
					}
					else if (cond == 3)
					{
						htmltext = "30512-06.htm";
					}
					else if (cond == 5)
					{
						htmltext = "30512-07.htm";
						if (player.getLevel() < 50)
						{
							st.addExpAndSp(309467, 20614);
						}
						st.giveItems(57, 109427);
						st.playSound("ItemSound.quest_finish");
						st.exitQuest(false);
					}
				}
				else if (npcId == Juris)
				{
					if (cond == 1)
					{
						htmltext = "30113-01.htm";
					}
					else if (cond == 2)
					{
						htmltext = "30113-04.htm";
					}
				}
				else if (npcId == Lorain)
				{
					if (cond == 3)
					{
						htmltext = "30673-01.htm";
						st.playSound("ItemSound.quest_middle");
						st.set("cond", "4");
					}
					else if (cond == 4)
					{
						htmltext = "30673-02.htm";
					}
				}
				else if (npcId == Nikola)
				{
					if (cond == 4)
					{
						htmltext = "30621-01.htm";
						st.playSound("ItemSound.quest_middle");
						st.set("cond", "5");
					}
					else if (cond == 5)
					{
						htmltext = "30621-02.htm";
					}
				}
				break;
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		QuestState qs = player.getQuestState("Q00187_NikolasHeart");
		
		if ((npc.getNpcId() == Kusto) && (qs != null) && qs.isCompleted() && (st == null))
		{
			st = newQuestState(player);
			st.setState(State.STARTED);
		}
		else
		{
			npc.showChatWindow(player);
		}
		return "";
	}
	
	public static void main(String[] args)
	{
		new Q00190_LostDream(190, Q00190_LostDream.class.getSimpleName(), "Lost Dream");
	}
}