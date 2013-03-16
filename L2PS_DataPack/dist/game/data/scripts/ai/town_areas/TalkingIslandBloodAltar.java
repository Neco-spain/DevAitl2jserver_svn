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
package ai.town_areas;

import javolution.util.FastList;

import com.l2jserver.Config;
import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.util.Rnd;

/**
 * Author: RobikBobik
 */
public class TalkingIslandBloodAltar extends Quest
{
	private static final long delay = Config.CHANGE_STATUS * 60 * 1000;
	
	private final FastList<L2Npc> deadnpcs = new FastList<>();
	private final FastList<L2Npc> alivenpcs = new FastList<>();
	
	private static final int[][] BLOODALTARS_DEAD_NPC =
	{
		{
			4328,
			-92376,
			244648,
			-3572,
			0
		},
		{
			4327,
			-92552,
			244600,
			-3571,
			17559
		},
		{
			4328,
			-92648,
			244728,
			-3571,
			32768
		}
	};
	
	private static final int[][] BLOODALTARS_ALIVE_NPC =
	{
		{
			4325,
			-92376,
			244648,
			-3572,
			0
		},
		{
			4324,
			-92552,
			244600,
			-3571,
			17559
		},
		{
			4325,
			-92648,
			244728,
			-3571,
			32768
		}
	};
	
	public TalkingIslandBloodAltar(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		manageNpcs(true);
		
		ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
		{
			@Override
			public void run()
			{
				changestatus();
			}
		}, delay);
	}
	
	protected void manageNpcs(boolean spawnAlive)
	{
		if (spawnAlive)
		{
			for (int[] spawn : BLOODALTARS_ALIVE_NPC)
			{
				L2Npc npc = addSpawn(spawn[0], spawn[1], spawn[2], spawn[3], spawn[4], false, 0, false);
				if (npc != null)
				{
					alivenpcs.add(npc);
				}
			}
			
			if (!deadnpcs.isEmpty())
			{
				for (L2Npc npc : deadnpcs)
				{
					if (npc != null)
					{
						npc.deleteMe();
					}
				}
			}
			deadnpcs.clear();
		}
		else
		{
			for (int[] spawn : BLOODALTARS_DEAD_NPC)
			{
				L2Npc npc = addSpawn(spawn[0], spawn[1], spawn[2], spawn[3], spawn[4], false, 0, false);
				if (npc != null)
				{
					deadnpcs.add(npc);
				}
			}
			
			if (!alivenpcs.isEmpty())
			{
				for (L2Npc npc : alivenpcs)
				{
					if (npc != null)
					{
						npc.deleteMe();
					}
				}
			}
			alivenpcs.clear();
		}
	}
	
	protected void changestatus()
	{
		ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
		{
			@Override
			public void run()
			{
				if (Rnd.chance(Config.CHANCE_SPAWN))
				{
					manageNpcs(false);
					ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
					{
						@Override
						public void run()
						{
							changestatus();
						}
					}, Config.RESPAWN_TIME * 60 * 1000);
				}
				else
				{
					manageNpcs(true);
					ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
					{
						@Override
						public void run()
						{
							changestatus();
						}
					}, Config.RESPAWN_TIME * 60 * 1000);
				}
			}
		}, 10000);
	}
	
	public static void main(String[] args)
	{
		new TalkingIslandBloodAltar(-1, TalkingIslandBloodAltar.class.getSimpleName(), "ai");
	}
}