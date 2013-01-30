package quests.Q00022_TragedyInVonHellmannForest;

import java.util.ArrayList;
import java.util.List;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.util.Rnd;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00022_TragedyInVonHellmannForest extends Quest
{
	private final static int Well = 31527;
	private final static int Tifaren = 31334;
	private final static int Innocentin = 31328;
	private final static int SoulOfWell = 27217;
	private final static int GhostOfPriest = 31528;
	private final static int GhostOfAdventurer = 31529;
	// Items
	private final static int ReportBox = 7147;
	private final static int LostSkullOfElf = 7142;
	public final int CrossOfEinhasad = 7141;
	private final static int SealedReportBox = 7146;
	private final static int LetterOfInnocentin = 7143;
	private final static int JewelOfAdventurerRed = 7145;
	private final static int JewelOfAdventurerGreen = 7144;
	// Monsters
	private static final List<Integer> Mobs = new ArrayList<>();
	static
	{
		for (int i : new int[]
		{
			21547,
			21548,
			21549,
			21550,
			21551,
			21552,
			21553,
			21554,
			21555,
			21556,
			21557,
			21558,
			21559,
			21560,
			21561,
			21562,
			21563,
			21564,
			21565,
			21566,
			21567,
			21568,
			21569,
			21570,
			21571,
			21572,
			21573,
			21574,
			21575,
			21576,
			21577,
			21578
		})
		{
			Mobs.add(i);
		}
	}
	
	private static L2Npc GhostOfPriestInstance = null;
	private static L2Npc SoulOfWellInstance = null;
	
	public Q00022_TragedyInVonHellmannForest(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(Tifaren);
		
		addTalkId(Innocentin);
		addTalkId(Tifaren);
		addTalkId(GhostOfPriest);
		addTalkId(GhostOfAdventurer);
		addTalkId(Well);
		
		addKillId(SoulOfWell);
		
		for (int npcId : Mobs)
		{
			addKillId(npcId);
		}
		
		questItemIds = new int[]
		{
			LostSkullOfElf,
			ReportBox,
			SealedReportBox,
			LetterOfInnocentin,
			JewelOfAdventurerRed,
			JewelOfAdventurerGreen
		};
	}
	
	private void spawnGhostOfPriest(QuestState st)
	{
		GhostOfPriestInstance = st.addSpawn(GhostOfPriest, st.getPlayer().getX(), st.getPlayer().getY(), st.getPlayer().getZ(), Rnd.get(50, 100), true, 0);
	}
	
	private void spawnSoulOfWell(QuestState st)
	{
		SoulOfWellInstance = st.addSpawn(SoulOfWell, st.getPlayer().getX(), st.getPlayer().getY(), st.getPlayer().getZ(), Rnd.get(50, 100), true, 0);
	}
	
	private void despawnGhostOfPriest()
	{
		if (GhostOfPriestInstance != null)
		{
			GhostOfPriestInstance.deleteMe();
		}
	}
	
	private void despawnSoulOfWell()
	{
		if (SoulOfWellInstance != null)
		{
			SoulOfWellInstance.deleteMe();
		}
	}
	
	protected void AutoChat(L2Npc npc, String text)
	{
		npc.broadcastNpcSay(text);
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
		
		if (event.equalsIgnoreCase("31334-02.htm"))
		{
			st.set("cond", "1");
		}
		if (event.equalsIgnoreCase("31334-04.htm"))
		{
			st.set("cond", "3");
		}
		else if (event.equalsIgnoreCase("31334-08.htm"))
		{
			st.set("cond", "4");
		}
		else if (event.equalsIgnoreCase("31334-13.htm"))
		{
			st.set("cond", "6");
			st.takeItems(LostSkullOfElf, 1);
			despawnGhostOfPriest();
			spawnGhostOfPriest(st);
			st.playSound("ItemSound.quest_middle");
			AutoChat(GhostOfPriestInstance, "Did you call me, " + st.getPlayer().getName() + "?");
		}
		else if (event.equalsIgnoreCase("31528-09.htm"))
		{
			despawnGhostOfPriest();
			st.set("cond", "8");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31328-10.htm"))
		{
			st.set("cond", "9");
			st.giveItems(LetterOfInnocentin, 1);
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31529-03.htm"))
		{
			st.takeItems(LetterOfInnocentin, 1);
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31529-15.htm"))
		{
			if (st.getQuestItemsCount(JewelOfAdventurerGreen) < 1)
			{
				st.giveItems(JewelOfAdventurerGreen, 1);
			}
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31527-02.htm"))
		{
			st.set("cond", "10");
			despawnSoulOfWell();
			spawnSoulOfWell(st);
			st.takeItems(JewelOfAdventurerGreen, -1);
			st.takeItems(JewelOfAdventurerRed, -1);
			st.giveItems(JewelOfAdventurerGreen, 1);
			st.playSound("ItemSound.quest_middle");
			st.startQuestTimer("attack_timer", 300000, SoulOfWellInstance);
			((L2Attackable) SoulOfWellInstance).addDamageHate(player, 0, 99999);
			SoulOfWellInstance.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, st.getPlayer(), true);
		}
		else if (event.equalsIgnoreCase("attack_timer"))
		{
			despawnSoulOfWell();
			st.takeItems(JewelOfAdventurerGreen, -1);
			st.giveItems(JewelOfAdventurerRed, 1);
			st.set("cond", "11");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("31328-13.htm"))
		{
			st.startQuestTimer("wait_timer", 60000);
			st.set("cond", "15");
			st.takeItems(ReportBox, 1);
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("wait_timer"))
		{
			st.set("cond", "16");
		}
		else if (event.equalsIgnoreCase("31328-21.htm"))
		{
			st.startQuestTimer("next_wait_timer", 30000);
			st.set("cond", "17");
		}
		else if (event.equalsIgnoreCase("next_wait_timer"))
		{
			st.set("cond", "18");
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		QuestState st2 = player.getQuestState("Q00021_HiddenTruth");
		
		String htmltext = getNoQuestMsg(player);
		if (st == null)
		{
			return htmltext;
		}
		
		if (st.getState() == State.COMPLETED)
		{
			htmltext = Quest.getAlreadyCompletedMsg(player);
			return htmltext;
		}
		
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		
		if (npcId == Innocentin)
		{
			if (st.getState() == State.COMPLETED)
			{
				htmltext = Quest.getAlreadyCompletedMsg(player);
				return htmltext;
			}
			else if (st.getState() == State.CREATED)
			{
				if ((st2 != null) && (st2.getState() == State.COMPLETED))
				{
					if (st.getPlayer().getLevel() >= 63)
					{
						htmltext = "31328-00.htm";
					}
					else
					{
						htmltext = "31334-03.htm";
						st.exitQuest(true);
					}
				}
				else
				{
					htmltext = "<html><body>Quest Light and Darkness need to be finished first.</body></html>";
					st.exitQuest(true);
				}
			}
			else if (cond == 8)
			{
				htmltext = "31328-03.htm";
			}
			else if (cond == 9)
			{
				htmltext = "31328-10.htm";
			}
			else if (cond == 14)
			{
				if (st.getQuestItemsCount(ReportBox) != 0)
				{
					htmltext = "31328-12.htm";
				}
				else
				{
					st.set("cond", "13");
					htmltext = "Go away!";
				}
			}
			else if (cond == 15)
			{
				htmltext = "<html><body>I must open it carefully and see what it holds. Why don't you come back a little later?</body></html>";
			}
			else if (cond == 16)
			{
				htmltext = "31328-14.htm";
			}
			else if (cond == 17)
			{
				htmltext = "<html><body>I had my doubts about Lidia. If she was resurrected, how did she become Lord of the Forest of the Dead? Why not Alfred?</body></html>";
			}
			else if (cond == 18)
			{
				htmltext = "31328-17.htm";
				st.playSound("ItemSound.quest_finish");
				st.addExpAndSp(345966, 31578);
				st.unset("cond");
				st.setState(State.COMPLETED);
				st.exitQuest(false);
				if (player.getLevel() < 64)
				{
					htmltext = "31328-23.htm";
				}
				else
				{
					htmltext = "31328-22.htm";
				}
			}
		}
		else if (npcId == Tifaren)
		{
			if (cond == 0)
			{
				if (player.getLevel() < 63)
				{
					htmltext = "31334-03.htm";
					st.exitQuest(true);
				}
				else if (st.getQuestItemsCount(CrossOfEinhasad) != 0)
				{
					st.setState(State.STARTED);
					st.playSound("ItemSound.quest_accept");
					htmltext = "31334-01.htm";
				}
			}
			else if (cond == 1)
			{
				htmltext = "31334-02.htm";
			}
			else if (cond == 3)
			{
				htmltext = "31334-05.htm";
			}
			else if (cond == 4)
			{
				htmltext = "31334-08.htm";
			}
			else if (cond == 5)
			{
				if (st.getQuestItemsCount(LostSkullOfElf) != 0)
				{
					htmltext = "31334-10.htm";
				}
				else
				{
					st.set("cond", "4");
					htmltext = "31334-06.htm";
				}
			}
			else if (cond == 6)
			{
				despawnGhostOfPriest();
				spawnGhostOfPriest(st);
				htmltext = "31334-13.htm";
			}
		}
		else if (npcId == GhostOfPriest)
		{
			if (cond == 6)
			{
				htmltext = "31528-01.htm";
			}
			else if (cond == 8)
			{
				htmltext = "31528-08.htm";
			}
		}
		else if (npcId == GhostOfAdventurer)
		{
			if (cond == 9)
			{
				if (st.getQuestItemsCount(LetterOfInnocentin) != 0)
				{
					htmltext = "31529-01.htm";
				}
				else
				{
					htmltext = "You have no Letter of Innocentin! Are they Please returned to High Priest Innocentin...";
				}
			}
			else if (cond == 11)
			{
				if (st.getQuestItemsCount(JewelOfAdventurerRed) != 0)
				{
					if (st.getQuestItemsCount(SealedReportBox) == 0)
					{
						htmltext = "31529-17.htm";
					}
					st.set("cond", "12");
					st.playSound("ItemSound.quest_middle");
				}
				else
				{
					st.set("cond", "9");
					htmltext = "31529-09.htm";
				}
			}
			else if (cond == 13)
			{
				if (st.getQuestItemsCount(SealedReportBox) != 0)
				{
					htmltext = "31529-18.htm";
					st.set("cond", "14");
					st.takeItems(SealedReportBox, 1);
					st.giveItems(ReportBox, 1);
					st.playSound("ItemSound.quest_middle");
				}
				else
				{
					st.set("cond", "12");
					htmltext = "31529-10.htm";
				}
			}
		}
		else if (npcId == Well)
		{
			if ((cond == 9) && (st.getQuestItemsCount(JewelOfAdventurerGreen) != 0))
			{
				htmltext = "31527-01.htm";
			}
			else if ((cond == 12) && (st.getQuestItemsCount(SealedReportBox) == 0))
			{
				htmltext = "31527-05.htm";
				st.takeItems(JewelOfAdventurerRed, 1);
				st.giveItems(SealedReportBox, 1);
				st.set("cond", "13");
				st.playSound("ItemSound.quest_middle");
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		
		if (npcId == SoulOfWell)
		{
			if (cond == 10)
			{
				st.set("cond", "9");
				st.takeItems(JewelOfAdventurerGreen, -1);
				st.takeItems(JewelOfAdventurerRed, -1);
				if (st.getQuestTimer("attack_timer") != null)
				{
					st.getQuestTimer("attack_timer").cancel();
				}
			}
		}
		else if (Mobs.contains(npcId))
		{
			if ((cond == 4) && (Rnd.get(10) < 1) && (st.getQuestItemsCount(LostSkullOfElf) < 1))
			{
				st.giveItems(LostSkullOfElf, 1);
				st.playSound("ItemSound.quest_middle");
				st.set("cond", "5");
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00022_TragedyInVonHellmannForest(22, Q00022_TragedyInVonHellmannForest.class.getSimpleName(), "Tragedy In VonHellmann Forest");
	}
}