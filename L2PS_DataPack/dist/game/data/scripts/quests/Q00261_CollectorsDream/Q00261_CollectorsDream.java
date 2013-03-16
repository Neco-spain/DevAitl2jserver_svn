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
package quests.Q00261_CollectorsDream;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00261_CollectorsDream extends Quest
{
	private final static int ALSHUPES = 30222;
	private final static int GIANT_SPIDER_LEG = 1087;
	private final static int ADENA = 57;
	
	public Q00261_CollectorsDream(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(ALSHUPES);
		addTalkId(ALSHUPES);
		
		addKillId(20308, 20460, 20466);
		
		questItemIds = new int[]
		{
			GIANT_SPIDER_LEG
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
		
		if (event.equalsIgnoreCase("30222-03.htm"))
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
				if ((player.getLevel() >= 15) && (player.getLevel() <= 21))
				{
					htmltext = "30222-02.htm";
				}
				else
				{
					htmltext = "30222-01.htm";
					st.exitQuest(true);
				}
				break;
			
			case State.STARTED:
				if (st.getInt("cond") == 2)
				{
					htmltext = "30222-05.htm";
					st.takeItems(GIANT_SPIDER_LEG, -1);
					st.rewardItems(ADENA, 1000);
					st.addExpAndSp(2000, 0);
					st.exitQuest(true);
					st.playSound("ItemSound.quest_finish");
					player.sendMessage("Last duty complete. Now go find the Newbie Guide.");
				}
				else
				{
					htmltext = "30222-04.htm";
				}
				break;
			
			case State.COMPLETED:
				htmltext = Quest.getAlreadyCompletedMsg(player);
				break;
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
		
		if ((st.getInt("cond") == 1) && (st.getQuestItemsCount(GIANT_SPIDER_LEG) < 8))
		{
			st.giveItems(GIANT_SPIDER_LEG, 1);
			if (st.getQuestItemsCount(GIANT_SPIDER_LEG) == 8)
			{
				st.playSound("ItemSound.quest_middle");
				st.set("cond", "2");
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
		new Q00261_CollectorsDream(261, Q00261_CollectorsDream.class.getSimpleName(), "Collectors Dream");
	}
}