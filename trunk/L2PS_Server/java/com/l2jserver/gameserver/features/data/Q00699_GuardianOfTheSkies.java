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
public final class Q00699_GuardianOfTheSkies extends Quest
{
	private static final int LEKON = 32557;
	private static final int GOLDEN_FEATHER = 13871;
	private static final int[] MOBS =
	{
		22614,
		22615,
		25623,
		25633
	};
	private static final int DROP_CHANCE = 80;
	
	public Q00699_GuardianOfTheSkies(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(LEKON);
		addTalkId(LEKON);
		
		for (int i : MOBS)
		{
			addKillId(i);
		}
		
		questItemIds = new int[]
		{
			GOLDEN_FEATHER
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
		
		if (event.equalsIgnoreCase("32557-03.htm"))
		{
			st.setState(State.STARTED);
			st.set("cond", "1");
		}
		else if (event.equalsIgnoreCase("32557-quit.htm"))
		{
			st.unset("cond");
			st.exitQuest(true);
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
		
		if (npc.getNpcId() == LEKON)
		{
			QuestState QS = player.getQuestState("Q10273_GoodDayToFly");
			if ((QS != null) && (QS.getState() == State.COMPLETED) && (st.getState() == State.CREATED) && (player.getLevel() >= 75))
			{
				htmltext = "32557-01.htm";
			}
			else
			{
				switch (st.getInt("cond"))
				{
					case 0:
						htmltext = "32557-00.htm";
						break;
					case 1:
						long count = st.getQuestItemsCount(GOLDEN_FEATHER);
						if (count > 0)
						{
							st.takeItems(GOLDEN_FEATHER, -1);
							st.giveReward(57, count * 2300);
							htmltext = "32557-06.htm";
						}
						else
						{
							htmltext = "32557-04.htm";
						}
						break;
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public final String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
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
				case 22614:
				case 22615:
				case 25623:
				case 25633:
					int chance = (int) (DROP_CHANCE * Config.RATE_QUEST_DROP);
					int numItems = chance / 100;
					chance = chance % 100;
					if (st.getRandom(100) < chance)
					{
						numItems++;
					}
					if (numItems > 0)
					{
						st.giveItems(GOLDEN_FEATHER, 1);
					}
					break;
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00699_GuardianOfTheSkies(699, Q00699_GuardianOfTheSkies.class.getSimpleName(), "Guardian of the Skies");
	}
}
