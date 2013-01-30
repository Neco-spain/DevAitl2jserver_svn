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
package quests.Q00192_SevenSignSeriesOfDoubt;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

public class Q00192_SevenSignSeriesOfDoubt extends Quest
{
	// NPC
    private static final int CROOP 		= 30676;
    private static final int HECTOR 	= 30197;
    private static final int STAN 		= 30200;
    private static final int CORPSE 	= 32568;
    private static final int HOLLINT 	= 30191;

	// ITEMS
    private static final int CROOP_INTRO 	= 13813;
    private static final int JACOB_NECK 	= 13814;
    private static final int CROOP_LETTER 	= 13815;

    public Q00192_SevenSignSeriesOfDoubt(int questId, String name, String descr)
    {
       	super(questId, name, descr);

        addStartNpc(CROOP);
        addTalkId(CROOP);
        addTalkId(HECTOR);
        addTalkId(STAN);
        addTalkId(CORPSE);
        addTalkId(HOLLINT);

		questItemIds = new int[] { CROOP_INTRO, JACOB_NECK, CROOP_LETTER };
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
        if(npc.getNpcId() == CROOP)
        {
            if("30676-03.htm".equals(event))
            {
                st.setState(State.STARTED);
                st.set("cond", "1");
                st.playSound("ItemSound.quest_accept");
            } 
			else
            {
                if("8".equals(event))
                {
                    st.set("cond", "2");
                    st.playSound("ItemSound.quest_middle");
                   	player.showQuestMovie(8);
                    startQuestTimer("playertele", 32000, npc, player);
                    return "";
                }
                if("playertele".equals(event))
                {
                    player.teleToLocation(81654, 54848, -1514);
                    return "";
                }
                if("30676-12.htm".equals(event))
                {
                 	st.set("cond", "7");
                    st.takeItems(JACOB_NECK, 1);
                    st.giveItems(CROOP_LETTER, 1);
                    st.playSound("ItemSound.quest_middle");
                }
            }
        } 
		else if(npc.getNpcId() == HECTOR)
        {
            if("30197-03.htm".equals(event))
            {
               	st.set("cond", "4");
                st.takeItems(CROOP_INTRO, 1);
               	st.playSound("ItemSound.quest_middle");
            }
        }
		else if(npc.getNpcId() == STAN)
        {
            if("30200-04.htm".equals(event))
            {
                st.set("cond", "5");
                st.playSound("ItemSound.quest_middle");
            }
       	}
		else if(npc.getNpcId() == CORPSE)
        {
            if("32568-02.htm".equals(event))
            {
                st.set("cond", "6");
                st.giveItems(JACOB_NECK, 1);
                st.playSound("ItemSound.quest_middle");
            }
        }
		else if(npc.getNpcId() == HOLLINT)
		{
			if("30191-03.htm".equals(event))
			{
				st.takeItems(CROOP_LETTER, 1);
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
        if(st == null)
            return htmltext;
        if(npc.getNpcId() == CROOP)
		{
            switch(st.getState())
            {
            	case State.CREATED:
                	if(player.getLevel() >= 79)
                	{
                   		htmltext = "30676-01.htm";
                	}
					else
                	{
                    	htmltext = "30676-00.htm";
                    	st.exitQuest(true);
                	}
                	break;
            	case State.STARTED:
                	if(st.getInt("cond") == 1)
                   		htmltext = "30676-04.htm";
                	else if(st.getInt("cond") == 2)
                	{
                    	htmltext = "30676-05.htm";
                   	 	st.set("cond", "3");
                    	st.playSound("ItemSound.quest_middle");
                    	st.giveItems(CROOP_INTRO, 1);
                	}
					else if(st.getInt("cond") >= 3 && st.getInt("cond") <= 5)
                    	htmltext = "30676-06.htm";
                	else if(st.getInt("cond") == 6)
                    	htmltext = "30676-07.htm";
                	break;
            	case State.COMPLETED:
                	htmltext = "30676-13.htm";
                	break;
            }
		}		
       	 else if(npc.getNpcId() == HECTOR)
        {
            if(st.getInt("cond") == 3)
                htmltext = "30197-01.htm";
            else if(st.getInt("cond") >= 4 && st.getInt("cond") <= 7)
				htmltext = "30197-04.htm";
		}
		else if(npc.getNpcId() == STAN)
        {
			if(st.getInt("cond") == 4)
                htmltext = "30200-01.htm";
            else if(st.getInt("cond") >= 5 && st.getInt("cond") <= 7)
				htmltext = "30200-05.htm";
        }
		else if(npc.getNpcId() == CORPSE)
        {
            if(st.getInt("cond") == 0)
                htmltext = "32568-03.htm";
            else if(st.getInt("cond") >= 1 && st.getInt("cond") <= 4)
                htmltext = "32568-04.htm";
            else if(st.getInt("cond") == 5)
                htmltext = "32568-01.htm";
        } 
		else if(npc.getNpcId() == HOLLINT)
		{
			if(st.getInt("cond") == 7)
			{
            	htmltext = "30191-01.htm";
			}
		}			
        return htmltext;
    }

    public static void main(String args[])
    {
        new Q00192_SevenSignSeriesOfDoubt(192, Q00192_SevenSignSeriesOfDoubt.class.getSimpleName(), "Seven Signs, Series of Doubt");
    }
}