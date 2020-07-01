/*
 Navicat Premium Data Transfer

 Source Server         : 221.2.140.133
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 127.0.0.1:3306
 Source Schema         : ms_console

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 04/04/2019 10:35:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cmn_auth_menu
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_menu`;
CREATE TABLE `cmn_auth_menu`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单编码',
  `URI` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单定位',
  `ICON` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图标路径',
  `TYPE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单类型',
  `PARENT_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '父级ID',
  `COMMENT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '说明',
  `SEQ_ORDER` decimal(2, 0) NOT NULL COMMENT '排序',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cmn_auth_menu
-- ----------------------------
INSERT INTO `cmn_auth_menu` VALUES ('1', '服务管理', 'menu.service', '/service', 'el-icon-mobile-phone', 'i', '', '服务管理', 0, '2018-12-11 11:41:44', '');
INSERT INTO `cmn_auth_menu` VALUES ('19', '角色管理', 'menu.roles', '/roles', 'fa fa-user-circle-o', 'i', '', '角色管理', 0, '2018-12-11 11:46:14', '');
INSERT INTO `cmn_auth_menu` VALUES ('2', '配置中心', 'menu.config', '/config', 'el-icon-date', 'i', '', '配置中心', 0, '2018-12-11 11:46:14', '');
INSERT INTO `cmn_auth_menu` VALUES ('3', '统一网关', 'menu.gateway', '/gateway', 'el-icon-date', 'i', '', '统一网关', 0, '2018-12-11 11:46:14', '');
INSERT INTO `cmn_auth_menu` VALUES ('4', '监控中心', 'menu.monitor', '', 'el-icon-date', 'i', '', '监控中心', 0, '2018-12-11 11:46:14', '');
INSERT INTO `cmn_auth_menu` VALUES ('5', '服务跟踪', 'menu.monitor.trace', '/monitor/service', 'el-icon-date', 'i', '4', '服务跟踪', 0, '2018-12-11 11:46:14', '');
INSERT INTO `cmn_auth_menu` VALUES ('6', '日志跟踪', 'menu.monitor.log', '/monitor/log', 'el-icon-date', 'i', '4', '日志跟踪', 0, '2018-12-11 11:46:14', '');
INSERT INTO `cmn_auth_menu` VALUES ('7', '基础监控', 'menu.monitor.infra', '/monitor/base', 'el-icon-date', 'i', '4', '基础监控', 0, '2018-12-11 11:46:14', '');
INSERT INTO `cmn_auth_menu` VALUES ('8', '容错情况', 'menu.monitor.brk', '/monitor/turbine', 'el-icon-date', 'i', '4', '容错情况', 0, '2018-12-11 11:46:14', '');

-- ----------------------------
-- Table structure for cmn_auth_obj_domain
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_obj_domain`;
CREATE TABLE `cmn_auth_obj_domain`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '域名称',
  `CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '域编码',
  `SEQ_ORDER` decimal(2, 0) NOT NULL COMMENT '排序',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `CODE`(`CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '要有初始化数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cmn_auth_obj_domain
-- ----------------------------
INSERT INTO `cmn_auth_obj_domain` VALUES ('1', '服务管理', 'service', 1, '2018-10-29 09:51:24', 'system');
INSERT INTO `cmn_auth_obj_domain` VALUES ('2', '配置中心', 'config', 2, '2018-10-29 10:18:53', 'system');
INSERT INTO `cmn_auth_obj_domain` VALUES ('3', '统一网关', 'gateway', 3, '2019-01-26 16:22:30', 'system');
INSERT INTO `cmn_auth_obj_domain` VALUES ('4', '监控中心', 'monitor', 4, '2019-01-26 16:22:30', 'system');

-- ----------------------------
-- Table structure for cmn_auth_obj_operation
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_obj_operation`;
CREATE TABLE `cmn_auth_obj_operation`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作名称',
  `CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作编码',
  `METHOD` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'GET' COMMENT '对应的HTTP METHOD',
  `IS_SYS` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否系统默认',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `CODE`(`CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '要有初始化数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cmn_auth_obj_operation
-- ----------------------------
INSERT INTO `cmn_auth_obj_operation` VALUES ('1', '创建', 'create', 'POST', b'1', '2018-10-17 00:00:00', 'system');
INSERT INTO `cmn_auth_obj_operation` VALUES ('2', '更新', 'update', 'PUT', b'1', '2018-10-17 00:00:00', 'system');
INSERT INTO `cmn_auth_obj_operation` VALUES ('3', '查询', 'query', 'GET', b'1', '2018-10-17 00:00:00', 'system');
INSERT INTO `cmn_auth_obj_operation` VALUES ('4', '删除', 'delete', 'DELETE', b'1', '2018-10-17 00:00:00', 'system');
INSERT INTO `cmn_auth_obj_operation` VALUES ('5', '获取注册服务', 'geteu', 'GET', b'0', '2019-03-05 14:22:18', 'system');
INSERT INTO `cmn_auth_obj_operation` VALUES ('6', '获取注册服务实例', 'geteui', 'GET', b'0', '2019-03-05 14:24:10', 'system');

-- ----------------------------
-- Table structure for cmn_auth_objects
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_objects`;
CREATE TABLE `cmn_auth_objects`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象名称',
  `CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '对象编码',
  `URI` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象定位',
  `OPERATE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象操作',
  `TYPE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象类型',
  `DOMAIN_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '所属域编码',
  `SEQ_ORDER` int(2) NOT NULL DEFAULT 0 COMMENT '排序',
  `DEF_AUTH_OP` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'c' COMMENT '默认权限行为',
  `COMMENT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '说明',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `NAME`(`NAME`) USING BTREE,
  UNIQUE INDEX `CODE`(`CODE`) USING BTREE,
  INDEX `URI`(`URI`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cmn_auth_objects
-- ----------------------------
INSERT INTO `cmn_auth_objects` VALUES ('10', '错误测试', 'get:/exception/{type}/{msg}', '/exception/{type}/{msg}', 'query', 'i', 'role', 0, 'a', NULL, '2018-11-08 12:01:06', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('101', '获取用户角色', 'get:/roles/{userCode}', '/roles/{userCode}', 'get', 'i', 'role', 0, 'a', NULL, '2019-01-29 16:05:56', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('11', '获取组织机构', 'get:/orgs', '/orgs', 'query', 'i', 'sys', 0, 'a', NULL, '2018-11-14 18:14:46', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('12', '获取用户', 'get:/users', '/users', 'get', 'i', 'sys', 0, 'a', NULL, '2018-11-14 18:15:31', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('13', '获得全部权限', 'get:/permissions', '/permissions', 'get', 'i', 'sys', 1, 'a', NULL, '2018-11-15 18:15:00', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('15', '设置角色的权限', 'post:/role_permission/{role}', '/role_permission/{role}', 'post', 'i', 'sys', 3, 'a', NULL, '2018-11-17 18:15:00', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('16', '获得用户的权限', 'get:/user_permission/{user}', '/user_permission/{user}', 'get', 'i', 'sys', 4, 'a', NULL, '2018-11-18 18:15:00', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('17', '获得全部菜单', 'get:/menus', '/menus', 'get', 'i', 'sys', 5, 'a', NULL, '2018-11-19 18:15:00', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('18', '获得角色的菜单', 'get:/role_menu/{role}', '/role_menu/{role}', 'get', 'i', 'sys', 6, 'a', NULL, '2018-11-20 18:15:00', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('19', '设置角色的菜单', 'post:/role_menu/{role}', '/role_menu/{role}', 'post', 'i', 'sys', 7, 'a', NULL, '2018-11-21 18:15:00', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('20', '获得用户的菜单', 'get:/user_menu/{user}', '/user_menu/{user}', 'get', 'i', 'sys', 8, 'a', NULL, '2018-11-22 18:15:00', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('201', '指定提交的内容', 'get:/config/content/{commitId}', '/config/content/{commitId}', 'get', 'i', 'config', 201, 'a', NULL, '2008-11-01 12:00:01', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('202', '查看指定文件的提交日志记录', 'get:/config/log', '/config/log', 'get', 'i', 'config', 202, 'a', NULL, '2008-11-01 12:00:01', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('203', '根据文件名和所属阶段查找配置文件', 'get:/config/search', '/config/search', 'get', 'i', 'config', 203, 'a', NULL, '2008-11-01 12:00:01', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('204', '对比指定文件最新内容和指定提交的内容对比', 'get:/config/diff/{commitId}', '/config/diff/{commitId}', 'get', 'i', 'config', 204, 'a', NULL, '2008-11-01 12:00:01', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('205', '推送最新仓库到服务器', 'put:/config/push', '/config/push', 'put', 'i', 'config', 205, 'a', NULL, '2008-11-01 12:00:01', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('206', '显示目录下所有的配置文件', 'get:/config/all', '/config/all', 'get', 'i', 'config', 206, 'a', NULL, '2008-11-01 12:00:01', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('207', '获取所有服务的列表', 'get:/discovery/services', '/discovery/services', 'get', 'i', 'gateway', 207, 'a', NULL, '2008-11-01 12:00:01', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('208', '启动指定的服务实例', 'post:/discovery/service/{appID}/{instanceID}', '/discovery/service/{appID}/{instanceID}', 'post', 'i', 'gateway', 208, 'a', NULL, '2008-11-01 12:00:01', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('209', '启动指定的服务', 'post:/discovery/service/{appID}', '/discovery/service/{appID}', 'post', 'i', 'gateway', 209, 'a', NULL, '2008-11-01 12:00:01', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('21', '将用户从角色中删除', 'put:/role_user/{roleCode}', '/role_user/{roleCode}', 'update', 'i', 'sys', 9, 'a', NULL, '2018-11-23 18:15:00', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('210', '停止指定的服务实例', 'delete:/discovery/service/{appID}/{instanceID}', '/discovery/service/{appID}/{instanceID}', 'delete', 'i', 'gateway', 210, 'a', NULL, '2008-11-01 12:00:01', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('211', '停止指定的服务', 'delete:/discovery/service/{appID}', '/discovery/service/{appID}', 'delete', 'i', 'gateway', 211, 'a', NULL, '2008-11-01 12:00:01', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('212', '获取eureka服务的列表', 'get:/discovery/eureka', '/discovery/eureka', 'geteu', 'i', 'service', 212, 'c', NULL, '2008-11-01 12:00:01', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('213', '获取指定的eureka服务的实例信息', 'get:/discovery/eureka/info', '/discovery/eureka/info', 'geteui', 'i', 'service', 213, 'c', NULL, '2008-11-01 12:00:01', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('214', '获取指定全部的路由配置', 'get:/gateway/routes', '/gateway/routes', 'get', 'i', 'service', 214, 'l', NULL, '2008-11-01 12:00:01', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('215', '更新路由限流配置', 'put:/gateway/rate_limit', '/gateway/rate_limit', 'put', 'i', 'service', 215, 'a', NULL, '2008-11-01 12:00:01', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('216', '获取所有网关的实例信息', 'get:/gateway/list', '/gateway/list', 'get', 'i', 'service', 216, 'a', NULL, '2008-11-01 12:00:01', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('217', '获取指定网关的默认限流信息', 'get:/gateway/default_rate_limit', '/gateway/default_rate_limit', 'get', 'i', 'service', 217, 'a', NULL, '2008-11-01 12:00:01', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('22', '为角色增加用户', 'post:/role_user/{roleCode}', '/role_user/{roleCode}', 'post', 'i', 'sys', 10, 'a', NULL, '2018-11-24 18:15:00', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('23', '获取角色下的用户', 'get:/role_user/{roleCode}', '/role_user/{roleCode}', 'get', 'i', 'sys', 11, 'a', NULL, '2018-11-25 18:15:00', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('24', '修改角色', 'put:/role', '/role', 'update', 'i', 'sys', 12, 'a', NULL, '2018-11-26 18:15:00', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('25', '获得角色的权限', 'get:/role_permission/{role}', '/role_permission/{role}', 'get', 'i', 'role', 0, 'a', NULL, '2018-11-14 18:42:49', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('6', '删除角色', 'delete:/role/{roleID}', '/role/{roleID}', 'delete', 'i', 'role', 0, 'a', NULL, '2018-11-05 22:33:12', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('7', '角色查询', 'get:/roles', '/roles', 'get', 'i', 'role', 0, 'a', NULL, '2018-11-08 12:01:06', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('8', '获取单个角色', 'get:/role/{roleID}', '/role/{roleID}', 'get', 'i', 'role', 0, 'a', NULL, '2018-11-08 12:01:06', 'system');
INSERT INTO `cmn_auth_objects` VALUES ('9', '创建角色', 'post:/role', '/role', 'create', 'i', 'role', 0, 'a', NULL, '2018-11-08 12:01:06', 'system');

-- ----------------------------
-- Table structure for cmn_auth_org_menu
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_org_menu`;
CREATE TABLE `cmn_auth_org_menu`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `ORG_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织机构编码',
  `MENU_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单编码',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cmn_auth_org_permission
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_org_permission`;
CREATE TABLE `cmn_auth_org_permission`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `ORG_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '机构编码',
  `OBJ_CODE` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象编码',
  `OBJ_URI` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象定位',
  `OBJ_OPERATE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象操作',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `ORG_CODE`(`ORG_CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cmn_auth_role
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_role`;
CREATE TABLE `cmn_auth_role`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `ORG_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '组织机构编码',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `CODE`(`CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cmn_auth_role
-- ----------------------------
INSERT INTO `cmn_auth_role` VALUES ('1', '系统管理员', 'sys_admin', 'sys', '2018-11-05 22:25:48', 'system');
INSERT INTO `cmn_auth_role` VALUES ('2', '普通用户', 'normal_user', 'sys', '2018-10-22 15:41:29', 'system');
INSERT INTO `cmn_auth_role` VALUES ('21ebe8f1-ed41-48a1-b535-324319047846', 'xxxxffvccccc2', 'bfds4', '003', '2019-03-05 21:41:05', 'system');
INSERT INTO `cmn_auth_role` VALUES ('2a64f14c-5001-496c-8166-9684cae059a2', '修改配置', 'modify', 'gwh', '2019-03-27 09:15:22', 'system');
INSERT INTO `cmn_auth_role` VALUES ('8cdd5ed2-7662-46c5-bf98-caa642e400ce', '丝丝11', '2222', 'test0305', '2019-03-10 20:21:15', 'system');
INSERT INTO `cmn_auth_role` VALUES ('8fbdddd2-9829-4626-91eb-f68c81195331', '统一网关', 'testRole', 'gwh', '2019-03-13 13:27:00', 'system');

-- ----------------------------
-- Table structure for cmn_auth_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_role_menu`;
CREATE TABLE `cmn_auth_role_menu`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `ROLE_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `MENU_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单编码',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cmn_auth_role_menu
-- ----------------------------
INSERT INTO `cmn_auth_role_menu` VALUES ('01de1af8-a82f-41fd-a461-29003a393d2b', 'sys_admin', 'menu.monitor', '2019-03-05 17:37:22', 'liuxiujun');
INSERT INTO `cmn_auth_role_menu` VALUES ('32be3bff-ba20-40db-b14a-d9251554b93f', 'sys_admin', 'menu.roles', '2019-03-05 17:37:22', 'liuxiujun');
INSERT INTO `cmn_auth_role_menu` VALUES ('348008cb-af3e-4f67-b4ea-ed9669a1c2bb', 'sys_admin', 'menu.monitor', '2019-03-05 17:37:22', 'liuxiujun');
INSERT INTO `cmn_auth_role_menu` VALUES ('3a931500-df39-47b8-8ff9-1f6bb0f1d2b7', 'sys_admin', 'menu.service', '2019-03-05 17:37:22', 'liuxiujun');
INSERT INTO `cmn_auth_role_menu` VALUES ('5f39ec5f-77b4-4b6f-a089-6af9984a6359', 'sys_admin', 'menu.monitor.log', '2019-03-05 17:37:22', 'liuxiujun');
INSERT INTO `cmn_auth_role_menu` VALUES ('68611a0d-be43-4863-8c0b-191cdf599807', 'sys_admin', 'menu.monitor.infra', '2019-03-05 17:37:22', 'liuxiujun');
INSERT INTO `cmn_auth_role_menu` VALUES ('6d5920e2-af87-403c-8e4c-73a8dd930249', 'sys_admin', 'menu.monitor', '2019-03-05 17:37:22', 'liuxiujun');
INSERT INTO `cmn_auth_role_menu` VALUES ('82a18a77-17ca-4f1a-b204-d6fb7232abdd', 'sys_admin', 'menu.monitor.brk', '2019-03-05 17:37:22', 'liuxiujun');
INSERT INTO `cmn_auth_role_menu` VALUES ('9a0ed0f4-716a-4a78-87b2-e7d249be43ec', 'sys_admin', 'menu.monitor', '2019-03-05 17:37:22', 'liuxiujun');
INSERT INTO `cmn_auth_role_menu` VALUES ('aec20261-39a9-453a-b4ca-d1c89926e532', 'modify', 'menu.config', '2019-03-27 09:15:30', 'liujintao');
INSERT INTO `cmn_auth_role_menu` VALUES ('b3e98d19-43fa-4e78-9e58-c0d5f732152d', '2222', 'menu.config', '2019-03-10 20:24:04', 'liuxiujun');
INSERT INTO `cmn_auth_role_menu` VALUES ('be1ee11f-43ee-47b0-ad51-e02ac4266a31', 'sys_admin', 'menu.gateway', '2019-03-05 17:37:22', 'liuxiujun');
INSERT INTO `cmn_auth_role_menu` VALUES ('c44b9002-b380-493c-b462-01b126b548d6', 'testRole', 'menu.gateway', '2019-03-13 13:27:24', 'yusujuan');
INSERT INTO `cmn_auth_role_menu` VALUES ('dfb8fd06-9051-4daa-8906-3b9814c8b7df', 'sys_admin', 'menu.config', '2019-03-05 17:37:22', 'liuxiujun');
INSERT INTO `cmn_auth_role_menu` VALUES ('e03bb33c-e634-430d-a6b2-5c2b6061a258', 'sys_admin', 'menu.monitor.trace', '2019-03-05 17:37:22', 'liuxiujun');
INSERT INTO `cmn_auth_role_menu` VALUES ('e6f03e8e-e832-4bf6-aee5-b9994827f84f', 'sys_admin', 'menu.monitor', '2019-03-05 17:37:22', 'liuxiujun');

-- ----------------------------
-- Table structure for cmn_auth_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_role_permission`;
CREATE TABLE `cmn_auth_role_permission`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `ROLE_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `OBJ_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象编码',
  `OBJ_URI` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象定位',
  `OBJ_OPERATE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象操作',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `ROLE_CODE`(`ROLE_CODE`, `OBJ_CODE`) USING BTREE,
  INDEX `OBJ_CODE`(`OBJ_CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cmn_auth_role_permission
-- ----------------------------
INSERT INTO `cmn_auth_role_permission` VALUES ('100', 'leader_fg', 'post:/baseTicketInform', '/baseTicketInform', 'create', '2019-01-18 11:23:07', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('101', 'leader_fg', 'delete:/baseTicketInform', '/baseTicketInform', 'delete', '2019-01-18 11:23:11', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('102', 'leader_fg', 'put:/baseTicketInform', '/baseTicketInform', 'update', '2019-01-18 11:23:14', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('103', 'leader_fg', 'get:/baseTicketInform', '/baseTicketInform', 'query', '2019-01-18 11:23:17', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('104', 'leader_fg', 'get:/baseTicketInform/{serialNum}', '/baseTicketInform/{serialNum}', 'query', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('105', 'post_cwc', 'post:/baseTicketInform', '/baseTicketInform', 'create', '2019-01-18 11:23:07', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('106', 'post_cwc', 'delete:/baseTicketInform', '/baseTicketInform', 'delete', '2019-01-18 11:23:11', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('107', 'post_cwc', 'put:/baseTicketInform', '/baseTicketInform', 'update', '2019-01-18 11:23:14', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('108', 'post_cwc', 'get:/baseTicketInform', '/baseTicketInform', 'query', '2019-01-18 11:23:17', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('109', 'post_cwc', 'get:/baseTicketInform/{serialNum}', '/baseTicketInform/{serialNum}', 'query', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('110', 'post_jdzx', 'get:/baseTicketPrint', '/baseTicketPrint', 'query', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('111', 'post_jdzx', 'put:/baseTicketPrint', '/baseTicketPrint', 'create', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('112', 'post_jdzx', 'get:/baseTicketPrint/{baseTicketInformSerialNum}', '/baseTicketPrint/{baseTicketInformSerialNum}', 'query', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('113', 'post_jdzx', 'get:/baseTicketIssue', '/baseTicketIssue', 'query', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('114', 'post_jdzx', 'put:/baseTicketIssue', '/baseTicketIssue', 'update', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('115', 'post_jdzx', 'get:/baseTicketIssue/{baseTicketInformSerialNum}', '/baseTicketIssue/{baseTicketInformSerialNum}', 'query', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('116', 'post_zjjs', 'get:/baseTicketPrint', '/baseTicketPrint', 'query', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('117', 'post_zjjs', 'put:/baseTicketPrint', '/baseTicketPrint', 'create', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('118', 'post_zjjs', 'get:/baseTicketPrint/{baseTicketInformSerialNum}', '/baseTicketPrint/{baseTicketInformSerialNum}', 'query', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('119', 'post_zjjs', 'get:/baseTicketIssue', '/baseTicketIssue', 'query', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('120', 'post_zjjs', 'put:/baseTicketIssue', '/baseTicketIssue', 'update', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('121', 'post_zjjs', 'get:/baseTicketIssue/{baseTicketInformSerialNum}', '/baseTicketIssue/{baseTicketInformSerialNum}', 'query', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('122', 'post_cwc', 'get:/baseTicketIssue', '/baseTicketIssue', 'query', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('123', 'post_cwc', 'put:/baseTicketIssue', '/baseTicketIssue', 'update', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('124', 'post_cwc', 'get:/baseTicketIssue/{baseTicketInformSerialNum}', '/baseTicketIssue/{baseTicketInformSerialNum}', 'query', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('125', 'committee', 'get:/baseTicketIssue', '/baseTicketIssue', 'query', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('126', 'committee', 'put:/baseTicketIssue', '/baseTicketIssue', 'update', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('127', 'committee', 'get:/baseTicketIssue/{baseTicketInformSerialNum}', '/baseTicketIssue/{baseTicketInformSerialNum}', 'query', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('128', 'post_zjjs', 'get:/baseTicketInform/{serialNum}', '/baseTicketInform/{serialNum}', 'query', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('129', 'post_jdzx', 'get:/baseTicketInform/{serialNum}', '/baseTicketInform/{serialNum}', 'query', '2019-01-18 11:23:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('131', 'certificate_unit_proposer', 'get:/user_menu/{user}', '/user_menu/{user}', 'get', '2019-01-16 15:43:44', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('132', 'certificate_unit_leader', 'get:/user_menu/{user}', '/user_menu/{user}', 'get', '2019-01-17 11:33:05', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('133', 'ticket_manager_leader', 'post:/takeTicketSecTrial', '/takeTicketSecTrial', 'post', '2019-01-17 13:56:15', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('134', 'certificate_unit_proposer', 'post:/addShipcertificateApply', '/addShipcertificateApply', 'post', '2018-12-08 16:04:06', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('135', 'certificate_unit_proposer', 'post:/updateShipcertificateApply', '/updateShipcertificateApply', 'post', '2018-12-08 16:08:26', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('136', 'certificate_unit_proposer', 'delete:/deleteShipcertificateApply', '/deleteShipcertificateApply', 'delete', '2018-12-08 16:08:50', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('137', 'certificate_unit_proposer', 'put:/revertShipcertificateApply', '/revertShipcertificateApply', 'put', '2018-12-08 16:09:10', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('138', 'certificate_unit_proposer', 'get:/shipcertificateApplyList', '/shipcertificateApplyList', 'get', '2018-12-08 16:09:28', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('139', 'certificate_unit_proposer', 'get:/shipcertificateApply/{serialNum}', '/shipcertificateApply/{serialNum}', 'get', '2018-12-08 16:09:50', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('140', 'certificate_unit_leader', 'get:/shipcertificateFirsttrialList', '/shipcertificateFirsttrialList', 'get', '2018-12-08 16:10:44', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('141', 'certificate_unit_leader', 'post:/shipcertificateFirsttrialApproval', '/shipcertificateFirsttrialApproval', 'post', '2018-12-08 16:11:13', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('142', 'certificate_unit_leader', 'post:/shipcertificateFirsttrialApprovalBatch', '/shipcertificateFirsttrialApprovalBatch', 'post', '2018-12-08 16:11:31', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('143', 'certificate_unit_leader', 'get:/shipcertificateFirsttrial/{serialNum}', '/shipcertificateFirsttrial/{serialNum}', 'get', '2018-12-08 16:11:55', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('144', 'certificate_manager_leader', 'get:/searchAllforSecTrial', '/searchAllforSecTrial', 'get', '2018-12-08 16:12:35', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('145', 'ticket_manager_leader', 'get:/getoneShipTicketSecTria/{serialNum}', '/getoneShipTicketSecTria/{serialNum}', 'get', '2018-12-14 21:40:31', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('146', 'certificate_manager_leader', 'post:/secTrialSingle', '/secTrialSingle', 'post', '2018-12-20 10:29:18', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('147', 'certificate_unit_proposer', 'get:/dictionary/getList/{diccode}', '/dictionary/getList/{diccode}', 'get', '2018-12-21 14:11:36', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('148', 'ticket_unit_proposer', 'get:/dictionary/getTicketList/{diccode}', '/dictionary/getTicketList/{diccode}', 'get', '2019-01-17 13:05:57', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('149', 'ticket_unit_proposer', 'get:/getTakeTicketApplyTable', '/getTakeTicketApplyTable', 'get', '2018-12-14 21:25:13', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('150', 'ticket_unit_proposer', 'post:/addTakeTicketApply', '/addTakeTicketApply', 'post', '2018-12-14 21:26:15', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('151', 'ticket_unit_proposer', 'delete:/deleteTakeTicketApply', '/deleteTakeTicketApply', 'delete', '2018-12-14 21:27:17', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('152', 'ticket_unit_proposer', 'put:/editTakeTicketApply', '/editTakeTicketApply', 'put', '2018-12-14 21:29:08', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('153', 'ticket_unit_proposer', 'get:/getTakeTicketApplyDetails/{serialNum}', '/getTakeTicketApplyDetails/{serialNum}', 'get', '2018-12-14 21:29:44', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('154', 'ticket_unit_proposer', 'put:/revertTakeTicketApply', '/revertTakeTicketApply', 'put', '2018-12-14 21:31:35', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('155', 'ticket_unit_leader', 'get:/taketicketFirtrialList', '/taketicketFirtrialList', 'get', '2018-12-14 21:32:30', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('156', 'ticket_unit_leader', 'post:/taketicketFirtrialApprovalAll', '/taketicketFirtrialApprovalAll', 'post', '2018-12-14 21:34:45', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('157', 'ticket_unit_leader', 'get:/takeTicketFirtrial/{serialNum}', '/takeTicketFirtrial/{serialNum}', 'get', '2018-12-14 21:35:26', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('158', 'ticket_unit_leader', 'post:/takeTicketFirtrialApproval', '/takeTicketFirtrialApproval', 'post', '2018-12-14 21:36:59', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('159', 'ticket_unit_leader', 'get:/getTakeTicketSecTrialTable', '/getTakeTicketSecTrialTable', 'get', '2018-12-14 21:37:35', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('160', 'ticket_manager_leader', 'get:/getTakeTicketSecTrialTable', '/getTakeTicketSecTrialTable', 'post', '2018-12-14 21:38:49', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('161', 'ticket_manager_leader', 'post:/shipTicketSecTrialApprovalAll', '/shipTicketSecTrialApprovalAll', 'post', '2018-12-14 21:39:20', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('162', 'certificate_manager_leader', 'get:/shipcertificateSectrial/{serialNum}', '/shipcertificateSectrial/{serialNum}', 'get', '2018-12-14 21:40:31', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('163', 'certificate_manager_leader', 'post:/secTrialBatch', '/secTrialBatch', 'post', '2018-12-21 16:52:57', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('164', 'certificate-cancel', 'get:/cancel', '/cancel', 'query', '2018-12-21 16:52:57', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('165', 'certificate-cancel', 'get:/findCancel', '/findCancel', 'query', '2018-12-21 16:52:57', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('166', 'certificate-cancel', 'put:/cancel', '/cancel', 'update', '2018-12-21 16:52:57', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('167', 'certificate-cancel', 'delete:/cancel', '/cancel', 'delete', '2018-12-21 16:52:57', 'system');
INSERT INTO `cmn_auth_role_permission` VALUES ('34507b73-9cf7-4cfe-bc77-9558f3135f89', '2222', 'get:/discovery/eureka', '/discovery/eureka', 'geteu', '2019-03-10 20:23:44', 'liuxiujun');
INSERT INTO `cmn_auth_role_permission` VALUES ('f97c2721-58b5-47fc-8ba7-65119de26524', 'normal_user', 'get:/discovery/eureka/info', '/discovery/eureka/info', 'geteui', '2019-03-05 21:17:36', 'liuxiujun');

-- ----------------------------
-- Table structure for cmn_auth_user_menu
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_user_menu`;
CREATE TABLE `cmn_auth_user_menu`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `USER_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户编码',
  `MENU_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单编码',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cmn_auth_user_permission
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_user_permission`;
CREATE TABLE `cmn_auth_user_permission`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `USER_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户编码',
  `OBJ_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '对象编码',
  `OBJ_URI` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象定位',
  `OBJ_OPERATE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象操作',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `USER_CODE`(`USER_CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cmn_auth_user_permission
-- ----------------------------
INSERT INTO `cmn_auth_user_permission` VALUES ('1', 'liuzhuo', 'put:/manage/user', 'manage/user', 'create', '2018-10-22 19:24:47', 'system');

-- ----------------------------
-- Table structure for cmn_auth_user_role
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_user_role`;
CREATE TABLE `cmn_auth_user_role`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `USER_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户系统ID',
  `USER_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户编码',
  `ROLE_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `USER_CODE`(`USER_CODE`, `ROLE_CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cmn_auth_user_role
-- ----------------------------
INSERT INTO `cmn_auth_user_role` VALUES ('1854a30e-f0e5-4ada-8aa8-b7876350ea18', 'b164e961f4c3450bbbf2717cbb73a7b1', 'linmingxiong', 'bfds4', '2019-03-05 23:25:52', 'system');
INSERT INTO `cmn_auth_user_role` VALUES ('62063125-75c0-4520-947b-e516cd5feb81', 'dadc2fa472e244a6bbf48b3308dad892', 'zhangsan02', 'bfds4', '2019-03-05 23:26:00', 'system');
INSERT INTO `cmn_auth_user_role` VALUES ('cef5d55d-c558-41dc-8db3-781781ca37ff', '285ecf9e1a3a4843a1793b2397af082b', 'sunxiangyou', 'testRole', '2019-03-13 13:27:38', 'system');

SET FOREIGN_KEY_CHECKS = 1;
