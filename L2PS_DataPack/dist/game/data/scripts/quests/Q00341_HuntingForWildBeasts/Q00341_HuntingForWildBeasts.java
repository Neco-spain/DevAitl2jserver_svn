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
package quests.Q00341_HuntingForWildBeasts;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00341_HuntingForWildBeasts extends Quest
{
	private static final int PANO = 30078;
	private static final int BEAR_SKIN = 4259;
	
	public Q00341_HuntingForWildBeasts(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(PANO);
		addTalkId(PANO);
		
		addKillId(20203, 20021, 20310, 20143);
		
		questItemIds = new int[]
		{
			BEAR_SKIN
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
		
		if (event.equalsIgnoreCase("30078-02.htm"))
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
				if ((player.getLevel() >= 20) && (player.getLevel() <= 24))
				{
					htmltext = "30078-01.htm";
				}
				else
				{
					htmltext = "30078-00.htm";
					st.exitQuest(false);
				}
				break;
			case State.STARTED:
				if (st.getQuestItemsCount(BEAR_SKIN) >= 20)
				{
					htmltext = "30078-04.htm";
					st.takeItems(BEAR_SKIN, -1);
					st.rewardItems(57, 3710);
					st.playSound("ItemSound.quest_finish");
					st.exitQuest(true);
				}
				else
				{
					htmltext = "30078-03.htm";
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
		
		if (st.isStarted())
		{
			st.dropQuestItems(BEAR_SKIN, 1, 20, 400000, true);
		}
		
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00341_HuntingForWildBeasts(341, Q00341_HuntingForWildBeasts.class.getSimpleName(), "Hunting For Wild Beasts");
	}
}