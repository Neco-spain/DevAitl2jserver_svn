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
public class Q00295_DreamingOfTheSkies extends Quest
{
	private static final int ARIN = 30536;
	private static final int FLOATING_STONE = 1492;
	private static final int RING_OF_FIREFLY = 1509;
	private static final int MAGICAL_WEAVER = 20153;
	
	public Q00295_DreamingOfTheSkies(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(ARIN);
		addTalkId(ARIN);
		
		addKillId(MAGICAL_WEAVER);
		
		questItemIds = new int[]
		{
			FLOATING_STONE
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
		
		if (event.equalsIgnoreCase("30536-03.htm"))
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
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.CREATED:
				if ((player.getLevel() >= 11) && (player.getLevel() <= 15))
				{
					htmltext = "30536-02.htm";
				}
				else
				{
					htmltext = "30536-01.htm";
					st.exitQuest(true);
				}
				break;
			case State.STARTED:
				if (st.getQuestItemsCount(FLOATING_STONE) < 50)
				{
					htmltext = "30536-04.htm";
				}
				else if (st.getQuestItemsCount(RING_OF_FIREFLY) == 0)
				{
					htmltext = "30536-05.htm";
					st.takeItems(FLOATING_STONE, -1);
					st.giveItems(RING_OF_FIREFLY, 1);
					st.addExpAndSp(0, 500);
					st.playSound("ItemSound.quest_finish");
					st.exitQuest(true);
				}
				else
				{
					htmltext = "30536-06.htm";
					st.takeItems(FLOATING_STONE, -1);
					st.rewardItems(57, 2400);
					st.addExpAndSp(0, 500);
					st.playSound("ItemSound.quest_finish");
					st.exitQuest(true);
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		if ((st.getInt("cond") == 1) && (st.getRandom(100) < 25))
		{
			int itemNumber = 1 + st.getRandom(2);
			
			if ((st.getQuestItemsCount(FLOATING_STONE) + itemNumber) > 50)
			{
				itemNumber = (int) (50 - st.getQuestItemsCount(FLOATING_STONE));
			}
			
			st.giveItems(FLOATING_STONE, itemNumber);
			if (st.getQuestItemsCount(FLOATING_STONE) < 50)
			{
				st.playSound("ItemSound.quest_itemget");
			}
			else
			{
				st.set("cond", "2");
				st.playSound("ItemSound.quest_middle");
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00295_DreamingOfTheSkies(295, Q00295_DreamingOfTheSkies.class.getSimpleName(), "Dreaming Of The Skies");
	}
}