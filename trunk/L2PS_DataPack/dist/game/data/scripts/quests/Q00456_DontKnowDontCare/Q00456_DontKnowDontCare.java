package quests.Q00456_DontKnowDontCare;

import java.util.Map;

import javolution.util.FastList;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.L2Attackable.AggroInfo;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.model.quest.QuestState.QuestType;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;
import com.l2jserver.gameserver.util.Util;
import com.l2jserver.util.Rnd;

public class Q00456_DontKnowDontCare extends Quest
{
	// NPC
	private static final int SEPARATED_SOUL[] = {32864, 32865, 32866, 32867, 32868, 32869, 32870};
	private static final int DRAKE_LORD = 25725;
	private static final int BEGEMOTH_LEADER = 25726;
	private static final int DRAGON_BEAST = 25727;
	
	private static final int DRAKE_LORD_CORPSE = 32884;
	private static final int BEGEMOTH_LEADER_CORPSE = 32885;
	private static final int DRAGON_BEAST_CORPSE = 32886;
	// Item
	private static final int DRAKE_LORDS_ESSENCE = 17251;
	private static final int BEHEMOTH_LEADERS_ESSENCE = 17252;
	private static final int DRAGON_BEASTS_ESSENCE = 17253;
	// Reward
	private static final int armor[] = {15743, 15744, 15745, 15746, 15747, 15748, 15749, 15750, 15751, 15752, 15753, 15754, 15755, 15756, 15757, 15758, 15759};
	private static final int accessory[] = {15763, 15764, 15765};
	private static final int weapons[] = {15558, 15559, 15560, 15561, 115562, 15563, 15564, 15565, 15566, 15567, 15568, 15569, 15570, 15571};
	private static final int bews = 6577;
	private static final int baws = 6578;
	private static final int attributes[] = {9552, 9553, 9554, 9555, 9556, 9557};
	private static final int ews = 959;
	private static final int gemstoneS = 2134;
	
	private FastList<Integer> agroList = new FastList<>();
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(getName());
		
		if (st == null)
			return htmltext;
		
		if (event.equalsIgnoreCase("separated_soul_01_q0456_07.html"))
		{
			st.setState(State.STARTED);
			st.set("cond", "1");
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("clear_data"))
		{
			agroList.clear();
			return null;
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		
		if (st == null)
			return htmltext;
		if (Util.contains(SEPARATED_SOUL, npc.getNpcId()))
		{
			switch (st.getState())
			{
				case State.CREATED:
				{
					if (player.getLevel() >= 80)
						htmltext = "separated_soul_01_q0456_01.htm";
					else
						htmltext = "separated_soul_01_q0456_03.html";
					break;
				}
				case State.STARTED:
				{
					if (st.getInt("cond") == 1)
					{
						if (st.hasQuestItems(DRAKE_LORDS_ESSENCE) || st.hasQuestItems(BEHEMOTH_LEADERS_ESSENCE) || st.hasQuestItems(DRAGON_BEASTS_ESSENCE))
							htmltext = "separated_soul_01_q0456_09.html";
						else
							htmltext = "separated_soul_01_q0456_08.html";
					}
					else if (st.getInt("cond") == 2)
					{
						st.exitQuest(QuestType.DAILY);
						st.takeItems(DRAKE_LORDS_ESSENCE, -1);
						st.takeItems(BEHEMOTH_LEADERS_ESSENCE, -1);
						st.takeItems(DRAGON_BEASTS_ESSENCE, -1);
						rewardPlayer(npc, player);
						htmltext = "separated_soul_01_q0456_10.html";
						st.playSound("ItemSound.quest_finish");
					}
					break;
				}
				case State.COMPLETED:
				{
					htmltext = "separated_soul_01_q0456_02.html";
					break;
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		int spawnCorpse = 0;
		Map<L2Character, AggroInfo> playerList = ((L2Attackable)npc).getAggroList();
		for(AggroInfo aggro : playerList.values())
		{
			L2Character ch = aggro.getAttacker();
			if (ch != null	&& ch.isInParty() && killer.isInParty()	&& ch.isInsideRadius(npc, 1000, true, false))
			{
				agroList.add(ch.getObjectId());
			}
		}
		playerList.clear();
		startQuestTimer("clear_data", 300000, npc, killer);
		
		switch (npc.getNpcId())
		{
			case DRAKE_LORD:
				spawnCorpse = DRAKE_LORD_CORPSE;
				break;
			case BEGEMOTH_LEADER:
				spawnCorpse = BEGEMOTH_LEADER_CORPSE;
				break;
			case DRAGON_BEAST:
				spawnCorpse = DRAGON_BEAST_CORPSE;
				break;
		}
		addSpawn(spawnCorpse, npc.getX()+Rnd.get(-10, 10), npc.getY()+Rnd.get(-10, 10), npc.getZ(), npc.getHeading(), false, 300000);
		return super.onKill(npc, killer, isPet);
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		
		int giveItem = 0;
		String giveItemHtm = "";
		String dontHaveQuest = "";
		String haveItem = "";
		switch (npc.getNpcId())
		{
			case DRAKE_LORD_CORPSE:
				giveItem = DRAKE_LORDS_ESSENCE;
				giveItemHtm = "drake_lord_corpse_q0456_01.html";
				haveItem = "drake_lord_corpse_q0456_03.html";
				dontHaveQuest = "drake_lord_corpse_q0456_02.html";
				break;
			case BEGEMOTH_LEADER_CORPSE:
				giveItem = BEHEMOTH_LEADERS_ESSENCE;
				giveItemHtm = "behemoth_leader_corpse_q0456_01.html";
				haveItem = "behemoth_leader_corpse_q0456_03.html";
				dontHaveQuest = "behemoth_leader_corpse_q0456_02.html";
				break;
			case DRAGON_BEAST_CORPSE:
				giveItem = DRAGON_BEASTS_ESSENCE;
				giveItemHtm = "dragon_beast_corpse_q0456_01.html";
				haveItem = "dragon_beast_corpse_q0456_03.html";
				dontHaveQuest = "dragon_beast_corpse_q0456_02.html";
				break;
		}
		if (st == null)
			return dontHaveQuest;
		else if (st.hasQuestItems(giveItem))
			return haveItem;
		else if (st.getInt("cond") == 1 && agroList.contains(player.getObjectId()))
		{
			st.giveItems(giveItem, 1);
			if (st.hasQuestItems(DRAKE_LORDS_ESSENCE) && st.hasQuestItems(BEHEMOTH_LEADERS_ESSENCE) && st.hasQuestItems(DRAGON_BEASTS_ESSENCE))
			{
				st.set("cond", "2");
				st.playSound("ItemSound.quest_middle");
			}
			return giveItemHtm;
		}
		return null;
	}
	
	private void rewardPlayer(L2Npc npc,L2PcInstance player)
	{
		int chance = Rnd.get(10000);
		int reward = 0;
		int count = 1;
		if (chance < 170)
			reward = armor[Rnd.get(0, armor.length-1)];
		else if (chance < 200)
			reward = accessory[Rnd.get(0, accessory.length-1)];
		else if (chance < 270)
			reward = weapons[Rnd.get(0, weapons.length-1)];
		else if (chance < 325)
			reward = bews;
		else if (chance < 425)
			reward = baws;
		else if (chance < 925)
			reward = attributes[Rnd.get(0, attributes.length-1)];
		else if (chance < 1100)
			reward = ews;
		else
		{
			count = 3;
			reward = gemstoneS;
		}
		L2ItemInstance item = player.addItem("Quest", reward, count, npc, true);
		// must be in system msg
		NpcSay packet = new NpcSay(npc.getObjectId(), Say2.ALL, npc.getNpcId(), NpcStringId.S1_RECEIVED_A_S2_ITEM_AS_A_REWARD_FROM_THE_SEPARATED_SOUL);
		packet.addStringParameter(player.getName());
		packet.addStringParameter(item.getName());
		npc.broadcastPacket(packet);
	}
	
	public Q00456_DontKnowDontCare(int questId, String name, String descr)
	{
		super(questId, name, descr);
		for(int npc : SEPARATED_SOUL)
		{
			addStartNpc(npc);
			addTalkId(npc);
		}

		addTalkId(DRAKE_LORD_CORPSE);
		addTalkId(BEGEMOTH_LEADER_CORPSE);
		addTalkId(DRAGON_BEAST_CORPSE);
		addKillId(DRAKE_LORD);
		addKillId(BEGEMOTH_LEADER);
		addKillId(DRAGON_BEAST);
		addFirstTalkId(DRAKE_LORD_CORPSE);
		addFirstTalkId(BEGEMOTH_LEADER_CORPSE);
		addFirstTalkId(DRAGON_BEAST_CORPSE);
		
		questItemIds = new int[] { DRAKE_LORDS_ESSENCE, BEHEMOTH_LEADERS_ESSENCE, DRAGON_BEASTS_ESSENCE };
	}
	
	public static void main(String[] args)
	{
		new Q00456_DontKnowDontCare(456, Q00456_DontKnowDontCare.class.getSimpleName(), "Don't Know Don't Care");
	}
}