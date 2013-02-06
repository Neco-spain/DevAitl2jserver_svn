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
public final class Q00116_BeyondTheHillsOfWinter extends Quest
{
	private static final int FILAUR = 30535;
	private static final int OBI = 32052;
	private static final int BANDAGE = 1833;
	private static final int ESTONE = 5589;
	private static final int TKEY = 1661;
	private static final int SGOODS = 8098;
	private static final int SS = 1463;
	private static final int ADENA = 57;
	
	private static final int[] QUESTITEMS =
	{
		SGOODS
	};
	
	public Q00116_BeyondTheHillsOfWinter(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(FILAUR);
		addTalkId(FILAUR);
		addTalkId(OBI);
		questItemIds = QUESTITEMS;
	}
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		
		if (event.equalsIgnoreCase("30535-02.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30535-05.htm"))
		{
			st.giveItems(SGOODS, 1);
			st.playSound("ItemSound.quest_itemget");
			st.set("cond", "3");
		}
		else if (event.equalsIgnoreCase("32052-01.htm"))
		{
			htmltext = "32052-01.htm";
		}
		else if (event.equalsIgnoreCase("materials"))
		{
			st.takeItems(SGOODS, -1);
			st.playSound("ItemSound.quest_itemget");
			st.giveReward(SS, 1650);
			st.addExpAndSp(82792, 4981);
			htmltext = "32052-02.htm";
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(false);
		}
		else if (event.equalsIgnoreCase("adena"))
		{
			st.takeItems(SGOODS, -1);
			st.playSound("ItemSound.quest_itemget");
			st.giveReward(ADENA, 16500);
			st.addExpAndSp(82792, 4981);
			htmltext = "32052-02.htm";
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(false);
		}
		else
		{
			htmltext = Quest.getNoQuestMsg(player);
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
		
		if (st.getState() == State.COMPLETED)
		{
			htmltext = getAlreadyCompletedMsg(player);
		}
		else
		{
			if (npcId == FILAUR)
			{
				if (cond == 0)
				{
					if (player.getLevel() >= 30)
					{
						htmltext = "30535-01.htm";
					}
					else
					{
						htmltext = "30535-00.htm";
						st.exitQuest(true);
					}
				}
				else if (cond == 1)
				{
					if ((st.getQuestItemsCount(BANDAGE) >= 20) && (st.getQuestItemsCount(ESTONE) >= 5) && (st.getQuestItemsCount(TKEY) >= 10))
					{
						htmltext = "30535-03.htm";
						st.takeItems(BANDAGE, 20);
						st.takeItems(ESTONE, 5);
						st.takeItems(TKEY, 10);
						st.set("cond", "2");
					}
					else
					{
						htmltext = "30535-04.htm";
					}
				}
				else if (cond == 2)
				{
					htmltext = "30535-03.htm";
				}
				else if (cond == 3)
				{
					htmltext = "30535-05.htm";
				}
			}
			else if ((npcId == OBI) && (cond == 3) && (st.getQuestItemsCount(SGOODS) == 1))
			{
				htmltext = "32052-00.htm";
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00116_BeyondTheHillsOfWinter(116, Q00116_BeyondTheHillsOfWinter.class.getSimpleName(), "Beyond the Hills of Winter");
	}
}
