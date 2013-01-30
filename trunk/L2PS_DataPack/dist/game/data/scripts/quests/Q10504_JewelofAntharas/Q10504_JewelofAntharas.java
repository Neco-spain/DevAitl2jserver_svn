package quests.Q10504_JewelofAntharas;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

public class Q10504_JewelofAntharas extends Quest
{
	private static final String qn = "10504_JewelofAntharas";
	
	private static final int THEODRICK = 30755;
	private static final int[] Antharas = { 29019, 29066, 29067, 29068 }; //Old, Weak, Normal, Strong

	private static final int Portal_stone = 3865;
	private static final int CLEAR_CRYSTAL = 21905;
	private static final int FILLED_CRYSTAL = 21907;
	private static final int JEWEL_OF_ANTHARAS = 21898; // Quest Item

	public Q10504_JewelofAntharas(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(THEODRICK);
		addTalkId(THEODRICK);
		addKillId(Antharas);
		questItemIds = new int[] { CLEAR_CRYSTAL, FILLED_CRYSTAL };
	}		
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		if("quest_accept".equals(event))
		{
			st.set("cond","1");
			st.giveItems(CLEAR_CRYSTAL, 1);
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
			htmltext = "30755-3.htm";
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(qn);
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == THEODRICK)
		{
			switch(st.getState())
			{		
				case State.CREATED :	
					if (player.getLevel() >= 84 && st.getQuestItemsCount(Portal_stone) >= 1)
						htmltext = "30755.htm";
					else if (player.getLevel() < 84)
					{
						htmltext = "30755-0.htm";
					}
					else
						htmltext = "30755-00.htm"; // No Portal Stone				
				break;			
				case State.STARTED :
					if(cond == 1)
						htmltext = "30755-4.htm";
					else if(cond == 2)
					{
						st.giveItems(JEWEL_OF_ANTHARAS, 1);
						st.playSound("ItemSound.quest_finish");
						st.exitQuest(false);
						htmltext = "30755-5.htm";
					}
				break;		
				case State.COMPLETED :
					htmltext = "30755-5.htm";
				break;	
			}	
		}	
		return htmltext;
	}

	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{	
		QuestState st = player.getQuestState(qn);
		if(st.getInt("cond") == 1)
		{
			st.takeItems(CLEAR_CRYSTAL, 1);
			st.giveItems(FILLED_CRYSTAL, 1);
			st.playSound("ItemSound.quest_itemget");
			st.playSound("ItemSound.quest_middle");
			st.set("cond","2");
		}
		return null;
	}
		
	public static void main(String[] args)
	{
		new Q10504_JewelofAntharas(10504, qn, "Jewel of Antharas");	
		_log.info("Loading: Quests Features --- 40 %");
	}	
}