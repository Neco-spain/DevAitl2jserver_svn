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
package events.SuperStar;

import com.l2jserver.FunEvents;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.util.Rnd;

/**
 * @Fixed by L2Ps Team
 * www.l2ps.tode.cz
 */
public class SuperStar extends Quest
{
	private static final String qn = "SuperStar";
	private static final int SuperStarNpc = 503;
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
			st.setState(State.STARTED);
		}
		String htmltext = "";
		if (FunEvents.SS_STARTED)
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
		int starInteger;
		try
		{
			starInteger = Integer.valueOf(st.get("starInteger"));
		}
		catch (Exception e)
		{
			starInteger = 0;
		}
		
		if (event.equalsIgnoreCase("prizes"))
		{
			htmltext = "prizes.htm";
		}
		if (event.equalsIgnoreCase("play_5"))
		{
			if (starInteger >= 5)
			{
				rewardPlayer(player,5);
				htmltext = "prizes.htm";
			}
			else
			{
				htmltext = "no.htm";
			}
		}
		if (event.equalsIgnoreCase("play_25"))
		{
			if (starInteger >= 25)
			{
				rewardPlayer(player,25);
				htmltext = "prizes.htm";
			}
			else
			{
				htmltext = "no.htm";
			}
		}
		if (event.equalsIgnoreCase("play_50"))
		{
			if (starInteger >= 50)
			{
				rewardPlayer(player,50);
				htmltext = "prizes.htm";
			}
			else
			{
				htmltext = "no.htm";
			}
		}
		if (event.equalsIgnoreCase("info"))
		{
			htmltext = "info.htm";
			if (starInteger >0)
			{
				player.sendMessage("You have "+ String.valueOf(starInteger)+" stars.");
			}
		}
		if (event.equalsIgnoreCase("back"))
		{
			htmltext = "welcome.htm";
		}
       	return htmltext;
	}

	/**
	 * RewardList
	 * 5 -] Adena 450-4500, Backup Stones - 1
	 * 25 -] Adena 750-4500, Vitality 5000, Backup Stones - 1
	 * 50 -] Adena 4000-4500, Vitality 10000,Backup Stones - 2
	 * @param player
	 * @param i
	 */
	private void rewardPlayer(L2PcInstance player, int i) 
	{
		QuestState st = player.getQuestState(qn);
		int starInteger;
		try
		{
			starInteger = Integer.valueOf(st.get("starInteger"));
		}
		catch (Exception e)
		{
			starInteger = 0;
		}
		int consumedValue = 0;
		if (i == 5)
		{
			consumedValue = starInteger - i;
			st.set("starInteger", String.valueOf(consumedValue));
			int randomChance = Rnd.get(100);
			if (randomChance < 50)
			{
				st.giveItems(57, Rnd.get(450,player.getMaxHp()));
			}
			else if (randomChance >=50 && randomChance < 70)
			{				
				int level = player.getLevel();
				if (level < 40)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_WEP_D, 1);	
				}
				else if (level >=40 && level < 52)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_WEP_C, 1);
				}
				else if (level >=52 && level < 61)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_WEP_B, 1);
				}
				else if (level >=61 && level < 76)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_WEP_A, 1);
				}
				else
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_WEP_S, 1);
				}
			}
			else
			{
				int level = player.getLevel();
				if (level < 40)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_ARM_D, 1);	
				}
				else if (level >=40 && level < 52)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_ARM_C, 1);
				}
				else if (level >=52 && level < 61)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_ARM_B, 1);
				}
				else if (level >=61 && level < 76)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_ARM_A, 1);
				}
				else
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_ARM_S, 1);
				}
			}
		}
		else if (i == 25)
		{
			consumedValue = starInteger - i;
			st.set("starInteger", String.valueOf(consumedValue));
			int randomChance = Rnd.get(100);
			if (randomChance < 50)
			{
				st.giveItems(57, Rnd.get(750,player.getMaxHp()));
			}
			else if (randomChance >=50 && randomChance < 70)
			{				
				int level = player.getLevel();
				if (level < 40)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_WEP_D, 1);	
				}
				else if (level >=40 && level < 52)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_WEP_C, 1);
				}
				else if (level >=52 && level < 61)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_WEP_B, 1);
				}
				else if (level >=61 && level < 76)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_WEP_A, 1);
				}
				else
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_WEP_S, 1);
				}
			}
			else if (randomChance >=70 && randomChance < 90)
			{
				int level = player.getLevel();
				if (level < 40)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_ARM_D, 1);	
				}
				else if (level >=40 && level < 52)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_ARM_C, 1);
				}
				else if (level >=52 && level < 61)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_ARM_B, 1);
				}
				else if (level >=61 && level < 76)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_ARM_A, 1);
				}
				else
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_ARM_S, 1);
				}
			}
			else
			{
				player.setVitalityPoints(player.getVitalityPoints() + 5000, false);
			}
		}
		else if (i == 50)
		{
			consumedValue = starInteger - i;
			st.set("starInteger", String.valueOf(consumedValue));
			int randomChance = Rnd.get(100);
			if (randomChance < 50)
			{
				st.giveItems(57, Rnd.get(1250,player.getMaxHp()));
			}
			else if (randomChance >=50 && randomChance < 70)
			{				
				int level = player.getLevel();
				if (level < 40)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_WEP_D, 2);	
				}
				else if (level >=40 && level < 52)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_WEP_C, 2);
				}
				else if (level >=52 && level < 61)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_WEP_B, 2);
				}
				else if (level >=61 && level < 76)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_WEP_A, 2);
				}
				else
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_WEP_S, 2);
				}
			}
			else if (randomChance >=70 && randomChance < 90)
			{
				int level = player.getLevel();
				if (level < 40)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_ARM_D, 2);	
				}
				else if (level >=40 && level < 52)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_ARM_C, 2);
				}
				else if (level >=52 && level < 61)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_ARM_B, 2);
				}
				else if (level >=61 && level < 76)
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_ARM_A, 2);
				}
				else
				{
					st.giveItems(FunEvents.SS_BACKUP_STONE_ARM_S, 2);
				}
			}
			else
			{
				player.setVitalityPoints(player.getVitalityPoints() + 10000, false);
			}
		}		
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
		if (FunEvents.SS_STARTED)
		{
			for(int ID : EventMonsters)
			{ 
				if (npcId == ID)
				{
					int killedValue = 0;
					try
					{
						killedValue = Integer.valueOf(st.get("starInteger")) + 1;						
					}
					catch(Exception e)
					{
						killedValue = 1;
					}
					st.set("starInteger", String.valueOf(killedValue));
					player.sendMessage("You find 1 star and have " + st.get("starInteger")+" stars.");
				}
			}			
		}
		return super.onKill(npc, player, isPet);
	}	
	
	public SuperStar(int questId, String name, String descr)
	{
		super(questId, name, descr);		
		addStartNpc(SuperStarNpc);
		addFirstTalkId(SuperStarNpc);
		addTalkId(SuperStarNpc);
		for (int MONSTER: EventMonsters)
		{
			addKillId(MONSTER);
		}		
	}
	public static void main(String[] args)
	{
		new SuperStar(-1,qn,"events");
		if (FunEvents.SS_STARTED)
			_log.info("Event System: SuperStar Event loaded ...");
	}
}