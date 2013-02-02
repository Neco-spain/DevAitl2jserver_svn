package com.l2jserver.gameserver.features.data;

import java.util.Map;

import javolution.util.FastMap;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.L2CharPosition;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

public class _0_NPC_Walk extends AbstractNpcAI
{
	private final L2Npc wharf_patrol01;
	private final L2Npc wharf_patrol02;
	private final L2Npc wharf_patrol03;
	private final L2Npc wharf_patrol04;
	private static Map<String, Object[]> walks = new FastMap<>();
	
	private void loadwalk()
	{
		walks.put("3262801", new Object[]
		{
			Integer.valueOf(-148230),
			Integer.valueOf(255280),
			Integer.valueOf(-184),
			"3262802"
		});
		walks.put("3262802", new Object[]
		{
			Integer.valueOf(-148280),
			Integer.valueOf(254820),
			Integer.valueOf(-184),
			"3262803"
		});
		walks.put("3262803", new Object[]
		{
			Integer.valueOf(-148670),
			Integer.valueOf(254380),
			Integer.valueOf(-184),
			"3262804"
		});
		walks.put("3262804", new Object[]
		{
			Integer.valueOf(-149230),
			Integer.valueOf(254100),
			Integer.valueOf(-184),
			"3262805"
		});
		walks.put("3262805", new Object[]
		{
			Integer.valueOf(-148670),
			Integer.valueOf(254380),
			Integer.valueOf(-184),
			"3262806"
		});
		walks.put("3262806", new Object[]
		{
			Integer.valueOf(-148280),
			Integer.valueOf(254820),
			Integer.valueOf(-184),
			"3262801"
		});
		walks.put("3262807", new Object[]
		{
			Integer.valueOf(-148270),
			Integer.valueOf(255320),
			Integer.valueOf(-184),
			"3262808"
		});
		walks.put("3262808", new Object[]
		{
			Integer.valueOf(-148320),
			Integer.valueOf(254860),
			Integer.valueOf(-184),
			"3262809"
		});
		walks.put("3262809", new Object[]
		{
			Integer.valueOf(-148710),
			Integer.valueOf(254420),
			Integer.valueOf(-184),
			"3262810"
		});
		walks.put("3262810", new Object[]
		{
			Integer.valueOf(-149270),
			Integer.valueOf(254140),
			Integer.valueOf(-184),
			"3262811"
		});
		walks.put("3262811", new Object[]
		{
			Integer.valueOf(-148710),
			Integer.valueOf(254420),
			Integer.valueOf(-184),
			"3262812"
		});
		walks.put("3262812", new Object[]
		{
			Integer.valueOf(-148320),
			Integer.valueOf(254860),
			Integer.valueOf(-184),
			"3262807"
		});
		walks.put("3262901", new Object[]
		{
			Integer.valueOf(-150500),
			Integer.valueOf(255280),
			Integer.valueOf(-184),
			"3262902"
		});
		walks.put("3262902", new Object[]
		{
			Integer.valueOf(-150450),
			Integer.valueOf(254820),
			Integer.valueOf(-184),
			"3262903"
		});
		walks.put("3262903", new Object[]
		{
			Integer.valueOf(-150060),
			Integer.valueOf(254380),
			Integer.valueOf(-184),
			"3262904"
		});
		walks.put("3262904", new Object[]
		{
			Integer.valueOf(-149500),
			Integer.valueOf(254100),
			Integer.valueOf(-184),
			"3262905"
		});
		walks.put("3262905", new Object[]
		{
			Integer.valueOf(-150060),
			Integer.valueOf(254380),
			Integer.valueOf(-184),
			"3262906"
		});
		walks.put("3262906", new Object[]
		{
			Integer.valueOf(-150450),
			Integer.valueOf(254820),
			Integer.valueOf(-184),
			"3262901"
		});
		walks.put("3262907", new Object[]
		{
			Integer.valueOf(-150460),
			Integer.valueOf(255320),
			Integer.valueOf(-184),
			"3262908"
		});
		walks.put("3262908", new Object[]
		{
			Integer.valueOf(-150410),
			Integer.valueOf(254860),
			Integer.valueOf(-184),
			"3262909"
		});
		walks.put("3262909", new Object[]
		{
			Integer.valueOf(-150020),
			Integer.valueOf(254420),
			Integer.valueOf(-184),
			"3262910"
		});
		walks.put("3262910", new Object[]
		{
			Integer.valueOf(-149460),
			Integer.valueOf(254140),
			Integer.valueOf(-184),
			"3262911"
		});
		walks.put("3262911", new Object[]
		{
			Integer.valueOf(-150020),
			Integer.valueOf(254420),
			Integer.valueOf(-184),
			"3262912"
		});
		walks.put("3262912", new Object[]
		{
			Integer.valueOf(-150410),
			Integer.valueOf(254860),
			Integer.valueOf(-184),
			"3262907"
		});
	}
	
	public _0_NPC_Walk(int id, String name, String descr)
	{
		super(name, descr);
		
		loadwalk();
		wharf_patrol01 = addSpawn(32628, -148230, 255280, -184, 0, false, 0);
		wharf_patrol02 = addSpawn(32628, -148270, 255320, -184, 0, false, 0);
		wharf_patrol03 = addSpawn(32629, -150500, 255280, -184, 0, false, 0);
		wharf_patrol04 = addSpawn(32629, -150460, 255320, -184, 0, false, 0);
		startQuestTimer("3262801", 5000, wharf_patrol01, null);
		startQuestTimer("3262807", 5000, wharf_patrol02, null);
		startQuestTimer("3262904", 5000, wharf_patrol03, null);
		startQuestTimer("3262910", 5000, wharf_patrol04, null);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (walks.containsKey(event))
		{
			int x = ((Integer) walks.get(event)[0]).intValue();
			int y = ((Integer) walks.get(event)[1]).intValue();
			int z = ((Integer) walks.get(event)[2]).intValue();
			String nextEvent = (String) walks.get(event)[3];
			npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new L2CharPosition(x, y, z, 0));
			if (((npc.getX() - 100) <= x) && ((npc.getX() + 100) >= x) && ((npc.getY() - 100) <= y) && ((npc.getY() + 100) >= y))
			{
				startQuestTimer(nextEvent, 1000, npc, null);
			}
			else
			{
				startQuestTimer(event, 1000, npc, null);
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	public static void main(String[] args)
	{
		new _0_NPC_Walk(-1, "npc_walk", "data");
	}
}