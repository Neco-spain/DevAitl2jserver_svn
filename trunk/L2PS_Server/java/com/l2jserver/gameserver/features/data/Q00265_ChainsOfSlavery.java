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
public class Q00265_ChainsOfSlavery extends Quest
{
	private static final int KRISTIN = 30357;
	private static final int IMP = 20004;
	private static final int IMP_ELDER = 20005;
	private static final int IMP_SHACKLES = 1368;
	private static final int NEWBIE_REWARD = 4;
	private static final int SPIRITSHOT_FOR_BEGINNERS = 5790;
	private static final int SOULSHOT_FOR_BEGINNERS = 5789;
	
	public Q00265_ChainsOfSlavery(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(KRISTIN);
		addTalkId(KRISTIN);
		
		addKillId(IMP);
		addKillId(IMP_ELDER);
		
		questItemIds = new int[]
		{
			IMP_SHACKLES
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
		
		if (event.equalsIgnoreCase("30357-03.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30357-06.htm"))
		{
			st.exitQuest(true);
			st.playSound("ItemSound.quest_finish");
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
			if (player.getRace().ordinal() != 2)
			{
				htmltext = "30357-00.htm";
				st.exitQuest(true);
			}
			else
			{
				if (player.getLevel() < 6)
				{
					htmltext = "30357-01.htm";
					st.exitQuest(true);
				}
				else
				{
					htmltext = "30357-02.htm";
				}
			}
		}
		else
		{
			long count = st.getQuestItemsCount(IMP_SHACKLES);
			
			if (count > 0)
			{
				if (count >= 10)
				{
					st.giveItems(57, (12 * count) + 500);
				}
				else
				{
					st.giveItems(57, 12 * count);
				}
				
				st.takeItems(IMP_SHACKLES, -1);
				
				int newbie = player.getNewbie();
				
				if ((newbie | NEWBIE_REWARD) != newbie)
				{
					player.setNewbie(newbie | NEWBIE_REWARD);
					st.showQuestionMark(26);
					
					if (st.getPlayer().getClassId().isMage())
					{
						st.playTutorialVoice("tutorial_voice_027");
						st.giveItems(SPIRITSHOT_FOR_BEGINNERS, 3000);
						player.sendMessage("Acquisition of Spiritshot for beginners complete. Go find the Newbie Guide.");
					}
					else
					{
						st.playTutorialVoice("tutorial_voice_026");
						st.giveItems(SOULSHOT_FOR_BEGINNERS, 6000);
						player.sendMessage("Acquisition of Soulshot for beginners complete. Go find the Newbie Guide.");
					}
				}
				htmltext = "30357-05.htm";
			}
			else
			{
				htmltext = "30357-04.htm";
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
		
		if ((cond == 1) && Rnd.getChance((5 + npcId) - 20004))
		{
			st.giveItems(IMP_SHACKLES, 1);
			st.playSound("ItemSound.quest_itemget");
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00265_ChainsOfSlavery(265, Q00265_ChainsOfSlavery.class.getSimpleName(), "Chains Of Slavery");
	}
}