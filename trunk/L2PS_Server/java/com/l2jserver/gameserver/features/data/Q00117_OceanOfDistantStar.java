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
public final class Q00117_OceanOfDistantStar extends Quest
{
	private static final int ABEY = 32053;
	private static final int GHOST = 32055;
	private static final int GHOST_F = 32054;
	private static final int OBI = 32052;
	private static final int BOX = 32076;
	private static final int[] MOBS =
	{
		22023,
		22024
	};
	private static final int GREY_STAR = 8495;
	private static final int ENGRAVED_HAMMER = 8488;
	private static final int DROP_CHANCE = 38;
	
	private static final int[] QUESTITEMS =
	{
		GREY_STAR
	};
	
	public Q00117_OceanOfDistantStar(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(ABEY);
		addTalkId(ABEY);
		addTalkId(GHOST);
		addTalkId(GHOST_F);
		addTalkId(OBI);
		addTalkId(BOX);
		for (int i : MOBS)
		{
			addKillId(i);
		}
		questItemIds = QUESTITEMS;
	}
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		
		if (event.equalsIgnoreCase("1"))
		{
			htmltext = "0a.htm";
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("2"))
		{
			htmltext = "1a.htm";
			st.set("cond", "2");
		}
		else if (event.equalsIgnoreCase("3"))
		{
			htmltext = "2a.htm";
			st.set("cond", "3");
		}
		else if (event.equalsIgnoreCase("4"))
		{
			htmltext = "3a.htm";
			st.set("cond", "4");
		}
		else if (event.equalsIgnoreCase("5"))
		{
			htmltext = "4a.htm";
			st.set("cond", "5");
			st.giveItems(ENGRAVED_HAMMER, 1);
		}
		else if (event.equalsIgnoreCase("6") && (st.getQuestItemsCount(ENGRAVED_HAMMER) == 1))
		{
			htmltext = "5a.htm";
			st.set("cond", "6");
		}
		else if (event.equalsIgnoreCase("7") && (st.getQuestItemsCount(ENGRAVED_HAMMER) == 1))
		{
			htmltext = "6a.htm";
			st.set("cond", "7");
		}
		else if (event.equalsIgnoreCase("8") && (st.getQuestItemsCount(GREY_STAR) == 1))
		{
			htmltext = "7a.htm";
			st.takeItems(GREY_STAR, 1);
			st.set("cond", "9");
		}
		else if (event.equalsIgnoreCase("9") && (st.getQuestItemsCount(ENGRAVED_HAMMER) == 1))
		{
			htmltext = "8a.htm";
			st.takeItems(ENGRAVED_HAMMER, 1);
			st.set("cond", "10");
		}
		else if (event.equalsIgnoreCase("10"))
		{
			htmltext = "9b.htm";
			st.giveReward(57, 17647);
			st.addExpAndSp(107387, 7369);
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(false);
		}
		return htmltext;
	}
	
	@Override
	public final String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		String htmltext = getNoQuestMsg(player);
		
		if (st == null)
		{
			return htmltext;
		}
		
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		
		switch (st.getState())
		{
			case State.COMPLETED:
			{
				htmltext = getAlreadyCompletedMsg(player);
				break;
			}
			case State.CREATED:
			{
				if (npcId == ABEY)
				{
					if (st.getPlayer().getLevel() >= 39)
					{
						htmltext = "0.htm";
					}
					else
					{
						htmltext = "0b.htm";
						st.exitQuest(true);
					}
				}
				break;
			}
			case State.STARTED:
			{
				if ((npcId == GHOST) && (cond == 1))
				{
					htmltext = "1.htm";
				}
				else if ((npcId == GHOST) && (cond == 9) && (st.getQuestItemsCount(ENGRAVED_HAMMER) == 1))
				{
					htmltext = "8.htm";
				}
				else if ((npcId == OBI) && (cond == 2))
				{
					htmltext = "2.htm";
				}
				else if ((npcId == OBI) && (cond == 6) && (st.getQuestItemsCount(ENGRAVED_HAMMER) == 1))
				{
					htmltext = "6.htm";
				}
				else if ((npcId == OBI) && (cond == 7) && (st.getQuestItemsCount(ENGRAVED_HAMMER) == 1))
				{
					htmltext = "6a.htm";
				}
				else if ((npcId == OBI) && (cond == 8) && (st.getQuestItemsCount(GREY_STAR) == 1))
				{
					htmltext = "7.htm";
				}
				else if ((npcId == ABEY) && (cond == 3))
				{
					htmltext = "3.htm";
				}
				else if ((npcId == ABEY) && (cond == 5) && (st.getQuestItemsCount(ENGRAVED_HAMMER) == 1))
				{
					htmltext = "5.htm";
				}
				else if ((npcId == ABEY) && (cond == 6) && (st.getQuestItemsCount(ENGRAVED_HAMMER) == 1))
				{
					htmltext = "5a.htm";
				}
				else if ((npcId == BOX) && (cond == 4))
				{
					htmltext = "4.htm";
				}
				else if ((npcId == GHOST_F) && (cond == 10))
				{
					htmltext = "9.htm";
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState st = player.getQuestState(getName());
		
		if (st == null)
		{
			return null;
		}
		
		if (st.getState() == State.STARTED)
		{
			int npcId = npc.getNpcId();
			if ((npcId == 22023) || (npcId == 22024))
			{
				if ((st.getInt("cond") == 7) && (st.getQuestItemsCount(GREY_STAR) < 1) && (st.getRandom(100) < DROP_CHANCE))
				{
					st.giveItems(GREY_STAR, 1);
					st.playSound("ItemSound.quest_itemget");
					st.set("cond", "8");
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00117_OceanOfDistantStar(117, Q00117_OceanOfDistantStar.class.getSimpleName(), "Ocean of Distant Star");
	}
}
