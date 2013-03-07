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

import com.l2jserver.gameserver.instancemanager.QuestManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00998_FallenAngelSelect extends Quest
{
	private static final int NATOOLS = 30894;
	
	public Q00998_FallenAngelSelect(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addTalkId(NATOOLS);
		
	}
	
	@Override
	public String onEvent(String event, QuestState st)
	{
		
		if (event.equalsIgnoreCase("dawn"))
		{
			Quest q1 = QuestManager.getInstance().getQuest("Q00142_FallenAngelRequestOfDawn");
			if (q1 != null)
			{
				QuestState qs1 = q1.newQuestState(st.getPlayer());
				qs1.setState(State.STARTED);
				q1.notifyEvent("30894-01.htm", null, st.getPlayer());
				st.setState(State.COMPLETED);
			}
			return null;
		}
		else if (event.equalsIgnoreCase("dusk"))
		{
			Quest q2 = QuestManager.getInstance().getQuest("Q00143_FallenAngelRequestOfDusk");
			if (q2 != null)
			{
				QuestState qs2 = q2.newQuestState(st.getPlayer());
				qs2.setState(State.STARTED);
				q2.notifyEvent("30894-01.htm", null, st.getPlayer());
				st.setState(State.COMPLETED);
			}
			return null;
		}
		return event;
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
		final int id = st.getState();
		if (id == State.STARTED)
		{
			htmltext = "30894-01.htm";
		}
		return htmltext;
		
	}
	
	public static void main(String[] args)
	{
		new Q00998_FallenAngelSelect(998, Q00998_FallenAngelSelect.class.getSimpleName(), "Fallen Angel - Select");
	}
}
