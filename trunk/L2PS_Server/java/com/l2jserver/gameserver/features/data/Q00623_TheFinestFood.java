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
public class Q00623_TheFinestFood extends Quest
{
	private static final int LEAF = 7199;
	private static final int MEAT = 7200;
	private static final int HORN = 7201;
	private static final int JEREMY = 31521;
	private static final int FLAVA = 21316;
	private static final int BUFFALO = 21315;
	private static final int ANTELOPE = 21318;
	
	public Q00623_TheFinestFood(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(JEREMY);
		addTalkId(JEREMY);
		
		addKillId(FLAVA, BUFFALO, ANTELOPE);
		
		questItemIds = new int[]
		{
			LEAF,
			MEAT,
			HORN
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
		
		if (event.equalsIgnoreCase("31521-02.htm"))
		{
			if ((player.getLevel() >= 71) && (player.getLevel() <= 78))
			{
				st.set("cond", "1");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
			}
			else
			{
				htmltext = "31521-03.htm";
			}
		}
		else if (event.equalsIgnoreCase("31521-05.htm"))
		{
			st.takeItems(LEAF, -1);
			st.takeItems(MEAT, -1);
			st.takeItems(HORN, -1);
			
			int luck = st.getRandom(100);
			if (luck < 11)
			{
				st.rewardItems(57, 25000);
				st.giveItems(6849, 1);
			}
			else if (luck < 23)
			{
				st.rewardItems(57, 65000);
				st.giveItems(6847, 1);
			}
			else if (luck < 33)
			{
				st.rewardItems(57, 25000);
				st.giveItems(6851, 1);
			}
			else
			{
				st.rewardItems(57, 73000);
				st.addExpAndSp(230000, 18250);
			}
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(true);
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
				htmltext = "31521-01.htm";
				break;
			case State.STARTED:
				int cond = st.getInt("cond");
				if (cond == 1)
				{
					htmltext = "31521-06.htm";
				}
				else if (cond == 2)
				{
					if ((st.getQuestItemsCount(LEAF) >= 100) && (st.getQuestItemsCount(MEAT) >= 100) && (st.getQuestItemsCount(HORN) >= 100))
					{
						htmltext = "31521-04.htm";
					}
					else
					{
						htmltext = "31521-07.htm";
					}
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		L2PcInstance partyMember = getRandomPartyMember(player, 1);
		if (partyMember == null)
		{
			return null;
		}
		
		QuestState st = partyMember.getQuestState(getName());
		
		if (st.getRandom(100) < 66)
		{
			switch (npc.getNpcId())
			{
				case FLAVA:
					if (st.getQuestItemsCount(LEAF) < 100)
					{
						st.giveItems(LEAF, 1);
						if ((st.getQuestItemsCount(LEAF) >= 100) && (st.getQuestItemsCount(MEAT) >= 100) && (st.getQuestItemsCount(HORN) >= 100))
						{
							st.set("cond", "2");
							st.playSound("ItemSound.quest_middle");
						}
						else
						{
							st.playSound("ItemSound.quest_itemget");
						}
					}
					break;
				case BUFFALO:
					if (st.getQuestItemsCount(MEAT) < 100)
					{
						st.giveItems(MEAT, 1);
						if ((st.getQuestItemsCount(LEAF) >= 100) && (st.getQuestItemsCount(MEAT) >= 100) && (st.getQuestItemsCount(HORN) >= 100))
						{
							st.set("cond", "2");
							st.playSound("ItemSound.quest_middle");
						}
						else
						{
							st.playSound("ItemSound.quest_itemget");
						}
					}
					break;
				case ANTELOPE:
					if (st.getQuestItemsCount(HORN) < 100)
					{
						st.giveItems(HORN, 1);
						if ((st.getQuestItemsCount(LEAF) >= 100) && (st.getQuestItemsCount(MEAT) >= 100) && (st.getQuestItemsCount(HORN) >= 100))
						{
							st.set("cond", "2");
							st.playSound("ItemSound.quest_middle");
						}
						else
						{
							st.playSound("ItemSound.quest_itemget");
						}
					}
					break;
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00623_TheFinestFood(623, Q00623_TheFinestFood.class.getSimpleName(), "The Finest Food");
	}
}