package individual;

import npc.AbstractNpcAI;

import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.MagicSkillUse;
import com.l2jserver.util.Rnd;


public class CannibalisticStakatoFollower extends AbstractNpcAI
{
	private static final int CANNIBALISTIC_LEADER = 22625;

	public CannibalisticStakatoFollower(int questId, String name, String descr)
	{
		super(name, descr);

		addAttackId(CANNIBALISTIC_LEADER);
	}

	@Override
	public String onAttack(L2Npc npc, L2PcInstance player, int damage, boolean isPet)
	{
		if (npc.getMaxHp() * 0.3 > npc.getCurrentHp())
		{
			if (Rnd.get(100) <= 25)
			{
				L2Npc minion = getLeaderMinion(npc);
				if ((minion != null) && (!minion.isDead()))
				{
					npc.broadcastPacket(new MagicSkillUse(npc, minion, 4485, 1, 3000, 0));
					ThreadPoolManager.getInstance().scheduleGeneral(new eatTask(npc, minion, null), 3000);
				}
			}
		}
		return super.onAttack(npc, player, damage, isPet);
	}

	public L2Npc getLeaderMinion(L2Npc leader)
	{
		if (((L2MonsterInstance)leader).getMinionList().getSpawnedMinions().size() > 0) 
		{
			return ((L2MonsterInstance)leader).getMinionList().getSpawnedMinions().get(0);
		}
		return null;
	}

	public static void main(String[] args)
	{
		new CannibalisticStakatoFollower(-1, "CannibalisticStakatoFollower", "individual");
	}

	private class eatTask implements Runnable
	{
		private L2Npc _npc;
		private L2Npc _minion;

		eatTask(L2Npc npc, L2Npc minion, Object object)
		{
			_npc = npc;
			_minion = minion;
		}

		@Override
		public void run()
		{
			if (_minion == null) 
			{
				return;
			}
			double hpToSacrifice = _minion.getCurrentHp();
			_npc.setCurrentHp(_npc.getCurrentHp() + hpToSacrifice);
			_npc.broadcastPacket(new MagicSkillUse(_npc, _minion, 4484, 1, 1000, 0));
			_minion.doDie(_minion);
		}
	}
}