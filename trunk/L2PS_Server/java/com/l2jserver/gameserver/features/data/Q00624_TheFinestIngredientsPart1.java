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
public class Q00624_TheFinestIngredientsPart1 extends Quest
{
	private static final int NEPENTHES = 21319;
	private static final int ATROX = 21321;
	private static final int ATROXSPAWN = 21317;
	private static final int BANDERSNATCH = 21314;
	private static final int TRUNK = 7202;
	private static final int FOOT = 7203;
	private static final int SPICE = 7204;
	private static final int CRYSTAL = 7080;
	private static final int SAUCE = 7205;
	
	public Q00624_TheFinestIngredientsPart1(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(31521);
		addTalkId(31521);
		
		addKillId(NEPENTHES, ATROX, ATROXSPAWN, BANDERSNATCH);
		
		questItemIds = new int[]
		{
			TRUNK,
			FOOT,
			SPICE
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
		
		if (event.equalsIgnoreCase("31521-02.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("31521-05.htm"))
		{
			if ((st.getQuestItemsCount(TRUNK) == 50) && (st.getQuestItemsCount(FOOT) == 50) && (st.getQuestItemsCount(SPICE) == 50))
			{
				st.takeItems(TRUNK, -1);
				st.takeItems(FOOT, -1);
				st.takeItems(SPICE, -1);
				st.giveItems(CRYSTAL, 1);
				st.giveItems(SAUCE, 1);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(true);
			}
			else
			{
				st.set("cond", "1");
				htmltext = "31521-07.htm";
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
				if (player.getLevel() >= 73)
				{
					htmltext = "31521-01.htm";
				}
				else
				{
					htmltext = "31521-03.htm";
					st.exitQuest(true);
				}
				break;
			case State.STARTED:
				int cond = st.getInt("cond");
				if (cond == 1)
				{
					htmltext = "31521-06.htm";
				}
				else if (cond == 2)
				{
					if ((st.getQuestItemsCount(TRUNK) == 50) && (st.getQuestItemsCount(FOOT) == 50) && (st.getQuestItemsCount(SPICE) == 50))
					{
						htmltext = "31521-04.htm";
					}
					else
					{
						htmltext = "31521-07.htm";
					}
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		QuestState st = checkPlayerCondition(player, npc, "cond", "1");
		if (st == null)
		{
			return null;
		}
		
		switch (npc.getNpcId())
		{
			case NEPENTHES:
				if (st.getQuestItemsCount(TRUNK) < 50)
				{
					st.dropQuestItems(TRUNK, 1, 50, 1000000, true);
				}
				break;
			case ATROXSPAWN:
			case ATROX:
				if (st.getQuestItemsCount(SPICE) < 50)
				{
					st.dropQuestItems(SPICE, 1, 50, 1000000, true);
				}
				break;
			case BANDERSNATCH:
				if (st.getQuestItemsCount(FOOT) < 50)
				{
					st.dropQuestItems(FOOT, 1, 50, 1000000, true);
				}
				break;
		}
		onKillCheck(st);
		
		return null;
	}
	
	private void onKillCheck(QuestState st)
	{
		if ((st.getQuestItemsCount(TRUNK) == 50) && (st.getQuestItemsCount(FOOT) == 50) && (st.getQuestItemsCount(SPICE) == 50))
		{
			st.playSound("ItemSound.quest_middle");
			st.set("cond", "2");
		}
		else
		{
			st.playSound("ItemSound.quest_itemget");
		}
	}
	
	public static void main(String[] args)
	{
		new Q00624_TheFinestIngredientsPart1(624, Q00624_TheFinestIngredientsPart1.class.getSimpleName(), "The Finest Ingredients Part 1");
	}
}