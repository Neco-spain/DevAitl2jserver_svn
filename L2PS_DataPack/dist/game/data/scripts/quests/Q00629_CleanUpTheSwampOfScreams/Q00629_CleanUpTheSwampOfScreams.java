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
package quests.Q00629_CleanUpTheSwampOfScreams;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00629_CleanUpTheSwampOfScreams extends Quest
{
	private static final int CAPTAIN = 31553;
	private static final int CLAWS = 7250;
	private static final int COIN = 7251;
	
	private static final int[][] CHANCE =
	{
		{
			21508,
			500000
		},
		{
			21509,
			430000
		},
		{
			21510,
			520000
		},
		{
			21511,
			570000
		},
		{
			21512,
			740000
		},
		{
			21513,
			530000
		},
		{
			21514,
			530000
		},
		{
			21515,
			540000
		},
		{
			21516,
			550000
		},
		{
			21517,
			560000
		}
	};
	
	public Q00629_CleanUpTheSwampOfScreams(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(CAPTAIN);
		addTalkId(CAPTAIN);
		
		for (int[] i : CHANCE)
		{
			addKillId(i[0]);
		}
		
		questItemIds = new int[]
		{
			CLAWS,
			COIN
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
		
		if (event.equalsIgnoreCase("31553-1.htm"))
		{
			if (player.getLevel() >= 66)
			{
				st.set("cond", "1");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
			}
			else
			{
				htmltext = "31553-0a.htm";
				st.exitQuest(true);
			}
		}
		else if (event.equalsIgnoreCase("31553-3.htm"))
		{
			if (st.getQuestItemsCount(CLAWS) >= 100)
			{
				st.takeItems(CLAWS, 100);
				st.giveItems(COIN, 20);
			}
			else
			{
				htmltext = "31553-3a.htm";
			}
		}
		else if (event.equalsIgnoreCase("31553-5.htm"))
		{
			st.playSound("ItemSound.quest_finish");
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
		
		if ((st.hasQuestItems(7246)) || (st.hasQuestItems(7247)))
		{
			switch (st.getState())
			{
				case State.CREATED:
					if (player.getLevel() >= 66)
					{
						htmltext = "31553-0.htm";
					}
					else
					{
						htmltext = "31553-0a.htm";
						st.exitQuest(true);
					}
					break;
				case State.STARTED:
					if (st.getQuestItemsCount(CLAWS) >= 100)
					{
						htmltext = "31553-2.htm";
					}
					else
					{
						htmltext = "31553-1a.htm";
					}
					break;
			}
		}
		else
		{
			htmltext = "31553-6.htm";
			st.exitQuest(true);
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		L2PcInstance partyMember = getRandomPartyMemberState(player, State.STARTED);
		if (partyMember == null)
		{
			return null;
		}
		
		QuestState st = partyMember.getQuestState(getName());
		
		st.dropItems(CLAWS, 1, 100, CHANCE[(npc.getNpcId() - 21508)][1]);
		
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00629_CleanUpTheSwampOfScreams(629, Q00629_CleanUpTheSwampOfScreams.class.getSimpleName(), "Clean Up The Swamp Of Screams");
	}
}