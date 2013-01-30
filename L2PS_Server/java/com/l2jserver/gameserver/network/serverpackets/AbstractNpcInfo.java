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
package com.l2jserver.gameserver.network.serverpackets;

import com.l2jserver.Config;
import com.l2jserver.gameserver.datatables.CharTemplateTable;
import com.l2jserver.gameserver.datatables.ClanTable;
import com.l2jserver.gameserver.datatables.NpcTable;
import com.l2jserver.gameserver.instancemanager.TownManager;
import com.l2jserver.gameserver.model.L2Clan;
import com.l2jserver.gameserver.model.PcCondOverride;
import com.l2jserver.gameserver.model.actor.FakePc;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.L2Trap;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2NpcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.templates.L2NpcTemplate;
import com.l2jserver.gameserver.model.actor.templates.L2PcTemplate;
import com.l2jserver.gameserver.model.effects.AbnormalEffect;
import com.l2jserver.gameserver.model.zone.ZoneId;

public abstract class AbstractNpcInfo extends L2GameServerPacket
{
	protected int _x, _y, _z, _heading;
	protected int _idTemplate;
	protected boolean _isAttackable, _isSummoned;
	protected int _mAtkSpd, _pAtkSpd;
	
	/**
	 * Run speed, swimming run speed and flying run speed
	 */
	protected int _runSpd;
	
	/**
	 * Walking speed, swimming walking speed and flying walking speed
	 */
	protected int _walkSpd;
	
	protected int _rhand, _lhand, _chest, _enchantEffect;
	protected double _collisionHeight, _collisionRadius;
	protected String _name = "";
	protected String _title = "";
	
	public AbstractNpcInfo(L2Character cha)
	{
		_isSummoned = cha.isShowSummonAnimation();
		_x = cha.getX();
		_y = cha.getY();
		_z = cha.getZ();
		_heading = cha.getHeading();
		_mAtkSpd = cha.getMAtkSpd();
		_pAtkSpd = cha.getPAtkSpd();
		_runSpd = cha.getTemplate().getBaseRunSpd();
		_walkSpd = cha.getTemplate().getBaseWalkSpd();
	}
	
	/**
	 * Packet for Npcs
	 */
	public static class NpcInfo extends AbstractNpcInfo
	{
		private final L2Npc _npc;
		private int _clanCrest = 0;
		private int _allyCrest = 0;
		private int _allyId = 0;
		private int _clanId = 0;
		private int _displayEffect = 0;
		
		public NpcInfo(L2Npc cha, L2Character attacker)
		{
			super(cha);
			_npc = cha;
			_idTemplate = cha.getTemplate().getIdTemplate(); // On every subclass
			_rhand = cha.getRightHandItem(); // On every subclass
			_lhand = cha.getLeftHandItem(); // On every subclass
			_enchantEffect = cha.getEnchantEffect();
			_collisionHeight = cha.getCollisionHeight();// On every subclass
			_collisionRadius = cha.getCollisionRadius();// On every subclass
			_isAttackable = cha.isAutoAttackable(attacker);
			if (cha.getTemplate().isServerSideName())
			{
				_name = cha.getName();// On every subclass
			}
			
			if (Config.CHAMPION_ENABLE && cha.isChampion())
			{
				_title = (Config.CHAMP_TITLE); // On every subclass
			}
			else if (cha.getTemplate().isServerSideTitle())
			{
				_title = cha.getTemplate().getTitle(); // On every subclass
			}
			else
			{
				_title = cha.getTitle(); // On every subclass
			}
			
			if (Config.SHOW_NPC_LVL && (_npc instanceof L2MonsterInstance))
			{
				String t = "Lv " + cha.getLevel() + (cha.getAggroRange() > 0 ? "*" : "");
				if (_title != null)
				{
					t += " " + _title;
				}
				
				_title = t;
			}
			
			if ((cha instanceof L2NpcInstance) && cha.isInsideZone(ZoneId.TOWN) && (Config.SHOW_CREST_WITHOUT_QUEST || cha.getCastle().getShowNpcCrest()) && (cha.getCastle().getOwnerId() != 0))
			{
				int townId = TownManager.getTown(_x, _y, _z).getTownId();
				if ((townId != 33) && (townId != 22))
				{
					L2Clan clan = ClanTable.getInstance().getClan(cha.getCastle().getOwnerId());
					_clanCrest = clan.getCrestId();
					_clanId = clan.getClanId();
					_allyCrest = clan.getAllyCrestId();
					_allyId = clan.getAllyId();
				}
			}
			
			_displayEffect = cha.getDisplayEffect();
		}
		
		@Override
		protected void writeImpl()
		{
			FakePc fPc = _npc.getFakePc();
			if (fPc == null)
			{
				writeC(0x0c);
				writeD(_npc.getObjectId());
				writeD(_idTemplate + 1000000);
				writeD(_isAttackable ? 1 : 0);
				writeD(_x);
				writeD(_y);
				writeD(_z);
				writeD(_heading);
				writeD(0x00);
				writeD(_mAtkSpd);
				writeD(_pAtkSpd);
				writeD(_runSpd);
				writeD(_walkSpd);
				writeD(_runSpd);
				writeD(_walkSpd);
				writeD(_runSpd);
				writeD(_walkSpd);
				writeD(_runSpd);
				writeD(_walkSpd);
				writeF(_npc.getMovementSpeedMultiplier());
				writeF(_npc.getAttackSpeedMultiplier());
				writeF(_collisionRadius);
				writeF(_collisionHeight);
				writeD(_rhand);
				writeD(_chest);
				writeD(_lhand);
				writeC(1);
				writeC(_npc.isRunning() ? 1 : 0);
				writeC(_npc.isInCombat() ? 1 : 0);
				writeC(_npc.isAlikeDead() ? 1 : 0);
				writeC(_isSummoned ? 2 : 0);
				writeD(-1);
				writeS(_name);
				writeD(-1);
				writeS(_title);
				writeD(0x00);
				writeD(0x00);
				writeD(0x00);
				
				writeD(_npc.getAbnormalEffect());
				writeD(_clanId);
				writeD(_clanCrest);
				writeD(_allyId);
				writeD(_allyCrest);
				
				writeC(_npc.isFlying() ? 2 : 0);
				writeC(_npc.getTeam());
				
				writeF(_collisionRadius);
				writeF(_collisionHeight);
				writeD(_enchantEffect);
				writeD(_npc.isFlying() ? 1 : 0);
				writeD(0x00);
				writeD(_npc.getColorEffect());
				writeC(_npc.isShowName() ? 0x01 : 0x00);
				writeC(_npc.isTargetable() ? 0x01 : 0x00);
				writeD(_npc.getSpecialEffect());
				writeD(_displayEffect);
			}
			else
			{
				L2PcInstance clientPlayer = getClient().getActiveChar();
				boolean invisStealth = false;
				if ((clientPlayer != null) && clientPlayer.isGM())
				{
					invisStealth = true;
				}
				
				writeC(0x31);
				writeD(_x);
				writeD(_y);
				writeD(_z);
				writeD(0);
				writeD(_npc.getObjectId());
				writeS(fPc.name.isEmpty() ? _npc.getName() : fPc.name);
				writeD(fPc.race);
				writeD(fPc.sex);
				writeD(fPc.clazz);
				writeD(fPc.pdUnder);
				writeD(fPc.pdHead);
				writeD(fPc.pdRHand);
				writeD(fPc.pdLHand);
				writeD(fPc.pdGloves);
				writeD(fPc.pdChest);
				writeD(fPc.pdLegs);
				writeD(fPc.pdFeet);
				writeD(fPc.pdCloak);
				writeD(fPc.pdRHand);
				writeD(fPc.pdHair);
				writeD(fPc.pdHair2);
				writeD(fPc.pdRBracelet);
				writeD(fPc.pdLBracelet);
				writeD(fPc.pdDeco1);
				writeD(fPc.pdDeco2);
				writeD(fPc.pdDeco3);
				writeD(fPc.pdDeco4);
				writeD(fPc.pdDeco5);
				writeD(fPc.pdDeco6);
				writeD(fPc.pdBelt);
				
				writeD(fPc.pdUnderAug);
				writeD(fPc.pdHeadAug);
				
				writeD(fPc.pdRHandAug);
				writeD(fPc.pdLHandAug);
				
				writeD(fPc.pdGlovesAug);
				writeD(fPc.pdChestAug);
				writeD(fPc.pdLegsAug);
				writeD(fPc.pdFeetAug);
				writeD(fPc.pdCloakAug);
				writeD(fPc.pdRHandAug);
				writeD(fPc.pdHairAug);
				writeD(fPc.pdHair2Aug);
				writeD(fPc.pdRBraceletAug);
				writeD(fPc.pdLBraceletAug);
				writeD(fPc.pdDeco1Aug);
				writeD(fPc.pdDeco2Aug);
				writeD(fPc.pdDeco3Aug);
				writeD(fPc.pdDeco4Aug);
				writeD(fPc.pdDeco5Aug);
				writeD(fPc.pdDeco6Aug);
				writeD(fPc.pdBeltAug);
				
				writeD(0x00);
				writeD(0x01);
				
				writeD(fPc.pvpFlag ? 1 : 0);
				writeD(fPc.karma);
				
				writeD(_mAtkSpd);
				writeD(_pAtkSpd);
				
				writeD(0x00);
				
				writeD(_runSpd);
				writeD(_walkSpd);
				writeD(_runSpd);
				writeD(_walkSpd);
				writeD(_runSpd);
				writeD(_walkSpd);
				writeD(_runSpd);
				writeD(_walkSpd);
				writeF(_npc.getMovementSpeedMultiplier());
				writeF(_npc.getAttackSpeedMultiplier());
				
				L2NpcTemplate mountNpcTmpl = NpcTable.getInstance().getTemplate(fPc.mountNpcId);
				if (mountNpcTmpl != null)
				{
					writeF(mountNpcTmpl.getfCollisionRadius());
					writeF(mountNpcTmpl.getfCollisionHeight());
				}
				else
				{
					L2PcTemplate pcTmpl = CharTemplateTable.getInstance().getTemplate(fPc.clazz);
					if (pcTmpl != null)
					{
						writeF(fPc.sex == 0 ? pcTmpl.getFCollisionRadiusMale() : pcTmpl.getFCollisionRadiusFemale());
						writeF(fPc.sex == 0 ? pcTmpl.getFCollisionHeightMale() : pcTmpl.getFCollisionHeightFemale());
					}
					else
					{
						writeF(0);
						writeF(0);
					}
				}
				
				writeD(fPc.hairStyle);
				writeD(fPc.hairColor);
				writeD(fPc.face);
				
				writeS(_npc.getTitle());
				
				writeD(fPc.clanId);
				writeD(fPc.clanCrestId);
				writeD(fPc.allyId);
				writeD(fPc.allyCrestId);
				
				writeC(fPc.sitWhileIdle && !_npc.isInCombat() && !_npc.isMoving() ? 0 : 1);
				writeC(_npc.isRunning() ? 1 : 0);
				writeC(_npc.isInCombat() ? 1 : 0);
				
				writeC(_npc.isAlikeDead() ? 1 : 0);
				
				writeC(fPc.invisible && !invisStealth ? 1 : 0);
				
				writeC(fPc.mount);
				writeC(0);
				
				writeH(0);
				
				writeC(0);
				
				writeD(fPc.invisible && invisStealth ? (_npc.getAbnormalEffect() | AbnormalEffect.STEALTH.getMask()) : _npc.getAbnormalEffect());
				
				writeC(fPc.mount == 2 ? 2 : 0);
				
				writeH(fPc.recomHave);
				writeD(fPc.mountNpcId + 1000000);
				writeD(fPc.clazz);
				writeD(0x00);
				writeC(fPc.mount != 0 ? 0 : _npc.getEnchantEffect());
				
				writeC(fPc.team);
				
				writeD(fPc.clanCrestLargeId);
				writeC(0);
				writeC(fPc.hero ? 1 : 0);
				
				writeC(fPc.fishing ? 1 : 0);
				writeD(fPc.fishingX);
				writeD(fPc.fishingY);
				writeD(fPc.fishingZ);
				
				writeD(fPc.nameColor);
				
				writeD(_heading);
				
				writeD(fPc.pledgeClass);
				writeD(fPc.pledgeType);
				
				writeD(fPc.titleColor);
				
				writeD(0);
				
				writeD(fPc.clanId != 0 ? fPc.reputationScore : 0);
				
				writeD(fPc.transformId);
				writeD(fPc.agathionId);
				
				writeD(0x01);
				
				writeD(_npc.getSpecialEffect());
			}
		}
	}
	
	public static class TrapInfo extends AbstractNpcInfo
	{
		private final L2Trap _trap;
		
		public TrapInfo(L2Trap cha, L2Character attacker)
		{
			super(cha);
			
			_trap = cha;
			_idTemplate = cha.getTemplate().getIdTemplate();
			_isAttackable = cha.isAutoAttackable(attacker);
			_rhand = 0;
			_lhand = 0;
			_collisionHeight = _trap.getTemplate().getfCollisionHeight();
			_collisionRadius = _trap.getTemplate().getfCollisionRadius();
			if (cha.getTemplate().isServerSideName())
			{
				_name = cha.getName();
			}
			_title = cha.getOwner() != null ? cha.getOwner().getName() : "";
			_runSpd = _trap.getRunSpeed();
			_walkSpd = _trap.getWalkSpeed();
		}
		
		@Override
		protected void writeImpl()
		{
			writeC(0x0c);
			writeD(_trap.getObjectId());
			writeD(_idTemplate + 1000000);
			writeD(_isAttackable ? 1 : 0);
			writeD(_x);
			writeD(_y);
			writeD(_z);
			writeD(_heading);
			writeD(0x00);
			writeD(_mAtkSpd);
			writeD(_pAtkSpd);
			writeD(_runSpd);
			writeD(_walkSpd);
			writeD(_runSpd);
			writeD(_walkSpd);
			writeD(_runSpd);
			writeD(_walkSpd);
			writeD(_runSpd);
			writeD(_walkSpd);
			writeF(_trap.getMovementSpeedMultiplier());
			writeF(_trap.getAttackSpeedMultiplier());
			writeF(_collisionRadius);
			writeF(_collisionHeight);
			writeD(_rhand);
			writeD(_chest);
			writeD(_lhand);
			writeC(1);
			writeC(1);
			writeC(_trap.isInCombat() ? 1 : 0);
			writeC(_trap.isAlikeDead() ? 1 : 0);
			writeC(_isSummoned ? 2 : 0);
			writeD(-1);
			writeS(_name);
			writeD(-1);
			writeS(_title);
			writeD(0x00);
			
			writeD(_trap.getPvpFlag());
			writeD(_trap.getKarma());
			
			writeD(_trap.getAbnormalEffect());
			writeD(0x00);
			writeD(0x00);
			writeD(0000);
			writeD(0000);
			writeC(0000);
			
			writeC(_trap.getTeam());
			
			writeF(_collisionRadius);
			writeF(_collisionHeight);
			writeD(0x00);
			writeD(0x00);
			writeD(0x00);
			writeD(0);
			writeC(0x01);
			writeC(0x01);
			writeD(0x00);
		}
	}
	
	public static class SummonInfo extends AbstractNpcInfo
	{
		private final L2Summon _summon;
		private int _form = 0;
		private int _val = 0;
		
		public SummonInfo(L2Summon cha, L2Character attacker, int val)
		{
			super(cha);
			_summon = cha;
			_val = val;
			if (_summon.isShowSummonAnimation())
			{
				_val = 2;
			}
			
			int npcId = cha.getTemplate().getNpcId();
			
			if ((npcId == 16041) || (npcId == 16042))
			{
				if (cha.getLevel() > 84)
				{
					_form = 3;
				}
				else if (cha.getLevel() > 79)
				{
					_form = 2;
				}
				else if (cha.getLevel() > 74)
				{
					_form = 1;
				}
			}
			else if ((npcId == 16025) || (npcId == 16037))
			{
				if (cha.getLevel() > 69)
				{
					_form = 3;
				}
				else if (cha.getLevel() > 64)
				{
					_form = 2;
				}
				else if (cha.getLevel() > 59)
				{
					_form = 1;
				}
			}
			
			_isAttackable = cha.isAutoAttackable(attacker);
			_rhand = cha.getWeapon();
			_lhand = 0;
			_chest = cha.getArmor();
			_enchantEffect = cha.getTemplate().getEnchantEffect();
			_name = cha.getName();
			_title = cha.getOwner() != null ? ((!cha.getOwner().isOnline()) ? "" : cha.getOwner().getName()) : ""; // when owner online, summon will show in title owner name
			_idTemplate = cha.getTemplate().getIdTemplate();
			_collisionHeight = cha.getTemplate().getfCollisionHeight();
			_collisionRadius = cha.getTemplate().getfCollisionRadius();
			_invisible = cha.getOwner() != null ? cha.getOwner().getAppearance().getInvisible() : false;
		}
		
		@Override
		protected void writeImpl()
		{
			boolean gmSeeInvis = false;
			if (_invisible)
			{
				L2PcInstance tmp = getClient().getActiveChar();
				if ((tmp != null) && tmp.canOverrideCond(PcCondOverride.SEE_ALL_PLAYERS))
				{
					gmSeeInvis = true;
				}
			}
			
			writeC(0x0c);
			writeD(_summon.getObjectId());
			writeD(_idTemplate + 1000000);
			writeD(_isAttackable ? 1 : 0);
			writeD(_x);
			writeD(_y);
			writeD(_z);
			writeD(_heading);
			writeD(0x00);
			writeD(_mAtkSpd);
			writeD(_pAtkSpd);
			writeD(_runSpd);
			writeD(_walkSpd);
			writeD(_runSpd);
			writeD(_walkSpd);
			writeD(_runSpd);
			writeD(_walkSpd);
			writeD(_runSpd);
			writeD(_walkSpd);
			writeF(_summon.getMovementSpeedMultiplier());
			writeF(_summon.getAttackSpeedMultiplier());
			writeF(_collisionRadius);
			writeF(_collisionHeight);
			writeD(_rhand);
			writeD(_chest);
			writeD(_lhand);
			writeC(0x01);
			writeC(0x01);
			writeC(_summon.isInCombat() ? 1 : 0);
			writeC(_summon.isAlikeDead() ? 1 : 0);
			writeC(_val);
			writeD(-1);
			writeS(_name);
			writeD(-1);
			writeS(_title);
			writeD(0x01);
			
			writeD(_summon.getPvpFlag());
			writeD(_summon.getKarma());
			
			writeD(gmSeeInvis ? _summon.getAbnormalEffect() | AbnormalEffect.STEALTH.getMask() : _summon.getAbnormalEffect());
			
			writeD(0x00);
			writeD(0x00);
			writeD(0000);
			writeD(0000);
			writeC(0000);
			
			writeC(_summon.getTeam());
			
			writeF(_collisionRadius);
			writeF(_collisionHeight);
			writeD(_enchantEffect);
			writeD(0x00);
			writeD(0x00);
			writeD(_form);
			writeC(0x01);
			writeC(0x01);
			writeD(_summon.getSpecialEffect());
		}
	}
}
