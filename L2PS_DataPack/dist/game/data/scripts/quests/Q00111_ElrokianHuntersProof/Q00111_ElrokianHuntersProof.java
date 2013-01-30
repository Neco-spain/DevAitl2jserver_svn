package quests.Q00111_ElrokianHuntersProof;

import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00111_ElrokianHuntersProof extends Quest
{
	private final static int QUEST_NPC[] =
	{
		32113,
		32114,
		32115,
		32116,
		32117
	};
	private final static int QUEST_ITEM[] =
	{
		8768
	};
	private final static int QUEST_MONSTERS1[] =
	{
		22196,
		22197,
		22198,
		22218
	};
	private final static int QUEST_MONSTERS2[] =
	{
		22200,
		22201,
		22202,
		22219
	};
	private final static int QUEST_MONSTERS3[] =
	{
		22208,
		22209,
		22210,
		22221
	};
	private final static int QUEST_MONSTERS4[] =
	{
		22203,
		22204,
		22205,
		22220
	};
	
	public Q00111_ElrokianHuntersProof(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(QUEST_NPC[0]);
		for (final int i : QUEST_NPC)
		{
			addTalkId(i);
		}
		for (final int i : QUEST_MONSTERS1)
		{
			addKillId(i);
		}
		for (final int i : QUEST_MONSTERS2)
		{
			addKillId(i);
		}
		for (final int i : QUEST_MONSTERS3)
		{
			addKillId(i);
		}
		for (final int i : QUEST_MONSTERS4)
		{
			addKillId(i);
		}
		questItemIds = QUEST_ITEM;
	}
	
	private boolean checkPartyCondition(QuestState st, L2PcInstance leader)
	{
		if (leader == null)
		{
			return false;
		}
		
		final L2Party party = leader.getParty();
		if (party == null)
		{
			return false;
		}
		
		if (party.getLeader() != leader)
		{
			return false;
		}
		
		for (L2PcInstance player : party.getMembers())
		{
			if (player.getLevel() < 75)
			{
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		final int cond = st.getInt("cond");
		final int npcId = npc.getNpcId();
		
		switch (st.getState())
		{
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
			case State.CREATED:
				if (npcId == QUEST_NPC[0])
				{
					if (checkPartyCondition(st, st.getPlayer()))
					{
						st.set("cond", "1");
						st.playSound("ItemSound.quest_accept");
						st.setState(State.STARTED);
						htmltext = "32113-1.htm";
					}
					else
					{
						st.exitQuest(true);
						htmltext = "<html><body>This Quest is avaible only for max level 75.</body></html>";
					}
				}
				break;
			case State.STARTED:
				if (npcId == QUEST_NPC[0])
				{
					switch (cond)
					{
						case 3:
							st.set("cond", "4");
							st.playSound("ItemSound.quest_middle");
							htmltext = "32113-2.htm";
							break;
						case 5:
							if (st.getQuestItemsCount(QUEST_ITEM[0]) >= 50)
							{
								st.takeItems(QUEST_ITEM[0], -1);
								st.set("cond", "6");
								st.playSound("ItemSound.quest_middle");
								htmltext = "32113-3.htm";
							}
							break;
					}
				}
				else if (npcId == QUEST_NPC[1])
				{
					if (cond == 1)
					{
						st.set("cond", "2");
						st.playSound("ItemSound.quest_middle");
						htmltext = "32114-1.htm";
					}
				}
				else if (npcId == QUEST_NPC[2])
				{
					switch (cond)
					{
						case 2:
							st.set("cond", "3");
							st.playSound("ItemSound.quest_middle");
							htmltext = "32115-1.htm";
							break;
						case 8:
							st.set("cond", "9");
							st.playSound("ItemSound.quest_middle");
							htmltext = "32115-2.htm";
							break;
						case 9:
							st.set("cond", "10");
							st.playSound("ItemSound.quest_middle");
							htmltext = "32115-3.htm";
							break;
						case 11:
							st.set("cond", "12");
							st.playSound("ItemSound.quest_middle");
							st.giveItems(8773, 1);
							htmltext = "32115-5.htm";
							break;
					}
				}
				else if (npcId == QUEST_NPC[3])
				{
					switch (cond)
					{
						case 6:
							st.set("cond", "8");
							st.playSound("EtcSound.elcroki_song_full");
							htmltext = "32116-1.htm";
							break;
						case 12:
							if (st.getQuestItemsCount(8773) >= 1)
							{
								st.takeItems(8773, 1);
								st.giveItems(8763, 1);
								st.giveItems(8764, 100);
								st.giveItems(57, 1022636);
								st.playSound("ItemSound.quest_finish");
								st.exitQuest(false);
								htmltext = "32116-2.htm";
							}
							break;
					}
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(final L2Npc npc, final L2PcInstance player, final boolean isPet)
	{
		if ((player == null) || (player.getParty() == null))
		{
			return super.onKill(npc, player, isPet);
		}
		
		final QuestState st = player.getParty().getLeader().getQuestState(getName());
		if ((st == null) || (st.getState() != State.STARTED))
		{
			return super.onKill(npc, player, isPet);
		}
		
		final int cond = st.getInt("cond");
		final int npcId = npc.getNpcId();
		
		switch (cond)
		{
			case 4:
				if (Util.contains(QUEST_MONSTERS1, npcId))
				{
					if (getRandom(100) < 25)
					{
						st.giveItems(QUEST_ITEM[0], 1);
						if (st.getQuestItemsCount(QUEST_ITEM[0]) <= 49)
						{
							st.playSound("ItemSound.quest_itemget");
						}
						else
						{
							st.set("cond", "5");
							st.playSound("ItemSound.quest_middle");
						}
					}
				}
				break;
			case 10:
				if (Util.contains(QUEST_MONSTERS2, npcId))
				{
					if (getRandom(100) < 75)
					{
						st.giveItems(8770, 1);
						if (st.getQuestItemsCount(8770) <= 9)
						{
							st.playSound("ItemSound.quest_itemget");
						}
					}
				}
				else if (Util.contains(QUEST_MONSTERS3, npcId))
				{
					if (getRandom(100) < 75)
					{
						st.giveItems(8772, 1);
						if (st.getQuestItemsCount(8771) <= 9)
						{
							st.playSound("ItemSound.quest_itemget");
						}
					}
				}
				else if (Util.contains(QUEST_MONSTERS4, npcId))
				{
					if (getRandom(100) < 75)
					{
						st.giveItems(8771, 1);
						if (st.getQuestItemsCount(8772) <= 9)
						{
							st.playSound("ItemSound.quest_itemget");
						}
					}
				}
				
				if ((st.getQuestItemsCount(8770) >= 10) && (st.getQuestItemsCount(8771) >= 10) && (st.getQuestItemsCount(8772) >= 10))
				{
					st.set("cond", "11");
					st.playSound("ItemSound.quest_middle");
				}
				break;
		}
		
		return super.onKill(npc, player, isPet);
	}
	
	public static void main(String[] args)
	{
		new Q00111_ElrokianHuntersProof(111, Q00111_ElrokianHuntersProof.class.getSimpleName(), "Elrokian Hunters Proof");
	}
}