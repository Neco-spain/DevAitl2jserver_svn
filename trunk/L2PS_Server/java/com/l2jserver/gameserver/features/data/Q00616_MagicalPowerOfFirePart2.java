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
public class Q00616_MagicalPowerOfFirePart2 extends Quest
{
	protected static final int Udan = 31379;
	protected static final int Alter = 31558;
	protected static final int Varka_Mobs[] =
	{
		21350,
		21351,
		21353,
		21354,
		21355,
		21357,
		21358,
		21360,
		21361,
		21362,
		21369,
		21370,
		21364,
		21365,
		21366,
		21368,
		21371,
		21372,
		21373,
		21374,
		21375
	};
	protected static final int Nastron = 25306;
	protected static final int Totem2 = 7243;
	protected static final int Fire_Heart = 7244;
	
	protected static boolean isAttacked = true;
	
	public Q00616_MagicalPowerOfFirePart2(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(Udan);
		addTalkId(Udan);
		addTalkId(Alter);
		
		addKillId(Nastron);
		addAttackId(Nastron);
		for (int Varka_Mob : Varka_Mobs)
		{
			addKillId(Varka_Mob);
		}
		
		questItemIds = new int[]
		{
			Fire_Heart
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
	
	@SuppressWarnings("null")
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
		long Heart = st.getQuestItemsCount(Fire_Heart);
		
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
					alterInstance.teleToLocation(142368, -82512, -6487);
				}
				alterInstance.setBusy(false);
			}
			return "";
		}
		else if (event.equalsIgnoreCase("31379-04.htm"))
		{
			if ((player.getLevel() >= 75) && (player.getAllianceWithVarkaKetra() <= -2))
			{
				if (Green_Totem != 0)
				{
					st.set("cond", "1");
					st.set("id", "1");
					st.setState(State.STARTED);
					st.playSound("ItemSound.quest_accept");
					htmltext = "31379-04.htm";
				}
				else
				{
					htmltext = "31379-02.htm";
					st.exitQuest(true);
				}
			}
			else
			{
				htmltext = "31379-03.htm";
				st.exitQuest(true);
			}
		}
		else if (event.equalsIgnoreCase("31379-08.htm"))
		{
			if (Heart != 0)
			{
				htmltext = "31379-08.htm";
				st.takeItems(Fire_Heart, -1);
				st.addExpAndSp(10000, 0);
				st.unset("id");
				st.unset("cond");
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(true);
			}
			else
			{
				htmltext = "31379-09.htm";
			}
		}
		else if (event.equalsIgnoreCase("31558-02.htm"))
		{
			if (Green_Totem == 0)
			{
				htmltext = "31558-04.htm";
			}
			else if (npc.isBusy())
			{
				htmltext = "31558-03.htm";
			}
			else
			{
				L2Npc spawnedNpc = FindTemplate(Nastron);
				if (spawnedNpc != null)
				{
					spawnedNpc.teleToLocation(142528, -82528, -6496);
				}
				else
				{
					spawnedNpc = st.addSpawn(Nastron, 142528, -82528, -6496);
				}
				
				st.takeItems(Totem2, 1);
				st.set("id", "2");
				st.set("cond", "2");
				npc.setBusy(true);
				npc.teleToLocation(142368, -82512, -6487);
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
			long Heart = st.getQuestItemsCount(Fire_Heart);
			if (npcId == Udan)
			{
				if (st.getState() == State.CREATED)
				{
					htmltext = "31379-01.htm";
				}
				else if ((id == 1) || (id == 2))
				{
					htmltext = "31379-05.htm";
				}
				else if (id == 3)
				{
					if (Heart != 0)
					{
						htmltext = "31379-06.htm";
					}
					else
					{
						htmltext = "31379-07.htm";
					}
				}
			}
			else if (npcId == Alter)
			{
				if (id == 1)
				{
					htmltext = "31558-01.htm";
				}
				else if (id == 2)
				{
					if (npc.isBusy())
					{
						htmltext = "31558-03.htm";
					}
					else
					{
						if (Green_Totem == 0)
						{
							htmltext = "31558-04.htm";
						}
						else if (npc.isBusy())
						{
							htmltext = "31558-03.htm";
						}
						else
						{
							htmltext = "31558-02.htm";
							L2Npc spawnedNpc = FindTemplate(Nastron);
							if (spawnedNpc != null)
							{
								spawnedNpc.teleToLocation(142528, -82528, -6496);
							}
							else
							{
								spawnedNpc = st.addSpawn(Nastron, 142528, -82528, -6496);
							}
							st.takeItems(Totem2, 1);
							st.set("id", "2");
							st.set("cond", "2");
							npc.setBusy(true);
							npc.teleToLocation(142368, -82512, -6487);
							isAttacked = true;
							_log.info("Fix minute 1");
							this.startQuestTimer("FixMinuteTick", 1, spawnedNpc, null, true);
							AutoChat(spawnedNpc, NpcStringId.THE_MAGICAL_POWER_OF_FIRE_IS_ALSO_THE_POWER_OF_FLAMES_AND_LAVA_IF_YOU_DARE_TO_CONFRONT_IT_ONLY_DEATH_WILL_AWAIT_YOU);
						}
					}
				}
				else if (id == 3)
				{
					htmltext = "31558-05.htm";
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
		if (npcId == Nastron)
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
				QuestState st2 = partyQuestMember.get(Rnd.get(partyQuestMember.size()));
				if (st2.getQuestItemsCount(Totem2) > 0)
				{
					st2.takeItems(Totem2, 1);
				}
				st2.giveItems(Fire_Heart, 1);
				st2.set("cond", "3");
				st2.set("id", "3");
				st2.playSound("ItemSound.quest_middle");
			}
			else
			{
				if ((st.getState() == State.STARTED) && ((st.getInt("cond") == 1) || (st.getInt("cond") == 2)))
				{
					if (st.getQuestItemsCount(Totem2) > 0)
					{
						st.takeItems(Totem2, 1);
					}
					st.giveItems(Fire_Heart, 1);
					st.set("cond", "3");
					st.set("id", "3");
					st.playSound("ItemSound.quest_middle");
				}
			}
		}
		else
		{
			boolean isVarkaMob = false;
			for (int Varka_Mob : Varka_Mobs)
			{
				if (npcId == Varka_Mob)
				{
					isVarkaMob = true;
					break;
				}
			}
			
			if (isVarkaMob)
			{
				if (st.getQuestItemsCount(Fire_Heart) != 0)
				{
					st.takeItems(Fire_Heart, -1);
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
		new Q00616_MagicalPowerOfFirePart2(616, Q00616_MagicalPowerOfFirePart2.class.getSimpleName(), "Magical Power Of Fire Part 2");
	}
}