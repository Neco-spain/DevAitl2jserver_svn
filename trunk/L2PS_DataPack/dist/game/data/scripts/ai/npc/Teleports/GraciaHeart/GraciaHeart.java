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
 * this program.
 */
package ai.npc.Teleports.GraciaHeart;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * Author: RobikBobik
 */
public class GraciaHeart extends Quest
{
	private final static int EmergyCompressor = 36570;
	
	public GraciaHeart(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(EmergyCompressor);
		addTalkId(EmergyCompressor);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		
		int npcId = npc.getNpcId();
		
		if (npcId == EmergyCompressor)
		{
			if (player.getLevel() >= 75)
			{
				player.teleToLocation(-204288, 242026, 1744);
			}
			else
			{
				htmltext = "36570-00.htm";
			}
		}
		st.exitQuest(true);
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new GraciaHeart(-1, GraciaHeart.class.getSimpleName(), "teleports");
	}
}