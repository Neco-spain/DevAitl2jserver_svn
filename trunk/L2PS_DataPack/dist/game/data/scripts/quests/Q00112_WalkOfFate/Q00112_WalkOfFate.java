package quests.Q00112_WalkOfFate;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00112_WalkOfFate extends Quest
{
	private static final int LIVINA = 30572;
	private static final int KARUDA = 32017;
	private final static int QUEST_REWARD[] =
	{
		956
	};
	
	public Q00112_WalkOfFate(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(LIVINA);
		addTalkId(LIVINA);
		addTalkId(KARUDA);
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
		
		if (event.equalsIgnoreCase("32017-02.htm"))
		{
			if (st.getInt("cond") == 1)
			{
				st.takeItems(57, 22308);
				st.takeItems(QUEST_REWARD[0], 1);
				st.addExpAndSp(112876, 5774);
				st.exitQuest(false);
				st.playSound("ItemSound.quest_finish");
			}
		}
		else if (event.equalsIgnoreCase("30572-02.htm"))
		{
			st.playSound("ItemSound.quest_accept");
			st.setState(State.STARTED);
			st.set("cond", "1");
		}
		return htmltext;
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
		
		final int npcId = npc.getNpcId();
		
		switch (st.getState())
		{
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
			case State.CREATED:
				if (npcId == LIVINA)
				{
					if (player.getLevel() >= 20)
					{
						htmltext = "30572-01.htm";
					}
					else
					{
						htmltext = "30572-00.htm";
						st.exitQuest(true);
					}
				}
				break;
			case State.STARTED:
				if (npcId == LIVINA)
				{
					htmltext = "30572-03.htm";
				}
				else if (npcId == KARUDA)
				{
					htmltext = "32017-01.htm";
				}
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00112_WalkOfFate(112, Q00112_WalkOfFate.class.getSimpleName(), "Walk Of Fate");
	}
}
