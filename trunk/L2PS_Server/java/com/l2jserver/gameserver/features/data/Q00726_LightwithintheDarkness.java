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

import javolution.text.TextBuilder;

import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.instancemanager.FortManager;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.instancemanager.InstanceManager.InstanceWorld;
import com.l2jserver.gameserver.model.L2Clan;
import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.Fort;
import com.l2jserver.gameserver.model.entity.Instance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

public class Q00726_LightwithintheDarkness extends Quest
{
	private static int KnightsEpaulette = 9912;
	
	// MOB's
	private static final int SeducedKnight = 36562;
	private static final int SeducedRanger = 36563;
	private static final int SeducedMage = 36564;
	private static final int SeducedWarrior = 36565;
	private static final int KanadisGuide1 = 25659;
	private static final int KanadisGuide2 = 25660;
	private static final int KanadisGuide3 = 25661;
	private static final int KanadisFollower1 = 25662;
	private static final int KanadisFollower2 = 25663;
	private static final int KanadisFollower3 = 25664;
	
	private class Pailakaworld extends InstanceWorld
	{
		public Pailakaworld()
		{
		}
	}
	
	public Q00726_LightwithintheDarkness(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(35666, 35698, 35735, 35767, 35804, 35835, 35867, 35904, 35936, 35974, 36011, 36043, 36081, 36118, 36149, 36181, 36219, 36257, 36294, 36326, 36364);
		addTalkId(35666, 35698, 35735, 35767, 35804, 35835, 35867, 35904, 35936, 35974, 36011, 36043, 36081, 36118, 36149, 36181, 36219, 36257, 36294, 36326, 36364);
		addKillId(KanadisGuide3);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, final L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return Quest.getNoQuestMsg(player);
		}
		
		int cond = st.getInt("cond");
		String htmltext = event;
		
		if (event.equals("dcw_q726_4.htm") && (cond == 0))
		{
			st.set("cond", "1");
			st.set("kanadis", "0");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equals("reward") && (cond == 1) && (st.getInt("done") == 1))
		{
			st.set("done", "0");
			st.giveItems(KnightsEpaulette, 152);
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(true);
			return null;
		}
		else if (event.equals("rimentrance"))
		{
			if (checkConditions(player))
			{
				enterInstance(player, 80);
				ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
				{
					@Override
					public void run()
					{
						FirstWave(player);
					}
				}, 10000);
				return null;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return Quest.getNoQuestMsg(player);
		}
		int cond = st.getInt("cond");
		QuestState qs = player.getQuestState("Q00727_HopeWithinTheDarkness");
		
		if (!check(st.getPlayer()))
		{
			st.exitQuest(true);
			return "dcw_q726_1a.htm";
		}
		if (qs != null)
		{
			st.exitQuest(true);
			return "dcw_q726_1b.htm";
		}
		else if (cond == 0)
		{
			if (st.getPlayer().getLevel() >= 70)
			{
				htmltext = "dcw_q726_1.htm";
			}
			else
			{
				htmltext = "dcw_q726_0.htm";
				st.exitQuest(true);
			}
		}
		else if (cond == 1)
		{
			if (st.getInt("done") == 1)
			{
				htmltext = "dcw_q726_6.htm";
			}
			else
			{
				htmltext = "dcw_q726_5.htm";
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		int npcId = npc.getNpcId();
		final L2Party party = player.getParty();
		if (party == null)
		{
			return null;
		}
		
		for (L2PcInstance partymember : party.getMembers())
		{
			QuestState st = partymember.getQuestState(getName());
			if (st != null)
			{
				int cond = st.getInt("cond");
				if ((cond == 1) && (npcId == KanadisGuide3))
				{
					if (!partymember.isDead() && partymember.isInsideRadius(npc, 1000, true, false))
					{
						if (st.getInt("kanadis") == 1)
						{
							st.set("done", "1");
							partymember.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.DUNGEON_EXPIRES_IN_S1_MINUTES).addNumber(5));
							st.playSound("ItemSound.quest_middle");
						}
						if (st.getInt("kanadis") == 0)
						{
							st.set("kanadis", "1");
						}
					}
				}
				
			}
			
		}
		return null;
	}
	
	private final synchronized void enterInstance(L2PcInstance player, int template_id)
	{
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		if (world != null)
		{
			if (!(world instanceof Pailakaworld))
			{
				player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER));
				return;
			}
			Instance inst = InstanceManager.getInstance().getInstance(world.instanceId);
			if (inst != null)
			{
				teleportPlayer(player, 48200, -12232, -9128, world.instanceId);
			}
			
			return;
		}
		if (!checkConditions(player))
		{
			return;
		}
		
		final int instanceId = InstanceManager.getInstance().createDynamicInstance("RimPailaka.xml");
		
		world = new Pailakaworld();
		world.instanceId = instanceId;
		world.templateId = template_id;
		world.status = 0;
		
		InstanceManager.getInstance().addWorld(world);
		final L2Party party = player.getParty();
		if ((party == null) || player.isGM())
		{
			setupPlayer((Pailakaworld) world, player, template_id);
			teleportPlayer(player, 48200, -12232, -9128, instanceId);
			return;
		}
		for (L2PcInstance partyMember : party.getMembers())
		{
			setupPlayer((Pailakaworld) world, partyMember, template_id);
			teleportPlayer(partyMember, 48200, -12232, -9128, instanceId);
		}
		_log.info("Rim Pailaka instance started: " + instanceId + " created by player: " + player.getName());
		return;
	}
	
	private static final void teleportPlayer(L2PcInstance player, int x, int y, int z, int instanceId)
	{
		player.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		player.setInstanceId(instanceId);
		player.teleToLocation(x, y, z, true);
	}
	
	private void setupPlayer(Pailakaworld world, L2PcInstance player, int template_id)
	{
		player.stopAllEffectsExceptThoseThatLastThroughDeath();
		InstanceManager.getInstance().setInstanceTime(player.getObjectId(), template_id, 14400000);
		world.allowed.add(player.getObjectId());
	}
	
	private static final boolean checkConditions(L2PcInstance player)
	{
		if (player.isGM())
		{
			return true;
		}
		
		final L2Party party = player.getParty();
		if (party == null)
		{
			player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.NOT_IN_PARTY_CANT_ENTER));
			return false;
		}
		else if (party.getLeader() != player)
		{
			player.sendPacket(SystemMessageId.ONLY_PARTY_LEADER_CAN_ENTER);
			return false;
		}
		for (L2PcInstance partymember : party.getMembers())
		{
			if (!partymember.isInsideRadius(player, 1000, true, true))
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_IS_IN_LOCATION_THAT_CANNOT_BE_ENTERED);
				sm.addPcName(partymember);
				player.sendPacket(sm);
				return false;
			}
			if (partymember.getLevel() < 70)
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_LEVEL_REQUIREMENT_NOT_SUFFICIENT);
				sm.addPcName(partymember);
				player.sendPacket(sm);
				return false;
			}
			long reentertime = InstanceManager.getInstance().getInstanceTime(partymember.getObjectId(), 80);
			if (System.currentTimeMillis() < reentertime)
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_MAY_NOT_REENTER_YET);
				sm.addPcName(partymember);
				partymember.broadcastPacket(sm);
				return false;
			}
		}
		return true;
	}
	
	private boolean check(L2PcInstance player)
	{
		L2Clan clan = player.getClan();
		if (clan != null)
		{
			Fort fort = FortManager.getInstance().getFortByOwner(clan);
			Fort fort1 = FortManager.getInstance().getFort(player.getX(), player.getY(), player.getZ());
			if ((fort1 != fort) || (fort1 == null) || (fort == null))
			{
				return false;
			}
			else if (fort.getFortState() == 0)
			{
				NpcHtmlMessage htm = new NpcHtmlMessage(5);
				TextBuilder replyMSG = new TextBuilder("<html><body>The fortress is currently in an unsettled state " + "because our relationship with the castle lord must be clearly determined.<br>(Entry into the camps " + "is only possible after gaining independence from the main castle.)</body></html>");
				htm.setHtml(replyMSG.toString());
				player.sendPacket(htm);
				return false;
			}
			
			else if (fort.getFortState() == 2)
			{
				NpcHtmlMessage htm = new NpcHtmlMessage(5);
				TextBuilder replyMSG = new TextBuilder("<html><body>Off limits! Lord's orders.<br>(You cannot enter the camp" + " with a contract related to the castle.)</body></html>");
				htm.setHtml(replyMSG.toString());
				player.sendPacket(htm);
				return false;
			}
		}
		return true;
	}
	
	public void FirstWave(final L2PcInstance player)
	{
		addSpawn(SeducedKnight, 49384, -12232, -9384, 0, false, 0, false, 80);
		addSpawn(SeducedRanger, 49192, -12232, -9384, 0, false, 0, false, 80);
		addSpawn(SeducedMage, 49192, -12456, -9392, 0, false, 0, false, 80);
		addSpawn(SeducedWarrior, 49192, -11992, -9392, 0, false, 0, false, 80);
		L2Party party = player.getParty();
		if (party != null)
		{
			for (L2PcInstance partyMember : party.getMembers())
			{
				partyMember.sendPacket(new ExShowScreenMessage("First stage begins!", 3000));
			}
		}
		addSpawn(KanadisGuide1, 50536, -12232, -9384, 32768, false, 0, false, 80);
		for (int i = 0; i < 10; i++)
		{
			addSpawn(KanadisFollower1, 50536, -12232, -9384, 32768, false, 0, false, 80);
		}
		ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
		{
			@Override
			public void run()
			{
				SecondWave(player);
			}
		}, 8 * 60 * 1000);
	}
	
	public void SecondWave(final L2PcInstance player)
	{
		L2Party party = player.getParty();
		if (party != null)
		{
			for (L2PcInstance partyMember : party.getMembers())
			{
				partyMember.sendPacket(new ExShowScreenMessage("Second stage begins!", 3000));
			}
		}
		addSpawn(KanadisGuide2, 50536, -12232, -9384, 32768, false, 0, false, 80);
		for (int i = 0; i < 10; i++)
		{
			addSpawn(KanadisFollower2, 50536, -12232, -9384, 32768, false, 0, false, 80);
		}
		ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
		{
			@Override
			public void run()
			{
				ThirdWave(player);
			}
		}, 8 * 60 * 1000);
	}
	
	public void ThirdWave(L2PcInstance player)
	{
		L2Party party = player.getParty();
		if (party != null)
		{
			for (L2PcInstance partyMember : party.getMembers())
			{
				partyMember.sendPacket(new ExShowScreenMessage("Third stage begins!", 3000));
			}
		}
		addSpawn(KanadisGuide3, 50536, -12232, -9384, 32768, false, 0, false, 80);
		addSpawn(KanadisGuide3, 50536, -12232, -9384, 32768, false, 0, false, 80);
		for (int i = 0; i < 10; i++)
		{
			addSpawn(KanadisFollower3, 50536, -12232, -9384, 32768, false, 0, false, 80);
		}
	}
	
	public static void main(String[] args)
	{
		new Q00726_LightwithintheDarkness(726, Q00726_LightwithintheDarkness.class.getSimpleName(), "Light Within The Darkness");
	}
}