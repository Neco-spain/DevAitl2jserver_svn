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
package events.ChristmasIsHere;

import com.l2jserver.FunEvents;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * @Fixed by L2Ps Team
 * www.l2ps.tode.cz
 */
public class ChristmasIsHere extends Quest
{
	private static final String qn = "ChristmasIsHere";
	private static final int SANTA_CLAUS = 502;
	private static final int[] EventMonsters = 
	{ 
		7000,7001,7002,7003,7004,7005,7006,7007,7008,7009,
		7010,7011,7012,7013,7014,7015,7016,7017,7018,7019,
		7020,7021,7022,7023
	};	
	@Override
	public final String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			st = newQuestState(player);	
		}
		String htmltext = "";
		if (FunEvents.CH_STARTED)
		{
			htmltext = "<html><body>Santa:<br>Ho ho ho !! Merry Christmas! Have you been bad or good this year?<br>You must see what i can give you.<br>";
			htmltext +="<a action=\"bypass -h Quest ChristmasIsHere getprizes\">Search for a Gift To Me</a><br>";
			htmltext +="<a action=\"bypass -h Quest ChristmasIsHere info\">How i can get prizes.</a><br></body></html>";
		}
		else
		{
			htmltext = FunEvents.EVENT_DISABLED;
		}
		return htmltext;
	}
	/**
	 * On Advanced Event Script 
	 */
	@Override
	public final String onAdvEvent (String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			st = newQuestState(player);	
		}
		String htmltext = "";
		if (event.equalsIgnoreCase("getprizes"))
		{
			htmltext = "prizes.htm";
		}
		else if (event.equalsIgnoreCase("info"))
		{
			htmltext = "<html><body>Santa:<br>Oh no i don't have my socks. You must go to dark dungeon (Mission Master) and get my socks, then you can get one of my prizes.<br>"; 
			htmltext += "<br></body></html>";
		}		
       	return htmltext;
	}
	/**
	 * On Kill Monster Script
	 */
	@Override
	public final String onKill(L2Npc npc,L2PcInstance player, boolean isPet)
	{
		QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			st = newQuestState(player);	
		}
		int npcId = npc.getNpcId();
		if (FunEvents.CH_ACTIVE_DROP)
		{
			for(int ID : EventMonsters)
			{ 
				if (npcId == ID)
				{
					st.giveItems(FunEvents.CH_CHRISTMAS_SOCK,1);					
				}
			}			
		}
		return super.onKill(npc, player, isPet);
	}	
	
	public ChristmasIsHere(int questId, String name, String descr)
	{
		super(questId, name, descr);		
		addStartNpc(SANTA_CLAUS);
		addFirstTalkId(SANTA_CLAUS);
		addTalkId(SANTA_CLAUS);
		for (int MONSTER: EventMonsters)
		{
			addKillId(MONSTER);
		}		
	}
	public static void main(String[] args)
	{
		new ChristmasIsHere(-1,qn,"events");
		if (FunEvents.CH_STARTED)
			_log.warning("Event System: Christmas Event loaded ...");
	}
}