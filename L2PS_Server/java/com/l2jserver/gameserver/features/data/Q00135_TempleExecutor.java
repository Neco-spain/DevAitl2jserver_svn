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

import java.util.ArrayList;
import java.util.List;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.util.Rnd;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00135_TempleExecutor extends Quest
{
	private final static int SHEGFIELD = 30068;
	private final static int PANO = 30078;
	private final static int ALEX = 30291;
	private final static int SONIN = 31773;
	private final static int[] mobs =
	{
		20781,
		21104,
		21105,
		21106,
		21107
	};
	private final static int CARGO = 10328;
	private final static int CRYSTAL = 10329;
	private final static int MAP = 10330;
	private final static int SONIN_CR = 10331;
	private final static int PANO_CR = 10332;
	private final static int ALEX_CR = 10333;
	private final static int BADGE = 10334;
	
	public Q00135_TempleExecutor(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(SHEGFIELD);
		addTalkId(SHEGFIELD);
		addTalkId(ALEX);
		addTalkId(SONIN);
		addTalkId(PANO);
		
		for (int mob : mobs)
		{
			addKillId(mob);
		}
		
		questItemIds = new int[]
		{
			CARGO,
			CRYSTAL,
			MAP,
			SONIN_CR,
			PANO_CR,
			ALEX_CR
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
		
		if (event.equalsIgnoreCase("30068-02.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30068-09.htm"))
		{
			st.playSound("ItemSound.quest_finish");
			st.unset("Report");
			st.giveItems(57, 16924);
			st.addExpAndSp(30000, 2000);
			st.giveItems(BADGE, 1);
			st.exitQuest(false);
		}
		else if (event.equalsIgnoreCase("30068-03.htm"))
		{
			st.set("cond", "2");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("30291-06.htm"))
		{
			st.set("cond", "3");
			st.playSound("ItemSound.quest_middle");
		}
		return htmltext;
	}
	
	@Override
	public final String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		int npcId = npc.getNpcId();
		int cond = st.getCond();
		
		switch (st.getState())
		{
			case State.CREATED:
				if (player.getLevel() >= 35)
				{
					htmltext = "30068-01.htm";
				}
				else
				{
					st.exitQuest(true);
					htmltext = "30068-00.htm";
				}
				break;
			case State.STARTED:
				if (npcId == SHEGFIELD)
				{
					if (cond == 1)
					{
						htmltext = "30068-02.htm";
					}
					else if ((cond == 2) || (cond == 3) || (cond == 4))
					{
						htmltext = "30068-04.htm";
					}
					else if (cond == 5)
					{
						if (st.getInt("Report") == 1)
						{
							htmltext = "30068-06.htm";
						}
						if ((st.getQuestItemsCount(SONIN_CR) > 0) && (st.getQuestItemsCount(PANO_CR) > 0) && (st.getQuestItemsCount(ALEX_CR) > 0))
						{
							st.takeItems(PANO_CR, -1);
							st.takeItems(SONIN_CR, -1);
							st.takeItems(ALEX_CR, -1);
							st.set("Report", "1");
							htmltext = "30068-05.htm";
						}
					}
				}
				
				if (npcId == ALEX)
				{
					if (cond == 2)
					{
						htmltext = "30291-01.htm";
					}
					else if (cond == 3)
					{
						htmltext = "30291-07.htm";
					}
					else if (cond == 4)
					{
						if ((st.getQuestItemsCount(SONIN_CR) > 0) && (st.getQuestItemsCount(PANO_CR) > 0))
						{
							st.setCond(5);
							st.takeItems(MAP, -1);
							st.giveItems(ALEX_CR, 1);
							st.playSound("ItemSound.quest_middle");
							htmltext = "30291-09.htm";
						}
						htmltext = "30291-08.htm";
					}
					else if (cond == 5)
					{
						htmltext = "30291-10.htm";
					}
				}
				
				if (npcId == SONIN)
				{
					if (cond == 4)
					{
						if (st.getQuestItemsCount(CARGO) < 10)
						{
							htmltext = "31773-02.htm";
						}
						st.takeItems(CARGO, -1);
						st.giveItems(SONIN_CR, 1);
						st.playSound("ItemSound.quest_middle");
						htmltext = "31773-01.htm";
					}
				}
				
				if (npcId == PANO)
				{
					if (cond == 4)
					{
						if (st.getQuestItemsCount(CRYSTAL) < 10)
						{
							htmltext = "30078-02.htm";
						}
						st.takeItems(CRYSTAL, -1);
						st.giveItems(PANO_CR, 1);
						st.playSound("ItemSound.quest_middle");
						htmltext = "30078-01.htm";
					}
				}
				break;
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		if (st.getCond() == 3)
		{
			List<Integer> drops = new ArrayList<>();
			if (st.getQuestItemsCount(CARGO) < 10)
			{
				drops.add(CARGO);
			}
			if (st.getQuestItemsCount(CRYSTAL) < 10)
			{
				drops.add(CRYSTAL);
			}
			if (st.getQuestItemsCount(MAP) < 10)
			{
				drops.add(MAP);
			}
			if (drops.isEmpty())
			{
				return null;
			}
			int drop = drops.get(Rnd.get(drops.size()));
			st.giveItems(drop, 1);
			if ((drops.size() == 1) && (st.getQuestItemsCount(drop) >= 10))
			{
				st.set("cond", "4");
				st.playSound("ItemSound.quest_middle");
				return null;
			}
			st.playSound("ItemSound.quest_itemget");
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00135_TempleExecutor(135, Q00135_TempleExecutor.class.getSimpleName(), "Temple Executor");
	}
}