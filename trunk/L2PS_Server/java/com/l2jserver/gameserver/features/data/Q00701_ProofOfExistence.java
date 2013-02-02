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

/**
 * Author: RobikBobik L2PS Team
 */
public final class Q00701_ProofOfExistence extends Quest
{
	private static final int ARTIUS = 32559;
	private static final int DEADMANS_REMAINS = 13875;
	private static final int[] MOBS =
	{
		22606,
		22607,
		22608,
		22609
	};
	
	// Chance
	private static final int DROP_CHANCE = 80;
	
	public Q00701_ProofOfExistence(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(ARTIUS);
		addTalkId(ARTIUS);
		
		for (int i : MOBS)
		{
			addKillId(i);
		}
		
		questItemIds = new int[]
		{
			DEADMANS_REMAINS
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
		
		if (event.equalsIgnoreCase("32559-03.htm"))
		{
			st.setState(State.STARTED);
			st.set("cond", "1");
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("32559-quit.htm"))
		{
			st.unset("cond");
			st.exitQuest(true);
			st.playSound("ItemSound.quest_finish");
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
		
		if (npc.getNpcId() == ARTIUS)
		{
			QuestState first = player.getQuestState("Q10273_GoodDayToFly");
			if ((first != null) && (first.getState() == State.COMPLETED) && (st.getState() == State.CREATED) && (player.getLevel() >= 78))
			{
				htmltext = "32559-01.htm";
			}
			else
			{
				switch (st.getInt("cond"))
				{
					case 0:
						htmltext = "32559-00.htm";
						break;
					case 1:
						long count = st.getQuestItemsCount(DEADMANS_REMAINS);
						if (count > 0)
						{
							st.takeItems(DEADMANS_REMAINS, -1);
							st.giveReward(57, count * 2500);
							st.playSound("ItemSound.quest_itemget");
							htmltext = "32559-06.htm";
						}
						else
						{
							htmltext = "32559-04.htm";
						}
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
		
		if (st.getInt("cond") == 1)
		{
			switch (npc.getNpcId())
			{
				case 22606:
				case 22607:
				case 22608:
				case 22609:
					int chance = (int) (DROP_CHANCE * Config.RATE_QUEST_DROP);
					int numItems = chance / 1000;
					chance = chance % 1000;
					if (st.getRandom(1000) < chance)
					{
						numItems++;
					}
					if (numItems > 0)
					{
						st.giveItems(DEADMANS_REMAINS, 1);
						st.playSound("ItemSound.quest_itemget");
					}
					break;
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00701_ProofOfExistence(701, Q00701_ProofOfExistence.class.getSimpleName(), "Proof of Existence");
	}
}
