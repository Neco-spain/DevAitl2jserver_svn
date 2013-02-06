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
public final class Q00119_LastImperialPrince extends Quest
{
	private static final int SPIRIT = 31453;
	private static final int DEVORIN = 32009;
	private static final int BROOCH = 7262;
	private static final int ADENA = 57;
	private static final int AMOUNT = 150292;
	
	public Q00119_LastImperialPrince(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(SPIRIT);
		addTalkId(SPIRIT);
		addTalkId(DEVORIN);
	}
	
	@Override
	public final String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if (event.equalsIgnoreCase("31453-4.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("32009-2.htm"))
		{
			if (st.getQuestItemsCount(BROOCH) < 1)
			{
				htmltext = "32009-4.htm";
				st.exitQuest(true);
			}
		}
		else if (event.equalsIgnoreCase("32009-3.htm"))
		{
			st.set("cond", "2");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31453-7.htm"))
		{
			st.giveReward(ADENA, AMOUNT);
			st.addExpAndSp(902439, 90067);
			st.exitQuest(false);
			st.playSound("ItemSound.quest_finish");
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
		
		if (st.getQuestItemsCount(BROOCH) < 1)
		{
			htmltext = "32009-4.htm";
			st.exitQuest(true);
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
				if (st.getPlayer().getLevel() < 74)
				{
					htmltext = "31453-8.htm";
					st.exitQuest(true);
				}
				else
				{
					htmltext = "31453-1.htm";
				}
				break;
			}
			case State.STARTED:
			{
				if ((npcId == SPIRIT) && (cond == 1))
				{
					htmltext = "31453-4.htm";
				}
				else if ((npcId == SPIRIT) && (cond == 2))
				{
					htmltext = "31453-5.htm";
				}
				else if ((npcId == DEVORIN) && (cond == 1))
				{
					htmltext = "32009-1.htm";
				}
				else if ((npcId == DEVORIN) && (cond == 2))
				{
					htmltext = "32009-3.htm";
				}
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00119_LastImperialPrince(119, Q00119_LastImperialPrince.class.getSimpleName(), "Last Imperial Prince");
	}
}
