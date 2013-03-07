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

import com.l2jserver.Config;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;
import com.l2jserver.util.Rnd;

/**
 * Author: RobikBobik L2PS Team
 */
public final class Q00692_HowtoOpposeEvil extends Quest
{
	private static final int DILIOS = 32549;
	private static final int KUTRAN = 32550;
	private static final int[] DESTRUCTION_MOBS =
	{
		22537,
		22538,
		22539,
		22540,
		22541,
		22542,
		22543,
		22544,
		22546,
		22547,
		22548,
		22549,
		22550,
		22551,
		22552,
		22593,
		22596,
		22597
	};
	private static final int[] IMMORTALITY_MOBS =
	{
		22510,
		22511,
		22512,
		22513,
		22514,
		22515
	};
	private static final int[] ITEMS =
	{
		13796,
		13841,
		13857
	};
	private static int CHANCE = 10;
	
	public Q00692_HowtoOpposeEvil(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(DILIOS);
		addTalkId(DILIOS);
		addTalkId(KUTRAN);
		
		for (int i : DESTRUCTION_MOBS)
		{
			addKillId(i);
		}
		for (int i : IMMORTALITY_MOBS)
		{
			addKillId(i);
		}
		
		questItemIds = new int[]
		{
			ITEMS[0],
			ITEMS[1]
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
		
		if (event.equalsIgnoreCase("32549-03.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("32550-04.htm"))
		{
			st.set("cond", "3");
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
		int cond = st.getInt("cond");
		
		if (st.getState() == State.CREATED)
		{
			if (player.getLevel() >= 75)
			{
				htmltext = "32549-01.htm";
			}
			else
			{
				htmltext = "32549-00.htm";
			}
		}
		else
		{
			if (npc.getNpcId() == DILIOS)
			{
				switch (cond)
				{
					case 1:
						if (st.getQuestItemsCount(ITEMS[2]) >= 1)
						{
							htmltext = "32549-04.htm";
							st.set("cond", "2");
						}
						break;
					case 2:
						htmltext = "32549-05.htm";
						break;
				}
			}
			else
			{
				switch (cond)
				{
					case 2:
						htmltext = "32550-01.htm";
						break;
					case 3:
						htmltext = "32550-04.htm";
						break;
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public final String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		L2PcInstance partyMember = getRandomPartyMember(player, 3);
		if (partyMember == null)
		{
			return null;
		}
		
		final int npcId = npc.getNpcId();
		
		if ((CHANCE * Config.RATE_QUEST_DROP) >= Rnd.get(100))
		{
			if (Util.contains(DESTRUCTION_MOBS, npcId))
			{
				st.giveItems(ITEMS[1], 3);
				st.playSound("ItemSound.quest_itemget");
			}
			else if (Util.contains(IMMORTALITY_MOBS, npcId))
			{
				st.giveItems(ITEMS[0], 3);
				st.playSound("ItemSound.quest_itemget");
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00692_HowtoOpposeEvil(692, Q00692_HowtoOpposeEvil.class.getSimpleName(), "How to Oppose Evil");
	}
}
