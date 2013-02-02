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
import com.l2jserver.gameserver.model.L2CharPosition;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.util.Rnd;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00021_HiddenTruth extends Quest
{
	public static final int MysteriousWizard = 31522;
	public static final int Tombstone = 31523;
	public static final int GhostofvonHellmannId = 31524;
	public static final int GhostofvonHellmannsPageId = 31525;
	public static final int BrokenBookshelf = 31526;
	public static final int Agripel = 31348;
	public static final int Dominic = 31350;
	public static final int Benedict = 31349;
	public static final int Innocentin = 31328;
	public static final int[] talkNpc =
	{
		MysteriousWizard,
		Tombstone,
		GhostofvonHellmannId,
		GhostofvonHellmannsPageId,
		BrokenBookshelf,
		Agripel,
		Dominic,
		Benedict,
		Innocentin
	};
	// itemId list:
	public static final int CrossofEinhasad = 7140;
	public static final int CrossofEinhasadNextQuest = 7141;
	
	public L2Npc GhostofvonHellmannsPage;
	public L2Npc GhostofvonHellmann;
	
	private void spawnGhostofvonHellmannsPage(QuestState st)
	{
		GhostofvonHellmannsPage = st.addSpawn(GhostofvonHellmannsPageId, 51462, -54539, -3176, Rnd.get(0, 20), true, 0);
		GhostofvonHellmannsPage.broadcastNpcSay("My master has instructed me to be your guide, " + st.getPlayer().getName());
	}
	
	private void despawnGhostofvonHellmannsPage(QuestState st)
	{
		if (GhostofvonHellmannsPage != null)
		{
			GhostofvonHellmannsPage.deleteMe();
		}
		GhostofvonHellmannsPage = null;
	}
	
	private void spawnGhostofvonHellmann(QuestState st)
	{
		GhostofvonHellmann = st.addSpawn(GhostofvonHellmannId, 51432, -54570, -3136, Rnd.get(0, 20), false, 0);
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
	
	public Q00021_HiddenTruth(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(MysteriousWizard);
		for (int npcId : talkNpc)
		{
			addTalkId(npcId);
		}
		questItemIds = new int[]
		{
			CrossofEinhasad
		};
	}
	
	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if (event.equalsIgnoreCase("31522-02.htm"))
		{
			st.setState(State.STARTED);
			st.set("cond", "1");
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("html"))
		{
			htmltext = "31328-05.htm";
		}
		else if (event.equalsIgnoreCase("31328-05.htm"))
		{
			st.unset("cond");
			st.takeItems(CrossofEinhasad, 1);
			if (st.getQuestItemsCount(CrossofEinhasadNextQuest) == 0)
			{
				st.giveItems(CrossofEinhasadNextQuest, 1);
			}
			st.playSound("ItemSound.quest_finish");
			st.addExpAndSp(131228, 11978);
			st.startQuestTimer("html", 1);
			htmltext = "<html><body>Congratulations! You have completed this quest!<br>The Quest \"Tragedy In Von Hellmann Forest\" become available.<br>Show Cross of Einhasad to High Priest Tifaren.</body></html>";
			st.setState(State.COMPLETED);
			st.exitQuest(false);
		}
		else if (event.equalsIgnoreCase("31523-03.htm"))
		{
			st.playSound("ItemSound.quest_middle");
			st.set("cond", "2");
			despawnGhostofvonHellmann(st);
			spawnGhostofvonHellmann(st);
		}
		else if (event.equalsIgnoreCase("31524-06.htm"))
		{
			st.set("cond", "3");
			despawnGhostofvonHellmannsPage(st);
			spawnGhostofvonHellmannsPage(st);
			startQuestTimer("1", 4000, GhostofvonHellmannsPage, st.getPlayer());
		}
		else if (event.equalsIgnoreCase("31526-03.htm"))
		{
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31526-08.htm"))
		{
			st.playSound("ItemSound.quest_middle");
			st.set("cond", "5");
		}
		else if (event.equalsIgnoreCase("31526-14.htm"))
		{
			st.giveItems(CrossofEinhasad, 1);
			st.set("cond", "6");
		}
		else if (event.equalsIgnoreCase("1"))
		{
			GhostofvonHellmannsPage.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new L2CharPosition(52373, -54296, -3136, 0));
			GhostofvonHellmannsPage.broadcastNpcSay("Follow me...");
			st.startQuestTimer("2", 5000, GhostofvonHellmannsPage);
		}
		else if (event.equalsIgnoreCase("2"))
		{
			GhostofvonHellmannsPage.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new L2CharPosition(52451, -52921, -3152, 0));
			st.startQuestTimer("3", 12000, GhostofvonHellmannsPage);
		}
		else if (event.equalsIgnoreCase("3"))
		{
			GhostofvonHellmannsPage.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new L2CharPosition(51909, -51725, -3125, 0));
			st.startQuestTimer("4", 15000, GhostofvonHellmannsPage);
		}
		else if (event.equalsIgnoreCase("4"))
		{
			GhostofvonHellmannsPage.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new L2CharPosition(52438, -51240, -3097, 0));
			GhostofvonHellmannsPage.broadcastNpcSay("This where that here...");
			st.startQuestTimer("5", 5000, GhostofvonHellmannsPage);
		}
		else if (event.equalsIgnoreCase("5"))
		{
			GhostofvonHellmannsPage.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new L2CharPosition(52143, -51418, -3085, 0));
			GhostofvonHellmannsPage.broadcastNpcSay("I want to speak to you...");
			return null;
		}
		
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		QuestState st = talker.getQuestState(getName());
		String htmltext = "<html><body>This person inaccessible and does not want with you to talk!<br>Are they please returned later...</body></html>";
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
		if (npcId == MysteriousWizard)
		{
			if (cond == 0)
			{
				if (st.getPlayer().getLevel() > 54)
				{
					htmltext = "31522-01.htm";
				}
				else
				{
					htmltext = "31522-03.htm";
					st.exitQuest(true);
				}
			}
			else if (cond == 1)
			{
				htmltext = "31522-05.htm";
			}
		}
		else if (npcId == Tombstone)
		{
			if (cond == 1)
			{
				htmltext = "31523-01.htm";
			}
			else if ((cond == 2) || (cond == 3))
			{
				htmltext = "31523-04.htm";
				st.playSound("ItemSound.quest_middle");
				despawnGhostofvonHellmann(st);
				spawnGhostofvonHellmann(st);
			}
		}
		else if (npcId == GhostofvonHellmannId)
		{
			if (cond == 2)
			{
				htmltext = "31524-01.htm";
			}
			else if (cond == 3)
			{
				htmltext = "31524-07b.htm";
			}
			else if (cond == 4)
			{
				htmltext = "31524-07c.htm";
			}
		}
		else if (npcId == GhostofvonHellmannsPageId)
		{
			if ((cond == 3) || (cond == 4))
			{
				htmltext = "31525-01.htm";
				
				if (!GhostofvonHellmannsPage.isMoving())
				{
					htmltext = "31525-02.htm";
					if (cond == 3)
					{
						st.set("cond", "4");
					}
				}
				else
				{
					return "31525-01.htm";
				}
			}
		}
		else if (npcId == BrokenBookshelf)
		{
			if ((cond == 3) || (cond == 4))
			{
				if ((GhostofvonHellmannsPage != null) && !GhostofvonHellmannsPage.isMoving())
				{
					despawnGhostofvonHellmannsPage(st);
					despawnGhostofvonHellmann(st);
					st.set("cond", "5");
					htmltext = "31526-01.htm";
				}
				else
				{
					return htmltext;
				}
			}
			else if (cond == 5)
			{
				htmltext = "31526-10.htm";
				st.playSound("ItemSound.quest_middle");
			}
			else if (cond == 6)
			{
				htmltext = "31526-15.htm";
			}
		}
		else if ((npcId == Agripel) && (st.getQuestItemsCount(CrossofEinhasad) >= 1))
		{
			if (cond == 6)
			{
				if ((st.getInt("DOMINIC") == 1) && (st.getInt("BENEDICT") == 1))
				{
					htmltext = "31348-02.htm";
					st.set("cond", "7");
				}
				else
				{
					st.set("AGRIPEL", "1");
					htmltext = "31348-0" + Rnd.get(3) + ".htm";
				}
			}
			else if (cond == 7)
			{
				htmltext = "31348-03.htm";
			}
		}
		else if ((npcId == Dominic) && (st.getQuestItemsCount(CrossofEinhasad) >= 1))
		{
			if (cond == 6)
			{
				if ((st.getInt("AGRIPEL") == 1) && (st.getInt("BENEDICT") == 1))
				{
					htmltext = "31350-02.htm";
					st.set("cond", "7");
				}
				else
				{
					st.set("DOMINIC", "1");
					htmltext = "31350-0" + Rnd.get(3) + ".htm";
				}
			}
			else if (cond == 7)
			{
				htmltext = "31350-03.htm";
			}
		}
		else if ((npcId == Benedict) && (st.getQuestItemsCount(CrossofEinhasad) >= 1))
		{
			if (cond == 6)
			{
				if ((st.getInt("AGRIPEL") == 1) && (st.getInt("DOMINIC") == 1))
				{
					htmltext = "31349-02.htm";
					st.set("cond", "7");
				}
				else
				{
					st.set("BENEDICT", "1");
					htmltext = "31349-0" + Rnd.get(3) + ".htm";
				}
			}
			else if (cond == 7)
			{
				htmltext = "31349-03.htm";
			}
		}
		else if (npcId == Innocentin)
		{
			if (cond == 7)
			{
				if (st.getQuestItemsCount(CrossofEinhasad) != 0)
				{
					htmltext = "31328-01.htm";
				}
			}
			else if (cond == 0)
			{
				htmltext = "31328-06.htm";
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00021_HiddenTruth(21, Q00021_HiddenTruth.class.getSimpleName(), "Hidden Truth");
	}
}