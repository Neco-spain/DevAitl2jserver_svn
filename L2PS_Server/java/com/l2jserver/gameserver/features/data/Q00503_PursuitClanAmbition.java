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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

import com.l2jserver.L2DatabaseFactory;
import com.l2jserver.gameserver.instancemanager.QuestManager;
import com.l2jserver.gameserver.model.L2Clan;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;
import com.l2jserver.util.Rnd;

import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00503_PursuitClanAmbition extends Quest
{
	private static int ImpGraveKepperStat = 1;
	private static final int G_Let_Martien = 3866;
	private static final int Th_Wyrm_Eggs = 3842;
	private static final int Drake_Eggs = 3841;
	private static final int Bl_Wyrm_Eggs = 3840;
	private static final int Mi_Drake_Eggs = 3839;
	private static final int Brooch = 3843;
	private static final int Bl_Anvil_Coin = 3871;
	private static final int G_Let_Balthazar = 3867;
	private static final int Spiteful_Soul_Energy = 14855;
	private static final int G_Let_Rodemai = 3868;
	private static final int Imp_Keys = 3847;
	private static final int Scepter_Judgement = 3869;
	private static final int Proof_Aspiration = 3870;
	private static final int[] EggList =
	{
		Mi_Drake_Eggs,
		Bl_Wyrm_Eggs,
		Drake_Eggs,
		Th_Wyrm_Eggs
	};
	private static final int[] NPC =
	{
		30645,
		30758,
		30759,
		30760,
		30761,
		30762,
		30763,
		30512,
		30764,
		30868,
		30765,
		30766
	};
	private static final String[] STATS =
	{
		"cond",
		"Fritz",
		"Lutz",
		"Kurtz",
		"ImpGraveKeeper"
	};
	
	private class dropList
	{
		public int cond, maxcount, chance;
		public int[] items;
		
		protected dropList(int _cond, int _maxcount, int _chance, int[] _items)
		{
			cond = _cond;
			maxcount = _maxcount;
			chance = _chance;
			items = _items;
		}
	}
	
	private static final TIntObjectHashMap<dropList> drop = new TIntObjectHashMap<>();
	
	public Q00503_PursuitClanAmbition(int id, String name, String descr)
	{
		super(id, name, descr);
		
		addStartNpc(NPC[3]);
		for (int npcId : NPC)
		{
			addTalkId(npcId);
		}
		
		addAttackId(27181);
		for (int mobId : drop.keys())
		{
			addKillId(mobId);
		}
		
		drop.put(20282, new dropList(2, 10, 20, new int[]
		{
			Th_Wyrm_Eggs
		})); // Thunder Wyrm 1
		drop.put(20243, new dropList(2, 10, 15, new int[]
		{
			Th_Wyrm_Eggs
		})); // Thunder Wyrm 2
		drop.put(20137, new dropList(2, 10, 20, new int[]
		{
			Drake_Eggs
		})); // Drake 1
		drop.put(20285, new dropList(2, 10, 25, new int[]
		{
			Drake_Eggs
		})); // Drake 2
		drop.put(20178, new dropList(2, 10, 100, new int[]
		{
			Bl_Wyrm_Eggs
		})); // Blitz Wyrm
		drop.put(20974, new dropList(5, 10, 20, new int[]
		{
			Spiteful_Soul_Energy
		})); // Spiteful Soul Leader
		drop.put(20668, new dropList(10, 0, 15, new int[] {})); // Grave Guard
		drop.put(20179, new dropList(10, 6, 80, new int[]
		{
			Imp_Keys
		})); // GraveKeyKeeper
		drop.put(20181, new dropList(10, 0, 100, new int[] {})); // Imperial Gravekeeper
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
		
		if (event.equals("30760-08.htm"))
		{
			st.giveItems(G_Let_Martien, 1);
			for (String var : STATS)
			{
				st.set(var, "1");
			}
			st.setState(State.STARTED);
		}
		else if (event.equals("30760-12.htm"))
		{
			st.giveItems(G_Let_Balthazar, 1);
			st.set("cond", "4");
		}
		else if (event.equals("30760-16.htm"))
		{
			st.giveItems(G_Let_Rodemai, 1);
			st.set("cond", "7");
		}
		else if (event.equals("30760-20.htm"))
		{
			exit(true, st);
		}
		else if (event.equals("30760-22.htm"))
		{
			st.set("cond", "13");
		}
		else if (event.equals("30760-23.htm"))
		{
			exit(true, st);
		}
		else if (event.equals("30645-03.htm"))
		{
			st.takeItems(G_Let_Martien, -1);
			st.set("cond", "2");
			suscribeMembers(player);
			for (L2PcInstance plr : st.getPlayer().getClan().getOnlineMembers(0))
			{
				QuestState pst = QuestManager.getInstance().getQuest(getName()).newQuestState(plr);
				pst.setState(State.STARTED);
			}
		}
		else if (event.equals("30763-03.htm"))
		{
			if (st.getInt("Kurtz") == 1)
			{
				st.giveItems(Mi_Drake_Eggs, 6);
				st.giveItems(Brooch, 1);
				st.set("Kurtz", "2");
				return "30763-02.htm";
			}
		}
		else if (event.equals("30762-03.htm"))
		{
			addSpawn(27178, npc.getX() + 50, npc.getY() + 50, -2770, 0, false, 120000);
			addSpawn(27178, npc.getX() - 50, npc.getY() - 50, -2770, 0, false, 120000);
			if (st.getInt("Lutz") == 1)
			{
				st.giveItems(Mi_Drake_Eggs, 4);
				st.giveItems(Bl_Wyrm_Eggs, 3);
				st.set("Lutz", "2");
				return "30762-02.htm";
			}
		}
		else if (event.equals("30761-03.htm"))
		{
			addSpawn(27178, npc.getX() + 50, npc.getY() + 50, -3025, 0, false, 120000);
			addSpawn(27178, npc.getX() - 50, npc.getY() - 50, -3020, 0, false, 120000);
			if (st.getInt("Fritz") == 1)
			{
				st.giveItems(Bl_Wyrm_Eggs, 3);
				st.set("Fritz", "2");
				return "30761-02.htm";
			}
		}
		else if (event.equals("30512-03.htm"))
		{
			st.takeItems(Brooch, -1);
			st.giveItems(Bl_Anvil_Coin, 1);
			st.set("Kurtz", "3");
		}
		else if (event.equals("30764-03.htm"))
		{
			st.takeItems(G_Let_Balthazar, -1);
			st.set("cond", "5");
			st.set("Kurtz", "3");
		}
		else if (event.equals("30764-05.htm"))
		{
			st.takeItems(G_Let_Balthazar, -1);
			st.set("cond", "5");
		}
		else if (event.equals("30764-06.htm"))
		{
			st.takeItems(Bl_Anvil_Coin, -1);
			st.set("Kurtz", "4");
		}
		else if (event.equals("30868-04.htm"))
		{
			st.takeItems(G_Let_Rodemai, -1);
			st.set("cond", "8");
		}
		else if (event.equals("30868-06a.htm"))
		{
			st.set("cond", "10");
		}
		else if (event.equals("30868-10.htm"))
		{
			st.set("cond", "12");
		}
		else if (event.equals("30766-04.htm"))
		{
			st.set("cond", "9");
			L2Npc spawnedNpc = addSpawn(30766, 160622, 21230, -3710, 0, false, 90000);
			spawnedNpc.broadcastPacket(new NpcSay(spawnedNpc.getObjectId(), 0, spawnedNpc.getNpcId(), NpcStringId.BLOOD_AND_HONOR));
			spawnedNpc = st.addSpawn(30759, 160665, 21209, -3710, 0, false, 90000);
			spawnedNpc.broadcastPacket(new NpcSay(spawnedNpc.getObjectId(), 0, spawnedNpc.getNpcId(), NpcStringId.AMBITION_AND_POWER));
			spawnedNpc = st.addSpawn(30758, 160665, 21291, -3710, 0, false, 90000);
			spawnedNpc.broadcastPacket(new NpcSay(spawnedNpc.getObjectId(), 0, spawnedNpc.getNpcId(), NpcStringId.WAR_AND_DEATH));
		}
		else if (event.equals("30766-08.htm"))
		{
			st.takeItems(Scepter_Judgement, -1);
			st.exitQuest(true);
			QuestState st1;
			for (L2PcInstance plr : st.getPlayer().getClan().getOnlineMembers(0))
			{
				st1 = plr.getQuestState(getName());
				if (st1 != null)
				{
					st1.exitQuest(true);
				}
			}
			offlineMemberExit(player);
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		if ((npc.getNpcId() != NPC[3]) && (st.getState() == State.CREATED))
		{
			return htmltext;
		}
		
		boolean isLeader = player.isClanLeader();
		
		if ((st.getState() == State.CREATED) && (npc.getNpcId() == 30760))
		{
			for (String var : STATS)
			{
				st.set(var, "0");
			}
			if (player.getClan() != null)
			{
				if (isLeader)
				{
					int clanLevel = player.getClan().getLevel();
					if (st.getQuestItemsCount(Proof_Aspiration) > 0)
					{
						htmltext = "30760-03.htm";
						st.exitQuest(true);
					}
					else if (clanLevel == 4)
					{
						htmltext = "30760-04.htm";
					}
					else
					{
						htmltext = "30760-02.htm";
						st.exitQuest(true);
					}
				}
				else
				{
					htmltext = "30760-04t.htm";
					st.exitQuest(true);
				}
			}
			else
			{
				htmltext = "30760-01.htm";
				st.exitQuest(true);
			}
			return htmltext;
		}
		else if ((player.getClan() != null) && (player.getClan().getLevel() >= 5))
		{
			return htmltext;
		}
		else if (st.isCompleted())
		{
			return htmltext;
		}
		else
		{
			if (isLeader)
			{
				int cond = st.getInt("cond");
				int kurtz = st.getInt("Kurtz");
				int lutz = st.getInt("Lutz");
				int fritz = st.getInt("Fritz");
				if (npc.getNpcId() == 30760)
				{
					if (cond == 1)
					{
						htmltext = "30760-09.htm";
					}
					else if (cond == 2)
					{
						htmltext = "30760-10.htm";
					}
					else if (cond == 3)
					{
						htmltext = "30760-11.htm";
					}
					else if (cond == 4)
					{
						htmltext = "30760-13.htm";
					}
					else if (cond == 5)
					{
						htmltext = "30760-14.htm";
					}
					else if (cond == 6)
					{
						htmltext = "30760-15.htm";
					}
					else if (cond == 7)
					{
						htmltext = "30760-17.htm";
					}
					else if (cond == 12)
					{
						htmltext = "30760-19.htm";
					}
					else if (cond == 13)
					{
						htmltext = "30760-24.htm";
					}
					else
					{
						htmltext = "30760-18.htm";
					}
				}
				else if (npc.getNpcId() == 30645)
				{
					if (cond == 1)
					{
						htmltext = "30645-02.htm";
					}
					else if (cond == 2)
					{
						if (checkEggs(st) && (kurtz > 1) && (lutz > 1) && (fritz > 1))
						{
							htmltext = "30645-05.htm";
							st.set("cond", "3");
							for (int item : EggList)
							{
								st.takeItems(item, -1);
							}
						}
						else
						{
							htmltext = "30645-04.htm";
						}
					}
					else if (cond == 3)
					{
						htmltext = "30645-07.htm";
					}
					else
					{
						htmltext = "30645-08.htm";
					}
				}
				else if (cond == 2)
				{
					if (npc.getNpcId() == 30762)
					{
						htmltext = "30762-01.htm";
					}
					else if (npc.getNpcId() == 32763)
					{
						htmltext = "30763-01.htm";
					}
					else if (npc.getNpcId() == 32761)
					{
						htmltext = "30761-01.htm";
					}
				}
				else if (npc.getNpcId() == 30512)
				{
					if (kurtz == 1)
					{
						htmltext = "30512-01.htm";
					}
					else if (kurtz == 2)
					{
						htmltext = "30512-02.htm";
					}
					else
					{
						htmltext = "30512-04.htm";
					}
					
					return htmltext;
				}
				else if (npc.getNpcId() == 30764)
				{
					if (cond == 4)
					{
						if (kurtz > 2)
						{
							htmltext = "30764-04.htm";
						}
						else
						{
							htmltext = "30764-02.htm";
						}
					}
					else if (cond == 5)
					{
						if (st.getQuestItemsCount(Spiteful_Soul_Energy) >= 10)
						{
							htmltext = "30764-08.htm";
							st.takeItems(Spiteful_Soul_Energy, -1);
							st.takeItems(Brooch, -1);
							st.set("cond", "6");
						}
						else
						{
							htmltext = "30764-07.htm";
						}
					}
					else if (cond == 6)
					{
						htmltext = "30764-09.htm";
					}
				}
				else if (npc.getNpcId() == 30868)
				{
					if (cond == 7)
					{
						htmltext = "30868-02.htm";
					}
					else if (cond == 8)
					{
						htmltext = "30868-05.htm";
					}
					else if (cond == 9)
					{
						htmltext = "30868-06.htm";
					}
					else if (cond == 10)
					{
						htmltext = "30868-08.htm";
					}
					else if (cond == 11)
					{
						htmltext = "30868-09.htm";
					}
					else if (cond == 12)
					{
						htmltext = "30868-11.htm";
					}
				}
				else if (npc.getNpcId() == 30766)
				{
					if (cond == 8)
					{
						htmltext = "30766-02.htm";
					}
					else if (cond == 9)
					{
						htmltext = "30766-05.htm";
					}
					else if (cond == 10)
					{
						htmltext = "30766-06.htm";
					}
					else if ((cond == 11) || (cond == 12) || (cond == 13))
					{
						htmltext = "30766-07.htm";
					}
				}
				else if (npc.getNpcId() == 30765)
				{
					if (cond == 10)
					{
						if (st.getQuestItemsCount(Imp_Keys) < 6)
						{
							htmltext = "30765-03a.htm";
						}
						else if (st.getInt("ImpGraveKeeper") == 3)
						{
							htmltext = "30765-02.htm";
							st.set("cond", "11");
							st.takeItems(Imp_Keys, 6);
							st.giveItems(Scepter_Judgement, 1);
						}
						else
						{
							htmltext = "30765-02a.htm";
						}
					}
					else
					{
						htmltext = "30765-03b.htm";
					}
				}
				else if (npc.getNpcId() == 30759)
				{
					htmltext = "30759-01.htm";
				}
				else if (npc.getNpcId() == 30758)
				{
					htmltext = "30758-01.htm";
				}
				return htmltext;
			}
			else
			{
				int cond = getLeaderVar(st, "cond");
				
				if ((npc.getNpcId() == 30645) && ((cond == 1) || (cond == 2) || (cond == 3)))
				{
					htmltext = "30645-01.htm";
				}
				else if (npc.getNpcId() == 30868)
				{
					if ((cond == 9) || (cond == 10))
					{
						htmltext = "30868-07.htm";
					}
					else if (cond == 7)
					{
						htmltext = "30868-01.htm";
					}
				}
				else if ((npc.getNpcId() == 30764) && (cond == 4))
				{
					htmltext = "30764-01.htm";
				}
				else if ((npc.getNpcId() == 30766) && (cond == 8))
				{
					htmltext = "30766-01.htm";
				}
				else if ((npc.getNpcId() == 30512) && (cond < 6) && (cond > 2))
				{
					htmltext = "30512-01a.htm";
				}
				else if ((npc.getNpcId() == 30765) && (cond == 10))
				{
					htmltext = "30765-01.htm";
				}
				else if (npc.getNpcId() == 30760)
				{
					if (cond == 3)
					{
						htmltext = "30760-11t.htm";
					}
					else if (cond == 4)
					{
						htmltext = "30760-15t.htm";
					}
					else if (cond == 12)
					{
						htmltext = "30760-19t.htm";
					}
					else if (cond == 13)
					{
						htmltext = "30766-24t.htm";
					}
				}
				return htmltext;
			}
		}
	}
	
	@Override
	public final String onAttack(L2Npc npc, L2PcInstance player, int damage, boolean isPet, L2Skill skill)
	{
		if ((npc.getMaxHp() / 2) > npc.getCurrentHp())
		{
			if (Rnd.get(100) < 4)
			{
				if (ImpGraveKepperStat == 1)
				{
					for (int i = 0; i < 19; i++)
					{
						int x = (int) (100 * Math.cos(i * .785));
						int y = (int) (100 * Math.sin(i * .785));
						addSpawn(27180, npc.getX() + x, npc.getY() + y, npc.getZ(), 0, false, 0);
					}
					ImpGraveKepperStat = 2;
				}
				else
				{
					Collection<L2PcInstance> plrs = npc.getKnownList().getKnownPlayers().values();
					{
						if (!plrs.isEmpty())
						{
							L2PcInstance playerToTP = (L2PcInstance) plrs.toArray()[Rnd.get(plrs.size())];
							playerToTP.teleToLocation(185462, 20342, -3250);
						}
					}
				}
			}
		}
		return super.onAttack(npc, player, damage, isPet, skill);
	}
	
	@Override
	public final String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState leader_st = null;
		if (player.isClanLeader())
		{
			leader_st = player.getQuestState(getName());
		}
		else
		{
			L2Clan clan = player.getClan();
			if (clan != null)
			{
				if (clan.getLeader() != null)
				{
					L2PcInstance leader = clan.getLeader().getPlayerInstance();
					if (leader != null)
					{
						if (player.isInsideRadius(leader, 2000, false, false))
						{
							leader_st = leader.getQuestState(getName());
						}
					}
				}
			}
		}
		if (leader_st != null)
		{
			if (leader_st.getState() != State.STARTED)
			{
				return super.onKill(npc, player, isPet);
			}
			
			dropList droplist;
			synchronized (drop)
			{
				droplist = drop.get(npc.getNpcId());
			}
			
			int cond = leader_st.getInt("cond");
			
			if ((cond == droplist.cond) && (Rnd.get(100) < droplist.chance))
			{
				if (droplist.items.length > 0)
				{
					giveItem(droplist.items[0], droplist.maxcount, leader_st);
				}
				else
				{
					if (npc.getNpcId() == 27181)
					{
						L2Npc spawnedNpc = leader_st.addSpawn(30765, 120000);
						npc.broadcastPacket(new NpcSay(spawnedNpc.getObjectId(), 0, spawnedNpc.getNpcId(), NpcStringId.CURSE_OF_THE_GODS_ON_THE_ONE_THAT_DEFILES_THE_PROPERTY_OF_THE_EMPIRE));
						leader_st.set("ImpGraveKeeper", "3");
						ImpGraveKepperStat = 1;
					}
					else
					{
						leader_st.addSpawn(27179);
					}
				}
			}
		}
		return super.onKill(npc, player, isPet);
	}
	
	private void exit(boolean complete, QuestState st)
	{
		if (complete)
		{
			st.giveItems(Proof_Aspiration, 1);
			st.addExpAndSp(0, 250000);
			for (String var : STATS)
			{
				st.unset(var);
			}
			st.exitQuest(false);
		}
		else
		{
			st.exitQuest(true);
		}
		st.takeItems(Scepter_Judgement, -1);
		QuestState st1;
		for (L2PcInstance plr : st.getPlayer().getClan().getOnlineMembers(0))
		{
			st1 = plr.getQuestState(getName());
			if (st1 != null)
			{
				st1.exitQuest(true);
			}
		}
		offlineMemberExit(st.getPlayer());
	}
	
	private void offlineMemberExit(L2PcInstance plr)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement offline = con.prepareStatement("DELETE FROM character_quests WHERE name = ? and charId IN (SELECT charId FROM characters WHERE clanId =? AND online=0");
			offline.setString(1, getName());
			offline.setInt(2, plr.getClan().getClanId());
			offline.execute();
			offline.close();
		}
		catch (Exception e)
		{
			System.out.println("Pursuit of Clan Ambition: cannot delete quest states offline");
		}
	}
	
	private void suscribeMembers(L2PcInstance plr)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement offline = con.prepareStatement("SELECT charId FROM characters WHERE clanid=? AND online=0");
			offline.setInt(1, plr.getClan().getClanId());
			ResultSet rs = offline.executeQuery();
			while (rs.next())
			{
				PreparedStatement insertion = con.prepareStatement("INSERT INTO character_quests (charId,name,var,value) VALUES (?,?,?,?)");
				insertion.setInt(1, rs.getInt("charId"));
				insertion.setString(2, getName());
				insertion.setString(3, "<state>");
				insertion.setString(4, "Started");
				insertion.execute();
				insertion.close();
			}
		}
		catch (Exception e)
		{
			System.out.println("Pursuit of Clan Ambition: cannot insert quest states offline");
		}
	}
	
	private int getLeaderVar(QuestState st, String var)
	{
		int val = -1;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			L2Clan clan = st.getPlayer().getClan();
			if (clan == null)
			{
				return -1;
			}
			L2PcInstance leader = clan.getLeader().getPlayerInstance();
			if (leader != null)
			{
				return leader.getQuestState(getName()).getInt(var);
			}
			PreparedStatement offline = con.prepareStatement("SELECT value FROM character_quests WHERE charId=? AND var=? AND name=?");
			offline.setInt(1, st.getPlayer().getClan().getLeaderId());
			offline.setString(2, var);
			offline.setString(3, getName());
			ResultSet rs = offline.executeQuery();
			while (rs.next())
			{
				val = rs.getInt("value");
			}
		}
		catch (Exception e)
		{
			System.out.println("Pursuit of Clan Ambition: cannot read quest states offline clan leader");
		}
		return val;
	}
	
	private boolean checkEggs(QuestState st)
	{
		int count = 0;
		for (int item : EggList)
		{
			if (st.getQuestItemsCount(item) > 9)
			{
				count += 1;
			}
		}
		return count > 3;
	}
	
	private void giveItem(int item, int maxcount, QuestState st)
	{
		long count = st.getQuestItemsCount(item);
		if (count < maxcount)
		{
			st.giveItems(item, 1);
			if (count == (maxcount - 1))
			{
				st.playSound("ItemSound.quest_middle");
			}
			else
			{
				st.playSound("ItemSound.quest_itemget");
			}
		}
	}
	
	public static void main(String[] args)
	{
		new Q00503_PursuitClanAmbition(503, Q00503_PursuitClanAmbition.class.getSimpleName(), "Pursuit Clan Ambition");
	}
}