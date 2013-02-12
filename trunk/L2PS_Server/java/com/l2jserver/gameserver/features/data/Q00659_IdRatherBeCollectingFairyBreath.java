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
public class Q00659_IdRatherBeCollectingFairyBreath extends Quest
{
	private static final int GALATEA = 30634;
	private static final int FAIRY_BREATH = 8286;
	private static final int SOBBING_WIND = 21023;
	private static final int BABBLING_WIND = 21024;
	private static final int GIGGLING_WIND = 21025;
	
	public Q00659_IdRatherBeCollectingFairyBreath(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(GALATEA);
		addTalkId(GALATEA);
		
		addKillId(new int[]
		{
			SOBBING_WIND,
			BABBLING_WIND,
			GIGGLING_WIND
		});
		
		questItemIds = new int[]
		{
			FAIRY_BREATH
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
		
		if (event.equalsIgnoreCase("30634-03.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30634-06.htm"))
		{
			int count = (int) st.getQuestItemsCount(8286);
			if (count > 0)
			{
				st.takeItems(8286, count);
				if (count < 10)
				{
					st.rewardItems(57, count * 50);
				}
				else
				{
					st.rewardItems(57, (count * 50) + 5365);
				}
			}
		}
		else if (event.equalsIgnoreCase("30634-08.htm"))
		{
			st.exitQuest(true);
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
				if (player.getLevel() >= 26)
				{
					htmltext = "30634-02.htm";
				}
				else
				{
					htmltext = "30634-01.htm";
					st.exitQuest(true);
				}
				break;
			case State.STARTED:
				if (st.getQuestItemsCount(8286) == 0L)
				{
					htmltext = "30634-04.htm";
				}
				else
				{
					htmltext = "30634-05.htm";
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
		
		if ((st.isStarted()) && (st.getRandom(10) < 9))
		{
			st.giveItems(FAIRY_BREATH, 1);
			st.playSound("ItemSound.quest_itemget");
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00659_IdRatherBeCollectingFairyBreath(659, Q00659_IdRatherBeCollectingFairyBreath.class.getSimpleName(), "Id Rather Be Collecting Fairy Breath");
	}
}