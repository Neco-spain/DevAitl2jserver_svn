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
package quests.Q00646_SignsOfRevolt;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * Author: RobikBobik L2PS Team
 */
public final class Q00646_SignsOfRevolt extends Quest
{
	
	public Q00646_SignsOfRevolt(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(32016);
		addTalkId(32016);
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
		
		if (npc.getNpcId() == 32016)
		{
			st.exitQuest(true);
		}
		return "32016-00.htm";
	}
	
	public static void main(String[] args)
	{
		new Q00646_SignsOfRevolt(646, Q00646_SignsOfRevolt.class.getSimpleName(), "Signs Of Revolt");
	}
}