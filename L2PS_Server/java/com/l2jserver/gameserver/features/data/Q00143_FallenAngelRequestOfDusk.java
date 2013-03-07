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
public class Q00143_FallenAngelRequestOfDusk extends Quest
{
	private final static int NATOOLS = 30894;
	private final static int TOBIAS = 30297;
	private final static int CASIAN = 30612;
	private final static int ROCK = 32368;
	private final static int ANGEL = 32369;
	private final static int MonsterAngel = 27338;
	private final static int SEALED_PATH = 10354;
	private final static int PATH = 10355;
	private final static int EMPTY_CRYSTAL = 10356;
	private final static int MEDICINE = 10357;
	private final static int MESSAGE = 10358;
	private int isAngelSpawned = 0;
	
	public Q00143_FallenAngelRequestOfDusk(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addTalkId(NATOOLS, TOBIAS, CASIAN, ROCK, ANGEL);
		
		questItemIds = new int[]
		{
			SEALED_PATH,
			PATH,
			EMPTY_CRYSTAL,
			MEDICINE,
			MESSAGE
		};
	}
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		if (event.equalsIgnoreCase("30894-01.htm"))
		{
			st.set("cond", "1");
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30894-03.htm"))
		{
			st.set("cond", "2");
			st.playSound("ItemSound.quest_middle");
			st.giveItems(SEALED_PATH, 1);
		}
		else if (event.equalsIgnoreCase("30297-04.htm"))
		{
			st.set("cond", "3");
			st.unset("talk");
			st.playSound("ItemSound.quest_middle");
			st.giveItems(PATH, 1);
			st.giveItems(EMPTY_CRYSTAL, 1);
		}
		else if (event.equalsIgnoreCase("30612-07.htm"))
		{
			st.set("cond", "4");
			st.unset("talk");
			st.giveItems(MEDICINE, 1);
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("32368-02.htm"))
		{
			if (isAngelSpawned == 0)
			{
				addSpawn(ANGEL, -21882, 186730, -4320, 0, false, 900000);
				isAngelSpawned = 1;
				startQuestTimer("angel_cleanup", 900000, null, player);
			}
		}
		else if (event.equalsIgnoreCase("32369-10.htm"))
		{
			st.set("cond", "5");
			st.unset("talk");
			st.takeItems(EMPTY_CRYSTAL, -1);
			st.giveItems(MESSAGE, 1);
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("angel_cleanup"))
		{
			if (isAngelSpawned == 1)
			{
				isAngelSpawned = 0;
			}
		}
		return htmltext;
	}
	
	@Override
	public final String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		final int cond = st.getInt("cond");
		final int npcId = npc.getNpcId();
		final int id = st.getState();
		
		if (id == State.CREATED)
		{
			return htmltext;
		}
		
		if (id == State.COMPLETED)
		{
			htmltext = getAlreadyCompletedMsg(player);
		}
		else if (npcId == NATOOLS)
		{
			if (cond == 1)
			{
				htmltext = "30894-01.htm";
			}
			else if (cond == 2)
			{
				htmltext = "30894-04.htm";
			}
		}
		else if (npcId == TOBIAS)
		{
			if (cond == 2)
			{
				if (st.getInt("talk") == 1)
				{
					htmltext = "30297-02.htm";
				}
				else
				{
					htmltext = "30297-01.htm";
					st.takeItems(SEALED_PATH, -1);
					st.set("talk", "1");
				}
			}
			else if (cond == 3)
			{
				htmltext = "30297-05.htm";
			}
			else if (cond == 5)
			{
				htmltext = "30297-06.htm";
				st.playSound("ItemSound.quest_finish");
				st.giveItems(57, 89046);
				st.takeItems(MESSAGE, -1);
				st.exitQuest(false);
				if ((player.getLevel() >= 38) && (player.getLevel() <= 43))
				{
					st.addExpAndSp(223036, 13901);
				}
			}
		}
		else if (npcId == CASIAN)
		{
			if (cond == 3)
			{
				if (st.getInt("talk") == 1)
				{
					htmltext = "30612-02.htm";
				}
				else
				{
					htmltext = "30612-01.htm";
					st.takeItems(PATH, -1);
					st.set("talk", "1");
				}
			}
			else if (cond == 4)
			{
				htmltext = "30612-07.htm";
			}
		}
		else if (npcId == ROCK)
		{
			if (cond == 4)
			{
				htmltext = "32368-01.htm";
			}
		}
		else if (npcId == ANGEL)
		{
			if (cond == 4)
			{
				if (st.getInt("talk") == 1)
				{
					htmltext = "32369-02.htm";
				}
				else
				{
					htmltext = "32369-01.htm";
					st.takeItems(MEDICINE, -1);
					st.set("talk", "1");
				}
			}
			else if (cond == 5)
			{
				htmltext = "32369-10.htm";
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00143_FallenAngelRequestOfDusk(143, Q00143_FallenAngelRequestOfDusk.class.getSimpleName(), "Fallen Angel Request Of Dusk");
	}
}