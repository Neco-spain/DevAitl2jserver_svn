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
public class Q00338_AlligatorHunter extends Quest
{
	private static final int ALLIGATOR = 20135;
	private static final int ENVERUN = 30892;
	private static final int ALLIGATOR_PELTS = 4337;
	
	public Q00338_AlligatorHunter(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(ENVERUN);
		addTalkId(ENVERUN);
		
		addKillId(ALLIGATOR);
		
		questItemIds = new int[]
		{
			ALLIGATOR_PELTS
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
		
		if (event.equalsIgnoreCase("30892-02.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30892-05.htm"))
		{
			long count = st.getQuestItemsCount(ALLIGATOR_PELTS);
			if (count > 0)
			{
				if (count > 10)
				{
					count = (count * 60) + 3430;
				}
				else
				{
					count = count * 60;
				}
				
				st.takeItems(ALLIGATOR_PELTS, -1);
				st.rewardItems(57, count);
			}
			else
			{
				htmltext = "30892-04.htm";
			}
		}
		else if ("30892-08.htm".equals(event))
		{
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(true);
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = Quest.getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.CREATED:
				if ((player.getLevel() >= 40) && (player.getLevel() <= 47))
				{
					htmltext = "30892-01.htm";
				}
				else
				{
					htmltext = "30892-00.htm";
				}
				break;
			case State.STARTED:
				if (st.getQuestItemsCount(ALLIGATOR_PELTS) > 0)
				{
					htmltext = "30892-03.htm";
				}
				else
				{
					htmltext = "30892-04.htm";
				}
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
		
		if (st.isStarted() && (st.getRandom(10) < 5))
		{
			st.giveItems(ALLIGATOR_PELTS, 1);
			st.playSound("ItemSound.quest_itemget");
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00338_AlligatorHunter(338, Q00338_AlligatorHunter.class.getSimpleName(), "Alligator Hunter");
	}
}