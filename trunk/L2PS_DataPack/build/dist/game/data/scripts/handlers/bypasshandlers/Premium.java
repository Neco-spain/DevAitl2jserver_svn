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
package handlers.bypasshandlers;

import com.l2jserver.Config;
import com.l2jserver.gameserver.cache.HtmCache;
import com.l2jserver.gameserver.datatables.ItemTable;
import com.l2jserver.gameserver.datatables.PremiumTable;
import com.l2jserver.gameserver.handler.IBypassHandler;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.network.DialogId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;
import com.l2jserver.gameserver.network.serverpackets.ConfirmDlg;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.util.Util;
import com.l2jserver.util.StringUtil;
import com.l2jserver.util.TimeConstant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;


public class Premium implements IBypassHandler
{
	private static final String[] COMMANDS =
	{
		"premiumShowList",
		"premiumShowChoice",
		"premiumAddPremium",
	};

	private String getListPage()
	{
		if (Config.PREMIUM_PRICE != null && !Config.PREMIUM_PRICE.isEmpty())
		{
			// select proper page to show
			// if base periods isn't defined, show "fixed_only" page
			if (PremiumTable.AVAILABLE_BASE_PERIODS.isEmpty()) 
			{
					return "premium/fixed_only";
			}
		
			else
			{
				if (!Config.PREMIUM_SMART_PRICING)
				{
					if (Config.PREMIUM_PRICE.size() > PremiumTable.AVAILABLE_BASE_PERIODS.size())
					{
						return "premium/fixed_only";
					}
				}
				else
				{
					return (Config.PREMIUM_PRICE.size() > PremiumTable.AVAILABLE_BASE_PERIODS.size()) ? "premium/both" : "premium/base_only";
				}
			}
		}
		
		return "misconfigured";
	}

	@Override
	public boolean useBypass(String command, L2PcInstance activeChar, L2Character target)
	{
		try
		{
			String path = "data/html/services/";
			String reply = null;
			String htmContent = null;

			if (!Config.PREMIUM_SERVICE_ENABLED || !Config.PREMIUM_ALLOW_VOICED)
			{
				reply = "service_disabled";
			}

			else if (!Config.PREMIUM_ALLOW_VOICED)
			{
				reply = "not_allowed";
			}
			
			else
			{
				if (command.equalsIgnoreCase("premiumShowList"))
				{
					reply = getListPage();
					htmContent = HtmCache.getInstance().getHtm(activeChar.getHtmlPrefix(), path + reply + ".html");

					if (htmContent != null)
					{
						if (reply.endsWith("base_only") || reply.endsWith("both"))
						{
							StringBuilder priceList = StringUtil.startAppend(50, "");
							StringBuilder periodList = StringUtil.startAppend(50, "");
							for (TimeConstant tc : PremiumTable.AVAILABLE_BASE_PERIODS)
							{
								ItemHolder payItem = Config.PREMIUM_PRICE.get(("1" + tc.getShortName()));
								String price = payItem.getCount() > 0 ? (Long.toString(payItem.getCount()) + " " + ItemTable.getInstance().getTemplate(payItem.getId()).getName()) : "free";
								StringUtil.append(priceList, "<tr><td>", "1 ", tc.getName(), " - ", price, "</td></tr>");
								StringUtil.append(periodList, tc.getName(), ";");
							}
							htmContent = htmContent.replaceAll("%price_list%", priceList.toString());
							htmContent = htmContent.replaceAll("%period_list%", periodList.toString().substring(0, periodList.length() - 1));
						}

						if (reply.endsWith("fixed_only") || reply.endsWith("both"))
						{
							StringBuilder list = StringUtil.startAppend(50, "");
							List<String> keys = new ArrayList<>(Config.PREMIUM_PRICE.keySet());
							Collections.reverse(keys);
							for (String defStr : keys) 
							{
								ItemHolder payItem = Config.PREMIUM_PRICE.get(defStr);
								String price = payItem.getCount() > 0 ? (Long.toString(payItem.getCount()) + " " + ItemTable.getInstance().getTemplate(payItem.getId()).getName()) : "free";
								String toNum = defStr.substring(0, defStr.length() - 1); // Whole string, except last symbol
								String period = defStr.substring(defStr.length() - 1, defStr.length()); // assume, that last symbol is code of time period
								StringUtil.append(list, "<td>", "<button value=\"", toNum, " ", Util.getTimeConstant(period).getName(), "('s) - ", price, 
																	"\" action=\"bypass -h premiumAddPremium ", defStr, 
																	"\" width=220 height=30 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
							}
							htmContent = htmContent.replaceAll("%list%", list.toString());
						}
					}

					if (htmContent != null)
					{
						NpcHtmlMessage html = new NpcHtmlMessage(0);
						html.setHtml(htmContent);
						activeChar.sendPacket(html);
						// Send a Server->Client ActionFailed to the L2PcInstance in order to avoid that the client wait another packet
						activeChar.sendPacket(ActionFailed.STATIC_PACKET);
					}

					return true;
				}
				
				else if (command.startsWith("premiumShowChoice"))
				{
					StringTokenizer st = new StringTokenizer(command, " ");
					st.nextToken();

					if (st.countTokens() < 2)
					{
						return false;
					}
					String cnt = st.nextToken();
					String period = st.nextToken();
					String defStr = cnt + period.substring(0, 1);
					ItemHolder payItem = PremiumTable.getPrice(defStr);

					if (payItem != null)
					{
						if (payItem.getCount() <= 0)
						{
							return PremiumTable.givePremium(activeChar, defStr, activeChar);
						}
						else
						{
							ConfirmDlg confirmation = new ConfirmDlg(SystemMessageId.S1.getId()).addString("It will cost " + 
																																														Long.toString(payItem.getCount()) +  " pcs. of " + 
																																														ItemTable.getInstance().getTemplate(payItem.getId()).getName() + 
																																														". Are you agreed?");
							activeChar.sendPacket(confirmation);
							activeChar.setDialogId(DialogId.PREMIUM);
							PremiumTable.getInstance().addRequest(activeChar, defStr);
							return true;
						}
					}
					else
					{
						activeChar.sendMessage("Wrong period!");
						return false;
					}
				}

				else if (command.startsWith("premiumAddPremium"))
				{
					StringTokenizer st = new StringTokenizer(command, " ");
					st.nextToken();

					if (st.countTokens() < 1)
					{
						return false;
					}

					String defStr = st.nextToken();

					ItemHolder payItem = Config.PREMIUM_PRICE.get(defStr);
					if (payItem != null)
					{
						return PremiumTable.givePremium(activeChar, defStr, activeChar);
					}
					else
					{
						activeChar.sendMessage("Wrong period!");
						return false;
					}
				}
			}
		}
		catch (Exception e)
		{
			_log.info("Exception in " + getClass().getSimpleName() + ": " + e.getMessage());
			e.printStackTrace();
		}

		return false;
	}		

	@Override
	public String[] getBypassList()
	{
		return COMMANDS;
	}
}