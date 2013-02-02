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
import com.l2jserver.gameserver.network.serverpackets.SocialAction;

public class Q10293_SevenSignForbiddenBookOfTheElmoreAdenKingdom extends Quest
{
	// NPC
	private static final int Sophia1 = 32596;
	private static final int Elcadia = 32784;
	private static final int Elcadia_Support = 32785;
	private static final int Books = 32809;
	private static final int Books1 = 32810;
	private static final int Books2 = 32811;
	private static final int Books3 = 32812;
	private static final int Books4 = 32813;
	private static final int Sophia2 = 32861;
	private static final int Sophia3 = 32863;
	// Item
	private static final int SolinasBiography = 17213;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(getName());
		int npcId = npc.getNpcId();
		if (st == null)
		{
			return htmltext;
		}
		
		if (npcId == Elcadia)
		{
			if (event.equalsIgnoreCase("32784-04.html"))
			{
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
			}
			else if (event.equalsIgnoreCase("32784-09.html"))
			{
				if (player.isSubClassActive())
				{
					htmltext = "32784-10.html";
				}
				else
				{
					htmltext = "32784-09.htm";
					st.addExpAndSp(15000000, 1500000);
					st.exitQuest(false);
					st.playSound("ItemSound.quest_finish");
					player.broadcastPacket(new SocialAction(player.getObjectId(), 3));
				}
			}
		}
		else if (npcId == Sophia2)
		{
			if (event.equalsIgnoreCase("32861-04.html"))
			{
				st.set("cond", "2");
				st.playSound("ItemSound.quest_middle");
			}
			if (event.equalsIgnoreCase("32861-08.html"))
			{
				st.set("cond", "4");
				st.playSound("ItemSound.quest_middle");
			}
			if (event.equalsIgnoreCase("32861-11.html"))
			{
				st.set("cond", "6");
				st.playSound("ItemSound.quest_middle");
			}
		}
		else if (npcId == Elcadia_Support)
		{
			if (event.equalsIgnoreCase("32785-07.html"))
			{
				st.set("cond", "5");
				st.playSound("ItemSound.quest_middle");
			}
		}
		else if (npcId == Books)
		{
			if (event.equalsIgnoreCase("32809-02.html"))
			{
				st.set("cond", "7");
				st.giveItems(SolinasBiography, 1);
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
		int cond = st.getInt("cond");
		int npcId = npc.getNpcId();
		switch (st.getState())
		{
			case State.CREATED:
				if (npc.getNpcId() == Elcadia)
				{
					QuestState GirlOfDoubt = player.getQuestState("Q10292_SevenSignGirlofDoubt");
					if ((player.getLevel() >= 81) && GirlOfDoubt.isCompleted())
					{
						htmltext = "32784-01.htm";
					}
					else
					{
						htmltext = "32784-11.htm";
						st.exitQuest(true);
					}
				}
				break;
			case State.STARTED:
				if (npcId == Elcadia)
				{
					if (cond == 1)
					{
						htmltext = "32784-06.html";
					}
					else if (cond >= 8)
					{
						htmltext = "32784-07.html";
					}
				}
				else if (npcId == Elcadia_Support)
				{
					switch (cond)
					{
						case 1:
							htmltext = "32785-01.html";
							break;
						case 2:
							htmltext = "32785-04.html";
							st.set("cond", "3");
							st.playSound("ItemSound.quest_middle");
							break;
						case 3:
							htmltext = "32785-05.html";
							break;
						case 4:
							htmltext = "32785-06.html";
							break;
						case 5:
							htmltext = "32785-08.html";
							break;
						case 6:
							htmltext = "32785-09.html";
							break;
						case 7:
							htmltext = "32785-11.html";
							st.set("cond", "8");
							st.playSound("ItemSound.quest_middle");
							break;
						case 8:
							htmltext = "32785-12.html";
							break;
					}
				}
				else if (npcId == Sophia1)
				{
					switch (cond)
					{
						case 1:
						case 2:
						case 3:
						case 4:
						case 5:
						case 6:
						case 7:
							htmltext = "32596-01.html";
							break;
						case 8:
							htmltext = "32596-05.html";
							break;
					}
				}
				else if (npcId == Sophia2)
				{
					switch (cond)
					{
						case 1:
							htmltext = "32861-01.html";
							break;
						case 2:
							htmltext = "32861-05.html";
							break;
						case 3:
							htmltext = "32861-06.html";
							break;
						case 4:
							htmltext = "32861-09.html";
							break;
						case 5:
							htmltext = "32861-10.html";
							break;
						case 6:
						case 7:
							htmltext = "32861-12.html";
							break;
						case 8:
							htmltext = "32861-14.html";
							break;
					}
				}
				else if (npcId == Books)
				{
					if (cond == 6)
					{
						htmltext = "32809-01.html";
					}
				}
				else if (npcId == Books1)
				{
					if (cond == 6)
					{
						htmltext = "32810-01.html";
					}
				}
				else if (npcId == Books2)
				{
					if (cond == 6)
					{
						htmltext = "32811-01.html";
					}
				}
				else if (npcId == Books3)
				{
					if (cond == 6)
					{
						htmltext = "32812-01.html";
					}
				}
				else if (npcId == Books4)
				{
					if (cond == 6)
					{
						htmltext = "32813-01.html";
					}
				}
				break;
			case State.COMPLETED:
				if (npcId == Elcadia)
				{
					htmltext = "32784-02.html";
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public final String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		if (npc.getNpcId() == Sophia3)
		{
			switch (st.getInt("cond"))
			{
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
				case 7:
					htmltext = "32863-01.html";
					break;
				case 8:
					htmltext = "32863-04.html";
					break;
			}
		}
		return htmltext;
	}
	
	public Q10293_SevenSignForbiddenBookOfTheElmoreAdenKingdom(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(Elcadia);
		addTalkId(Elcadia);
		addTalkId(Sophia1);
		addTalkId(Elcadia_Support);
		addTalkId(Books);
		addTalkId(Books1);
		addTalkId(Books2);
		addTalkId(Books3);
		addTalkId(Books4);
		addTalkId(Sophia2);
		addTalkId(Sophia3);
		addStartNpc(Sophia3);
		addFirstTalkId(Sophia3);
		
		questItemIds = new int[]
		{
			SolinasBiography
		};
	}
	
	public static void main(String[] args)
	{
		new Q10293_SevenSignForbiddenBookOfTheElmoreAdenKingdom(10293, Q10293_SevenSignForbiddenBookOfTheElmoreAdenKingdom.class.getSimpleName(), "Seven Signs, Forbidden Book of the Elmore Aden Kingdom");
	}
}
