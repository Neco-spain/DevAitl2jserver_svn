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
import com.l2jserver.util.Rnd;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00274_AgainstWolfMen extends Quest
{
	private static final int MARAKU_WEREWOLF_HEAD = 1477;
	private static final int NECKLACE_OF_VALOR = 1507;
	private static final int NECKLACE_OF_COURAGE = 1506;
	private static final int MARAKU_WOLFMEN_TOTEM = 1501;
	
	public Q00274_AgainstWolfMen(int scriptId, String name, String descr)
	{
		super(scriptId, name, descr);
		
		addStartNpc(30569);
		addTalkId(30569);
		
		addKillId(20363, 20364);
		
		questItemIds = new int[]
		{
			MARAKU_WEREWOLF_HEAD
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
		
		if (event.equalsIgnoreCase("30569-03.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		String htmltext = getNoQuestMsg(player);
		if (st == null)
		{
			return htmltext;
		}
		
		long totems = st.getQuestItemsCount(MARAKU_WOLFMEN_TOTEM);
		
		switch (st.getState())
		{
			case State.CREATED:
				if (player.getRace().ordinal() == 3)
				{
					if (player.getLevel() > 8)
					{
						if ((st.getQuestItemsCount(NECKLACE_OF_VALOR) > 0) || (st.getQuestItemsCount(NECKLACE_OF_COURAGE) > 0))
						{
							htmltext = "30569-02.htm";
						}
						else
						{
							htmltext = "30569-07.htm";
							st.exitQuest(true);
						}
					}
					else
					{
						htmltext = "30569-01.htm";
						st.exitQuest(true);
					}
				}
				else
				{
					htmltext = "30569-00.htm";
					st.exitQuest(true);
				}
				break;
			case State.STARTED:
				if (st.getQuestItemsCount(MARAKU_WEREWOLF_HEAD) < 40)
				{
					htmltext = "30569-04.htm";
				}
				else
				{
					int amount = 3500;
					if (totems > 0)
					{
						amount += 600 * totems;
					}
					htmltext = "30569-05.htm";
					st.playSound("ItemSound.quest_finish");
					st.giveItems(57, amount);
					st.takeItems(MARAKU_WEREWOLF_HEAD, -1);
					st.takeItems(MARAKU_WOLFMEN_TOTEM, -1);
					st.exitQuest(true);
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
			long count = st.getQuestItemsCount(MARAKU_WEREWOLF_HEAD);
			
			if (count < 40)
			{
				if (count < 39)
				{
					st.playSound("ItemSound.quest_itemget");
				}
				else
				{
					st.playSound("ItemSound.quest_middle");
					st.set("cond", "2");
				}
				st.giveItems(MARAKU_WEREWOLF_HEAD, 1);
				if (Rnd.get(100) <= 15)
				{
					st.giveItems(MARAKU_WOLFMEN_TOTEM, 1);
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00274_AgainstWolfMen(274, Q00274_AgainstWolfMen.class.getSimpleName(), "Against Wolf Men");
	}
}