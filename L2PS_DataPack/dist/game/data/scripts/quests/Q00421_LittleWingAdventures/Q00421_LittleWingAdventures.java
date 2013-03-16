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
package quests.Q00421_LittleWingAdventures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PetInstance;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.model.skills.L2Skill;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00421_LittleWingAdventures extends Quest
{
	private final static int CRONOS = 30610;
	private final static int MIMYU = 30747;
	private final static int WIND = 27185;
	private final static int STAR = 27186;
	private final static int TWILIGHT = 27187;
	private final static int ABYSS = 27188;
	private final static int DF_WIND = 3500;
	private final static int DF_STAR = 3501;
	private final static int DF_TWILIGHT = 3502;
	private final static int FT_LEAF = 4325;
	private final static int GUARDIAN = 27189;
	
	Map<Integer, List<Integer>> killedTrees = null;
	Map<Integer, Map<Integer, List<L2Npc>>> treesGuards = null;
	
	private static final int[] TREES =
	{
		27185,
		27186,
		27187,
		27188
	};
	
	public Q00421_LittleWingAdventures(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(CRONOS);
		
		addTalkId(CRONOS);
		addTalkId(MIMYU);
		addTalkId(WIND);
		addTalkId(STAR);
		addTalkId(TWILIGHT);
		addTalkId(ABYSS);
		
		addAttackId(WIND);
		addAttackId(STAR);
		addAttackId(TWILIGHT);
		addAttackId(ABYSS);
		
		for (int mobId : TREES)
		{
			addAttackId(mobId);
		}
		
		this.killedTrees = new HashMap<>();
		this.treesGuards = new HashMap<>();
		questItemIds = new int[]
		{
			FT_LEAF
		};
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(getName());
		
		if (st == null)
		{
			return null;
		}
		
		long WCount = st.getQuestItemsCount(DF_WIND);
		long SCount = st.getQuestItemsCount(DF_STAR);
		long TCount = st.getQuestItemsCount(DF_TWILIGHT);
		int summonOId = st.getInt("summonOid");
		
		L2ItemInstance item = null;
		L2PetInstance summon = (L2PetInstance) player.getSummon();
		
		if (event.equals("30610-05.htm"))
		{
			if ((WCount + SCount + TCount) == 1)
			{
				if (WCount == 1)
				{
					item = player.getInventory().getItemByItemId(DF_WIND);
				}
				else if (SCount == 1)
				{
					item = player.getInventory().getItemByItemId(DF_STAR);
				}
				else if (TCount == 1)
				{
					item = player.getInventory().getItemByItemId(DF_TWILIGHT);
				}
				if (item.getEnchantLevel() < 55)
				{
					st.exitQuest(true);
					htmltext = "30610-06.htm";
				}
				else
				{
					st.setState(State.STARTED);
					st.set("summonOid", String.valueOf(item.getObjectId()));
					st.set("cond", "1");
					st.set("id", "1");
					st.playSound("ItemSound.quest_accept");
				}
			}
			else
			{
				st.exitQuest(true);
				htmltext = "30610-06.htm";
			}
		}
		else if (event.equals("30747-02.htm"))
		{
			if (summon != null)
			{
				htmltext = summon.getControlItem().getObjectId() == summonOId ? "30747-04.htm" : "30747-03.htm";
			}
		}
		else if (event.equals("30747-05.htm"))
		{
			if (summon != null)
			{
				if (summon.getControlItem().getObjectId() == summonOId)
				{
					htmltext = "30747-05.htm";
					st.giveItems(FT_LEAF, 4);
					st.set("cond", "2");
					st.set("id", "0");
					st.playSound("ItemSound.quest_middle");
				}
				else
				{
					htmltext = "30747-06.htm";
				}
			}
			else
			{
				htmltext = "30747-06.htm";
			}
		}
		
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		String htmltext = Quest.getNoQuestMsg(player);
		
		if (st == null)
		{
			return htmltext;
		}
		
		int state = st.getState();
		int id = st.getInt("id");
		int summonOId = st.getInt("summonOid");
		int npcId = npc.getNpcId();
		
		long WCount = st.getQuestItemsCount(DF_WIND);
		long SCount = st.getQuestItemsCount(DF_STAR);
		long TCount = st.getQuestItemsCount(DF_TWILIGHT);
		long LCount = st.getQuestItemsCount(FT_LEAF);
		
		L2ItemInstance item = null;
		L2PetInstance summon = (L2PetInstance) player.getSummon();
		
		if ((state == State.CREATED) && (npcId == CRONOS))
		{
			if ((player.getLevel() < 45) && ((WCount > 0) || (SCount > 0) || (TCount > 0)))
			{
				st.exitQuest(true);
				htmltext = "30610-01.htm";
			}
			else if ((player.getLevel() >= 45) && ((WCount + SCount + TCount) >= 2))
			{
				st.exitQuest(true);
				htmltext = "30610-02.htm";
			}
			else if ((player.getLevel() >= 45) && ((WCount + SCount + TCount) == 1))
			{
				if (WCount == 1)
				{
					item = player.getInventory().getItemByItemId(DF_WIND);
				}
				else if (SCount == 1)
				{
					item = player.getInventory().getItemByItemId(DF_STAR);
				}
				else if (TCount == 1)
				{
					item = player.getInventory().getItemByItemId(DF_TWILIGHT);
				}
				if (item != null)
				{
					htmltext = item.getEnchantLevel() < 55 ? "30610-03.htm" : "30610-04.htm";
				}
			}
		}
		else if (state == State.STARTED)
		{
			if (npcId == CRONOS)
			{
				htmltext = "30610-07.htm";
			}
			else if (npcId == MIMYU)
			{
				if (id == 1)
				{
					st.set("id", "2");
					htmltext = "30747-01.htm";
				}
				else if (id == 2)
				{
					htmltext = summon != null ? (summon.getControlItem().getObjectId() == summonOId ? "30747-04.htm" : "30747-03.htm") : "30747-02.htm";
				}
				else if (id == 0)
				{
					htmltext = "30747-07.htm";
				}
				else if ((id > 0) && (id < 15) && (LCount >= 1))
				{
					htmltext = "30747-11.htm";
				}
				else if ((id == 15) && (LCount == 0))
				{
					if (summon != null)
					{
						if (summon.getControlItem().getObjectId() == summonOId)
						{
							st.set("id", "16");
							htmltext = "30747-13.htm";
						}
						else
						{
							htmltext = "30747-14.htm";
						}
					}
					else
					{
						htmltext = "30747-12.htm";
					}
				}
				else if (id == 16)
				{
					if (summon != null)
					{
						htmltext = "30747-15.htm";
					}
					else if ((WCount + SCount + TCount) == 1)
					{
						if (WCount == 1)
						{
							item = player.getInventory().getItemByItemId(DF_WIND);
						}
						else if (SCount == 1)
						{
							item = player.getInventory().getItemByItemId(DF_STAR);
						}
						else if (TCount == 1)
						{
							item = player.getInventory().getItemByItemId(DF_TWILIGHT);
						}
						if (item != null)
						{
							if (item.getObjectId() == summonOId)
							{
								st.takeItems(item.getItemId(), 1);
								st.giveItems(item.getItemId() + 922, 1);
								htmltext = "30747-16.htm";
								st.exitQuest(true);
								st.playSound("ItemSound.quest_finish");
							}
							else
							{
								npc.setTarget(player);
								L2Skill skill = SkillTable.getInstance().getInfo(4167, 1);
								if (skill != null)
								{
									skill.getEffects(npc, player);
									htmltext = "30747-18.htm";
								}
							}
						}
						else
						{
							htmltext = "30747-18.htm";
						}
					}
					else if ((WCount + SCount + TCount) >= 2)
					{
						htmltext = "30747-17.htm";
					}
				}
			}
		}
		
		return htmltext;
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isPet)
	{
		int npcId = npc.getNpcId();
		QuestState st = attacker.getQuestState(getName());
		
		if (st == null)
		{
			return super.onAttack(npc, attacker, damage, isPet);
		}
		
		if (killedTrees.get(attacker.getObjectId()) == null)
		{
			killedTrees.put(attacker.getObjectId(), new ArrayList<Integer>());
		}
		
		if (killedTrees.get(attacker.getObjectId()).contains(npcId))
		{
			return super.onAttack(npc, attacker, damage, isPet);
		}
		
		if (treesGuards.get(attacker.getObjectId()) == null)
		{
			treesGuards.put(attacker.getObjectId(), new HashMap<Integer, List<L2Npc>>());
			
			for (int mobId : TREES)
			{
				List<L2Npc> npcs = new ArrayList<>();
				L2Spawn temp = SpawnTable.getInstance().getSpawnsByNpcId(mobId).get(0); // it only have 1 spawn
				for (int i = 0; i < 3; i++)
				{
					npcs.add(addSpawn(GUARDIAN, temp.getLocx(), temp.getLocy(), temp.getLocz(), temp.getHeading(), true, 0));
				}
				treesGuards.get(attacker.getObjectId()).put(mobId, npcs);
			}
		}
		
		if (treesGuards.get(attacker.getObjectId()).containsKey(npcId))
		{
			for (L2Npc temp : treesGuards.get(attacker.getObjectId()).get(npcId))
			{
				L2Attackable newNpc = (L2Attackable) temp;
				newNpc.setRunning();
				newNpc.addDamageHate(attacker, 0, 99999);
				newNpc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
			}
		}
		
		if (isPet && (st.getInt("id") < 16))
		{
			if ((st.getRandom(100) <= 2) && (st.getQuestItemsCount(FT_LEAF) > 0))
			{
				st.takeItems(FT_LEAF, 1);
				st.playSound("ItemSound.quest_middle");
				npc.broadcastNpcSay("gives me spirit leaf...!");
				killedTrees.get(attacker.getObjectId()).add(npcId);
				if (st.getQuestItemsCount(FT_LEAF) == 0)
				{
					killedTrees.remove(attacker.getObjectId());
					treesGuards.remove(attacker.getObjectId());
					st.set("id", "15");
					st.set("cond", "3");
				}
			}
		}
		return super.onAttack(npc, attacker, damage, isPet);
	}
	
	public static void main(String[] args)
	{
		new Q00421_LittleWingAdventures(421, Q00421_LittleWingAdventures.class.getSimpleName(), "Little Wing's Big Adventures");
	}
}
