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
package quests.Q00193_SevenSignDyingMessage;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;

public class Q00193_SevenSignDyingMessage extends Quest
{

	// NPC
    private static final int HOLLINT 	= 30191;
    private static final int CAIN 		= 32569;
    private static final int ERIC 		= 32570;
    private static final int ATHEBALDT 	= 30760;

	// MOB
    private static final int SHILENSEVIL 	= 27343;

	// ITEMS
    private static final int JACOB_NECK 	= 13814;
    private static final int DEADMANS_HERB 	= 13816;
    private static final int SCULPTURE 	= 14353;

    private boolean ShilensevilOnSpawn;

    public Q00193_SevenSignDyingMessage(int questId, String name, String descr)
    {
        super(questId, name, descr);

        ShilensevilOnSpawn = false;

        addStartNpc(HOLLINT);
        addTalkId(HOLLINT);
        addTalkId(CAIN);
        addTalkId(ERIC);
        addTalkId(ATHEBALDT);

        addKillId(SHILENSEVIL);
		questItemIds = new int[] { JACOB_NECK, DEADMANS_HERB, SCULPTURE };
    }

    @Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
    {
       	String htmltext = event;
        QuestState st = player.getQuestState(getName());
        if(st == null)
		{
          	return htmltext;
		}	
        if(npc.getNpcId() == HOLLINT)
        {
           	if("30191-02.htm".equals(event))
            {
                st.setState(State.STARTED);
                st.set("cond", "1");
                st.giveItems(JACOB_NECK, 1);
                st.playSound("ItemSound.quest_accept");
            }
        }
		else if(npc.getNpcId() == CAIN)
        {
            if("32569-05.htm".equals(event))
            {
                st.set("cond", "2");
                st.takeItems(JACOB_NECK, 1);
                st.playSound("ItemSound.quest_middle");
            }
			else
            {
                if("9".equals(event))
                {
                    st.takeItems(DEADMANS_HERB, 1);
                    st.set("cond", "4");
                    st.playSound("ItemSound.quest_middle");
                   	player.showQuestMovie(9);
                   	return "";
               	}
               	if("32569-09.htm".equals(event))
                {
					if(ShilensevilOnSpawn)
                   	{
                       	htmltext = getNoQuestMsg(player);
                    }
					else
					{
						npc.broadcastPacket(new NpcSay(npc.getObjectId(), 0, npc.getNpcId(), "! That stranger must be defeated!"));
						L2MonsterInstance monster = (L2MonsterInstance)addSpawn(SHILENSEVIL, 82624, 47422, -3220, 0, false, 300000, true);
						monster.broadcastPacket(new NpcSay(monster.getObjectId(), 0, monster.getNpcId(), "You are not the owner of that item!!"));
						monster.setRunning();
						monster.addDamageHate(player, 0, 999);
						monster.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, st.getPlayer());
						ShilensevilOnSpawn = true;
						startQuestTimer("spawnS", 301000, npc, player);
						startQuestTimer("aiplayer", 30000, npc, player);
						startQuestTimer("stopaiplayer", 301000, npc, player);
					}
                }
				else
                {
                   	if("spawnS".equals(event))
                   	{
                       	ShilensevilOnSpawn = false;
                       	return "";
                   	}
                   	if("aiplayer".equals(event))
                   	{
                       	npc.setTarget(player);
                   	 	npc.doCast(SkillTable.getInstance().getInfo(1011, 18));
                       	startQuestTimer("aiplayer", 30000, npc, player);
                        return "";
                    }
                    if("stopaiplayer".equals(event))
                    {
                        cancelQuestTimer("aiplayer", npc, player);
                        return "";
                    }
                    if("32569-13.htm".equals(event))
                    {
                       	st.set("cond", "6");
                       	st.takeItems(SCULPTURE, 1);
						st.playSound("ItemSound.quest_middle");
                    }
                }
            }
        }
		else if(npc.getNpcId() == ERIC)
        {
           	if("32570-02.htm".equals(event))
           	{
               	st.set("cond", "3");
               	st.giveItems(DEADMANS_HERB, 1);
          	 	st.playSound("ItemSound.quest_middle");
           	}
       	}
		else if(npc.getNpcId() == ATHEBALDT)
       	{
			if("30760-02.htm".equals(event))
			{
				st.addExpAndSp(25000000, 2500000);
				st.exitQuest(false);
				st.playSound("ItemSound.quest_finish");
			}	
        }
		return htmltext;
    }

  	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
    {
  		String htmltext = getNoQuestMsg(player);
  		QuestState st = player.getQuestState(getName());
        QuestState first = player.getQuestState("Q00192_SevenSignSeriesOfDoubt");
        if(st == null)
		{
          	return htmltext;
		}	
        if(npc.getNpcId() == HOLLINT)
		{
           	switch(st.getState())
            {
            	case State.CREATED:
                	if(first != null && first.getState() == State.COMPLETED && player.getLevel() >= 79)
                	{
                   		htmltext = "30191-01.htm";
                	}
					else
                	{
                    	htmltext = "30191-00.htm";
                    	st.exitQuest(true);
                	}
                break;
            	case State.STARTED:
                	if(st.getInt("cond") == 1)
                    	htmltext = "30191-03.htm";
                break;
            	case State.COMPLETED:
                	htmltext = getAlreadyCompletedMsg(player);
                break;
			}
		}
        else if(npc.getNpcId() == CAIN)
        {
           	if(st.getState() == State.STARTED)
			{
				switch(st.getInt("cond"))
				{
					case 1:
						htmltext = "32569-01.htm";
					break;
					case 2:
						htmltext = "32569-06.htm";
					break;
					case 3:
						htmltext = "32569-07.htm";
					break;
					case 4:
						htmltext = "32569-08.htm";
					break;
					case 5:
						htmltext = "32569-10.htm";
					break;
				}
			}	
        }
		else if(npc.getNpcId() == ERIC)
        {
           	if(st.getState() == State.STARTED)
			{
				switch(st.getInt("cond"))
				{
					case 2:
						htmltext = "32570-01.htm";
					break;
					case 3:
						htmltext = "32570-03.htm";
					break;
				}
			}	
        }
		else if(npc.getNpcId() == ATHEBALDT)
		{
			if(st.getInt("cond") == 6)
				htmltext = "30760-01.htm";
		}	
        return htmltext;
    }

    @Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
    {
       	QuestState st = player.getQuestState(getName());
       	if(st == null)
		{
           	return null;
		}	
       	if(npc.getNpcId() == SHILENSEVIL && st.getInt("cond") == 4)
       	{
			npc.broadcastPacket(new NpcSay(npc.getObjectId(), 0, npc.getNpcId(), "... You may have won this time... But next time, I will surely capture you!"));
           	st.giveItems(SCULPTURE, 1);
           	st.set("cond", "5");
           	st.playSound("ItemSound.quest_middle");
           	ShilensevilOnSpawn = false;
           	cancelQuestTimer("aiplayer", npc, player);
       	}
       	return null;
   	}

   	public static void main(String args[])
   	{
       	new Q00193_SevenSignDyingMessage(193, Q00193_SevenSignDyingMessage.class.getSimpleName(), "Seven Signs, Dying Message");
    }
}