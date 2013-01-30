package quests.Q00107_MercilessPunishment;

import com.l2jserver.gameserver.customs.CustomMessage;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;
import com.l2jserver.gameserver.network.serverpackets.SocialAction;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00107_MercilessPunishment extends Quest
{
	// QuestItem
	private static final int HATOSS_ORDER1_ID = 1553;
	private static final int HATOSS_ORDER2_ID = 1554;
	private static final int HATOSS_ORDER3_ID = 1555;
	private static final int LETTER_TO_HUMAN_ID = 1557;
	private static final int LETTER_TO_DARKELF_ID = 1556;
	private static final int LETTER_TO_ELF_ID = 1558;
	private static final int BUTCHER_ID = 1510;
	private static final int LESSER_HEALING_ID = 1060;
	private static final int CRYSTAL_BATTLE = 4412;
	private static final int CRYSTAL_LOVE = 4413;
	private static final int CRYSTAL_SOLITUDE = 4414;
	private static final int CRYSTAL_FEAST = 4415;
	private static final int CRYSTAL_CELEBRATION = 4416;
	
	// REWARDS
	private final static int SPIRITSHOT_FOR_BEGINNERS = 5790;
	private final static int SOULSHOT_FOR_BEGINNERS = 5789;
	
	public Q00107_MercilessPunishment(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(30568);
		addTalkId(30568);
		addTalkId(30580);
		
		addKillId(27041);
		
		questItemIds = new int[]
		{
			HATOSS_ORDER2_ID,
			LETTER_TO_DARKELF_ID,
			LETTER_TO_HUMAN_ID,
			LETTER_TO_ELF_ID,
			HATOSS_ORDER1_ID,
			HATOSS_ORDER3_ID
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
		
		if (event.equalsIgnoreCase("1"))
		{
			st.set("id", "0");
			htmltext = "30568-03.htm";
			st.giveItems(HATOSS_ORDER1_ID, 1);
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("30568_1"))
		{
			htmltext = "30568-06.htm";
			st.exitQuest(true);
			st.playSound("ItemSound.quest_giveup");
		}
		else if (event.equalsIgnoreCase("30568_2"))
		{
			htmltext = "30568-07.htm";
			st.takeItems(HATOSS_ORDER1_ID, 1);
			if (st.getQuestItemsCount(HATOSS_ORDER2_ID) == 0)
			{
				st.giveItems(HATOSS_ORDER2_ID, 1);
			}
		}
		else if (event.equalsIgnoreCase("30568_3"))
		{
			htmltext = "30568-06.htm";
			st.exitQuest(true);
			st.playSound("ItemSound.quest_giveup");
		}
		else if (event.equalsIgnoreCase("30568_4"))
		{
			htmltext = "30568-09.htm";
			st.takeItems(HATOSS_ORDER2_ID, 1);
			if (st.getQuestItemsCount(HATOSS_ORDER3_ID) == 0)
			{
				st.giveItems(HATOSS_ORDER3_ID, 1);
			}
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
			htmltext = getAlreadyCompletedMsg(player);
		}
		
		int npcId = npc.getNpcId();
		int id = st.getState();
		
		if ((npcId == 30568) && (id == State.CREATED))
		{
			if (player.getRace().ordinal() != 3)
			{
				htmltext = "30568-00.htm";
				st.exitQuest(true);
			}
			else if (player.getLevel() >= 10)
			{
				htmltext = "30568-02.htm";
			}
			else
			{
				htmltext = "30568-01.htm";
				st.exitQuest(true);
			}
		}
		else if ((npcId == 30568) && (st.getInt("cond") == 1) && (st.getQuestItemsCount(HATOSS_ORDER1_ID) > 0) && (st.getQuestItemsCount(LETTER_TO_HUMAN_ID) == 0))
		{
			htmltext = "30568-04.htm";
		}
		else if ((npcId == 30568) && (st.getInt("cond") == 1) && (st.getQuestItemsCount(HATOSS_ORDER1_ID) > 0) && (st.getQuestItemsCount(LETTER_TO_HUMAN_ID) >= 1))
		{
			htmltext = "30568-05.htm";
		}
		else if ((npcId == 30568) && (st.getInt("cond") == 1) && (st.getQuestItemsCount(HATOSS_ORDER2_ID) > 0) && (st.getQuestItemsCount(LETTER_TO_DARKELF_ID) >= 1))
		{
			htmltext = "30568-08.htm";
		}
		else if ((npcId == 30568) && (st.getInt("cond") == 1) && (st.getQuestItemsCount(HATOSS_ORDER3_ID) > 0) && ((st.getQuestItemsCount(LETTER_TO_ELF_ID) + st.getQuestItemsCount(LETTER_TO_HUMAN_ID) + st.getQuestItemsCount(LETTER_TO_DARKELF_ID)) == 3))
		{
			if (st.getInt("id") != 107)
			{
				st.set("id", "107");
				htmltext = "30568-10.htm";
				st.takeItems(LETTER_TO_DARKELF_ID, 1);
				st.takeItems(LETTER_TO_HUMAN_ID, 1);
				st.takeItems(LETTER_TO_ELF_ID, 1);
				st.takeItems(HATOSS_ORDER3_ID, 1);
				st.giveItems(57, 14666);
				st.giveItems(LESSER_HEALING_ID, 100);
				st.giveItems(BUTCHER_ID, 1);
				st.giveItems(CRYSTAL_BATTLE, 10);
				st.giveItems(CRYSTAL_LOVE, 10);
				st.giveItems(CRYSTAL_SOLITUDE, 10);
				st.giveItems(CRYSTAL_FEAST, 10);
				st.giveItems(CRYSTAL_CELEBRATION, 10);
				st.addExpAndSp(34565, 2962);
				if (!player.getClassId().isMage())
				{
					st.giveItems(SPIRITSHOT_FOR_BEGINNERS, 3000);
					st.playTutorialVoice("tutorial_voice_027");
				}
				else
				{
					st.giveItems(SOULSHOT_FOR_BEGINNERS, 7000);
					st.playTutorialVoice("tutorial_voice_026");
				}
				st.unset("cond");
				player.sendPacket(new ExShowScreenMessage(((new CustomMessage("Newbie.Message3", player.getLang())).toString()), 3000));
				player.sendPacket(new SocialAction(player.getObjectId(), 3));
				st.exitQuest(false);
				st.playSound("ItemSound.quest_finish");
			}
		}
		else if (((npcId == 30580) && (st.getInt("cond") == 1) && (id == State.STARTED) && (st.getQuestItemsCount(HATOSS_ORDER1_ID) > 0)) || (st.getQuestItemsCount(HATOSS_ORDER2_ID) > 0) || (st.getQuestItemsCount(HATOSS_ORDER3_ID) > 0))
		{
			htmltext = "30580-01.htm";
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
		
		if (npcId == 27041)
		{
			st.set("id", "0");
			
			if ((cond == 1) && (st.getQuestItemsCount(HATOSS_ORDER1_ID) > 0) && (st.getQuestItemsCount(LETTER_TO_HUMAN_ID) == 0))
			{
				st.giveItems(LETTER_TO_HUMAN_ID, 1);
				st.playSound("ItemSound.quest_itemget");
			}
			
			if ((cond == 1) && (st.getQuestItemsCount(HATOSS_ORDER2_ID) > 0) && (st.getQuestItemsCount(LETTER_TO_DARKELF_ID) == 0))
			{
				st.giveItems(LETTER_TO_DARKELF_ID, 1);
				st.playSound("ItemSound.quest_itemget");
			}
			
			if ((cond == 1) && (st.getQuestItemsCount(HATOSS_ORDER3_ID) > 0) && (st.getQuestItemsCount(LETTER_TO_ELF_ID) == 0))
			{
				st.giveItems(LETTER_TO_ELF_ID, 1);
				st.playSound("ItemSound.quest_itemget");
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00107_MercilessPunishment(107, Q00107_MercilessPunishment.class.getSimpleName(), "Merciless Punishment");
	}
}