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

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.QuestState.QuestType;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Rumble in the Base (461)
 * @author malyelfik
 */
public class Q00461_RumbleInTheBase extends Quest
{
	// NPC
	private static final int STAN = 30200;
	
	// Item
	private static final int SHINY_SALMON = 15503;
	private static final int SHOES_STRING_OF_SEL_MAHUM = 16382;
	
	// Mobs
	private static final Map<Integer, Integer> MONSTERS = new HashMap<>();
	
	static
	{
		MONSTERS.put(22780, 581);
		MONSTERS.put(22781, 772);
		MONSTERS.put(22782, 581);
		MONSTERS.put(22783, 563);
		MONSTERS.put(22784, 581);
		MONSTERS.put(22785, 271);
		MONSTERS.put(18908, 782);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = null;
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		if (event.equalsIgnoreCase("30200-05.htm"))
		{
			st.startQuest();
			htmltext = event;
		}
		else if (event.equalsIgnoreCase("30200-04.htm"))
		{
			htmltext = event;
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
		
		final QuestState prev = player.getQuestState(Q00252_ItSmellsDelicious.class.getSimpleName());
		switch (st.getState())
		{
			case State.CREATED:
				htmltext = ((player.getLevel() >= 82) && (prev != null) && prev.isCompleted()) ? "30200-01.htm" : "30200-02.htm";
				break;
			case State.STARTED:
				if (st.isCond(1))
				{
					htmltext = "30200-06.html";
				}
				else
				{
					st.addExpAndSp(224784, 342528);
					st.exitQuest(QuestType.DAILY, true);
					htmltext = "30200-07.html";
				}
				break;
			case State.COMPLETED:
				if (!st.isNowAvailable())
				{
					htmltext = "30200-03.htm";
				}
				else
				{
					st.setState(State.CREATED);
					htmltext = ((player.getLevel() >= 82) && (prev != null) && (prev.getState() == State.COMPLETED)) ? "30200-01.htm" : "30200-02.htm";
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState st = null;
		if (getRandom(1000) >= MONSTERS.get(npc.getNpcId()))
		{
			return super.onKill(npc, player, isPet);
		}
		
		if (npc.getNpcId() == 18908)
		{
			st = player.getQuestState(getName());
			if ((st != null) && st.isCond(1) && (st.getQuestItemsCount(SHINY_SALMON) < 5))
			{
				st.giveItems(SHINY_SALMON, 1);
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				if ((st.getQuestItemsCount(SHINY_SALMON) >= 5) && (st.getQuestItemsCount(SHOES_STRING_OF_SEL_MAHUM) >= 10))
				{
					st.setCond(2, true);
				}
			}
		}
		else
		{
			final L2PcInstance member = getRandomPartyMember(player, "1");
			if (member == null)
			{
				return super.onKill(npc, player, isPet);
			}
			
			st = member.getQuestState(getName());
			if (st.getQuestItemsCount(SHOES_STRING_OF_SEL_MAHUM) < 10)
			{
				st.giveItems(SHOES_STRING_OF_SEL_MAHUM, 1);
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				if ((st.getQuestItemsCount(SHINY_SALMON) >= 5) && (st.getQuestItemsCount(SHOES_STRING_OF_SEL_MAHUM) >= 10))
				{
					st.setCond(2, true);
				}
			}
		}
		return super.onKill(npc, player, isPet);
	}
	
	public Q00461_RumbleInTheBase(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(STAN);
		addTalkId(STAN);
		addKillId(MONSTERS.keySet());
		
		registerQuestItems(SHINY_SALMON, SHOES_STRING_OF_SEL_MAHUM);
	}
	
	public static void main(String[] args)
	{
		new Q00461_RumbleInTheBase(461, Q00461_RumbleInTheBase.class.getSimpleName(), "Rumble in the Base");
	}
}
