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

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

public class _1_DragonGuard extends AbstractNpcAI
{
	private static final int DRAGON_GUARD = 22852;
	private static final int DRAGON_MAGE = 22853;
	private static final int[] WALL_MONSTERS =
	{
		DRAGON_GUARD,
		DRAGON_MAGE
	};
	
	public _1_DragonGuard(int questId, String name, String descr)
	{
		super(name, descr);
		
		for (int mobId : WALL_MONSTERS)
		{
			addSpawnId(mobId);
			addAggroRangeEnterId(mobId);
			addAttackId(mobId);
		}
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		if ((npc instanceof L2MonsterInstance))
		{
			for (int mobId : WALL_MONSTERS)
			{
				if (mobId != npc.getNpcId())
				{
					continue;
				}
				L2MonsterInstance monster = (L2MonsterInstance) npc;
				monster.setIsImmobilized(true);
				break;
			}
		}
		
		return super.onSpawn(npc);
	}
	
	@Override
	public String onAggroRangeEnter(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		if ((!npc.isCastingNow()) && (!npc.isAttackingNow()) && (!npc.isInCombat()) && (!player.isDead()))
		{
			npc.setIsImmobilized(false);
			npc.setRunning();
			((L2Attackable) npc).addDamageHate(player, 0, 999);
			((L2Attackable) npc).getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
		}
		return super.onAggroRangeEnter(npc, player, isPet);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance player, int damage, boolean isPet)
	{
		if ((npc instanceof L2MonsterInstance))
		{
			for (int mobId : WALL_MONSTERS)
			{
				if (mobId != npc.getNpcId())
				{
					continue;
				}
				L2MonsterInstance monster = (L2MonsterInstance) npc;
				monster.setIsImmobilized(false);
				monster.setRunning();
				break;
			}
		}
		
		return super.onAttack(npc, player, damage, isPet);
	}
	
	public static void main(String[] args)
	{
		new _1_DragonGuard(-1, "DragonGuard", "data");
	}
}
