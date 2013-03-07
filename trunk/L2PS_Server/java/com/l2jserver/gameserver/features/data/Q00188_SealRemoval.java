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
public final class Q00188_SealRemoval extends Quest
{
	private static int Nikola = 30621;
	private static int Lorain = 30673;
	private static int Dorothy = 30970;
	private static int BrokenMetal = 10369;
	
	public Q00188_SealRemoval(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(Lorain);
		addTalkId(Lorain);
		addTalkId(Nikola);
		addTalkId(Dorothy);
		
		questItemIds = new int[]
		{
			BrokenMetal
		};
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
		
		if (event.equalsIgnoreCase("30673-02.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
			st.giveItems(BrokenMetal, 1);
		}
		else if (event.equalsIgnoreCase("30621-03.htm"))
		{
			st.set("cond", "2");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("30970-03.htm"))
		{
			if (player.getLevel() < 50)
			{
				st.addExpAndSp(285935, 18711);
			}
			st.giveItems(57, 98583);
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(false);
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
				QuestState qs = player.getQuestState("Q00185_NikolasCooperationConsideration");
				QuestState qs1 = player.getQuestState("Q00184_NikolasCooperationContract");
				QuestState qs2 = player.getQuestState("Q00186_ContractExecution");
				if ((npcId == Lorain) && ((qs.getState() == State.COMPLETED) || (qs1.getState() == State.COMPLETED)) && (qs2 == null))
				{
					if (player.getLevel() < 41)
					{
						htmltext = "30673-00.htm";
					}
					else
					{
						htmltext = "30673-01.htm";
					}
				}
				break;
			case State.STARTED:
				if (npcId == Lorain)
				{
					if (cond == 1)
					{
						htmltext = "30673-03.htm";
					}
				}
				else if (npcId == Nikola)
				{
					if (cond == 1)
					{
						htmltext = "30621-01.htm";
					}
					else if (cond == 2)
					{
						htmltext = "30621-05.htm";
					}
				}
				else if (npcId == Dorothy)
				{
					if (cond == 2)
					{
						htmltext = "30970-01.htm";
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
		new Q00188_SealRemoval(188, Q00188_SealRemoval.class.getSimpleName(), "Seal Removal");
	}
}