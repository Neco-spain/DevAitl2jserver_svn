package quests.Q00010_IntoTheWorld;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00010_IntoTheWorld extends Quest
{
	private static final int VERY_EXPENSIVE_NECKLACE = 7574;
	private static final int SCROLL_OF_ESCAPE_GIRAN = 7126;
	private static final int MARK_OF_TRAVELER = 7570;
	
	private static final int BALANKI = 30533;
	private static final int REED = 30520;
	private static final int GERALD = 30650;
	
	public Q00010_IntoTheWorld(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(BALANKI);
		addTalkId(BALANKI);
		addTalkId(REED);
		addTalkId(GERALD);
		questItemIds = new int[]
		{
			VERY_EXPENSIVE_NECKLACE
		};
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		if (event.equalsIgnoreCase("30533-03.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30520-02.htm"))
		{
			st.giveItems(VERY_EXPENSIVE_NECKLACE, 1);
			st.set("cond", "2");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("30650-02.htm"))
		{
			st.takeItems(VERY_EXPENSIVE_NECKLACE, 1);
			st.set("cond", "3");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("30520-05.htm"))
		{
			st.set("cond", "4");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("30533-06.htm"))
		{
			st.giveItems(SCROLL_OF_ESCAPE_GIRAN, 1);
			st.giveItems(MARK_OF_TRAVELER, 1);
			st.exitQuest(false);
			st.playSound("ItemSound.quest_finish");
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
				if (npcId == BALANKI)
				{
					if ((qs.getPlayer().getRace().ordinal() == 4) && (qs.getPlayer().getLevel() >= 3))
					{
						htmltext = "30533-02.htm";
					}
					else
					{
						htmltext = "30533-01.htm";
						qs.exitQuest(true);
					}
				}
				break;
			case State.STARTED:
				switch (npcId)
				{
					case BALANKI:
						switch (cond)
						{
							case 1:
								htmltext = "30533-04.htm";
								break;
							case 4:
								htmltext = "30533-05.htm";
								break;
						}
						break;
					case REED:
						switch (cond)
						{
							case 1:
								htmltext = "30520-01.htm";
								break;
							case 2:
								htmltext = "30520-03.htm";
								break;
							case 3:
								htmltext = "30520-04.htm";
								qs.set("cond", "4");
								break;
							case 4:
								htmltext = "30520-06.htm";
								break;
						}
						break;
					case GERALD:
						switch (cond)
						{
							case 2:
								if (qs.getQuestItemsCount(VERY_EXPENSIVE_NECKLACE) > 0)
								{
									htmltext = "30520-01.htm";
								}
								break;
							case 3:
								htmltext = "30650-03.htm";
								break;
							default:
								htmltext = "30650-04.htm";
								break;
						}
						break;
				}
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00010_IntoTheWorld(10, Q00010_IntoTheWorld.class.getSimpleName(), "");
	}
}
