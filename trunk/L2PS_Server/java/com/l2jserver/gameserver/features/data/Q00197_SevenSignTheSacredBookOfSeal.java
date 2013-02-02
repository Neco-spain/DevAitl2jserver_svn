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

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00197_SevenSignTheSacredBookOfSeal extends Quest
{
	// NPCs
	private static final int WOOD = 32593;
	private static final int ORVEN = 30857;
	private static final int LEOPARD = 32594;
	private static final int LAWRENCE = 32595;
	private static final int SOFIA = 32596;
	
	// MOB
	private static final int SHILENSEVIL = 27396;
	
	// ITEMS
	private static final int TEXT = 13829;
	private static final int SCULPTURE = 14355;
	
	private boolean ShilensevilOnSpawn = false;
	
	public Q00197_SevenSignTheSacredBookOfSeal(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(WOOD);
		addTalkId(WOOD);
		addTalkId(ORVEN);
		addTalkId(LEOPARD);
		addTalkId(LAWRENCE);
		addTalkId(SOFIA);
		addKillId(SHILENSEVIL);
		
		questItemIds = new int[]
		{
			TEXT,
			SCULPTURE
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
		
		if (npc.getNpcId() == WOOD)
		{
			if ("32593-04.htm".equals(event))
			{
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
			}
			
			else if ("32593-08.htm".equals(event))
			{
				st.takeItems(TEXT, 1);
				st.takeItems(SCULPTURE, 1);
				st.addExpAndSp(25000000, 2500000);
				st.unset("cond");
				st.setState(State.COMPLETED);
				st.exitQuest(false);
				st.playSound("ItemSound.quest_finish");
			}
		}
		else if (npc.getNpcId() == ORVEN)
		{
			if ("30857-04.htm".equals(event))
			{
				st.set("cond", "2");
				st.playSound("ItemSound.quest_middle");
			}
		}
		else if (npc.getNpcId() == LEOPARD)
		{
			if ("32594-03.htm".equals(event))
			{
				st.set("cond", "3");
				st.playSound("ItemSound.quest_middle");
			}
		}
		else if (npc.getNpcId() == LAWRENCE)
		{
			if ("32595-04.htm".equals(event))
			{
				if (ShilensevilOnSpawn)
				{
					htmltext = "<html><body>You are not the owner of that item!</body></html>";
				}
				else
				{
					L2MonsterInstance monster = (L2MonsterInstance) addSpawn(SHILENSEVIL, 152520, -57486, -3430, 0, false, 300000, true);
					monster.broadcastPacket(new NpcSay(monster.getObjectId(), 0, monster.getNpcId(), NpcStringId.YOU_ARE_NOT_THE_OWNER_OF_THAT_ITEM));
					monster.setRunning();
					monster.addDamageHate(player, 0, 999);
					monster.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, st.getPlayer());
					ShilensevilOnSpawn = true;
					startQuestTimer("spawnS", 300000, npc, player);
				}
			}
			else if ("spawnS".equals(event))
			{
				if (ShilensevilOnSpawn)
				{
					ShilensevilOnSpawn = false;
					npc.broadcastPacket(new NpcSay(SHILENSEVIL, 0, SHILENSEVIL, NpcStringId.NEXT_TIME_YOU_WILL_NOT_ESCAPE));
					htmltext = "";
				}
				else
				{
					htmltext = "";
				}
			}
			
			else if ("32595-08.htm".equals(event))
			{
				st.set("cond", "5");
				st.playSound("ItemSound.quest_middle");
			}
		}
		else if (npc.getNpcId() == SOFIA)
		{
			if ("32596-04.htm".equals(event))
			{
				st.set("cond", "6");
				st.giveItems(TEXT, 1);
				st.playSound("ItemSound.quest_middle");
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
		
		QuestState fourth = player.getQuestState("Q00196_SevenSignSealOfTheEmperor");
		
		if (npc.getNpcId() == WOOD)
		{
			switch (st.getState())
			{
				case State.CREATED:
					if ((fourth != null) && (fourth.getState() == State.COMPLETED) && (player.getLevel() >= 79))
					{
						htmltext = "32593-01.htm";
					}
					else
					{
						htmltext = "32593-00.htm";
						st.exitQuest(true);
					}
					break;
				
				case State.STARTED:
					if ((st.getInt("cond") >= 1) && (st.getInt("cond") <= 5))
					{
						htmltext = "32593-05.htm";
					}
					else if (st.getInt("cond") == 6)
					{
						htmltext = "32593-06.htm";
					}
					break;
			}
		}
		else if (npc.getNpcId() == ORVEN)
		{
			if (st.getState() == State.STARTED)
			{
				if (st.getInt("cond") == 1)
				{
					htmltext = "30857-01.htm";
				}
				else if (st.getInt("cond") >= 2)
				{
					htmltext = "30857-05.htm";
				}
			}
		}
		else if (npc.getNpcId() == LEOPARD)
		{
			if (st.getState() == State.STARTED)
			{
				if (st.getInt("cond") == 2)
				{
					htmltext = "32594-01.htm";
				}
				else if (st.getInt("cond") >= 3)
				{
					htmltext = "32594-04.htm";
				}
			}
		}
		else if (npc.getNpcId() == LAWRENCE)
		{
			if (st.getState() == State.STARTED)
			{
				if (st.getInt("cond") == 3)
				{
					htmltext = "32595-01.htm";
				}
				else if (st.getInt("cond") == 4)
				{
					htmltext = "32595-05.htm";
				}
				else if (st.getInt("cond") >= 5)
				{
					htmltext = "32595-09.htm";
				}
			}
		}
		else if (npc.getNpcId() == SOFIA)
		{
			if (st.getState() == State.STARTED)
			{
				if (st.getInt("cond") == 5)
				{
					htmltext = "32596-01.htm";
				}
				else if (st.getInt("cond") == 6)
				{
					htmltext = "32596-05.htm";
				}
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
			return super.onKill(npc, player, isPet);
		}
		
		if ((npc.getNpcId() == SHILENSEVIL) && (st.getInt("cond") == 3))
		{
			ShilensevilOnSpawn = false;
			npc.broadcastPacket(new NpcSay(npc.getObjectId(), 0, npc.getNpcId(), "... You may have won this time... But next time, I will surely capture you!"));
			st.giveItems(SCULPTURE, 1);
			st.set("cond", "4");
			st.playSound("ItemSound.quest_middle");
		}
		return super.onKill(npc, player, isPet);
	}
	
	public static void main(String[] args)
	{
		new Q00197_SevenSignTheSacredBookOfSeal(197, Q00197_SevenSignTheSacredBookOfSeal.class.getSimpleName(), "Seven Signs, the Sacred Book of Seal");
	}
}