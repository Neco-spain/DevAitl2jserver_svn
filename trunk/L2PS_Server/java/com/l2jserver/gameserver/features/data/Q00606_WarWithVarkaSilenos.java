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
public class Q00606_WarWithVarkaSilenos extends Quest
{
	private final int KADUN = 31370;
	private final int[] VARKA_MOBS =
	{
		21350,
		21353,
		21354,
		21355,
		21357,
		21358,
		21360,
		21362,
		21364,
		21365,
		21366,
		21368,
		21369,
		21371,
		21373
	};
	private final int[] KETRA_ORCS =
	{
		21324,
		21325,
		21327,
		21328,
		21329,
		21331,
		21332,
		21334,
		21335,
		21336,
		21338,
		21339,
		21340,
		21342,
		21343,
		21344,
		21345,
		21346,
		21347,
		21348,
		21349
	};
	
	private final int[][] CHANCE =
	{
		{
			21350,
			500
		}, // Recruit
		{
			21353,
			510
		}, // Scout
		{
			21354,
			522
		}, // Hunter
		{
			21355,
			519
		}, // Shaman
		{
			21357,
			529
		}, // Priest
		{
			21358,
			529
		}, // Warrior
		{
			21360,
			539
		}, // Medium
		{
			21362,
			568
		}, // Officer
		{
			21364,
			558
		}, // Seer
		{
			21365,
			568
		}, // Great Magus
		{
			21366,
			664
		}, // General
		{
			21368,
			568
		}, // Great Seer
		{
			21369,
			548
		}, // Commander
		{
			21371,
			713
		}, // Head magus
		{
			21373,
			738
		}
	// Prophet
	};
	
	// Items
	private final int HORN = 7186;
	private final int MANE = 7233;
	
	public Q00606_WarWithVarkaSilenos(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(KADUN);
		addTalkId(KADUN);
		
		for (int element : VARKA_MOBS)
		{
			addKillId(element);
		}
		for (int element : KETRA_ORCS)
		{
			addKillId(element);
		}
		
		questItemIds = new int[]
		{
			MANE
		};
	}
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		long manes = st.getQuestItemsCount(MANE);
		
		if (event.equalsIgnoreCase("31370-03.htm"))
		{
			if ((player.getLevel() >= 74) && (player.getAllianceWithVarkaKetra() >= 1))
			{
				st.set("cond", "1");
				st.set("id", "1");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
				htmltext = "31370-03.htm";
			}
			else
			{
				htmltext = "31370-02.htm";
				st.exitQuest(true);
			}
		}
		else if (event.equalsIgnoreCase("31370-06.htm"))
		{
			htmltext = "31370-06.htm";
		}
		else if (event.equalsIgnoreCase("31370-07.htm"))
		{
			if (manes >= 100)
			{
				htmltext = "31370-07.htm";
				st.takeItems(MANE, 100);
				st.giveItems(HORN, 20);
			}
			else
			{
				htmltext = "31370-08.htm";
			}
		}
		else if (event.equalsIgnoreCase("31370-09.htm"))
		{
			htmltext = "31370-09.htm";
			st.unset("id");
			st.takeItems(MANE, -1);
			st.exitQuest(true);
		}
		return htmltext;
	}
	
	@Override
	public final String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		if (st != null)
		{
			int npcId = npc.getNpcId();
			int id = st.getInt("id");
			long manes = st.getQuestItemsCount(MANE);
			if (npcId == KADUN)
			{
				if (id == 1)
				{
					if (manes > 0)
					{
						htmltext = "31370-04.htm";
					}
					else
					{
						htmltext = "31370-05.htm";
					}
				}
			}
			else
			{
				htmltext = "31370-01.htm";
			}
		}
		return htmltext;
	}
	
	@Override
	public final String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		L2PcInstance partyMember = getRandomPartyMemberState(player, State.STARTED);
		if (partyMember == null)
		{
			return null;
		}
		
		final QuestState st = partyMember.getQuestState(getName());
		
		if (st != null)
		{
			if (st.getState() == State.STARTED)
			{
				int npcId = npc.getNpcId();
				long count = st.getQuestItemsCount(MANE);
				final QuestState st2 = partyMember.getQuestState("Q00611_AllianceWithVarkaSilenos");
				if (checkArray(VARKA_MOBS, npcId) && (partyMember.getAllianceWithVarkaKetra() >= 1))
				{
					if (st2 == null)
					{
						int chance = (int) (Config.RATE_QUEST_DROP * getValue(CHANCE, npcId));
						int numItems = chance / 100;
						
						if (st.getRandom(1000) < chance)
						{
							numItems++;
						}
						if (numItems != 0)
						{
							if (((count + numItems) / 100) > (count / 100))
							{
								st.playSound("ItemSound.quest_middle");
							}
							else
							{
								st.playSound("ItemSound.quest_itemget");
							}
							st.giveItems(MANE, numItems);
						}
					}
				}
				else if (checkArray(KETRA_ORCS, npcId))
				{
					st.unset("id");
					st.takeItems(MANE, -1);
					st.exitQuest(true);
				}
			}
		}
		return null;
	}
	
	private int getValue(int[][] array, int value)
	{
		for (int[] element : array)
		{
			if (element[0] == value)
			{
				return element[1];
			}
		}
		return 0;
	}
	
	private boolean checkArray(int[] array, int value)
	{
		for (int element : array)
		{
			if (element == value)
			{
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args)
	{
		new Q00606_WarWithVarkaSilenos(606, Q00606_WarWithVarkaSilenos.class.getSimpleName(), "War With Varka Silenos");
	}
}