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
package quests.Q00277_GatekeepersOffering;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00277_GatekeepersOffering extends Quest
{
	private static final int TAMIL = 30576;
	private static final int STARSTONE = 1572;
	private static final int GATEKEEPER_CHARM = 1658;
	private static final int GRAYSTONE_GOLEM = 20333;
	
	public Q00277_GatekeepersOffering(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(TAMIL);
		addTalkId(TAMIL);
		
		addKillId(GRAYSTONE_GOLEM);
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
		
		if (event.equalsIgnoreCase("30576-03.htm"))
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
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.CREATED:
				if ((player.getLevel() >= 15) && (player.getLevel() <= 21))
				{
					htmltext = "30576-02.htm";
				}
				else
				{
					htmltext = "30576-01.htm";
					st.exitQuest(true);
				}
				break;
			case State.STARTED:
				int cond = st.getInt("cond");
				if ((cond == 1) && (st.getQuestItemsCount(STARSTONE) < 20))
				{
					htmltext = "30576-04.htm";
				}
				else if ((cond == 2) && (st.getQuestItemsCount(STARSTONE) >= 20))
				{
					htmltext = "30576-05.htm";
					st.takeItems(STARSTONE, -1);
					st.rewardItems(GATEKEEPER_CHARM, 2);
					st.exitQuest(true);
					st.playSound("ItemSound.quest_finish");
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
		
		if ((st.getInt("cond") == 1) && (st.getRandom(100) < 20))
		{
			st.giveItems(STARSTONE, 1);
			if (st.getQuestItemsCount(STARSTONE) == 20)
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
		new Q00277_GatekeepersOffering(277, Q00277_GatekeepersOffering.class.getSimpleName(), "Gatekeepers Offering");
	}
}