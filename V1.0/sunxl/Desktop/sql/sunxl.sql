/*
Navicat MySQL Data Transfer

Source Server         : cloundsun
Source Server Version : 50173
Source Host           : localhost:3306
Source Database       : sunxl

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-08-12 15:38:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `base_adminuser`
-- ----------------------------
DROP TABLE IF EXISTS `base_adminuser`;
CREATE TABLE `base_adminuser` (
  `id` int(11) NOT NULL,
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱号',
  `mobilePhone` varchar(255) DEFAULT NULL COMMENT '电话号码',
  `passWord` varchar(255) NOT NULL COMMENT '密码',
  `userName` varchar(255) NOT NULL COMMENT '用户名',
  `isSuperAdmin` int(2) DEFAULT '0' COMMENT '是否超级管理员',
  `defaultRoleId` int(11) DEFAULT NULL COMMENT '默认角色',
  `realName` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `deptId` int(11) NOT NULL COMMENT '所属部门',
  `type` varchar(10) DEFAULT NULL,
  `sid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_ADMINUSER_ID` (`id`) USING BTREE,
  KEY `FKB2321FAC8742810E` (`deptId`),
  KEY `FKB2321FAC73D5D8C6` (`defaultRoleId`),
  KEY `FKB2321FAC9B7A1C03` (`sid`),
  CONSTRAINT `FKB2321FAC73D5D8C6` FOREIGN KEY (`defaultRoleId`) REFERENCES `base_roles` (`id`),
  CONSTRAINT `FKB2321FAC8742810E` FOREIGN KEY (`deptId`) REFERENCES `base_dept` (`id`),
  CONSTRAINT `FKB2321FAC9B7A1C03` FOREIGN KEY (`sid`) REFERENCES `base_systid` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_adminuser
-- ----------------------------
INSERT INTO `base_adminuser` VALUES ('1', '2017-08-07 09:09:43', '1961198571@qq.com', '18124042465', '83nq88gxsE3hU0adG+w0Xg==', 'xionglang', '1', '1', '熊浪', '1', 'USE', null);
INSERT INTO `base_adminuser` VALUES ('2', '2017-08-07 09:09:27', '1961198572@qq.com', '18124042464', '83nq88gxsE3hU0adG+w0Xg==', 'admin', '1', null, 'admin', '1', 'DREDGE', null);

-- ----------------------------
-- Table structure for `base_adminusers_roles`
-- ----------------------------
DROP TABLE IF EXISTS `base_adminusers_roles`;
CREATE TABLE `base_adminusers_roles` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_ROLE_USER_ID` (`id`) USING BTREE,
  KEY `FK4D4AB6C511E9E2D7` (`userId`),
  KEY `FK4D4AB6C59FB7BD30` (`roleId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_adminusers_roles
-- ----------------------------
INSERT INTO `base_adminusers_roles` VALUES ('1', '1', '1');
INSERT INTO `base_adminusers_roles` VALUES ('2', '1', '2');

-- ----------------------------
-- Table structure for `base_checkcode`
-- ----------------------------
DROP TABLE IF EXISTS `base_checkcode`;
CREATE TABLE `base_checkcode` (
  `id` int(11) NOT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `checkCode` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `begTime` timestamp NULL DEFAULT NULL,
  `expireAt` int(11) DEFAULT NULL,
  `isUse` int(1) DEFAULT NULL,
  `usingAt` timestamp NULL DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `checkCode_send` varchar(255) DEFAULT NULL,
  `endTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_CHECKCODE_ID` (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_checkcode
-- ----------------------------

-- ----------------------------
-- Table structure for `base_company`
-- ----------------------------
DROP TABLE IF EXISTS `base_company`;
CREATE TABLE `base_company` (
  `id` int(11) NOT NULL,
  `companyName` varchar(255) NOT NULL COMMENT '公司名称',
  `userId` int(11) DEFAULT '0' COMMENT '公司法人',
  `companyScale` varchar(255) DEFAULT NULL COMMENT '公司规模',
  `companyIntroduction` text COMMENT '公司简介',
  `url` varchar(255) DEFAULT NULL COMMENT '公司网址',
  `phone` varchar(255) DEFAULT NULL COMMENT '公司对外电话',
  `createTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '公司创立时间',
  `companyPath` varchar(255) DEFAULT NULL COMMENT '公司地址',
  `corporationType` varchar(255) DEFAULT NULL COMMENT '公司性质',
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_COMPANY_ID` (`id`) USING BTREE,
  KEY `FK5DDD890FA50D129A` (`userId`),
  CONSTRAINT `FK5DDD890FA50D129A` FOREIGN KEY (`userId`) REFERENCES `base_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_company
-- ----------------------------
INSERT INTO `base_company` VALUES ('1', '测试公司', '1', '10-99人', '这是个测试公司', 'http://www.stffocus.com/', '18124042465', '2017-06-01 18:45:48', '南昌', 'CORPORATION');

-- ----------------------------
-- Table structure for `base_dept`
-- ----------------------------
DROP TABLE IF EXISTS `base_dept`;
CREATE TABLE `base_dept` (
  `id` int(11) NOT NULL,
  `companyId` int(11) NOT NULL COMMENT '所属公司',
  `deptName` varchar(255) NOT NULL COMMENT '部门名称',
  `deptId` int(11) DEFAULT '0' COMMENT '上级部门，顶级部门为-1，默认为公司',
  `userId` int(11) DEFAULT '0' COMMENT '部门负责人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_DEPT_ID` (`id`) USING BTREE,
  KEY `FK93B44AF34BEB98C` (`companyId`),
  KEY `FK93B44AF38742810E` (`deptId`),
  KEY `FK93B44AF3A50D129A` (`userId`),
  CONSTRAINT `FK93B44AF34BEB98C` FOREIGN KEY (`companyId`) REFERENCES `base_company` (`id`),
  CONSTRAINT `FK93B44AF38742810E` FOREIGN KEY (`deptId`) REFERENCES `base_dept` (`id`),
  CONSTRAINT `FK93B44AF3A50D129A` FOREIGN KEY (`userId`) REFERENCES `base_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_dept
-- ----------------------------
INSERT INTO `base_dept` VALUES ('1', '1', '测试部门', '1', '1');

-- ----------------------------
-- Table structure for `base_dynamic`
-- ----------------------------
DROP TABLE IF EXISTS `base_dynamic`;
CREATE TABLE `base_dynamic` (
  `id` int(11) NOT NULL,
  `addTime` datetime DEFAULT NULL,
  `content` varchar(255) NOT NULL,
  `other` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_DYNAMIC_ID` (`id`) USING BTREE,
  KEY `FKA3DBACD1A50D129A` (`userId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_dynamic
-- ----------------------------

-- ----------------------------
-- Table structure for `base_export`
-- ----------------------------
DROP TABLE IF EXISTS `base_export`;
CREATE TABLE `base_export` (
  `id` int(11) NOT NULL,
  `createTime` datetime DEFAULT NULL,
  `exportId` varchar(50) DEFAULT NULL,
  `exportName` varchar(50) DEFAULT NULL,
  `smrytx` varchar(255) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_export
-- ----------------------------

-- ----------------------------
-- Table structure for `base_export_exportinfo`
-- ----------------------------
DROP TABLE IF EXISTS `base_export_exportinfo`;
CREATE TABLE `base_export_exportinfo` (
  `id` int(11) NOT NULL,
  `eid` int(11) NOT NULL DEFAULT '0',
  `fid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK37EBD9FF181317A7` (`eid`),
  KEY `FK37EBD9FF8C81E8B6` (`fid`),
  KEY `FK37EBD9FF8C81E4F5` (`eid`),
  KEY `FK37EBD9FF18131B68` (`fid`),
  CONSTRAINT `FK37EBD9FF181317A7` FOREIGN KEY (`eid`) REFERENCES `base_export` (`id`),
  CONSTRAINT `FK37EBD9FF18131B68` FOREIGN KEY (`fid`) REFERENCES `base_export` (`id`),
  CONSTRAINT `FK37EBD9FF8C81E4F5` FOREIGN KEY (`eid`) REFERENCES `base_export_info` (`id`),
  CONSTRAINT `FK37EBD9FF8C81E8B6` FOREIGN KEY (`fid`) REFERENCES `base_export_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_export_exportinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `base_export_info`
-- ----------------------------
DROP TABLE IF EXISTS `base_export_info`;
CREATE TABLE `base_export_info` (
  `id` int(11) NOT NULL,
  `chartSet` varchar(255) DEFAULT NULL,
  `exportInfoId` varchar(255) DEFAULT NULL,
  `exportInfoName` varchar(255) DEFAULT NULL,
  `fileex` varchar(255) DEFAULT NULL,
  `filesz` varchar(255) DEFAULT NULL,
  `insrtg` int(11) NOT NULL,
  `path` varchar(255) DEFAULT NULL,
  `smrytx` varchar(255) DEFAULT NULL,
  `splitChar` varchar(255) DEFAULT NULL,
  `sqlstr` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `sid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5458EDEB9B7A1C03` (`sid`),
  CONSTRAINT `FK5458EDEB9B7A1C03` FOREIGN KEY (`sid`) REFERENCES `base_systid` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_export_info
-- ----------------------------

-- ----------------------------
-- Table structure for `base_import`
-- ----------------------------
DROP TABLE IF EXISTS `base_import`;
CREATE TABLE `base_import` (
  `id` int(11) NOT NULL,
  `createTime` datetime DEFAULT NULL,
  `importId` varchar(255) DEFAULT NULL,
  `importName` varchar(255) DEFAULT NULL,
  `smrytx` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_import
-- ----------------------------

-- ----------------------------
-- Table structure for `base_import_colm`
-- ----------------------------
DROP TABLE IF EXISTS `base_import_colm`;
CREATE TABLE `base_import_colm` (
  `id` int(11) NOT NULL,
  `coluna` varchar(50) NOT NULL,
  `commet` varchar(255) DEFAULT NULL,
  `defava` varchar(50) NOT NULL,
  `ordeid` int(3) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_import_colm
-- ----------------------------

-- ----------------------------
-- Table structure for `base_import_importcolm`
-- ----------------------------
DROP TABLE IF EXISTS `base_import_importcolm`;
CREATE TABLE `base_import_importcolm` (
  `id` int(11) NOT NULL,
  `cid` int(11) NOT NULL DEFAULT '0',
  `iid` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `PK_BASE_IMPORT_IMPORTCOLM` (`id`) USING BTREE,
  KEY `FK3B8F9EFED45506A3` (`cid`),
  KEY `FK3B8F9EFED457D2EA` (`iid`),
  CONSTRAINT `FK3B8F9EFED457D2EA` FOREIGN KEY (`iid`) REFERENCES `base_import_info` (`id`),
  CONSTRAINT `FK3B8F9EFED45506A3` FOREIGN KEY (`cid`) REFERENCES `base_import_colm` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_import_importcolm
-- ----------------------------

-- ----------------------------
-- Table structure for `base_import_importinfo`
-- ----------------------------
DROP TABLE IF EXISTS `base_import_importinfo`;
CREATE TABLE `base_import_importinfo` (
  `fid` int(11) NOT NULL,
  `iid` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `PK_BASE_IMPORT_IMPORTCOLM` (`id`) USING BTREE,
  KEY `FK3B83C8DFD457D2EA` (`iid`),
  KEY `FK3B83C8DF8A481AD9` (`fid`),
  KEY `FK3B9254BF8A48261C` (`iid`),
  KEY `FK3B9254BFD457C7A7` (`fid`),
  CONSTRAINT `FK3B9254BFD457C7A7` FOREIGN KEY (`fid`) REFERENCES `base_import_info` (`id`),
  CONSTRAINT `FK3B83C8DF8A481AD9` FOREIGN KEY (`fid`) REFERENCES `base_import` (`id`),
  CONSTRAINT `FK3B83C8DFD457D2EA` FOREIGN KEY (`iid`) REFERENCES `base_import_info` (`id`),
  CONSTRAINT `FK3B9254BF8A48261C` FOREIGN KEY (`iid`) REFERENCES `base_import` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_import_importinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `base_import_info`
-- ----------------------------
DROP TABLE IF EXISTS `base_import_info`;
CREATE TABLE `base_import_info` (
  `id` int(11) NOT NULL,
  `certty` varchar(255) DEFAULT NULL,
  `chartSet` varchar(255) DEFAULT NULL,
  `importInfoId` varchar(255) DEFAULT NULL,
  `importInfoName` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `splitChar` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `sid` int(11) DEFAULT NULL,
  `smrytx` varchar(255) DEFAULT NULL,
  `tableName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK28C3DC9A9B7A1C03` (`sid`),
  CONSTRAINT `FK28C3DC9A9B7A1C03` FOREIGN KEY (`sid`) REFERENCES `base_systid` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_import_info
-- ----------------------------
INSERT INTO `base_import_info` VALUES ('1', 'cmmd', 'GBK', 'gli_vchr', '涉票流水', '/home/wasadmin/hexin/shepiao', ',|', 'STOP', '1', '核心价税分离的原始流水数据', 'KL_GLI_VCHR');

-- ----------------------------
-- Table structure for `base_loginfo`
-- ----------------------------
DROP TABLE IF EXISTS `base_loginfo`;
CREATE TABLE `base_loginfo` (
  `id` int(11) NOT NULL,
  `loginTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '登陆时间',
  `ip` varchar(20) NOT NULL COMMENT '登陆IP',
  `userId` int(11) NOT NULL COMMENT '前台登陆用户名',
  `adminUserId` int(11) DEFAULT NULL COMMENT '后台登陆用户名',
  `method` varchar(100) DEFAULT NULL COMMENT '登陆执行的方法和类',
  `exception` varchar(1000) DEFAULT NULL COMMENT '登陆时抛出的异常',
  `logType` int(1) DEFAULT NULL COMMENT '日志类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_LOG_TYPE` (`id`) USING BTREE,
  KEY `FK399E128460ADEEE6` (`adminUserId`),
  KEY `FK399E1284A50D129A` (`userId`),
  CONSTRAINT `FK399E128460ADEEE6` FOREIGN KEY (`adminUserId`) REFERENCES `base_adminuser` (`id`),
  CONSTRAINT `FK399E1284A50D129A` FOREIGN KEY (`userId`) REFERENCES `base_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_loginfo
-- ----------------------------

-- ----------------------------
-- Table structure for `base_menus`
-- ----------------------------
DROP TABLE IF EXISTS `base_menus`;
CREATE TABLE `base_menus` (
  `id` int(11) NOT NULL,
  `function` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `fid` int(11) DEFAULT NULL,
  `pertainId` int(11) NOT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_BASE_MENUS_ID` (`id`) USING BTREE,
  KEY `FKE353E006D6DF9609` (`fid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_menus
-- ----------------------------
INSERT INTO `base_menus` VALUES ('1', '', '索引管理', '', '5', null, '0', '2017-08-03 15:26:28');
INSERT INTO `base_menus` VALUES ('3', '', '基础管理', '', '1', null, '0', '2017-08-02 14:49:11');
INSERT INTO `base_menus` VALUES ('4', 'PrivateMenuController.goMenu', '菜单管理', '/private/menu/viewMenuJson', '0', '3', '0', '2017-07-28 09:29:24');
INSERT INTO `base_menus` VALUES ('2', 'PrivateIdSequenceController.goIdSequence', 'SUNXL项目索引', '/private/idSequence/viewIdSequenceJson', '0', '1', '0', '2017-07-28 09:34:23');
INSERT INTO `base_menus` VALUES ('5', '', '数据管理', '', '0', null, '0', '2017-08-01 09:35:58');
INSERT INTO `base_menus` VALUES ('6', 'PrivateScheduleController.goSchedule', '开启调度', '/private/schedule/ViewScheduleJson', '1', '5', '0', '2017-08-02 15:11:09');
INSERT INTO `base_menus` VALUES ('7', 'PrivateAdminUserController.goAdminUser', '后台用户管理', '/private/adminUser/viewAdminUserJson', '5', '3', '0', '2017-08-02 15:19:17');
INSERT INTO `base_menus` VALUES ('8', 'PrivateExportDataController.goExportData', '卸数方案', '/private/exportData/viewExportDataJson', '2', '5', '0', '2017-08-02 15:12:22');
INSERT INTO `base_menus` VALUES ('9', 'PrivateExportDataInfoController.goExportInfo', '卸数配置', '/private/exportDataInfo/viewExportDataInfoJson', '1', '5', '0', '2017-08-02 15:12:50');
INSERT INTO `base_menus` VALUES ('10', 'PrivateImportDataColmController.goImportDataColm', '导数字段信息', '/private/importDataColm/viewImportDataColmJson', '1', '5', '0', '2017-08-02 15:13:23');
INSERT INTO `base_menus` VALUES ('11', 'PrivateImportDataController.goImportData', '导数方案', '/private/importData/viewImportDataJson', '1', '5', '0', '2017-08-02 15:13:46');
INSERT INTO `base_menus` VALUES ('12', 'PrivateImportDataInfoController.goImportDataInfo', '导数配置', '/private/importDataInfo/viewimportDataInfoJson', '2', '5', '0', '2017-08-02 15:18:50');
INSERT INTO `base_menus` VALUES ('13', 'PrivateRoleController.goRole', '角色管理', '/private/role/viewRoleJson', '0', '3', '0', '2017-08-02 15:07:33');
INSERT INTO `base_menus` VALUES ('14', 'PrivateSysTidDataController.goSysTidData', '系统管理', '/private/sysTidData/viewSysTidDataJson', '1', '3', '0', '2017-08-02 15:18:43');
INSERT INTO `base_menus` VALUES ('15', 'PrivateSysDataController.goSysData', '数据字典', '/private/sysData/viewSysDataJson', '0', '3', '0', '2017-08-08 10:13:34');

-- ----------------------------
-- Table structure for `base_menus_roles`
-- ----------------------------
DROP TABLE IF EXISTS `base_menus_roles`;
CREATE TABLE `base_menus_roles` (
  `id` int(11) NOT NULL,
  `menuId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_MENU_ROLE_ID` (`id`) USING BTREE,
  KEY `FK32E8354496A150C2` (`menuId`),
  KEY `FK32E835449FB7BD30` (`roleId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_menus_roles
-- ----------------------------
INSERT INTO `base_menus_roles` VALUES ('1', '1', '1');
INSERT INTO `base_menus_roles` VALUES ('2', '2', '1');
INSERT INTO `base_menus_roles` VALUES ('3', '3', '1');
INSERT INTO `base_menus_roles` VALUES ('4', '4', '1');
INSERT INTO `base_menus_roles` VALUES ('5', '5', '1');
INSERT INTO `base_menus_roles` VALUES ('6', '6', '1');
INSERT INTO `base_menus_roles` VALUES ('7', '3', '2');
INSERT INTO `base_menus_roles` VALUES ('8', '7', '1');
INSERT INTO `base_menus_roles` VALUES ('9', '7', '2');
INSERT INTO `base_menus_roles` VALUES ('10', '8', '1');
INSERT INTO `base_menus_roles` VALUES ('11', '8', '2');
INSERT INTO `base_menus_roles` VALUES ('12', '9', '1');
INSERT INTO `base_menus_roles` VALUES ('13', '9', '2');
INSERT INTO `base_menus_roles` VALUES ('14', '10', '1');
INSERT INTO `base_menus_roles` VALUES ('15', '10', '2');
INSERT INTO `base_menus_roles` VALUES ('16', '11', '1');
INSERT INTO `base_menus_roles` VALUES ('17', '11', '2');
INSERT INTO `base_menus_roles` VALUES ('18', '12', '1');
INSERT INTO `base_menus_roles` VALUES ('19', '12', '2');
INSERT INTO `base_menus_roles` VALUES ('20', '13', '1');
INSERT INTO `base_menus_roles` VALUES ('21', '13', '2');
INSERT INTO `base_menus_roles` VALUES ('22', '14', '1');
INSERT INTO `base_menus_roles` VALUES ('23', '14', '2');
INSERT INTO `base_menus_roles` VALUES ('24', '6', '2');
INSERT INTO `base_menus_roles` VALUES ('25', '5', '2');
INSERT INTO `base_menus_roles` VALUES ('28', '15', '1');
INSERT INTO `base_menus_roles` VALUES ('29', '15', '2');

-- ----------------------------
-- Table structure for `base_resource`
-- ----------------------------
DROP TABLE IF EXISTS `base_resource`;
CREATE TABLE `base_resource` (
  `id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `fileName` varchar(500) NOT NULL,
  `model` varchar(100) DEFAULT NULL,
  `ordinal` int(11) NOT NULL,
  `otherId` int(11) NOT NULL,
  `resourcePath` varchar(255) NOT NULL,
  `resourceType` varchar(255) DEFAULT NULL,
  `uploadDate` datetime DEFAULT NULL,
  `version` int(11) NOT NULL,
  `original` varchar(255) DEFAULT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_RESOURCE_ID` (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_resource
-- ----------------------------
INSERT INTO `base_resource` VALUES ('1', null, '授权书.doc', null, '0', '0', '/alidata/server/bjtf/image/2014/06/08/1402216228945授权书.doc', 'IMAGE', '2014-06-08 16:30:28', '0', null, null);

-- ----------------------------
-- Table structure for `base_roles`
-- ----------------------------
DROP TABLE IF EXISTS `base_roles`;
CREATE TABLE `base_roles` (
  `id` int(11) NOT NULL,
  `name` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `version` int(11) DEFAULT NULL,
  `rank` int(11) NOT NULL COMMENT '角色级别级别从小到大排列，0级别最高（开发者），1次之(管理员)',
  `createTime` datetime DEFAULT NULL,
  `smrytx` varchar(255) DEFAULT NULL,
  `sid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_ROLE_ID` (`id`) USING BTREE,
  KEY `FKE39ED7CF9B7A1C03` (`sid`),
  CONSTRAINT `FKE39ED7CF9B7A1C03` FOREIGN KEY (`sid`) REFERENCES `base_systid` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_roles
-- ----------------------------
INSERT INTO `base_roles` VALUES ('1', '开发者', '1', '0', '2017-08-04 09:30:38', '开发人员使用，权限最高，可以看到所有的信息', '14');
INSERT INTO `base_roles` VALUES ('2', 'admin', '1', '1', '2017-08-04 09:30:41', '用户管理员使用，权限极高，能看到除开发使用的模块外的全部信息', '14');
INSERT INTO `base_roles` VALUES ('3', '观察者', '1', '2', '2017-08-04 09:30:43', '测试使用', '14');

-- ----------------------------
-- Table structure for `base_schedule_status`
-- ----------------------------
DROP TABLE IF EXISTS `base_schedule_status`;
CREATE TABLE `base_schedule_status` (
  `id` int(11) NOT NULL,
  `sid` int(10) NOT NULL COMMENT '数据来源',
  `createDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `acctbr` varchar(30) DEFAULT NULL COMMENT '所属机构',
  `bathid` varchar(50) NOT NULL COMMENT '批次号',
  `uid` int(10) DEFAULT NULL COMMENT '操作员用户',
  `status` int(2) NOT NULL COMMENT ' ''核对状态 0卸载未完成 1已卸载完成  2 卸载出错 3 导入出错 4 导入成功 5 数据处理成功 6 数据处理失败 7数据处理中'';',
  `currdt` varchar(50) DEFAULT NULL COMMENT '下次批次号',
  `switdt` varchar(30) NOT NULL COMMENT '下次取数日期',
  `typecd` int(1) NOT NULL COMMENT '调度状态0，卸数；1，导数'';',
  `plancd` varchar(50) NOT NULL COMMENT '方案',
  `datach` varchar(50) NOT NULL COMMENT '单据',
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_BASE_IMDT_ID` (`id`) USING BTREE,
  KEY `PK_BASE_USER_ID` (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_schedule_status
-- ----------------------------

-- ----------------------------
-- Table structure for `base_sysdata`
-- ----------------------------
DROP TABLE IF EXISTS `base_sysdata`;
CREATE TABLE `base_sysdata` (
  `id` int(11) NOT NULL,
  `doubleData` double(22,2) DEFAULT NULL COMMENT '参数为double',
  `intData` int(11) DEFAULT NULL COMMENT '参数为int',
  `orderForData` int(1) NOT NULL DEFAULT '0' COMMENT '排序，0不排序，1升序排序，2降序排序',
  `paraFour` varchar(512) DEFAULT NULL COMMENT '字符串参数4大小512',
  `paraOne` varchar(256) DEFAULT NULL COMMENT '字符串参数3大小256',
  `paraThree` varchar(128) DEFAULT NULL COMMENT '字符串参数2大小128',
  `paraTwo` varchar(64) DEFAULT NULL COMMENT '字符串参数1大小64',
  `project` varchar(32) NOT NULL COMMENT '项目名',
  `type` varchar(32) NOT NULL COMMENT '所属位置',
  `value` varchar(32) DEFAULT NULL COMMENT '参数值默认使用32位的字符串',
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_BASE_SYSDATA` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_sysdata
-- ----------------------------
INSERT INTO `base_sysdata` VALUES ('1', null, '0', '0', '', '开通', '', '', 'sunxl', 'statusType', 'DREDGE');
INSERT INTO `base_sysdata` VALUES ('2', null, '1', '0', '', '试用', '', '', 'sunxl', 'statusType', 'USE');
INSERT INTO `base_sysdata` VALUES ('3', null, '-1', '0', '', '冻结', '', '', 'suntxl', 'statusType', 'FROST');
INSERT INTO `base_sysdata` VALUES ('4', null, '1', '0', '', '启用', '', '', 'sunxl', 'type', 'START');
INSERT INTO `base_sysdata` VALUES ('5', null, '0', '0', '', '不启用', '', '', 'sunxl', 'type', 'STOP');

-- ----------------------------
-- Table structure for `base_systid`
-- ----------------------------
DROP TABLE IF EXISTS `base_systid`;
CREATE TABLE `base_systid` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_systid
-- ----------------------------
INSERT INTO `base_systid` VALUES ('1', '核心系统', '/home/wasadmin/shedule/import/hexin', '2017-08-03 09:58:57');
INSERT INTO `base_systid` VALUES ('2', '财务系统', '/home/wasadmin/shedule/import/caiwu', '2017-08-03 09:59:23');
INSERT INTO `base_systid` VALUES ('3', '国结系统', '/home/wasadmin/shedule/import/guojie', '2017-08-03 09:59:47');
INSERT INTO `base_systid` VALUES ('4', '资金系统', '/home/wasadmin/shedule/import/zijin', '2017-08-03 10:00:17');
INSERT INTO `base_systid` VALUES ('5', '信贷系统', '/home/wasadmin/shedule/import/xindai', '2017-08-03 10:02:39');
INSERT INTO `base_systid` VALUES ('6', '总账系统', '/home/wasadmin/shedule/import/zongzhang', '2017-08-03 10:03:30');
INSERT INTO `base_systid` VALUES ('7', '核心系统', '/home/wasadmin/shedule/export/hexin', '2017-08-03 09:58:57');
INSERT INTO `base_systid` VALUES ('8', '财务系统', '/home/wasadmin/shedule/export/caiwu', '2017-08-03 09:59:23');
INSERT INTO `base_systid` VALUES ('9', '国结系统', '/home/wasadmin/shedule/export/guojie', '2017-08-03 09:59:47');
INSERT INTO `base_systid` VALUES ('10', '资金系统', '/home/wasadmin/shedule/export/zijin', '2017-08-03 10:00:17');
INSERT INTO `base_systid` VALUES ('11', '信贷系统', '/home/wasadmin/shedule/export/xindai', '2017-08-03 10:02:39');
INSERT INTO `base_systid` VALUES ('12', '总账系统', '/home/wasadmin/shedule/export/zongzhang', '2017-08-03 10:03:30');
INSERT INTO `base_systid` VALUES ('13', 'XXX后台系统', 'http://localhost:8080/sunxl/admin/login', '2017-08-04 09:40:33');
INSERT INTO `base_systid` VALUES ('14', 'XXX前台系统', 'http://localhost:8080/login', '2017-08-04 09:41:07');

-- ----------------------------
-- Table structure for `base_user`
-- ----------------------------
DROP TABLE IF EXISTS `base_user`;
CREATE TABLE `base_user` (
  `id` int(11) NOT NULL,
  `active` int(11) NOT NULL DEFAULT '0' COMMENT '活跃次数',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `email` varchar(45) DEFAULT NULL COMMENT '邮箱',
  `gender` varchar(5) DEFAULT NULL COMMENT '性别',
  `homePhone` varchar(45) DEFAULT NULL COMMENT '家庭电话',
  `lastLoginTime` timestamp NULL DEFAULT NULL COMMENT '最后登陆时间',
  `mobilePhone` varchar(45) DEFAULT NULL COMMENT '电话号码',
  `passWord` varchar(45) DEFAULT NULL COMMENT '密码',
  `userName` varchar(45) DEFAULT NULL COMMENT '用户名',
  `qq` varchar(20) DEFAULT NULL COMMENT 'qq号码',
  `realname` varchar(45) DEFAULT NULL COMMENT '真实姓名',
  `version` int(11) DEFAULT NULL,
  `workPhone` varchar(45) DEFAULT NULL COMMENT '工作电话',
  `recommendedName` varchar(45) DEFAULT NULL COMMENT '推荐人名称',
  `deptId` int(11) DEFAULT '0' COMMENT '所属部门',
  `loginTimes` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `isSuperAdmin` varchar(1) DEFAULT NULL,
  `defaultRoleId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_USER_ID` (`id`) USING BTREE,
  KEY `FOREIGN_DEPT` (`deptId`) USING BTREE,
  KEY `FK93BC38798742810E` (`deptId`),
  KEY `FK93BC387945881EB1` (`defaultRoleId`),
  CONSTRAINT `FK93BC387945881EB1` FOREIGN KEY (`defaultRoleId`) REFERENCES `base_roles` (`id`),
  CONSTRAINT `FK93BC38798742810E` FOREIGN KEY (`deptId`) REFERENCES `base_dept` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_user
-- ----------------------------
INSERT INTO `base_user` VALUES ('1', '0', '2017-06-02 09:57:33', '3411329@qq.com', 'MALE', null, '2017-06-02 09:59:03', '18124042465', 'fEqNCco3Yq9h5ZUglD3CZJT4lBs=', 'xionglang', '3411329', '熊浪', '1', null, 'admin', '1', '1', null, null, null);

-- ----------------------------
-- Table structure for `base_users_roles`
-- ----------------------------
DROP TABLE IF EXISTS `base_users_roles`;
CREATE TABLE `base_users_roles` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `PK_ROLE_USER_ID` (`id`) USING BTREE,
  KEY `FKC8068D58CE057745` (`roleId`),
  KEY `FKC8068D5811E9E2D7` (`userId`),
  KEY `FKC8068D589FB7BD30` (`roleId`),
  KEY `FKC8068D58A50D129A` (`userId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_users_roles
-- ----------------------------
INSERT INTO `base_users_roles` VALUES ('1', '1', '1');
INSERT INTO `base_users_roles` VALUES ('2', '1', '2');

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
INSERT INTO `id_sequence` VALUES ('1', 'com.sunxl.base.entity.IdSequence', '25', 'id_sequence', '管理数据库的索引信息，用于主键自增，同一个数据库，多个数据库需要新建多个索引信息，如果这里的索引sunxl数据库，新建就可以是索引XXX。', '2017-06-27 09:49:30');
INSERT INTO `id_sequence` VALUES ('2', 'com.sunxl.base.entity.AdminUser', '3', 'base_adminuser', '后台用户', '2017-06-27 09:49:32');
INSERT INTO `id_sequence` VALUES ('3', 'com.sunxl.base.entity.CheckCode', '1', 'base_checkcode', '手机验证码', '2017-06-27 09:49:35');
INSERT INTO `id_sequence` VALUES ('4', 'com.sunxl.base.entity.Company', '2', 'base_company', '公司', '2017-06-27 09:49:38');
INSERT INTO `id_sequence` VALUES ('5', 'com.sunxl.base.entity.Dept', '2', 'base_dept', '部门', '2017-06-27 09:49:40');
INSERT INTO `id_sequence` VALUES ('6', 'com.sunxl.base.entity.Dynamic', '1', 'base_dynamic', '动态信息', '2017-06-27 09:49:42');
INSERT INTO `id_sequence` VALUES ('7', 'com.sunxl.base.entity.LogInfo', '1', 'base_loginfo', '日志信息', '2017-06-27 09:49:44');
INSERT INTO `id_sequence` VALUES ('8', 'com.sunxl.base.entity.Menus', '16', 'base_menus', '菜单', '2017-06-27 09:49:47');
INSERT INTO `id_sequence` VALUES ('9', 'com.sunxl.base.entity.Resource', '2', 'base_resource', '上传文件信息', '2017-06-27 09:49:49');
INSERT INTO `id_sequence` VALUES ('10', 'com.sunxl.base.entity.Roles', '4', 'base_roles', '角色', '2017-06-27 09:49:51');
INSERT INTO `id_sequence` VALUES ('11', 'com.sunxl.base.entity.User', '2', 'base_user', '前台用户', '2017-06-27 09:49:53');
INSERT INTO `id_sequence` VALUES ('12', 'com.sunxl.base.entity.SysData', '6', 'base_sysdata', '初始化数据', '2017-07-05 10:40:43');
INSERT INTO `id_sequence` VALUES ('13', 'com.sunxl.base.entity.AdminUserToRole', '3', 'base_adminusers_roles', '后台用户角色关联表', '2017-08-01 15:50:42');
INSERT INTO `id_sequence` VALUES ('22', 'com.sunxl.base.entity.UserToRole', '3', 'base_users_roles', '前台用户角色信息关联表', '2017-08-02 11:52:11');
INSERT INTO `id_sequence` VALUES ('15', 'com.sunxl.base.entity.ExportData', '1', 'base_export', '卸数方案', '2017-08-01 16:48:10');
INSERT INTO `id_sequence` VALUES ('16', 'com.sunxl.base.entity.ExportDataInfo', '1', 'base_export_info', '卸数配置信息', '2017-08-01 16:48:47');
INSERT INTO `id_sequence` VALUES ('17', 'com.sunxl.base.entity.ImportData', '1', 'base_import', '导数方案', '2017-08-01 16:49:20');
INSERT INTO `id_sequence` VALUES ('18', 'com.sunxl.base.entity.ImportDataInfo', '2', 'base_import_info', '导数配置', '2017-08-01 16:51:42');
INSERT INTO `id_sequence` VALUES ('19', 'com.sunxl.base.entity.ImportDataColm', '1', 'base_import_colm', '导数表字段信息', '2017-08-01 16:52:32');
INSERT INTO `id_sequence` VALUES ('20', 'com.sunxl.base.entity.SysTidData', '15', 'base_systid', '数据来源信息', '2017-08-01 16:54:30');
INSERT INTO `id_sequence` VALUES ('21', 'com.sunxl.base.entity.MenuToRole', '30', 'base_menus_roles', '菜单和角色关联表', '2017-08-02 11:50:38');
INSERT INTO `id_sequence` VALUES ('23', 'com.sunxl.base.entity.ScheduleStatus', '1', 'base_schedule_status', '调度控制信息表', '2017-08-02 11:53:38');
INSERT INTO `id_sequence` VALUES ('24', 'com.sunxl.base.entity.ImportToColm', '1', 'base_import_importcolm', '表和表字段关联表', '2017-08-10 10:16:11');
