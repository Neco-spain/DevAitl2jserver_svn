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
package quests.Q00649_ALooterAndARailroadMan;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00649_ALooterAndARailroadMan extends Quest
{
	private static final int THIEF_GUILD_MARK = 8099;
	private static final int OBI = 32052;
	
	public Q00649_ALooterAndARailroadMan(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(OBI);
		addTalkId(OBI);
		
		addKillId(new int[]
		{
			22017,
			22018,
			22019,
			22021,
			22022,
			22023,
			22024,
			22026
		});
		
		questItemIds = new int[]
		{
			THIEF_GUILD_MARK
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
		
		if (event.equalsIgnoreCase("32052-1.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("32052-3.htm"))
		{
			if (st.getQuestItemsCount(THIEF_GUILD_MARK) < 200L)
			{
				htmltext = "32052-3a.htm";
			}
			else
			{
				st.takeItems(THIEF_GUILD_MARK, -1L);
				st.rewardItems(57, 21698L);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(true);
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
				if (player.getLevel() >= 30)
				{
					htmltext = "32052-0.htm";
				}
				else
				{
					htmltext = "32052-0a.htm";
					st.exitQuest(true);
				}
				break;
			case State.STARTED:
				if (st.getQuestItemsCount(THIEF_GUILD_MARK) == 200L)
				{
					htmltext = "32052-2.htm";
				}
				else
				{
					htmltext = "32052-2a.htm";
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
		
		if ((st.getInt("cond") == 1) && (st.getRandom(10) < 8))
		{
			st.giveItems(THIEF_GUILD_MARK, 1L);
			if (st.getQuestItemsCount(THIEF_GUILD_MARK) == 200L)
			{
				st.set("cond", "2");
				st.playSound("ItemSound.quest_middle");
			}
			else
			{
				st.playSound("ItemSound.quest_itemget");
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00649_ALooterAndARailroadMan(649, Q00649_ALooterAndARailroadMan.class.getSimpleName(), "A Looter And A Railroad Man");
	}
}