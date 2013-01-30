package quests.Q00033_MakeAPairOfDressShoes;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Tem
 */
public class Q00033_MakeAPairOfDressShoes extends Quest
{
	private static final int ADENA = 57;
	private static final int DRESS_SHOES_BOX = 7113;
	private static final int LEATHER = 1882;
	private static final int THREAD = 1868;
	
	public Q00033_MakeAPairOfDressShoes(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(30838);
		addTalkId(30838);
		addTalkId(30164);
		addTalkId(31520);
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
		
		if (event.equalsIgnoreCase("30838-1.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("31520-1.htm"))
		{
			st.set("cond", "2");
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30838-3.htm"))
		{
			st.set("cond", "3");
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30838-5.htm"))
		{
			if ((st.getQuestItemsCount(LEATHER) >= 200) && (st.getQuestItemsCount(THREAD) >= 600) && (st.getQuestItemsCount(ADENA) >= 200000))
			{
				st.takeItems(LEATHER, 200);
				st.takeItems(THREAD, 600);
				st.takeItems(ADENA, 200000);
				st.set("cond", "4");
				st.playSound("ItemSound.quest_accept");
			}
			else
			{
				htmltext = "30838-3a.htm";
			}
		}
		else if (event.equalsIgnoreCase("30164-1.htm"))
		{
			if (st.getQuestItemsCount(ADENA) >= 300000)
			{
				st.takeItems(ADENA, 300000);
				st.set("cond", "5");
				st.playSound("ItemSound.quest_accept");
			}
			else
			{
				htmltext = "30164-1b.htm";
			}
		}
		else if (event.equalsIgnoreCase("30838-7.htm"))
		{
			st.giveItems(DRESS_SHOES_BOX, 1);
			st.playSound("ItemSound.quest_finish");
			st.unset("cond");
			st.exitQuest(true);
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		
		if (st.isCompleted())
		{
			htmltext = getAlreadyCompletedMsg(player);
		}
		
		if (npcId == 30838)
		{
			if ((cond == 0) && (st.getQuestItemsCount(DRESS_SHOES_BOX) == 0))
			{
				if (st.getPlayer().getLevel() >= 60)
				{
					QuestState fwear = st.getPlayer().getQuestState("Q00037_PleaseMakeMeFormalWear");
					if ((fwear != null) && (fwear.get("cond") != null))
					{
						if (fwear.get("cond").equals("7"))
						{
							htmltext = "30838-0.htm";
						}
						else
						{
							htmltext = "30838-8.htm";
							st.exitQuest(true);
						}
					}
					else
					{
						htmltext = "30838-8.htm";
						st.exitQuest(true);
					}
				}
				else
				{
					htmltext = "30838-8.htm";
				}
			}
			else if (cond == 1)
			{
				htmltext = "30838-1a.htm";
			}
			else if (cond == 2)
			{
				htmltext = "30838-2.htm";
			}
			else if (cond == 3)
			{
				htmltext = "30838-4.htm";
			}
			else if (cond == 4)
			{
				htmltext = "30838-5a.htm";
			}
			else if (cond == 5)
			{
				htmltext = "30838-6.htm";
			}
		}
		else if (npcId == 31520)
		{
			if (cond == 1)
			{
				htmltext = "31520-0.htm";
			}
			else if (cond == 2)
			{
				htmltext = "31520-1a.htm";
			}
		}
		else if (npcId == 30164)
		{
			if (cond == 4)
			{
				htmltext = "30164-0.htm";
			}
			else if (cond == 5)
			{
				htmltext = "30164-1a.htm";
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00033_MakeAPairOfDressShoes(33, Q00033_MakeAPairOfDressShoes.class.getSimpleName(), "");
	}
}