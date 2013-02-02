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
public final class Q00164_BloodFiend extends Quest
{
	private static final int CREAMEES = 30149;
	private static final int KIRUNAK_SKULL = 1044;
	private static final int KIRUNAK = 27021;
	
	private Q00164_BloodFiend(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(CREAMEES);
		addTalkId(CREAMEES);
		
		addKillId(KIRUNAK);
		
		questItemIds = new int[]
		{
			KIRUNAK_SKULL
		};
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(getName());
		
		if (st == null)
		{
			return htmltext;
		}
		
		if (event.equalsIgnoreCase("1"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
			htmltext = "30149-04.htm";
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
		else if (st.isCompleted())
		{
			htmltext = getAlreadyCompletedMsg(player);
		}
		
		int cond = st.getInt("cond");
		
		if (cond == 0)
		{
			if (player.getRace().ordinal() == 2)
			{
				st.exitQuest(true);
				htmltext = "30149-00.htm";
			}
			else if (player.getLevel() < 21)
			{
				st.exitQuest(true);
				htmltext = "30149-02.htm";
			}
			else
			{
				htmltext = "30149-03.htm";
			}
		}
		else
		{
			if (st.getQuestItemsCount(KIRUNAK_SKULL) != 0)
			{
				st.exitQuest(false);
				st.rewardItems(57, 42130);
				st.addExpAndSp(35637, 1854);
				st.playSound("ItemSound.quest_finish");
				htmltext = "30149-06.htm";
			}
			else
			{
				htmltext = "30149-05.htm";
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
		
		if (npc.getNpcId() == KIRUNAK)
		{
			if (st.getInt("cond") == 1)
			{
				if (st.getQuestItemsCount(KIRUNAK_SKULL) == 0)
				{
					st.giveItems(KIRUNAK_SKULL, 1);
					st.playSound("ItemSound.quest_middle");
					st.set("cond", "2");
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00164_BloodFiend(164, Q00164_BloodFiend.class.getSimpleName(), "Blood Fiend");
	}
}