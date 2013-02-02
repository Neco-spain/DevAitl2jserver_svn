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
import com.l2jserver.gameserver.util.Util;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00700_CursedLife extends Quest
{
	private static final int ORBYU = 32560;
	private static final int SWALLOWED_SKULL = 13872;
	private static final int SWALLOWED_STERNUM = 13873;
	private static final int SWALLOWED_BONES = 13874;
	private static final int[] MOBS =
	{
		22602,
		22603,
		22604,
		22605
	};
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		if (event.equalsIgnoreCase("32560-03.htm"))
		{
			st.setState(State.STARTED);
			st.set("cond", "1");
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("32560-quit.htm"))
		{
			st.unset("cond");
			st.exitQuest(true);
			st.playSound("ItemSound.quest_finish");
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
		int cond = st.getInt("cond");
		if (npc.getNpcId() == ORBYU)
		{
			QuestState first = player.getQuestState("Q10273_GoodDayToFly");
			if ((first != null) && (first.getState() == State.COMPLETED) && (st.getState() == State.CREATED) && (player.getLevel() >= 75))
			{
				htmltext = "32560-01.htm";
			}
			else if (cond == 1)
			{
				long count1 = st.getQuestItemsCount(SWALLOWED_BONES);
				long count2 = st.getQuestItemsCount(SWALLOWED_STERNUM);
				long count3 = st.getQuestItemsCount(SWALLOWED_SKULL);
				
				if ((count1 > 0) || (count2 > 0) || (count3 > 0))
				{
					long reward = (count1 * 500) + (count2 * 5000) + (count3 * 50000);
					st.takeItems(SWALLOWED_BONES, -1);
					st.takeItems(SWALLOWED_STERNUM, -1);
					st.takeItems(SWALLOWED_SKULL, -1);
					st.giveItems(57, reward);
					st.playSound("ItemSound.quest_itemget");
					htmltext = "32560-06.htm";
				}
				else
				{
					htmltext = "32560-04.htm";
				}
			}
			else if (cond == 0)
			{
				htmltext = "32560-00.htm";
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
		if ((st.getInt("cond") == 1) && Util.contains(MOBS, npc.getNpcId()))
		{
			int chance = st.getRandom(100);
			if (chance < 5)
			{
				st.giveItems(SWALLOWED_SKULL, 1);
			}
			else if (chance < 20)
			{
				st.giveItems(SWALLOWED_STERNUM, 1);
			}
			else
			{
				st.giveItems(SWALLOWED_BONES, 1);
			}
			st.playSound("ItemSound.quest_itemget");
		}
		return null;
	}
	
	public Q00700_CursedLife(int id, String name, String desc)
	{
		super(id, name, desc);
		addStartNpc(ORBYU);
		addTalkId(ORBYU);
		for (int i : MOBS)
		{
			addKillId(i);
		}
		this.questItemIds = new int[]
		{
			SWALLOWED_SKULL,
			SWALLOWED_STERNUM,
			SWALLOWED_BONES
		};
	}
	
	public static void main(String[] args)
	{
		new Q00700_CursedLife(700, Q00700_CursedLife.class.getSimpleName(), "Cursed Life");
	}
}