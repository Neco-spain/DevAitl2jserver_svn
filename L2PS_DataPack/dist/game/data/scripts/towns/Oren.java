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
package towns;


import javolution.util.FastList;

import com.l2jserver.Config;
import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.util.Rnd;

public class Oren extends Quest
{
	private static final long delay = 120000 - System.currentTimeMillis();
	protected static boolean bossesSpawned = false;
	
	private final FastList<L2Npc> deadnpcs = new FastList<>();
	private final FastList<L2Npc> alivenpcs = new FastList<>();
	private final FastList<L2Npc> bosses = new FastList<>();
	
	protected boolean progress1 = false;
	protected boolean progress2 = false;
	
	private static final int[][] bossGroups =
	{
		{
			25767,
			80328,
			46792,
			-3189,
			36123
		},
		{
			25770,
			80520,
			47368,
			-3193,
			36736
		}
	};
	
	private static final int[][] BLOODALTARS_DEAD_NPC =
	{
		{
			4328,
			80328,
			47192,
			-3176,
			53375
		},
		{
			4328,
			80040,
			47176,
			-3176,
			16000
		},
		{
			4327,
			80184,
			47272,
			-3178,
			6000
		}
	};
	
	private static final int[][] BLOODALTARS_ALIVE_NPC =
	{
		{
			4325,
			80328,
			47192,
			-3176,
			53375
		},
		{
			4325,
			80040,
			47176,
			-3176,
			16000
		},
		{
			4324,
			80184,
			47272,
			-3178,
			6000
		}
	};
	
	public Oren(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		manageNpcs(true);
		
		addKillId(25767);
		addKillId(25770);
		
		ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
		{
			@Override
			public void run()
			{
				changestatus();
				_log.info("STATUS CHANGED");
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
					_log.info("SPAWNED ALIVE");
				}
			}
			
			if (!deadnpcs.isEmpty())
			{
				for (L2Npc npc : deadnpcs)
				{
					if (npc != null)
					{
						npc.deleteMe();
						_log.info("SPAWNED DEAD - deleting");
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
					_log.info("SPAWNED DEAD");
				}
			}
			
			if (!alivenpcs.isEmpty())
			{
				for (L2Npc npc : alivenpcs)
				{
					if (npc != null)
					{
						npc.deleteMe();
						_log.info("SPAWNED ALIVE - deleting");
					}
				}
			}
			alivenpcs.clear();
		}
	}
	
	protected void manageBosses(boolean spawn)
	{
		if (spawn)
		{
			for (int[] bossspawn : bossGroups)
			{
				L2Npc boss = addSpawn(bossspawn[0], bossspawn[1], bossspawn[2], bossspawn[3], bossspawn[4], false, 0, false);
				if (boss != null)
				{
					bosses.add(boss);
					_log.info("SPAWNED BOSS");
				}
			}
		}
		else
		{
			if (!bosses.isEmpty())
			{
				for (L2Npc boss : bosses)
				{
					if (boss != null)
					{
						boss.deleteMe();
						_log.info("SPAWNED BOSS - deleting");
					}
				}
			}
		}
	}
	
	protected void changestatus()
	{
		ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
		{
			@Override
			public void run()
			{
				if (Rnd.chance(100))
				{
					if (!bossesSpawned)
					{
						manageNpcs(false);
						manageBosses(true);
						bossesSpawned = true;
					}
					else
					{
						manageBosses(false);
						manageNpcs(true);
						bossesSpawned = false;
						ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
						{
							@Override
							public void run()
							{
								changestatus();
							}
						}, 60000 - System.currentTimeMillis());
						_log.info("changestatus - chaning");
					}
				}
			}
		}, 10000);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		final int npcId = npc.getNpcId();
		
		if (npcId == 25767)
		{
			progress1 = true;
		}
		
		if (npcId == 25770)
		{
			progress2 = true;
		}
		
		if (progress1 && progress2)
		{
			ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
			{
				@Override
				public void run()
				{
					progress1 = false;
					progress2 = false;
					
					manageBosses(false);
					manageNpcs(true);
					bossesSpawned = false;
					ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
					{
						@Override
						public void run()
						{
							changestatus();
						}
					}, Config.WELCOME_MESSAGE_TIME);
				}
			}, 30000);
		}
		return super.onKill(npc, player, isPet);
	}
	
	public static void main(String[] args)
	{
		new Oren(-1, Oren.class.getSimpleName(), "towns");
		_log.info("OREN LOADED");
	}
}