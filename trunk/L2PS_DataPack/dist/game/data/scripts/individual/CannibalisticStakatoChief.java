package individual;

import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.List;

import npc.AbstractNpcAI;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.util.Util;
import com.l2jserver.util.Rnd;

public class CannibalisticStakatoChief extends AbstractNpcAI
{
	private static final int CANNIBALISTIC_CHIEF = 25667;
	private static final int[] BIZARRE_COCOONS = { 18795, 18798 };
	private static final int LARGE_COCOON = 14834;
	private static final int SMALL_COCOON = 14833;
	private static TIntObjectHashMap<Integer> _captainSpawn = new TIntObjectHashMap<>();

	public CannibalisticStakatoChief(int questId, String name, String descr)
	{
		super(name, descr);

		addKillId(CANNIBALISTIC_CHIEF);
		for (int i : BIZARRE_COCOONS)
			addSkillSeeId(i);
	}	

	@Override
	public String onSkillSee(L2Npc npc, L2PcInstance caster, L2Skill skill, L2Object[] targets, boolean isPet)
	{
		int npcId = npc.getNpcId();
		if ((Util.contains(BIZARRE_COCOONS, npcId)) && (skill.getId() == 2905) && (caster.getTarget() == npc))
		{
			npc.getSpawn().stopRespawn();
			npc.doDie(npc);
			L2Npc captain = addSpawn(CANNIBALISTIC_CHIEF, npc.getSpawn().getLocx(), npc.getSpawn().getLocy(), npc.getSpawn().getLocz(), 0, false, 0);
			_captainSpawn.put(captain.getObjectId(), Integer.valueOf(npc.getNpcId()));
			caster.getInventory().destroyItemByItemId("removeAccelerator", 14832, 1, caster, caster);
		}
		return super.onSkillSee(npc, caster, skill, targets, isPet);
	}

	@Override
	@Deprecated
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		L2Party partyK = killer.getParty();
		if (partyK != null)
		{
			List<L2PcInstance> party = partyK.getMembers();
			for (L2PcInstance member : party)
			{
				if (Rnd.get(100) > 80)
					member.addItem("BigCocoon", LARGE_COCOON, 1, npc, true);
				else 
				{
					member.addItem("SMALL_COCOON", SMALL_COCOON, 1, npc, true);
				}
			}
		}
		else if (Rnd.get(100) > 80) 
		{
	
			killer.addItem("BigCocoon", LARGE_COCOON, 1, npc, true);
		} 
		else 
		{
			killer.addItem("SMALL_COCOON", SMALL_COCOON, 1, npc, true);
		}

		addSpawn(((Integer)_captainSpawn.get(npc.getObjectId())).intValue(), npc.getSpawn().getLocx(), npc.getSpawn().getLocy(), npc.getSpawn().getLocz(), 0, false, 0);
		_captainSpawn.remove(npc.getObjectId());

		return super.onKill(npc, killer, isPet);
	}

	public static void main(String[] args)
	{
		new CannibalisticStakatoChief(-1, "CannibalisticStakatoChief", "individual");
	}
}