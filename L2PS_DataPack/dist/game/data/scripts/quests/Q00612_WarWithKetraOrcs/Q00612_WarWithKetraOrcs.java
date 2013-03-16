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
package quests.Q00612_WarWithKetraOrcs;

import com.l2jserver.Config;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00612_WarWithKetraOrcs extends Quest
{
	private final int ASHAS = 31377;
	private final int[] VARKA_MOBS =
	{
		21350,
		21351,
		21353,
		21354,
		21355,
		21357,
		21358,
		21360,
		21361,
		21362,
		21369,
		21370,
		21364,
		21365,
		21366,
		21368,
		21371,
		21372,
		21373,
		21374,
		21375
	};
	private final int[] KETRA_ORCS =
	{
		21324,
		21327,
		21328,
		21329,
		21331,
		21332,
		21334,
		21336,
		21338,
		21339,
		21340,
		21342,
		21343,
		21345,
		21347
	};
	
	private final int[][] CHANCE =
	{
		{
			21324,
			500
		},
		{
			21327,
			510
		},
		{
			21328,
			522
		},
		{
			21329,
			519
		},
		{
			21331,
			529
		},
		{
			21332,
			664
		},
		{
			21334,
			539
		},
		{
			21336,
			529
		},
		{
			21338,
			558
		},
		{
			21339,
			568
		},
		{
			21340,
			568
		},
		{
			21342,
			578
		},
		{
			21343,
			548
		},
		{
			21345,
			713
		},
		{
			21347,
			738
		}
	};
	
	// Items
	private final int SEED = 7187;
	private final int MOLAR = 7234;
	
	public Q00612_WarWithKetraOrcs(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(ASHAS);
		addTalkId(ASHAS);
		
		for (int element : KETRA_ORCS)
		{
			addKillId(element);
		}
		
		for (int element : VARKA_MOBS)
		{
			addKillId(element);
		}
		
		questItemIds = new int[]
		{
			MOLAR
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
		
		long molars = st.getQuestItemsCount(MOLAR);
		
		if (event.equalsIgnoreCase("31377-03.htm"))
		{
			if ((player.getLevel() >= 74) && (player.getAllianceWithVarkaKetra() <= -1))
			{
				st.set("cond", "1");
				st.set("id", "1");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
				htmltext = "31377-03.htm";
			}
			else
			{
				htmltext = "31377-02.htm";
				st.exitQuest(true);
			}
		}
		else if (event.equalsIgnoreCase("31377-06.htm"))
		{
			htmltext = "31377-06.htm";
		}
		else if (event.equalsIgnoreCase("31377-07.htm"))
		{
			if (molars >= 100)
			{
				htmltext = "31377-07.htm";
				st.takeItems(MOLAR, 100);
				st.giveItems(SEED, 20);
			}
			else
			{
				htmltext = "31377-08.htm";
			}
		}
		else if (event.equalsIgnoreCase("31377-09.htm"))
		{
			htmltext = "31377-09.htm";
			st.unset("id");
			st.takeItems(MOLAR, -1);
			st.exitQuest(true);
		}
		return htmltext;
	}
	
	@Override
	public final String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		String htmltext = getNoQuestMsg(player);
		if (st != null)
		{
			int npcId = npc.getNpcId();
			int id = st.getInt("id");
			long molars = st.getQuestItemsCount(MOLAR);
			
			if ((npcId == ASHAS) && (player.getAllianceWithVarkaKetra() <= -1))
			{
				if (id == 1)
				{
					if (molars > 0)
					{
						htmltext = "31377-04.htm";
					}
					else
					{
						htmltext = "31377-05.htm";
					}
				}
				else
				{
					htmltext = "31377-01.htm";
				}
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
		if (st == null)
		{
			return null;
		}
		
		int npcId = npc.getNpcId();
		long count = st.getQuestItemsCount(MOLAR);
		final QuestState st2 = partyMember.getQuestState("Q00605_AllianceWithKetraOrcs");
		
		if (checkArray(KETRA_ORCS, npcId) && (partyMember.getAllianceWithVarkaKetra() <= -1))
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
					st.giveItems(MOLAR, numItems);
				}
			}
		}
		else if (checkArray(VARKA_MOBS, npcId))
		{
			st.unset("id");
			st.takeItems(MOLAR, -1);
			st.exitQuest(true);
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
		new Q00612_WarWithKetraOrcs(612, Q00612_WarWithKetraOrcs.class.getSimpleName(), "War With Ketra Orcs");
	}
}