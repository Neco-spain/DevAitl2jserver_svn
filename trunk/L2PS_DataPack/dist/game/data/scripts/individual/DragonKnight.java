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

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.util.Rnd;

public class DragonKnight extends AbstractNpcAI
{
	private static final int DRAGON_KNIGHT_1 = 22844;
	private static final int DRAGON_KNIGHT_2 = 22845;
	private static final int ELITE_DRAGON_KNIGHT = 22846;
	private static final int DRAGON_KNIGHT_WARRIOR = 22847;

	public DragonKnight(int questId, String name, String descr)
	{
		super(name, descr);

		addKillId(DRAGON_KNIGHT_1);
		addKillId(DRAGON_KNIGHT_2);
		addKillId(ELITE_DRAGON_KNIGHT);
		addKillId(DRAGON_KNIGHT_WARRIOR);
	}

	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		if (npc.getNpcId() == DRAGON_KNIGHT_1)
		{
			if (Rnd.get(1000) < 400)
			{
				L2Attackable warrior = (L2Attackable) addSpawn(DRAGON_KNIGHT_2, npc.getX() + Rnd.get(10, 50), npc.getY() + Rnd.get(10, 50), npc.getZ(), 0, false, 240000, true);
				warrior.setRunning();
				warrior.addDamageHate(killer, 1, 99999);
				warrior.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, killer);

				L2Attackable warrior1 = (L2Attackable) addSpawn(DRAGON_KNIGHT_2, npc.getX() + Rnd.get(10, 50), npc.getY() + Rnd.get(10, 50), npc.getZ(), 0, false, 240000, true);
				warrior1.setRunning();
				warrior1.addDamageHate(killer, 1, 99999);
				warrior1.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, killer);
			}
		}
		if (npc.getNpcId() == DRAGON_KNIGHT_2)
		{
			if (Rnd.get(1000) < 350)
			{
				L2Attackable knight = (L2Attackable) addSpawn(ELITE_DRAGON_KNIGHT, npc.getX() + Rnd.get(10, 50), npc.getY() + Rnd.get(10, 50), npc.getZ(), 0, false, 240000, true);
				knight.setRunning();
				knight.addDamageHate(killer, 1, 99999);
				knight.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, killer);
			}

			if (Rnd.get(1000) < 350)
			{
				L2Attackable warior = (L2Attackable) addSpawn(DRAGON_KNIGHT_WARRIOR, npc.getX() + Rnd.get(10, 50), npc.getY() + Rnd.get(10, 50), npc.getZ(), 0, false, 240000, true);
				warior.setRunning();
				warior.addDamageHate(killer, 1, 99999);
				warior.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, killer);
			}
		}
		return super.onKill(npc, killer, isPet);
	}

	public static void main(String[] args)
	{
		new DragonKnight(-1, "DragonKnight", "individual");
	}
}