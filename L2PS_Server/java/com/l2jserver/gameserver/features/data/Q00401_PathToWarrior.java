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
public class Q00401_PathToWarrior extends Quest
{
	private static final int AURON = 30010;
	private static final int SIMPLON = 30253;
	private static final int[] TALKERS =
	{
		AURON,
		SIMPLON
	};
	// Mobs
	private static final int TRACKER_SKELETON = 20035;
	private static final int VENOMOUS_SPIDER = 20038;
	private static final int TRACKER_SKELETON_LEADER = 20042;
	private static final int ARACHNID_TRACKER = 20043;
	private static final int[] MOBS =
	{
		TRACKER_SKELETON,
		VENOMOUS_SPIDER,
		TRACKER_SKELETON_LEADER,
		ARACHNID_TRACKER
	};
	// Quest items
	private static final int EINS_LETTER = 1138;
	private static final int WARRIOR_GUILD_MARK = 1139;
	private static final int RUSTED_BRONZE_SWORD1 = 1140;
	private static final int RUSTED_BRONZE_SWORD2 = 1141;
	private static final int RUSTED_BRONZE_SWORD3 = 1142;
	private static final int SIMPLONS_LETTER = 1143;
	private static final int POISON_SPIDER_LEG2 = 1144;
	private static final int[] QUESTITEMS =
	{
		EINS_LETTER,
		WARRIOR_GUILD_MARK,
		RUSTED_BRONZE_SWORD1,
		RUSTED_BRONZE_SWORD2,
		RUSTED_BRONZE_SWORD3,
		SIMPLONS_LETTER,
		POISON_SPIDER_LEG2
	};
	// Reward
	private static final int MEDALLION_OF_WARRIOR = 1145;
	
	public Q00401_PathToWarrior(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(AURON);
		
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
		
		if (event.equalsIgnoreCase("401_1"))
		{
			if (player.getClassId().getId() == 0x00)
			{
				if (player.getLevel() >= 18)
				{
					htmltext = st.getQuestItemsCount(MEDALLION_OF_WARRIOR) > 0 ? "30010-04.htm" : "30010-05.htm";
				}
				else
				{
					htmltext = "30010-02.htm";
				}
			}
			else
			{
				htmltext = player.getClassId().getId() == 0x01 ? "30010-02a.htm" : "30010-03.htm";
			}
		}
		else if (event.equalsIgnoreCase("401_2"))
		{
			htmltext = "30010-10.htm";
		}
		else if (event.equalsIgnoreCase("401_3"))
		{
			htmltext = "30010-11.htm";
			st.takeItems(SIMPLONS_LETTER, 1);
			st.takeItems(RUSTED_BRONZE_SWORD2, 1);
			st.giveItems(RUSTED_BRONZE_SWORD3, 1);
			st.set("cond", "5");
		}
		else if (event.equalsIgnoreCase("1"))
		{
			st.set("id", "0");
			if (st.getQuestItemsCount(EINS_LETTER) == 0)
			{
				st.set("cond", "1");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
				st.giveItems(EINS_LETTER, 1);
				htmltext = "30010-06.htm";
			}
		}
		else if (event.equalsIgnoreCase("30253_1"))
		{
			htmltext = "30253-02.htm";
			st.takeItems(EINS_LETTER, 1);
			st.giveItems(WARRIOR_GUILD_MARK, 1);
			st.set("cond", "2");
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
		if ((npcId != AURON) && (id != State.STARTED))
		{
			return htmltext;
		}
		
		if ((npcId == AURON) && (st.getInt("cond") == 0))
		{
			htmltext = "30010-01.htm";
		}
		else if ((npcId == AURON) && (st.getInt("cond") > 0) && (st.getQuestItemsCount(EINS_LETTER) > 0))
		{
			htmltext = "30010-07.htm";
		}
		else if ((npcId == AURON) && (st.getInt("cond") > 0) && (st.getQuestItemsCount(WARRIOR_GUILD_MARK) == 1))
		{
			htmltext = "30010-08.htm";
		}
		else if ((npcId == SIMPLON) && (st.getInt("cond") > 0) && (st.getQuestItemsCount(EINS_LETTER) > 0))
		{
			htmltext = "30253-01.htm";
		}
		else if ((npcId == SIMPLON) && (st.getInt("cond") > 0) && (st.getQuestItemsCount(WARRIOR_GUILD_MARK) > 0))
		{
			if (st.getQuestItemsCount(RUSTED_BRONZE_SWORD1) < 1)
			{
				htmltext = "30253-03.htm";
			}
			else if (st.getQuestItemsCount(RUSTED_BRONZE_SWORD1) < 10)
			{
				htmltext = "30253-04.htm";
			}
			else if (st.getQuestItemsCount(RUSTED_BRONZE_SWORD1) >= 10)
			{
				st.takeItems(WARRIOR_GUILD_MARK, 1);
				st.takeItems(RUSTED_BRONZE_SWORD1, st.getQuestItemsCount(RUSTED_BRONZE_SWORD1));
				st.giveItems(RUSTED_BRONZE_SWORD2, 1);
				st.giveItems(SIMPLONS_LETTER, 1);
				st.set("cond", "4");
				htmltext = "30253-05.htm";
			}
		}
		else if ((npcId == SIMPLON) && (st.getInt("cond") > 0) && (st.getQuestItemsCount(SIMPLONS_LETTER) > 0))
		{
			htmltext = "30253-06.htm";
		}
		else if ((npcId == AURON) && (st.getInt("cond") > 0) && (st.getQuestItemsCount(SIMPLONS_LETTER) > 0) && (st.getQuestItemsCount(RUSTED_BRONZE_SWORD2) > 0) && (st.getQuestItemsCount(WARRIOR_GUILD_MARK) == 0) && (st.getQuestItemsCount(EINS_LETTER) == 0))
		{
			htmltext = "30010-09.htm";
		}
		else if ((npcId == AURON) && (st.getInt("cond") > 0) && (st.getQuestItemsCount(RUSTED_BRONZE_SWORD3) > 0) && (st.getQuestItemsCount(WARRIOR_GUILD_MARK) == 0) && (st.getQuestItemsCount(EINS_LETTER) == 0))
		{
			if (st.getQuestItemsCount(POISON_SPIDER_LEG2) < 20)
			{
				htmltext = "30010-12.htm";
			}
			else if (st.getQuestItemsCount(POISON_SPIDER_LEG2) > 19)
			{
				htmltext = "30010-13.htm";
				st.saveGlobalQuestVar("1ClassQuestFinished", "1");
				st.set("cond", "0");
				st.takeItems(POISON_SPIDER_LEG2, st.getQuestItemsCount(POISON_SPIDER_LEG2));
				st.takeItems(RUSTED_BRONZE_SWORD3, 1);
				st.addExpAndSp(228064, 21055);
				st.giveItems(57, 81900);
				st.giveItems(MEDALLION_OF_WARRIOR, 1);
				st.exitQuest(false);
				st.playSound("ItemSound.quest_finish");
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
		if ((npcId == TRACKER_SKELETON) || (npcId == TRACKER_SKELETON_LEADER))
		{
			st.set("id", "0");
			if ((st.getInt("cond") == 2) && (st.getQuestItemsCount(RUSTED_BRONZE_SWORD1) < 10))
			{
				if (st.getRandom(10) < 4)
				{
					st.giveItems(RUSTED_BRONZE_SWORD1, 1);
					if (st.getQuestItemsCount(RUSTED_BRONZE_SWORD1) == 10)
					{
						st.playSound("ItemSound.quest_middle");
						st.set("cond", "3");
					}
					else
					{
						st.playSound("ItemSound.quest_itemget");
					}
				}
			}
		}
		else if ((npcId == VENOMOUS_SPIDER) || (npcId == ARACHNID_TRACKER))
		{
			st.set("id", "0");
			if ((st.getInt("cond") > 0) && (st.getQuestItemsCount(POISON_SPIDER_LEG2) < 20) && (st.getQuestItemsCount(RUSTED_BRONZE_SWORD3) == 1) && (st.getItemEquipped(9) == RUSTED_BRONZE_SWORD3))
			{
				st.giveItems(POISON_SPIDER_LEG2, 1);
				if (st.getQuestItemsCount(POISON_SPIDER_LEG2) == 20)
				{
					st.playSound("ItemSound.quest_middle");
					st.set("cond", "6");
				}
				else
				{
					st.playSound("ItemSound.quest_itemget");
				}
			}
		}
		
		return super.onKill(npc, killer, isPet);
	}
	
	public static void main(String[] args)
	{
		new Q00401_PathToWarrior(401, Q00401_PathToWarrior.class.getSimpleName(), "Path To Warrior");
	}
}
