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
package quests.Q00260_HuntTheOrcs;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00260_HuntTheOrcs extends Quest
{
	private static final int RAYEN = 30221;
	private static final int ORC_AMULET = 1114;
	private static final int ORCS_NECKLACE = 1115;
	private static final int KABOO_ORC = 20468;
	private static final int KABOO_ORC_ARCHER = 20469;
	private static final int KABOO_ORC_GRUNT = 20470;
	private static final int KABOO_ORC_FIGHTER = 20471;
	private static final int KABOO_ORC_FIGHTER_LEADER = 20472;
	private static final int KABOO_ORC_FIGHTER_LIEUTENANT = 20473;
	private static final int NEWBIE_REWARD = 4;
	private static final int SPIRITSHOT_FOR_BEGINNERS = 5790;
	private static final int SOULSHOT_FOR_BEGINNERS = 5789;
	
	public Q00260_HuntTheOrcs(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(RAYEN);
		addTalkId(RAYEN);
		
		addKillId(KABOO_ORC, KABOO_ORC_ARCHER, KABOO_ORC_GRUNT, KABOO_ORC_FIGHTER, KABOO_ORC_FIGHTER_LEADER, KABOO_ORC_FIGHTER_LIEUTENANT);
		
		questItemIds = new int[]
		{
			ORC_AMULET,
			ORCS_NECKLACE
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
		
		if (event.equalsIgnoreCase("30221-03.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30221-06.htm"))
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
		
		switch (st.getState())
		{
			case State.CREATED:
				if (player.getRace().ordinal() == 1)
				{
					if ((player.getLevel() >= 6) && (player.getLevel() <= 16))
					{
						htmltext = "30221-02.htm";
					}
					else
					{
						htmltext = "30221-01.htm";
						st.exitQuest(true);
					}
				}
				else
				{
					htmltext = "30221-00.htm";
					st.exitQuest(true);
				}
				break;
			
			case State.STARTED:
				long amulet = st.getQuestItemsCount(ORC_AMULET);
				long necklace = st.getQuestItemsCount(ORCS_NECKLACE);
				
				if ((amulet == 0) && (necklace == 0))
				{
					htmltext = "30221-04.htm";
				}
				else
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
							player.sendMessage("Acquisition of Spiritshot for beginners complete. Go find the Newbie Guide.");
						}
						else
						{
							st.playTutorialVoice("tutorial_voice_026");
							st.giveItems(SOULSHOT_FOR_BEGINNERS, 6000);
							player.sendMessage("Acquisition of Soulshot for beginners complete. Go find the Newbie Guide.");
						}
					}
					htmltext = "30221-05.htm";
					st.takeItems(ORC_AMULET, -1);
					st.takeItems(ORCS_NECKLACE, -1);
					st.giveItems(57, (amulet * 5) + (necklace * 15));
				}
				break;
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
		
		if (st.isStarted())
		{
			switch (npc.getNpcId())
			{
				case KABOO_ORC:
				case KABOO_ORC_GRUNT:
				case KABOO_ORC_ARCHER:
					if (getRandom(10) < 4)
					{
						st.giveItems(ORC_AMULET, 1);
					}
					break;
				
				case KABOO_ORC_FIGHTER:
				case KABOO_ORC_FIGHTER_LEADER:
				case KABOO_ORC_FIGHTER_LIEUTENANT:
					if (getRandom(10) < 4)
					{
						st.giveItems(ORCS_NECKLACE, 1);
					}
					break;
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00260_HuntTheOrcs(260, Q00260_HuntTheOrcs.class.getSimpleName(), "Hunt The Orcs");
	}
}