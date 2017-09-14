/*
Navicat MySQL Data Transfer

Source Server         : cloundsun
Source Server Version : 50173
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-09-14 17:20:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `base_exception_info`
-- ----------------------------
DROP TABLE IF EXISTS `base_exception_info`;
CREATE TABLE `base_exception_info` (
  `id` int(11) NOT NULL,
  `adminUserCode` varchar(255) DEFAULT NULL,
  `className` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `info` longtext,
  `ip` varchar(255) DEFAULT NULL,
  `methodName` varchar(255) DEFAULT NULL,
  `urlPath` varchar(255) DEFAULT NULL,
  `userCode` varchar(255) DEFAULT NULL,
  `agent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of base_exception_info
-- ----------------------------
INSERT INTO `base_exception_info` VALUES ('1', 'xionglang', 'com.sunxl.base.service.impl.MenuServiceImpl', '2017-08-28 16:33:44', 'java.lang.ArithmeticException: / by zero', '127.0.0.1', 'update', '/sunxl/private/menu/update', null, 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3159.5 Safari/537.36');
INSERT INTO `base_exception_info` VALUES ('2', 'xionglang', 'com.sunxl.base.service.impl.MenuServiceImpl', '2017-08-29 10:58:07', 'java.lang.NullPointerException', '0:0:0:0:0:0:0:1', 'update', '/sunxl/private/menu/update', null, 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3159.5 Safari/537.36');
INSERT INTO `base_exception_info` VALUES ('3', 'xionglang', 'com.sunxl.base.service.impl.MenuServiceImpl.update', '2017-08-29 11:06:43', 'javax.persistence.PersistenceException: Error while committing the transaction', '0:0:0:0:0:0:0:1', 'update', '/sunxl/private/menu/update', null, 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3159.5 Safari/537.36');
INSERT INTO `base_exception_info` VALUES ('4', 'xionglang', 'com.sunxl.base.service.impl.MenuServiceImpl.update', '2017-08-29 11:08:03', 'javax.persistence.PersistenceException: Error while committing the transaction', '0:0:0:0:0:0:0:1', 'update', '/sunxl/private/menu/update', null, 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3159.5 Safari/537.36');
INSERT INTO `base_exception_info` VALUES ('6', 'xionglang', 'com.sunxl.base.service.impl.MenuServiceImpl.update', '2017-08-29 11:14:02', 'javax.persistence.PersistenceException: Error while committing the transaction', '0:0:0:0:0:0:0:1', 'update', '/sunxl/private/menu/update', null, 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3159.5 Safari/537.36');
INSERT INTO `base_exception_info` VALUES ('7', 'xionglang', 'com.sunxl.base.service.impl.MenuServiceImpl.update', '2017-08-29 11:14:46', 'javax.persistence.PersistenceException: Error while committing the transaction', '0:0:0:0:0:0:0:1', 'update', '/sunxl/private/menu/update', null, 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3159.5 Safari/537.36');

-- ----------------------------
-- Table structure for `id_sequence`
-- ----------------------------
DROP TABLE IF EXISTS `id_sequence`;
CREATE TABLE `id_sequence` (
  `id` int(11) NOT NULL,
  `ID_KEY` varchar(255) NOT NULL COMMENT '类名',
  `VALUE` int(11) NOT NULL COMMENT '索引值',
  `tableName` varchar(255) NOT NULL,
  `smrytx` varchar(255) DEFAULT NULL COMMENT '表用途',
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_ID_SEQUENCD_ID` (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of id_sequence
-- ----------------------------
INSERT INTO `id_sequence` VALUES ('1', 'com.sunxl.base.two.entity.IdSequence', '3', 'id_sequence', '管理数据库的索引信息，用于主键自增，同一个数据库，多个数据库需要新建多个索引信息，如果这里的索引sunxl数据库，新建就可以是索引XXX。', '2017-06-27 09:49:30');
INSERT INTO `id_sequence` VALUES ('2', 'com.sunxl.base.two.entity.ExceptionInfo', '8', 'base_exception_info', '错误信息日志', '2017-08-25 17:07:32');
