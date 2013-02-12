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
public class Q00614_SlayTheEnemyCommander extends Quest
{
	private static final int TAYR_HEAD = 7241;
	private static final int WISDOM_FEATHER = 7230;
	private static final int VARKA_ALLIANCE_FOUR = 7224;
	
	public Q00614_SlayTheEnemyCommander(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(31377);
		addTalkId(31377);
		
		addKillId(25302);
		
		questItemIds = new int[]
		{
			TAYR_HEAD
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
		
		if (event.equalsIgnoreCase("31377-04.htm"))
		{
			if ((player.getAllianceWithVarkaKetra() <= -4) && (st.getQuestItemsCount(VARKA_ALLIANCE_FOUR) > 0) && (st.getQuestItemsCount(WISDOM_FEATHER) == 0))
			{
				if (player.getLevel() >= 75)
				{
					st.set("cond", "1");
					st.setState(State.STARTED);
					st.playSound("ItemSound.quest_accept");
				}
				else
				{
					htmltext = "31377-03.htm";
					st.exitQuest(true);
				}
			}
			else
			{
				htmltext = "31377-02.htm";
				st.exitQuest(true);
			}
		}
		else if (event.equalsIgnoreCase("31377-07.htm"))
		{
			if (st.getQuestItemsCount(TAYR_HEAD) == 1)
			{
				st.takeItems(TAYR_HEAD, -1);
				st.giveItems(WISDOM_FEATHER, 1);
				st.addExpAndSp(10000, 0);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(true);
			}
			else
			{
				htmltext = "31377-06.htm";
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.CREATED:
				htmltext = "31377-01.htm";
				break;
			case State.STARTED:
				if (st.getQuestItemsCount(TAYR_HEAD) > 0)
				{
					htmltext = "31377-05.htm";
				}
				else
				{
					htmltext = "31377-06.htm";
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		if (player.getAllianceWithVarkaKetra() <= -4)
		{
			QuestState st = player.getQuestState(getName());
			if (st.hasQuestItems(VARKA_ALLIANCE_FOUR))
			{
				st.set("cond", "2");
				st.giveItems(TAYR_HEAD, 1);
				st.playSound("ItemSound.quest_middle");
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00614_SlayTheEnemyCommander(614, Q00614_SlayTheEnemyCommander.class.getSimpleName(), "Slay The Enemy Commander");
	}
}