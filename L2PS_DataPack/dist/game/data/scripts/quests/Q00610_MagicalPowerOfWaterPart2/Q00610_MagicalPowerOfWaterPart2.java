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
package quests.Q00610_MagicalPowerOfWaterPart2;

import java.util.Vector;

import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;
import com.l2jserver.util.Rnd;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00610_MagicalPowerOfWaterPart2 extends Quest
{
	protected static final int Asefa = 31372;
	protected static final int Alter = 31560;
	protected static final int Ketra_Orcs[] =
	{
		21324,
		21325,
		21327,
		21328,
		21329,
		21331,
		21332,
		21334,
		21335,
		21336,
		21338,
		21339,
		21340,
		21342,
		21343,
		21344,
		21345,
		21346,
		21347,
		21348,
		21349
	};
	protected static final int Ashutar = 25316;
	protected static final int Totem2 = 7238;
	protected static final int Ice_Heart = 7239;
	
	protected static boolean isAttacked = true;
	
	public Q00610_MagicalPowerOfWaterPart2(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(Asefa);
		addTalkId(Asefa);
		addTalkId(Alter);
		
		addKillId(Ashutar);
		addAttackId(Ashutar);
		
		for (int Ketra_Orc : Ketra_Orcs)
		{
			addKillId(Ketra_Orc);
		}
		
		questItemIds = new int[]
		{
			Ice_Heart
		};
	}
	
	protected L2Npc FindTemplate(int npcId)
	{
		for (L2Spawn aSpawnTable : SpawnTable.getInstance().getAllSpawns())
		{
			if (aSpawnTable.getNpcid() == npcId)
			{
				return aSpawnTable.getLastSpawn();
			}
		}
		return null;
	}
	
	protected void AutoChat(L2Npc npc, NpcStringId npcString)
	{
		if ((npc.getKnownList() != null) && (npc.getKnownList().getKnownPlayers() != null))
		{
			for (L2PcInstance charOne : npc.getKnownList().getKnownPlayers().values())
			{
				NpcSay cs = new NpcSay(npc.getObjectId(), 0, npc.getNpcId(), npcString);
				charOne.sendPacket(cs);
			}
		}
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
		
		long Green_Totem = st.getQuestItemsCount(Totem2);
		long Heart = st.getQuestItemsCount(Ice_Heart);
		
		if (event.equalsIgnoreCase("FixMinuteTick"))
		{
			if (isAttacked)
			{
				isAttacked = false;
			}
			
			else
			{
				this.cancelQuestTimer("FixMinuteTick", npc, null);
				npc.reduceCurrentHp(999999999, npc, null);
				L2Npc alterInstance = FindTemplate(Alter);
				if (alterInstance != null)
				{
					alterInstance.teleToLocation(105452, -36775, -1050);
				}
				
				alterInstance.setBusy(false);
			}
			return "";
		}
		else if (event.equalsIgnoreCase("31372-04.htm"))
		{
			if ((player.getLevel() >= 75) && (player.getAllianceWithVarkaKetra() >= 2))
			{
				if (Green_Totem != 0)
				{
					st.set("cond", "1");
					st.set("id", "1");
					st.setState(State.STARTED);
					st.playSound("ItemSound.quest_accept");
					htmltext = "31372-04.htm";
				}
				else
				{
					htmltext = "31372-02.htm";
					st.exitQuest(true);
				}
			}
			else
			{
				htmltext = "31372-03.htm";
				st.exitQuest(true);
			}
		}
		else if (event.equalsIgnoreCase("31372-08.htm"))
		{
			if (Heart != 0)
			{
				htmltext = "31372-08.htm";
				st.takeItems(Ice_Heart, -1);
				st.addExpAndSp(10000, 0);
				st.unset("id");
				st.unset("cond");
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(true);
			}
			else
			{
				htmltext = "31372-09.htm";
			}
		}
		else if (event.equalsIgnoreCase("31560-02.htm"))
		{
			if (Green_Totem == 0)
			{
				htmltext = "31560-04.htm";
			}
			else if (npc.isBusy())
			{
				htmltext = "31560-03.htm";
			}
			else
			{
				L2Npc spawnedNpc = FindTemplate(Ashutar);
				if (spawnedNpc != null)
				{
					spawnedNpc.teleToLocation(104825, -36926, -1136);
				}
				else
				{
					spawnedNpc = st.addSpawn(Ashutar, 104825, -36926, -1136);
				}
				
				st.takeItems(Totem2, 1);
				st.set("id", "2");
				st.set("cond", "2");
				npc.setBusy(true);
				npc.teleToLocation(105236, -36826, -1071);
				isAttacked = true;
				_log.info("Fix minute 1");
				this.startQuestTimer("FixMinuteTick", 1, spawnedNpc, null, true);
				AutoChat(spawnedNpc, NpcStringId.THE_MAGICAL_POWER_OF_FIRE_IS_ALSO_THE_POWER_OF_FLAMES_AND_LAVA_IF_YOU_DARE_TO_CONFRONT_IT_ONLY_DEATH_WILL_AWAIT_YOU);
			}
			
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		
		QuestState st = player.getQuestState(getName());
		if (st != null)
		{
			int npcId = npc.getNpcId();
			int id = st.getInt("id");
			long Green_Totem = st.getQuestItemsCount(Totem2);
			long Heart = st.getQuestItemsCount(Ice_Heart);
			
			if (npcId == Asefa)
			{
				if (st.getState() == State.CREATED)
				{
					htmltext = "31372-01.htm";
				}
				else if ((id == 1) || (id == 2))
				{
					htmltext = "31372-05.htm";
				}
				else if (id == 3)
				{
					if (Heart != 0)
					{
						htmltext = "31372-06.htm";
					}
					else
					{
						htmltext = "31372-07.htm";
					}
				}
			}
			else if (npcId == Alter)
			{
				if (id == 1)
				{
					htmltext = "31560-01.htm";
				}
				else if (id == 2)
				{
					if (npc.isBusy())
					{
						htmltext = "31560-03.htm";
					}
					else
					{
						if (Green_Totem == 0)
						{
							htmltext = "31560-04.htm";
						}
						else if (npc.isBusy())
						{
							htmltext = "31560-03.htm";
						}
						else
						{
							htmltext = "31560-02.htm";
							L2Npc spawnedNpc = FindTemplate(Ashutar);
							if (spawnedNpc != null)
							{
								spawnedNpc.teleToLocation(104825, -36926, -1136);
							}
							else
							{
								spawnedNpc = st.addSpawn(Ashutar, 104825, -36926, -1136);
							}
							st.takeItems(Totem2, 1);
							st.set("id", "2");
							st.set("cond", "2");
							npc.setBusy(true);
							npc.teleToLocation(105236, -36826, -1071);
							isAttacked = true;
							_log.info("Fix minute 1-1");
							this.startQuestTimer("FixMinuteTick", 1, spawnedNpc, null, true);
							AutoChat(spawnedNpc, NpcStringId.THE_MAGICAL_POWER_OF_FIRE_IS_ALSO_THE_POWER_OF_FLAMES_AND_LAVA_IF_YOU_DARE_TO_CONFRONT_IT_ONLY_DEATH_WILL_AWAIT_YOU);
						}
					}
				}
				else if (id == 3)
				{
					htmltext = "31560-05.htm";
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isPet)
	{
		isAttacked = true;
		return "";
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		int npcId = npc.getNpcId();
		
		if (npcId == Ashutar)
		{
			L2Npc alterInstance = FindTemplate(Alter);
			if (alterInstance != null)
			{
				alterInstance.setBusy(false);
				alterInstance.setIsInvul(false);
				alterInstance.reduceCurrentHp(999999999, alterInstance, null);
			}
			this.cancelQuestTimer("FixMinuteTick", npc, null);
			L2Party party = player.getParty();
			if (party != null)
			{
				Vector<QuestState> partyQuestMember = new Vector<>();
				for (L2PcInstance aPartyMember : party.getMembers())
				{
					QuestState st1 = aPartyMember.getQuestState(getName());
					if (st1 != null)
					{
						if ((st1.getState() == State.STARTED) && ((st1.getInt("cond") == 1) || (st1.getInt("cond") == 2)))
						{
							partyQuestMember.add(st1);
						}
					}
				}
				if (partyQuestMember.size() == 0)
				{
					return "";
				}
				QuestState st1 = partyQuestMember.get(Rnd.get(partyQuestMember.size()));
				if (st1.getQuestItemsCount(Totem2) > 0)
				{
					st1.takeItems(Totem2, 1);
				}
				st1.giveItems(Ice_Heart, 1);
				st1.set("cond", "3");
				st1.set("id", "3");
				st1.playSound("ItemSound.quest_middle");
			}
			else
			{
				if ((st.getState() == State.STARTED) && ((st.getInt("cond") == 1) || (st.getInt("cond") == 2)))
				{
					if (st.getQuestItemsCount(Totem2) > 0)
					{
						st.takeItems(Totem2, 1);
					}
					st.giveItems(Ice_Heart, 1);
					st.set("cond", "3");
					st.set("id", "3");
					st.playSound("ItemSound.quest_middle");
				}
			}
		}
		else
		{
			boolean isKetraMob = false;
			for (int Ketra_Orc : Ketra_Orcs)
			{
				if (npcId == Ketra_Orc)
				{
					isKetraMob = true;
					break;
				}
			}
			if (isKetraMob)
			{
				if (st.getQuestItemsCount(Ice_Heart) != 0)
				{
					st.takeItems(Ice_Heart, -1);
					st.unset("cond");
					st.unset("id");
					st.exitQuest(true);
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00610_MagicalPowerOfWaterPart2(610, Q00610_MagicalPowerOfWaterPart2.class.getSimpleName(), "Magical Power Of Water Part 2");
	}
}