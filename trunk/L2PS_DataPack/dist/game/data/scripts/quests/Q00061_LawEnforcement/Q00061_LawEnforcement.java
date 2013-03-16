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
package quests.Q00061_LawEnforcement;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.base.ClassId;
import com.l2jserver.gameserver.model.base.Race;
import com.l2jserver.gameserver.model.itemcontainer.PcInventory;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public final class Q00061_LawEnforcement extends Quest
{
	private static final int LIANE = 32222;
	private static final int KEKROPUS = 32138;
	private static final int EINDBURGH = 32469;
	
	private Q00061_LawEnforcement(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(LIANE);
		addTalkId(LIANE);
		addTalkId(KEKROPUS);
		addTalkId(EINDBURGH);
	}
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		if (event.equalsIgnoreCase("32222-05.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("32138-09.htm"))
		{
			st.set("cond", "2");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("32469-08.htm") || event.equals("32469-09.htm"))
		{
			player.setClassId(ClassId.judicator.getId());
			player.broadcastUserInfo();
			st.rewardItems(PcInventory.ADENA_ID, 26000);
			st.exitQuest(false);
			st.playSound("ItemSound.quest_finish");
		}
		return htmltext;
	}
	
	@Override
	public final String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		
		if (st.isCompleted())
		{
			return getAlreadyCompletedMsg(player);
		}
		
		if (npcId == LIANE)
		{
			if (cond == 0)
			{
				if (player.getRace() == Race.Kamael)
				{
					if ((player.getClassId() == ClassId.inspector) && (player.getLevel() >= 76))
					{
						return "32222-01.htm";
					}
					return "32222-02.htm";
				}
				return "32222-03.htm";
			}
			else if (cond == 1)
			{
				return "32222-06.htm";
			}
		}
		else if (npcId == KEKROPUS)
		{
			if (cond == 1)
			{
				return "32138-01.htm";
			}
			else if (cond == 2)
			{
				return "32138-10.htm";
			}
		}
		else if ((npcId == EINDBURGH) && (cond == 2))
		{
			return "32469-01.htm";
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00061_LawEnforcement(61, Q00061_LawEnforcement.class.getSimpleName(), "");
	}
}