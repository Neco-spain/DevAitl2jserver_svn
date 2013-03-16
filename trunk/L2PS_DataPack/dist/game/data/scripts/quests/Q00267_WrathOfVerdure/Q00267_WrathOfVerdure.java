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
package quests.Q00267_WrathOfVerdure;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00267_WrathOfVerdure extends Quest
{
	private static final int GOBLIN_CLUB = 1335;
	private static final int SILVERY_LEAF = 1340;
	private static final int TREANT_BREMEC = 31853;
	private static final int GOBLIN = 20325;
	
	public Q00267_WrathOfVerdure(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(TREANT_BREMEC);
		addTalkId(TREANT_BREMEC);
		
		addKillId(GOBLIN);
		
		questItemIds = new int[]
		{
			GOBLIN_CLUB
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
		
		if (event.equalsIgnoreCase("31853-03.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("31853-06.htm"))
		{
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(true);
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
		
		switch (st.getState())
		{
			case State.CREATED:
				if (player.getRace().ordinal() == 1)
				{
					if ((player.getLevel() >= 4) && (player.getLevel() <= 9))
					{
						htmltext = "31853-02.htm";
					}
					else
					{
						htmltext = "31853-01.htm";
						st.exitQuest(true);
					}
				}
				else
				{
					htmltext = "31853-00.htm";
					st.exitQuest(true);
				}
				break;
			
			case State.STARTED:
				int count = (int) st.getQuestItemsCount(GOBLIN_CLUB);
				
				if (count > 0)
				{
					htmltext = "31853-05.htm";
					st.takeItems(GOBLIN_CLUB, -1);
					st.rewardItems(SILVERY_LEAF, count);
				}
				else
				{
					htmltext = "31853-04.htm";
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
		
		if ((st.getInt("cond") == 1) && (st.getRandom(10) < 5))
		{
			st.giveItems(GOBLIN_CLUB, 1);
			st.playSound("ItemSound.quest_itemget");
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00267_WrathOfVerdure(267, Q00267_WrathOfVerdure.class.getSimpleName(), "Wrath Of Verdure");
	}
}