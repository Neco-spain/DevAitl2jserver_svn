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

/**
 * Author: RobikBobik L2PS Team
 */
public final class Q00316_DestroyPlaguebringers extends Quest
{
	private static final int ELLIASIN = 30155;
	private static final int WERERAT_FANG = 1042;
	private static final int NORMAL_FANG_REWARD = 60;
	private static final int VAROOL_FOULCLAWS_FANG = 1043;
	private static final int LEADER_FANG_REWARD = 10000;
	private static final int SUKAR_WERERAT = 20040;
	private static final int SUKAR_WERERAT_LEADER = 20047;
	private static final int VAROOL_FOULCLAW = 27020;
	
	private Q00316_DestroyPlaguebringers(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(ELLIASIN);
		addTalkId(ELLIASIN);
		
		addKillId(SUKAR_WERERAT);
		addKillId(SUKAR_WERERAT_LEADER);
		addKillId(VAROOL_FOULCLAW);
		
		questItemIds = new int[]
		{
			WERERAT_FANG,
			VAROOL_FOULCLAWS_FANG
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
		
		if (event.equalsIgnoreCase("30155-04.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30155-08.htm"))
		{
			st.exitQuest(true);
			st.playSound("ItemSound.quest_finish");
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
			if (player.getRace().ordinal() != 1)
			{
				st.exitQuest(true);
				htmltext = "30155-00.htm";
			}
			else if (player.getLevel() < 18)
			{
				st.exitQuest(true);
				htmltext = "30155-02.htm";
			}
			else
			{
				htmltext = "30155-03.htm";
			}
		}
		else
		{
			long normal = st.getQuestItemsCount(WERERAT_FANG);
			long leader = st.getQuestItemsCount(VAROOL_FOULCLAWS_FANG);
			if ((normal != 0) || (leader != 0))
			{
				st.takeItems(WERERAT_FANG, normal);
				st.takeItems(VAROOL_FOULCLAWS_FANG, leader);
				st.rewardItems(57, ((normal * NORMAL_FANG_REWARD) + (leader * LEADER_FANG_REWARD)));
				return "30155-07.htm";
			}
			else
			{
				htmltext = "30155-05.htm";
			}
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
		
		if (st.getInt("cond") == 1)
		{
			if (npc.getNpcId() == VAROOL_FOULCLAW)
			{
				if (st.getQuestItemsCount(VAROOL_FOULCLAWS_FANG) == 0)
				{
					st.giveItems(VAROOL_FOULCLAWS_FANG, 1);
					st.playSound("ItemSound.quest_middle");
				}
			}
			else
			{
				st.giveItems(WERERAT_FANG, 1);
				st.playSound("ItemSound.quest_itemget");
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00316_DestroyPlaguebringers(316, Q00316_DestroyPlaguebringers.class.getSimpleName(), "Destroy Plague Bringers");
	}
}