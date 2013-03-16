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
package quests.Q00317_CatchTheWind;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00317_CatchTheWind extends Quest
{
	private static final int RIZRAELL = 30361;
	private static final int WIND_SHARD = 1078;
	
	public Q00317_CatchTheWind(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(RIZRAELL);
		addTalkId(RIZRAELL);
		
		addKillId(20036, 20044);
		
		questItemIds = new int[]
		{
			WIND_SHARD
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
		
		if (event.equalsIgnoreCase("30361-04.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30361-08.htm"))
		{
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(true);
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
				if ((player.getLevel() >= 18) && (player.getLevel() <= 23))
				{
					htmltext = "30361-03.htm";
				}
				else
				{
					htmltext = "30361-02.htm";
					st.exitQuest(true);
				}
				break;
			
			case State.STARTED:
				long shards = st.getQuestItemsCount(WIND_SHARD);
				if (shards == 0)
				{
					htmltext = "30361-05.htm";
				}
				else
				{
					long reward = (40 * shards) + (shards >= 10 ? 2988 : 0);
					htmltext = "30361-07.htm";
					st.takeItems(WIND_SHARD, -1);
					st.rewardItems(57, reward);
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
		
		if (st.isStarted() && (st.getRandom(100) < 50))
		{
			st.giveItems(WIND_SHARD, 1);
			st.playSound("ItemSound.quest_itemget");
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00317_CatchTheWind(317, Q00317_CatchTheWind.class.getSimpleName(), "Catch The Wind");
	}
}