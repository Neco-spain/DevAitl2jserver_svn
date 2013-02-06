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
package com.l2jserver.gameserver.features.data;

import javolution.util.FastList;
import javolution.util.FastMap;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.instancemanager.InstanceManager.InstanceWorld;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.util.Util;

public class Q10292_SevenSignGirlofDoubt extends Quest
{
	private class HoDWorld extends InstanceWorld
	{
		public long[] storeTime =
		{
			0,
			0
		};
		
		public HoDWorld()
		{
		}
	}
	
	private static final int INSTANCEID = 113;
	
	// NPC
	private static final int Hardin = 30832;
	private static final int Wood = 32593;
	private static final int Franz = 32597;
	private static final int Elcadia = 32784;
	private static final int Gruff_looking_Man = 32862;
	private static final int Jeina = 32617;
	
	// Item
	private static final int Elcadias_Mark = 17226;
	
	// Mobs
	private static final int[] Mobs =
	{
		22801,
		22802,
		22804,
		22805
	};
	
	private final FastMap<Integer, InstanceHolder> instanceWorlds = new FastMap<>();
	
	protected static class InstanceHolder
	{
		FastList<L2Npc> mobs = new FastList<>();
		boolean spawned = false;
	}
	
	protected class teleCoord
	{
		int instanceId;
		int x;
		int y;
		int z;
	}
	
	public Q10292_SevenSignGirlofDoubt(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(Wood);
		addTalkId(Wood);
		addTalkId(Franz);
		addTalkId(Hardin);
		addTalkId(Elcadia);
		addTalkId(Gruff_looking_Man);
		addTalkId(Jeina);
		
		addKillId(27422);
		for (int _npc : Mobs)
		{
			addKillId(_npc);
		}
		
		questItemIds = new int[]
		{
			Elcadias_Mark
		};
	}
	
	private static final void removeBuffs(L2Character ch)
	{
		for (L2Effect e : ch.getAllEffects())
		{
			if (e == null)
			{
				continue;
			}
			L2Skill skill = e.getSkill();
			if (skill.isDebuff() || skill.isStayAfterDeath())
			{
				continue;
			}
			e.exit();
		}
	}
	
	private void teleportplayer(L2PcInstance player, teleCoord teleto)
	{
		removeBuffs(player);
		if (player.getSummon() != null)
		{
			removeBuffs(player.getSummon());
		}
		player.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		player.setInstanceId(teleto.instanceId);
		player.teleToLocation(teleto.x, teleto.y, teleto.z);
		return;
	}
	
	protected void exitInstance(L2PcInstance player, teleCoord tele)
	{
		player.setInstanceId(0);
		player.teleToLocation(tele.x, tele.y, tele.z);
	}
	
	protected int enterInstance(L2PcInstance player, String template, teleCoord teleto)
	{
		int instanceId = 0;
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		if (world != null)
		{
			if (!(world instanceof HoDWorld))
			{
				player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER));
				return 0;
			}
			teleto.instanceId = world.instanceId;
			teleportplayer(player, teleto);
			return instanceId;
		}
		else
		{
			instanceId = InstanceManager.getInstance().createDynamicInstance(template);
			world = new HoDWorld();
			world.instanceId = instanceId;
			world.templateId = INSTANCEID;
			world.status = 0;
			((HoDWorld) world).storeTime[0] = System.currentTimeMillis();
			InstanceManager.getInstance().addWorld(world);
			teleto.instanceId = instanceId;
			teleportplayer(player, teleto);
			world.allowed.add(player.getObjectId());
			
			return instanceId;
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(getName());
		
		int instanceId = npc.getInstanceId();
		InstanceHolder holder = instanceWorlds.get(instanceId);
		if ((holder == null) && (instanceId > 0))
		{
			holder = new InstanceHolder();
			instanceWorlds.put(instanceId, holder);
		}
		
		if (st == null)
		{
			return htmltext;
		}
		if (event.equalsIgnoreCase("evil_despawn"))
		{
			holder.spawned = false;
			for (L2Npc h : holder.mobs)
			{
				if (h != null)
				{
					h.deleteMe();
				}
			}
			holder.mobs.clear();
			instanceWorlds.remove(instanceId);
			return null;
		}
		else if (npc.getNpcId() == Wood)
		{
			if (event.equalsIgnoreCase("32593-05.htm"))
			{
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
			}
			else if (event.equalsIgnoreCase("32593-05a.htm"))
			{
				teleCoord tele = new teleCoord();
				tele.x = -23769;
				tele.y = -8961;
				tele.z = -5392;
				enterInstance(player, "HideoutoftheDawn.xml", tele);
			}
			
		}
		else if (npc.getNpcId() == Franz)
		{
			if (event.equalsIgnoreCase("32597-08.htm"))
			{
				st.set("cond", "2");
				st.playSound("ItemSound.quest_middle");
			}
		}
		else if (npc.getNpcId() == Hardin)
		{
			if (event.equalsIgnoreCase("30832-02.htm"))
			{
				st.set("cond", "8");
				st.playSound("ItemSound.quest_middle");
			}
		}
		else if (npc.getNpcId() == Elcadia)
		{
			if (event.equalsIgnoreCase("32784-03.htm"))
			{
				st.set("cond", "3");
				st.playSound("ItemSound.quest_middle");
			}
			else if (event.equalsIgnoreCase("32784-14.htm"))
			{
				st.set("cond", "7");
				st.playSound("ItemSound.quest_middle");
			}
			else if (event.equalsIgnoreCase("spawn"))
			{
				if (!holder.spawned)
				{
					st.takeItems(Elcadias_Mark, -1);
					holder.spawned = true;
					L2Npc evil = addSpawn(27422, 89440, -238016, -9632, 335, false, 0, false, player.getInstanceId());
					evil.setIsNoRndWalk(true);
					holder.mobs.add(evil);
					L2Npc evil1 = addSpawn(27424, 89524, -238131, -9632, 56, false, 0, false, player.getInstanceId());
					evil1.setIsNoRndWalk(true);
					holder.mobs.add(evil1);
					startQuestTimer("evil_despawn", 60000, evil, player);
					return null;
				}
				else
				{
					htmltext = "32593-02.htm";
				}
			}
		}
		else if (npc.getNpcId() == Jeina)
		{
			if (event.equalsIgnoreCase("32617-02.htm"))
			{
				InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
				world.allowed.remove(world.allowed.indexOf(player.getObjectId()));
				teleCoord tele = new teleCoord();
				tele.instanceId = 0;
				tele.x = 147072;
				tele.y = 23743;
				tele.z = -1984;
				exitInstance(player, tele);
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		
		if (st == null)
		{
			return htmltext;
		}
		else if (npc.getNpcId() == Wood)
		{
			if (st.getState() == State.COMPLETED)
			{
				htmltext = "32593-02.htm";
			}
			else if (player.getLevel() < 81)
			{
				htmltext = "32593-03.htm";
			}
			else if ((player.getQuestState("Q00198_SevenSignEmbryo") == null) || (player.getQuestState("Q00198_SevenSignEmbryo").getState() != State.COMPLETED))
			{
				htmltext = "32593-03.htm";
			}
			else if (st.getState() == State.CREATED)
			{
				htmltext = "32593-01.htm";
			}
			else if (st.getInt("cond") >= 1)
			{
				htmltext = "32593-07.htm";
			}
		}
		else if (npc.getNpcId() == Franz)
		{
			if (st.getInt("cond") == 1)
			{
				htmltext = "32597-01.htm";
			}
			else if (st.getInt("cond") == 2)
			{
				htmltext = "32597-03.htm";
			}
		}
		else if (npc.getNpcId() == Elcadia)
		{
			if (st.getInt("cond") == 2)
			{
				htmltext = "32784-01.htm";
			}
			else if (st.getInt("cond") == 3)
			{
				htmltext = "32784-04.htm";
			}
			else if (st.getInt("cond") == 4)
			{
				st.playSound("ItemSound.quest_middle");
				st.set("cond", "5");
				htmltext = "32784-05.htm";
			}
			else if (st.getInt("cond") == 5)
			{
				st.playSound("ItemSound.quest_middle");
				htmltext = "32784-05.htm";
			}
			else if (st.getInt("cond") == 6)
			{
				st.playSound("ItemSound.quest_middle");
				htmltext = "32784-11.htm";
			}
			else if (st.getInt("cond") == 8)
			{
				if (player.isSubClassActive())
				{
					htmltext = "32784-18.htm";
				}
				else
				{
					st.playSound("ItemSound.quest_finish");
					st.addExpAndSp(10000000, 1000000);
					st.exitQuest(false);
					htmltext = "32784-16.htm";
				}
			}
		}
		else if (npc.getNpcId() == Hardin)
		{
			if (st.getInt("cond") == 7)
			{
				htmltext = "30832-01.htm";
			}
			else if (st.getInt("cond") == 8)
			{
				htmltext = "30832-04.htm";
			}
		}
		else if (npc.getNpcId() == Jeina)
		{
			if (st.getState() == State.STARTED)
			{
				if (st.getInt("cond") >= 1)
				{
					htmltext = "32617-01.htm";
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState st = player.getQuestState(getName());
		
		if ((st != null) && (st.getInt("cond") == 3) && Util.contains(Mobs, npc.getNpcId()) && (st.getQuestItemsCount(Elcadias_Mark) < 10) && (st.getQuestItemsCount(Elcadias_Mark) != 9))
		{
			st.giveItems(Elcadias_Mark, 1);
			st.playSound("ItemSound.quest_middle");
		}
		else if ((st != null) && (st.getInt("cond") == 3) && Util.contains(Mobs, npc.getNpcId()) && (st.getQuestItemsCount(Elcadias_Mark) >= 9))
		{
			st.giveItems(Elcadias_Mark, 1);
			st.playSound("ItemSound.quest_middle");
			st.set("cond", "4");
		}
		else if ((st != null) && (st.getInt("cond") == 5) && (npc.getNpcId() == 27422))
		{
			int instanceid = npc.getInstanceId();
			InstanceHolder holder = instanceWorlds.get(instanceid);
			if (holder == null)
			{
				return null;
			}
			for (L2Npc h : holder.mobs)
			{
				if (h != null)
				{
					h.deleteMe();
				}
			}
			holder.spawned = false;
			holder.mobs.clear();
			instanceWorlds.remove(instanceid);
			st.set("cond", "6");
			st.playSound("ItemSound.quest_middle");
		}
		return super.onKill(npc, player, isPet);
	}
	
	public static void main(String[] args)
	{
		new Q10292_SevenSignGirlofDoubt(10292, Q10292_SevenSignGirlofDoubt.class.getSimpleName(), "Seven Sign Girl of Doubt");
	}
}