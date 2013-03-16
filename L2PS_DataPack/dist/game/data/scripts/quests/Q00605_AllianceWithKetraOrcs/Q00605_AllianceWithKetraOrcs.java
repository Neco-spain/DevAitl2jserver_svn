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
package quests.Q00605_AllianceWithKetraOrcs;

import com.l2jserver.Config;
import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00605_AllianceWithKetraOrcs extends Quest
{
	private final int[][] CHANCE =
	{
		{
			21351,
			500
		}, // Footman
		{
			21366,
			628
		}, // General
		{
			21365,
			500
		}, // Great Magus
		{
			21368,
			508
		}, // Great Seer
		{
			21354,
			521
		}, // Hunter
		{
			21361,
			518
		}, // Magus
		{
			21360,
			509
		}, // Medium
		{
			21362,
			500
		}, // Officer
		{
			21357,
			500
		}, // Priest
		{
			21350,
			500
		}, // Recruit
		{
			21353,
			509
		}, // Scout
		{
			21364,
			527
		}, // Seer
		{
			21355,
			519
		}, // Shaman
		{
			21358,
			500
		}, // Warrior
		{
			21369,
			518
		}, // Commander
		{
			21370,
			604
		}, // Elite guard
		{
			21372,
			604
		}, // Head guard
		{
			21371,
			627
		}, // Head magus
		{
			21374,
			626
		}, // Prophet Guard
		{
			21375,
			626
		}, // Disciple of Prophet
		{
			21373,
			649
		}
	// Prophet
	};
	
	private final int[][] CHANCE_MANE =
	{
		{
			21366,
			664
		}, // General
		{
			21365,
			568
		}, // Great Magus
		{
			21368,
			568
		}, // Great Seer
		{
			21354,
			522
		}, // Hunter
		{
			21360,
			539
		}, // Medium
		{
			21362,
			568
		}, // Officer
		{
			21357,
			529
		}, // Priest
		{
			21350,
			500
		}, // Recruit
		{
			21353,
			510
		}, // Scout
		{
			21364,
			558
		}, // Seer
		{
			21355,
			519
		}, // Shaman
		{
			21358,
			529
		}, // Warrior
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
	
	private final int KETRA_ALLIANCE_FIVE = 7215;
	private final int KETRA_ALLIANCE_FOUR = 7214;
	private final int KETRA_ALLIANCE_ONE = 7211;
	
	private final int KETRA_ALLIANCE_THREE = 7213;
	
	private final int KETRA_ALLIANCE_TWO = 7212;
	
	// All Ketra Orc mobs
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
	private final int MANE = 7233;
	private final int VALOR_TOTEM = 7219;
	private final int VARKA_ALLIANCE_FIVE = 7225;
	private final int VARKA_ALLIANCE_FOUR = 7224;
	
	private final int VARKA_ALLIANCE_ONE = 7221;
	private final int VARKA_ALLIANCE_THREE = 7223;
	private final int VARKA_ALLIANCE_TWO = 7222;
	private final int VARKA_BADGE_CAPTAIN = 7218;
	private final int VARKA_BADGE_OFFICER = 7217;
	
	// Quest Items
	private final int VARKA_BADGE_SOLDIER = 7216;
	
	// Mobs for Alliance lvl 1:Varka Silenos- Recruit, Footman, Scout, Hunter, Shaman
	private final int[] VARKA_ONE =
	{
		21350,
		21351,
		21353,
		21354,
		21355
	};
	
	// Varka's - Head Magus, Head Guard, Prophet, Prophet Guard, and Disciple of Prophet
	private final int[] VARKA_THREE =
	{
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
	
	// Varka's- Commander, Elite Guard
	private final int[] VARKA_TWO =
	{
		21357,
		21358,
		21360,
		21361,
		21362,
		21369,
		21370,
	};
	
	// NPC
	private final int WAHKAN = 31371;
	
	private final int WISDOM_TOTEM = 7220;
	
	// drop system - cond:[item_id,max,drop_id]
	private final int[][] ONE =
	{
		{},
		{
			57,
			100,
			VARKA_BADGE_SOLDIER
		},
		{
			KETRA_ALLIANCE_ONE,
			200,
			VARKA_BADGE_SOLDIER
		},
		{
			KETRA_ALLIANCE_TWO,
			300,
			VARKA_BADGE_SOLDIER
		},
		{
			KETRA_ALLIANCE_THREE,
			300,
			VARKA_BADGE_SOLDIER
		},
		{
			KETRA_ALLIANCE_FOUR,
			400,
			VARKA_BADGE_SOLDIER
		}
	};
	
	private final int[][] TWO =
	{
		{},
		{},
		{
			KETRA_ALLIANCE_ONE,
			100,
			VARKA_BADGE_OFFICER
		},
		{
			KETRA_ALLIANCE_TWO,
			200,
			VARKA_BADGE_OFFICER
		},
		{
			KETRA_ALLIANCE_THREE,
			300,
			VARKA_BADGE_OFFICER
		},
		{
			KETRA_ALLIANCE_FOUR,
			400,
			VARKA_BADGE_OFFICER
		}
	};
	
	private final int[][] THREE =
	{
		{},
		{},
		{},
		{
			KETRA_ALLIANCE_TWO,
			100,
			VARKA_BADGE_CAPTAIN
		},
		{
			KETRA_ALLIANCE_THREE,
			200,
			VARKA_BADGE_CAPTAIN
		},
		{
			KETRA_ALLIANCE_FOUR,
			200,
			VARKA_BADGE_CAPTAIN
		}
	};
	
	public Q00605_AllianceWithKetraOrcs(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(WAHKAN);
		addTalkId(WAHKAN);
		
		for (int[] element : CHANCE)
		{
			addKillId(element[0]);
		}
		
		for (int element : KETRA_ORCS)
		{
			addKillId(element);
		}
		
		questItemIds = new int[]
		{
			VARKA_BADGE_SOLDIER,
			VARKA_BADGE_OFFICER,
			VARKA_BADGE_CAPTAIN
		};
	}
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		String htmltext = event;
		
		if (event.equalsIgnoreCase("31371-03a.htm"))
		{
			if (player.getLevel() >= 74)
			{
				st.set("cond", "1");
				st.set("id", "2");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
				htmltext = "31371-03a.htm";
			}
			else
			{
				htmltext = "31371-02b.htm";
				st.exitQuest(true);
				player.setAllianceWithVarkaKetra(0);
			}
		}
		else if (event.equalsIgnoreCase("31371-10-1.htm"))
		{
			htmltext = "31371-10-1.htm";
			st.set("id", "3");
			st.takeItems(VARKA_BADGE_SOLDIER, 100);
			st.giveItems(KETRA_ALLIANCE_ONE, 1);
			player.setAllianceWithVarkaKetra(1);
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31371-10-2.htm"))
		{
			htmltext = "31371-10-2.htm";
			st.set("id", "3");
			st.takeItems(VARKA_BADGE_SOLDIER, 200);
			st.takeItems(VARKA_BADGE_OFFICER, 100);
			st.takeItems(KETRA_ALLIANCE_ONE, -1);
			st.giveItems(KETRA_ALLIANCE_TWO, 1);
			player.setAllianceWithVarkaKetra(2);
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31371-10-3.htm"))
		{
			htmltext = "31371-10-3.htm";
			st.set("id", "3");
			st.takeItems(VARKA_BADGE_SOLDIER, 300);
			st.takeItems(VARKA_BADGE_OFFICER, 200);
			st.takeItems(VARKA_BADGE_CAPTAIN, 100);
			st.takeItems(KETRA_ALLIANCE_TWO, -1);
			st.giveItems(KETRA_ALLIANCE_THREE, 1);
			player.setAllianceWithVarkaKetra(3);
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31371-10-4.htm"))
		{
			htmltext = "31371-10-4.htm";
			st.set("id", "3");
			st.takeItems(VARKA_BADGE_SOLDIER, 300);
			st.takeItems(VARKA_BADGE_OFFICER, 300);
			st.takeItems(VARKA_BADGE_CAPTAIN, 200);
			st.takeItems(KETRA_ALLIANCE_THREE, -1);
			st.takeItems(VALOR_TOTEM, -1);
			st.giveItems(KETRA_ALLIANCE_FOUR, 1);
			player.setAllianceWithVarkaKetra(4);
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31371-11a.htm"))
		{
			htmltext = "31371-11a.htm";
		}
		else if (event.equalsIgnoreCase("31371-19.htm"))
		{
			htmltext = "31371-19.htm";
		}
		else if (event.equalsIgnoreCase("31371-11b.htm"))
		{
			htmltext = "31371-11b.htm";
		}
		else if (event.equalsIgnoreCase("31371-20.htm"))
		{
			htmltext = "31371-20.htm";
			st.takeItems(VARKA_BADGE_SOLDIER, -1);
			st.takeItems(VARKA_BADGE_OFFICER, -1);
			st.takeItems(VARKA_BADGE_CAPTAIN, -1);
			st.takeItems(KETRA_ALLIANCE_ONE, -1);
			st.takeItems(KETRA_ALLIANCE_TWO, -1);
			st.takeItems(KETRA_ALLIANCE_THREE, -1);
			st.takeItems(KETRA_ALLIANCE_FOUR, -1);
			st.takeItems(KETRA_ALLIANCE_FIVE, -1);
			st.takeItems(VALOR_TOTEM, -1);
			st.takeItems(WISDOM_TOTEM, -1);
			player.setAllianceWithVarkaKetra(0);
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
			int cond = st.getInt("cond");
			long VBadgeS = st.getQuestItemsCount(VARKA_BADGE_SOLDIER);
			long VBadgeO = st.getQuestItemsCount(VARKA_BADGE_OFFICER);
			long VBadgeC = st.getQuestItemsCount(VARKA_BADGE_CAPTAIN);
			long KAlliance1 = st.getQuestItemsCount(KETRA_ALLIANCE_ONE);
			long KAlliance2 = st.getQuestItemsCount(KETRA_ALLIANCE_TWO);
			long KAlliance3 = st.getQuestItemsCount(KETRA_ALLIANCE_THREE);
			long KAlliance4 = st.getQuestItemsCount(KETRA_ALLIANCE_FOUR);
			long KAlliance5 = st.getQuestItemsCount(KETRA_ALLIANCE_FIVE);
			long KAlliance = KAlliance1 + KAlliance2 + KAlliance3 + KAlliance4 + KAlliance5;
			long VAlliance = st.getQuestItemsCount(VARKA_ALLIANCE_ONE) + st.getQuestItemsCount(VARKA_ALLIANCE_TWO) + st.getQuestItemsCount(VARKA_ALLIANCE_THREE) + st.getQuestItemsCount(VARKA_ALLIANCE_FOUR) + st.getQuestItemsCount(VARKA_ALLIANCE_FIVE);
			long Valor = st.getQuestItemsCount(VALOR_TOTEM);
			long Wisdom = st.getQuestItemsCount(WISDOM_TOTEM);
			if (npcId == WAHKAN)
			{
				st.set("id", "1");
				if (player.isAlliedWithVarka() || (VAlliance > 0))
				{
					htmltext = "31371-02a.htm";
					st.exitQuest(true);
				}
				else if (KAlliance == 0)
				{
					if (cond != 1)
					{
						htmltext = "31371-01.htm";
					}
					else
					{
						st.set("id", "2");
						if (VBadgeS < 100)
						{
							htmltext = "31371-03b.htm";
						}
						else if (VBadgeS >= 100)
						{
							htmltext = "31371-09.htm";
						}
					}
				}
				else if (KAlliance > 0)
				{
					st.setState(State.STARTED);
					st.set("id", "2");
					if (KAlliance1 > 0)
					{
						if (cond != 2)
						{
							htmltext = "31371-04.htm";
							st.set("cond", "2");
							player.setAllianceWithVarkaKetra(1);
						}
						else
						{
							if ((VBadgeS < 200) || (VBadgeO < 100))
							{
								htmltext = "31371-12.htm";
							}
							else if ((VBadgeS >= 200) && (VBadgeO >= 100))
							{
								htmltext = "31371-13.htm";
							}
						}
					}
					else if (KAlliance2 > 0)
					{
						if (cond != 3)
						{
							htmltext = "31371-05.htm";
							st.set("cond", "3");
							player.setAllianceWithVarkaKetra(2);
						}
						else
						{
							if ((VBadgeS < 300) || (VBadgeO < 200) || (VBadgeC < 100))
							{
								htmltext = "31371-15.htm";
							}
							else if ((VBadgeS >= 300) && (VBadgeO >= 200) && (VBadgeC >= 100))
							{
								htmltext = "31371-16.htm";
							}
						}
					}
					else if (KAlliance3 > 0)
					{
						if (cond != 4)
						{
							htmltext = "31371-06.htm";
							st.set("cond", "4");
							player.setAllianceWithVarkaKetra(3);
						}
						else
						{
							if ((VBadgeS < 300) || (VBadgeO < 300) || (VBadgeC < 200) || (Valor == 0))
							{
								htmltext = "31371-21.htm";
							}
							else if ((VBadgeS >= 300) && (VBadgeO >= 300) && (VBadgeC >= 200) && (Valor > 0))
							{
								htmltext = "31371-22.htm";
							}
						}
					}
					else if (KAlliance4 > 0)
					{
						if (cond != 5)
						{
							htmltext = "31371-07.htm";
							st.set("cond", "5");
							player.setAllianceWithVarkaKetra(4);
						}
						else
						{
							if ((VBadgeS < 400) || (VBadgeO < 400) || (VBadgeC < 200) || (Wisdom == 0))
							{
								htmltext = "31371-17.htm";
							}
							else if ((VBadgeS >= 400) && (VBadgeO >= 400) && (VBadgeC >= 200) && (Wisdom > 0))
							{
								htmltext = "31371-10-5.htm";
								st.takeItems(VARKA_BADGE_SOLDIER, 400);
								st.takeItems(VARKA_BADGE_OFFICER, 400);
								st.takeItems(VARKA_BADGE_CAPTAIN, 200);
								st.takeItems(KETRA_ALLIANCE_FOUR, -1);
								st.takeItems(WISDOM_TOTEM, -1);
								st.giveItems(KETRA_ALLIANCE_FIVE, 1);
								player.setAllianceWithVarkaKetra(5);
								st.set("id", "3");
								st.playSound("ItemSound.quest_middle");
							}
						}
					}
					else if (KAlliance5 > 0)
					{
						if (cond != 6)
						{
							htmltext = "31371-18.htm";
							st.set("cond", "6");
							player.setAllianceWithVarkaKetra(5);
						}
						else
						{
							htmltext = "31371-08.htm";
						}
					}
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
		
		final QuestState st = player.getQuestState(getName());
		
		if (st != null)
		{
			if (st.getState() == State.STARTED)
			{
				int npcId = npc.getNpcId();
				int cond = st.getInt("cond");
				int id = st.getInt("id");
				final QuestState st2 = partyMember.getQuestState("Q00606_WarWithVarkaSilenos");
				if (!partyMember.isAlliedWithVarka())
				{
					if (checkArray(VARKA_ONE, npcId) || checkArray(VARKA_TWO, npcId) || checkArray(VARKA_THREE, npcId))
					{
						int item = 0;
						int max = 0;
						int drop = 0;
						if (cond <= 5)
						{
							if (checkArray(VARKA_ONE, npcId))
							{
								item = ONE[cond][0];
								max = ONE[cond][1];
								drop = ONE[cond][2];
							}
							else if (checkArray(VARKA_TWO, npcId) && (cond > 1))
							{
								item = TWO[cond][0];
								max = TWO[cond][1];
								drop = TWO[cond][2];
							}
							else if (checkArray(VARKA_THREE, npcId) && (cond > 2))
							{
								item = THREE[cond][0];
								max = THREE[cond][1];
								drop = THREE[cond][2];
							}
						}
						if (item != 0)
						{
							if (st.getQuestItemsCount(drop) == max)
							{
								item = 0;
							}
						}
						
						int chance = getValue(CHANCE, npcId);
						
						if (st2 != null)
						{
							if (((st.getRandom(2) == 1) || (item == 0)) && checkArray(CHANCE_MANE, npcId))
							{
								item = 57;
								max = 100;
								drop = MANE;
								chance = getValue(CHANCE_MANE, npcId);
								giveItems(st, item, chance, max, drop);
							}
							else if ((id == 2) && (item != 0))
							{
								giveItems(st, item, chance, max, drop);
							}
						}
						else if ((id == 2) && (item != 0))
						{
							giveItems(st, item, chance, max, drop);
						}
					}
					else if (checkArray(KETRA_ORCS, npcId))
					{
						L2Party party = partyMember.getParty();
						if (party != null)
						{
							for (L2PcInstance member : party.getMembers())
							{
								if (Util.checkIfInRange(5000, player, member, true))
								{
									QuestState pst = member.getQuestState(getName());
									if (pst != null)
									{
										decreaseAlliance(pst);
									}
								}
							}
						}
						else
						{
							decreaseAlliance(st);
						}
					}
				}
			}
		}
		return null;
		
	}
	
	private void decreaseAlliance(QuestState st)
	{
		if (st.getPlayer().isAlliedWithKetra())
		{
			int cond = st.getInt("cond");
			int id = st.getInt("id");
			st.takeItems(VARKA_BADGE_SOLDIER, -1);
			st.takeItems(VARKA_BADGE_OFFICER, -1);
			st.takeItems(VARKA_BADGE_CAPTAIN, -1);
			st.takeItems(VALOR_TOTEM, -1);
			st.takeItems(WISDOM_TOTEM, -1);
			st.getPlayer().setAllianceWithVarkaKetra(0);
			st.exitQuest(true);
			if (cond == 2)
			{
				if (id == 2)
				{
					st.takeItems(KETRA_ALLIANCE_ONE, -1);
				}
				else
				{
					st.takeItems(KETRA_ALLIANCE_TWO, -1);
					st.giveItems(KETRA_ALLIANCE_ONE, 1);
				}
			}
			else if (cond == 3)
			{
				if (id == 2)
				{
					st.takeItems(KETRA_ALLIANCE_TWO, -1);
					st.giveItems(KETRA_ALLIANCE_ONE, 1);
				}
				else
				{
					st.takeItems(KETRA_ALLIANCE_THREE, -1);
					st.giveItems(KETRA_ALLIANCE_ONE, 1);
				}
			}
			else if (cond == 4)
			{
				if (id == 2)
				{
					st.takeItems(KETRA_ALLIANCE_THREE, -1);
					st.giveItems(KETRA_ALLIANCE_ONE, 1);
				}
				else
				{
					st.takeItems(KETRA_ALLIANCE_FOUR, -1);
					st.giveItems(KETRA_ALLIANCE_THREE, 1);
				}
			}
			else if (cond == 5)
			{
				if (id == 2)
				{
					st.takeItems(KETRA_ALLIANCE_FOUR, -1);
					st.giveItems(KETRA_ALLIANCE_THREE, 1);
				}
				else
				{
					st.takeItems(KETRA_ALLIANCE_FIVE, -1);
					st.giveItems(KETRA_ALLIANCE_FOUR, 1);
				}
			}
			else if (cond == 6)
			{
				st.takeItems(KETRA_ALLIANCE_FIVE, -1);
				st.giveItems(KETRA_ALLIANCE_FOUR, 1);
			}
		}
	}
	
	private void giveItems(QuestState st, int item, int chance, int max, int drop)
	{
		if (st.getQuestItemsCount(item) > 0)
		{
			long count = st.getQuestItemsCount(drop);
			if ((count < max) || (drop == MANE))
			{
				chance = (int) (Config.RATE_QUEST_DROP * chance);
				int numItems = chance / 100;
				if (st.getRandom(1000) < chance)
				{
					numItems++;
				}
				if (numItems > 0)
				{
					if (((count + numItems) >= max) && (drop != MANE))
					{
						numItems = (int) (max - count);
						st.playSound("ItemSound.quest_middle");
					}
					else if ((drop == MANE) && (((count + numItems) / 100) > (count / 100)))
					{
						st.playSound("ItemSound.quest_middle");
					}
					else
					{
						st.playSound("ItemSound.quest_itemget");
					}
					st.giveItems(drop, numItems);
				}
			}
		}
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
	
	private boolean checkArray(int[][] array, int value)
	{
		for (int[] element : array)
		{
			if (element[0] == value)
			{
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args)
	{
		new Q00605_AllianceWithKetraOrcs(605, Q00605_AllianceWithKetraOrcs.class.getSimpleName(), "Alliance With Ketra Orcs");
	}
}
