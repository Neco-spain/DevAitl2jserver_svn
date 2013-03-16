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
package quests.Q00237_WindsOfChange;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00237_WindsOfChange extends Quest
{
	private static final int Flauen = 30899;
	private static final int Iason = 30969;
	private static final int Roman = 30897;
	private static final int Morelyn = 30925;
	private static final int Helvetica = 32641;
	private static final int Athenia = 32643;
	private static final int[] _npcs =
	{
		Flauen,
		Iason,
		Roman,
		Morelyn,
		Helvetica,
		Athenia
	};
	private static final int FlauensLetter = 14862;
	private static final int LetterToHelvetica = 14863;
	private static final int LetterToAthenia = 14864;
	private static final int VicinityOfTheFieldOfSilenceResearchCenter = 14865;
	private static final int CertificateOfSupport = 14866;
	
	public Q00237_WindsOfChange(int id, String name, String descr)
	{
		super(id, name, descr);
		
		addStartNpc(Flauen);
		
		for (int i : _npcs)
		{
			addTalkId(i);
		}
		
		questItemIds = new int[]
		{
			FlauensLetter,
			LetterToHelvetica,
			LetterToAthenia,
			VicinityOfTheFieldOfSilenceResearchCenter,
			CertificateOfSupport
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
		
		if (event.equalsIgnoreCase("30899-06.htm"))
		{
			st.giveItems(FlauensLetter, 1);
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30969-05.htm"))
		{
			st.set("cond", "2");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("30897-03.htm"))
		{
			st.set("cond", "3");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("30925-03.htm"))
		{
			st.set("cond", "4");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("30969-09.htm"))
		{
			st.giveItems(LetterToHelvetica, 1);
			st.set("cond", "5");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("30969-10.htm"))
		{
			st.giveItems(LetterToAthenia, 1);
			st.set("cond", "6");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("32641-02.htm"))
		{
			st.takeItems(LetterToHelvetica, -1);
			st.giveItems(57, 213876);
			st.addExpAndSp(892773, 60012);
			st.giveItems(VicinityOfTheFieldOfSilenceResearchCenter, 1);
			st.playSound("ItemSound.quest_finish");
			st.setState(State.COMPLETED);
			st.exitQuest(false);
		}
		else if (event.equalsIgnoreCase("32643-02.htm"))
		{
			st.takeItems(LetterToAthenia, -1);
			st.giveItems(57, 213876);
			st.addExpAndSp(892773, 60012);
			st.giveItems(CertificateOfSupport, 1);
			st.setState(State.COMPLETED);
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(false);
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
		
		int npcId = npc.getNpcId();
		int id = st.getState();
		int cond = st.getInt("cond");
		
		if (npcId == Flauen)
		{
			if (id == State.CREATED)
			{
				if (st.getPlayer().getLevel() < 82)
				{
					return "30899-00.htm";
				}
				return "30899-01.htm";
			}
			else if (id == State.COMPLETED)
			{
				return "30899-09.htm";
			}
			else if (cond < 5)
			{
				return "30899-07.htm";
			}
			else
			{
				return "30899-08.htm";
			}
		}
		else if (npcId == Iason)
		{
			if (cond == 1)
			{
				st.takeItems(FlauensLetter, -1);
				return "30969-01.htm";
			}
			else if ((cond > 1) && (cond < 4))
			{
				return "30969-06.htm";
			}
			else if (cond == 4)
			{
				return "30969-07.htm";
			}
			else if (cond > 4)
			{
				return "30969-11.htm";
			}
		}
		else if (npcId == Roman)
		{
			if (cond == 2)
			{
				return "30897-01.htm";
			}
			else if (cond > 2)
			{
				return "30897-04.htm";
			}
		}
		else if (npcId == Morelyn)
		{
			if (cond == 3)
			{
				return "30925-01.htm";
			}
			else if (cond > 3)
			{
				return "30925-04.htm";
			}
		}
		else if (npcId == Helvetica)
		{
			if (cond == 5)
			{
				return "32641-01.htm";
			}
			else if (id == State.COMPLETED)
			{
				return "32641-03.htm";
			}
		}
		else if (npcId == Athenia)
		{
			if (cond == 6)
			{
				return "32643-01.htm";
			}
			else if (id == State.COMPLETED)
			{
				return "32643-03.htm";
			}
		}
		
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00237_WindsOfChange(237, Q00237_WindsOfChange.class.getSimpleName(), "Winds Of Change");
	}
}