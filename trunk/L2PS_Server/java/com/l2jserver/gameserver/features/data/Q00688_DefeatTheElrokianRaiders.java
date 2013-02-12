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

import com.l2jserver.Config;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.util.Rnd;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00688_DefeatTheElrokianRaiders extends Quest
{
	private static int DROP_CHANCE = 50;
	
	private static int DINOSAUR_FANG_NECKLACE = 8785;
	
	public Q00688_DefeatTheElrokianRaiders(int id, String name, String descr)
	{
		super(id, name, descr);
		
		addStartNpc(32105);
		addTalkId(32105);
		addKillId(22214);
		questItemIds = new int[]
		{
			DINOSAUR_FANG_NECKLACE
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
		
		long count = st.getQuestItemsCount(DINOSAUR_FANG_NECKLACE);
		
		if (event.equalsIgnoreCase("32105-02.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("32105-08.htm"))
		{
			if (count > 0)
			{
				st.takeItems(DINOSAUR_FANG_NECKLACE, -1);
				st.giveItems(57, count * 3000);
			}
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(true);
		}
		else if (event.equalsIgnoreCase("32105-06.htm"))
		{
			if (count > 0)
			{
				st.takeItems(DINOSAUR_FANG_NECKLACE, -1);
				st.giveItems(57, count * 3000);
			}
			else
			{
				htmltext = "32105-06a.htm";
			}
		}
		else if (event.equalsIgnoreCase("32105-07.htm"))
		{
			if (count >= 100)
			{
				st.takeItems(DINOSAUR_FANG_NECKLACE, 100);
				st.giveItems(57, 450000);
			}
			else
			{
				htmltext = "32105-07a.htm";
			}
		}
		else if (event.equalsIgnoreCase("None"))
		{
			htmltext = null;
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
		
		int id = st.getState();
		int cond = st.getInt("cond");
		long count = st.getQuestItemsCount(DINOSAUR_FANG_NECKLACE);
		
		if ((id == State.CREATED) & (cond == 0))
		{
			if (st.getPlayer().getLevel() >= 75)
			{
				htmltext = "32105-01.htm";
			}
			else
			{
				htmltext = "32105-00.htm";
				st.exitQuest(true);
			}
		}
		else if ((id == State.STARTED) & (cond == 1))
		{
			if (count == 0)
			{
				htmltext = "32105-06a.htm";
			}
			else
			{
				htmltext = "32105-05.htm";
			}
		}
		return htmltext;
	}
	
	public String onKill(L2Npc npc, QuestState st)
	{
		long count = st.getQuestItemsCount(DINOSAUR_FANG_NECKLACE);
		if ((Rnd.get(100) < DROP_CHANCE) && (st.getInt("cond") == 1))
		{
			long numItems = (int) Config.RATE_QUEST_DROP;
			if ((count + numItems) > 100)
			{
				numItems = 100 - count;
			}
			if ((count + numItems) >= 100)
			{
				st.playSound("ItemSound.quest_middle");
			}
			else
			{
				st.playSound("ItemSound.quest_itemget");
			}
			st.giveItems(DINOSAUR_FANG_NECKLACE, numItems);
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00688_DefeatTheElrokianRaiders(688, Q00688_DefeatTheElrokianRaiders.class.getSimpleName(), "Defeat The Elrokian Raiders");
	}
}