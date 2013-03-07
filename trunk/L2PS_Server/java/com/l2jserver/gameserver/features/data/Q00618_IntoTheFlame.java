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
public class Q00618_IntoTheFlame extends Quest
{
	private static final int KLEIN = 31540;
	private static final int HILDA = 31271;
	private static final int VACUALITE_ORE = 7265;
	private static final int VACUALITE = 7266;
	private static final int FLOATING_STONE = 7267;
	
	public Q00618_IntoTheFlame(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(KLEIN);
		addTalkId(KLEIN, HILDA);
		
		addKillId(21274, 21275, 21276, 21277, 21282, 21283, 21284, 21285, 21290, 21291, 21292, 21293);
		
		questItemIds = new int[]
		{
			VACUALITE_ORE,
			VACUALITE
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
		
		int cond = st.getInt("cond");
		if ((cond == 0) && (event.equalsIgnoreCase("31540-03.htm")))
		{
			st.setState(State.STARTED);
			st.set("cond", "1");
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("31540-05.htm"))
		{
			if ((cond == 4) && (st.getQuestItemsCount(VACUALITE) > 0))
			{
				st.takeItems(VACUALITE, 1);
				st.giveItems(FLOATING_STONE, 1);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(true);
			}
			else
			{
				htmltext = "31540-03.htm";
			}
		}
		else if ((cond == 1) && (event.equalsIgnoreCase("31271-02.htm")))
		{
			st.set("cond", "2");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31271-05.htm"))
		{
			if ((cond == 3) && (st.getQuestItemsCount(VACUALITE_ORE) == 50))
			{
				st.takeItems(VACUALITE_ORE, -1);
				st.giveItems(VACUALITE, 1);
				st.set("cond", "4");
				st.playSound("ItemSound.quest_middle");
			}
			else
			{
				htmltext = "31271-03.htm";
			}
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
		
		switch (st.getState())
		{
			case State.CREATED:
				if (player.getLevel() < 60)
				{
					htmltext = "31540-01.htm";
					st.exitQuest(true);
				}
				else
				{
					htmltext = "31540-02.htm";
				}
				break;
			case State.STARTED:
				int cond = st.getInt("cond");
				switch (npc.getNpcId())
				{
					case KLEIN:
						if ((cond == 4) && (st.getQuestItemsCount(VACUALITE) > 0))
						{
							htmltext = "31540-04.htm";
						}
						else
						{
							htmltext = "31540-03.htm";
						}
						break;
					case HILDA:
						if (cond == 1)
						{
							htmltext = "31271-01.htm";
						}
						else if ((cond == 3) && (st.getQuestItemsCount(VACUALITE_ORE) == 50))
						{
							htmltext = "31271-04.htm";
						}
						else if (cond == 4)
						{
							htmltext = "31271-06.htm";
						}
						else
						{
							htmltext = "31271-03.htm";
						}
						break;
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		L2PcInstance partyMember = getRandomPartyMember(player, 2);
		if (partyMember == null)
		{
			return null;
		}
		
		QuestState st = partyMember.getQuestState(getName());
		
		if (st.dropQuestItems(VACUALITE_ORE, 1, 50, 500000, true))
		{
			st.set("cond", "3");
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00618_IntoTheFlame(618, Q00618_IntoTheFlame.class.getSimpleName(), "Into The Flame");
	}
}