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

public class ExStartScenePlayer extends L2GameServerPacket
{
	public static final int LINDVIOR = 1;
    public static final int EKIMUS_OPENING = 2;
    public static final int EKIMUS_SUCCESS = 3;
    public static final int EKIMUS_FAIL = 4;
    public static final int TIAT_OPENING = 5;
    public static final int TIAT_SUCCESS = 6;
    public static final int TIAT_FAIL = 7;
    public static final int SSQ_SUSPECIOUS_DEATHS = 8;
    public static final int SSQ_DYING_MASSAGE = 9;
    public static final int SSQ_CONTRACT_OF_MAMMON = 10;
    public static final int SSQ_RITUAL_OF_PRIEST = 11;
    public static final int SSQ_SEALING_EMPEROR_1ST = 12;
    public static final int SSQ_SEALING_EMPEROR_2ND = 13;
    public static final int SSQ_EMBRYO = 14;
    public static final int SCENE_FREYA_OPENING = 15;
    public static final int SCENE_FREYA_PHASECH_A = 16;
    public static final int SCENE_FREYA_PHASECH_B = 17;
    public static final int SCENE_KEGOR_INTRUION = 18;
    public static final int SCENE_FREYA_ENDING_A = 19;
    public static final int SCENE_FREYA_ENDING_B = 20;
    public static final int SCENE_FREYA_FORCED_DEFEAT = 21;
    public static final int SCENE_FREYA_DEFEAT = 22;
    public static final int SCENE_ICE_HEAVYKNIGHT_SPAWN = 23;
    public static final int SCENE_SSQ2_HOLY_BURIAL_GROUND_OPENING = 24;
    public static final int SCENE_SSQ2_HOLY_BURIAL_GROUND_CLOSING = 25;
    public static final int SCENE_SSQ2_SOLINA_TOMB_OPENING = 26;
    public static final int SCENE_SSQ2_SOLINA_TOMB_CLOSING = 27;
    public static final int SCENE_SSQ2_ELYSS_NARRATION = 28;
    public static final int SCENE_SSQ2_BOSS_OPENING = 29;
    public static final int SCENE_SSQ2_BOSS_CLOSING = 30;
    public static final int LAND_KSERTH_A = 1000;
    public static final int LAND_KSERTH_B = 1001;
    public static final int LAND_UNDEAD_A = 1002;
    public static final int LAND_DISTRUCTION_A = 1004;
	private final int _movieId;
	
	public ExStartScenePlayer(int id)
	{
		_movieId = id;
	}
	
	@Override
	public void writeImpl()
	{
		writeC(0xFE);
		writeH(0x99);
		writeD(_movieId);
	}
}
