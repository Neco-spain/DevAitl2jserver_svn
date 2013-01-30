package quests.Q00005_MinersFavor;

import com.l2jserver.Config;
import com.l2jserver.gameserver.customs.CustomMessage;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00005_MinersFavor extends Quest
{
	private final static int QUEST_ITEM[] =
	{
		1547,
		1552,
		1548,
		1549,
		1550,
		1551
	};
	private final static int QUEST_NPC[] =
	{
		30554,
		30517,
		30518,
		30520,
		30526
	};
	private final static int QUEST_REWARD[] =
	{
		57,
		906
	};
	
	public Q00005_MinersFavor(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(QUEST_NPC[0]);
		for (int npcId : QUEST_NPC)
		{
			addTalkId(npcId);
		}
		questItemIds = QUEST_ITEM;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		
		QuestState qs = player.getQuestState(getName());
		if (qs == null)
		{
			return htmltext;
		}
		
		if (event.equalsIgnoreCase("30554-03.htm"))
		{
			qs.giveItems(QUEST_ITEM[0], 1);
			qs.giveItems(QUEST_ITEM[1], 1);
			qs.set("cond", "1");
			qs.setState(State.STARTED);
			qs.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30526-02.htm"))
		{
			qs.takeItems(QUEST_ITEM[1], -1);
			qs.giveItems(QUEST_ITEM[3], 1);
			if ((qs.getQuestItemsCount(QUEST_ITEM[0]) > 0) && ((qs.getQuestItemsCount(QUEST_ITEM[2]) > 0) && (qs.getQuestItemsCount(QUEST_ITEM[3]) > 0) && (qs.getQuestItemsCount(QUEST_ITEM[4]) > 0) && (qs.getQuestItemsCount(QUEST_ITEM[5]) > 0)))
			{
				qs.set("cond", "2");
				qs.playSound("ItemSound.quest_middle");
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState qs = player.getQuestState(getName());
		if (qs == null)
		{
			qs = newQuestState(player);
		}
		
		String htmltext = getNoQuestMsg(player);
		final int cond = qs.getInt("cond");
		final int npcId = npc.getNpcId();
		
		switch (qs.getState())
		{
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
			case State.CREATED:
				if (player.getLevel() >= 2)
				{
					htmltext = "30554-02.htm";
				}
				else
				{
					htmltext = "30554-01.htm";
					qs.exitQuest(true);
				}
				break;
			case State.STARTED:
				switch (cond)
				{
					case 1:
						if (npcId == QUEST_NPC[0])
						{
							htmltext = "30554-04.htm";
						}
						else if (npcId == QUEST_NPC[1])
						{
							if (qs.getQuestItemsCount(QUEST_ITEM[0]) > 0)
							{
								if (qs.getQuestItemsCount(QUEST_ITEM[4]) == 0)
								{
									htmltext = "30517-01.htm";
									qs.giveItems(QUEST_ITEM[4], 1);
									qs.playSound("ItemSound.quest_itemget");
								}
								else
								{
									htmltext = "30517-02.htm";
								}
							}
						}
						else if (npcId == QUEST_NPC[2])
						{
							if ((qs.getQuestItemsCount(QUEST_ITEM[0]) > 0) && (qs.getQuestItemsCount(QUEST_ITEM[4]) > 0))
							{
								if (qs.getQuestItemsCount(QUEST_ITEM[2]) == 0)
								{
									htmltext = "30518-01.htm";
									qs.giveItems(QUEST_ITEM[2], 1);
									qs.playSound("ItemSound.quest_itemget");
								}
								else
								{
									htmltext = "30518-02.htm";
								}
							}
						}
						else if (npcId == QUEST_NPC[3])
						{
							if ((qs.getQuestItemsCount(QUEST_ITEM[0]) > 0) && (qs.getQuestItemsCount(QUEST_ITEM[2]) > 0))
							{
								if (qs.getQuestItemsCount(QUEST_ITEM[5]) == 0)
								{
									htmltext = "30520-01.htm";
									qs.giveItems(QUEST_ITEM[5], 1);
									qs.playSound("ItemSound.quest_itemget");
								}
								else
								{
									htmltext = "30520-02.htm";
								}
							}
						}
						else if (npcId == QUEST_NPC[4])
						{
							if ((qs.getQuestItemsCount(QUEST_ITEM[0]) > 0) && (qs.getQuestItemsCount(QUEST_ITEM[5]) > 0))
							{
								if ((qs.getQuestItemsCount(QUEST_ITEM[3]) == 0) && (qs.getQuestItemsCount(QUEST_ITEM[1]) > 0))
								{
									htmltext = "30526-01.htm";
								}
								else
								{
									htmltext = "30526-03.htm";
								}
							}
						}
						break;
					case 2:
						if (npcId == QUEST_NPC[0])
						{
							htmltext = "30554-06.htm";
							qs.giveItems(QUEST_REWARD[0], Math.round(2466 * Config.RATE_QUEST_REWARD_ADENA));
							qs.giveItems(QUEST_REWARD[1], (long) Config.RATE_QUEST_REWARD);
							qs.addExpAndSp(5672, 446);
							qs.unset("cond");
							qs.exitQuest(false);
							qs.playSound("ItemSound.quest_finish");
							player.sendPacket(new ExShowScreenMessage(((new CustomMessage("Newbie.Message1", player.getLang())).toString()), 3000));
						}
						break;
				}
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00005_MinersFavor(5, Q00005_MinersFavor.class.getSimpleName(), "");
	}
}