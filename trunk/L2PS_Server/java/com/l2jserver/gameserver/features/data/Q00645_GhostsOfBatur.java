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
package com.l2jserver.gameserver.features.data;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00645_GhostsOfBatur extends Quest
{
	private static final int KARUDA = 32017;
	private static final int CURSED_BURIAL = 14861;
	private static final int DROP_CHANCE = 50;
	private static final int[] MOBS =
	{
		22703,
		22704,
		22705,
		22706
	};
	
	public Q00645_GhostsOfBatur(int id, String name, String descr)
	{
		super(id, name, descr);
		this.questItemIds = new int[]
		{
			CURSED_BURIAL
		};
		addStartNpc(KARUDA);
		addTalkId(KARUDA);
		
		for (int i : MOBS)
		{
			addKillId(i);
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		String htmltext = event;
		if (st == null)
		{
			return htmltext;
		}
		if (event.equalsIgnoreCase("32017-3.htm"))
		{
			if (player.getLevel() < 80)
			{
				htmltext = "32017-2.htm";
				st.exitQuest(true);
			}
			else
			{
				st.set("cond", "1");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
			}
		}
		else if (event.startsWith("reward")) // dynasty recipes
		{
			if (st.getQuestItemsCount(CURSED_BURIAL) >= 500)
			{
				int rewardId = Integer.parseInt(event.substring(7));
				if (rewardId > 0)
				{
					st.takeItems(CURSED_BURIAL, 500);
					st.giveItems(rewardId, 1);
					st.playSound("ItemSound.quest_itemget");
					htmltext = "32017-5c.htm";
				}
			}
			else
			{
				htmltext = "32017-7.htm";
			}
		}
		else if (event.equalsIgnoreCase("LEO")) // Leonard
		{
			if (st.getQuestItemsCount(CURSED_BURIAL) >= 8)
			{
				st.takeItems(CURSED_BURIAL, 8);
				st.giveItems(9628, 1);
				st.playSound("ItemSound.quest_itemget");
				htmltext = "32017-5c.htm";
			}
			else
			{
				htmltext = "32017-7.htm";
			}
		}
		else if (event.equalsIgnoreCase("ADA")) // Adamantine
		{
			if (st.getQuestItemsCount(CURSED_BURIAL) >= 15)
			{
				st.takeItems(CURSED_BURIAL, 15);
				st.giveItems(9629, 1);
				st.playSound("ItemSound.quest_itemget");
				htmltext = "32017-5c.htm";
			}
			else
			{
				htmltext = "32017-7.htm";
			}
		}
		else if (event.equalsIgnoreCase("ORI")) // Orichalcum
		{
			if (st.getQuestItemsCount(CURSED_BURIAL) >= 12)
			{
				st.takeItems(CURSED_BURIAL, 12);
				st.giveItems(9630, 1);
				st.playSound("ItemSound.quest_itemget");
				htmltext = "32017-5c.htm";
			}
			else
			{
				htmltext = "32017-7.htm";
			}
		}
		else if (event.equalsIgnoreCase("32017-8.htm"))
		{
			st.exitQuest(true);
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
			htmltext = "32017-1.htm";
		}
		else if (cond == 1)
		{
			if (st.getQuestItemsCount(CURSED_BURIAL) > 0)
			{
				htmltext = "32017-5b.htm";
			}
			else
			{
				htmltext = "32017-5a.htm";
			}
		}
		else
		{
			htmltext = "32017-2.htm";
			st.exitQuest(true);
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		int npcId = npc.getNpcId();
		QuestState st = player.getQuestState(getName());
		L2PcInstance partyMember = getRandomPartyMember(player);
		if ((partyMember == null) || (st == null))
		{
			return null;
		}
		
		if ((st.getInt("cond") == 1) && Util.contains(MOBS, npcId))
		{
			st.dropQuestItems(CURSED_BURIAL, 1, 1, 99999, true, DROP_CHANCE, true);
		}
		return super.onKill(npc, player, isPet);
	}
	
	public static void main(String[] args)
	{
		new Q00645_GhostsOfBatur(645, Q00645_GhostsOfBatur.class.getSimpleName(), "Ghosts of Batur");
	}
}