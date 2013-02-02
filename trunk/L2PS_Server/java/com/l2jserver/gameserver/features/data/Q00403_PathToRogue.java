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

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00403_PathToRogue extends Quest
{
	private static final int BEZIQUE = 30379;
	private static final int NETI = 30425;
	private static final int[] TALKERS =
	{
		BEZIQUE,
		NETI
	};
	// Mobs
	private static final int TRACKER_SKELETON = 20035;
	private static final int CATS_EYE_BANDIT = 27038;
	private static final int TRACKER_SKELETON_LEADER = 20042;
	private static final int SKELETON_SCOUT = 20045;
	private static final int SKELETON_BOWMAN = 20051;
	private static final int RUIN_SPARTOI = 20054;
	private static final int RAGING_SPARTOI = 20060;
	private static final int[] MOBS =
	{
		TRACKER_SKELETON,
		CATS_EYE_BANDIT,
		TRACKER_SKELETON_LEADER,
		SKELETON_SCOUT,
		SKELETON_BOWMAN,
		RUIN_SPARTOI,
		RAGING_SPARTOI
	};
	// Quest items
	private static final int BEZIQUES_LETTER = 1180;
	private static final int NETIS_BOW = 1181;
	private static final int NETIS_DAGGER = 1182;
	private static final int SPATOIS_BONES = 1183;
	private static final int HORSESHOE_OF_LIGHT = 1184;
	private static final int WANTED_BILL = 1185;
	private static final int STOLEN_JEWELRY = 1186;
	private static final int STOLEN_TOMES = 1187;
	private static final int STOLEN_RING = 1188;
	private static final int STOLEN_NECKLACE = 1189;
	// Reward
	private static final int BEZIQUES_RECOMMENDATION = 1190;
	private static final int[] QUESTITEMS =
	{
		BEZIQUES_LETTER,
		NETIS_BOW,
		NETIS_DAGGER,
		SPATOIS_BONES,
		HORSESHOE_OF_LIGHT,
		WANTED_BILL,
		STOLEN_JEWELRY,
		STOLEN_TOMES,
		STOLEN_RING,
		STOLEN_NECKLACE
	};
	private static final int[] STOLENITEMS =
	{
		STOLEN_JEWELRY,
		STOLEN_TOMES,
		STOLEN_RING,
		STOLEN_NECKLACE
	};
	// Chances in %
	private static Map<Integer, Integer> DROPCHANCE = new HashMap<>();
	
	static
	{
		DROPCHANCE.put(TRACKER_SKELETON, 20);
		DROPCHANCE.put(TRACKER_SKELETON_LEADER, 30);
		DROPCHANCE.put(SKELETON_SCOUT, 20);
		DROPCHANCE.put(SKELETON_BOWMAN, 20);
		DROPCHANCE.put(RUIN_SPARTOI, 80);
		DROPCHANCE.put(RAGING_SPARTOI, 80);
	}
	
	public Q00403_PathToRogue(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(BEZIQUE);
		
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
	
	private boolean haveAllStolenItems(QuestState st)
	{
		for (int item : STOLENITEMS)
		{
			if (st.getQuestItemsCount(item) == 0)
			{
				return false;
			}
		}
		
		return true;
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
		
		if (event.equalsIgnoreCase("30379_2"))
		{
			if (player.getClassId().getId() == 0x00)
			{
				if (player.getLevel() >= 18)
				{
					htmltext = st.getQuestItemsCount(BEZIQUES_RECOMMENDATION) > 0 ? "30379-04.htm" : "30379-05.htm";
				}
				else
				{
					htmltext = "30379-03.htm";
				}
			}
			else
			{
				htmltext = player.getClassId().getId() == 0x07 ? "30379-02a.htm" : "30379-02.htm";
			}
		}
		else if (event.equalsIgnoreCase("1"))
		{
			st.set("id", "0");
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
			st.giveItems(BEZIQUES_LETTER, 1);
			htmltext = "30379-06.htm";
		}
		else if (event.equalsIgnoreCase("30425_1"))
		{
			st.takeItems(BEZIQUES_LETTER, 1);
			if (st.getQuestItemsCount(NETIS_BOW) == 0)
			{
				st.giveItems(NETIS_BOW, 1);
			}
			if (st.getQuestItemsCount(NETIS_DAGGER) == 0)
			{
				st.giveItems(NETIS_DAGGER, 1);
			}
			st.set("cond", "2");
			htmltext = "30425-05.htm";
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
		if ((npcId != BEZIQUE) && (id != State.STARTED))
		{
			return htmltext;
		}
		
		if ((npcId == BEZIQUE) && (st.getInt("cond") == 0))
		{
			htmltext = "30379-01.htm";
		}
		else if ((npcId == BEZIQUE) && (st.getInt("cond") > 0))
		{
			if ((st.getQuestItemsCount(HORSESHOE_OF_LIGHT) == 0) && haveAllStolenItems(st))
			{
				htmltext = "30379-09.htm";
				String isFinished = st.getGlobalQuestVar("1ClassQuestFinished");
				if (isFinished.equalsIgnoreCase(""))
				{
					st.giveReward(57, 81900);
					st.addExpAndSp(295862, 2060);
				}
				st.saveGlobalQuestVar("1ClassQuestFinished", "1");
				st.set("cond", "0");
				st.giveItems(BEZIQUES_RECOMMENDATION, 1);
				st.exitQuest(false);
				st.playSound("ItemSound.quest_finish");
			}
			else if ((st.getQuestItemsCount(HORSESHOE_OF_LIGHT) == 0) && (st.getQuestItemsCount(BEZIQUES_LETTER) > 0))
			{
				htmltext = "30379-07.htm";
			}
			else if (st.getQuestItemsCount(HORSESHOE_OF_LIGHT) > 0)
			{
				htmltext = "30379-08.htm";
				st.takeItems(HORSESHOE_OF_LIGHT, 1);
				st.giveItems(WANTED_BILL, 1);
				st.set("cond", "5");
			}
			else if ((st.getQuestItemsCount(NETIS_BOW) > 0) && (st.getQuestItemsCount(NETIS_DAGGER) > 0) && (st.getQuestItemsCount(WANTED_BILL) == 0))
			{
				htmltext = "30379-10.htm";
			}
			else if (st.getQuestItemsCount(WANTED_BILL) > 0)
			{
				htmltext = "30379-11.htm";
			}
		}
		else if ((npcId == NETI) && (st.getInt("cond") > 0) && (st.getQuestItemsCount(BEZIQUES_LETTER) > 0))
		{
			htmltext = "30425-01.htm";
		}
		else if ((npcId == NETI) && (st.getInt("cond") > 0) && (st.getQuestItemsCount(HORSESHOE_OF_LIGHT) == 0) && (st.getQuestItemsCount(BEZIQUES_LETTER) == 0))
		{
			if (st.getQuestItemsCount(SPATOIS_BONES) < 10)
			{
				htmltext = "30425-06.htm";
			}
			else if (st.getQuestItemsCount(WANTED_BILL) > 0)
			{
				htmltext = "30425-08.htm";
			}
			else if (st.getQuestItemsCount(SPATOIS_BONES) >= 10)
			{
				htmltext = "30425-07.htm";
				st.takeItems(SPATOIS_BONES, st.getQuestItemsCount(SPATOIS_BONES));
				st.giveItems(HORSESHOE_OF_LIGHT, 1);
				st.set("cond", "4");
			}
		}
		else if ((npcId == NETI) && (st.getInt("cond") > 0) && (st.getQuestItemsCount(HORSESHOE_OF_LIGHT) > 0))
		{
			htmltext = "30425-08.htm";
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
		if ((st.getItemEquipped(16) == NETIS_BOW) || (st.getItemEquipped(9) == NETIS_DAGGER))
		{
			if (DROPCHANCE.keySet().contains(npcId))
			{
				st.set("id", "0");
				if ((st.getInt("cond") > 0) && (st.getQuestItemsCount(SPATOIS_BONES) < 10) && (st.getRandom(100) < DROPCHANCE.get(npcId)))
				{
					st.giveItems(SPATOIS_BONES, 1);
				}
				if (st.getQuestItemsCount(SPATOIS_BONES) == 10)
				{
					st.playSound("ItemSound.quest_middle");
					st.set("cond", "3");
				}
				else
				{
					st.playSound("ItemSound.quest_itemget");
				}
			}
			else if (npcId == CATS_EYE_BANDIT)
			{
				st.set("id", "0");
				if ((st.getInt("cond") > 0) && (st.getQuestItemsCount(WANTED_BILL) > 0))
				{
					int n = st.getRandom(4);
					if (st.getQuestItemsCount(STOLENITEMS[n]) == 0)
					{
						st.giveItems(STOLENITEMS[n], 1);
						if (!haveAllStolenItems(st))
						{
							st.playSound("ItemSound.quest_itemget");
						}
						else
						{
							st.playSound("ItemSound.quest_middle");
							st.set("cond", "6");
						}
					}
				}
			}
		}
		
		return super.onKill(npc, killer, isPet);
	}
	
	public static void main(String[] args)
	{
		new Q00403_PathToRogue(403, Q00403_PathToRogue.class.getSimpleName(), "Path To Rogue");
	}
}
