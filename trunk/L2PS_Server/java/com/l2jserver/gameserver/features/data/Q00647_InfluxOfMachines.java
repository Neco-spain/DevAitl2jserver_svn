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
import com.l2jserver.gameserver.util.Util;

/**
 * Author: RobikBobik L2PS Team
 */
public final class Q00647_InfluxOfMachines extends Quest
{
	private static final int GUTENHAGEN = 32069;
	private static final int BROKEN_GOLEM_FRAGMENT = 15521;
	
	private static final int[] MOBS =
	{
		22801,
		22802,
		22803,
		22804,
		22805,
		22806,
		22807,
		22808,
		22809,
		22810,
		22811,
		22812
	};
	
	private static final int[] REWARDS =
	{
		6887,
		6881,
		6897,
		7580,
		6883,
		6899,
		6891,
		6885,
		6893,
		6895
	};
	
	public Q00647_InfluxOfMachines(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(GUTENHAGEN);
		addTalkId(GUTENHAGEN);
		
		for (int i : MOBS)
		{
			addKillId(i);
		}
		
		questItemIds = new int[]
		{
			BROKEN_GOLEM_FRAGMENT
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
		
		if (event.equalsIgnoreCase("32069-03.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("32069-06.htm"))
		{
			if (st.getQuestItemsCount(BROKEN_GOLEM_FRAGMENT) < 500L)
			{
				htmltext = "32069-07.htm";
			}
			else if (st.getQuestItemsCount(BROKEN_GOLEM_FRAGMENT) >= 500L)
			{
				st.giveItems(REWARDS[com.l2jserver.util.Rnd.get(REWARDS.length)], 1L);
				st.takeItems(BROKEN_GOLEM_FRAGMENT, 500L);
				st.playSound("ItemSound.quest_finish");
				htmltext = "32069-07.htm";
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
		
		if (npc.getNpcId() == 32069)
		{
			switch (st.getState())
			{
				case State.CREATED:
					if (player.getLevel() >= 70)
					{
						htmltext = "32069-01.htm";
					}
					else
					{
						htmltext = "32069-02.htm";
					}
					break;
				case State.STARTED:
					if (st.getInt("cond") != 1)
					{
						break;
					}
					if (st.getQuestItemsCount(BROKEN_GOLEM_FRAGMENT) < 500L)
					{
						htmltext = "32069-05.htm";
					}
					else
					{
						if (st.getQuestItemsCount(BROKEN_GOLEM_FRAGMENT) < 500L)
						{
							break;
						}
						htmltext = "32069-04.htm";
					}
			}
		}
		return htmltext;
	}
	
	@Override
	public final String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState st = player.getQuestState(getName());
		if ((st == null) || (st.getState() != 1))
		{
			return null;
		}
		
		if (Util.contains(MOBS, npc.getNpcId()))
		{
			int chance = (int) (30.0F * Config.RATE_QUEST_DROP);
			int numItems = chance / 100;
			chance %= 100;
			if (st.getRandom(100) < chance)
			{
				numItems++;
			}
			if (numItems > 0)
			{
				st.playSound("ItemSound.quest_itemget");
				st.giveItems(BROKEN_GOLEM_FRAGMENT, numItems);
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00647_InfluxOfMachines(647, Q00647_InfluxOfMachines.class.getSimpleName(), "In flux Of Machines");
	}
}