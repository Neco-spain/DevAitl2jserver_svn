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
package events.EventsSpawn;

import com.l2jserver.FunEvents;
import com.l2jserver.gameserver.model.quest.Quest;

/**
 * @Fixed by L2Ps Team
 * www.l2ps.tode.cz
 */
public class EventsSpawn extends Quest
{
	private static final String qn = "EventsSpawn";
	private static final boolean HC_SPAWN = FunEvents.HC_STARTED;
	private static final int FARMER = 13183;
	private static final int HC_SpawnLocs[][] = 
	{
		{ 83000,147529,-3477,15689 }, 	// Giran
		{ 147931,26598,-2200,19424 }, 	// Aden
		{ 147341,-56846,-2783,1722 }, 	// Goddard
		{ 44160,-48746,-799,30212 }  	// Rune
	};
	private static final boolean L2DAY_SPAWN = FunEvents.L2DAY_STARTED;
	private static final int L2DAY_CAT = 31228;
	private static final int L2DAY_SpawnLocs[][] = 
	{
		{ 82700,147529,-3477,15689 }, 	// Giran
		{ 147880,26598,-2213,16383 }, 	// Aden
		{ 147322,-56916,-2788,0 }, 		// Goddard
		{ 44163,-48668,-805,29654 }  	// Rune
	};
	private static final boolean MY_SPAWN = FunEvents.MY_STARTED;
	private static final int _master_yogi 			= 32599;	
	private static final int MY_SpawnLocs[][] = 
	{
		{ 82948,149729,-3471,49648 }, 	// Giran
		{ 148547,26722,-2200,32420 }, 	// Aden
		{ 148127,-56955,-2776,32767 }, 	// Goddard
		{ 44677,-48369,-799,17184 }  	// Rune
	};
	private static final boolean NA_SPAWN = FunEvents.NA_STARTED;
	private static final int Ninja_Master = 500;
	private static final int NA_SpawnLocs[][] = 
	{
		{ 82900,147529,-3477,15689 }, 	// Giran
		{ 148548,26781,-2207,32767 }, 	// Aden
		{ 148128,-57014,-2783,34490 }, 	// Goddard
		{ 44623,-48385,-792,18476 }  	// Rune
	};
	private static final boolean TVE_SPAWN = FunEvents.TVE_STARTED;
	private static final int TVE_npc		= 4301;
	private static final int TVE_SpawnLocs[][] = 
	{
		{ 83006,149729,-3471,47093 }, 	// Giran
		{ 148548,26840,-2207,31522 }, 	// Aden
		{ 148138,-57076,-2783,37604 }, 	// Goddard
		{ 44570,-48389,-799,17216 } 	// Rune
	};
	private static final boolean TOT_SPAWN = FunEvents.TOT_STARTED;
	private static final int SPECIAL_CHEST = 13036;
	private static final int TOT_SpawnLocs[][] = 
	{
		{ 82800,147529,-3477,15689 }, 	// Giran
		{ 148004,26604,-2200,16152 }, 	// Aden
		{ 147374,-56785,-2783,63255 }, 	// Goddard
		{ 44160,-48823,-799,29412 }  	// Rune
	};
	private static final boolean HY_SPAWN = FunEvents.HY_STARTED;
	private static final int HALLOWEEN_MANAGER = 501;
	private static final int HY_SpawnLocs[][] = 
	{
		{ 82887,149729,-3464,49648 }, 	// Giran
		{ 148078,26597,-2200,16152 }, 	// Aden
		{ 148117,-56895,-2776,32767 }, 	// Goddard
		{ 44740,-48358,-792,17216 } 	// Rune
	};
	private static final boolean CH_SPAWN = FunEvents.CH_STARTED;
	private static final int CHRISTMAS_SANTA = 502;
	private static final int CH_SpawnLocs[][] = 
	{
		{ 82817,149727,-3464,49648 }, 	// Giran
		{ 148546,26660,-2200,32420 }, 	// Aden
		{ 147400,-56730,-2776,63255 }, 	// Goddard
		{ 44795,-48353,-792,17216 } 	// Rune
	};
	private static final boolean SQUASH_SPAWN = FunEvents.SQUASH_STARTED;
	private static final int SQUASH_NPC = 31860;
	private static final int[][] SQUASH_SpawnLocs = 
	{
		{ 82756, 149727, -3477, 48909 }, 	// Giran
		{ 148126, 26602, -2213, 16152 }, 	// Aden
		{ 148171, -57173, -2776, 32767 }, 	// Goddard
		{ 44368, -48386, -805, 17216 }, 	// Rune
	};
	private static final boolean SD_SPAWN = FunEvents.SD_STARTED;
	private static final int SD_NPC = 13182;
	private static final int SD_SpawnLocs[][] = 
	{
		{ 82697,149727,-3464,46596 }, 	// Giran
		{ 148543,26598,-2213,28799 }, 	// Aden
		{ 147291,-57055,-2776,61312 }, 	// Goddard
		{ 44212,-48951,-805,32461 }  	// Rune
	};
	private static final boolean AP_SPAWN = FunEvents.AP_STARTED;
	private static final int AP_NPC = 32639;
	private static final int AP_SpawnLocs[][] = 
	{
		{ 82631,149722,-3477,46596 }, 	// Giran
		{ 148178,29596,-2213,16152 }, 	// Aden
		{ 148086,-56803,-2776,32767 }, 	// Goddard
		{ 44482,-48399,-792,17216 } 	// Rune
	};
	public static final boolean SS_SPAWN = FunEvents.SS_STARTED;
	public static final int SS_NPC = 503;
	public static final int SS_SpawnLocs[][] = 
	{
		{ 83067,149724,-3464,46596 },	// Giran
		{ 148552,27169,-2213,28799 }, 	// Aden
		{ 148082,-56761,-2789,32767 }, 	// Goddard
		{ 44433,-48396,-805,17216 } 	// Rune
	};
	
	public EventsSpawn(int questId, String name, String descr)
	{
		super(questId, name, descr);
		checkEvent(HC_SPAWN,FARMER, HC_SpawnLocs);
		checkEvent(L2DAY_SPAWN,L2DAY_CAT, L2DAY_SpawnLocs);
		checkEvent(MY_SPAWN,_master_yogi, MY_SpawnLocs);
		checkEvent(NA_SPAWN,Ninja_Master, NA_SpawnLocs);
		checkEvent(TVE_SPAWN,TVE_npc, TVE_SpawnLocs);
		checkEvent(TOT_SPAWN,SPECIAL_CHEST, TOT_SpawnLocs);
		checkEvent(HY_SPAWN,HALLOWEEN_MANAGER, HY_SpawnLocs);
		checkEvent(CH_SPAWN,CHRISTMAS_SANTA, CH_SpawnLocs);
		checkEvent(SQUASH_SPAWN,SQUASH_NPC, SQUASH_SpawnLocs);
		checkEvent(SD_SPAWN,SD_NPC, SD_SpawnLocs);
		checkEvent(AP_SPAWN,AP_NPC, AP_SpawnLocs);
		checkEvent(SS_SPAWN,SS_NPC, SS_SpawnLocs);
	}
	private void checkEvent(boolean event,int npcId,int[][] loc)
	{
		if (event)
		{
			try
			{
				SpawnEventNpc(npcId,loc);				
			}
			catch (Exception e)
			{				
			}
		}
	}
	
	private void SpawnEventNpc(int npcId,int[][] loc) 
	{
		for (int i=0; i < loc.length; i++)
		{			
			try 
			{
				addSpawn(npcId, loc[i][0], loc[i][1], loc[i][2], loc[i][3], false, 0, false);
			}
			catch (Exception e) 
			{
				_log.warning("EventNpcSpawn: Spawns could not be initialized: "	+ e);
			}
		}
	}
	
	public static void main(String[] args)
	{
		new EventsSpawn(-1,qn,"events");
			_log.warning("Events System: Spawn Events Npcs");
	}
}