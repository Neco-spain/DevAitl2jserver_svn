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
package group_template;

import npc.AbstractNpcAI;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;
import com.l2jserver.util.Rnd;

public class DeluLizardmanSpecialCommander extends AbstractNpcAI
{
	private static final int DELU_LIZARDMAN_SPECIAL_COMMANDER = 21107;
	
	public DeluLizardmanSpecialCommander(int questId, String name, String descr)
	{
		super(name, descr);
		addAttackId(DELU_LIZARDMAN_SPECIAL_COMMANDER);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance player, int damage, boolean isPet)
	{
		if (npc.getNpcId() == DELU_LIZARDMAN_SPECIAL_COMMANDER)
		{
			if (npc.getAI().getIntention() != CtrlIntention.AI_INTENTION_ATTACK)
			{
				if (Rnd.get(100) < 70)
					npc.broadcastPacket(new NpcSay(npc.getObjectId(), 0, npc.getNpcId(), NpcStringId.HOW_DARE_YOU_INTERRUPT_A_SACRED_DUEL_YOU_MUST_BE_TAUGHT_A_LESSON));
			}
			else if (Rnd.get(100) < 10)
				npc.broadcastPacket(new NpcSay(npc.getObjectId(), 0, npc.getNpcId(), NpcStringId.S1_COME_ON_ILL_TAKE_YOU_ON));
		}
		
		return super.onAttack(npc, player, damage, isPet);
	}
	
	public static void main(String[] args)
	{
		new DeluLizardmanSpecialCommander(-1, "DeluLizardmanSpecialCommander", "group_template");
	}
}
