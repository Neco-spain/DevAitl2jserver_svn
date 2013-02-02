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
import com.l2jserver.util.Rnd;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00273_InvadersOfHolyland extends Quest
{
	public final int BLACK_SOULSTONE = 1475;
	public final int RED_SOULSTONE = 1476;
	private static final int NEWBIE_REWARD = 4;
	private static final int SPIRITSHOT_FOR_BEGINNERS = 5790;
	private static final int SOULSHOT_FOR_BEGINNERS = 5789;
	
	public Q00273_InvadersOfHolyland(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(30566);
		addTalkId(30566);
		
		addKillId(20311);
		addKillId(20312);
		addKillId(20313);
		
		questItemIds = new int[]
		{
			BLACK_SOULSTONE,
			RED_SOULSTONE
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
		
		if (event.equalsIgnoreCase("30566-03.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30566-07.htm"))
		{
			st.set("cond", "0");
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(true);
		}
		else if (event.equalsIgnoreCase("30566-08.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
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
		
		int cond = st.getInt("cond");
		int id = st.getState();
		
		if (id == State.CREATED)
		{
			st.set("cond", "0");
		}
		
		if (cond == 0)
		{
			if (player.getRace().ordinal() != 3)
			{
				htmltext = "30566-00.htm";
				st.exitQuest(true);
			}
			else if (player.getLevel() < 6)
			{
				htmltext = "30566-01.htm";
				st.exitQuest(true);
			}
			else
			{
				htmltext = "30566-02.htm";
			}
		}
		else if (cond > 0)
		{
			if ((st.getQuestItemsCount(BLACK_SOULSTONE) == 0) && (st.getQuestItemsCount(RED_SOULSTONE) == 0))
			{
				htmltext = "30566-04.htm";
			}
			else
			{
				long red = st.getQuestItemsCount(RED_SOULSTONE);
				long black = st.getQuestItemsCount(BLACK_SOULSTONE);
				
				if ((red + black) == 0)
				{
					htmltext = "30566-04.htm";
				}
				else if (red == 0)
				{
					htmltext = "30566-05.htm";
					
					if (black > 9)
					{
						st.giveItems(57, (black * 3) + 1500);
					}
					else
					{
						st.giveItems(57, black * 3);
					}
					
					st.takeItems(BLACK_SOULSTONE, black);
					st.playSound("ItemSound.quest_finish");
				}
				else
				{
					htmltext = "30566-06.htm";
					
					long amount = 0;
					
					if (black >= 1)
					{
						amount = black * 3;
						st.takeItems(BLACK_SOULSTONE, black);
					}
					
					amount += red * 10;
					
					if ((black + red) > 9)
					{
						amount += 1800;
					}
					
					st.takeItems(RED_SOULSTONE, red);
					st.giveItems(57, amount);
					st.playSound("ItemSound.quest_finish");
				}
				
				if ((red + black) != 0)
				{
					int newbie = player.getNewbie();
					
					if ((newbie | NEWBIE_REWARD) != newbie)
					{
						player.setNewbie(newbie | NEWBIE_REWARD);
						st.showQuestionMark(26);
						
						if (st.getPlayer().getClassId().isMage())
						{
							st.playTutorialVoice("tutorial_voice_027");
							st.giveItems(SPIRITSHOT_FOR_BEGINNERS, 3000);
							player.sendPacket(new ExShowScreenMessage(((new CustomMessage("Newbie.Message2", player.getLang())).toString()), 3000));
						}
						else
						{
							st.playTutorialVoice("tutorial_voice_026");
							st.giveItems(SOULSHOT_FOR_BEGINNERS, 6000);
							player.sendPacket(new ExShowScreenMessage(((new CustomMessage("Newbie.Message2a", player.getLang())).toString()), 3000));
						}
					}
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
			return null;
		}
		
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		
		if (npcId == 20311)
		{
			if (cond == 1)
			{
				if (Rnd.getChance(90))
				{
					st.giveItems(BLACK_SOULSTONE, 1);
				}
				else
				{
					st.giveItems(RED_SOULSTONE, 1);
				}
				st.playSound("ItemSound.quest_itemget");
			}
		}
		else if (npcId == 20312)
		{
			if (cond == 1)
			{
				if (Rnd.getChance(87))
				{
					st.giveItems(BLACK_SOULSTONE, 1);
				}
				else
				{
					st.giveItems(RED_SOULSTONE, 1);
				}
				st.playSound("ItemSound.quest_itemget");
			}
		}
		else if (npcId == 20313)
		{
			if (cond == 1)
			{
				if (Rnd.getChance(77))
				{
					st.giveItems(BLACK_SOULSTONE, 1);
				}
				else
				{
					st.giveItems(RED_SOULSTONE, 1);
				}
				st.playSound("ItemSound.quest_itemget");
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00273_InvadersOfHolyland(273, Q00273_InvadersOfHolyland.class.getSimpleName(), "Invaders Of Holyland");
	}
}