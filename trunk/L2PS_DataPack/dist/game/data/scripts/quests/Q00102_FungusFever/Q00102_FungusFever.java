package quests.Q00102_FungusFever;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.util.Rnd;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00102_FungusFever extends Quest
{
	int ALBERRYUS_LETTER = 964;
	int EVERGREEN_AMULET = 965;
	int DRYAD_TEARS = 966;
	int LBERRYUS_LIST = 746;
	int COBS_MEDICINE1 = 1130;
	int COBS_MEDICINE2 = 1131;
	int COBS_MEDICINE3 = 1132;
	int COBS_MEDICINE4 = 1133;
	int COBS_MEDICINE5 = 1134;
	int SWORD_OF_SENTINEL = 743;
	int STAFF_OF_SENTINEL = 744;
	int ALBERRYUS_LIST = 746;
	
	public Q00102_FungusFever(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(30284);
		addTalkId(30156);
		addTalkId(30217);
		addTalkId(30219);
		addTalkId(30221);
		addTalkId(30284);
		addTalkId(30285);
		
		addKillId(20013);
		addKillId(20019);
		
		questItemIds = new int[]
		{
			ALBERRYUS_LETTER,
			EVERGREEN_AMULET,
			DRYAD_TEARS,
			COBS_MEDICINE1,
			COBS_MEDICINE2,
			COBS_MEDICINE3,
			COBS_MEDICINE4,
			COBS_MEDICINE5,
			ALBERRYUS_LIST
		};
	}
	
	private void check(QuestState st)
	{
		if ((st.getQuestItemsCount(COBS_MEDICINE2) == 0) && (st.getQuestItemsCount(COBS_MEDICINE3) == 0) && (st.getQuestItemsCount(COBS_MEDICINE4) == 0) && (st.getQuestItemsCount(COBS_MEDICINE5) == 0))
		{
			st.playSound("ItemSound.quest_middle");
			st.set("cond", "6");
		}
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
		
		if (event.equalsIgnoreCase("1"))
		{
			htmltext = "30284-02.htm";
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.giveItems(ALBERRYUS_LETTER, 1);
			st.playSound("ItemSound.quest_accept");
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
		
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		
		if (npcId == 30284)
		{
			if (cond == 0)
			{
				if (st.getPlayer().getRace().ordinal() != 1)
				{
					htmltext = "30284-00.htm";
					st.exitQuest(true);
				}
				else if (st.getPlayer().getLevel() >= 12)
				{
					htmltext = "30284-07.htm";
					return htmltext;
				}
				else
				{
					htmltext = "30284-08.htm";
					st.exitQuest(true);
				}
				
			}
			else if ((cond == 1) && (st.getQuestItemsCount(ALBERRYUS_LETTER) == 1))
			{
				htmltext = "30284-03.htm";
			}
			else if ((cond == 2) && (st.getQuestItemsCount(EVERGREEN_AMULET) == 1))
			{
				htmltext = "30284-09.htm";
			}
			else if ((cond == 4) && (st.getQuestItemsCount(COBS_MEDICINE1) == 1))
			{
				st.set("cond", "5");
				st.playSound("ItemSound.quest_middle");
				st.takeItems(COBS_MEDICINE1, 1);
				st.giveItems(ALBERRYUS_LIST, 1);
				htmltext = "30284-04.htm";
			}
			else if (cond == 5)
			{
				htmltext = "30284-05.htm";
			}
			else if ((cond == 6) && (st.getQuestItemsCount(ALBERRYUS_LIST) == 1))
			{
				st.takeItems(ALBERRYUS_LIST, 1);
				st.set("cond", "0");
				st.exitQuest(false);
				st.playSound("ItemSound.quest_finish");
				htmltext = "30284-06.htm";
				st.giveItems(1060, 100);
				for (int item = 4412; item <= 4417; item++)
				{
					st.giveItems(item, 10);
				}
				if (st.getPlayer().getClassId().isMage())
				{
					st.giveItems(5790, 3000);
					st.giveItems(STAFF_OF_SENTINEL, 1);
				}
				else
				{
					st.giveItems(5789, 6000);
					st.giveItems(SWORD_OF_SENTINEL, 1);
				}
			}
		}
		else if (npcId == 30156)
		{
			if ((cond == 1) && (st.getQuestItemsCount(ALBERRYUS_LETTER) == 1))
			{
				st.takeItems(ALBERRYUS_LETTER, 1);
				st.giveItems(EVERGREEN_AMULET, 1);
				st.set("cond", "2");
				htmltext = "30156-03.htm";
			}
			else if ((cond == 2) && (st.getQuestItemsCount(EVERGREEN_AMULET) > 0) && (st.getQuestItemsCount(DRYAD_TEARS) < 10))
			{
				htmltext = "30156-04.htm";
			}
			else if ((cond > 3) && (st.getQuestItemsCount(ALBERRYUS_LIST) > 0))
			{
				htmltext = "30156-07.htm";
			}
			else if ((cond == 3) && (st.getQuestItemsCount(EVERGREEN_AMULET) > 0) && (st.getQuestItemsCount(DRYAD_TEARS) >= 10))
			{
				st.takeItems(EVERGREEN_AMULET, 1);
				st.takeItems(DRYAD_TEARS, -1);
				st.giveItems(COBS_MEDICINE1, 1);
				st.giveItems(COBS_MEDICINE2, 1);
				st.giveItems(COBS_MEDICINE3, 1);
				st.giveItems(COBS_MEDICINE4, 1);
				st.giveItems(COBS_MEDICINE5, 1);
				st.set("cond", "4");
				htmltext = "30156-05.htm";
			}
			else if (cond == 4)
			{
				htmltext = "30156-06.htm";
			}
		}
		else if ((npcId == 30217) && (cond == 5) && (st.getQuestItemsCount(ALBERRYUS_LIST) == 1) && (st.getQuestItemsCount(COBS_MEDICINE2) == 1))
		{
			st.takeItems(COBS_MEDICINE2, 1);
			htmltext = "30217-01.htm";
			check(st);
		}
		else if ((npcId == 30219) && (cond == 5) && (st.getQuestItemsCount(ALBERRYUS_LIST) == 1) && (st.getQuestItemsCount(COBS_MEDICINE3) == 1))
		{
			st.takeItems(COBS_MEDICINE3, 1);
			htmltext = "30219-01.htm";
			check(st);
		}
		else if ((npcId == 30221) && (cond == 5) && (st.getQuestItemsCount(ALBERRYUS_LIST) == 1) && (st.getQuestItemsCount(COBS_MEDICINE4) == 1))
		{
			st.takeItems(COBS_MEDICINE4, 1);
			htmltext = "30221-01.htm";
			check(st);
		}
		else if ((npcId == 30285) && (cond == 5) && (st.getQuestItemsCount(ALBERRYUS_LIST) == 1) && (st.getQuestItemsCount(COBS_MEDICINE5) == 1))
		{
			st.takeItems(COBS_MEDICINE5, 1);
			htmltext = "30285-01.htm";
			check(st);
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		QuestState st = killer.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		int npcId = npc.getNpcId();
		
		if (((npcId == 20013) || (npcId == 20019)) && Rnd.chance(33))
		{
			if ((st.getQuestItemsCount(EVERGREEN_AMULET) > 0) && (st.getQuestItemsCount(DRYAD_TEARS) < 10))
			{
				st.giveItems(DRYAD_TEARS, 1);
				if (st.getQuestItemsCount(DRYAD_TEARS) == 10)
				{
					st.set("cond", "3");
					st.playSound("ItemSound.quest_middle");
				}
				else
				{
					st.playSound("ItemSound.quest_itemget");
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00102_FungusFever(102, Q00102_FungusFever.class.getSimpleName(), "Fungus Fever");
	}
}