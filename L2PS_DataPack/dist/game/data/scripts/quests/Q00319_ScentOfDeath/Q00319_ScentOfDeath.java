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
package quests.Q00319_ScentOfDeath;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00319_ScentOfDeath extends Quest
{
	private static final int MINALESS = 30138;
	private static final int ZOMBIE_SKIN = 1045;
	
	public Q00319_ScentOfDeath(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(MINALESS);
		addTalkId(MINALESS);
		
		addKillId(20015, 20020);
		
		questItemIds = new int[]
		{
			ZOMBIE_SKIN
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
		
		if (event.equalsIgnoreCase("30138-04.htm"))
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
		
		switch (st.getState())
		{
			case State.CREATED:
				if ((player.getLevel() >= 11) && (player.getLevel() <= 18))
				{
					htmltext = "30138-03.htm";
				}
				else
				{
					htmltext = "30138-02.htm";
					st.exitQuest(true);
				}
				break;
			case State.STARTED:
				if (st.getQuestItemsCount(ZOMBIE_SKIN) == 5)
				{
					htmltext = "30138-06.htm";
					st.takeItems(ZOMBIE_SKIN, 5);
					st.rewardItems(57, 3350);
					st.rewardItems(1060, 1);
					st.playSound("ItemSound.quest_finish");
					st.exitQuest(true);
				}
				else
				{
					htmltext = "30138-05.htm";
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
			if (st.dropQuestItems(ZOMBIE_SKIN, 1, 5, 300000, true))
			{
				st.set("cond", "2");
			}
		}
		
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00319_ScentOfDeath(319, Q00319_ScentOfDeath.class.getSimpleName(), "Scent Of Death");
	}
}