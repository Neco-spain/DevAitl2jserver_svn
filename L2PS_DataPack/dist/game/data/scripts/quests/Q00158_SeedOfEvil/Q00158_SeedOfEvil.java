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
package quests.Q00158_SeedOfEvil;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public final class Q00158_SeedOfEvil extends Quest
{
	private static final int BIOTIN = 30031;
	private static final int CLAY_TABLET = 1025;
	private static final int NERKAS = 27016;
	
	private Q00158_SeedOfEvil(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(BIOTIN);
		addTalkId(BIOTIN);
		
		addKillId(NERKAS);
		
		questItemIds = new int[]
		{
			CLAY_TABLET
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
		
		if (event.equalsIgnoreCase("1"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
			htmltext = "30031-04.htm";
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
				if (player.getLevel() < 21)
				{
					st.exitQuest(true);
					htmltext = "30031-02.htm";
				}
				else
				{
					htmltext = "30031-03.htm";
				}
				break;
			case State.STARTED:
				if (st.getQuestItemsCount(CLAY_TABLET) != 0)
				{
					st.exitQuest(false);
					st.rewardItems(57, 1495);
					st.giveItems(956, 1);
					st.addExpAndSp(17818, 927);
					st.unset("cond");
					st.playSound("ItemSound.quest_finish");
					htmltext = "30031-06.htm";
				}
				else
				{
					htmltext = "30031-05.htm";
				}
				break;
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
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
		
		if (npc.getNpcId() == NERKAS)
		{
			if (st.getQuestItemsCount(CLAY_TABLET) == 0)
			{
				st.giveItems(CLAY_TABLET, 1);
				st.playSound("ItemSound.quest_middle");
				st.set("cond", "2");
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00158_SeedOfEvil(158, Q00158_SeedOfEvil.class.getSimpleName(), "Seed Of Evil");
	}
}