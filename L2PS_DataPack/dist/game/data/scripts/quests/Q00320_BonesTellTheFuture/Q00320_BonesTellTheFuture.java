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
package quests.Q00320_BonesTellTheFuture;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.base.Race;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00320_BonesTellTheFuture extends Quest
{
	private final int BONE_FRAGMENT = 809;
	
	public Q00320_BonesTellTheFuture(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(30359);
		addTalkId(30359);
		
		addKillId(20517, 20518, 20022, 20455);
		
		questItemIds = new int[]
		{
			BONE_FRAGMENT
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
		
		if (event.equalsIgnoreCase("30359-04.htm"))
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
				if (player.getRace() != Race.DarkElf)
				{
					htmltext = "30359-00.htm";
					st.exitQuest(true);
				}
				else if ((player.getLevel() >= 10) && (player.getLevel() <= 18))
				{
					htmltext = "30359-03.htm";
				}
				else
				{
					htmltext = "30359-02.htm";
					st.exitQuest(true);
				}
				break;
			case State.STARTED:
				if (st.getQuestItemsCount(BONE_FRAGMENT) < 10)
				{
					htmltext = "30359-05.htm";
				}
				else
				{
					htmltext = "30359-06.htm";
					st.takeItems(BONE_FRAGMENT, -1);
					st.rewardItems(57, 8470);
					st.playSound("ItemSound.quest_finish");
					st.exitQuest(true);
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
			if (st.dropQuestItems(BONE_FRAGMENT, 1, 10, 200000, true))
			{
				st.set("cond", "2");
			}
		}
		
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00320_BonesTellTheFuture(320, Q00320_BonesTellTheFuture.class.getSimpleName(), "Bones Tell The Future");
	}
}