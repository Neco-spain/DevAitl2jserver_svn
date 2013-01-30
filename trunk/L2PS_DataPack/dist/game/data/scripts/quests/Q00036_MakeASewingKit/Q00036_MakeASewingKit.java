package quests.Q00036_MakeASewingKit;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00036_MakeASewingKit extends Quest
{
	private static final int ARTISANS_FRAME = 1891;
	private static final int ORIHARUKON = 1893;
	private static final int REINFORCED_STEEL = 7163;
	private static final int SEWING_KIT = 7078;
	
	public Q00036_MakeASewingKit(int id, String name, String descr)
	{
		super(id, name, descr);
		
		addStartNpc(30847);
		addTalkId(30847);
		addKillId(20566);
		questItemIds = new int[]
		{
			REINFORCED_STEEL
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
		if (event.equalsIgnoreCase("30847-1.htm") && (cond == 0))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30847-3.htm") && (cond == 2))
		{
			st.takeItems(REINFORCED_STEEL, 5);
			st.set("cond", "3");
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30847-4a.htm"))
		{
			st.takeItems(ORIHARUKON, 10);
			st.takeItems(ARTISANS_FRAME, 10);
			st.giveItems(SEWING_KIT, 1);
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
		
		int cond = st.getInt("cond");
		
		if (st.isCompleted())
		{
			htmltext = getAlreadyCompletedMsg(player);
		}
		
		if ((cond == 0) && (st.getQuestItemsCount(SEWING_KIT) == 0))
		{
			if (st.getPlayer().getLevel() >= 60)
			{
				QuestState fwear = st.getPlayer().getQuestState("Q00037_PleaseMakeMeFormalWear");
				if ((fwear != null) && (fwear.getState() == State.STARTED))
				{
					if (fwear.get("cond").equals("6"))
					{
						htmltext = "30847-0.htm";
					}
					else
					{
						htmltext = "30847-5.htm";
						st.exitQuest(true);
					}
				}
				else
				{
					htmltext = "30847-5.htm";
					st.exitQuest(true);
				}
			}
			else
			{
				htmltext = "30847-5.htm";
			}
		}
		else if ((cond == 1) && (st.getQuestItemsCount(REINFORCED_STEEL) < 5))
		{
			htmltext = "30847-1a.htm";
		}
		else if ((cond == 2) && (st.getQuestItemsCount(REINFORCED_STEEL) == 5))
		{
			htmltext = "30847-2.htm";
		}
		else if ((cond == 3) && (st.getQuestItemsCount(ORIHARUKON) >= 10) && (st.getQuestItemsCount(ARTISANS_FRAME) >= 10))
		{
			htmltext = "30847-4.htm";
		}
		else
		{
			htmltext = "30847-3a.htm";
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState st = player.getQuestState(getName());
		
		if (st.getQuestItemsCount(REINFORCED_STEEL) < 5)
		{
			st.giveItems(REINFORCED_STEEL, 1);
			if (st.getQuestItemsCount(REINFORCED_STEEL) == 5)
			{
				st.playSound("ItemSound.quest_middle");
				st.set("cond", "2");
			}
			else
			{
				st.playSound("ItemSound.quest_itemget");
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00036_MakeASewingKit(36, Q00036_MakeASewingKit.class.getSimpleName(), "");
	}
}