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
public final class Q00172_NewHorizons extends Quest
{
	private static int ZENYA = 32140;
	private static int RAGARA = 32163;
	
	private static int SCROLL_OF_ESCAPE_GIRAN = 7559;
	private static int MARK_OF_TRAVELER = 7570;
	
	public Q00172_NewHorizons(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(ZENYA);
		addTalkId(ZENYA);
		addTalkId(RAGARA);
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
		
		if (event.equalsIgnoreCase("32140-03.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("32163-02.htm"))
		{
			st.unset("cond");
			st.giveItems(SCROLL_OF_ESCAPE_GIRAN, 1);
			st.giveItems(MARK_OF_TRAVELER, 1);
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(false);
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
		int npcId = npc.getNpcId();
		
		switch (st.getState())
		{
			case State.CREATED:
				if (npcId == ZENYA)
				{
					if ((player.getLevel() >= 3) && (player.getRace().ordinal() == 5))
					{
						htmltext = "32140-01.htm";
					}
					else
					{
						htmltext = "32140-02.htm";
						st.exitQuest(true);
					}
				}
				break;
			case State.STARTED:
				if (npcId == ZENYA)
				{
					if (cond == 1)
					{
						htmltext = "32140-04.htm";
					}
				}
				else if (npcId == RAGARA)
				{
					if (cond == 1)
					{
						htmltext = "32163-01.htm";
					}
				}
				break;
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00172_NewHorizons(172, Q00172_NewHorizons.class.getSimpleName(), "New Horizons");
	}
}