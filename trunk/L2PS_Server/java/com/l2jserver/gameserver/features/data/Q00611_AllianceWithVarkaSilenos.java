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
public class Q00611_AllianceWithVarkaSilenos extends Quest
{
	private final int NARAN_ASHANUK = 31378;
	private final int[] KETRA_ONE =
	{
		21324,
		21325,
		21327,
		21328,
		21329
	};
	
	// White Captains, Seers, Commanders, Elite Guards
	private final int[] KETRA_TWO =
	{
		21331,
		21332,
		21334,
		21335,
		21336,
		21338,
		21343,
		21344
	};
	
	// Ketra's - Head Shaman, Head Guard, Prophet, Prophet's Guard, and Prophet's Aide
	private final int[] KETRA_THREE =
	{
		21339,
		21340,
		21342,
		21345,
		21346,
		21347,
		21348,
		21349
	};
	
	// All Varka Silenos mobs
	private final int[] VARKA_SILENOS =
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
	
	private final int[][] CHANCE =
	{
		{
			21325,
			500
		},
		{
			21339,
			500
		},
		{
			21340,
			500
		},
		{
			21324,
			500
		},
		{
			21336,
			500
		},
		{
			21331,
			500
		},
		{
			21342,
			508
		},
		{
			21327,
			509
		},
		{
			21334,
			509
		},
		{
			21335,
			518
		},
		{
			21343,
			518
		},
		{
			21329,
			519
		},
		{
			21328,
			521
		},
		{
			21338,
			527
		},
		{
			21344,
			604
		},
		{
			21346,
			604
		},
		{
			21348,
			626
		},
		{
			21349,
			626
		},
		{
			21345,
			627
		},
		{
			21332,
			628
		},
		{
			21347,
			649
		}
	};
	
	private final int[][] CHANCE_MOLAR =
	{
		{
			21339,
			568
		},
		{
			21340,
			568
		},
		{
			21324,
			500
		},
		{
			21336,
			529
		},
		{
			21331,
			529
		},
		{
			21342,
			578
		},
		{
			21327,
			510
		},
		{
			21334,
			539
		},
		{
			21343,
			548
		},
		{
			21329,
			519
		},
		{
			21328,
			522
		},
		{
			21338,
			558
		},
		{
			21345,
			713
		},
		{
			21332,
			664
		},
		{
			21347,
			638
		}
	};
	
	// Quest Items
	private final int KETRA_ALLIANCE_ONE = 7211;
	private final int KETRA_ALLIANCE_TWO = 7212;
	private final int KETRA_ALLIANCE_THREE = 7213;
	private final int KETRA_ALLIANCE_FOUR = 7214;
	private final int KETRA_ALLIANCE_FIVE = 7215;
	
	private final int VARKA_ALLIANCE_ONE = 7221;
	private final int VARKA_ALLIANCE_TWO = 7222;
	private final int VARKA_ALLIANCE_THREE = 7223;
	private final int VARKA_ALLIANCE_FOUR = 7224;
	private final int VARKA_ALLIANCE_FIVE = 7225;
	
	private final int KETRA_BADGE_SOLDIER = 7226;
	private final int KETRA_BADGE_OFFICER = 7227;
	private final int KETRA_BADGE_CAPTAIN = 7228;
	
	private final int VALOR_FEATHER = 7229;
	private final int WISDOM_FEATHER = 7230;
	
	private final int MOLAR = 7234;
	
	private final int[][] ONE =
	{
		{},
		{
			57,
			100,
			KETRA_BADGE_SOLDIER
		},
		{
			VARKA_ALLIANCE_ONE,
			200,
			KETRA_BADGE_SOLDIER
		},
		{
			VARKA_ALLIANCE_TWO,
			300,
			KETRA_BADGE_SOLDIER
		},
		{
			VARKA_ALLIANCE_THREE,
			300,
			KETRA_BADGE_SOLDIER
		},
		{
			VARKA_ALLIANCE_FOUR,
			400,
			KETRA_BADGE_SOLDIER
		}
	};
	
	private final int[][] TWO =
	{
		{},
		{},
		{
			VARKA_ALLIANCE_ONE,
			100,
			KETRA_BADGE_OFFICER
		},
		{
			VARKA_ALLIANCE_TWO,
			200,
			KETRA_BADGE_OFFICER
		},
		{
			VARKA_ALLIANCE_THREE,
			300,
			KETRA_BADGE_OFFICER
		},
		{
			VARKA_ALLIANCE_FOUR,
			400,
			KETRA_BADGE_OFFICER
		}
	};
	private final int[][] THREE =
	{
		{},
		{},
		{},
		{
			VARKA_ALLIANCE_TWO,
			100,
			KETRA_BADGE_CAPTAIN
		},
		{
			VARKA_ALLIANCE_THREE,
			200,
			KETRA_BADGE_CAPTAIN
		},
		{
			VARKA_ALLIANCE_FOUR,
			200,
			KETRA_BADGE_CAPTAIN
		}
	};
	
	public Q00611_AllianceWithVarkaSilenos(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(NARAN_ASHANUK);
		addTalkId(NARAN_ASHANUK);
		
		for (int[] element : CHANCE)
		{
			addKillId(element[0]);
		}
		
		for (int element : VARKA_SILENOS)
		{
			addKillId(element);
		}
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
		
		if (event.equalsIgnoreCase("31378-03a.htm"))
		{
			if (player.getLevel() >= 74)
			{
				st.set("cond", "1");
				st.set("id", "2");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
				htmltext = "31378-03a.htm";
			}
			else
			{
				htmltext = "31378-02b.htm";
				player.setAllianceWithVarkaKetra(0);
				st.exitQuest(true);
			}
		}
		else if (event.equalsIgnoreCase("31378-10-1.htm"))
		{
			htmltext = "31378-10-1.htm";
			st.set("id", "3");
			st.takeItems(KETRA_BADGE_SOLDIER, 100);
			st.giveItems(VARKA_ALLIANCE_ONE, 1);
			player.setAllianceWithVarkaKetra(-1);
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31378-10-2.htm"))
		{
			htmltext = "31378-10-2.htm";
			st.set("id", "3");
			st.takeItems(KETRA_BADGE_SOLDIER, 200);
			st.takeItems(KETRA_BADGE_OFFICER, 100);
			st.takeItems(VARKA_ALLIANCE_ONE, -1);
			st.giveItems(VARKA_ALLIANCE_TWO, 1);
			player.setAllianceWithVarkaKetra(-2);
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31378-10-3.htm"))
		{
			htmltext = "31378-10-3.htm";
			st.set("id", "3");
			st.takeItems(KETRA_BADGE_SOLDIER, 300);
			st.takeItems(KETRA_BADGE_OFFICER, 200);
			st.takeItems(KETRA_BADGE_CAPTAIN, 100);
			st.takeItems(VARKA_ALLIANCE_TWO, -1);
			st.giveItems(VARKA_ALLIANCE_THREE, 1);
			player.setAllianceWithVarkaKetra(-3);
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31378-10-4.htm"))
		{
			htmltext = "31378-10-4.htm";
			st.set("id", "3");
			st.takeItems(KETRA_BADGE_SOLDIER, 300);
			st.takeItems(KETRA_BADGE_OFFICER, 300);
			st.takeItems(KETRA_BADGE_CAPTAIN, 200);
			st.takeItems(VARKA_ALLIANCE_THREE, -1);
			st.takeItems(VALOR_FEATHER, -1);
			st.giveItems(VARKA_ALLIANCE_FOUR, 1);
			player.setAllianceWithVarkaKetra(-4);
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31378-11a.htm"))
		{
			htmltext = "31378-11a.htm";
		}
		else if (event.equalsIgnoreCase("31378-19.htm"))
		{
			htmltext = "31378-19.htm";
		}
		else if (event.equalsIgnoreCase("31378-11b.htm"))
		{
			htmltext = "31378-11b.htm";
		}
		else if (event.equalsIgnoreCase("31378-20.htm"))
		{
			htmltext = "31378-20.htm";
			st.takeItems(KETRA_BADGE_SOLDIER, -1);
			st.takeItems(KETRA_BADGE_OFFICER, -1);
			st.takeItems(KETRA_BADGE_CAPTAIN, -1);
			st.takeItems(VARKA_ALLIANCE_ONE, -1);
			st.takeItems(VARKA_ALLIANCE_TWO, -1);
			st.takeItems(VARKA_ALLIANCE_THREE, -1);
			st.takeItems(VARKA_ALLIANCE_FOUR, -1);
			st.takeItems(VARKA_ALLIANCE_FIVE, -1);
			st.takeItems(VALOR_FEATHER, -1);
			st.takeItems(WISDOM_FEATHER, -1);
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
			long KBadgeS = st.getQuestItemsCount(KETRA_BADGE_SOLDIER);
			long KBadgeO = st.getQuestItemsCount(KETRA_BADGE_OFFICER);
			long KBadgeC = st.getQuestItemsCount(KETRA_BADGE_CAPTAIN);
			long VAlliance1 = st.getQuestItemsCount(VARKA_ALLIANCE_ONE);
			long VAlliance2 = st.getQuestItemsCount(VARKA_ALLIANCE_TWO);
			long VAlliance3 = st.getQuestItemsCount(VARKA_ALLIANCE_THREE);
			long VAlliance4 = st.getQuestItemsCount(VARKA_ALLIANCE_FOUR);
			long VAlliance5 = st.getQuestItemsCount(VARKA_ALLIANCE_FIVE);
			long VAlliance = VAlliance1 + VAlliance2 + VAlliance3 + VAlliance4 + VAlliance5;
			long KAlliance = st.getQuestItemsCount(KETRA_ALLIANCE_ONE) + st.getQuestItemsCount(KETRA_ALLIANCE_TWO) + st.getQuestItemsCount(KETRA_ALLIANCE_THREE) + st.getQuestItemsCount(KETRA_ALLIANCE_FOUR) + st.getQuestItemsCount(KETRA_ALLIANCE_FIVE);
			long Valor = st.getQuestItemsCount(VALOR_FEATHER);
			long Wisdom = st.getQuestItemsCount(WISDOM_FEATHER);
			if (npcId == NARAN_ASHANUK)
			{
				st.set("id", "1");
				if (player.isAlliedWithKetra() || (KAlliance > 0))
				{
					htmltext = "31378-02a.htm";
					st.exitQuest(true);
				}
				else if (VAlliance == 0)
				{
					if (cond != 1)
					{
						htmltext = "31378-01.htm";
					}
					else
					{
						st.set("id", "2");
						if (KBadgeS < 100)
						{
							htmltext = "31378-03b.htm";
						}
						else if (KBadgeS >= 100)
						{
							htmltext = "31378-09.htm";
						}
					}
				}
				else if (VAlliance > 0)
				{
					st.setState(State.STARTED);
					st.set("id", "2");
					if (VAlliance1 > 0)
					{
						if (cond != 2)
						{
							htmltext = "31378-04.htm";
							st.set("cond", "2");
							player.setAllianceWithVarkaKetra(-1);
						}
						else
						{
							if ((KBadgeS < 200) || (KBadgeO < 100))
							{
								htmltext = "31378-12.htm";
							}
							else if ((KBadgeS >= 200) && (KBadgeO >= 100))
							{
								htmltext = "31378-13.htm";
							}
						}
					}
					else if (VAlliance2 > 0)
					{
						if (cond != 3)
						{
							htmltext = "31378-05.htm";
							st.set("cond", "3");
							player.setAllianceWithVarkaKetra(-2);
						}
						else
						{
							if ((KBadgeS < 300) || (KBadgeO < 200) || (KBadgeC < 100))
							{
								htmltext = "31378-15.htm";
							}
							else if ((KBadgeS >= 300) && (KBadgeO >= 200) && (KBadgeC >= 100))
							{
								htmltext = "31378-16.htm";
							}
						}
					}
					else if (VAlliance3 > 0)
					{
						if (cond != 4)
						{
							htmltext = "31378-06.htm";
							st.set("cond", "4");
							player.setAllianceWithVarkaKetra(-3);
						}
						else
						{
							if ((KBadgeS < 300) || (KBadgeO < 300) || (KBadgeC < 200) || (Valor == 0))
							{
								htmltext = "31378-21.htm";
							}
							else if ((KBadgeS >= 300) && (KBadgeO >= 300) && (KBadgeC >= 200) && (Valor > 0))
							{
								htmltext = "31378-22.htm";
							}
						}
					}
					else if (VAlliance4 > 0)
					{
						if (cond != 5)
						{
							htmltext = "31378-07.htm";
							st.set("cond", "5");
							player.setAllianceWithVarkaKetra(-4);
						}
						else
						{
							if ((KBadgeS < 400) || (KBadgeO < 400) || (KBadgeC < 200) || (Wisdom == 0))
							{
								htmltext = "31378-17.htm";
							}
							else if ((KBadgeS >= 400) && (KBadgeO >= 400) && (KBadgeC >= 200) && (Wisdom > 0))
							{
								htmltext = "31378-10-5.htm";
								st.takeItems(KETRA_BADGE_SOLDIER, 400);
								st.takeItems(KETRA_BADGE_OFFICER, 400);
								st.takeItems(KETRA_BADGE_CAPTAIN, 200);
								st.takeItems(VARKA_ALLIANCE_FOUR, -1);
								st.takeItems(WISDOM_FEATHER, -1);
								st.giveItems(VARKA_ALLIANCE_FIVE, 1);
								player.setAllianceWithVarkaKetra(-5);
								st.set("id", "3");
								st.playSound("ItemSound.quest_middle");
							}
						}
					}
					else if (VAlliance5 > 0)
					{
						if (cond != 6)
						{
							htmltext = "31378-18.htm";
							st.set("cond", "6");
							player.setAllianceWithVarkaKetra(-5);
						}
						else
						{
							htmltext = "31378-08.htm";
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
		
		final QuestState st = partyMember.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		int id = st.getInt("id");
		final QuestState st2 = partyMember.getQuestState("Q00612_WarWithKetraOrcs");
		
		if (!partyMember.isAlliedWithKetra())
		{
			if (checkArray(KETRA_ONE, npcId) || checkArray(KETRA_TWO, npcId) || checkArray(KETRA_THREE, npcId))
			{
				int item = 0;
				int max = 0;
				int drop = 0;
				if (cond <= 5)
				{
					if (checkArray(KETRA_ONE, npcId))
					{
						item = ONE[cond][0];
						max = ONE[cond][1];
						drop = ONE[cond][2];
					}
					else if (checkArray(KETRA_TWO, npcId) && (cond > 1))
					{
						item = TWO[cond][0];
						max = TWO[cond][1];
						drop = TWO[cond][2];
					}
					else if (checkArray(KETRA_THREE, npcId) && (cond > 2))
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
					if (((st.getRandom(2) == 1) || (item == 0)) && checkArray(CHANCE_MOLAR, npcId))
					{
						item = 57;
						max = 100;
						drop = MOLAR;
						chance = getValue(CHANCE_MOLAR, npcId);
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
			else if (checkArray(VARKA_SILENOS, npcId))
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
		return null;
	}
	
	private void decreaseAlliance(QuestState st)
	{
		if (st.getPlayer().isAlliedWithVarka())
		{
			int cond = st.getInt("cond");
			int id = st.getInt("id");
			st.takeItems(KETRA_BADGE_SOLDIER, -1);
			st.takeItems(KETRA_BADGE_OFFICER, -1);
			st.takeItems(KETRA_BADGE_CAPTAIN, -1);
			st.takeItems(VALOR_FEATHER, -1);
			st.takeItems(WISDOM_FEATHER, -1);
			st.getPlayer().setAllianceWithVarkaKetra(0);
			st.exitQuest(true);
			if (cond == 2)
			{
				if (id == 2)
				{
					st.takeItems(VARKA_ALLIANCE_ONE, -1);
				}
				else
				{
					st.takeItems(VARKA_ALLIANCE_TWO, -1);
					st.giveItems(VARKA_ALLIANCE_ONE, 1);
				}
			}
			else if (cond == 3)
			{
				if (id == 2)
				{
					st.takeItems(VARKA_ALLIANCE_TWO, -1);
					st.giveItems(VARKA_ALLIANCE_ONE, 1);
				}
				else
				{
					st.takeItems(VARKA_ALLIANCE_THREE, -1);
					st.giveItems(VARKA_ALLIANCE_TWO, 1);
				}
			}
			else if (cond == 4)
			{
				if (id == 2)
				{
					st.takeItems(VARKA_ALLIANCE_THREE, -1);
					st.giveItems(VARKA_ALLIANCE_TWO, 1);
				}
				else
				{
					st.takeItems(VARKA_ALLIANCE_FOUR, -1);
					st.giveItems(VARKA_ALLIANCE_THREE, 1);
				}
			}
			else if (cond == 5)
			{
				if (id == 2)
				{
					st.takeItems(VARKA_ALLIANCE_FOUR, -1);
					st.giveItems(VARKA_ALLIANCE_THREE, 1);
				}
				else
				{
					st.takeItems(VARKA_ALLIANCE_FIVE, -1);
					st.giveItems(VARKA_ALLIANCE_FOUR, 1);
				}
			}
			else if (cond == 6)
			{
				st.takeItems(VARKA_ALLIANCE_FIVE, -1);
				st.giveItems(VARKA_ALLIANCE_FOUR, 1);
			}
		}
	}
	
	private void giveItems(QuestState st, int item, int chance, int max, int drop)
	{
		if (st.getQuestItemsCount(item) > 0)
		{
			long count = st.getQuestItemsCount(drop);
			if ((count < max) || (drop == MOLAR))
			{
				chance = (int) (Config.RATE_QUEST_DROP * chance);
				int numItems = chance / 100;
				if (st.getRandom(1000) < chance)
				{
					numItems++;
				}
				if (numItems != 0)
				{
					if (((count + numItems) >= max) && (drop != MOLAR))
					{
						numItems = (int) (max - count);
						st.playSound("ItemSound.quest_middle");
					}
					else if ((drop == MOLAR) && (((count + numItems) / 100) > (count / 100)))
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
		new Q00611_AllianceWithVarkaSilenos(611, Q00611_AllianceWithVarkaSilenos.class.getSimpleName(), "Alliance With Varka Silenos");
	}
}