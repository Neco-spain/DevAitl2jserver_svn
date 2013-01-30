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
 * this program. If not, see <http://com.l2jserver.ru/>.
 */
package instances.ToTheMonastery;

import java.util.List;

import javolution.util.FastList;
import javolution.util.FastMap;

import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.instancemanager.InstanceManager.InstanceWorld;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.entity.Instance;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.util.Rnd;

public class ToTheMonastery extends Quest
{
	private static final String qn = "ToTheMonastery";

	private class ToTheMonasteryWorld extends InstanceWorld
	{
		public long[] storeTime = { 0, 0 };
		public List<L2Npc> firstgroup;
		public List<L2Npc> secondgroup;
		public List<L2Npc> thirdgroup;
		public List<L2Npc> fourthgroup;
		public ToTheMonasteryWorld() {}
	}

	private final FastMap<Integer, InstanceHolder> instanceWorlds = new FastMap<>();
	
	private static class InstanceHolder
	{
		FastList<L2Npc> mobs = new FastList<>();
	}

	private static final int INSTANCE_ID = 151;

	private boolean progress1 = false;
	private boolean progress2 = false;
	private boolean progress3 = false;
	private boolean progress4 = false;
	private boolean active 	  = false;

	// NPCs
	private static int GLOBE 	 	= 32815;
	private static int ELCARDIA2 	 	= 32787;
	private static int EVIL 	 	= 32792;
	private static int GUARDIAN 	 	= 32803;
	private static int WEST_WATCHER  	= 32804;
	private static int NORTH_WATCHER 	= 32805;
	private static int EAST_WATCHER  	= 32806;
	private static int SOUTH_WATCHER 	= 32807;
	private static int WEST_DEVICE 	 	= 32816;
	private static int NORTH_DEVICE  	= 32817;
	private static int EAST_DEVICE 	 	= 32818;
	private static int SOUTH_DEVICE  	= 32819;
	private static int SOLINA 	 	= 32793;
	private static int TELEPORT_DEVICE 	= 32820;
	private static int TELEPORT_DEVICE_2 	= 32837;
	private static int TELEPORT_DEVICE_3 	= 32842;
	private static int TOMB_OF_SAINTESS 	= 32843;

	private static int POWERFUL_DEVICE_1 	= 32838;
	private static int POWERFUL_DEVICE_2 	= 32839;
	private static int POWERFUL_DEVICE_3 	= 32840;
	private static int POWERFUL_DEVICE_4 	= 32841;

	// ITEMs
	private static int SCROLL_OF_ABSTINENCE = 17228;
	private static int SHIELD_OF_SACRIFICE 	= 17229;
	private static int SWORD_OF_HOLYSPIRIT 	= 17230;
	private static int STAFF_OF_BLESSING 	= 17231;

	// MOBs
	private static int ETISETINA 		= 18949;
	private static int[] TombGuardians 	= { 18956, 18957, 18958, 18959 };
	private static int[] Minions 		= { 27403, 27404 };

	private static final int[][] minions_1 = 
	{
		{ 56504, -252840, -6760, 0 },
		{ 56504, -252728, -6760, 0 },
		{ 56392, -252728, -6760, 0 },
		{ 56408, -252840, -6760, 0 }
	};

	private static final int[][] minions_2 = 
	{
		{ 55672, -252728, -6760, 0 },
		{ 55752, -252840, -6760, 0 },
		{ 55768, -252840, -6760, 0 },
		{ 55752, -252712, -6760, 0 }
	};

	private static final int[][] minions_3 = 
	{
		{ 55672, -252120, -6760, 0 },
		{ 55752, -252120, -6760, 0 },
		{ 55656, -252216, -6760, 0 },
		{ 55736, -252216, -6760, 0 }
	};

	private static final int[][] minions_4 = 
	{
		{ 56520, -252232, -6760, 0 },
		{ 56520, -252104, -6760, 0 },
		{ 56424, -252104, -6760, 0 },
		{ 56440, -252216, -6760, 0 }
	};

	private static final int[] NPCs  = { GLOBE, EVIL, GUARDIAN, WEST_WATCHER, NORTH_WATCHER, EAST_WATCHER, SOUTH_WATCHER, WEST_DEVICE, NORTH_DEVICE, EAST_DEVICE, SOUTH_DEVICE, SOLINA, TELEPORT_DEVICE, TELEPORT_DEVICE_2, TELEPORT_DEVICE_3, TOMB_OF_SAINTESS, POWERFUL_DEVICE_1, POWERFUL_DEVICE_2, POWERFUL_DEVICE_3, POWERFUL_DEVICE_4 };

	private static final int[][] TELEPORTS = 
	{
		{ 120664, -86968, -3392 },
		{ 116324, -84994, -3397 },
		{ 85937, -249618, -8320 },
		{ 120727, -86868, -3392 },
		{ 85937, -249618, -8320 },
		{ 82434, -249546, -8320 },
		{ 85691, -252426, -8320 },
		{ 88573, -249556, -8320 },
		{ 85675, -246630, -8320 },
		{ 45512, -249832, -6760 },
		{ 120664, -86968, -3392 },
		{ 56033, -252944, -6760 },
		{ 56081, -250391, -6760 },
		{ 76736, -241021, -10832 },
		{ 76736, -241021, -10832 }
	};

	public ToTheMonastery(int questId, String name, String descr)
	{
		super(questId, name, descr);
	
		addStartNpc(GLOBE);

		for(int npcs : NPCs)
		{
			addTalkId(npcs);
		}

		for (int id : Minions)
		{
			addKillId(id);
		}

		for (int ids : TombGuardians)
		{
			addKillId(ids);
			addSpawnId(ids);
		}

		addKillId(ETISETINA);

		questItemIds = new int[] { SCROLL_OF_ABSTINENCE, SHIELD_OF_SACRIFICE, SWORD_OF_HOLYSPIRIT, STAFF_OF_BLESSING };
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{	
        	String htmltext = event;
		QuestState st = player.getQuestState(qn);
		if (st == null)
			st = newQuestState(player);	
	
		int npcId = npc.getNpcId();

		InstanceWorld tmpworld = InstanceManager.getInstance().getPlayerWorld(player);
		if (tmpworld instanceof ToTheMonasteryWorld)
		{
			ToTheMonasteryWorld world = (ToTheMonasteryWorld) tmpworld;

			if (npcId == EVIL)
			{
				if(event.equalsIgnoreCase("Enter3"))
				{		
					teleportPlayer(npc, player, TELEPORTS[2], player.getInstanceId());
                			return null;
				}
				else if(event.equalsIgnoreCase("teleport_in"))
				{
					teleportPlayer(npc, player, TELEPORTS[9], player.getInstanceId());
					return null;
				}
				else if(event.equalsIgnoreCase("start_scene"))
				{
					QuestState check = player.getQuestState("Q10296_SevenSignPoweroftheSeal");

            				check.set("cond","2");
					ThreadPoolManager.getInstance().scheduleGeneral(new Teleport(npc, player, TELEPORTS[13], world.instanceId), 60500L);
					player.showQuestMovie(29);
					return null;
				}
				else if(event.equalsIgnoreCase("teleport_back"))
				{
					teleportPlayer(npc, player, TELEPORTS[14], player.getInstanceId());
					return null;
				}
			}
			else if (npcId == GUARDIAN)
			{
				if(event.equalsIgnoreCase("ReturnToEris"))
				{
					teleportPlayer(npc, player, TELEPORTS[3], player.getInstanceId());
					return null;
				}
			}
			else if (npcId == TELEPORT_DEVICE || npcId == EVIL)
			{
				if(event.equalsIgnoreCase("teleport_solina"))
				{
					teleportPlayer(npc, player, TELEPORTS[11], player.getInstanceId());
					return null;
				}
			}
			else if(event.equalsIgnoreCase("FirstGroupSpawn"))
			{
				SpawnFirstGroup(world);
				return null;
			}
			else if(event.equalsIgnoreCase("SecondGroupSpawn"))
			{
				SpawnSecondGroup(world);
				return null;
			}
			else if(event.equalsIgnoreCase("ThirdGroupSpawn"))
			{
				SpawnThirdGroup(world);
				return null;
			}
			else if(event.equalsIgnoreCase("FourthGroupSpawn"))
			{
				SpawnFourthGroup(world);
				return null;
			}
			else if(event.equalsIgnoreCase("check_player"))
			{
				cancelQuestTimer("check_player", npc, player);

				if(player.getCurrentHp() < (player.getMaxHp() * 0.8))
				{
					L2Skill skill = SkillTable.getInstance().getInfo(6724, 1);
					if (skill != null)
					{
						npc.setTarget(player);
						npc.doCast(skill);
					}
				}

				if(player.getCurrentMp() < (player.getMaxMp() * 0.5))
				{
					L2Skill skill = SkillTable.getInstance().getInfo(6728, 1);
					if (skill != null)
					{
						npc.setTarget(player);
						npc.doCast(skill);
					}
				}

				if(player.getCurrentHp() < (player.getMaxHp() * 0.1))
				{
					L2Skill skill = SkillTable.getInstance().getInfo(6730, 1);
					if (skill != null)
					{
						npc.setTarget(player);
						npc.doCast(skill);
					}
				}

				if(player.isInCombat())
				{
					L2Skill skill = SkillTable.getInstance().getInfo(6725, 1);
					if (skill != null)
					{
						npc.setTarget(player);
						npc.doCast(skill);
					}
				}
				return "";
			}
			else if(event.equalsIgnoreCase("check_voice"))
			{
				cancelQuestTimer("check_voice", npc, player);

				QuestState qs = player.getQuestState("10294_SevenSignToTheMonastery");
				if(qs != null && !qs.isCompleted())
				{
						if(qs.getInt("cond") == 2)
						{
							if(Rnd.getChance(5))
							{
								if(Rnd.getChance(10))
									npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.ALL, npc.getNpcId(), NpcStringId.IT_SEEMS_THAT_YOU_CANNOT_REMEMBER_TO_THE_ROOM_OF_THE_WATCHER_WHO_FOUND_THE_BOOK));
								else
									npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.ALL, npc.getNpcId(), NpcStringId.REMEMBER_THE_CONTENT_OF_THE_BOOKS_THAT_YOU_FOUND_YOU_CANT_TAKE_THEM_OUT_WITH_YOU));
							}
						}
						else if(qs.getInt("cond") == 3)
						if(Rnd.getChance(8))
							npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.ALL, npc.getNpcId(), NpcStringId.YOUR_WORK_HERE_IS_DONE_SO_RETURN_TO_THE_CENTRAL_GUARDIAN));
				}

				QuestState qs2 = player.getQuestState("10295_SevenSignSolinasTomb");
				if(qs2 != null && !qs2.isCompleted())
				{
					if(qs2.getInt("cond") == 1)
					{
						if(Rnd.getChance(5))
						{
							if(Rnd.getChance(10))
								npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.ALL, npc.getNpcId(), NpcStringId.TO_REMOVE_THE_BARRIER_YOU_MUST_FIND_THE_RELICS_THAT_FIT_THE_BARRIER_AND_ACTIVATE_THE_DEVICE));
							else if(Rnd.getChance(15))
								npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.ALL, npc.getNpcId(), NpcStringId.THE_GUARDIAN_OF_THE_SEAL_DOESNT_SEEM_TO_GET_INJURED_AT_ALL_UNTIL_THE_BARRIER_IS_DESTROYED));
							else
								npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.ALL, npc.getNpcId(), NpcStringId.THE_DEVICE_LOCATED_IN_THE_ROOM_IN_FRONT_OF_THE_GUARDIAN_OF_THE_SEAL_IS_DEFINITELY_THE_BARRIER_THAT_CONTROLS_THE_GUARDIANS_POWER));
						}
					}
				}
				startQuestTimer("check_voice", 100000, npc, player);
				return "";
			}
			else if(event.equalsIgnoreCase("check_follow"))
			{
				cancelQuestTimer("check_follow", npc, player);
				npc.getAI().stopFollow();
				npc.setIsRunning(true);
				npc.getAI().startFollow(player);

				QuestState qs3 = player.getQuestState("10296_SevenSignPoweroftheSeal");
				if(qs3 != null && !qs3.isCompleted())
				{
					if(player.isInCombat())
					{
						if(player.getCurrentHp() < (player.getMaxHp() * 0.8))
						{
							L2Skill skill = SkillTable.getInstance().getInfo(6724, 1);
							if (skill != null)
							{
								npc.setTarget(player);
								npc.doCast(skill);
							}
						}

						if(player.getCurrentMp() < (player.getMaxMp() * 0.5))
						{
							L2Skill skill = SkillTable.getInstance().getInfo(6728, 1);
							if (skill != null)
							{
								npc.setTarget(player);
								npc.doCast(skill);
							}
						}

						if(player.getCurrentHp() < (player.getMaxHp() * 0.1))
						{
							L2Skill skill = SkillTable.getInstance().getInfo(6730, 1);
							if (skill != null)
							{
								npc.setTarget(player);
								npc.doCast(skill);
							}
						}

						L2Skill skill = SkillTable.getInstance().getInfo(6725, 1);
						if (skill != null)
						{
							npc.setTarget(player);
							npc.doCast(skill);
						}
					}
				}
				startQuestTimer("check_follow", 5000, npc, player);
				return "";
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(qn);
		if (st == null)
			st = newQuestState(player);

		int npcId = npc.getNpcId();

		if(npcId == GLOBE)
		{
			if(player.getQuestState("10294_SevenSignToTheMonastery") != null && player.getQuestState("10294_SevenSignToTheMonastery").getState() == State.STARTED)
			{
				enterInstance(npc, player);
				return null;
			}
			else if (player.getQuestState("10294_SevenSignToTheMonastery") != null && player.getQuestState("10294_SevenSignToTheMonastery").getState() == State.COMPLETED && player.getQuestState("10295_SevenSignSolinasTomb") == null)
			{
				enterInstance(npc, player);
				return null;
			}
			else if (player.getQuestState("10295_SevenSignSolinasTomb") != null && player.getQuestState("10295_SevenSignSolinasTomb").getState() != State.COMPLETED)
			{
				enterInstance(npc, player);
				return null;
			}
			else if (player.getQuestState("10295_SevenSignSolinasTomb") != null && player.getQuestState("10295_SevenSignSolinasTomb").getState() == State.COMPLETED && player.getQuestState("10296_SevenSignPoweroftheSeal") == null)
			{
				enterInstance(npc, player);
				return null;
			}
			else if (player.getQuestState("10296_SevenSignPoweroftheSeal") != null && player.getQuestState("10296_SevenSignPoweroftheSeal").getState() != State.COMPLETED)
			{
				enterInstance(npc, player);
				return null;
			}
			else
				htmltext = "32815-00.htm";
		}

		InstanceWorld tmpworld = InstanceManager.getInstance().getPlayerWorld(player);
		if (tmpworld instanceof ToTheMonasteryWorld)
		{
			ToTheMonasteryWorld world = (ToTheMonasteryWorld) tmpworld;

			if (npcId == EVIL)
			{
				InstanceHolder holder = instanceWorlds.get(player.getInstanceId());
				if (holder != null)
				{
					for(L2Npc h : holder.mobs)
					{
						h.deleteMe();
					}
					holder.mobs.clear();
				}
				teleportPlayer(npc, player, TELEPORTS[1], 0);
				player.setInstanceId(0);
				return null;
			}
			if (npcId == WEST_DEVICE)
			{
				teleportPlayer(npc, player, TELEPORTS[5], player.getInstanceId());
				return null;			
			}
			if (npcId == NORTH_DEVICE)
			{
				teleportPlayer(npc, player, TELEPORTS[6], player.getInstanceId());
				return null;			
			}
			if (npcId == EAST_DEVICE)
			{
				teleportPlayer(npc, player, TELEPORTS[7], player.getInstanceId());
				return null;			
			}
			if (npcId == SOUTH_DEVICE)
			{
				teleportPlayer(npc, player, TELEPORTS[8], player.getInstanceId());
				return null;			
			}
			if (npcId == WEST_WATCHER || npcId == NORTH_WATCHER || npcId == EAST_WATCHER || npcId == SOUTH_WATCHER)
			{
				teleportPlayer(npc, player, TELEPORTS[4], player.getInstanceId());
				return null;
			}
			if(npcId == SOLINA || npcId ==TELEPORT_DEVICE  || npcId == TELEPORT_DEVICE_2)
			{
				teleportPlayer(npc, player, TELEPORTS[10], player.getInstanceId());
				return null;
			}
			if(npcId == TELEPORT_DEVICE_3)
			{
				teleportPlayer(npc, player, TELEPORTS[12], player.getInstanceId());
				player.showQuestMovie(28);
				return null;
			}
			if(npcId == POWERFUL_DEVICE_1)
			{
				if(st.getQuestItemsCount(STAFF_OF_BLESSING) > 0)
				{
					st.takeItems(STAFF_OF_BLESSING, -1);
					addSpawn(18953, 45400, -246072, -6754, 49152, false, 0, false, world.instanceId);
					return null;
				}
				else
					htmltext = "no-item.htm";
			}
			if(npcId == POWERFUL_DEVICE_2)
			{
				if(st.getQuestItemsCount(SCROLL_OF_ABSTINENCE) > 0)
				{
					st.takeItems(SCROLL_OF_ABSTINENCE, -1);
					addSpawn(18954, 48968, -249640, -6754, 32768, false, 0, false, world.instanceId);
					return null;
				}
				else
					htmltext = "no-item.htm";
			}
			if(npcId == POWERFUL_DEVICE_3)
			{
				if(st.getQuestItemsCount(SWORD_OF_HOLYSPIRIT) > 0)
				{
					st.takeItems(SWORD_OF_HOLYSPIRIT, -1);
					addSpawn(18955, 45400, -253208, -6754, 16384, false, 0, false, world.instanceId);
					return null;
				}
				else
					htmltext = "no-item.htm";
			}
			if(npcId == POWERFUL_DEVICE_4)
			{
				if(st.getQuestItemsCount(SHIELD_OF_SACRIFICE) > 0)
				{
					st.takeItems(SHIELD_OF_SACRIFICE, -1);
					addSpawn(18952, 41784, -249640, -6754, 0, false, 0, false, world.instanceId);
					return null;
				}
				else
					htmltext = "no-item.htm";
			}
			if(npcId == TOMB_OF_SAINTESS)
			{
				if (active)
					htmltext = "32843-03.htm";
				else
				{
					openDoor(21100101, world.instanceId);
					openDoor(21100102, world.instanceId);
					openDoor(21100103, world.instanceId);
					openDoor(21100104, world.instanceId);
					SpawnFirstGroup(world);
					SpawnSecondGroup(world);
					SpawnThirdGroup(world);
					SpawnFourthGroup(world);
					active = true;
					htmltext = "32843-02.htm";
				}
			}
		}
		return htmltext;
	}

    	@Override
		public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
    	{
        	QuestState st = player.getQuestState(qn);
        	if(st == null)
            		return null;

		int npcId = npc.getNpcId();

		InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		ToTheMonasteryWorld world;

		if (tmpworld instanceof ToTheMonasteryWorld)
		{
			world = (ToTheMonasteryWorld)tmpworld;

			if (npcId == 27403)
			{
				if (world.firstgroup != null)
				{
					world.firstgroup.remove((L2Npc) npc);
					if (world.firstgroup.isEmpty())
					{
						world.firstgroup = null;

						if (progress1)
							cancelQuestTimer("FirstGroupSpawn", npc, player);
						else
							startQuestTimer("FirstGroupSpawn", 10000, npc, player);
					}
				}

				if (world.secondgroup != null)
				{
					world.secondgroup.remove((L2Npc) npc);
					if (world.secondgroup.isEmpty())
					{
						world.secondgroup = null;

						if (progress2)
							cancelQuestTimer("SecondGroupSpawn", npc, player);
						else
							startQuestTimer("SecondGroupSpawn", 10000, npc, player);
					}
				}
			}

			if (npcId == 27404)
			{
				if (world.thirdgroup != null)
				{
					world.thirdgroup.remove((L2Npc) npc);
					if (world.thirdgroup.isEmpty())
					{
						world.thirdgroup = null;

						if (progress3)
							cancelQuestTimer("ThirdGroupSpawn", npc, player);
						else
							startQuestTimer("ThirdGroupSpawn", 10000, npc, player);
					}
				}

				if (world.fourthgroup != null)
				{
					world.fourthgroup.remove((L2Npc) npc);
					if (world.fourthgroup.isEmpty())
					{
						world.fourthgroup = null;

						if (progress4)
							cancelQuestTimer("FourthGroupSpawn", npc, player);
						else
							startQuestTimer("FourthGroupSpawn", 10000, npc, player);
					}
				}
			}

			if(npcId == ETISETINA)
			{
				ThreadPoolManager.getInstance().scheduleGeneral(new Teleport(npc, player, TELEPORTS[0], world.instanceId), 60500L);
				return null;
			}

			if (npcId == 18956)
				progress1 = true;

			if (npcId == 18957)
				progress2 = true;

			if (npcId == 18958)
				progress3 = true;

			if (npcId == 18959)
				progress4 = true;

			if(progress1 && progress2 && progress3 && progress4)
			{
				openDoor(21100018, world.instanceId);
			}
		}
		return "";
	}

	@Override
	public String onSpawn(L2Npc npc)
	{
		if (npc instanceof L2MonsterInstance)
		{
			for (int mobId : TombGuardians)
			{			
				if (mobId == npc.getNpcId())
				{
					final L2MonsterInstance monster = (L2MonsterInstance)npc;
					monster.setIsNoRndWalk(true);
					monster.setIsImmobilized(true);
					break;
				}
			}
		}
		return super.onSpawn(npc);
	}

	protected void enterInstance(L2Npc npc, L2PcInstance player)
	{
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		if (world != null)
		{
			if (!(world instanceof ToTheMonasteryWorld))
			{
				player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER));
				return;
			}
			Instance inst = InstanceManager.getInstance().getInstance(world.instanceId);
			if (inst != null)
			{
				teleportPlayer(npc, player, TELEPORTS[0], world.instanceId);
			}
			return;
		}
		else
		{
			final int instanceId = InstanceManager.getInstance().createDynamicInstance("ToTheMonastery.xml");
			final Instance inst = InstanceManager.getInstance().getInstance(instanceId);
			final int[] returnLoc = { player.getX(), player.getY(), player.getZ() };
			inst.setSpawnLoc(returnLoc);

			world = new ToTheMonasteryWorld();
			world.instanceId = instanceId;
			world.templateId = INSTANCE_ID;
			InstanceManager.getInstance().addWorld(world);
			((ToTheMonasteryWorld) world).storeTime[0] = System.currentTimeMillis();
			world.allowed.add(player.getObjectId());
			teleportPlayer(npc, player, TELEPORTS[0], instanceId);

			openDoor(21100001, world.instanceId);
			openDoor(21100002, world.instanceId);
			openDoor(21100003, world.instanceId);
			openDoor(21100004, world.instanceId);
			openDoor(21100005, world.instanceId);
			openDoor(21100006, world.instanceId);
			openDoor(21100007, world.instanceId);
			openDoor(21100008, world.instanceId);
			openDoor(21100009, world.instanceId);
			openDoor(21100010, world.instanceId);
			openDoor(21100011, world.instanceId);
			openDoor(21100012, world.instanceId);
			openDoor(21100013, world.instanceId);
			openDoor(21100014, world.instanceId);
			openDoor(21100015, world.instanceId);
			openDoor(21100016, world.instanceId);

			return;
		}
	}

	private void teleportPlayer(L2Npc npc, L2PcInstance player, int[] coords, int instanceId)
	{
		InstanceHolder holder = instanceWorlds.get(instanceId);
		if (holder == null && instanceId > 0)
		{
			holder = new InstanceHolder();
			instanceWorlds.put(instanceId, holder);
		}
		player.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		player.setInstanceId(instanceId);
		player.teleToLocation(coords[0], coords[1], coords[2], false);
		cancelQuestTimer("check_follow", npc, player);
		cancelQuestTimer("check_player", npc, player);
		cancelQuestTimer("check_voice", npc, player);
		if (holder != null)
		{
			for(L2Npc h : holder.mobs)
			{
				h.deleteMe();
			}
			holder.mobs.clear();
		}
		if (instanceId > 0)
		{
			L2Npc support = addSpawn(ELCARDIA2, player.getX(), player.getY(),player.getZ(), 0, false, 0, false, player.getInstanceId());
			holder.mobs.add(support);
			startQuestTimer("check_follow", 3000, support, player);
			startQuestTimer("check_player", 3000, support, player);
			startQuestTimer("check_voice", 3000, support, player);
		}
	}

	protected void openDoor(int doorId, int instanceId)
	{
		for (L2DoorInstance door : InstanceManager.getInstance().getInstance(instanceId).getDoors())
		{
			if (door.getDoorId() == doorId)
				door.openMe();
		}
	}

	protected void SpawnFirstGroup(ToTheMonasteryWorld world)
	{
		world.firstgroup = new FastList<>();
		for(int[] spawn : minions_1)
		{
			L2Npc spawnedMob = (L2Npc) addSpawn(27403, spawn[0], spawn[1], spawn[2], spawn[3], false, 0, false, world.instanceId);
			world.firstgroup.add(spawnedMob);
		}
	}

	protected void SpawnSecondGroup(ToTheMonasteryWorld world)
	{
		world.secondgroup = new FastList<>();
		for(int[] spawn : minions_2)
		{
			L2Npc spawnedMob = (L2Npc) addSpawn(27403, spawn[0], spawn[1], spawn[2], spawn[3], false, 0, false, world.instanceId);
			world.secondgroup.add(spawnedMob);
		}
	}

	protected void SpawnThirdGroup(ToTheMonasteryWorld world)
	{
		world.thirdgroup = new FastList<>();
		for(int[] spawn : minions_3)
		{
			L2Npc spawnedMob = (L2Npc) addSpawn(27404, spawn[0], spawn[1], spawn[2], spawn[3], false, 0, false, world.instanceId);
			world.thirdgroup.add(spawnedMob);
		}
	}

	protected void SpawnFourthGroup(ToTheMonasteryWorld world)
	{
		world.fourthgroup = new FastList<>();
		for(int[] spawn : minions_4)
		{
			L2Npc spawnedMob = (L2Npc) addSpawn(27404, spawn[0], spawn[1], spawn[2], spawn[3], false, 0, false, world.instanceId);
			world.fourthgroup.add(spawnedMob);
		}
	}

	private class Teleport implements Runnable
	{
		private final L2Npc _npc;
		private final L2PcInstance _player;
		private final int _instanceId;
		private final int[] _cords;

		public Teleport(L2Npc npc, L2PcInstance player, int[] cords, int id)
		{
			_npc = npc;
			_player = player;
			_cords = cords;
			_instanceId = id;
		}

		@Override
		public void run()
		{
			try
			{
				teleportPlayer(_npc, _player, _cords, _instanceId);
				startQuestTimer("check_follow", 3000, _npc, _player);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args)
	{
		new ToTheMonastery(-1, qn, "instances");
	}
}