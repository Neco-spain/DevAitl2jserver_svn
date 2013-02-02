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
public class Q00627_HeartInSearchOfPower extends Quest
{
	private static final int M_NECROMANCER = 31518;
	private static final int ENFEUX = 31519;
	private static final int SEAL_OF_LIGHT = 7170;
	private static final int BEAD_OF_OBEDIENCE = 7171;
	private static final int GEM_OF_SAINTS = 7172;
	private static final int ADENA = 57;
	private static final int MOLD_HARDENER = 4041;
	private static final int ENRIA = 4042;
	private static final int ASOFE = 4043;
	private static final int THONS = 4044;
	
	public Q00627_HeartInSearchOfPower()
	{
		super(627, Q00627_HeartInSearchOfPower.class.getSimpleName(), "Heart In Search Of Power");
		questItemIds = new int[]
		{
			BEAD_OF_OBEDIENCE
		};
		addStartNpc(M_NECROMANCER);
		addTalkId(M_NECROMANCER);
		addTalkId(ENFEUX);
		addKillId(21658);
		for (int i = 21520; i <= 21541; i++)
		{
			addKillId(i);
		}
	}
	
	@Override
	public String onEvent(String event, QuestState st)
	{
		if ("31518-1.htm".equals(event))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if ("31518-3.htm".equals(event))
		{
			st.takeItems(BEAD_OF_OBEDIENCE, 300);
			st.giveItems(SEAL_OF_LIGHT, 1);
			st.set("cond", "3");
		}
		else if ("31519-1.htm".equals(event))
		{
			st.takeItems(SEAL_OF_LIGHT, 1);
			st.giveItems(GEM_OF_SAINTS, 1);
			st.set("cond", "4");
		}
		else if ("31518-5.htm".equals(event) && (st.getQuestItemsCount(GEM_OF_SAINTS) == 1))
		{
			st.takeItems(GEM_OF_SAINTS, 1);
			st.set("cond", "5");
		}
		else
		{
			if ("31518-6.htm".equals(event))
			{
				st.giveItems(ADENA, 100000);
			}
			else if ("31518-7.htm".equals(event))
			{
				st.giveItems(ASOFE, (int) (13 * Config.RATE_QUEST_REWARD));
				st.giveItems(ADENA, 6400);
			}
			else if ("31518-8.htm".equals(event))
			{
				st.giveItems(THONS, (int) (13 * Config.RATE_QUEST_REWARD));
				st.giveItems(ADENA, 6400);
			}
			else if ("31518-9.htm".equals(event))
			{
				st.giveItems(ENRIA, (int) (6 * Config.RATE_QUEST_REWARD));
				st.giveItems(ADENA, 13600);
			}
			else if ("31518-10.htm".equals(event))
			{
				st.giveItems(MOLD_HARDENER, (int) (3 * Config.RATE_QUEST_REWARD));
				st.giveItems(ADENA, 17200);
			}
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(true);
		}
		return event;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		if (st != null)
		{
			int npcId = npc.getNpcId();
			byte id = st.getState();
			int cond = st.getInt("cond");
			if (cond == 0)
			{
				if (player.getLevel() >= 60)
				{
					htmltext = "31518-0.htm";
				}
				else
				{
					htmltext = "31518-0a.htm";
					st.exitQuest(true);
				}
			}
			else if (id == State.STARTED)
			{
				if (npcId == M_NECROMANCER)
				{
					if (cond == 1)
					{
						htmltext = "31518-1a.htm";
					}
					else if (st.getQuestItemsCount(BEAD_OF_OBEDIENCE) >= 300)
					{
						htmltext = "31518-2.htm";
					}
					else if (st.getQuestItemsCount(GEM_OF_SAINTS) > 0)
					{
						htmltext = "31518-4.htm";
					}
					else if (cond == 5)
					{
						htmltext = "31518-5.htm";
					}
				}
				else if ((npcId == ENFEUX) && (st.getQuestItemsCount(SEAL_OF_LIGHT) > 0))
				{
					htmltext = "31519-0.htm";
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState st = player.getQuestState(getName());
		if ((st != null) && (st.getState() == State.STARTED))
		{
			int count = (int) st.getQuestItemsCount(BEAD_OF_OBEDIENCE);
			if ((st.getInt("cond") == 1) && (count < 300))
			{
				st.giveItems(BEAD_OF_OBEDIENCE, (int) (Config.RATE_QUEST_DROP * 1));
				count = (int) st.getQuestItemsCount(BEAD_OF_OBEDIENCE);
				if (count >= 300)
				{
					st.playSound("ItemSound.quest_middle");
					st.set("cond", "2");
				}
				else
				{
					st.playSound("ItemSound.quest_itemget");
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00627_HeartInSearchOfPower();
	}
}
