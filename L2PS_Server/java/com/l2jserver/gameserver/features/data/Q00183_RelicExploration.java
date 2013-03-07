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
public final class Q00183_RelicExploration extends Quest
{
	private static int Kusto = 30512;
	private static int Lorain = 30673;
	private static int Nikola = 30621;
	
	public Q00183_RelicExploration(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(Kusto);
		addTalkId(Kusto);
		addTalkId(Lorain);
		addTalkId(Nikola);
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
		
		if (event.equalsIgnoreCase("30512-03.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30673-04.htm"))
		{
			st.set("cond", "2");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("30621-02.htm"))
		{
			if (player.getLevel() < 50)
			{
				st.addExpAndSp(60000, 3000);
			}
			st.giveItems(57, 18100);
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(false);
		}
		else if (event.equalsIgnoreCase("Contract"))
		{
			Quest q1 = QuestManager.getInstance().getQuest("Q00184_NikolasCooperationContract");
			QuestState qs1 = player.getQuestState("Q00184_NikolasCooperationContract");
			if (q1 != null)
			{
				qs1 = q1.newQuestState(player);
				qs1.setState(State.STARTED);
				q1.notifyEvent("30621-01.htm", npc, player);
			}
			return null;
		}
		else if (event.equalsIgnoreCase("Consideration"))
		{
			Quest q2 = QuestManager.getInstance().getQuest("Q00185_NikolasCooperationConsideration");
			QuestState qs2 = player.getQuestState("Q00185_NikolasCooperationConsideration");
			if (q2 != null)
			{
				qs2 = q2.newQuestState(player);
				qs2.setState(State.STARTED);
				q2.notifyEvent("30621-01.htm", npc, player);
			}
			return null;
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
			case State.CREATED:
				if (npcId == Kusto)
				{
					if (player.getLevel() < 40)
					{
						htmltext = "30512-00.htm";
					}
					else
					{
						htmltext = "30512-01.htm";
					}
				}
				break;
			case State.STARTED:
				if (npcId == Kusto)
				{
					if (cond == 1)
					{
						htmltext = "30512-04.htm";
					}
				}
				else if (npcId == Lorain)
				{
					if (cond == 1)
					{
						htmltext = "30673-01.htm";
					}
					else
					{
						htmltext = "30673-05.htm";
					}
				}
				else if (npcId == Nikola)
				{
					if (cond == 2)
					{
						htmltext = "30621-01.htm";
					}
				}
				break;
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00183_RelicExploration(183, Q00183_RelicExploration.class.getSimpleName(), "Relic Exploration");
	}
}