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
public class Q00294_CovertBusiness extends Quest
{
	private static final int BatFang = 1491;
	private static final int RingOfRaccoon = 1508;
	private static final int Barded = 20370;
	private static final int Blade = 20480;
	private static final int Keef = 30534;
	
	public Q00294_CovertBusiness(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(Keef);
		addTalkId(Keef);
		
		addKillId(Barded, Blade);
		
		questItemIds = new int[]
		{
			BatFang
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
		
		if (event.equalsIgnoreCase("30534-03.htm"))
		{
			st.setState(State.STARTED);
			st.set("cond", "1");
			st.playSound("ItemSound.quest_accept");
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		int cond = st.getInt("cond");
		
		switch (st.getState())
		{
			case State.CREATED:
				if (player.getRace().ordinal() == 4)
				{
					if ((player.getLevel() >= 10) && (player.getLevel() <= 16))
					{
						htmltext = "30534-02.htm";
					}
					else
					{
						htmltext = "30534-01.htm";
						st.exitQuest(true);
					}
				}
				else
				{
					htmltext = "30534-00.htm";
					st.exitQuest(true);
				}
				break;
			case State.STARTED:
				if (cond == 1)
				{
					htmltext = "30534-04.htm";
				}
				else if (cond == 2)
				{
					htmltext = "30534-05.htm";
					st.takeItems(BatFang, -1);
					st.giveItems(RingOfRaccoon, 1);
					st.giveItems(57, 2400);
					st.addExpAndSp(0, 600);
					st.exitQuest(true);
					st.playSound("ItemSound.quest_finish");
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
		
		if (st.getInt("cond") == 1)
		{
			int qty = 0;
			int count = (int) st.getQuestItemsCount(BatFang);
			
			qty = 1 + st.getRandom(4);
			
			if ((count + qty) >= 100)
			{
				qty = 100 - count;
				st.set("cond", "2");
				st.playSound("ItemSound.quest_middle");
			}
			else
			{
				st.playSound("ItemSound.quest_itemget");
			}
			
			st.giveItems(BatFang, qty);
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00294_CovertBusiness(294, Q00294_CovertBusiness.class.getSimpleName(), "Covert Business");
	}
}