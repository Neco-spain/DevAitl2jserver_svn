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
public class Q00268_TracesOfEvil extends Quest
{
	private static final int[] NPCS =
	{
		20474,
		20476,
		20478
	};
	
	public Q00268_TracesOfEvil(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(30559);
		addTalkId(30559);
		
		for (int mob : NPCS)
		{
			addKillId(mob);
		}
		
		questItemIds = new int[]
		{
			10869
		};
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		if (event.equalsIgnoreCase("30559-02.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = Quest.getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.CREATED:
				if (player.getLevel() < 15)
				{
					htmltext = "30559-00.htm";
					st.exitQuest(true);
				}
				else
				{
					htmltext = "30559-01.htm";
				}
				break;
			case State.STARTED:
				if (st.getQuestItemsCount(10869) >= 30)
				{
					htmltext = "30559-04.htm";
					st.takeItems(10869, -1);
					st.giveItems(57, 2474);
					st.addExpAndSp(8738, 409);
					st.playSound("ItemSound.quest_finish");
					st.exitQuest(true);
				}
				else
				{
					htmltext = "30559-03.htm";
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public final String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		if (st.getInt("cond") == 1)
		{
			if (st.getQuestItemsCount(10869) < 29)
			{
				st.playSound("ItemSound.quest_itemget");
			}
			else if (st.getQuestItemsCount(10869) >= 29)
			{
				st.playSound("ItemSound.quest_middle");
				st.set("cond", "2");
				st.giveItems(10869, 1);
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00268_TracesOfEvil(268, Q00268_TracesOfEvil.class.getSimpleName(), "Traces Of Evil");
	}
}