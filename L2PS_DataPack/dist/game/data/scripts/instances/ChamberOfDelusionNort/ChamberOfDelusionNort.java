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
package instances.ChamberOfDelusionNort;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.util.Util;
import com.l2jserver.util.Rnd;

/**
 * Author: RobikBobik
 */
public class ChamberOfDelusionNort extends Quest
{
	private class CDWorld extends InstanceWorld
	{
		protected L2Npc manager, managera, managerb, managerc, managerd, managere, managerf, managerg, managerh, chesta, chestb, chestc, chestd, _aenkinel;
		
		public CDWorld()
		{
			super();
		}
	}
	
	private static final int INSTANCEID = 130;
	private static boolean RB_Attacked = false;
	private static final int GKSTART = 32661;
	private static final int GKFINISH = 32667;
	private static final int AENKINEL = 25693;
	private static final int PRIZ = 65520;
	private static final int FAIL1 = 65519;
	private static final int FAIL2 = 65519;
	private static final int FAIL3 = 65519;
	private static final int ROOMRB = 4;
	private int rb = 0;
	private int a;
	public int instId = 0;
	private int b;
	private int g = 0;
	private int h = 0;
	private int c;
	
	protected class teleCoord
	{
		int instanceId;
		int x;
		int y;
		int z;
	}
	
	private static final int[][] TELEPORT =
	{
		{
			-122368,
			-152624,
			-6752
		},
		{
			-122368,
			-153504,
			-6752
		},
		{
			-120496,
			-154304,
			-6752
		},
		{
			-120496,
			-155184,
			-6752
		},
		{
			-121440,
			-154688,
			-6752
		},
		{
			-121440,
			-151328,
			-6752
		},
		{
			-120496,
			-153008,
			-6752
		},
		{
			-122368,
			-154800,
			-6752
		},
		{
			-121440,
			-153008,
			-6752
		}
	};
	
	private static final int[] CHESTSPAWN_X =
	{
		-121524,
		-121486,
		-121457,
		-121428
	};
	
	private int spawn2, spawn3, spawn4;
	
	public ChamberOfDelusionNort(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(GKSTART);
		addTalkId(GKSTART);
		addStartNpc(GKFINISH);
		addFirstTalkId(GKFINISH);
		addTalkId(GKFINISH);
		
		addKillId(AENKINEL);
		
		addAttackId(PRIZ);
		addAttackId(FAIL1);
		addAttackId(FAIL2);
		addAttackId(FAIL3);
	}
	
	private boolean checkConditions(L2PcInstance player)
	{
		L2Party party = player.getParty();
		
		if (party == null)
		{
			player.sendPacket(SystemMessage.getSystemMessage(2101));
			return false;
		}
		if (party.getLeader() != player)
		{
			player.sendPacket(SystemMessage.getSystemMessage(2185));
			return false;
		}
		for (L2PcInstance partyMember : party.getMembers())
		{
			if (partyMember.getLevel() < 80)
			{
				final SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_LEVEL_REQUIREMENT_NOT_SUFFICIENT);
				sm.addPcName(partyMember);
				party.broadcastPacket(sm);
				return false;
			}
			if (!Util.checkIfInRange(1000, player, partyMember, true))
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_IS_IN_LOCATION_THAT_CANNOT_BE_ENTERED);
				sm.addPcName(partyMember);
				party.broadcastPacket(sm);
				return false;
			}
		}
		return true;
		
	}
	
	private void teleportplayer(L2PcInstance player, teleCoord teleto)
	{
		player.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		player.setInstanceId(teleto.instanceId);
		player.teleToLocation(teleto.x, teleto.y, teleto.z);
		return;
	}
	
	private void teleportrnd(L2PcInstance player, CDWorld world)
	{
		int tp = Rnd.get(TELEPORT.length);
		if ((rb == 1) && (tp == ROOMRB))
		{
			tp = Rnd.get(TELEPORT.length);
			for (int i = 0; i < TELEPORT.length; i++)
			{
				if (i == tp)
				{
					for (L2PcInstance partyMember : player.getParty().getMembers())
					{
						partyMember.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
						partyMember.setInstanceId(instId);
						partyMember.teleToLocation(TELEPORT[i][0], TELEPORT[i][1], TELEPORT[i][2]);
					}
				}
			}
			a = player.getX();
			b = player.getY();
			c = player.getZ();
		}
		else
		{
			for (int i = 0; i < TELEPORT.length; i++)
			{
				if (i == tp)
				{
					for (L2PcInstance partyMember : player.getParty().getMembers())
					{
						partyMember.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
						partyMember.setInstanceId(instId);
						partyMember.teleToLocation(TELEPORT[i][0], TELEPORT[i][1], TELEPORT[i][2]);
					}
				}
			}
			a = player.getX();
			b = player.getY();
			c = player.getZ();
		}
		return;
	}
	
	protected void spawnState(CDWorld world)
	{
		world._aenkinel = addSpawn(AENKINEL, -121463, -155094, -6752, 0, false, 0, false, world.getInstanceId());
		world._aenkinel.setIsNoRndWalk(false);
		world.manager = addSpawn(32667, -121440, -154688, -6752, 0, false, 0, false, world.getInstanceId());
		world.manager.setIsNoRndWalk(true);
		world.managerb = addSpawn(32667, -122368, -153504, -6752, 0, false, 0, false, world.getInstanceId());
		world.managerb.setIsNoRndWalk(true);
		world.managerc = addSpawn(32667, -120496, -154304, -6752, 0, false, 0, false, world.getInstanceId());
		world.managerc.setIsNoRndWalk(true);
		world.managerd = addSpawn(32667, -120496, -155184, -6752, 0, false, 0, false, world.getInstanceId());
		world.managerd.setIsNoRndWalk(true);
		world.managere = addSpawn(32667, -121440, -151328, -6752, 0, false, 0, false, world.getInstanceId());
		world.managere.setIsNoRndWalk(true);
		world.managerf = addSpawn(32667, -120496, -153008, -6752, 0, false, 0, false, world.getInstanceId());
		world.managerf.setIsNoRndWalk(true);
		world.managerg = addSpawn(32667, -122368, -154800, -6752, 0, false, 0, false, world.getInstanceId());
		world.managerg.setIsNoRndWalk(true);
		world.managerh = addSpawn(32667, -121440, -153008, -6752, 0, false, 0, false, world.getInstanceId());
		world.managerh.setIsNoRndWalk(true);
		world.managera = addSpawn(32667, -122368, -152624, -6752, 0, false, 0, false, world.getInstanceId());
		world.managera.setIsNoRndWalk(true);
	}
	
	protected int enterInstance(L2PcInstance player, String template)
	{
		int instanceId = 0;
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		
		if (world != null)
		{
			if (!(world instanceof CDWorld))
			{
				player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER));
				return 0;
			}
			teleCoord tele = new teleCoord();
			tele.x = a;
			tele.y = b;
			tele.z = c;
			tele.instanceId = world.getInstanceId();
			teleportplayer(player, tele);
		}
		else
		{
			if (!checkConditions(player))
			{
				return 0;
			}
			L2Party party = player.getParty();
			instanceId = InstanceManager.getInstance().createDynamicInstance(template);
			world = new CDWorld();
			world.setInstanceId(instanceId);
			world.setTemplateId(INSTANCEID);
			world.setStatus(0);
			InstanceManager.getInstance().addWorld(world);
			spawnState((CDWorld) world);
			instId = world.getInstanceId();
			
			for (L2PcInstance partyMember : party.getMembers())
			{
				teleportrnd(partyMember, (CDWorld) world);
				world.addAllowed(partyMember.getObjectId());
			}
			startQuestTimer("tproom", 480000, null, player);
			RB_Attacked = false;
		}
		return instanceId;
	}
	
	protected void exitInstance(L2PcInstance player, teleCoord tele)
	{
		player.setInstanceId(0);
		player.teleToLocation(tele.x, tele.y, tele.z);
		L2Summon pet = player.getSummon();
		if (pet != null)
		{
			pet.setInstanceId(0);
			pet.teleToLocation(tele.x, tele.y, tele.z);
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		InstanceWorld tmpworld = InstanceManager.getInstance().getPlayerWorld(player);
		if (tmpworld instanceof CDWorld)
		{
			CDWorld world = (CDWorld) tmpworld;
			L2Party party = player.getParty();
			instId = player.getInstanceId();
			if (event.equalsIgnoreCase("tproom"))
			{
				
				for (L2PcInstance partyMember : party.getMembers())
				{
					teleportrnd(partyMember, world);
				}
				
				startQuestTimer("tproom1", 480000, null, player);
				h++;
			}
			else if (event.equalsIgnoreCase("tproom1"))
			{
				
				for (L2PcInstance partyMember : party.getMembers())
				{
					teleportrnd(partyMember, world);
				}
				
				startQuestTimer("tproom2", 480000, null, player);
				h++;
			}
			else if (event.equalsIgnoreCase("tproom2"))
			{
				
				for (L2PcInstance partyMember : party.getMembers())
				{
					teleportrnd(partyMember, world);
				}
				
				startQuestTimer("tproom3", 480000, null, player);
				h++;
			}
			else if (event.equalsIgnoreCase("tproom3"))
			{
				
				for (L2PcInstance partyMember : party.getMembers())
				{
					teleportrnd(partyMember, world);
				}
				
			}
			else if ("7".equalsIgnoreCase(event))
			{
				if (g == 0)
				{
					if (h == 0)
					{
						cancelQuestTimers("tproom");
						for (L2PcInstance partyMember : party.getMembers())
						{
							teleportrnd(partyMember, world);
						}
						startQuestTimer("tproom1", 480000, null, player);
						g = 1;
					}
					else if (h == 1)
					{
						cancelQuestTimers("tproom1");
						for (L2PcInstance partyMember : party.getMembers())
						{
							teleportrnd(partyMember, world);
						}
						startQuestTimer("tproom2", 480000, null, player);
						g = 1;
					}
					else if (h == 2)
					{
						cancelQuestTimers("tproom2");
						for (L2PcInstance partyMember : party.getMembers())
						{
							teleportrnd(partyMember, world);
						}
						startQuestTimer("tproom3", 480000, null, player);
						g = 1;
					}
					else if (h == 3)
					{
						cancelQuestTimers("tproom3");
						for (L2PcInstance partyMember : party.getMembers())
						{
							teleportrnd(partyMember, world);
						}
						g = 1;
					}
				}
			}
			
		}
		return "";
	}
	
	@Override
	public String onAttack(final L2Npc npc, final L2PcInstance attacker, final int damage, final boolean isSummon, final L2Skill skill)
	{
		InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		if (tmpworld instanceof CDWorld)
		{
			CDWorld world = (CDWorld) tmpworld;
			if ((npc.getNpcId() == FAIL1) || (npc.getNpcId() == FAIL2) || (npc.getNpcId() == FAIL3))
			{
				world.chesta.deleteMe();
				world.chestb.deleteMe();
				world.chestc.deleteMe();
				world.chestd.deleteMe();
			}
			else if (npc.getNpcId() == PRIZ)
			{
				world.chestb.deleteMe();
				world.chestc.deleteMe();
				world.chestd.deleteMe();
			}
			else if ((npc.getNpcId() == AENKINEL) && !RB_Attacked)
			{
				RB_Attacked = true;
				cancelQuestTimers("tproom");
				cancelQuestTimers("tproom1");
				cancelQuestTimers("tproom2");
				cancelQuestTimers("tproom3");
			}
		}
		return null;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		int npcId = npc.getNpcId();
		if ((rb == 0) && (npcId == AENKINEL))
		{
			rb = 1;
			InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
			if (tmpworld instanceof CDWorld)
			{
				CDWorld world = (CDWorld) tmpworld;
				
				int sr_x = Rnd.get(3);
				int spawn_x = CHESTSPAWN_X[sr_x];
				switch (sr_x)
				{
					case 0:
						spawn2 = CHESTSPAWN_X[1];
						spawn3 = CHESTSPAWN_X[2];
						spawn4 = CHESTSPAWN_X[3];
						break;
					case 1:
						spawn2 = CHESTSPAWN_X[0];
						spawn3 = CHESTSPAWN_X[2];
						spawn4 = CHESTSPAWN_X[3];
						break;
					case 2:
						spawn2 = CHESTSPAWN_X[0];
						spawn3 = CHESTSPAWN_X[1];
						spawn4 = CHESTSPAWN_X[3];
						break;
					case 3:
						spawn2 = CHESTSPAWN_X[0];
						spawn3 = CHESTSPAWN_X[1];
						spawn4 = CHESTSPAWN_X[2];
						break;
				}
				
				world.chesta = addSpawn(PRIZ, spawn_x, -155073, -6752, 0, false, 0, false, world.getInstanceId());
				world.chesta.setIsNoRndWalk(true);
				world.chestb = addSpawn(FAIL1, spawn2, -155070, -6752, 0, false, 0, false, world.getInstanceId());
				world.chestb.setIsNoRndWalk(true);
				world.chestc = addSpawn(FAIL2, spawn3, -155071, -6752, 0, false, 0, false, world.getInstanceId());
				world.chestc.setIsNoRndWalk(true);
				world.chestd = addSpawn(FAIL3, spawn4, -155070, -6752, 0, false, 0, false, world.getInstanceId());
				world.chestd.setIsNoRndWalk(true);
				cancelQuestTimers("tproom");
				cancelQuestTimers("tproom1");
				cancelQuestTimers("tproom2");
				cancelQuestTimers("tproom3");
				
			}
		}
		return "";
	}
	
	@Override
	public final String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		return npc.getNpcId() + ".htm";
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		int npcId = npc.getNpcId();
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			st = newQuestState(player);
		}
		if (npcId == GKSTART)
		{
			enterInstance(player, "ChamberofDelusionNort.xml");
			return "";
		}
		else if (npcId == GKFINISH)
		{
			InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
			teleCoord tele = new teleCoord();
			tele.instanceId = 0;
			tele.x = -114592;
			tele.y = -152509;
			tele.z = -6723;
			cancelQuestTimers("tproom");
			cancelQuestTimers("tproom1");
			cancelQuestTimers("tproom2");
			cancelQuestTimers("tproom3");
			for (int objectId : world.getAllowed())
			{
				L2PcInstance plyr = L2World.getInstance().getPlayer(objectId);
				if ((plyr != null) && plyr.isOnline())
				{
					exitInstance(plyr, tele);
					world.removeAllowed(world.getAllowed().indexOf(plyr.getObjectId()));
				}
			}
		}
		return "";
	}
	
	public static void main(String[] args)
	{
		new ChamberOfDelusionNort(-1, ChamberOfDelusionNort.class.getSimpleName(), "instances");
	}
}