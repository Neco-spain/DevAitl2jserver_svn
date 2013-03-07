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
package events.HallowedYou;

import com.l2jserver.FunEvents;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * @Fixed by L2Ps Team
 * www.l2ps.tode.cz
 */
public class HallowedYou extends Quest
{
	private static final String qn = "HallowedYou";

	private static final int HALLOWEN_NPC = 504;
	private static final int GHOST = 505;
	
	private static final int[] ForestOfDeadNight = 
	{
		18119,21547,21553,21557,21559,21561,21563,21565,21567,21570,
		21572,21574,21578,21580,21581,21583,21587,21590,21593,21596,
		21599
	};
    private static final int[] TheCementary = 
    {
    	20666,20668,20669,20678,20997,20998,20999,21000
	};
    private static final int[] ImperialTomb = 
    {
    	21396,21397,21398,21399,21400,21401,21402,21403,21404,21405,
		21406,21407,21408,21409,21410,21411,21412,21413,21414,21415,
		21416,21417,21418,21419,21420,21421,21422,21423,21424,21425,
		21426,21427,21428,21429,21430,21431
	};    	
	
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
		if (FunEvents.HY_STARTED)
		{
			htmltext = "welcome.htm";
		}
		else
		{
			htmltext = FunEvents.EVENT_DISABLED;
		}
		return htmltext;
	}
	@Override
	public final String onAdvEvent (String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			st = newQuestState(player);	
		}
		String htmltext = "";
		if (event.equalsIgnoreCase("getprizes"))
		{
			htmltext = "prize.htm";			
		}		
		if (event.equalsIgnoreCase("info"))
		{
			htmltext = "info.htm";
		}
       	return htmltext;
	}
	
	@Override
	public final String onKill(L2Npc npc,L2PcInstance player, boolean isPet)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			st = newQuestState(player);	
		}
		int npcId = npc.getNpcId();
		for (int Id: ForestOfDeadNight)
		{ 
			if (npcId == Id)
			{
				spawnGhost(npc,player);
			}
		}
		for (int Id: TheCementary)
		{ 
			if (npcId == Id)
			{
				spawnGhost(npc,player);
			}
		}
		for (int Id: ImperialTomb)
		{ 
			if (npcId == Id)
			{
				spawnGhost(npc,player);
			}
		}
		if (npcId == GHOST)
		{
			st.giveItems(FunEvents.HALLOWEEN_CANDY, 1);			
		}
		return super.onKill(npc, player, isPet);
	}
	
	private void spawnGhost(L2Npc npc, L2PcInstance player) 
	{
		QuestState st = player.getQuestState(qn);
		int npcId = npc.getNpcId();
		if (FunEvents.HY_ACTIVE_DROP)
		{
			L2Attackable newNpc;		
			for (int id: ForestOfDeadNight)
			{
				if (npcId == id)
				{
					newNpc = (L2Attackable) st.addSpawn(GHOST, 60000);
					boolean isPet = false;
					if (player.getSummon() != null)
					{
						isPet = true;
					}
					L2Character originalAttacker = isPet? player.getSummon(): player;
					newNpc.setRunning();
					newNpc.addDamageHate(originalAttacker,0,500);
					newNpc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, originalAttacker);
				}
			}
			for (int id: TheCementary)
			{
				if (npcId == id)
				{
					newNpc = (L2Attackable) st.addSpawn(GHOST, 60000);
					boolean isPet = false;
					if (player.getSummon() != null)
					{
						isPet = true;
					}
					L2Character originalAttacker = isPet? player.getSummon(): player;
					newNpc.setRunning();
					newNpc.addDamageHate(originalAttacker,0,500);
					newNpc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, originalAttacker);
				}
			}
			for (int id: ImperialTomb)
			{
				if (npcId == id)
				{
					newNpc = (L2Attackable) st.addSpawn(GHOST, 60000);
					boolean isPet = false;
					if (player.getSummon() != null)
					{
						isPet = true;
					}
					L2Character originalAttacker = isPet? player.getSummon(): player;
					newNpc.setRunning();
					newNpc.addDamageHate(originalAttacker,0,500);
					newNpc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, originalAttacker);
				}
			}
		}
		else
		{
			// do nothing
		}
	}
	public HallowedYou(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(HALLOWEN_NPC);
		addFirstTalkId(HALLOWEN_NPC);
		addTalkId(HALLOWEN_NPC);
		for (int id : ForestOfDeadNight)
		{
			addKillId(id);
		}
		for (int id : TheCementary)
		{
			addKillId(id);
		}
		for (int id : ImperialTomb)
		{
			addKillId(id);
		}
		addKillId(GHOST);		
	}
	public static void main(String[] args)
	{
		new HallowedYou(-1,qn,"events");
		if(FunEvents.HY_STARTED)
		{
			_log.warning("Event System: Hallowen Event loaded ...");
		}
	}
}