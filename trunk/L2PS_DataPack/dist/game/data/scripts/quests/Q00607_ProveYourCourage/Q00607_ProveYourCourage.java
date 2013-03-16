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
package quests.Q00607_ProveYourCourage;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00607_ProveYourCourage extends Quest
{
	public Q00607_ProveYourCourage(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(31370);
		addKillId(25309);
		
		questItemIds = new int[]
		{
			7235
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
		
		if (event.equalsIgnoreCase("31370-2.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("31370-4.htm"))
		{
			if (st.getQuestItemsCount(7235) >= 1)
			{
				st.takeItems(7235, -1);
				st.giveItems(7219, 1);
				st.addExpAndSp(0, 10000);
				st.unset("cond");
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(true);
			}
			else
			{
				htmltext = "31370-2r.htm";
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
		
		int cond = st.getInt("cond");
		
		if (cond == 0)
		{
			if (st.getPlayer().getLevel() >= 75)
			{
				if ((player.getAllianceWithVarkaKetra() <= +3) && (st.getQuestItemsCount(7213) == 1))
				{
					htmltext = "31370-1.htm";
					st.set("cond", "1");
					st.setState(State.STARTED);
					st.playSound("ItemSound.quest_accept");
				}
				else
				{
					htmltext = "31370-00.htm";
					st.exitQuest(true);
				}
			}
			else
			{
				htmltext = "31370-0.htm";
				st.exitQuest(true);
			}
		}
		else if ((cond == 1) && (st.getQuestItemsCount(7235) == 0))
		{
			htmltext = "31370-2r.htm";
		}
		else if ((cond == 2) && (st.getQuestItemsCount(7235) >= 1))
		{
			htmltext = "31370-3.htm";
			st.takeItems(7235, -1);
			st.giveItems(7219, 1);
			st.addExpAndSp(10000, 0);
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(true);
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState st = player.getQuestState(getName());
		
		if (npc.getNpcId() == 25309)
		{
			if (player.getParty() != null)
			{
				for (@SuppressWarnings("unused")
				L2PcInstance plr : player.getParty().getMembers())
				{
					if ((st != null) && (st.getInt("cond") == 1))
					{
						st.giveItems(7235, 1);
						st.set("cond", "2");
						st.playSound("ItemSound.quest_itemget");
					}
				}
			}
			else
			{
				if ((st != null) && (st.getInt("cond") == 1))
				{
					st.giveItems(7235, 1);
					st.set("cond", "2");
					st.playSound("ItemSound.quest_itemget");
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00607_ProveYourCourage(607, Q00607_ProveYourCourage.class.getSimpleName(), "Prove Your Courage");
	}
}