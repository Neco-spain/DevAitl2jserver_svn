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
package events.SchoolDays;

import com.l2jserver.FunEvents;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.base.Race;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.util.Rnd;

/**
 * @Fixed by L2Ps Team
 * www.l2ps.tode.cz
 */
public class SchoolDays extends Quest
{
	private static final String qn = "SchoolDays";
	private static final int SCHOOL_DAYS_NPC = 13182;
	private static final int[] EventMonsters = 
	{ 
		7000,7001,7002,7003,7004,7005,7006,7007,7008,7009,
		7010,7011,7012,7013,7014,7015,7016,7017,7018,7019,
		7020,7021,7022,7023
	};
	private static final int[] BOOKS_HUMAN =
	{
		1372,1397,1401
	};
	private static final int[] BOOKS_ELF =
	{
		1370,1380,1402
	};
	private static final int[] BOOKS_DARK_ELF =
	{
		1371,1391,1408
	};
	private static final int[] BOOKS_ORC =
	{
		1518,1519,1520
	};
	private static final int[] BOOKS_DWARF =
	{
		3038,3940,4915
	};
	private static final int[] BOOKS_KAMAEL =
	{
		10025,10026,10027
	};
	
	/**
	 * On First Talk Script
	 * @param npc 
	 * @param player 
	 * @return 
	 */
	@Override
	public final String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			st = newQuestState(player);
			st.setState(State.STARTED);
		}
		String htmltext = "";
		if (FunEvents.SD_STARTED)
		{
			htmltext = "welcome.htm";
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
		
		if (event.equalsIgnoreCase("getreward"))
		{
			htmltext = "reward.htm";
		}
		if (event.equalsIgnoreCase("info"))
		{
			htmltext = "info.htm";
		}
		if (event.equalsIgnoreCase("back"))
		{
			htmltext = "welcome.htm";
		}
       	return htmltext;
	}	

	/**
	 * On Kill Monster Script
	 */
	@Override
	public final String onKill(L2Npc npc,L2PcInstance player, boolean isPet)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			st = newQuestState(player);	
		}
		int npcId = npc.getNpcId();
		if (FunEvents.SD_ACTIVE_DROP)
		{
			for(int ID : EventMonsters)
			{ 
				if (npcId == ID)
				{
					if (player.getRace() == Race.Human)
					{
						int random = Rnd.get(100);
						if (random < 33)
						{
							st.giveItems(BOOKS_HUMAN[Rnd.get(BOOKS_HUMAN.length)],1);
						}
					}
					else if (player.getRace() == Race.Elf)
					{
						int random = Rnd.get(100);
						if (random < 33)
						{
							st.giveItems(BOOKS_ELF[Rnd.get(BOOKS_ELF.length)],1);
						}
					}
					else if (player.getRace() == Race.DarkElf)
					{
						int random = Rnd.get(100);
						if (random < 33)
						{
							st.giveItems(BOOKS_DARK_ELF[Rnd.get(BOOKS_DARK_ELF.length)],1);
						}
					}
					else if (player.getRace() == Race.Orc)
					{
						int random = Rnd.get(100);
						if (random < 33)
						{
							st.giveItems(BOOKS_ORC[Rnd.get(BOOKS_ORC.length)],1);
						}
					}
					else if (player.getRace() == Race.Dwarf)
					{
						int random = Rnd.get(100);
						if (random < 33)
						{
							st.giveItems(BOOKS_DWARF[Rnd.get(BOOKS_DWARF.length)],1);
						}
					}
					else if (player.getRace() == Race.Kamael)
					{
						int random = Rnd.get(100);
						if (random < 33)
						{
							st.giveItems(BOOKS_KAMAEL[Rnd.get(BOOKS_KAMAEL.length)],1);
						}
					}
				}
			}			
		}
		return super.onKill(npc, player, isPet);
	}	
	
	public SchoolDays(int questId, String name, String descr)
	{
		super(questId, name, descr);		
		addStartNpc(SCHOOL_DAYS_NPC);
		addFirstTalkId(SCHOOL_DAYS_NPC);
		addTalkId(SCHOOL_DAYS_NPC);
		for (int MONSTER: EventMonsters)
		{
			addKillId(MONSTER);
		}		
	}
	
	public static void main(String[] args)
	{
		new SchoolDays(-1,qn,"events");
		if (FunEvents.SD_STARTED)
			_log.info("Event System: School Day Event loaded ...");
	}
}