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
public class Q00651_RunawayYouth extends Quest
{
	private static int IVAN = 32014;
	private static int BATIDAE = 31989;
	protected L2Npc _npc;
	private static int SOE = 736;
	
	public Q00651_RunawayYouth(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(IVAN);
		addTalkId(BATIDAE);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		if (event.equalsIgnoreCase("32014-04.htm"))
		{
			if (st.getQuestItemsCount(SOE) > 0)
			{
				st.set("cond", "1");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
				st.takeItems(SOE, 1);
				htmltext = "32014-03.htm";
				st.startQuestTimer("ivan_timer", 20000);
			}
		}
		else if (event.equalsIgnoreCase("32014-04a.htm"))
		{
			st.exitQuest(true);
			st.playSound("ItemSound.quest_giveup");
		}
		else if (event.equalsIgnoreCase("ivan_timer"))
		{
			_npc.deleteMe();
			htmltext = null;
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		String htmltext = Quest.getNoQuestMsg(player);
		final int npcId = npc.getNpcId();
		if (st == null)
		{
			return htmltext;
		}
		final int id = st.getState();
		final int cond = st.getInt("cond");
		if ((npcId == IVAN) && (id == State.CREATED))
		{
			if (st.getPlayer().getLevel() >= 26)
			{
				htmltext = "32014-02.htm";
			}
			else
			{
				htmltext = "32014-01.htm";
				st.exitQuest(true);
			}
		}
		else if ((npcId == BATIDAE) && (cond == 1))
		{
			htmltext = "31989-01.htm";
			st.giveItems(57, 2883);
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(true);
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00651_RunawayYouth(651, Q00651_RunawayYouth.class.getSimpleName(), "Runaway Youth");
	}
}
