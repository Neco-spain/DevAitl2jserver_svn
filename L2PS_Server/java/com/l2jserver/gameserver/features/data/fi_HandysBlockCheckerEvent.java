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

import com.l2jserver.Config;
import com.l2jserver.gameserver.eventsmanager.HandysBlockCheckerManager;
import com.l2jserver.gameserver.eventsmanager.HandysBlockCheckerManager.ArenaParticipantsHolder;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ExCubeGameChangeTimeToStart;
import com.l2jserver.gameserver.network.serverpackets.ExCubeGameRequestReady;
import com.l2jserver.gameserver.network.serverpackets.ExCubeGameTeamList;

/**
 * Handys Block Checker Event AI.
 * @authors BiggBoss, Gigiikun
 */
public class fi_HandysBlockCheckerEvent extends Quest
{
	// Arena Managers
	private static final int A_MANAGER_1 = 32521;
	private static final int A_MANAGER_2 = 32522;
	private static final int A_MANAGER_3 = 32523;
	private static final int A_MANAGER_4 = 32524;
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if ((npc == null) || (player == null))
		{
			return null;
		}
		
		final int arena = npc.getNpcId() - A_MANAGER_1;
		if (eventIsFull(arena))
		{
			player.sendPacket(SystemMessageId.CANNOT_REGISTER_CAUSE_QUEUE_FULL);
			return null;
		}
		
		if (HandysBlockCheckerManager.getInstance().arenaIsBeingUsed(arena))
		{
			player.sendPacket(SystemMessageId.MATCH_BEING_PREPARED_TRY_LATER);
			return null;
		}
		
		if (HandysBlockCheckerManager.getInstance().addPlayerToArena(player, arena))
		{
			ArenaParticipantsHolder holder = HandysBlockCheckerManager.getInstance().getHolder(arena);
			
			final ExCubeGameTeamList tl = new ExCubeGameTeamList(holder.getRedPlayers(), holder.getBluePlayers(), arena);
			
			player.sendPacket(tl);
			
			int countBlue = holder.getBlueTeamSize();
			int countRed = holder.getRedTeamSize();
			int minMembers = Config.MIN_BLOCK_CHECKER_TEAM_MEMBERS;
			
			if ((countBlue >= minMembers) && (countRed >= minMembers))
			{
				holder.updateEvent();
				holder.broadCastPacketToTeam(new ExCubeGameRequestReady());
				holder.broadCastPacketToTeam(new ExCubeGameChangeTimeToStart(10));
			}
		}
		return null;
	}
	
	private boolean eventIsFull(int arena)
	{
		if (HandysBlockCheckerManager.getInstance().getHolder(arena).getAllPlayers().size() == 12)
		{
			return true;
		}
		return false;
	}
	
	public fi_HandysBlockCheckerEvent(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addFirstTalkId(A_MANAGER_1, A_MANAGER_2, A_MANAGER_3, A_MANAGER_4);
	}
	
	public static void main(String[] args)
	{
		if (!Config.ENABLE_BLOCK_CHECKER_EVENT)
		{
			_log.info("Handy's Block Checker Event is disabled");
		}
		else
		{
			new fi_HandysBlockCheckerEvent(-1, fi_HandysBlockCheckerEvent.class.getSimpleName(), "Handy's Block Checker Event");
			HandysBlockCheckerManager.getInstance().startUpParticipantsQueue();
			_log.info("Handy's Block Checker Event is enabled");
		}
	}
}