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
package quests.Q00155_FindSirWindawood;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00155_FindSirWindawood extends Quest
{
	private static final int ABELLOS = 30042;
	private static final int WINDAWOOD = 30311;
	private static final int OFFICIAL_LETTER = 1019;
	private static final int HASTE_POTION = 734;
	
	public Q00155_FindSirWindawood(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(ABELLOS);
		addTalkId(ABELLOS);
		addTalkId(WINDAWOOD);
		
		questItemIds = new int[]
		{
			OFFICIAL_LETTER
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
		
		if (event.equalsIgnoreCase("30042-02.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.giveItems(OFFICIAL_LETTER, 1);
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
				if ((player.getLevel() >= 3) && (player.getLevel() <= 6))
				{
					htmltext = "30042-01.htm";
				}
				else
				{
					htmltext = "30042-01a.htm";
					st.exitQuest(true);
				}
				break;
			case State.STARTED:
				switch (npc.getNpcId())
				{
					case ABELLOS:
						htmltext = "30042-03.htm";
						break;
					case WINDAWOOD:
						if (st.getQuestItemsCount(OFFICIAL_LETTER) == 1)
						{
							htmltext = "30311-01.htm";
							st.takeItems(OFFICIAL_LETTER, -1);
							st.rewardItems(HASTE_POTION, 1);
							st.playSound("ItemSound.quest_finish");
							st.unset("cond");
							st.exitQuest(false);
						}
						break;
				}
				break;
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00155_FindSirWindawood(155, Q00155_FindSirWindawood.class.getSimpleName(), "Find Sir Windawood");
	}
}