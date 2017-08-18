/*
Navicat MySQL Data Transfer

Source Server         : cloundsun
Source Server Version : 50173
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-05-31 17:06:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `base_test`
-- ----------------------------
DROP TABLE IF EXISTS `base_test`;
CREATE TABLE `base_test` (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_TEST_KEY` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of base_test
-- ----------------------------

-- ----------------------------
-- Table structure for `id_sequence`
-- ----------------------------
DROP TABLE IF EXISTS `id_sequence`;
CREATE TABLE `id_sequence` (
  `id` int(11) NOT NULL,
  `ID_KEY` varchar(255) NOT NULL COMMENT '类名',
  `VALUE` int(11) NOT NULL COMMENT '索引值',
  `tableName` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_ID_SEQUENCD_ID` (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of id_sequence
-- ----------------------------
INSERT INTO `id_sequence` VALUES ('1', 'com.sunxl.base.entity.Test', '2', 'base_test');
