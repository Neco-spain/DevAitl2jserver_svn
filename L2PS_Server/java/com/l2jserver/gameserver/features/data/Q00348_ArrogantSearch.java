/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jserver.gameserver.features.data;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.model.skills.L2Skill;

import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00348_ArrogantSearch extends Quest
{
	private static final int YINTZU = 20647;
	private static final int PALIOTE = 20648;
	private static final int ARK_GUARDIAN_ELBEROTH = 27182;
	private static final int ARK_GUARDIAN_SHADOWFANG = 27183;
	private static final int ANGEL_KILLER = 27184;
	private static final int PLATINUM_TRIBE_SHAMAN = 20828;
	private static final int PLATINUM_TRIBE_OVERLORD = 20829;
	private static final int GUARDIAN_ANGEL_1 = 20830;
	private static final int GUARDIAN_ANGEL_2 = 20859;
	private static final int SEAL_ANGEL_1 = 20831;
	private static final int SEAL_ANGEL_2 = 20860;
	private static final int HANELLIN = 30864;
	private static final int HOLY_ARK_OF_SECRECY_1 = 30977;
	private static final int HOLY_ARK_OF_SECRECY_2 = 30978;
	private static final int HOLY_ARK_OF_SECRECY_3 = 30979;
	private static final int ARK_GUARDIANS_CORPSE = 30980;
	private static final int HARNE = 30144;
	private static final int CLAUDIA_ATHEBALT = 31001;
	private static final int MARTIEN = 30645;
	private static final int GUSTAV_ATHEBALDT = 30760;
	private static final int HARDIN = 30832;
	private static final int HEINE = 30969;
	private static final int SHELL_OF_MONSTERS = 14857;
	private static final int HANELLINS_FIRST_LETTER = 4288;
	private static final int HANELLINS_SECOND_LETTER = 4289;
	private static final int HANELLINS_THIRD_LETTER = 4290;
	private static final int FIRST_KEY_OF_ARK = 4291;
	private static final int SECOND_KEY_OF_ARK = 4292;
	private static final int THIRD_KEY_OF_ARK = 4293;
	private static final int WHITE_FABRIC_1 = 4294;
	private static final int BLOODED_FABRIC = 4295;
	private static final int BOOK_OF_SAINT = 4397;
	private static final int BLOOD_OF_SAINT = 4398;
	private static final int BRANCH_OF_SAINT = 4399;
	private static final int WHITE_FABRIC_0 = 4400;
	private static final int ANTIDOTE = 1831;
	private static final int HEALING_POTION = 1061;
	private static final int ANIMAL_BONE = 1872;
	private static final int SYNTHETIC_COKES = 1888;
	private static final int ADENA = 57;
	private static final TIntObjectHashMap<Object[]> ARKS = new TIntObjectHashMap<>();
	private static final TIntObjectHashMap<Object[]> ARK_OWNERS = new TIntObjectHashMap<>();
	private static final TIntObjectHashMap<Object[]> BLOODY_OWNERS = new TIntObjectHashMap<>();
	private static final TIntObjectHashMap<Object[]> DROPS = new TIntObjectHashMap<>();
	private static final TIntObjectHashMap<Object[]> DROPS_29 = new TIntObjectHashMap<>();
	private static final TIntObjectHashMap<Object[]> ATTACK_DROPS_24 = new TIntObjectHashMap<>();
	private static final TIntObjectHashMap<Object[]> ATTACK_DROPS_25 = new TIntObjectHashMap<>();
	
	public Q00348_ArrogantSearch(int id, String name, String descr)
	{
		super(id, name, descr);
		
		addStartNpc(HANELLIN);
		addTalkId(HANELLIN);
		addTalkId(ARK_GUARDIANS_CORPSE);
		
		addAttackId(PLATINUM_TRIBE_SHAMAN);
		addAttackId(PLATINUM_TRIBE_OVERLORD);
		
		ARK_OWNERS.put(HARNE, new Object[]
		{
			HANELLINS_FIRST_LETTER,
			BLOOD_OF_SAINT,
			"30144-01.htm",
			"30144-02.htm",
			"30144-03.htm",
			new int[]
			{
				-418,
				44174,
				-3568
			}
		});
		ARK_OWNERS.put(CLAUDIA_ATHEBALT, new Object[]
		{
			HANELLINS_SECOND_LETTER,
			BOOK_OF_SAINT,
			"31001-01.htm",
			"31001-02.htm",
			"31001-03.htm",
			new int[]
			{
				181472,
				7158,
				-2725
			}
		});
		ARK_OWNERS.put(MARTIEN, new Object[]
		{
			HANELLINS_THIRD_LETTER,
			BRANCH_OF_SAINT,
			"30645-01.htm",
			"30645-02.htm",
			"30645-03.htm",
			new int[]
			{
				50693,
				158674,
				376
			}
		});
		ARKS.put(HOLY_ARK_OF_SECRECY_1, new Object[]
		{
			FIRST_KEY_OF_ARK,
			0,
			"30977-01.htm",
			"30977-02.htm",
			"30977-03.htm",
			BLOOD_OF_SAINT
		});
		ARKS.put(HOLY_ARK_OF_SECRECY_2, new Object[]
		{
			SECOND_KEY_OF_ARK,
			ARK_GUARDIAN_ELBEROTH,
			"That doesn't belong to you.  Don't touch it!",
			"30978-02.htm",
			"30978-03.htm",
			BOOK_OF_SAINT
		});
		ARKS.put(HOLY_ARK_OF_SECRECY_3, new Object[]
		{
			THIRD_KEY_OF_ARK,
			ARK_GUARDIAN_SHADOWFANG,
			"Get off my sight, you infidels!",
			"30979-02.htm",
			"30979-03.htm",
			BRANCH_OF_SAINT
		});
		BLOODY_OWNERS.put(GUSTAV_ATHEBALDT, new Object[]
		{
			3,
			"athebaldt_delivery",
			"30760-01.htm",
			"30760-01a.htm",
			"30760-01b.htm"
		});
		BLOODY_OWNERS.put(HARDIN, new Object[]
		{
			1,
			"hardin_delivery",
			"30832-01.htm",
			"30832-01a.htm",
			"30832-01b.htm"
		});
		BLOODY_OWNERS.put(HEINE, new Object[]
		{
			6,
			"heine_delivery",
			"30969-01.htm",
			"30969-01a.htm",
			"30969-01b.htm"
		});
		DROPS.put(YINTZU, new Object[]
		{
			2,
			SHELL_OF_MONSTERS,
			1,
			10,
			0
		});
		DROPS.put(PALIOTE, new Object[]
		{
			2,
			SHELL_OF_MONSTERS,
			1,
			10,
			0
		});
		DROPS.put(ANGEL_KILLER, new Object[]
		{
			5,
			FIRST_KEY_OF_ARK,
			1,
			100,
			0
		});
		DROPS.put(ARK_GUARDIAN_ELBEROTH, new Object[]
		{
			5,
			SECOND_KEY_OF_ARK,
			1,
			100,
			0
		});
		DROPS.put(ARK_GUARDIAN_SHADOWFANG, new Object[]
		{
			5,
			THIRD_KEY_OF_ARK,
			1,
			100,
			0
		});
		DROPS.put(PLATINUM_TRIBE_SHAMAN, new Object[]
		{
			25,
			BLOODED_FABRIC,
			1,
			10,
			WHITE_FABRIC_1
		});
		DROPS.put(PLATINUM_TRIBE_OVERLORD, new Object[]
		{
			25,
			BLOODED_FABRIC,
			1,
			10,
			WHITE_FABRIC_1
		});
		DROPS.put(GUARDIAN_ANGEL_1, new Object[]
		{
			26,
			BLOODED_FABRIC,
			10,
			25,
			WHITE_FABRIC_1
		});
		DROPS.put(GUARDIAN_ANGEL_2, new Object[]
		{
			26,
			BLOODED_FABRIC,
			10,
			25,
			WHITE_FABRIC_1
		});
		DROPS.put(SEAL_ANGEL_1, new Object[]
		{
			26,
			BLOODED_FABRIC,
			10,
			25,
			WHITE_FABRIC_1
		});
		DROPS.put(SEAL_ANGEL_2, new Object[]
		{
			26,
			BLOODED_FABRIC,
			10,
			25,
			WHITE_FABRIC_1
		});
		DROPS_29.put(GUARDIAN_ANGEL_1, new Object[]
		{
			29,
			BLOODED_FABRIC,
			10,
			25,
			WHITE_FABRIC_1
		});
		DROPS_29.put(GUARDIAN_ANGEL_2, new Object[]
		{
			29,
			BLOODED_FABRIC,
			10,
			25,
			WHITE_FABRIC_1
		});
		DROPS_29.put(SEAL_ANGEL_1, new Object[]
		{
			29,
			BLOODED_FABRIC,
			10,
			25,
			WHITE_FABRIC_1
		});
		DROPS_29.put(SEAL_ANGEL_2, new Object[]
		{
			29,
			BLOODED_FABRIC,
			10,
			25,
			WHITE_FABRIC_1
		});
		ATTACK_DROPS_24.put(PLATINUM_TRIBE_SHAMAN, new Object[]
		{
			24,
			BLOODED_FABRIC,
			1,
			2,
			WHITE_FABRIC_1
		});
		ATTACK_DROPS_24.put(PLATINUM_TRIBE_OVERLORD, new Object[]
		{
			24,
			BLOODED_FABRIC,
			1,
			2,
			WHITE_FABRIC_1
		});
		ATTACK_DROPS_25.put(PLATINUM_TRIBE_SHAMAN, new Object[]
		{
			25,
			BLOODED_FABRIC,
			1,
			2,
			WHITE_FABRIC_1
		});
		ATTACK_DROPS_25.put(PLATINUM_TRIBE_OVERLORD, new Object[]
		{
			25,
			BLOODED_FABRIC,
			1,
			2,
			WHITE_FABRIC_1
		});
		
		for (int i : ARK_OWNERS.keys())
		{
			addTalkId(i);
		}
		for (int i : ARKS.keys())
		{
			addTalkId(i);
		}
		for (int i : BLOODY_OWNERS.keys())
		{
			addTalkId(i);
		}
		for (int i : DROPS.keys())
		{
			addKillId(i);
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
		
		if (event.equalsIgnoreCase("30864-02c.htm"))
		{
			st.setState(State.STARTED);
			st.set("cond", "2");
			st.set("reward1", "0");
			st.set("athebaldt_delivery", "0");
			st.set("hardin_delivery", "0");
			st.set("heine_delivery", "0");
		}
		else if (event.equalsIgnoreCase("30864_04a"))
		{
			st.set("cond", "4");
			htmltext = "30864-04c.htm";
			st.set("companions", "0");
		}
		else if (event.equalsIgnoreCase("30864_04b"))
		{
			st.set("cond", "3");
			st.set("companions", "1");
			st.takeItems(SHELL_OF_MONSTERS, -1);
			htmltext = "not yet implemented";
		}
		else if (event.equalsIgnoreCase("30864_07"))
		{
			htmltext = "30864-07b.htm";
		}
		else if (event.equalsIgnoreCase("30864_07b"))
		{
			htmltext = "30864-07c.htm";
		}
		else if (event.equalsIgnoreCase("30864_07c"))
		{
			htmltext = "30864-07d.htm";
		}
		else if (event.equalsIgnoreCase("30864_07meet"))
		{
			htmltext = "30864-07meet.htm";
			st.set("cond", "24");
		}
		else if (event.equalsIgnoreCase("30864_07money"))
		{
			htmltext = "30864-07money.htm";
			st.set("cond", "25");
		}
		else if (event.equalsIgnoreCase("30864_08"))
		{
			htmltext = "30864-08b.htm";
		}
		else if (event.equalsIgnoreCase("30864_08b"))
		{
			htmltext = "30864-08c.htm";
			st.giveItems(WHITE_FABRIC_1, 9);
			st.set("cond", "26");
		}
		else if (event.equalsIgnoreCase("30864_09"))
		{
			st.set("cond", "27");
			htmltext = "30864-09c.htm";
		}
		else if (event.equalsIgnoreCase("30864_10continue"))
		{
			htmltext = "30864-08c.htm";
			st.giveItems(WHITE_FABRIC_1, 10);
			st.set("athebaldt_delivery", "0");
			st.set("hardin_delivery", "0");
			st.set("heine_delivery", "0");
			st.set("cond", "29");
		}
		else if (event.equalsIgnoreCase("30864_10quit"))
		{
			htmltext = "30864-10c.htm";
			st.takeItems(WHITE_FABRIC_1, -1);
			st.takeItems(BLOODED_FABRIC, -1);
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(true);
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
		
		if ((npc.getNpcId() != HANELLIN) & (st.getState() != State.STARTED))
		{
			return htmltext;
		}
		
		int cond = st.getInt("cond");
		int reward1 = st.getInt("reward1");
		
		if (npc.getNpcId() == HANELLIN)
		{
			if (st.getState() == State.CREATED)
			{
				if (st.getQuestItemsCount(BLOODED_FABRIC) == 1)
				{
					htmltext = "30864-Baium.htm";
					st.exitQuest(true);
				}
				else
				{
					if (player.getLevel() < 60)
					{
						st.exitQuest(true);
						htmltext = "30864-01.htm";
					}
					else if (cond == 0)
					{
						htmltext = "30864-02.htm";
					}
					else if (cond == 1)
					{
						htmltext = "30864-02.htm";
					}
				}
			}
			else if (cond == 2)
			{
				if (st.getQuestItemsCount(SHELL_OF_MONSTERS) == 0)
				{
					htmltext = "30864-03.htm";
				}
				else
				{
					st.takeItems(SHELL_OF_MONSTERS, -1);
					htmltext = "30864-04.htm";
				}
			}
			else if (cond == 4)
			{
				st.set("cond", "5");
				st.giveItems(HANELLINS_FIRST_LETTER, 1);
				st.giveItems(HANELLINS_SECOND_LETTER, 1);
				st.giveItems(HANELLINS_THIRD_LETTER, 1);
				htmltext = "30864-05.htm";
			}
			else if ((cond == 5) && ((st.getQuestItemsCount(BOOK_OF_SAINT) + st.getQuestItemsCount(BLOOD_OF_SAINT) + st.getQuestItemsCount(BRANCH_OF_SAINT)) < 3))
			{
				htmltext = "30864-05.htm";
			}
			else if (cond == 5)
			{
				htmltext = "30864-06.htm";
				st.takeItems(BOOK_OF_SAINT, -1);
				st.takeItems(BLOOD_OF_SAINT, -1);
				st.takeItems(BRANCH_OF_SAINT, -1);
				st.set("cond", "22");
			}
			else if ((cond == 22) && (st.getQuestItemsCount(ANTIDOTE) < 5) && (st.getQuestItemsCount(HEALING_POTION) < 1))
			{
				htmltext = "30864-06a.htm";
			}
			else if ((cond == 22) && (st.getQuestItemsCount(WHITE_FABRIC_1) > 0))
			{
				htmltext = "30864-07c.htm";
			}
			else if (cond == 22)
			{
				st.takeItems(ANTIDOTE, 5);
				st.takeItems(HEALING_POTION, 1);
				if (st.getInt("companions") == 0)
				{
					htmltext = "30864-07.htm";
					st.giveItems(WHITE_FABRIC_1, 1);
				}
				else
				{
					st.set("cond", "23");
					htmltext = "not implemented yet";
					st.giveItems(WHITE_FABRIC_0, 3);
				}
			}
			else if ((cond == 24) && (st.getQuestItemsCount(BLOODED_FABRIC) < 1))
			{
				htmltext = "30864-07a.htm";
			}
			else if ((cond == 25) && (st.getQuestItemsCount(BLOODED_FABRIC) < 1))
			{
				htmltext = "30864-07a.htm";
			}
			else if ((cond == 25) && (reward1 > 0))
			{
				htmltext = "30864-08b.htm";
			}
			else if (cond == 25)
			{
				htmltext = "30864-08.htm";
				st.giveItems(ANIMAL_BONE, 2);
				st.giveItems(SYNTHETIC_COKES, 2);
				int lowbgrade = getRandom(10) + 4103;
				st.giveItems(lowbgrade, 1);
				st.set("reward1", "1");
			}
			else if ((cond == 26) && (st.getQuestItemsCount(WHITE_FABRIC_1) > 0))
			{
				htmltext = "30864-09a.htm";
			}
			else if ((cond == 26) && (st.getQuestItemsCount(BLOODED_FABRIC) < 10))
			{
				htmltext = "30864-09b.htm";
				st.giveItems(ADENA, 5000);
				st.takeItems(BLOODED_FABRIC, -1);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(true);
			}
			else if (cond == 26)
			{
				htmltext = "30864-09.htm";
			}
			else if ((cond == 27) && ((st.getInt("athebaldt_delivery") + st.getInt("hardin_delivery") + st.getInt("heine_delivery")) < 3))
			{
				htmltext = "30864-10a.htm";
			}
			else if (cond == 27)
			{
				htmltext = "30864-10.htm";
				st.giveItems(ANIMAL_BONE, 5);
				int highbgrade = getRandom(8) + 4113;
				st.giveItems(highbgrade, 1);
				st.set("cond", "28");
			}
			else if (cond == 28)
			{
				htmltext = "30864-10b.htm";
			}
			else if ((cond == 29) && (st.getQuestItemsCount(WHITE_FABRIC_1) > 0))
			{
				htmltext = "30864-09a.htm";
			}
			else if ((cond == 29) && (st.getQuestItemsCount(BLOODED_FABRIC) < 10))
			{
				htmltext = "30864-09b.htm";
				st.giveItems(ADENA, 5000);
				st.takeItems(BLOODED_FABRIC, -1);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(true);
			}
			else if (cond == 29)
			{
				htmltext = "30864-09.htm";
			}
		}
		else if (cond == 5)
		{
			int npcId = npc.getNpcId();
			if (ARK_OWNERS.containsKey(npcId))
			{
				if (st.getQuestItemsCount((Integer) ARK_OWNERS.get(npc.getNpcId())[0]) == 1)
				{
					st.takeItems((Integer) ARK_OWNERS.get(npc.getNpcId())[0], 1);
					htmltext = (String) ARK_OWNERS.get(npcId)[2];
					int[] i = (int[]) ARK_OWNERS.get(npcId)[5];
					st.addRadar(i[0], i[1], i[2]);
				}
				else if (st.getQuestItemsCount((Integer) ARK_OWNERS.get(npcId)[1]) < 1)
				{
					htmltext = (String) ARK_OWNERS.get(npcId)[3];
					int[] i = (int[]) ARK_OWNERS.get(npcId)[5];
					st.addRadar(i[0], i[1], i[2]);
				}
				else
				{
					htmltext = (String) ARK_OWNERS.get(npcId)[4];
				}
			}
			else if (ARKS.containsKey(npcId))
			{
				if (st.getQuestItemsCount((Integer) ARKS.get(npcId)[0]) == 0)
				{
					if ((Integer) ARKS.get(npcId)[1] != 0)
					{
						st.addSpawn((Integer) ARKS.get(npcId)[1], 120000);
					}
					return (String) ARKS.get(npcId)[2];
				}
				else if (st.getQuestItemsCount((Integer) ARKS.get(npcId)[5]) == 1)
				{
					htmltext = (String) ARKS.get(npcId)[4];
				}
				else
				{
					htmltext = (String) ARKS.get(npcId)[3];
					st.takeItems((Integer) ARKS.get(npcId)[0], 1);
					st.giveItems((Integer) ARKS.get(npcId)[5], 1);
				}
			}
			else if (npcId == ARK_GUARDIANS_CORPSE)
			{
				if ((st.getQuestItemsCount(FIRST_KEY_OF_ARK) == 0) && (st.getInt("angelKillerIsDefeated") == 0))
				{
					st.addSpawn(ANGEL_KILLER, 120000);
					htmltext = "30980-01.htm";
				}
				else if ((st.getQuestItemsCount(FIRST_KEY_OF_ARK) == 0) && (st.getInt("angelKillerIsDefeated") == 1))
				{
					st.giveItems(FIRST_KEY_OF_ARK, 1);
					htmltext = "30980-02.htm";
				}
				else
				{
					htmltext = "30980-03.htm";
				}
			}
		}
		else if (cond == 27)
		{
			int npcId = npc.getNpcId();
			if (BLOODY_OWNERS.containsKey(npcId))
			{
				if (st.getInt((String) BLOODY_OWNERS.get(npcId)[1]) < 1)
				{
					if (st.getQuestItemsCount(BLOODED_FABRIC) >= (Integer) BLOODY_OWNERS.get(npcId)[0]) // deliver blood
					{
						st.takeItems(BLOODED_FABRIC, (Integer) BLOODY_OWNERS.get(npcId)[0]);
						st.set((String) BLOODY_OWNERS.get(npcId)[1], "1");
						htmltext = (String) BLOODY_OWNERS.get(npcId)[2];
					}
					else
					{
						htmltext = (String) BLOODY_OWNERS.get(npcId)[3];
					}
				}
				else
				{
					htmltext = (String) BLOODY_OWNERS.get(npcId)[4];
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance player, int damage, boolean isPet, L2Skill skill)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		if (st.getState() != State.STARTED)
		{
			return null;
		}
		
		int npcId = npc.getNpcId();
		
		if (ATTACK_DROPS_24.containsKey(npcId))
		{
			int cond = (Integer) ATTACK_DROPS_24.get(npcId)[0];
			int chance = (Integer) ATTACK_DROPS_24.get(npcId)[3];
			if ((st.getInt("cond") == cond) && (getRandom(1000) < chance) && (st.getQuestItemsCount((Integer) ATTACK_DROPS_24.get(npcId)[4]) > 0)) // Attack drops are low chance
			{
				st.giveItems((Integer) ATTACK_DROPS_24.get(npcId)[1], (Integer) ATTACK_DROPS_24.get(npcId)[2]);
				st.playSound("ItemSound.quest_itemget");
				st.takeItems((Integer) ATTACK_DROPS_24.get(npcId)[4], 1);
				if (cond == 24)
				{
					st.playSound("ItemSound.quest_finish");
					st.exitQuest(true);
				}
			}
		}
		else if (ATTACK_DROPS_25.containsKey(npcId))
		{
			int cond = (Integer) ATTACK_DROPS_25.get(npcId)[0];
			int chance = (Integer) ATTACK_DROPS_25.get(npcId)[3];
			if ((st.getInt("cond") == cond) && (getRandom(1000) < chance) && (st.getQuestItemsCount((Integer) ATTACK_DROPS_25.get(npcId)[4]) > 0)) // Attack drops are low chance
			{
				st.giveItems((Integer) ATTACK_DROPS_25.get(npcId)[1], (Integer) ATTACK_DROPS_25.get(npcId)[2]);
				st.playSound("ItemSound.quest_itemget");
				st.takeItems((Integer) ATTACK_DROPS_25.get(npcId)[4], 1);
				if (cond == 24)
				{
					st.playSound("ItemSound.quest_finish");
					st.exitQuest(true);
				}
			}
		}
		return null;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		if (st.getState() != State.STARTED)
		{
			return null;
		}
		
		int npcId = npc.getNpcId();
		
		if (DROPS.containsKey(npcId))
		{
			int cond = (Integer) DROPS.get(npcId)[0];
			
			if ((st.getInt("cond") == cond) && (st.getQuestItemsCount((Integer) DROPS.get(npcId)[1]) < (Integer) DROPS.get(npcId)[2]) && (getRandom(100) < (Integer) DROPS.get(npcId)[3]) && (((Integer) DROPS.get(npcId)[4] == 0) || (st.getQuestItemsCount((Integer) DROPS.get(npcId)[4]) > 0)))
			{
				st.giveItems((Integer) DROPS.get(npcId)[1], 1);
				st.playSound("ItemSound.quest_itemget");
				if ((Integer) DROPS.get(npcId)[4] != 0)
				{
					st.takeItems((Integer) DROPS.get(npcId)[4], 1);
				}
			}
			
			if (cond == 24)
			{
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(true);
			}
		}
		
		if (DROPS_29.containsKey(npcId))
		{
			int cond = (Integer) DROPS_29.get(npcId)[0];
			if ((st.getInt("cond") == cond) && (st.getQuestItemsCount((Integer) DROPS_29.get(npcId)[1]) < (Integer) DROPS.get(npcId)[2]) && (getRandom(100) < (Integer) DROPS.get(npcId)[3]) && (((Integer) DROPS.get(npcId)[4] == 0) || (st.getQuestItemsCount((Integer) DROPS.get(npcId)[4]) > 0)))
			{
				st.giveItems((Integer) DROPS_29.get(npcId)[1], 1);
				st.playSound("ItemSound.quest_itemget");
				if ((Integer) DROPS_29.get(npcId)[4] != 0)
				{
					st.takeItems((Integer) DROPS_29.get(npcId)[4], 1);
				}
			}
		}
		if (npcId == ANGEL_KILLER)
		{
			return "Awesome !";
		}
		
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00348_ArrogantSearch(348, Q00348_ArrogantSearch.class.getSimpleName(), "Arrogant Search");
	}
}