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
package quests.Q00298_LizardmensConspiracy;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00298_LizardmensConspiracy extends Quest
{
	private static final int PRAGA = 30333;
	private static final int ROHMER = 30344;
	private static final int PATROL_REPORT = 7182;
	private static final int WHITE_GEM = 7183;
	private static final int RED_GEM = 7184;
	
	public Q00298_LizardmensConspiracy(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(PRAGA);
		addTalkId(PRAGA);
		addTalkId(ROHMER);
		
		addKillId(20926, 20927, 20922, 20923, 20924);
		
		questItemIds = new int[]
		{
			PATROL_REPORT,
			WHITE_GEM,
			RED_GEM
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
		
		if (event.equalsIgnoreCase("30333-1.htm"))
		{
			st.set("cond", "1");
			st.giveItems(PATROL_REPORT, 1);
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30344-1.htm"))
		{
			st.takeItems(PATROL_REPORT, 1);
			st.set("cond", "2");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("30344-3.htm"))
		{
			if ((st.getQuestItemsCount(WHITE_GEM) >= 50) && (st.getQuestItemsCount(RED_GEM) >= 50))
			{
				st.takeItems(WHITE_GEM, -1);
				st.takeItems(RED_GEM, -1);
				st.addExpAndSp(0, 42000);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(true);
			}
			else
			{
				htmltext = "30344-4.htm";
			}
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
		
		int cond = st.getInt("cond");
		
		switch (st.getState())
		{
			case State.CREATED:
				if ((player.getLevel() >= 25) && (player.getLevel() <= 34))
				{
					htmltext = "30333-0a.htm";
				}
				else
				{
					htmltext = "30333-0b.htm";
					st.exitQuest(true);
				}
				break;
			case State.STARTED:
				switch (npc.getNpcId())
				{
					case PRAGA:
						htmltext = "30333-2.htm";
						break;
					case ROHMER:
						if (cond == 1)
						{
							if (st.getQuestItemsCount(PATROL_REPORT) == 1)
							{
								htmltext = "30344-0.htm";
							}
							else
							{
								htmltext = "30344-0a.htm";
							}
						}
						else if ((cond == 2) || (cond == 3))
						{
							htmltext = "30344-2.htm";
						}
						break;
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		L2PcInstance partyMember = getRandomPartyMember(player, 2);
		if (partyMember == null)
		{
			return null;
		}
		
		QuestState st = partyMember.getQuestState(getName());
		
		if (st.getRandom(100) < 62)
		{
			switch (npc.getNpcId())
			{
				case 20926:
				case 20927:
					if (st.getQuestItemsCount(RED_GEM) < 50)
					{
						st.giveItems(RED_GEM, 1);
						if ((st.getQuestItemsCount(WHITE_GEM) >= 50) && (st.getQuestItemsCount(RED_GEM) >= 50))
						{
							st.set("cond", "3");
							st.playSound("ItemSound.quest_middle");
						}
						else
						{
							st.playSound("ItemSound.quest_itemget");
						}
					}
					break;
				case 20922:
				case 20923:
				case 20924:
					if (st.getQuestItemsCount(WHITE_GEM) < 50)
					{
						st.giveItems(WHITE_GEM, 1);
						if ((st.getQuestItemsCount(RED_GEM) >= 50) && (st.getQuestItemsCount(WHITE_GEM) >= 50))
						{
							st.set("cond", "3");
							st.playSound("ItemSound.quest_middle");
						}
						else
						{
							st.playSound("ItemSound.quest_itemget");
						}
					}
					break;
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00298_LizardmensConspiracy(298, Q00298_LizardmensConspiracy.class.getSimpleName(), "Lizardmens Conspiracy");
	}
}