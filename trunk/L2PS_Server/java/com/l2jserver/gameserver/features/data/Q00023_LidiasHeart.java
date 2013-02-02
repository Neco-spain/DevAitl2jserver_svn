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
import com.l2jserver.util.Rnd;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00023_LidiasHeart extends Quest
{
	private final static int Innocentin = 31328;
	private final static int BrokenBookshelf = 31526;
	private final static int GhostofvonHellmannId = 31524;
	private final static int Tombstone = 31523;
	private final static int Violet = 31386;
	private final static int Box = 31530;
	private final static int MapForestofDeadman = 7063;
	private final static int SilverKey = 7149;
	private final static int LidiaHairPin = 7148;
	private final static int LidiaDiary = 7064;
	private final static int SilverSpear = 7150;
	private final static int Adena = 57;
	public L2Npc GhostofvonHellmann;
	
	public Q00023_LidiasHeart(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(Innocentin);
		addTalkId(Innocentin);
		addTalkId(BrokenBookshelf);
		addTalkId(GhostofvonHellmannId);
		addTalkId(Tombstone);
		addTalkId(Violet);
		addTalkId(Box);
		questItemIds = new int[]
		{
			MapForestofDeadman,
			SilverKey,
			LidiaDiary,
			LidiaHairPin,
			SilverSpear
		};
	}
	
	private void spawnGhostofvonHellmann(QuestState st)
	{
		GhostofvonHellmann = st.addSpawn(GhostofvonHellmannId, 51432, -54570, -3136, Rnd.get(0, 20), false, 180000);
		GhostofvonHellmann.broadcastNpcSay("Who awoke me?");
	}
	
	private void despawnGhostofvonHellmann(QuestState st)
	{
		if (GhostofvonHellmann != null)
		{
			GhostofvonHellmann.deleteMe();
		}
		GhostofvonHellmann = null;
	}
	
	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if (event.equals("31328-02.htm"))
		{
			st.giveItems(MapForestofDeadman, 1);
			st.giveItems(SilverKey, 1);
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equals("31328-03.htm"))
		{
			st.set("cond", "2");
		}
		else if (event.equals("31526-01.htm"))
		{
			st.set("cond", "3");
		}
		else if (event.equals("31526-05.htm"))
		{
			st.giveItems(LidiaHairPin, 1);
			if (st.getQuestItemsCount(LidiaDiary) != 0)
			{
				st.set("cond", "4");
			}
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equals("31526-11.htm"))
		{
			st.giveItems(LidiaDiary, 1);
			if (st.getQuestItemsCount(LidiaHairPin) != 0)
			{
				st.set("cond", "4");
			}
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equals("31328-19.htm"))
		{
			st.set("cond", "6");
		}
		else if (event.equals("31524-04.htm"))
		{
			st.set("cond", "7");
			st.takeItems(LidiaDiary, -1);
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equals("31523-02.htm"))
		{
			despawnGhostofvonHellmann(st);
			spawnGhostofvonHellmann(st);
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equals("31523-05.htm"))
		{
			st.startQuestTimer("viwer_timer", 10000);
		}
		else if (event.equals("viwer_timer"))
		{
			st.set("cond", "8");
			htmltext = "31523-06.htm";
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equals("31530-02.htm"))
		{
			st.set("cond", "10");
			st.takeItems(SilverKey, -1);
			st.giveItems(SilverSpear, 1);
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equals("i7064-02.htm"))
		{
			htmltext = "i7064-02.htm";
		}
		else if (event.equals("31526-13.htm"))
		{
			st.startQuestTimer("read_book", 120000);
		}
		else if (event.equals("read_book"))
		{
			htmltext = "i7064.htm";
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		QuestState st = talker.getQuestState(getName());
		String htmltext = getNoQuestMsg(talker);
		if (st == null)
		{
			return htmltext;
		}
		
		if (st.getState() == State.COMPLETED)
		{
			htmltext = getAlreadyCompletedMsg(talker);
			return htmltext;
		}
		
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if (npcId == Innocentin)
		{
			if (cond == 0)
			{
				QuestState qs = st.getPlayer().getQuestState("Q00022_TragedyInVonHellmannForest");
				if (qs != null)
				{
					if (qs.isCompleted())
					{
						htmltext = "31328-01.htm";
					}
					else
					{
						htmltext = "31328-00.htm";
					}
				}
			}
			else if (cond == 1)
			{
				htmltext = "31328-03.htm";
			}
			else if (cond == 2)
			{
				htmltext = "31328-07.htm";
			}
			else if (cond == 4)
			{
				htmltext = "31328-08.htm";
			}
			else if (cond == 6)
			{
				htmltext = "31328-19.htm";
			}
		}
		else if (npcId == BrokenBookshelf)
		{
			if (cond == 2)
			{
				if (st.getQuestItemsCount(SilverKey) != 0)
				{
					htmltext = "31526-00.htm";
				}
			}
			else if (cond == 3)
			{
				if ((st.getQuestItemsCount(LidiaHairPin) == 0) && (st.getQuestItemsCount(LidiaDiary) != 0))
				{
					htmltext = "31526-12.htm";
				}
				else if ((st.getQuestItemsCount(LidiaHairPin) != 0) && (st.getQuestItemsCount(LidiaDiary) == 0))
				{
					htmltext = "31526-06.htm";
				}
				else if ((st.getQuestItemsCount(LidiaHairPin) == 0) && (st.getQuestItemsCount(LidiaDiary) == 0))
				{
					htmltext = "31526-02.htm";
				}
			}
			else if (cond == 4)
			{
				htmltext = "31526-13.htm";
			}
		}
		else if (npcId == GhostofvonHellmannId)
		{
			if (cond == 6)
			{
				htmltext = "31524-01.htm";
			}
			else if (cond == 7)
			{
				htmltext = "31524-05.htm";
			}
		}
		else if (npcId == Tombstone)
		{
			if (cond == 6)
			{
				if (st.getQuestTimer("spawn_timer") != null)
				{
					htmltext = "31523-03.htm";
				}
				else
				{
					htmltext = "31523-01.htm";
				}
			}
			if (cond == 7)
			{
				htmltext = "31523-04.htm";
			}
			else if (cond == 8)
			{
				htmltext = "31523-06.htm";
			}
		}
		else if (npcId == Violet)
		{
			if (cond == 8)
			{
				htmltext = "31386-01.htm";
				st.set("cond", "9");
			}
			else if (cond == 9)
			{
				htmltext = "31386-02.htm";
			}
			else if (cond == 10)
			{
				if (st.getQuestItemsCount(SilverSpear) != 0)
				{
					htmltext = "31386-03.htm";
					st.takeItems(SilverSpear, -1);
					st.addExpAndSp(456893, 42112);
					st.rewardItems(Adena, 350000);
					st.playSound("ItemSound.quest_finish");
					st.unset("cond");
					st.setState(State.COMPLETED);
					st.exitQuest(false);
				}
				else
				{
					htmltext = "<html><body>You have no Silver Spear...</body></html>";
				}
			}
		}
		else if (npcId == Box)
		{
			if (cond == 9)
			{
				if (st.getQuestItemsCount(SilverKey) != 0)
				{
					htmltext = "31530-01.htm";
				}
				else
				{
					htmltext = "You have no key...";
				}
			}
			else if (cond == 10)
			{
				htmltext = "31386-03.htm";
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00023_LidiasHeart(23, Q00023_LidiasHeart.class.getSimpleName(), "Lidia's Heart");
	}
}