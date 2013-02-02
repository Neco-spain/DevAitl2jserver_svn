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

public class _0_EtisEtina extends AbstractNpcAI
{
	private static final int ETIS = 18949;
	private static final int GUARD1 = 18950;
	private static final int GUARD2 = 18951;
	private boolean _FirstAttacked = false;
	
	public _0_EtisEtina(int id, String name, String descr)
	{
		super(name, descr);
		addAttackId(ETIS);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isPet)
	{
		if (npc.getNpcId() == ETIS)
		{
			int maxHp = npc.getMaxHp();
			double nowHp = npc.getStatus().getCurrentHp();
			
			if (nowHp < (maxHp * 0.7))
			{
				if (_FirstAttacked)
				{
					return null;
				}
				
				L2Attackable guardian1 = (L2Attackable) addSpawn(GUARD1, npc.getX() + Rnd.get(10, 50), npc.getY() + Rnd.get(10, 50), npc.getZ(), 0, false, 0, false, npc.getInstanceId());
				guardian1.setRunning();
				guardian1.addDamageHate(attacker, 1, 99999);
				guardian1.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
				
				L2Attackable guardian2 = (L2Attackable) addSpawn(GUARD2, npc.getX() + Rnd.get(10, 80), npc.getY() + Rnd.get(10, 80), npc.getZ(), 0, false, 0, false, npc.getInstanceId());
				guardian2.setRunning();
				guardian2.addDamageHate(attacker, 1, 99999);
				guardian2.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
				_FirstAttacked = true;
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new _0_EtisEtina(-1, "EtisEtina", "data");
	}
}