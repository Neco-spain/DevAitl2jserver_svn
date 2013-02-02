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

import com.l2jserver.Config;
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
public class Q00004_LongLiveThePaagrioLord extends Quest
{
	private final static int QUEST_GIFTS[][] =
	{
		{
			30585,
			30566,
			30562,
			30560,
			30559,
			30587
		},
		{
			1541,
			1542,
			1543,
			1544,
			1545,
			1546
		}
	};
	private final static int NAKUSIN = 30578;
	private final static int QUEST_REWARD[] =
	{
		57,
		4
	};
	
	public Q00004_LongLiveThePaagrioLord(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(NAKUSIN);
		addTalkId(NAKUSIN);
		for (int npcId : QUEST_GIFTS[0])
		{
			addTalkId(npcId);
		}
		questItemIds = QUEST_GIFTS[1];
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		
		QuestState qs = player.getQuestState(getName());
		if (qs == null)
		{
			return htmltext;
		}
		
		if (event.equalsIgnoreCase("30578-03.htm"))
		{
			qs.set("cond", "1");
			qs.set("id", "1");
			qs.setState(State.STARTED);
			qs.playSound("ItemSound.quest_accept");
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState qs = player.getQuestState(getName());
		if (qs == null)
		{
			qs = newQuestState(player);
		}
		String htmltext = getNoQuestMsg(player);
		final int cond = qs.getInt("cond");
		final int npcId = npc.getNpcId();
		
		switch (qs.getState())
		{
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
			case State.CREATED:
				if (npcId == NAKUSIN)
				{
					if (player.getRace().ordinal() != 3)
					{
						htmltext = "30578-00.htm";
						qs.exitQuest(true);
					}
					else if (player.getLevel() >= 2)
					{
						htmltext = "30578-02.htm";
					}
					else
					{
						htmltext = "30578-01.htm";
						qs.exitQuest(true);
					}
				}
				break;
			case State.STARTED:
				switch (cond)
				{
					case 1:
						if (npcId == NAKUSIN)
						{
							htmltext = "30578-04.htm";
						}
						else if (npcId == checkNpc(npcId))
						{
							int index = 0;
							for (int i = 0; i < QUEST_GIFTS[1].length; i++)
							{
								if (QUEST_GIFTS[0][i] == checkNpc(npcId))
								{
									index = i;
									break;
								}
							}
							
							if (qs.getQuestItemsCount(QUEST_GIFTS[1][index]) > 0)
							{
								htmltext = String.valueOf(npcId).concat("-02.htm");
							}
							else
							{
								qs.giveItems(QUEST_GIFTS[1][index], 1);
								htmltext = String.valueOf(npcId).concat("-01.htm");
								
								int count = 0;
								for (int item : QUEST_GIFTS[1])
								{
									if (qs.getQuestItemsCount(item) > 0)
									{
										count += 1;
									}
								}
								
								if (count == 6)
								{
									qs.set("cond", "2");
									qs.set("id", "2");
									qs.playSound("ItemSound.quest_middle");
								}
								else
								{
									qs.playSound("ItemSound.quest_itemget");
								}
							}
						}
						break;
					case 2:
						if (npcId == NAKUSIN)
						{
							htmltext = "30578-06.htm";
							qs.giveItems(QUEST_REWARD[1], (long) Config.RATE_QUEST_REWARD);
							qs.giveItems(QUEST_REWARD[0], Math.round(1850 * Config.RATE_QUEST_REWARD_ADENA));
							qs.addExpAndSp(4254, 335);
							for (int item : QUEST_GIFTS[1])
							{
								qs.takeItems(item, -1);
							}
							qs.unset("cond");
							qs.exitQuest(false);
							qs.playSound("ItemSound.quest_finish");
							player.sendPacket(new ExShowScreenMessage(((new CustomMessage("Newbie.Message1", player.getLang())).toString()), 3000));
						}
						break;
				}
				break;
		}
		return htmltext;
	}
	
	private int checkNpc(int npcId)
	{
		for (int id : QUEST_GIFTS[0])
		{
			if (id == npcId)
			{
				return id;
			}
		}
		return 0;
	}
	
	public static void main(String[] args)
	{
		new Q00004_LongLiveThePaagrioLord(4, Q00004_LongLiveThePaagrioLord.class.getSimpleName(), "");
	}
}