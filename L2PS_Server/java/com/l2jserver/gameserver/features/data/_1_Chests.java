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

import java.util.List;

import com.l2jserver.gameserver.datatables.NpcTable;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2ChestInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.templates.L2NpcTemplate;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.network.serverpackets.PlaySound;
import com.l2jserver.gameserver.util.Util;
import com.l2jserver.util.Rnd;

/**
 * Chest AI implementation.
 * @author Fulminus L2PS Fix
 */
public class _1_Chests extends AbstractNpcAI
{
	private static final int _SKILL_UNLOCK_ID = 27;
	private static final int _SKILL_MAESTRO_KEY_ID = 22271;
	// TARGET_AURA skill with suicide(Treasure Bomb)
	private static final int _SKILL_SUICIDE_ID = 4143;
	
	private static void makeSelfDestruction(L2ChestInstance chest)
	{
		int skillLevel = chest.getLevel() / 10;
		chest.doCast(SkillTable.getInstance().getInfo(_SKILL_SUICIDE_ID, skillLevel));
	}
	
	private static void makeDieReward(L2ChestInstance chest, L2PcInstance attacker)
	{
		chest.setIsInvul(false);
		chest.setSpecialDrop();
		chest.reduceCurrentHp(chest.getMaxHp(), attacker, null);
	}
	
	private static void handleChanceFailure(L2ChestInstance chest, L2PcInstance attacker)
	{
		attacker.sendPacket(new PlaySound("ItemSound2.broken_key"));
		chest.deleteMe();
	}
	
	public static void main(String[] args)
	{
		new _1_Chests("chests", "data");
	}
	
	private _1_Chests(String name, String descr)
	{
		super(name, descr);
		List<L2NpcTemplate> chestTemplates = NpcTable.getInstance().getAllNpcOfClassType("L2Chest");
		for (L2NpcTemplate chestTemplate : chestTemplates)
		{
			int chestNpcId = chestTemplate.getNpcId();
			addSpawnId(chestNpcId);
			addAttackId(chestNpcId);
			addSkillSeeId(chestNpcId);
			addSpellFinishedId(chestNpcId);
		}
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		// this is set to avoid killing the chest when a player is too strong with an attack
		npc.setIsInvul(true);
		// this is set to avoid the chests to attack back
		npc.disableCoreAI(true);
		return null;
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isPet)
	{
		L2ChestInstance chest = (L2ChestInstance) npc;
		if (chest.isInteracted())
		{
			return null;
		}
		
		chest.setInteracted();
		makeSelfDestruction(chest);
		return null;
	}
	
	@Override
	public String onSkillSee(L2Npc npc, L2PcInstance caster, L2Skill skill, L2Object[] targets, boolean isPet)
	{
		L2ChestInstance chest = (L2ChestInstance) npc;
		if (!Util.contains(targets, npc) || chest.isInteracted())
		{
			return null;
		}
		
		chest.setInteracted();
		
		int rewardChance = 0;
		
		switch (skill.getId())
		{
			case _SKILL_UNLOCK_ID:
			{
				int maxChance = 0;
				
				switch (skill.getLevel())
				{
					case 1:
						maxChance = 98;
						break;
					case 2:
						maxChance = 84;
						break;
					case 3:
						maxChance = 99;
						break;
					case 4:
						maxChance = 84;
						break;
					case 5:
						maxChance = 88;
						break;
					case 6:
						maxChance = 90;
						break;
					case 7:
						maxChance = 89;
						break;
					case 8:
						maxChance = 88;
						break;
					case 9:
						maxChance = 86;
						break;
					case 10:
						maxChance = 90;
						break;
					case 11:
						maxChance = 87;
						break;
					case 12:
					case 13:
					case 14:
					case 15:
						maxChance = 89;
						break;
					default:
						break;
				}
				rewardChance = maxChance - ((chest.getLevel() - (skill.getLevel() * 4) - 16) * 6);
				if (rewardChance > maxChance)
				{
					rewardChance = maxChance;
				}
			}
			case _SKILL_MAESTRO_KEY_ID:
				if (((caster.getLevel() <= 77) && (Math.abs(chest.getLevel() - caster.getLevel()) > 6)) || ((caster.getLevel() >= 78) && (Math.abs(chest.getLevel() - caster.getLevel()) > 5)))
				{
					rewardChance = 0;
				}
				else
				{
					rewardChance = 100;
				}
				
				break;
			default:
				makeSelfDestruction(chest);
				return null;
		}
		if (Rnd.get(100) < rewardChance)
		{
			makeDieReward(chest, caster);
		}
		else
		{
			handleChanceFailure(chest, caster);
		}
		
		return null;
	}
	
	@Override
	public String onSpellFinished(L2Npc npc, L2PcInstance player, L2Skill skill)
	{
		npc.deleteMe();
		return null;
	}
}
