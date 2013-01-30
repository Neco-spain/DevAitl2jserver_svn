package quests.Q00104_SpiritOfMirrors;

import com.l2jserver.gameserver.customs.CustomMessage;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.itemcontainer.Inventory;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00104_SpiritOfMirrors extends Quest
{
	// QUEST ITEMS
	private final static int GALLINT_OAK_WAND = 748;
	private final static int WAND_SPIRITBOUND1 = 1135;
	private final static int WAND_SPIRITBOUND2 = 1136;
	private final static int WAND_SPIRITBOUND3 = 1137;
	
	// REWARDS
	private final static int LONG_SWORD = 2;
	private final static int WAND_OF_ADEPT = 747;
	private final static int SPIRITSHOT_NO_GRADE_FOR_BEGINNERS = 5790;
	private final static int SOULSHOT_NO_GRADE_FOR_BEGINNERS = 5789;
	private final static int SPIRITSHOT_NO_GRADE = 2509;
	private final static int SOULSHOT_NO_GRADE = 1835;
	private final static int LESSER_HEALING_POT = 1060;
	
	// NPC
	private final static int GALLINT = 30017;
	private final static int ARNOLD = 30041;
	private final static int JOHNSTONE = 30043;
	private final static int KENYOS = 30045;
	
	private final int[] talkNpc =
	{
		30017,
		30041,
		30043,
		30045,
	};
	
	private static final int[][] DROPLIST_COND =
	{
		{
			27003,
			WAND_SPIRITBOUND1
		},
		{
			27004,
			WAND_SPIRITBOUND2
		},
		{
			27005,
			WAND_SPIRITBOUND3
		}
	};
	
	public Q00104_SpiritOfMirrors(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(GALLINT);
		for (int npcId : talkNpc)
		{
			addTalkId(npcId);
		}
		
		for (int[] element : DROPLIST_COND)
		{
			addKillId(element[0]);
		}
		
		questItemIds = new int[]
		{
			WAND_SPIRITBOUND1,
			WAND_SPIRITBOUND2,
			WAND_SPIRITBOUND3,
			GALLINT_OAK_WAND
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
		
		if (event.equalsIgnoreCase("30017-03.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
			st.giveItems(GALLINT_OAK_WAND, 3);
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
		int cond = st.getInt("cond");
		int id = st.getState();
		
		if ((id == State.CREATED) && (cond == 0))
		{
			if (npcId == GALLINT)
			{
				if (st.getPlayer().getRace().ordinal() != 0)
				{
					htmltext = "30017-00.htm";
					st.exitQuest(true);
				}
				else if (st.getPlayer().getLevel() >= 10)
				{
					htmltext = "30017-02.htm";
					return htmltext;
				}
				else
				{
					htmltext = "30017-06.htm";
					st.exitQuest(true);
				}
			}
		}
		else if (id == State.STARTED)
		{
			if (npcId == GALLINT)
			{
				if (((cond == 1) && (st.getQuestItemsCount(GALLINT_OAK_WAND) >= 1) && (st.getQuestItemsCount(WAND_SPIRITBOUND1) == 0)) || (st.getQuestItemsCount(WAND_SPIRITBOUND2) == 0) || (st.getQuestItemsCount(WAND_SPIRITBOUND3) == 0))
				{
					htmltext = "30017-04.htm";
				}
				else if ((cond == 3) && (st.getQuestItemsCount(WAND_SPIRITBOUND1) == 1) && (st.getQuestItemsCount(WAND_SPIRITBOUND2) == 1) && (st.getQuestItemsCount(WAND_SPIRITBOUND3) == 1))
				{
					st.takeItems(WAND_SPIRITBOUND1, 1);
					st.takeItems(WAND_SPIRITBOUND2, 1);
					st.takeItems(WAND_SPIRITBOUND3, 1);
					st.giveItems(LESSER_HEALING_POT, 100);
					for (int ECHO_CHRYSTAL = 4412; ECHO_CHRYSTAL <= 4416; ECHO_CHRYSTAL++)
					{
						st.giveItems(ECHO_CHRYSTAL, 10);
					}
					if (st.getPlayer().getClassId().isMage())
					{
						st.giveItems(SPIRITSHOT_NO_GRADE_FOR_BEGINNERS, 3000);
						st.giveItems(SPIRITSHOT_NO_GRADE, 500);
						st.giveItems(WAND_OF_ADEPT, 1);
						player.sendPacket(new ExShowScreenMessage(((new CustomMessage("Newbie.Message3", player.getLang())).toString()), 3000));
					}
					else
					{
						st.giveItems(SOULSHOT_NO_GRADE_FOR_BEGINNERS, 6000);
						st.giveItems(SOULSHOT_NO_GRADE, 1000);
						st.giveItems(LONG_SWORD, 1);
					}
					st.addExpAndSp(39750, 3407);
					st.giveItems(57, 16866);
					htmltext = "30017-05.htm";
					st.unset("cond");
					st.exitQuest(false);
					st.playSound("ItemSound.quest_finish");
				}
			}
			else if ((npcId == ARNOLD) || (npcId == JOHNSTONE) || ((npcId == KENYOS) && (cond >= 1)))
			{
				st.set("cond", "2");
				st.playSound("ItemSound.quest_middle");
				htmltext = npcId + "-01.htm";
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
		for (int[] element : DROPLIST_COND)
		{
			if ((st.getInt("cond") >= 1) && (st.getItemEquipped(Inventory.PAPERDOLL_RHAND) == GALLINT_OAK_WAND) && (npcId == element[0]) && (st.getQuestItemsCount(element[1]) == 0))
			{
				st.takeItems(GALLINT_OAK_WAND, 1);
				st.giveItems(element[1], 1);
				
				long HaveAllQuestItems = st.getQuestItemsCount(WAND_SPIRITBOUND1) + st.getQuestItemsCount(WAND_SPIRITBOUND2) + st.getQuestItemsCount(WAND_SPIRITBOUND3);
				if (HaveAllQuestItems == 3)
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
		new Q00104_SpiritOfMirrors(104, Q00104_SpiritOfMirrors.class.getSimpleName(), "Spirit Of Mirrors");
	}
}