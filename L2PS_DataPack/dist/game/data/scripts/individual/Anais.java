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
package individual;

import npc.AbstractNpcAI;
import com.l2jserver.gameserver.GeoData;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.network.serverpackets.AbstractNpcInfo;
import com.l2jserver.util.Rnd;

import javolution.util.FastList;
import javolution.util.FastMap;

public class Anais extends AbstractNpcAI
{
	private static final int ANAIS = 25701;
	private static final int GUARD = 25702;
	private static boolean DEBUG = false;

	private static boolean FIGHTHING = false;
	private FastList<L2Npc> burners = new FastList<>();
	private FastList<L2Npc> guards = new FastList<>();
	private FastMap<L2Npc, L2PcInstance> targets = new FastMap<>();

	private static int BURNERS_ENABLED = 0;

	private static final int[][] BURNERS = 
	{ 
		{ 113632, -75616, 50 }, 
		{ 111904, -75616, 58 }, 
		{ 111904, -77424, 51 }, 
		{ 113696, -77393, 48 } 
	};

	L2Skill guard_skill = SkillTable.getInstance().getInfo(6326, 1);

	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("check_status"))
		{
			if (FIGHTHING)
			{
				if ((npc.getAI().getIntention() == CtrlIntention.AI_INTENTION_ACTIVE) || (npc.getAI().getIntention() == CtrlIntention.AI_INTENTION_IDLE))
				{
					stopFight();
				}
				else
					startQuestTimer("check_status", 50000, npc, null);
			}
		}
		else if (event.equalsIgnoreCase("burner_action"))
		{
			if ((FIGHTHING) && (npc != null))
			{
				if (DEBUG) 
				{
					_log.info("Spawning guard");
				}
				L2Npc guard = addSpawn(GUARD, npc);
				if (guard != null)
				{
					guards.add(guard);
					startQuestTimer("guard_action", 500, guard, null);
				}
				startQuestTimer("burner_action", 20000, npc, null);
			}
		}
		else if (event.equalsIgnoreCase("guard_action"))
		{
			if ((FIGHTHING) && (npc != null) && (!npc.isDead()))
			{
				if (DEBUG) 
				{
					_log.info("Guard action");
				}
				if (targets.containsKey(npc))
				{
					L2PcInstance target = targets.get(npc);
					if ((target != null) && (target.isOnline()) && (target.isInsideRadius(npc, 5000, false, false)))
					{
						npc.setIsRunning(true);
						npc.setTarget(target);

						if (target.isInsideRadius(npc, 200, false, false))
								npc.doCast(guard_skill);
						else 
						{
							npc.getAI().setIntention(CtrlIntention.AI_INTENTION_FOLLOW, target);
						}
					}
					else
					{
						npc.deleteMe();
						if (targets.containsKey(npc))
							targets.remove(npc);
					}
				}
				else
				{
					FastList<L2PcInstance> result = FastList.newInstance();
					L2PcInstance target = null;
					for (L2PcInstance pl : npc.getKnownList().getKnownPlayersInRadius(3000))
					{
						if ((pl == null) || (pl.isAlikeDead()))
							continue;
						if ((pl.isGM()) || (pl.getAppearance().getInvisible()))
							continue;
						if ((pl.isInsideRadius(npc, 3000, true, false)) && (GeoData.getInstance().canSeeTarget(npc, pl)))
							result.add(pl);
					}
					if (!result.isEmpty())
						target = result.get(Rnd.get(result.size() - 1));
					if (target != null)
					{
						npc.setTitle(target.getName());
						npc.broadcastPacket(new AbstractNpcInfo.NpcInfo(npc, target));
						npc.setIsRunning(true);
						targets.put(npc, target);
					}
					FastList.recycle(result);
				}
				startQuestTimer("guard_action", 1000, npc, null);
			}
		}
    return super.onAdvEvent(event, npc, player);
  }

	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isPet)
	{
		if (npc.getNpcId() == ANAIS)
		{
			if (!FIGHTHING)
			{
				FIGHTHING = true;

				startQuestTimer("check_status", 50000, npc, null);

				if (DEBUG) 
				{
					_log.info("Switching state to FIGHTHING");
				}

			}
			else if ((Rnd.get(10) == 0) && (BURNERS_ENABLED < 4)) 
			{
				checkBurnerStatus(npc);
			}
		}
		return super.onAttack(npc, attacker, damage, isPet);
	}

	@Override
	public String onSpellFinished(L2Npc npc, L2PcInstance player, L2Skill skill)
	{
		if (DEBUG) 
		{
			_log.info("Spell Finished for " + npc.getName());
		}
		if (npc.getNpcId() == GUARD)
		{
			if (guards.contains(npc)) 
			{
				guards.remove(npc);
			}
			npc.doDie(npc);
			npc.deleteMe();
		}
		return super.onSpellFinished(npc, player, skill);
	}

	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		if (npc.getNpcId() == ANAIS)
		{
			stopFight();
		}
		return super.onKill(npc, killer, isPet);
	}

	private synchronized void checkBurnerStatus(L2Npc anais)
	{
		if (DEBUG) 
		{
			_log.info("Burner status check for " + BURNERS_ENABLED);
		}
		switch (BURNERS_ENABLED)
		{
			case 0:
				enableBurner(1);
				BURNERS_ENABLED = 1;
			break;
			case 1:
				if (anais.getCurrentHp() > anais.getMaxHp() * 0.75)
				{
					enableBurner(2);
					BURNERS_ENABLED = 2;
				}
			break;
			case 2:
				if (anais.getCurrentHp() > anais.getMaxHp() * 0.5)
				{
					enableBurner(3);
					BURNERS_ENABLED = 3;
				}
			break;
			case 3:
				if (anais.getCurrentHp() > anais.getMaxHp() * 0.25)
				{
					enableBurner(4);
					BURNERS_ENABLED = 4;
				}
			break;
		}
	}

	private void enableBurner(int index)
	{
		if (!burners.isEmpty())
		{
			L2Npc burner = burners.get(index - 1);
			if (burner != null)
			{
				burner.setDisplayEffect(1);
				startQuestTimer("burner_action", 1000, burner, null);
			}
		}
	}

	private void spawnBurners()
	{
		for (int[] SPAWN : BURNERS)
		{
			L2Npc npc = addSpawn(18915, SPAWN[0], SPAWN[1], SPAWN[2], 0, false, 0);
			if (npc == null)
				continue;
			burners.add(npc);
		}
	}

	private void stopFight()
	{
		if (DEBUG) 
		{
			_log.info("Switching state to IDLE");
		}
		FIGHTHING = false;
		BURNERS_ENABLED = 0;

		cancelQuestTimers("guard_action");
		cancelQuestTimers("burner_action");

		if (!targets.isEmpty()) 
		{
			targets.clear();
		}
		if (!burners.isEmpty())
		{
			for (L2Npc burner : burners)
			{
				if (burner != null) 
				{
					burner.setDisplayEffect(2);
				}
			}
		}
		if (!guards.isEmpty())
		{
			for (L2Npc guard : guards)
			{
				if (guard != null)
					guard.deleteMe();
			}
		}
	}

	public Anais(int questId, String name, String descr)
	{
		super(name, descr);

		registerMobs(new int[] { ANAIS, GUARD });
		spawnBurners();
	}

	public static void main(String[] args)
	{
		new Anais(-1, "Anais", "individual");
	}	
}