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

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.ZoneManager;
import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.base.ClassId;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.zone.type.L2ScriptZone;

public class _3_DragonValleyZone extends AbstractNpcAI
{
	public static final Map<ClassId, Double> weight = new HashMap<>();
	public static final L2ScriptZone zone = ZoneManager.getInstance().getZoneById(80005, L2ScriptZone.class);
	
	static
	{
		weight.put(ClassId.duelist, 0.2);
		weight.put(ClassId.dreadnought, 0.7);
		weight.put(ClassId.phoenixKnight, 0.5);
		weight.put(ClassId.hellKnight, 0.5);
		weight.put(ClassId.sagittarius, 0.3);
		weight.put(ClassId.adventurer, 0.4);
		weight.put(ClassId.archmage, 0.3);
		weight.put(ClassId.soultaker, 0.3);
		weight.put(ClassId.arcanaLord, 1.);
		weight.put(ClassId.cardinal, -0.6);
		weight.put(ClassId.hierophant, 0.);
		weight.put(ClassId.evaTemplar, 0.8);
		weight.put(ClassId.swordMuse, 0.5);
		weight.put(ClassId.windRider, 0.4);
		weight.put(ClassId.moonlightSentinel, 0.3);
		weight.put(ClassId.mysticMuse, 0.3);
		weight.put(ClassId.elementalMaster, 1.);
		weight.put(ClassId.evaSaint, -0.6);
		weight.put(ClassId.shillienTemplar, 0.8);
		weight.put(ClassId.spectralDancer, 0.5);
		weight.put(ClassId.ghostHunter, 0.4);
		weight.put(ClassId.ghostSentinel, 0.3);
		weight.put(ClassId.stormScreamer, 0.3);
		weight.put(ClassId.spectralMaster, 1.);
		weight.put(ClassId.shillienSaint, -0.6);
		weight.put(ClassId.titan, 0.3);
		weight.put(ClassId.dominator, 0.1);
		weight.put(ClassId.grandKhavatari, 0.2);
		weight.put(ClassId.doomcryer, 0.1);
		weight.put(ClassId.fortuneSeeker, 0.9);
		weight.put(ClassId.maestro, 0.7);
		weight.put(ClassId.doombringer, 0.2);
		weight.put(ClassId.trickster, 0.5);
		weight.put(ClassId.judicator, 0.1);
		weight.put(ClassId.maleSoulhound, 0.3);
		weight.put(ClassId.femaleSoulhound, 0.3);
	}
	
	public int getBuffLevel(L2PcInstance player)
	{
		if (!player.isInParty())
		{
			return 0;
		}
		
		L2Party party = player.getParty();
		if (party.getMemberCount() < 5)
		{
			return 0;
		}
		
		for (L2PcInstance p : party.getMembers())
		{
			if (p.getLevel() < 80)
			{
				return 0;
			}
		}
		
		double points = 0;
		int count = party.getMemberCount();
		
		for (L2PcInstance p : party.getMembers())
		{
			points += weight.get(p.getClassId());
		}
		
		return (int) Math.max(0, Math.min(3, Math.round(points * getCoefficient(count)))); // Brutally custom
	}
	
	private double getCoefficient(int count)
	{
		double cf;
		
		switch (count)
		{
			case 2:
				cf = 0.1;
				break;
			case 3:
				cf = 0.5;
				break;
			case 4:
				cf = 0.7;
				break;
			case 5:
				cf = 0.75;
				break;
			case 6:
				cf = 0.8;
				break;
			case 7:
				cf = 0.85;
				break;
			case 8:
				cf = 0.9;
				break;
			case 9:
				cf = 0.95;
				break;
			default:
				cf = 1;
		}
		return cf;
	}
	
	public _3_DragonValleyZone(int questId, String name, String descr)
	{
		super(descr, descr);
		
		ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new BuffTask(), 1000, 10000);
	}
	
	private class BuffTask implements Runnable
	{
		public BuffTask()
		{
		}
		
		@Override
		public void run()
		{
			for (L2PcInstance pc : zone.getPlayersInside())
			{
				int num = getBuffLevel(pc);
				if (num > 0)
				{
					L2Skill skill = SkillTable.getInstance().getInfo(6885, num);
					skill.getEffects(pc, pc);
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		new _3_DragonValleyZone(-1, _3_DragonValleyZone.class.getSimpleName(), "data");
	}
}