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

import com.l2jserver.gameserver.customs.CustomMessage;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00283_TheFewTheProudTheBrave extends Quest
{
	private static int PERWAN = 32133;
	private static int CRIMSON_SPIDER = 22244;
	private static int CRIMSON_SPIDER_CLAW = 9747;
	
	public Q00283_TheFewTheProudTheBrave(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(PERWAN);
		addTalkId(PERWAN);
		
		addKillId(CRIMSON_SPIDER);
		
		questItemIds = new int[]
		{
			CRIMSON_SPIDER_CLAW
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
		
		int onlyone = st.getInt("onlyone");
		
		if (event.equalsIgnoreCase("32133-03.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("32133-06.htm"))
		{
			long count = st.getQuestItemsCount(CRIMSON_SPIDER_CLAW);
			if (count > 0)
			{
				st.takeItems(CRIMSON_SPIDER_CLAW, -1);
				st.giveItems(57, 45 * count);
				st.playSound("ItemSound.quest_middle");
				if (onlyone == 0)
				{
					player.sendPacket(new ExShowScreenMessage(((new CustomMessage("Newbie.Message4", player.getLang())).toString()), 3000));
					st.set("onlyone", "1");
				}
			}
		}
		else if (event.equalsIgnoreCase("32133-08.htm"))
		{
			st.takeItems(CRIMSON_SPIDER_CLAW, -1);
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(true);
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
		
		int id = st.getState();
		long claw = st.getQuestItemsCount(CRIMSON_SPIDER_CLAW);
		
		if ((id == State.CREATED) && (npc.getNpcId() == PERWAN))
		{
			if (player.getLevel() < 15)
			{
				htmltext = "32133-02.htm";
				st.exitQuest(true);
			}
			else
			{
				htmltext = "32133-01.htm";
			}
		}
		else if ((id == State.STARTED) && (npc.getNpcId() == PERWAN))
		{
			if (claw > 0)
			{
				htmltext = "32133-05.htm";
			}
			else
			{
				htmltext = "32133-04.htm";
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
		
		int npcId = npc.getNpcId();
		int chance = getRandom(100);
		
		if ((npcId == CRIMSON_SPIDER) && (chance < 35))
		{
			st.giveItems(CRIMSON_SPIDER_CLAW, 1);
			st.playSound("ItemSound.quest_itemget");
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00283_TheFewTheProudTheBrave(283, Q00283_TheFewTheProudTheBrave.class.getSimpleName(), "The Few The Proud The Brave");
	}
}