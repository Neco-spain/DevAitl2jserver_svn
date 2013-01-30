/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : l2jgs

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2012-12-20 15:14:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `custom_npc`
-- ----------------------------
DROP TABLE IF EXISTS `custom_npc`;
CREATE TABLE `custom_npc` (
  `id` mediumint(7) unsigned NOT NULL,
  `idTemplate` smallint(5) unsigned NOT NULL,
  `name` varchar(200) NOT NULL DEFAULT '',
  `serverSideName` tinyint(1) NOT NULL DEFAULT '1',
  `title` varchar(45) NOT NULL DEFAULT '',
  `serverSideTitle` tinyint(1) NOT NULL DEFAULT '1',
  `class` varchar(200) DEFAULT NULL,
  `collision_radius` decimal(6,2) DEFAULT NULL,
  `collision_height` decimal(6,2) DEFAULT NULL,
  `level` tinyint(2) DEFAULT NULL,
  `sex` enum('etc','female','male') NOT NULL DEFAULT 'etc',
  `type` varchar(22) DEFAULT NULL,
  `attackrange` smallint(4) DEFAULT NULL,
  `hp` decimal(30,15) DEFAULT NULL,
  `mp` decimal(30,15) DEFAULT NULL,
  `hpreg` decimal(30,15) DEFAULT NULL,
  `mpreg` decimal(30,15) DEFAULT NULL,
  `str` tinyint(2) NOT NULL DEFAULT '40',
  `con` tinyint(2) NOT NULL DEFAULT '43',
  `dex` tinyint(2) NOT NULL DEFAULT '30',
  `int` tinyint(2) NOT NULL DEFAULT '21',
  `wit` tinyint(2) NOT NULL DEFAULT '20',
  `men` tinyint(2) NOT NULL DEFAULT '20',
  `exp` int(9) NOT NULL DEFAULT '0',
  `sp` int(9) NOT NULL DEFAULT '0',
  `patk` decimal(12,5) DEFAULT NULL,
  `pdef` decimal(12,5) DEFAULT NULL,
  `matk` decimal(12,5) DEFAULT NULL,
  `mdef` decimal(12,5) DEFAULT NULL,
  `atkspd` smallint(4) NOT NULL DEFAULT '230',
  `critical` tinyint(1) NOT NULL DEFAULT '1',
  `matkspd` smallint(4) NOT NULL DEFAULT '333',
  `rhand` smallint(5) unsigned NOT NULL DEFAULT '0',
  `lhand` smallint(5) unsigned NOT NULL DEFAULT '0',
  `enchant` tinyint(1) NOT NULL DEFAULT '0',
  `walkspd` decimal(10,5) NOT NULL DEFAULT '60.00000',
  `runspd` decimal(10,5) NOT NULL DEFAULT '120.00000',
  `dropHerbGroup` tinyint(1) NOT NULL DEFAULT '0',
  `basestats` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of custom_npc
-- ----------------------------
INSERT INTO `custom_npc` VALUES ('500', '32226', 'Ninja', '1', 'Event Manager', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '70', 'male', 'L2Npc', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('501', '32226', 'Halloween', '1', 'Event Manager', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '70', 'male', 'L2Npc', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('502', '32226', 'Christmas', '1', 'Event Manager', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '70', 'male', 'L2Npc', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('503', '32226', 'Super Star', '1', 'Event Manager', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '70', 'male', 'L2Npc', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('504', '32226', 'Hallowen Npc', '1', 'Event Manager', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '70', 'male', 'L2Npc', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('505', '32226', 'Hallowen Ghost', '1', 'Event Manager', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '70', 'male', 'L2Npc', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7000', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7001', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7002', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7003', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7004', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7005', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7006', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7007', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7008', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7009', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7010', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7011', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7012', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7013', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7014', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7015', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7016', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7017', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7018', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7019', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7020', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7021', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7022', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('7023', '32226', 'Event Monster', '1', '', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '1', 'male', 'L2Monster', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('8888', '32226', 'Ranking', '1', 'Top', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '70', 'male', 'L2Npc', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('9999', '32226', 'Event Task', '1', 'L2PS', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '70', 'male', 'L2Npc', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('50007', '31324', 'Andromeda', '1', 'Wedding Manager', '1', 'NPC.a_casino_FDarkElf', '8.00', '23.00', '70', 'female', 'L2WeddingManager', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('65000', '32226', 'Shiela', '1', 'L2PS Buffer', '1', 'LineageNPC2.K_F1_grand', '11.00', '22.25', '70', 'male', 'L2Npc', '40', '2444.000000000000000', '2444.000000000000000', '0.000000000000000', '0.000000000000000', '10', '10', '10', '10', '10', '10', '0', '0', '500.00000', '500.00000', '500.00000', '500.00000', '278', '1', '333', '0', '0', '0', '28.00000', '120.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('800000', '20432', 'L2PS', '1', 'Project', '1', 'LineageMonster.elpy', '5.00', '4.50', '80', 'male', 'L2Npc', '40', '39.745190000000000', '40.000000000000000', '2.000000000000000', '0.900000000000000', '40', '43', '30', '21', '20', '20', '35', '2', '8.47458', '44.44444', '5.78704', '29.59162', '253', '4', '333', '0', '0', '0', '50.00000', '80.00000', '1', '1');
INSERT INTO `custom_npc` VALUES ('900100', '20432', 'Elpy', '1', '', '1', 'LineageMonster.elpy', '5.00', '4.50', '1', 'male', 'L2EventMonster', '40', '40.000000000000000', '36.000000000000000', '3.160000000000000', '0.910000000000000', '40', '43', '30', '21', '20', '20', '35', '2', '8.00000', '40.00000', '7.00000', '25.00000', '230', '1', '333', '0', '0', '0', '50.00000', '80.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('900101', '32365', 'Snow', '1', 'Event Manager', '1', 'LineageNPC2.TP_game_staff', '5.00', '12.50', '70', 'male', 'L2Npc', '40', '2444.000000000000000', '1225.000000000000000', '0.000000000000000', '0.000000000000000', '40', '43', '30', '21', '20', '20', '0', '0', '1086.00000', '471.00000', '749.00000', '313.00000', '230', '1', '333', '0', '0', '0', '68.00000', '109.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('900102', '13098', 'Event Treasure Chest', '1', '', '1', 'LineageMonster.mimic_even', '8.50', '8.50', '80', 'male', 'L2EventChest', '40', '2880.000000000000000', '1524.000000000000000', '0.000000000000000', '0.000000000000000', '40', '43', '30', '21', '20', '20', '0', '0', '1499.00000', '577.00000', '1035.00000', '384.00000', '230', '1', '253', '0', '0', '0', '1.00000', '1.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('900103', '32365', 'Start', '1', 'Event Manager', '1', 'LineageNPC2.TP_game_staff', '5.00', '12.50', '70', 'male', 'L2Npc', '40', '2444.000000000000000', '1225.000000000000000', '0.000000000000000', '0.000000000000000', '40', '43', '30', '21', '20', '20', '0', '0', '1086.00000', '471.00000', '749.00000', '313.00000', '230', '1', '333', '0', '0', '0', '68.00000', '109.00000', '0', '0');
INSERT INTO `custom_npc` VALUES ('900104', '32365', 'Finish', '1', 'Event Manager', '1', 'LineageNPC2.TP_game_staff', '5.00', '12.50', '70', 'male', 'L2Npc', '40', '2444.000000000000000', '1225.000000000000000', '0.000000000000000', '0.000000000000000', '40', '43', '30', '21', '20', '20', '0', '0', '1086.00000', '471.00000', '749.00000', '313.00000', '230', '1', '333', '0', '0', '0', '68.00000', '109.00000', '0', '0');
