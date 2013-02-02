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

import java.util.Map;

import javolution.util.FastMap;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.util.Util;

public class _1_SelfExplosiveKamikaze extends AbstractNpcAI
{
	private static final Map<Integer, SkillHolder> MONSTERS = new FastMap<>();
	
	static
	{
		MONSTERS.put(18817, new SkillHolder(5376, 4));
		MONSTERS.put(18818, new SkillHolder(5376, 4));
		MONSTERS.put(18821, new SkillHolder(5376, 5));
		MONSTERS.put(21666, new SkillHolder(4614, 3));
		MONSTERS.put(21689, new SkillHolder(4614, 4));
		MONSTERS.put(21712, new SkillHolder(4614, 5));
		MONSTERS.put(21735, new SkillHolder(4614, 6));
		MONSTERS.put(21758, new SkillHolder(4614, 7));
		MONSTERS.put(21781, new SkillHolder(4614, 9));
	}
	
	public _1_SelfExplosiveKamikaze(int questId, String name, String descr)
	{
		super(name, descr);
		
		for (int npcId : MONSTERS.keySet())
		{
			addAttackId(npcId);
			addSpellFinishedId(npcId);
		}
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance player, int damage, boolean isPet, L2Skill skil)
	{
		if (player != null)
		{
			if (MONSTERS.containsKey(npc.getNpcId()) && !npc.isDead() && Util.checkIfInRange(MONSTERS.get(npc.getNpcId()).getSkill().getSkillRadius(), player, npc, true))
			{
				npc.doCast(MONSTERS.get(npc.getNpcId()).getSkill());
			}
		}
		
		return super.onAttack(npc, player, damage, isPet, skil);
	}
	
	@Override
	public String onSpellFinished(L2Npc npc, L2PcInstance player, L2Skill skill)
	{
		if (MONSTERS.containsKey(npc.getNpcId()) && !npc.isDead() && ((skill.getId() == 4614) || (skill.getId() == 5376)))
		{
			npc.doDie(null);
		}
		
		return super.onSpellFinished(npc, player, skill);
	}
	
	public static void main(String[] args)
	{
		new _1_SelfExplosiveKamikaze(-1, "SelfExplosiveKamikaze", "data");
	}
}