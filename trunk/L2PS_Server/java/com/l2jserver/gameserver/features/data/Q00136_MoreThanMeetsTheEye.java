package com.l2jserver.gameserver.features.data;

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * Author: RobikBobik L2PS Team
 */
public final class Q00136_MoreThanMeetsTheEye extends Quest
{
	private static final int HARDIN = 30832;
	private static final int ERRICKIN = 30701;
	private static final int CLAYTON = 30464;
	private static final int ECTOPLASM = 9787;
	private static final int STABILIZED_ECTOPLASM = 9786;
	private static final int ORDER = 9788;
	private static final int GLASS_JAGUAR_CRYSTAL = 9789;
	private static final int BOOK_OF_SEAL = 9790;
	private static final int ADENA = 57;
	private static final int TRANSFORM_BOOK = 9648;
	private static final int[] KILLD_IDS_ECTOPLASM =
	{
		20636,
		20637,
		20638,
		20639
	};
	private static final int GLASS_JAGUAR = 20250;
	
	private static Map<Integer, int[]> DROPLIST = new HashMap<>();
	
	static
	{
		DROPLIST.put(20636, new int[]
		{
			ECTOPLASM,
			45,
			0
		});
		DROPLIST.put(20637, new int[]
		{
			ECTOPLASM,
			50,
			5
		});
		DROPLIST.put(20638, new int[]
		{
			ECTOPLASM,
			55,
			10
		});
		DROPLIST.put(20639, new int[]
		{
			ECTOPLASM,
			60,
			120
		});
		DROPLIST.put(20250, new int[]
		{
			GLASS_JAGUAR_CRYSTAL,
			100,
			100
		});
	}
	
	public Q00136_MoreThanMeetsTheEye(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(HARDIN);
		addTalkId(HARDIN);
		addTalkId(ERRICKIN);
		addTalkId(CLAYTON);
		for (int mob : DROPLIST.keySet())
		{
			addKillId(mob);
		}
		
		questItemIds = new int[]
		{
			ECTOPLASM,
			STABILIZED_ECTOPLASM,
			ORDER,
			GLASS_JAGUAR_CRYSTAL,
			BOOK_OF_SEAL
		};
	}
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		if (event.equalsIgnoreCase("30832-02.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30832-05.htm"))
		{
			st.set("cond", "2");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("30832-10.htm"))
		{
			st.takeItems(STABILIZED_ECTOPLASM, 1);
			st.giveItems(ORDER, 1);
			st.set("cond", "6");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("30832-14.htm"))
		{
			st.takeItems(BOOK_OF_SEAL, 1);
			st.giveItems(ADENA, 67550);
			st.giveItems(TRANSFORM_BOOK, 1);
			st.playSound("ItemSound.quest_finish");
			st.setState(State.COMPLETED);
			st.exitQuest(false);
		}
		else if (event.equalsIgnoreCase("30701-02.htm"))
		{
			st.set("cond", "3");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("30464-02.htm"))
		{
			st.takeItems(ORDER, 1);
			st.set("cond", "7");
			st.playSound("ItemSound.quest_middle");
		}
		return htmltext;
	}
	
	@Override
	public final String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = Quest.getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		final int cond = st.getInt("cond");
		final int npcId = npc.getNpcId();
		final byte id = st.getState();
		if (id == State.COMPLETED)
		{
			htmltext = Quest.getAlreadyCompletedMsg(player);
		}
		else if (npcId == HARDIN)
		{
			if (cond == 0)
			{
				if (player.getLevel() >= 50)
				{
					htmltext = "30832-01.htm";
				}
				else
				{
					htmltext = "30832-00.htm";
					st.exitQuest(true);
				}
			}
			else if (cond == 1)
			{
				htmltext = "30832-02.htm";
			}
			else if ((cond == 2) || (cond == 3) || (cond == 4))
			{
				htmltext = "30832-05.htm";
			}
			else if (cond == 5)
			{
				htmltext = "30832-06.htm";
			}
			else if ((cond == 6) || (cond == 7) || (cond == 8))
			{
				htmltext = "30832-10.htm";
			}
			else if (cond == 9)
			{
				htmltext = "30832-11.htm";
			}
		}
		else if (npcId == ERRICKIN)
		{
			if (cond == 2)
			{
				htmltext = "30701-01.htm";
			}
			else if (cond == 4)
			{
				htmltext = "30701-03.htm";
				st.takeItems(ECTOPLASM, 35);
				st.giveItems(STABILIZED_ECTOPLASM, 1);
				st.set("cond", "5");
				st.playSound("ItemSound.quest_middle");
			}
			else if (cond >= 5)
			{
				htmltext = "30701-04.htm";
			}
		}
		else if (npcId == CLAYTON)
		{
			if (cond == 6)
			{
				htmltext = "30464-01.htm";
			}
			else if (cond == 7)
			{
				htmltext = "30464-04.htm";
			}
			else if (cond == 8)
			{
				htmltext = "30464-03.htm";
				st.takeItems(GLASS_JAGUAR_CRYSTAL, 5);
				st.giveItems(BOOK_OF_SEAL, 1);
				st.set("cond", "9");
				st.playSound("ItemSound.quest_middle");
			}
			else if (cond == 9)
			{
				htmltext = "30464-05.htm";
			}
		}
		return htmltext;
	}
	
	@Override
	public final String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		L2PcInstance partyMember = this.getRandomPartyMemberState(player, State.STARTED);
		if (partyMember == null)
		{
			return null;
		}
		QuestState st = partyMember.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		if (st.getState() != State.STARTED)
		{
			return null;
		}
		final int cond = st.getInt("cond");
		final int npcId = npc.getNpcId();
		final int itemId = DROPLIST.get(npcId)[0];
		final int chance1 = DROPLIST.get(npcId)[1];
		final int chance2 = DROPLIST.get(npcId)[2];
		
		if (cond == 3)
		{
			if (Util.contains(KILLD_IDS_ECTOPLASM, npcId) && (st.getQuestItemsCount(ECTOPLASM) < 35))
			{
				st.dropQuestItems(itemId, 1, 1, 35, false, chance1, true);
				if (st.getQuestItemsCount(ECTOPLASM) >= 35)
				{
					st.set("cond", "4");
				}
			}
		}
		else if (cond == 7)
		{
			if ((npcId == GLASS_JAGUAR) && (st.getQuestItemsCount(GLASS_JAGUAR_CRYSTAL) < 5))
			{
				st.dropQuestItems(itemId, 1, 1, 5, false, chance2, true);
				if (st.getQuestItemsCount(GLASS_JAGUAR_CRYSTAL) >= 5)
				{
					st.set("cond", "8");
				}
			}
		}
		
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00136_MoreThanMeetsTheEye(136, Q00136_MoreThanMeetsTheEye.class.getName(), "More Than Meets The Eye");
	}
}