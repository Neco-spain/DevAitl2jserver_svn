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
public class Q00409_PathToOracle extends Quest
{
	private static final int MANUEL = 30293;
	private static final int ALLANA = 30424;
	private static final int PERRIN = 30428;
	private static final int[] TALKERS =
	{
		MANUEL,
		ALLANA,
		PERRIN
	};
	// Mobs
	private static final int LIZARDMAN_WARRIOR = 27032;
	private static final int LIZARDMAN_SCOUT = 27033;
	private static final int LIZARDMAN = 27034;
	private static final int TAMIL = 27035;
	private static final int[] MOBS =
	{
		LIZARDMAN_WARRIOR,
		LIZARDMAN_SCOUT,
		LIZARDMAN,
		TAMIL
	};
	// Quest items
	private static final int CRYSTAL_MEDALLION = 1231;
	private static final int MONEY_OF_SWINDLER = 1232;
	private static final int DAIRY_OF_ALLANA = 1233;
	private static final int LIZARD_CAPTAIN_ORDER = 1234;
	private static final int TAMATOS_NECKLACE = 1275;
	private static final int HALF_OF_DAIRY = 1236;
	// Reward
	private static final int LEAF_OF_ORACLE = 1235;
	private static final int[] QUESTITEMS =
	{
		MONEY_OF_SWINDLER,
		DAIRY_OF_ALLANA,
		LIZARD_CAPTAIN_ORDER,
		CRYSTAL_MEDALLION,
		HALF_OF_DAIRY,
		TAMATOS_NECKLACE
	};
	
	public Q00409_PathToOracle(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(MANUEL);
		
		for (int talkId : TALKERS)
		{
			addTalkId(talkId);
		}
		
		for (int mobId : MOBS)
		{
			addKillId(mobId);
		}
		
		questItemIds = QUESTITEMS;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(getName());
		
		if (st == null)
		{
			return super.onAdvEvent(event, npc, player);
		}
		
		int level = player.getLevel();
		int classId = player.getClassId().getId();
		if (event.equalsIgnoreCase("1"))
		{
			st.set("id", "0");
			if ((level >= 18) && (classId == 0x19) && (st.getQuestItemsCount(LEAF_OF_ORACLE) == 0))
			{
				st.set("cond", "1");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
				st.giveItems(CRYSTAL_MEDALLION, 1);
				htmltext = "30293-05.htm";
			}
			else if (classId != 0x19)
			{
				htmltext = classId == 0x1d ? "30293-02a.htm" : "30293-02.htm";
			}
			else if ((level < 18) && (classId == 0x19))
			{
				htmltext = "30293-03.htm";
			}
			else if ((level >= 18) && (classId == 0x19) && (st.getQuestItemsCount(LEAF_OF_ORACLE) == 1))
			{
				htmltext = "30293-04.htm";
			}
		}
		else if (event.equalsIgnoreCase("30424-08.htm"))
		{
			if (st.getInt("cond") > 0)
			{
				st.addSpawn(LIZARDMAN_WARRIOR, player.getX() + 60, player.getY(), player.getZ(), 180000);
				st.addSpawn(LIZARDMAN_SCOUT, player.getX(), player.getY() + 60, player.getZ(), 180000);
				st.addSpawn(LIZARDMAN, player.getX() - 60, player.getY(), player.getZ(), 180000);
				st.set("cond", "2");
			}
		}
		else if (event.equalsIgnoreCase("30424_1"))
		{
			return super.onAdvEvent(event, npc, player);
		}
		else if (event.equalsIgnoreCase("30428_1"))
		{
			if (st.getInt("cond") > 0)
			{
				htmltext = "30428-02.htm";
			}
		}
		else if (event.equalsIgnoreCase("30428_2"))
		{
			if (st.getInt("cond") > 0)
			{
				htmltext = "30428-03.htm";
			}
		}
		else if (event.equalsIgnoreCase("30428_3"))
		{
			if (st.getInt("cond") > 0)
			{
				st.addSpawn(TAMIL, player.getX() + 50, player.getY(), player.getZ(), 180000);
			}
		}
		
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		String htmltext = Quest.getNoQuestMsg(talker);
		QuestState st = talker.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		int npcId = npc.getNpcId();
		int id = st.getState();
		if ((npcId != MANUEL) && (id != State.STARTED))
		{
			return htmltext;
		}
		
		if ((npcId == MANUEL) && (st.getInt("cond") == 0))
		{
			htmltext = st.getQuestItemsCount(LEAF_OF_ORACLE) == 0 ? "30293-01.htm" : "30293-04.htm";
		}
		else if ((npcId == MANUEL) && (st.getInt("cond") > 0) && (st.getQuestItemsCount(CRYSTAL_MEDALLION) > 0))
		{
			if ((st.getQuestItemsCount(MONEY_OF_SWINDLER) == 0) && (st.getQuestItemsCount(DAIRY_OF_ALLANA) == 0) && (st.getQuestItemsCount(LIZARD_CAPTAIN_ORDER) == 0) && (st.getQuestItemsCount(HALF_OF_DAIRY) == 0))
			{
				htmltext = st.getInt("cond") > 0 ? "30293-09.htm" : "30293-06.htm";
			}
			else
			{
				if ((st.getQuestItemsCount(MONEY_OF_SWINDLER) == 1) && (st.getQuestItemsCount(DAIRY_OF_ALLANA) == 1) && (st.getQuestItemsCount(LIZARD_CAPTAIN_ORDER) == 1) && (st.getQuestItemsCount(HALF_OF_DAIRY) == 0))
				{
					st.takeItems(MONEY_OF_SWINDLER, 1);
					st.takeItems(DAIRY_OF_ALLANA, 1);
					st.takeItems(LIZARD_CAPTAIN_ORDER, 1);
					st.takeItems(CRYSTAL_MEDALLION, 1);
					String isFinished = st.getGlobalQuestVar("1ClassQuestFinished");
					if (isFinished.equalsIgnoreCase(""))
					{
						st.addExpAndSp(295862, 1910);
					}
					st.giveItems(LEAF_OF_ORACLE, 1);
					st.saveGlobalQuestVar("1ClassQuestFinished", "1");
					st.set("cond", "0");
					st.exitQuest(false);
					st.playSound("ItemSound.quest_finish");
					htmltext = "30293-08.htm";
				}
				else
				{
					htmltext = "30293-07.htm";
				}
			}
		}
		else if ((npcId == ALLANA) && (st.getInt("cond") > 0) && (st.getQuestItemsCount(CRYSTAL_MEDALLION) > 0))
		{
			if ((st.getQuestItemsCount(MONEY_OF_SWINDLER) == 0) && (st.getQuestItemsCount(DAIRY_OF_ALLANA) == 0) && (st.getQuestItemsCount(LIZARD_CAPTAIN_ORDER) == 0) && (st.getQuestItemsCount(HALF_OF_DAIRY) == 0))
			{
				htmltext = st.getInt("cond") > 2 ? "30424-05.htm" : "30424-01.htm";
			}
			else if ((st.getQuestItemsCount(MONEY_OF_SWINDLER) == 0) && (st.getQuestItemsCount(DAIRY_OF_ALLANA) == 0) && (st.getQuestItemsCount(LIZARD_CAPTAIN_ORDER) == 1) && (st.getQuestItemsCount(HALF_OF_DAIRY) == 0))
			{
				st.giveItems(HALF_OF_DAIRY, 1);
				st.set("cond", "4");
				htmltext = "30424-02.htm";
			}
			else if ((st.getQuestItemsCount(MONEY_OF_SWINDLER) == 0) && (st.getQuestItemsCount(DAIRY_OF_ALLANA) == 0) && (st.getQuestItemsCount(LIZARD_CAPTAIN_ORDER) == 1) && (st.getQuestItemsCount(HALF_OF_DAIRY) == 1))
			{
				htmltext = (st.getInt("cond") > 0) && (st.getQuestItemsCount(TAMATOS_NECKLACE) == 0) ? "30424-06.htm" : "30424-03.htm";
			}
			else if ((st.getQuestItemsCount(MONEY_OF_SWINDLER) == 1) && (st.getQuestItemsCount(DAIRY_OF_ALLANA) == 0) && (st.getQuestItemsCount(LIZARD_CAPTAIN_ORDER) == 1) && (st.getQuestItemsCount(HALF_OF_DAIRY) == 1))
			{
				st.takeItems(HALF_OF_DAIRY, 1);
				st.giveItems(DAIRY_OF_ALLANA, 1);
				st.set("cond", "7");
				htmltext = "30424-04.htm";
			}
			else
			{
				if ((st.getQuestItemsCount(MONEY_OF_SWINDLER) == 1) && (st.getQuestItemsCount(LIZARD_CAPTAIN_ORDER) == 1) && (st.getQuestItemsCount(HALF_OF_DAIRY) == 0) && (st.getQuestItemsCount(DAIRY_OF_ALLANA) > 0))
				{
					htmltext = "30424-05.htm";
				}
			}
		}
		else if ((npcId == PERRIN) && (st.getInt("cond") > 0) && (st.getQuestItemsCount(CRYSTAL_MEDALLION) > 0) && (st.getQuestItemsCount(LIZARD_CAPTAIN_ORDER) > 0))
		{
			if (st.getQuestItemsCount(TAMATOS_NECKLACE) == 1)
			{
				st.giveItems(MONEY_OF_SWINDLER, 1);
				st.takeItems(TAMATOS_NECKLACE, 1);
				st.set("cond", "6");
				htmltext = "30428-04.htm";
			}
			else
			{
				if (st.getQuestItemsCount(MONEY_OF_SWINDLER) > 0)
				{
					htmltext = "30428-05.htm";
				}
				else
				{
					htmltext = st.getInt("cond") > 4 ? "30428-06.htm" : "30428-01.htm";
				}
			}
		}
		
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		QuestState st = killer.getQuestState(getName());
		if (st == null)
		{
			return super.onKill(npc, killer, isPet);
		}
		
		if (st.getState() != State.STARTED)
		{
			return super.onKill(npc, killer, isPet);
		}
		
		int npcId = npc.getNpcId();
		if (npcId == LIZARDMAN_WARRIOR)
		{
			st.set("id", "0");
			if ((st.getInt("cond") > 0) && (st.getQuestItemsCount(LIZARD_CAPTAIN_ORDER) == 0))
			{
				st.giveItems(LIZARD_CAPTAIN_ORDER, 1);
				st.playSound("ItemSound.quest_middle");
				st.set("cond", "3");
			}
		}
		else if (npcId == TAMIL)
		{
			st.set("id", "0");
			if ((st.getInt("cond") > 0) && (st.getQuestItemsCount(TAMATOS_NECKLACE) == 0))
			{
				st.giveItems(TAMATOS_NECKLACE, 1);
				st.playSound("ItemSound.quest_middle");
				st.set("cond", "5");
			}
		}
		
		return super.onKill(npc, killer, isPet);
	}
	
	public static void main(String[] args)
	{
		new Q00409_PathToOracle(409, Q00409_PathToOracle.class.getSimpleName(), "Path To Oracle");
	}
}
