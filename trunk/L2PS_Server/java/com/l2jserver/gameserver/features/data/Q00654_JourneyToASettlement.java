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
public final class Q00654_JourneyToASettlement extends Quest
{
	private static final int SPIRIT = 31453;
	private static final int[] MOBS =
	{
		21294,
		21295
	};
	private static final int ANTELOPE_SKIN = 8072;
	private static final int FRINTEZZA_FORCE_SCROLL = 8073;
	
	private static final int[] QUESTITEMS =
	{
		ANTELOPE_SKIN,
		FRINTEZZA_FORCE_SCROLL
	};
	
	public Q00654_JourneyToASettlement(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(SPIRIT);
		addTalkId(SPIRIT);
		for (int i : MOBS)
		{
			addKillId(i);
		}
		questItemIds = QUESTITEMS;
	}
	
	@Override
	public final String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if (event.equalsIgnoreCase("31453-2.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("31453-3.htm"))
		{
			st.set("cond", "2");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31453-5.htm") && st.hasQuestItems(ANTELOPE_SKIN))
		{
			st.takeItems(ANTELOPE_SKIN, 1);
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(true);
			st.giveItems(FRINTEZZA_FORCE_SCROLL, 1);
			st.unset("cond");
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
			case State.CREATED:
			{
				QuestState qs = st.getPlayer().getQuestState("Q00119_LastImperialPrince");
				if (player.getLevel() < 74)
				{
					htmltext = "31453-6.htm";
					st.exitQuest(true);
					break;
				}
				else if (qs == null)
				{
					htmltext = "31453-7.htm";
					st.exitQuest(true);
					break;
				}
				else if (!qs.isCompleted())
				{
					htmltext = "31453-7.htm";
					st.exitQuest(true);
					break;
				}
				else
				{
					htmltext = "31453-1.htm";
				}
				break;
			}
			case State.STARTED:
			{
				if ((npcId == SPIRIT) && (cond != 3))
				{
					htmltext = "31453-2.htm";
					break;
				}
				else if ((npcId == SPIRIT) && (cond == 3))
				{
					htmltext = "31453-4.htm";
					break;
				}
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
		
		if (st.getInt("cond") == 2)
		{
			st.dropQuestItems(ANTELOPE_SKIN, 1, 1, 1, false, 5, true);
		}
		if (st.hasQuestItems(ANTELOPE_SKIN))
		{
			st.set("cond", "3");
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00654_JourneyToASettlement(654, Q00654_JourneyToASettlement.class.getSimpleName(), "Journey to a Settlement");
	}
}
