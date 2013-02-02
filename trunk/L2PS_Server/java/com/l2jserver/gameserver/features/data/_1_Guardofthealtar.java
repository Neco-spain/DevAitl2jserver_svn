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
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.util.Rnd;

public class _1_Guardofthealtar extends AbstractNpcAI
{
	private static final int GUARD_OF_THE_ALTAR = 18811;
	private static final int DARK_SHAMAN_VARANGKA = 18808;
	
	public _1_Guardofthealtar(int questId, String name, String descr)
	{
		super(name, descr);
		
		addKillId(GUARD_OF_THE_ALTAR);
		addKillId(DARK_SHAMAN_VARANGKA);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		if (npc.getNpcId() == GUARD_OF_THE_ALTAR)
		{
			if (Rnd.get(1000) < 400)
			{
				L2Attackable summon = (L2Attackable) addSpawn(DARK_SHAMAN_VARANGKA, npc.getX() + Rnd.get(10, 50), npc.getY() + Rnd.get(10, 50), npc.getZ(), 0, false, 240000, true);
				summon.setRunning();
				summon.addDamageHate(killer, 1, 99999);
				summon.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, killer);
				
				L2Attackable summon1 = (L2Attackable) addSpawn(DARK_SHAMAN_VARANGKA, npc.getX() + Rnd.get(10, 50), npc.getY() + Rnd.get(10, 50), npc.getZ(), 0, false, 240000, true);
				summon1.setRunning();
				summon1.addDamageHate(killer, 1, 99999);
				summon1.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, killer);
			}
		}
		return super.onKill(npc, killer, isPet);
	}
	
	public static void main(String[] args)
	{
		new _1_Guardofthealtar(-1, "Guard of the altar", "data");
	}
}