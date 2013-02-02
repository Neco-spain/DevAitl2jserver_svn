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

import java.util.Calendar;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

import gnu.trove.map.hash.TIntIntHashMap;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00451_LuciensAltar extends Quest
{
	private static final int DAICHIR = 30537;
	private static final TIntIntHashMap ALTARS = new TIntIntHashMap();
	static
	{
		ALTARS.put(32706, 1);
		ALTARS.put(32707, 2);
		ALTARS.put(32708, 4);
		ALTARS.put(32709, 8);
		ALTARS.put(32710, 16);
	}
	// Items
	private static final int REPLENISHEDBEAD = 14877;
	private static final int DISCHARGEDBEAD = 14878;
	// Time Variables
	private static final int RESET_HOUR = 6;
	private static final int RESET_MIN = 30;
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		if (event.equalsIgnoreCase("30537-03.htm"))
		{
			st.set("cond", "1");
			st.set("altars_state", "0");
			st.setState(State.STARTED);
			st.giveItems(REPLENISHEDBEAD, 5);
			st.playSound("ItemSound.quest_accept");
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
		
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if (npcId == DAICHIR)
		{
			switch (cond)
			{
				case 0:
					String reset = st.get("reset");
					long remain = 0;
					try
					{
						remain = Long.parseLong(reset) - System.currentTimeMillis();
					}
					catch (NumberFormatException nfe)
					{
					}
					if (remain <= 0)
					{
						if (player.getLevel() >= 80)
						{
							htmltext = "30537-01.htm";
						}
						else
						{
							htmltext = "30537-00.htm";
						}
						st.exitQuest(true);
					}
					else
					{
						htmltext = "30537-06.htm";
					}
					break;
				case 1:
					htmltext = "30537-04.htm";
					break;
				case 2:
					htmltext = "30537-05.htm";
					st.giveReward(57, 127690);
					st.takeItems(DISCHARGEDBEAD, 5);
					st.setState(State.COMPLETED);
					st.unset("cond");
					st.unset("altars_state");
					st.exitQuest(false);
					st.playSound("ItemSound.quest_finish");
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.MINUTE, RESET_MIN);
					// if time is >= RESET_HOUR - roll to the next day;
					if (cal.get(Calendar.HOUR_OF_DAY) >= RESET_HOUR)
					{
						cal.add(Calendar.DATE, 1);
					}
					cal.set(Calendar.HOUR_OF_DAY, RESET_HOUR);
					st.set("reset", String.valueOf(cal.getTimeInMillis()));
			}
		}
		else if ((cond == 1) && ALTARS.containsKey(npcId))
		{
			int idx = ALTARS.get(npcId);
			int state = st.getInt("altars_state");
			if ((state & idx) == 0)
			{
				st.set("altars_state", String.valueOf(state | idx));
				st.takeItems(REPLENISHEDBEAD, 1);
				st.giveItems(DISCHARGEDBEAD, 1);
				st.playSound("ItemSound.quest_itemget");
				if (st.getQuestItemsCount(DISCHARGEDBEAD) == 5)
				{
					st.set("cond", "2");
					st.playSound("ItemSound.quest_middle");
				}
				htmltext = "recharge.htm";
			}
			else
			{
				htmltext = "findother.htm";
			}
		}
		return htmltext;
	}
	
	public Q00451_LuciensAltar(int questId, String name, String descr)
	{
		super(questId, name, descr);
		questItemIds = new int[]
		{
			REPLENISHEDBEAD,
			DISCHARGEDBEAD
		};
		addStartNpc(DAICHIR);
		addTalkId(DAICHIR);
	}
	
	public static void main(String[] args)
	{
		new Q00451_LuciensAltar(451, Q00451_LuciensAltar.class.getSimpleName(), "Lucien's Altar");
	}
}
