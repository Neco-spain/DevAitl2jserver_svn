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
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00238_SuccesFailureOfBusiness extends Quest
{
	private static final int DrHelvetica = 32641;
	private static final int BrazierOfPurity = 18806;
	private static final int GuardianSpiritsOfMagicForce = 22659;
	private static final int EvilSpiritsInMagicForce = 22658;
	private static final int BrokenPieveOfMagicForce = 14867;
	private static final int GuardianSpiritFragment = 14868;
	private static final int VicinityOfTheFieldOfSilenceResearchCenter = 14865;
	
	public Q00238_SuccesFailureOfBusiness(int id, String name, String descr)
	{
		super(id, name, descr);
		
		addStartNpc(DrHelvetica);
		addTalkId(DrHelvetica);
		
		addKillId(BrazierOfPurity);
		addKillId(GuardianSpiritsOfMagicForce);
		addKillId(EvilSpiritsInMagicForce);
		
		questItemIds = new int[]
		{
			BrokenPieveOfMagicForce,
			GuardianSpiritFragment
		};
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		if (event.equals("32461-03.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equals("32461-06.htm"))
		{
			st.set("cond", "3");
			st.playSound("ItemSound.quest_middle");
		}
		return event;
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
		
		int cond = st.getInt("cond");
		
		QuestState qs = st.getPlayer().getQuestState("Q00237_WindsOfChange");
		QuestState qs2 = st.getPlayer().getQuestState("Q00239_WontYouJoinUs");
		
		if (npc.getNpcId() == DrHelvetica)
		{
			if (st.getState() == State.COMPLETED)
			{
				htmltext = "32461-09.htm";
			}
			else if (cond == 0)
			{
				if (qs2.getState() == State.COMPLETED)
				{
					htmltext = "32461-10.htm";
				}
				else if (qs.getState() == State.COMPLETED)
				{
					if (player.getLevel() >= 82)
					{
						htmltext = "32461-01.htm";
					}
					else
					{
						htmltext = "32461-00.htm";
						st.exitQuest(true);
					}
				}
			}
			else if (cond == 1)
			{
				htmltext = "32461-04.htm";
			}
			else if (cond == 2)
			{
				st.takeItems(BrokenPieveOfMagicForce, 10);
				htmltext = "32461-05.htm";
			}
			else if (cond == 3)
			{
				htmltext = "32461-07.htm";
			}
			else if ((cond == 4) && (st.getQuestItemsCount(GuardianSpiritFragment) >= 20))
			{
				htmltext = "32461-08.htm";
				st.giveItems(57, 283346);
				st.takeItems(VicinityOfTheFieldOfSilenceResearchCenter, -1);
				st.takeItems(GuardianSpiritFragment, 20);
				st.addExpAndSp(1319736, 103553);
				st.setState(State.COMPLETED);
				st.unset("cond");
				st.exitQuest(false);
				st.playSound("ItemSound.quest_finish");
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		L2PcInstance partyMember = getRandomPartyMemberState(player, State.STARTED);
		if (partyMember == null)
		{
			return null;
		}
		
		QuestState st = partyMember.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		int cond = st.getInt("cond");
		
		if (npc.getNpcId() == BrazierOfPurity)
		{
			if (cond == 1)
			{
				int count = 1;
				int chance = (int) (5 * Config.RATE_QUEST_DROP);
				while (chance > 1000)
				{
					chance -= 1000;
					count++;
				}
				if (getRandom(1000) <= chance)
				{
					st.giveItems(BrokenPieveOfMagicForce, count);
					st.playSound("ItemSound.quest_itemget");
					if (st.getQuestItemsCount(BrokenPieveOfMagicForce) == 10)
					{
						st.set("cond", "2");
						st.playSound("ItemSound.quest_middle");
					}
				}
			}
		}
		else if ((npc.getNpcId() == GuardianSpiritsOfMagicForce) || (npc.getNpcId() == EvilSpiritsInMagicForce))
		{
			if (cond == 3)
			{
				int count = 1;
				int chance = (int) (5 * Config.RATE_QUEST_DROP);
				while (chance > 1000)
				{
					chance -= 1000;
					count++;
				}
				if (getRandom(1000) <= chance)
				{
					st.giveItems(GuardianSpiritFragment, count);
					st.playSound("ItemSound.quest_itemget");
					if (st.getQuestItemsCount(GuardianSpiritFragment) == 20)
					{
						st.set("cond", "4");
						st.playSound("ItemSound.quest_middle");
					}
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00238_SuccesFailureOfBusiness(238, Q00238_SuccesFailureOfBusiness.class.getSimpleName(), "Succes Failure Of Business");
	}
}