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
package quests.Q00168_DeliverSupplies;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00168_DeliverSupplies extends Quest
{
	private static final int JENNA = 30349;
	private static final int ROSELYN = 30355;
	private static final int KRISTIN = 30357;
	private static final int HARANT = 30360;
	private static final int JENNIES_LETTER = 1153;
	private static final int SENTRY_BLADE1 = 1154;
	private static final int SENTRY_BLADE2 = 1155;
	private static final int SENTRY_BLADE3 = 1156;
	private static final int OLD_BRONZE_SWORD = 1157;
	
	public Q00168_DeliverSupplies(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(JENNA);
		addTalkId(JENNA);
		addTalkId(ROSELYN);
		addTalkId(KRISTIN);
		addTalkId(HARANT);
		
		questItemIds = new int[]
		{
			SENTRY_BLADE1,
			OLD_BRONZE_SWORD,
			JENNIES_LETTER,
			SENTRY_BLADE2,
			SENTRY_BLADE3
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
		
		if (event.equalsIgnoreCase("30349-03.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
			st.giveItems(JENNIES_LETTER, 1);
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
		
		int cond = st.getInt("cond");
		
		switch (st.getState())
		{
			case State.CREATED:
				if (player.getRace().ordinal() == 2)
				{
					if ((player.getLevel() >= 3) && (player.getLevel() <= 6))
					{
						htmltext = "30349-02.htm";
					}
					else
					{
						htmltext = "30349-01.htm";
						st.exitQuest(true);
					}
				}
				else
				{
					htmltext = "30349-00.htm";
					st.exitQuest(true);
				}
				break;
			case State.STARTED:
				switch (npc.getNpcId())
				{
					case JENNA:
						if (cond == 1)
						{
							htmltext = "30349-04.htm";
						}
						else if (cond == 2)
						{
							htmltext = "30349-05.htm";
							st.set("cond", "3");
							st.takeItems(SENTRY_BLADE1, 1);
							st.playSound("ItemSound.quest_middle");
						}
						else if (cond == 3)
						{
							htmltext = "30349-07.htm";
						}
						else if (cond == 4)
						{
							htmltext = "30349-06.htm";
							st.takeItems(OLD_BRONZE_SWORD, 2);
							st.rewardItems(57, 820L);
							st.playSound("ItemSound.quest_finish");
							st.exitQuest(false);
						}
						break;
					case HARANT:
						if (cond == 1)
						{
							if (st.getQuestItemsCount(JENNIES_LETTER) == 1)
							{
								htmltext = "30360-01.htm";
								st.takeItems(JENNIES_LETTER, 1);
								st.giveItems(SENTRY_BLADE1, 1);
								st.giveItems(SENTRY_BLADE2, 1);
								st.giveItems(SENTRY_BLADE3, 1);
								st.set("cond", "2");
								st.playSound("ItemSound.quest_middle");
							}
						}
						else if (cond == 2)
						{
							htmltext = "30360-02.htm";
						}
						break;
					case ROSELYN:
						if (cond == 3)
						{
							if ((st.getQuestItemsCount(SENTRY_BLADE1) == 0) && (st.getQuestItemsCount(SENTRY_BLADE2) == 1))
							{
								htmltext = "30355-01.htm";
								st.takeItems(SENTRY_BLADE2, 1);
								st.giveItems(OLD_BRONZE_SWORD, 1);
								if (st.getQuestItemsCount(OLD_BRONZE_SWORD) == 2)
								{
									st.set("cond", "4");
									st.playSound("ItemSound.quest_middle");
								}
							}
							else if (st.getQuestItemsCount(SENTRY_BLADE2) == 0)
							{
								htmltext = "30355-02.htm";
							}
						}
						else if (cond == 4)
						{
							htmltext = "30355-02.htm";
						}
						break;
					case KRISTIN:
						if (cond == 3)
						{
							if ((st.getQuestItemsCount(SENTRY_BLADE3) == 1) && (st.getQuestItemsCount(SENTRY_BLADE1) == 0))
							{
								htmltext = "30357-01.htm";
								st.takeItems(SENTRY_BLADE3, 1);
								st.giveItems(OLD_BRONZE_SWORD, 1);
								if (st.getQuestItemsCount(OLD_BRONZE_SWORD) == 2)
								{
									st.set("cond", "4");
									st.playSound("ItemSound.quest_middle");
								}
							}
							else if (st.getQuestItemsCount(SENTRY_BLADE3) == 0)
							{
								htmltext = "30357-02.htm";
							}
						}
						else if (cond == 4)
						{
							htmltext = "30357-02.htm";
						}
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
		new Q00168_DeliverSupplies(168, Q00168_DeliverSupplies.class.getSimpleName(), "Deliver Supplies");
	}
}