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
package com.l2jserver.gameserver.model.entity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;

import javolution.util.FastMap;

import com.l2jserver.Config;
import com.l2jserver.gameserver.Announcements;
import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

public class VoteEvent
{
	private static String hopzoneUrl = Config.HOPZONE_SERVER_LINK;
	private static String page1Url = Config.HOPZONE_FIRST_PAGE_LINK;
	private static int voteRewardVotesDifference = Config.HOPZONE_VOTES_DIFFERENCE;
	private static int firstPageRankNeeded = Config.HOPZONE_FIRST_PAGE_RANK_NEEDED;
	private static int checkTime = 60 * 1000 * Config.HOPZONE_REWARD_CHECK_TIME;
	private static int lastVotes = 0;
	private static FastMap<String, Integer> playerIps = new FastMap<>();
	
	public static void updateConfigurations()
	{
		hopzoneUrl = Config.HOPZONE_SERVER_LINK;
		page1Url = Config.HOPZONE_FIRST_PAGE_LINK;
		voteRewardVotesDifference = Config.HOPZONE_VOTES_DIFFERENCE;
		firstPageRankNeeded = Config.HOPZONE_FIRST_PAGE_RANK_NEEDED;
		checkTime = 60 * 1000 * Config.HOPZONE_REWARD_CHECK_TIME;
	}
	
	public static void getInstance()
	{
		System.out.println("Vote reward system initialized.");
		ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new Runnable()
		{
			@Override
			public void run()
			{
				if (Config.ALLOW_HOPZONE_VOTE_REWARD)
				{
					reward();
				}
				else
				{
					return;
				}
			}
		}, checkTime / 2, checkTime);
	}
	
	static void reward()
	{
		int firstPageVotes = getFirstPageRankVotes();
		int currentVotes = getVotes();
		
		if ((firstPageVotes == -1) || (currentVotes == -1))
		{
			if (firstPageVotes == -1)
			{
				System.out.println("There was a problem on getting votes from server with rank " + firstPageRankNeeded + ".");
			}
			if (currentVotes == -1)
			{
				System.out.println("There was a problem on getting server votes.");
			}
			
			return;
		}
		
		if (lastVotes == 0)
		{
			lastVotes = currentVotes;
			Announcements.getInstance().announceToAll("Vote reward: Current vote count is " + currentVotes + ".");
			Announcements.getInstance().announceToAll("Vote reward: We need " + ((lastVotes + voteRewardVotesDifference) - currentVotes) + " vote(s) for reward.");
			if (Config.ALLOW_HOPZONE_GAME_SERVER_REPORT)
			{
				System.out.println("Server votes on hopzone: " + currentVotes);
				System.out.println("Votes needed for reward: " + ((lastVotes + voteRewardVotesDifference) - currentVotes));
			}
			if ((firstPageVotes - lastVotes) <= 0)
			{
				Announcements.getInstance().announceToAll("Vote reward: We are in the first page of Hopzone, so the reward will be big.");
				if (Config.ALLOW_HOPZONE_GAME_SERVER_REPORT)
				{
					System.out.println("Server is on the first page of hopzone.");
				}
			}
			else
			{
				Announcements.getInstance().announceToAll("Vote reward: We need " + (firstPageVotes - lastVotes) + " vote(s) to get to the first page of Hopzone for big reward.");
				if (Config.ALLOW_HOPZONE_GAME_SERVER_REPORT)
				{
					System.out.println("Server votes needed for first page: " + (firstPageVotes - lastVotes));
				}
			}
			Announcements.getInstance().announceToAll("Vote reward: Type .menu to see what the big and what the small reward is.");
			return;
		}
		
		if (currentVotes >= (lastVotes + voteRewardVotesDifference))
		{
			Collection<L2PcInstance> pls = L2World.getInstance().getAllPlayers().valueCollection();
			if ((firstPageVotes - currentVotes) <= 0)
			{
				if (Config.ALLOW_HOPZONE_GAME_SERVER_REPORT)
				{
					System.out.println("Server votes on hopzone: " + currentVotes);
					System.out.println("Server is on the first page of hopzone.");
					System.out.println("Votes needed for next reward: " + ((currentVotes + voteRewardVotesDifference) - currentVotes));
				}
				Announcements.getInstance().announceToAll("Vote reward: Everyone has been rewarded with big reward.");
				Announcements.getInstance().announceToAll("Vote reward: Current vote count is " + currentVotes + ".");
				Announcements.getInstance().announceToAll("Vote reward: Type .menu to see what the big and what the small reward is.");
				for (L2PcInstance p : pls)
				{
					boolean canReward = false;
					String pIp = p.getClient().getConnection().getInetAddress().getHostAddress();
					if (playerIps.containsKey(pIp))
					{
						int count = playerIps.get(pIp);
						if (count < Config.HOPZONE_DUALBOXES_ALLOWED)
						{
							playerIps.remove(pIp);
							playerIps.put(pIp, count + 1);
							canReward = true;
						}
					}
					else
					{
						canReward = true;
						playerIps.put(pIp, 1);
					}
					if (canReward)
					{
						for (int i : Config.HOPZONE_BIG_REWARD.keySet())
						{
							p.addItem("Vote reward.", i, Config.HOPZONE_BIG_REWARD.get(i), p, true);
						}
					}
					else
					{
						p.sendMessage("Already " + Config.HOPZONE_DUALBOXES_ALLOWED + " character(s) of your ip have been rewarded, so this character won't be rewarded.");
					}
				}
				playerIps.clear();
			}
			else
			{
				if (Config.ALLOW_HOPZONE_GAME_SERVER_REPORT)
				{
					System.out.println("Server votes on hopzone: " + currentVotes);
					System.out.println("Server votes needed for first page: " + (firstPageVotes - lastVotes));
					System.out.println("Votes needed for next reward: " + ((currentVotes + voteRewardVotesDifference) - currentVotes));
				}
				Announcements.getInstance().announceToAll("Vote reward: Everyone has been rewarded with small reward.");
				Announcements.getInstance().announceToAll("Vote reward: Current vote count is " + currentVotes + ".");
				Announcements.getInstance().announceToAll("Vote reward: We need " + (firstPageVotes - currentVotes) + " vote(s) to get to the first page of Hopzone for big reward.");
				Announcements.getInstance().announceToAll("Vote reward: Type .menu to see what the big and what the small reward is.");
				for (L2PcInstance p : pls)
				{
					boolean canReward = false;
					String pIp = p.getClient().getConnection().getInetAddress().getHostAddress();
					if (playerIps.containsKey(pIp))
					{
						int count = playerIps.get(pIp);
						if (count < Config.HOPZONE_DUALBOXES_ALLOWED)
						{
							playerIps.remove(pIp);
							playerIps.put(pIp, count + 1);
							canReward = true;
						}
					}
					else
					{
						canReward = true;
						playerIps.put(pIp, 1);
					}
					if (canReward)
					{
						for (int i : Config.HOPZONE_SMALL_REWARD.keySet())
						{
							p.addItem("Vote reward.", i, Config.HOPZONE_SMALL_REWARD.get(i), p, true);
						}
					}
					else
					{
						p.sendMessage("Already " + Config.HOPZONE_DUALBOXES_ALLOWED + " character(s) of your ip have been rewarded, so this character won't be rewarded.");
					}
				}
				playerIps.clear();
			}
			
			lastVotes = currentVotes;
		}
		else
		{
			if ((firstPageVotes - currentVotes) <= 0)
			{
				if (Config.ALLOW_HOPZONE_GAME_SERVER_REPORT)
				{
					System.out.println("Server votes on hopzone: " + currentVotes);
					System.out.println("Server is on the first page of hopzone.");
					System.out.println("Votes needed for next reward: " + ((lastVotes + voteRewardVotesDifference) - currentVotes));
				}
				Announcements.getInstance().announceToAll("Vote reward: Current vote count is " + currentVotes + ".");
				Announcements.getInstance().announceToAll("Vote reward: We need " + ((lastVotes + voteRewardVotesDifference) - currentVotes) + " vote(s) for big reward.");
				Announcements.getInstance().announceToAll("Vote reward: Type .menu to see what the big and what the small reward is.");
			}
			else
			{
				if (Config.ALLOW_HOPZONE_GAME_SERVER_REPORT)
				{
					System.out.println("Server votes on hopzone: " + currentVotes);
					System.out.println("Server votes needed for first page: " + (firstPageVotes - lastVotes));
					System.out.println("Votes needed for next reward: " + ((lastVotes + voteRewardVotesDifference) - currentVotes));
				}
				Announcements.getInstance().announceToAll("Vote reward: Current vote count is " + currentVotes + ".");
				Announcements.getInstance().announceToAll("Vote reward: We need " + ((lastVotes + voteRewardVotesDifference) - currentVotes) + " vote(s) for small reward.");
				Announcements.getInstance().announceToAll("Vote reward: We need " + (firstPageVotes - currentVotes) + " vote(s) to get to the first page of Hopzone for big reward.");
				Announcements.getInstance().announceToAll("Vote reward: Type .menu to see what the big and what the small reward is.");
			}
		}
	}
	
	private static int getFirstPageRankVotes()
	{
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try
		{
			URLConnection con = new URL(page1Url).openConnection();
			con.addRequestProperty("User-Agent", "Mozilla/4.76");
			isr = new InputStreamReader(con.getInputStream());
			br = new BufferedReader(isr);
			
			String line;
			int i = 0;
			while ((line = br.readLine()) != null)
			{
				if (line.contains("<span class=\"no\">" + firstPageRankNeeded + "</span>"))
				{
					i++;
				}
				if (line.contains("Anonymous Votes") && (i == 1))
				{
					i = 0;
					int votes = Integer.valueOf(line.split(">")[1].replace("</span", ""));
					return votes;
				}
			}
			
			br.close();
			isr.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
			System.out.println("Error while getting server vote count.");
		}
		
		return -1;
	}
	
	private static int getVotes()
	{
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try
		{
			URLConnection con = new URL(hopzoneUrl).openConnection();
			con.addRequestProperty("User-Agent", "Mozilla/4.76");
			isr = new InputStreamReader(con.getInputStream());
			br = new BufferedReader(isr);
			
			String line;
			while ((line = br.readLine()) != null)
			{
				if (line.contains("Anonymous User Votes"))
				{
					int votes = Integer.valueOf(line.split(">")[2].replace("</span", ""));
					return votes;
				}
			}
			
			br.close();
			isr.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
			System.out.println("Error while getting server vote count.");
		}
		
		return -1;
	}
}