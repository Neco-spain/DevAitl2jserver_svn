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

import com.l2jserver.gameserver.ai.CtrlIntention;
import npc.AbstractNpcAI;
import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.instancemanager.WalkingManager;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

public class ZealotOfShilen extends AbstractNpcAI
{
	private static final int ZEALOT = 18782;
	
	private static final int GUARD1 = 32628;
	private static final int GUARD2 = 32629;
	
	private static final int[] ROUTE_ID =
	{
		101, // gludio_airport1
		102, // gludio_airport2
		103, // gludio_airport3
		104, // gludio_airport4
	};
	
	private static final L2Npc[] _guards = new L2Npc[4];
	
	private static boolean _killOneAtStart = true; // Lets help the guards :)
	
	public ZealotOfShilen(String name, String descr)
	{
		super(name, descr);
		addSpawnId(GUARD1, GUARD2, ZEALOT);
		
		int i = 0;
		for (L2Spawn spawn : SpawnTable.getInstance().getSpawnTable())
		{
			switch (spawn.getNpcid())
			{
				case GUARD1:
				case GUARD2:
					L2Npc guard = spawn.getLastSpawn();
					guard.setIsInvul(true);
					((L2Attackable) guard).setCanReturnToSpawnPoint(false);
					startQuestTimer("watching", 10000, guard, null, true);
					WalkingManager.getInstance().startMoving(guard, ROUTE_ID[i]);
					_guards[i] = guard;
					i++;
					break;
				case ZEALOT:
					if (_killOneAtStart)
					{
						spawn.getLastSpawn().doDie(null);
						_killOneAtStart = false;
					}
					spawn.getLastSpawn().setIsNoRndWalk(true);
					break;
			}
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("watching") && !npc.isAttackingNow())
		{
			for (L2Character character : npc.getKnownList().getKnownCharacters())
			{
				if (character.isMonster() && !character.isDead() && !((L2Attackable) character).isDecayed())
				{
					npc.setRunning();
					((L2Attackable) npc).addDamageHate(character, 0, 999);
					npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, character, null);
				}
			}
		}
		return event;
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		switch (npc.getNpcId())
		{
			case GUARD1:
			case GUARD2:
				for (int i = 0; i <= 3; i++)
				{
					if (npc.equals(_guards[i]))
					{
						WalkingManager.getInstance().startMoving(npc, ROUTE_ID[i]);
					}
				}
				break;
			case ZEALOT:
				npc.setIsNoRndWalk(true);
				break;
		}
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new ZealotOfShilen(ZealotOfShilen.class.getSimpleName(), "individual");
	}
}
