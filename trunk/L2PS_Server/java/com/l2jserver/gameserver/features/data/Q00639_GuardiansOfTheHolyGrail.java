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

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00639_GuardiansOfTheHolyGrail extends Quest
{
	private final static int DOMINIC = 31350;
	private final static int GREMORY = 32008;
	private final static int GRAIL = 32028;
	
	private static final int[] MOBS =
	{
		22122,
		22123,
		22124,
		22125,
		22126,
		22127,
		22128,
		22129,
		22130,
		22131,
		22132,
		22133,
		22134,
		22135,
		22136
	};
	
	private final static int WATER_BOTTLE = 8070;
	private final static int HOLY_WATER_BOTTLE = 8071;
	private final static int SCRIPTURES = 8069;
	
	public Q00639_GuardiansOfTheHolyGrail(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(DOMINIC);
		addTalkId(DOMINIC);
		addTalkId(GREMORY);
		addTalkId(GRAIL);
		
		for (int mob : MOBS)
		{
			addKillId(mob);
		}
		
		questItemIds = new int[]
		{
			WATER_BOTTLE,
			HOLY_WATER_BOTTLE,
			SCRIPTURES
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
		
		if (event.equalsIgnoreCase("31350-03.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("31350-07.htm"))
		{
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(true);
		}
		else if (event.equalsIgnoreCase("31350-08.htm"))
		{
			long QI = st.getQuestItemsCount(SCRIPTURES);
			st.takeItems(SCRIPTURES, -1);
			st.giveItems(57, 1625 * QI);
		}
		else if (event.equalsIgnoreCase("32008-05.htm"))
		{
			st.set("cond", "2");
			st.playSound("ItemSound.quest_middle");
			st.giveItems(WATER_BOTTLE, 1);
		}
		else if (event.equalsIgnoreCase("32028-02.htm"))
		{
			st.set("cond", "3");
			st.playSound("ItemSound.quest_middle");
			st.takeItems(WATER_BOTTLE, -1);
			st.giveItems(HOLY_WATER_BOTTLE, 1);
		}
		else if (event.equalsIgnoreCase("32008-07.htm"))
		{
			st.set("cond", "4");
			st.playSound("ItemSound.quest_middle");
			st.takeItems(HOLY_WATER_BOTTLE, -1);
		}
		else if (event.equalsIgnoreCase("32008-08a.htm"))
		{
			st.takeItems(SCRIPTURES, 4000);
			st.giveItems(959, 1);
		}
		else if (event.equalsIgnoreCase("32008-08b.htm"))
		{
			st.takeItems(SCRIPTURES, 400);
			st.giveItems(960, 1);
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
		
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		
		switch (st.getState())
		{
			case State.CREATED:
				if (npcId == DOMINIC)
				{
					if (player.getLevel() >= 73)
					{
						htmltext = "31350-01.htm";
					}
					else
					{
						htmltext = "31350-00.htm";
						st.exitQuest(true);
					}
				}
				break;
			case State.STARTED:
				if (npcId == DOMINIC)
				{
					if (st.getQuestItemsCount(SCRIPTURES) > 0)
					{
						htmltext = "31350-04.htm";
					}
					else
					{
						htmltext = "31350-05.htm";
					}
				}
				else if (npcId == GREMORY)
				{
					if (cond == 1)
					{
						htmltext = "32008-01.htm";
					}
					else if (cond == 2)
					{
						htmltext = "32008-05b.htm";
					}
					else if (cond == 3)
					{
						htmltext = "32008-06.htm";
					}
					else if (cond == 4)
					{
						if (st.getQuestItemsCount(SCRIPTURES) < 400)
						{
							htmltext = "32008-08d.htm";
						}
						else if (st.getQuestItemsCount(SCRIPTURES) >= 4000)
						{
							htmltext = "32008-08c.htm";
						}
						else
						{
							htmltext = "32008-08.htm";
						}
					}
				}
				else if (npcId == GRAIL)
				{
					if (cond == 2)
					{
						htmltext = "32028-01.htm";
					}
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public final String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final QuestState st = player.getQuestState(getName());
		if ((st == null) || !st.isStarted())
		{
			return null;
		}
		
		int chance = (int) (300 * Config.RATE_QUEST_DROP);
		int numItems = (chance / 1000);
		chance = chance % 1000;
		if (getRandom(1000) < chance)
		{
			numItems += 1;
		}
		
		if (numItems > 0)
		{
			st.giveItems(SCRIPTURES, (numItems));
			st.playSound("ItemSound.quest_itemget");
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00639_GuardiansOfTheHolyGrail(639, Q00639_GuardiansOfTheHolyGrail.class.getSimpleName(), "Guardians Of The Holy Grail");
	}
}