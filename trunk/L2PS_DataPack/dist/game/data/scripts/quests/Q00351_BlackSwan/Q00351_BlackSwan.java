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
package quests.Q00351_BlackSwan;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00351_BlackSwan extends Quest
{
	private static final int GOSTA = 30916;
	private static final int IASON_HEINE = 30969;
	private static final int ROMAN = 30897;
	private static final int ORDER_OF_GOSTA = 4296;
	private static final int LIZARD_FANG = 4297;
	private static final int BARREL_OF_LEAGUE = 4298;
	private static final int BILL_OF_IASON_HEINE = 4310;
	
	public Q00351_BlackSwan(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(GOSTA);
		addTalkId(GOSTA, IASON_HEINE, ROMAN);
		
		addKillId(20784, 20785, 21639, 21640, 21642, 21643);
		
		questItemIds = new int[]
		{
			ORDER_OF_GOSTA,
			BARREL_OF_LEAGUE,
			LIZARD_FANG
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
		
		if (event.equalsIgnoreCase("30916-03.htm"))
		{
			st.setState(State.STARTED);
			st.set("cond", "1");
			st.giveItems(ORDER_OF_GOSTA, 1);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30969-02a.htm"))
		{
			long lizardFangs = st.getQuestItemsCount(LIZARD_FANG);
			if (lizardFangs > 0)
			{
				htmltext = "30969-02.htm";
				st.takeItems(LIZARD_FANG, -1);
				st.rewardItems(57, lizardFangs * 20);
			}
		}
		else if (event.equalsIgnoreCase("30969-03a.htm"))
		{
			long barrels = st.getQuestItemsCount(BARREL_OF_LEAGUE);
			if (barrels > 0)
			{
				htmltext = "30969-03.htm";
				st.takeItems(BARREL_OF_LEAGUE, -1);
				st.rewardItems(BILL_OF_IASON_HEINE, barrels);
				if (st.getInt("cond") == 1)
				{
					st.set("cond", "2");
					st.playSound("ItemSound.quest_middle");
				}
			}
		}
		else if (event.equalsIgnoreCase("30969-06.htm"))
		{
			if ((st.getQuestItemsCount(BARREL_OF_LEAGUE) == 0) && (st.getQuestItemsCount(LIZARD_FANG) == 0))
			{
				htmltext = "30969-07.htm";
				st.exitQuest(true);
				st.playSound("ItemSound.quest_finish");
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = Quest.getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.CREATED:
				if ((player.getLevel() >= 32) && (player.getLevel() <= 36))
				{
					htmltext = "30916-01.htm";
				}
				else
				{
					htmltext = "30916-00.htm";
					st.exitQuest(true);
				}
				break;
			case State.STARTED:
				switch (npc.getNpcId())
				{
					case GOSTA:
						htmltext = "30916-04.htm";
						break;
					case IASON_HEINE:
						htmltext = "30969-01.htm";
						break;
					case ROMAN:
						if (st.getQuestItemsCount(BILL_OF_IASON_HEINE) > 0)
						{
							htmltext = "30897-01.htm";
						}
						else
						{
							htmltext = "30897-02.htm";
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
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		int random = st.getRandom(20);
		
		if (random < 10)
		{
			if (random < 5)
			{
				st.giveItems(LIZARD_FANG, 1);
			}
			else
			{
				st.giveItems(LIZARD_FANG, 2);
			}
			
			if (random == 0)
			{
				st.giveItems(BARREL_OF_LEAGUE, 1);
				st.playSound("ItemSound.quest_middle");
			}
			else
			{
				st.playSound("ItemSound.quest_itemget");
			}
		}
		else if (random == 10)
		{
			st.giveItems(BARREL_OF_LEAGUE, 1);
			st.playSound("ItemSound.quest_middle");
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00351_BlackSwan(351, Q00351_BlackSwan.class.getSimpleName(), "Black Swan");
	}
}