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

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00194_SevenSignContractOfMammon extends Quest
{
	// NPC
	private static final int ATHEBALDT = 30760;
	private static final int COLIN = 32571;
	private static final int FROG = 32572;
	private static final int TESS = 32573;
	private static final int KUTA = 32574;
	private static final int CLAUDIA = 31001;
	
	// ITEMS
	private static final int INTRODUCTION = 13818;
	private static final int FROG_KING_BEAD = 13820;
	private static final int CANDY_POUCH = 13821;
	private static final int NATIVES_GLOVE = 13819;
	
	public Q00194_SevenSignContractOfMammon(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(ATHEBALDT);
		addTalkId(ATHEBALDT);
		addTalkId(COLIN);
		addTalkId(FROG);
		addTalkId(TESS);
		addTalkId(KUTA);
		addTalkId(CLAUDIA);
		
		questItemIds = new int[]
		{
			INTRODUCTION,
			FROG_KING_BEAD,
			CANDY_POUCH,
			NATIVES_GLOVE
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
		if (npc.getNpcId() == ATHEBALDT)
		{
			if ("30760-02.htm".equals(event))
			{
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
			}
			else if ("30760-07.htm".equals(event))
			{
				st.set("cond", "3");
				st.giveItems(INTRODUCTION, 1);
				st.playSound("ItemSound.quest_middle");
			}
			else if ("10".equals(event))
			{
				st.set("cond", "2");
				st.playSound("ItemSound.quest_middle");
				player.showQuestMovie(10);
				return "";
			}
		}
		else if (npc.getNpcId() == COLIN)
		{
			if ("32571-04.htm".equals(event))
			{
				st.set("cond", "4");
				st.takeItems(INTRODUCTION, 1);
				transformPlayer(npc, player, 6201);
				st.playSound("ItemSound.quest_middle");
			}
			if (("32571-06.htm".equals(event)) || ("32571-14.htm".equals(event)) || ("32571-22.htm".equals(event)))
			{
				if (player.isTransformed())
				{
					player.untransform();
				}
			}
			else if ("32571-08.htm".equals(event))
			{
				transformPlayer(npc, player, 6201);
			}
			else if ("32571-10.htm".equals(event))
			{
				st.set("cond", "6");
				st.takeItems(FROG_KING_BEAD, 1);
				st.playSound("ItemSound.quest_middle");
			}
			else if ("32571-12.htm".equals(event))
			{
				st.set("cond", "7");
				transformPlayer(npc, player, 6202);
				st.playSound("ItemSound.quest_middle");
			}
			else if ("32571-16.htm".equals(event))
			{
				transformPlayer(npc, player, 6202);
			}
			else if ("32571-18.htm".equals(event))
			{
				st.set("cond", "9");
				st.takeItems(CANDY_POUCH, 1);
				st.playSound("ItemSound.quest_middle");
			}
			else if ("32571-20.htm".equals(event))
			{
				st.set("cond", "10");
				transformPlayer(npc, player, 6203);
				st.playSound("ItemSound.quest_middle");
			}
			else if ("32571-24.htm".equals(event))
			{
				transformPlayer(npc, player, 6203);
			}
			else if ("32571-26.htm".equals(event))
			{
				st.set("cond", "12");
				st.takeItems(NATIVES_GLOVE, 1);
				st.playSound("ItemSound.quest_middle");
			}
		}
		else if (npc.getNpcId() == FROG)
		{
			if ("32572-04.htm".equals(event))
			{
				st.set("cond", "5");
				st.giveItems(FROG_KING_BEAD, 1);
				st.playSound("ItemSound.quest_middle");
			}
		}
		else if (npc.getNpcId() == TESS)
		{
			if ("32573-03.htm".equals(event))
			{
				st.set("cond", "8");
				st.giveItems(CANDY_POUCH, 1);
				st.playSound("ItemSound.quest_middle");
			}
		}
		else if (npc.getNpcId() == KUTA)
		{
			if ("32574-04.htm".equals(event))
			{
				st.set("cond", "11");
				st.giveItems(NATIVES_GLOVE, 1);
				st.playSound("ItemSound.quest_middle");
			}
		}
		else if (npc.getNpcId() == CLAUDIA)
		{
			if ("31001-03.htm".equals(event))
			{
				st.addExpAndSp(25000000, 2500000);
				st.exitQuest(false);
				st.playSound("ItemSound.quest_finish");
			}
		}
		return htmltext;
	}
	
	private void transformPlayer(L2Npc npc, L2PcInstance player, int transId)
	{
		if (player.isTransformed())
		{
			player.untransform();
			try
			{
				Thread.sleep(2000L);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		L2Effect arr$[] = player.getAllEffects();
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			L2Effect effect = arr$[i$];
			if (effect.getAbnormalType().equalsIgnoreCase("speed_up"))
			{
				effect.exit();
			}
		}
		npc.setTarget(player);
		npc.doCast(SkillTable.getInstance().getInfo(transId, 1));
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		QuestState second = player.getQuestState("Q00193_SevenSignDyingMessage");
		if (st == null)
		{
			return htmltext;
		}
		if (npc.getNpcId() == ATHEBALDT)
		{
			switch (st.getState())
			{
				case State.CREATED:
					if ((second != null) && (second.getState() == 2) && (player.getLevel() >= 79))
					{
						htmltext = "30760-01.htm";
					}
					else
					{
						htmltext = "30760-00.htm";
						st.exitQuest(true);
					}
					break;
				case State.STARTED:
					if (st.getInt("cond") == 1)
					{
						htmltext = "30760-03.htm";
					}
					else if (st.getInt("cond") == 2)
					{
						htmltext = "30760-05.htm";
					}
					else if (st.getInt("cond") == 3)
					{
						htmltext = "30760-08.htm";
					}
					break;
				case State.COMPLETED:
					htmltext = getAlreadyCompletedMsg(player);
					break;
			}
		}
		else if (npc.getNpcId() == COLIN)
		{
			switch (st.getInt("cond"))
			{
				case 3:
					htmltext = "32571-01.htm";
					break;
				case 4:
					if (checkPlayer(player, 6201))
					{
						htmltext = "32571-05.htm";
					}
					else
					{
						htmltext = "32571-07.htm";
					}
					break;
				case 5:
					htmltext = "32571-09.htm";
					break;
				case 6:
					htmltext = "32571-11.htm";
					break;
				case 7:
					if (checkPlayer(player, 6202))
					{
						htmltext = "32571-13.htm";
					}
					else
					{
						htmltext = "32571-15.htm";
					}
					break;
				case 8:
					htmltext = "32571-17.htm";
					break;
				case 9:
					htmltext = "32571-19.htm";
					break;
				case 10:
					if (checkPlayer(player, 6203))
					{
						htmltext = "32571-21.htm";
					}
					else
					{
						htmltext = "32571-23.htm";
					}
					break;
				case 11:
					htmltext = "32571-25.htm";
					break;
				case 12:
					htmltext = "32571-27.htm";
					break;
			}
		}
		else if (npc.getNpcId() == FROG)
		{
			if (checkPlayer(player, 6201))
			{
				if (st.getInt("cond") == 4)
				{
					htmltext = "32572-01.htm";
				}
				else if (st.getInt("cond") == 5)
				{
					htmltext = "32572-05.htm";
				}
			}
			else
			{
				htmltext = "32572-00.htm";
			}
		}
		else if (npc.getNpcId() == TESS)
		{
			if (checkPlayer(player, 6202))
			{
				if (st.getInt("cond") == 7)
				{
					htmltext = "32573-01.htm";
				}
				else if (st.getInt("cond") == 8)
				{
					htmltext = "32573-04.htm";
				}
			}
			else
			{
				htmltext = "32573-00.htm";
			}
		}
		else if (npc.getNpcId() == KUTA)
		{
			if (checkPlayer(player, 6203))
			{
				if (st.getInt("cond") == 10)
				{
					htmltext = "32574-01.htm";
				}
				else if (st.getInt("cond") == 11)
				{
					htmltext = "32574-05.htm";
				}
			}
			else
			{
				htmltext = "32574-00.htm";
			}
		}
		else if (npc.getNpcId() == CLAUDIA)
		{
			if (st.getInt("cond") == 12)
			{
				htmltext = "31001-01.htm";
			}
		}
		return htmltext;
	}
	
	private boolean checkPlayer(L2PcInstance player, int transId)
	{
		L2Effect effect = player.getFirstEffect(transId);
		return effect != null;
	}
	
	public static void main(String args[])
	{
		new Q00194_SevenSignContractOfMammon(194, Q00194_SevenSignContractOfMammon.class.getSimpleName(), "Seven Signs, Mammon's Contract");
	}
}