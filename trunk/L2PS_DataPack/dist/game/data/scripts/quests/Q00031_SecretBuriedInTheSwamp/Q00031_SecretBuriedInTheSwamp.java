package quests.Q00031_SecretBuriedInTheSwamp;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00031_SecretBuriedInTheSwamp extends Quest
{
	private static final int ABERCROMBIE = 31555;
	private static final int FORGOTTEN_MONUMENT_1 = 31661;
	private static final int FORGOTTEN_MONUMENT_2 = 31662;
	private static final int FORGOTTEN_MONUMENT_3 = 31663;
	private static final int FORGOTTEN_MONUMENT_4 = 31664;
	private static final int CORPSE_OF_DWARF = 31665;
	private static final int KRORINS_JOURNAL = 7252;
	
	public Q00031_SecretBuriedInTheSwamp(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(ABERCROMBIE);
		
		for (int i = 31661; i <= 31665; i++)
		{
			addTalkId(i);
		}
		
		questItemIds = new int[]
		{
			KRORINS_JOURNAL
		};
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		int cond = st.getInt("cond");
		
		if (event.equalsIgnoreCase("31555-1.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("31665-1.htm") && (cond == 1))
		{
			st.set("cond", "2");
			st.playSound("ItemSound.quest_itemget");
			st.giveItems(KRORINS_JOURNAL, 1);
		}
		else if (event.equalsIgnoreCase("31555-4.htm") && (cond == 2))
		{
			st.set("cond", "3");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31661-1.htm") && (cond == 3))
		{
			st.set("cond", "4");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31662-1.htm") && (cond == 4))
		{
			st.set("cond", "5");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31663-1.htm") && (cond == 5))
		{
			st.set("cond", "6");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31664-1.htm") && (cond == 6))
		{
			st.set("cond", "7");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31555-7.htm") && (cond == 7))
		{
			st.takeItems(KRORINS_JOURNAL, -1);
			st.addExpAndSp(130000, 0);
			st.giveItems(57, 40000);
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(false);
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		if (st.isCompleted())
		{
			return htmltext = getAlreadyCompletedMsg(player);
		}
		
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if (npcId == ABERCROMBIE)
		{
			if (cond == 0)
			{
				if (st.getPlayer().getLevel() >= 66)
				{
					htmltext = "31555-0.htm";
				}
				else
				{
					htmltext = "31555-0a.htm";
					st.exitQuest(true);
				}
			}
			else if (cond == 1)
			{
				htmltext = "31555-2.htm";
			}
			else if (cond == 2)
			{
				htmltext = "31555-3.htm";
			}
			else if (cond == 3)
			{
				htmltext = "31555-5.htm";
			}
			else if (cond == 7)
			{
				htmltext = "31555-6.htm";
			}
		}
		else if (npcId == CORPSE_OF_DWARF)
		{
			if (cond == 1)
			{
				htmltext = "31665-0.htm";
			}
			else if (cond == 2)
			{
				htmltext = "31665-2.htm";
			}
		}
		else if (npcId == FORGOTTEN_MONUMENT_1)
		{
			if (cond == 3)
			{
				htmltext = "31661-0.htm";
			}
			else if (cond > 3)
			{
				htmltext = "31661-2.htm";
			}
		}
		else if (npcId == FORGOTTEN_MONUMENT_2)
		{
			if (cond == 4)
			{
				htmltext = "31662-0.htm";
			}
			else if (cond > 4)
			{
				htmltext = "31662-2.htm";
			}
		}
		else if (npcId == FORGOTTEN_MONUMENT_3)
		{
			if (cond == 5)
			{
				htmltext = "31663-0.htm";
			}
			else if (cond > 5)
			{
				htmltext = "31663-2.htm";
			}
		}
		else if (npcId == FORGOTTEN_MONUMENT_4)
		{
			if (cond == 6)
			{
				htmltext = "31664-0.htm";
			}
			else if (cond > 6)
			{
				htmltext = "31664-2.htm";
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00031_SecretBuriedInTheSwamp(31, Q00031_SecretBuriedInTheSwamp.class.getSimpleName(), "");
	}
}