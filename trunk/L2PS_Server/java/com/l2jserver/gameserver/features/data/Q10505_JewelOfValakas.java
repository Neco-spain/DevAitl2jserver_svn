package com.l2jserver.gameserver.features.data;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

public class Q10505_JewelOfValakas extends Quest
{
	private static final String qn = "10505_JewelOfValakas";
	
	// NPC's
	private static final int KLEIN = 31540;
	private static final int VALAKAS = 29028;
	// Item's
	private static final int EMPTY_CRYSTAL = 21906;
	private static final int FILLED_CRYSTAL_VALAKAS = 21908;
	private static final int VACUALITE_FLOATING_STONE = 7267;
	private static final int JEWEL_OF_VALAKAS = 21896;
	
	public Q10505_JewelOfValakas(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(KLEIN);
		addTalkId(KLEIN);
		questItemIds = new int[]
		{
			EMPTY_CRYSTAL,
			FILLED_CRYSTAL_VALAKAS
		};
		addKillId(VALAKAS);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		if ("quest_accept".equals(event))
		{
			st.set("cond", "1");
			st.giveItems(EMPTY_CRYSTAL, 1);
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
			htmltext = "valakas_watchman_klein_q10505_04.htm";
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
		if (npcId == KLEIN)
		{
			switch (st.getState())
			{
				case State.CREATED:
					if ((player.getLevel() >= 84) && (st.getQuestItemsCount(VACUALITE_FLOATING_STONE) >= 1))
					{
						htmltext = "valakas_watchman_klein_q10505_01.htm";
					}
					else if (player.getLevel() < 84)
					{
						htmltext = "valakas_watchman_klein_q10505_00.htm";
					}
					else
					{
						htmltext = "valakas_watchman_klein_q10505_00a.htm"; // No Floating Stone
					}
					break;
				case State.STARTED:
					if (cond == 1)
					{
						if (st.getQuestItemsCount(EMPTY_CRYSTAL) < 1)
						{
							htmltext = "valakas_watchman_klein_q10505_08.htm";
							st.giveItems(EMPTY_CRYSTAL, 1);
						}
						else
						{
							htmltext = "valakas_watchman_klein_q10505_05.htm";
						}
					}
					else if (cond == 2)
					{
						if (st.getQuestItemsCount(FILLED_CRYSTAL_VALAKAS) >= 1)
						{
							htmltext = "valakas_watchman_klein_q10505_07.htm";
							st.takeItems(FILLED_CRYSTAL_VALAKAS, 1);
							st.giveItems(JEWEL_OF_VALAKAS, 1);
							st.playSound("ItemSound.quest_finish");
							st.exitQuest(false);
						}
						else
						{
							htmltext = "valakas_watchman_klein_q10505_06.htm";
						}
					}
					break;
				case State.COMPLETED:
					htmltext = "valakas_watchman_klein_q10505_09.htm";
					break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState st = player.getQuestState(qn);
		if (st.getInt("cond") == 1)
		{
			st.takeItems(EMPTY_CRYSTAL, 1);
			st.giveItems(FILLED_CRYSTAL_VALAKAS, 1);
			st.playSound("ItemSound.quest_itemget");
			st.playSound("ItemSound.quest_middle");
			st.set("cond", "2");
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q10505_JewelOfValakas(10505, qn, "Jewel of Valakas");
	}
}