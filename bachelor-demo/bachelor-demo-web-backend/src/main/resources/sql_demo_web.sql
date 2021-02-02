/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : eprs_contact

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 02/02/2021 18:10:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cmn_acm_menu
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_menu`;
CREATE TABLE `cmn_acm_menu`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单编码',
  `URI` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单定位',
  `ICON` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标路径',
  `TYPE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单类型',
  `PARENT_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级ID',
  `COMMENT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '说明',
  `SEQ_ORDER` decimal(2, 0) NOT NULL COMMENT '排序',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_acm_menu
-- ----------------------------

-- ----------------------------
-- Table structure for cmn_acm_obj_domain
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_obj_domain`;
CREATE TABLE `cmn_acm_obj_domain`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '域名称',
  `CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '域编码',
  `IS_SYS` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否系统默认',
  `SEQ_ORDER` decimal(2, 0) NOT NULL COMMENT '排序',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `CODE`(`CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '要有初始化数据' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_acm_obj_domain
-- ----------------------------

-- ----------------------------
-- Table structure for cmn_acm_obj_operation
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_obj_operation`;
CREATE TABLE `cmn_acm_obj_operation`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作名称',
  `CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作编码',
  `METHOD` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'GET' COMMENT '对应的HTTP METHOD',
  `IS_SYS` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否系统默认',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `CODE`(`CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '要有初始化数据' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_acm_obj_operation
-- ----------------------------

-- ----------------------------
-- Table structure for cmn_acm_obj_permission
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_obj_permission`;
CREATE TABLE `cmn_acm_obj_permission`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象名称',
  `CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对象编码',
  `URI` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象定位',
  `OPERATE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象操作',
  `TYPE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象类型',
  `DOMAIN_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属域编码',
  `IS_SYS` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否系统默认',
  `SEQ_ORDER` int(0) NOT NULL DEFAULT 0 COMMENT '排序',
  `DEF_AUTH_OP` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'c' COMMENT '默认权限行为',
  `COMMENT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明',
  `UPDATE_TIME` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `NAME`(`NAME`) USING BTREE,
  UNIQUE INDEX `CODE`(`CODE`) USING BTREE,
  INDEX `URI`(`URI`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_acm_obj_permission
-- ----------------------------

-- ----------------------------
-- Table structure for cmn_acm_org_menu
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_org_menu`;
CREATE TABLE `cmn_acm_org_menu`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `ORG_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织机构编码',
  `MENU_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单编码',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_acm_org_menu
-- ----------------------------

-- ----------------------------
-- Table structure for cmn_acm_org_permission
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_org_permission`;
CREATE TABLE `cmn_acm_org_permission`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `ORG_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '机构编码',
  `OBJ_CODE` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象编码',
  `OBJ_URI` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象定位',
  `OBJ_OPERATE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象操作',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `ORG_CODE`(`ORG_CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_acm_org_permission
-- ----------------------------

-- ----------------------------
-- Table structure for cmn_acm_role
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_role`;
CREATE TABLE `cmn_acm_role`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `ORG_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织机构编码',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `CODE`(`CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_acm_role
-- ----------------------------

-- ----------------------------
-- Table structure for cmn_acm_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_role_menu`;
CREATE TABLE `cmn_acm_role_menu`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `ROLE_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `MENU_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单编码',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_acm_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for cmn_acm_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_role_permission`;
CREATE TABLE `cmn_acm_role_permission`  (
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_acm_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for cmn_acm_user_menu
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_user_menu`;
CREATE TABLE `cmn_acm_user_menu`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `USER_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户编码',
  `MENU_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单编码',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_acm_user_menu
-- ----------------------------

-- ----------------------------
-- Table structure for cmn_acm_user_permission
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_user_permission`;
CREATE TABLE `cmn_acm_user_permission`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `USER_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户编码',
  `OBJ_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对象编码',
  `OBJ_URI` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象定位',
  `OBJ_OPERATE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象操作',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `USER_CODE`(`USER_CODE`, `OBJ_CODE`) USING BTREE,
  INDEX `OBJ_CODE`(`OBJ_CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_acm_user_permission
-- ----------------------------

-- ----------------------------
-- Table structure for cmn_acm_user_role
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_user_role`;
CREATE TABLE `cmn_acm_user_role`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `USER_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户系统ID',
  `USER_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户编码',
  `ROLE_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `USER_CODE`(`USER_CODE`, `ROLE_CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_acm_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for cmn_auth_menu
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_menu`;
CREATE TABLE `cmn_auth_menu`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单编码',
  `URI` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单定位',
  `ICON` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标路径',
  `TYPE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单类型',
  `PARENT_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级ID',
  `COMMENT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '说明',
  `SEQ_ORDER` decimal(2, 0) NOT NULL COMMENT '排序',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通讯录系统的菜单' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_auth_menu
-- ----------------------------
INSERT INTO `cmn_auth_menu` VALUES ('1', '通讯录管理', '01', '/', 'icon-crgl', '1', NULL, '通讯录管理', 1, '2020-12-30 10:43:24', 'gxf');
INSERT INTO `cmn_auth_menu` VALUES ('2', '园区通讯录', '01_02', '/parkPhoneBook', 'icon-crsq', '2', '1', '园区通讯录', 2, '2020-12-30 10:44:59', 'gxf');
INSERT INTO `cmn_auth_menu` VALUES ('3', '系统管理', '03', '/', 'icon-shez', '1', NULL, '系统管理', 1, '2020-12-30 11:25:33', 'gxf');
INSERT INTO `cmn_auth_menu` VALUES ('4', '用户权限', '03_01', '/userRight', 'icon-yhqx', '2', '3', '用户权限', 1, '2020-12-30 10:44:59', 'gxf');
INSERT INTO `cmn_auth_menu` VALUES ('5', '组织机构', '01_01', '/orgPhoneBook', 'icon-crsq', '2', '1', '组织机构', 1, '2020-12-30 10:44:59', 'gxf');

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '要有初始化数据' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_auth_obj_domain
-- ----------------------------
INSERT INTO `cmn_auth_obj_domain` VALUES ('19', '通讯录', 'contact', 19, '2021-01-11 17:03:38', 'gxf');
INSERT INTO `cmn_auth_obj_domain` VALUES ('20', '通讯录组织机构', 'contact_org', 20, '2021-01-12 15:16:07', 'gxf');

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '要有初始化数据' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_auth_obj_operation
-- ----------------------------
INSERT INTO `cmn_auth_obj_operation` VALUES ('1', '创建', 'create', 'POST', b'1', '2020-08-13 15:05:26', 'zjh');
INSERT INTO `cmn_auth_obj_operation` VALUES ('2', '更新', 'update', 'PUT', b'1', '2020-08-13 15:06:07', 'zjh');
INSERT INTO `cmn_auth_obj_operation` VALUES ('3', '查询', 'query', 'GET', b'1', '2020-08-13 15:06:51', 'zjh');
INSERT INTO `cmn_auth_obj_operation` VALUES ('4', '删除', 'delete', 'DELETE', b'1', '2020-08-13 15:07:18', 'zjh');
INSERT INTO `cmn_auth_obj_operation` VALUES ('5', '综合查询', 'multicond_query', 'POST', b'0', '2020-08-13 15:06:51', 'zjh');
INSERT INTO `cmn_auth_obj_operation` VALUES ('6', '组织机构树查询', 'contact_orgtree_query', 'GET', b'0', '2020-08-13 15:06:51', 'zjh');

-- ----------------------------
-- Table structure for cmn_auth_objects
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_objects`;
CREATE TABLE `cmn_auth_objects`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象名称',
  `CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对象编码',
  `URI` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象定位',
  `OPERATE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象操作',
  `TYPE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象类型',
  `DOMAIN_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属域编码',
  `SEQ_ORDER` int(0) NOT NULL DEFAULT 0 COMMENT '排序',
  `DEF_AUTH_OP` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'c' COMMENT '默认权限行为',
  `COMMENT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明',
  `UPDATE_TIME` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `NAME`(`NAME`) USING BTREE,
  UNIQUE INDEX `CODE`(`CODE`) USING BTREE,
  INDEX `URI`(`URI`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_auth_objects
-- ----------------------------
INSERT INTO `cmn_auth_objects` VALUES ('23', '权限相关23', 'get:/user/show', '/user/show', 'query', 'i', 'user', 0, 'a', '1', '2021-01-12 08:41:53', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('24', '权限相关24', 'post:/user/refreshToken', '/user/refreshToken', 'create', 'i', 'user', 0, 'a', '1', '2021-01-12 08:41:54', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('25', '获得用户的权限', 'get:/user_permission/{user}', '/user_permission/:userCode', 'query', 'i', 'user_permission', 0, 'l', '1', '2021-01-12 15:50:44', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('26', '权限相关26', 'get:/user_menu/{user}', '/user_menu/{user}', 'query', 'i', 'user', 0, 'a', '1', '2021-01-12 14:51:46', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('27', '\r\n查询用户', 'get:/roles/{userCode}', '/roles/:userCode', 'query', 'i', 'role', 1, 'l', '1', '2021-01-12 15:59:00', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('28', '权限相关28', 'get:/user_sys/app/workbench/url', '/user_sys/app/workbench/url', 'query', 'i', 'user_sys', 0, 'c', '1', '2020-08-13 10:26:33', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('29', '权限相关29', 'get:/role_user/sys_admin', 'role_user/sys_admin', 'query', 'i', 'role_user', 0, 'c', '1', '2020-08-26 17:05:31', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('31', '获得机构列表', 'get:/user_sys/orgs', 'user_sys/orgs', 'query', 'i', 'user_sys', 0, 'l', '1', '2021-01-12 15:58:19', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('32', '获得机构下部门列表', 'get:/user_sys/depts', '/user_sys/depts', 'query', 'i', 'user_sys', 0, 'c', '1', '2020-08-26 17:05:31', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('33', '查询用户', 'get:/user_sys/users', '/user_sys/users', 'query', 'i', 'user_sys', 0, 'c', '1', '2020-08-26 17:05:31', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('34', '查询角色', 'get:/roles', '/roles', 'query', 'i', 'role', 0, 'l', '1', '2021-01-12 15:58:56', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('35', '根据角色ID获取角色', 'get:/role/{roleID}', '/role/{roleID}', 'query', 'i', 'role', 0, 'l', '1', '2021-01-12 15:49:41', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('36', '删除角色', 'delete:/role/{roleID}', '/role/{roleID}', 'delete', 'i', 'role', 0, 'c', '1', '2021-01-12 15:48:32', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('37', '修改角色', 'put:/role', '/role', 'update', 'i', 'role', 0, 'c', '1', '2020-08-26 17:05:31', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('38', '\r\n创建角色', 'post:/role', '/role', 'create', 'i', 'role', 0, 'c', '1', '2020-08-26 17:05:31', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('39', '获取角色下的用户', 'get:/role_user/{roleCode}', '/role_user/{roleCode}', 'query', 'i', 'role_user', 0, 'c', '1', '2021-01-12 15:49:09', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('40', '将用户从角色中删除', 'put:/role_user/{roleCode}', '/role_user/{roleCode}', 'update', 'i', 'role_user', 0, 'c', '1', '2021-01-12 15:49:12', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('41', '为角色增加用户', 'post:/role_user/{roleCode}', '/role_user/{roleCode}', 'create', 'i', 'role_user', 0, 'c', '1', '2021-01-12 15:49:17', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('42', '\r\n获得全部权限', 'get:/permissions', '/permissions', 'query', 'i', 'role_permission', 0, 'a', '1', '2021-01-12 08:42:17', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('43', '获得角色的权限', 'get:/role_permission/{role}', '/role_permission/{role}', 'query', 'i', 'role_permission', 0, 'a', '1', '2021-01-12 15:48:53', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('44', '设置角色的权限', 'post:/role_permission/{role}', '/role_permission/{role}', 'create', 'i', 'role_permission', 0, 'a', '1', '2021-01-12 15:48:59', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('45', '获得全部菜单', 'get:/menus', '/menus', 'query', 'i', 'role_menu', 0, 'a', '1', '2021-01-12 08:42:11', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('46', '获得角色的菜单', 'get:/role_menu/{role}', '/role_menu/{role}', 'query', 'i', 'role_menu', 0, 'a', '1', '2021-01-12 15:48:43', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('47', '设置角色的菜单', 'post:/role_menu/{role}', '/role_menu/{role}', 'create', 'i', 'role_menu', 0, 'a', '1', '2021-01-12 15:48:47', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('48', '权限相关48', 'get:/userinfo/user', '/userinfo/user', 'query', 'i', 'userinfo', 0, 'a', '1', '2021-01-12 08:42:06', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('49', '权限相关49', 'get:/userinfo/user/{id}', '/userinfo/user/:id', 'query', 'i', 'userinfo', 0, 'a', '1', '2021-01-12 08:42:04', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('50', '权限相关50', 'delete:/userinfo/user/{id}', '/userinfo/user/:id', 'delete', 'i', 'userinfo', 0, 'a', '1', '2021-01-12 08:41:32', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('51', '权限相关51', 'post:/userinfo/user', '/userinfo/user', 'create', 'i', 'userinfo', 0, 'a', '1', '2021-01-12 08:41:33', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('52', '权限相关52', 'put:/userinfo/user', '/userinfo/user', 'update', 'i', 'userinfo', 0, 'a', '1', '2021-01-12 08:41:34', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('65', '获取用户详情', 'get:/userinfo/getUsersDetail', '/userinfo/getUsersDetail', 'query', 'i', 'user_info', 0, 'a', '1', '2021-01-12 08:41:35', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('66', '获取role_code', 'get:/userinfo/findUserRole', '/userinfo/findUserRole', 'query', 'i', 'user_info', 0, 'a', '1', '2021-01-12 08:41:35', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('68', '组织机构树展示', 'get:/contact/organization/findOrganizationTree', '/contact/organization/findOrganizationTree', 'contact_orgtree_query', 'i', 'contact_org', 0, 'c', '1', '2021-01-12 16:09:15', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('69', '通讯录查询', 'post:/contact/params', '/contact/params', 'multicond_query', 'i', 'contact', 0, 'c', '1', '2021-01-12 16:09:27', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('70', '组织机构列表展示', 'post:/contact/organization/params', '/contact/organization/params', 'multicond_query', 'i', 'contact_org', 0, 'c', '1', '2021-01-12 16:45:06', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('71', '删除组织机构', 'delete:/contact/organization/{id}', '/contact/organization/{id}', 'delete', 'i', 'contact_org', 0, 'c', '1', '2021-01-12 16:44:53', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('72', '修改组织机构', 'put:/contact/organization', '/contact/organization', 'update', 'i', 'contact_org', 0, 'c', '1', '2021-01-12 16:44:26', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('73', '\r\n创建组织机构', 'post:/contact/organization', '/contact/organization', 'create', 'i', 'contact_org', 0, 'c', '1', '2021-01-12 16:44:32', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('74', '删除通讯录', 'delete:/contact/{id}', '/contact/{id}', 'delete', 'i', 'contact', 0, 'c', '1', '2021-01-12 16:09:22', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('75', '修改通讯录', 'put:/contact', '/contact', 'update', 'i', 'contact', 0, 'c', '1', '2021-01-12 16:09:21', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('76', '\r\n创建通讯录', 'post:/contact', '/contact', 'create', 'i', 'contact', 0, 'c', '1', '2021-01-12 16:09:19', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('77', '根据id获取通讯录', 'get:/contact/{id}', '/contact/{id}', 'query', 'i', 'contact', 0, 'c', '1', '2021-01-12 16:09:17', 'q');
INSERT INTO `cmn_auth_objects` VALUES ('78', '根据id组织机构', 'get:/contact/organization/{id}', '/contact/organization/{id}', 'query', 'i', 'contact_org', 0, 'c', '1', '2021-01-12 16:40:33', 'q');

-- ----------------------------
-- Table structure for cmn_auth_org_menu
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_org_menu`;
CREATE TABLE `cmn_auth_org_menu`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `ORG_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织机构编码',
  `MENU_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单编码',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组织机构和菜单的关系' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_auth_org_menu
-- ----------------------------

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组织机构和路由的关系' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_auth_org_permission
-- ----------------------------

-- ----------------------------
-- Table structure for cmn_auth_role
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_role`;
CREATE TABLE `cmn_auth_role`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `ORG_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织机构编码',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `CODE`(`CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通讯录角色' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_auth_role
-- ----------------------------
INSERT INTO `cmn_auth_role` VALUES ('023ea370-39f7-436f-b73e-dc6fc885b5fe', '管理员', 'txl_admin', 'dygorg_txl', '2020-12-04 15:29:59', 'system');
INSERT INTO `cmn_auth_role` VALUES ('3a1ef6a6-2390-4243-89c5-a7aa4b9739a9', '值班人员', 'txl_user', 'dygorg_txl', '2020-12-08 10:43:49', 'system');

-- ----------------------------
-- Table structure for cmn_auth_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_role_menu`;
CREATE TABLE `cmn_auth_role_menu`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `ROLE_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `MENU_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单编码',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通讯录角色和菜单对应关系' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_auth_role_menu
-- ----------------------------
INSERT INTO `cmn_auth_role_menu` VALUES ('0ba38eb8-a5e8-4872-be05-849e1579a326', 'txl_user', '03', '2021-01-12 15:45:57', 'txl_admin');
INSERT INTO `cmn_auth_role_menu` VALUES ('15906d6d-cf37-4be9-ba1b-12353030771a', 'txl_admin', '01', '2021-01-12 16:49:29', 'txl_admin');
INSERT INTO `cmn_auth_role_menu` VALUES ('1c877a9e-91e8-42da-8c9e-a1a0511bccbd', 'txl_admin', '03', '2021-01-12 16:49:29', 'txl_admin');
INSERT INTO `cmn_auth_role_menu` VALUES ('2a4b31cc-1e7c-4038-bba2-874b2aea32c6', 'txl_admin', '01', '2021-01-12 16:49:29', 'txl_admin');
INSERT INTO `cmn_auth_role_menu` VALUES ('47739133-ff2f-4509-8179-c898278c956e', 'txl_admin', '03', '2021-01-12 16:49:29', 'txl_admin');
INSERT INTO `cmn_auth_role_menu` VALUES ('653712ee-51f3-4b3e-b879-a6408cce90d4', 'txl_user', '03_01', '2021-01-12 15:45:56', 'txl_admin');
INSERT INTO `cmn_auth_role_menu` VALUES ('792a3528-0279-4a85-9a6c-274c06b295ac', 'txl_admin', '03_01', '2021-01-12 16:49:29', 'txl_admin');
INSERT INTO `cmn_auth_role_menu` VALUES ('89665765-0297-4533-9e5d-e4d682e73ce6', 'txl_admin', '01_02', '2021-01-12 16:49:29', 'txl_admin');
INSERT INTO `cmn_auth_role_menu` VALUES ('8eaa9254-4a43-4ad3-899d-3ad1f42fc3d7', 'txl_user', '01', '2021-01-12 15:45:56', 'txl_admin');
INSERT INTO `cmn_auth_role_menu` VALUES ('912deb32-cf65-4c3e-8aff-c5610ff370f8', 'txl_admin', '01_01', '2021-01-12 16:49:29', 'txl_admin');
INSERT INTO `cmn_auth_role_menu` VALUES ('99f8e976-f4fd-46e0-be34-836505d99c23', 'txl_user', '01_01', '2021-01-12 15:45:55', 'txl_admin');
INSERT INTO `cmn_auth_role_menu` VALUES ('dbb40d53-b83d-4550-a4a7-ff46b426c33b', 'txl_user', '03', '2021-01-12 15:45:55', 'txl_admin');
INSERT INTO `cmn_auth_role_menu` VALUES ('e77a5dde-5af5-4666-bdac-37bd8eb7a528', 'txl_admin', '01', '2021-01-12 16:49:29', 'txl_admin');

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和路由关系' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_auth_role_permission
-- ----------------------------
INSERT INTO `cmn_auth_role_permission` VALUES ('1289c635-34af-43bd-98ad-fcc605760cad', 'txl_admin', 'post:/contact/organization', '/contact/organization', 'create', '2021-01-12 16:49:29', 'txl_admin');
INSERT INTO `cmn_auth_role_permission` VALUES ('3098f6b1-a545-4ec2-a7a5-069ccbb290da', 'txl_admin', 'put:/contact/organization', '/contact/organization', 'update', '2021-01-12 16:49:29', 'txl_admin');
INSERT INTO `cmn_auth_role_permission` VALUES ('3ce97679-c6ee-4055-a6bc-deae25b5045e', 'txl_admin', 'delete:/contact/{id}', '/contact/{id}', 'delete', '2021-01-12 16:09:53', 'txl_admin');
INSERT INTO `cmn_auth_role_permission` VALUES ('4894bbd8-ecb9-47d0-bf22-ce39ad72c767', 'txl_admin', 'put:/contact', '/contact', 'update', '2021-01-12 16:22:23', 'txl_admin');
INSERT INTO `cmn_auth_role_permission` VALUES ('4b062bdb-a837-44a6-8910-e1997bc15fff', 'txl_admin', 'post:/contact/params', '/contact/params', 'multicond_query', '2021-01-12 16:09:53', 'txl_admin');
INSERT INTO `cmn_auth_role_permission` VALUES ('7823044e-7e46-4820-b3a3-e5fe31b00049', 'txl_admin', 'delete:/contact/organization/{id}', '/contact/organization/{id}', 'delete', '2021-01-12 16:49:29', 'txl_admin');
INSERT INTO `cmn_auth_role_permission` VALUES ('81d58d94-b1fb-4c50-9270-174f4df98e0d', 'txl_admin', 'get:/contact/organization/{id}', '/contact/organization/{id}', 'query', '2021-01-12 16:42:29', 'txl_admin');
INSERT INTO `cmn_auth_role_permission` VALUES ('85a37dac-9bc4-4e59-98d6-44f0486ec501', 'txl_admin', 'post:/contact/organization/params', '/contact/organization/params', 'multicond_query', '2021-01-12 16:49:28', 'txl_admin');
INSERT INTO `cmn_auth_role_permission` VALUES ('977e4e8d-962c-45d6-a297-f2ea36234754', 'txl_admin', 'get:/contact/organization/findOrganizationTree', '/contact/organization/findOrganizationTree', 'contact_orgtree_query', '2021-01-12 16:09:53', 'txl_admin');
INSERT INTO `cmn_auth_role_permission` VALUES ('a44b07be-0be7-4fcb-803a-e847588c179c', 'txl_admin', 'post:/contact', '/contact', 'create', '2021-01-12 16:09:53', 'txl_admin');
INSERT INTO `cmn_auth_role_permission` VALUES ('c64b1b15-5952-456f-bcb2-d28f0a948850', 'txl_admin', 'get:/contact/{id}', '/contact/{id}', 'query', '2021-01-12 16:09:53', 'txl_admin');

-- ----------------------------
-- Table structure for cmn_auth_user_menu
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_user_menu`;
CREATE TABLE `cmn_auth_user_menu`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `USER_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户编码',
  `MENU_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单编码',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通讯录用户和菜单关系' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_auth_user_menu
-- ----------------------------

-- ----------------------------
-- Table structure for cmn_auth_user_permission
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_user_permission`;
CREATE TABLE `cmn_auth_user_permission`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `USER_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户编码',
  `OBJ_CODE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对象编码',
  `OBJ_URI` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象定位',
  `OBJ_OPERATE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象操作',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `USER_CODE`(`USER_CODE`, `OBJ_CODE`) USING BTREE,
  INDEX `OBJ_CODE`(`OBJ_CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通讯录用户和路由关系' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_auth_user_permission
-- ----------------------------
INSERT INTO `cmn_auth_user_permission` VALUES ('c52866ca-6154-4a84-aecf-5d6b5cf91f98', 'txl_admin', 'get:/userinfo/findUserRole', '/userinfo/findUserRole', 'query', '2020-12-12 15:51:45', 'park_admin');
INSERT INTO `cmn_auth_user_permission` VALUES ('d08fbff0-2920-4a27-85-1d28bfaaadsff', 'txl_admin', 'get:/organization/findOrganizationTree', '/organization/findOrganizationTree', 'query', '2021-01-09 11:27:41', 'gxf');
INSERT INTO `cmn_auth_user_permission` VALUES ('d08fbff0-2920-4a27-85d4-1d28bfaaade1', 'txl_user', 'get:/userinfo/findUserRole', '/userinfo/findUserRole', 'query', '2020-12-10 09:51:28', 'park_company');
INSERT INTO `cmn_auth_user_permission` VALUES ('d08fbff0-2920-4a27-85d4-1d28bfaaade2', 'txl_user', 'get:/organization/findOrganizationTree', '/organization/findOrganizationTree', 'query', '2020-12-10 09:51:28', 'gxf');

-- ----------------------------
-- Table structure for cmn_auth_user_role
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_user_role`;
CREATE TABLE `cmn_auth_user_role`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `USER_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户系统ID',
  `USER_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户编码',
  `ROLE_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `USER_CODE`(`USER_CODE`, `ROLE_CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通讯录用户和角色关系' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cmn_auth_user_role
-- ----------------------------
INSERT INTO `cmn_auth_user_role` VALUES ('348d5f60-9aaf-43e2-a6a1-9230b01257bc', '76ababe9efe6498a919029e6cd0bd9b0', 'txl_user', 'txl_user', '2020-12-04 16:08:16', 'system');
INSERT INTO `cmn_auth_user_role` VALUES ('79da63cb-e544-4101-aaf9-727078ffda11', '41924961b7c14ddd8669c2f1553a51b7', 'clyy_admin', 'txl_admin', '2020-12-30 10:58:44', 'system');
INSERT INTO `cmn_auth_user_role` VALUES ('f353a0f6-56e7-4620-81f7-d27ae68aa638', '41924961b7c14ddd8669c2f1553a51b6', 'txl_admin', 'txl_user', '2020-12-08 10:40:46', 'system');
INSERT INTO `cmn_auth_user_role` VALUES ('f353a0f6-56e7-4620-81f7-d27ae68aa639', '41924961b7c14ddd8669c2f1553a51b6', 'txl_admin', 'txl_admin', '2020-12-08 10:40:46', 'system');

-- ----------------------------
-- Table structure for eprs_contacts_contact
-- ----------------------------
DROP TABLE IF EXISTS `eprs_contacts_contact`;
CREATE TABLE `eprs_contacts_contact`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `b_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `gender` tinyint(1) NOT NULL DEFAULT 0 COMMENT '性别1男2女0保密',
  `office_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '办公电话',
  `home_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '家庭电话',
  `home_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '家庭地址',
  `group_id` int(0) NOT NULL COMMENT '所属分类id book_group表的id',
  `office_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作单位',
  `department` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门',
  `b_status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否有效：默认为是（1），否（0）',
  `sort_num` int(0) NOT NULL DEFAULT 0 COMMENT '排序号：数字越大越排在前面，默认为0',
  `add_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `add_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '添加人id',
  `update_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 564 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通讯录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of eprs_contacts_contact
-- ----------------------------
INSERT INTO `eprs_contacts_contact` VALUES (1, '上', 'string', 'string', 0, 'string', 'string', 'string', 1, 'string', 'string', 0, 0, '2020-12-24 15:48:22', '2020-12-25 15:39:16', 'string', 'string');
INSERT INTO `eprs_contacts_contact` VALUES (2, '上官', 'string', 'string', 0, 'string', 'string', 'string', 2, 'string', 'string', 0, 0, '2020-12-25 02:03:01', '2020-12-25 15:39:19', 'string', 'string');
INSERT INTO `eprs_contacts_contact` VALUES (4, '上官樱', 'string', 'string', 0, 'string', 'string', 'string', 3, 'string', 'string', 0, 0, '2020-12-25 02:03:01', '2020-12-25 15:39:21', 'string', 'string');
INSERT INTO `eprs_contacts_contact` VALUES (5, '上官樱木', 'string', 'string', 0, 'string', 'string', 'string', 4, 'string', 'string', 0, 0, '2020-12-25 02:03:01', '2020-12-25 15:39:12', 'string', 'string');
INSERT INTO `eprs_contacts_contact` VALUES (6, '啥大事', 'string', '199199@qq.com', 1, 'string', '18888888888', 'string', 12138, 'string', 'AAS', 0, 0, '2020-12-25 02:03:01', '2020-12-26 15:12:19', '141414141', 'string');
INSERT INTO `eprs_contacts_contact` VALUES (7, '高飞', 'string', 'string', 0, 'string', 'string', 'string', 6, 'string', 'string', 0, 0, '2020-12-25 02:03:01', '2020-12-25 15:39:36', 'string', 'string');
INSERT INTO `eprs_contacts_contact` VALUES (8, '高飞飞', 'string', 'string', 0, 'string', 'string', 'string', 7, 'string', 'string', 0, 0, '2020-12-25 02:03:01', '2020-12-25 15:39:31', 'string', 'string');
INSERT INTO `eprs_contacts_contact` VALUES (11, 'string', 'string', 'string', 0, 'string', 'string', 'string', 10, 'string', 'string', 0, 0, '2020-12-25 02:03:01', '2020-12-25 11:11:20', 'string', 'string');
INSERT INTO `eprs_contacts_contact` VALUES (12, 'ASDAASD', 'string', 'string', 0, '15808888888', 'string', 'string', 0, '前端的群', 'XVCS', 0, 0, NULL, NULL, NULL, 'admin');
INSERT INTO `eprs_contacts_contact` VALUES (48, '张三', '15685996653', 'az7582@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 3, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (49, '张三', '15685996654', 'az7583@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 4, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (50, '张三', '15685996655', 'az7584@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 5, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (51, '张三', '15685996656', 'az7585@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 6, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (52, '张三', '15685996657', 'az7586@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 7, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (53, '张三', '15685996658', 'az7587@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 8, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (54, '张三', '15685996659', 'az7588@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 9, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (55, '张三', '15685996660', 'az7589@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 10, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (56, '张三', '15685996661', 'az7590@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 11, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (57, '张三', '15685996662', 'az7591@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 12, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (58, '张三', '15685996663', 'az7592@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 13, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (59, '张三', '15685996664', 'az7593@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 14, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (60, '张三', '15685996665', 'az7594@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 15, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (61, '张三', '15685996666', 'az7595@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 16, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (62, '张三', '15685996667', 'az7596@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 17, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (63, '张三', '15685996668', 'az7597@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 18, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (64, '张三', '15685996669', 'az7598@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 19, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (65, '张三', '15685996670', 'az7599@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 20, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (66, '张三', '15685996671', 'az7600@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 21, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (67, '张三', '15685996672', 'az7601@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 22, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (68, '张三', '15685996673', 'az7602@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 23, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (69, '张三', '15685996674', 'az7603@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 24, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (70, '张三', '15685996675', 'az7604@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 25, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (71, '张三', '15685996676', 'az7605@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 26, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (72, '张三', '15685996677', 'az7606@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 27, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (73, '张三', '15685996678', 'az7607@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 28, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (74, '张三', '15685996679', 'az7608@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 29, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (75, '张三', '15685996680', 'az7609@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 30, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (76, '张三', '15685996681', 'az7610@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 31, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (77, '张三', '15685996682', 'az7611@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 32, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (78, '张三', '15685996683', 'az7612@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 33, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (79, '张三', '15685996684', 'az7613@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 34, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (80, '张三', '15685996685', 'az7614@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 35, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (81, '张三', '15685996686', 'az7615@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 36, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (82, '张三', '15685996687', 'az7616@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 37, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (83, '张三', '15685996688', 'az7617@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 38, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (84, '张三', '15685996689', 'az7618@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 39, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (85, '张三', '15685996690', 'az7619@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 40, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (86, '张三', '15685996691', 'az7620@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 41, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (87, '张三', '15685996692', 'az7621@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 42, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (88, '张三', '15685996693', 'az7622@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 43, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (89, '张三', '15685996694', 'az7623@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 44, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (90, '张三', '15685996695', 'az7624@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 45, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (91, '张三', '15685996696', 'az7625@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 46, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (92, '张三', '15685996697', 'az7626@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 47, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (93, '张三', '15685996698', 'az7627@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 48, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (94, '张三', '15685996699', 'az7628@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 49, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (95, '张三', '15685996700', 'az7629@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 50, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (96, '张三', '15685996701', 'az7630@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 51, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (97, '张三', '15685996702', 'az7631@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 52, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (98, '张三', '15685996703', 'az7632@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 53, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (99, '张三', '15685996704', 'az7633@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 54, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (100, '张三', '15685996705', 'az7634@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 55, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (101, '张三', '15685996706', 'az7635@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 56, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (102, '张三', '15685996707', 'az7636@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 57, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (103, '张三', '15685996708', 'az7637@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 58, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (104, '张三', '15685996709', 'az7638@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 59, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (105, '张三', '15685996710', 'az7639@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 60, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (106, '张三', '15685996711', 'az7640@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 61, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (107, '张三', '15685996712', 'az7641@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 62, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (108, '张三', '15685996713', 'az7642@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 63, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (109, '张三', '15685996714', 'az7643@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 64, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (110, '张三', '15685996715', 'az7644@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 65, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (111, '张三', '15685996716', 'az7645@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 66, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (112, '张三', '15685996717', 'az7646@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 67, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (113, '张三', '15685996718', 'az7647@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 68, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (114, '张三', '15685996719', 'az7648@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 69, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (115, '张三', '15685996720', 'az7649@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 70, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (116, '张三', '15685996721', 'az7650@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 71, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (117, '张三', '15685996722', 'az7651@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 72, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (118, '张三', '15685996723', 'az7652@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 73, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (119, '张三', '15685996724', 'az7653@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 74, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (120, '张三', '15685996725', 'az7654@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 75, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (121, '张三', '15685996726', 'az7655@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 76, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (122, '张三', '15685996727', 'az7656@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 77, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (123, '张三', '15685996728', 'az7657@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 78, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (124, '张三', '15685996729', 'az7658@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 79, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (125, '张三', '15685996730', 'az7659@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 80, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (126, '张三', '15685996731', 'az7660@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 81, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (127, '张三', '15685996732', 'az7661@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 82, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (128, '张三', '15685996733', 'az7662@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 83, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (129, '张三', '15685996734', 'az7663@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 84, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (130, '张三', '15685996735', 'az7664@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 85, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (131, '张三', '15685996736', 'az7665@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 86, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (132, '张三', '15685996737', 'az7666@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 87, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (133, '张三', '15685996738', 'az7667@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 88, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (134, '张三', '15685996739', 'az7668@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 89, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (135, '张三', '15685996740', 'az7669@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 90, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (136, '张三', '15685996741', 'az7670@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 91, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (137, '张三', '15685996742', 'az7671@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 92, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (138, '张三', '15685996743', 'az7672@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 93, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (139, '张三', '15685996744', 'az7673@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 94, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (140, '张三', '15685996745', 'az7674@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 95, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (141, '张三', '15685996746', 'az7675@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 96, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (142, '张三', '15685996747', 'az7676@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 97, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (143, '张三', '15685996748', 'az7677@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 98, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (144, '张三', '15685996749', 'az7678@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 99, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (145, '张三', '15685996750', 'az7679@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 100, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (146, '张三', '15685996751', 'az7680@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 101, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (147, '张三', '15685996752', 'az7681@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 102, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (148, '张三', '15685996753', 'az7682@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 103, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (149, '张三', '15685996754', 'az7683@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 104, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (150, '张三', '15685996755', 'az7684@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 105, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (151, '张三', '15685996756', 'az7685@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 106, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (152, '张三', '15685996757', 'az7686@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 107, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (153, '张三', '15685996758', 'az7687@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 108, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (154, '张三', '15685996759', 'az7688@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 109, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (155, '张三', '15685996760', 'az7689@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 110, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (156, '张三', '15685996761', 'az7690@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 111, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (157, '张三', '15685996762', 'az7691@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 112, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (158, '张三', '15685996763', 'az7692@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 113, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (159, '张三', '15685996764', 'az7693@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 114, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (160, '张三', '15685996765', 'az7694@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 115, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (161, '张三', '15685996766', 'az7695@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 116, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (162, '张三', '15685996767', 'az7696@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 117, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (163, '张三', '15685996768', 'az7697@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 118, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (164, '张三', '15685996769', 'az7698@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 119, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (165, '张三', '15685996770', 'az7699@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 120, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (166, '张三', '15685996771', 'az7700@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 121, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (167, '张三', '15685996772', 'az7701@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 122, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (168, '张三', '15685996773', 'az7702@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 123, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (169, '张三', '15685996774', 'az7703@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 124, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (170, '张三', '15685996775', 'az7704@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 125, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (171, '张三', '15685996776', 'az7705@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 126, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (172, '张三', '15685996777', 'az7706@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 127, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (173, '张三', '15685996778', 'az7707@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 128, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (174, '张三', '15685996779', 'az7708@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 129, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (175, '张三', '15685996780', 'az7709@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 130, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (176, '张三', '15685996781', 'az7710@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 131, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (177, '张三', '15685996782', 'az7711@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 132, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (178, '张三', '15685996783', 'az7712@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 133, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (179, '张三', '15685996784', 'az7713@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 134, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (180, '张三', '15685996785', 'az7714@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 135, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (181, '张三', '15685996786', 'az7715@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 136, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (182, '张三', '15685996787', 'az7716@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 137, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (183, '张三', '15685996788', 'az7717@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 138, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (184, '张三', '15685996789', 'az7718@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 139, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (185, '张三', '15685996790', 'az7719@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 140, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (186, '张三', '15685996791', 'az7720@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 141, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (187, '张三', '15685996792', 'az7721@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 142, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (188, '张三', '15685996793', 'az7722@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 143, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (189, '张三', '15685996794', 'az7723@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 144, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (190, '张三', '15685996795', 'az7724@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 145, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (191, '张三', '15685996796', 'az7725@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 146, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (192, '张三', '15685996797', 'az7726@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 147, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (193, '张三', '15685996798', 'az7727@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 148, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (194, '张三', '15685996799', 'az7728@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 149, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (195, '张三', '15685996800', 'az7729@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 150, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (196, '张三', '15685996801', 'az7730@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 151, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (197, '张三', '15685996802', 'az7731@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 152, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (198, '张三', '15685996803', 'az7732@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 153, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (199, '张三', '15685996804', 'az7733@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 154, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (200, '张三', '15685996805', 'az7734@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 155, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (201, '张三', '15685996806', 'az7735@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 156, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (202, '张三', '15685996807', 'az7736@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 157, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (203, '张三', '15685996808', 'az7737@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 158, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (204, '张三', '15685996809', 'az7738@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 159, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (205, '张三', '15685996810', 'az7739@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 160, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (206, '张三', '15685996811', 'az7740@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 161, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (207, '张三', '15685996812', 'az7741@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 162, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (208, '张三', '15685996813', 'az7742@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 163, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (209, '张三', '15685996814', 'az7743@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 164, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (210, '张三', '15685996815', 'az7744@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 165, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (211, '张三', '15685996816', 'az7745@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 166, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (212, '张三', '15685996817', 'az7746@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 167, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (213, '张三', '15685996818', 'az7747@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 168, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (214, '张三', '15685996819', 'az7748@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 169, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (215, '张三', '15685996820', 'az7749@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 170, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (216, '张三', '15685996821', 'az7750@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 171, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (217, '张三', '15685996822', 'az7751@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 172, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (218, '张三', '15685996823', 'az7752@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 173, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (219, '张三', '15685996824', 'az7753@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 174, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (220, '张三', '15685996825', 'az7754@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 175, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (221, '张三', '15685996826', 'az7755@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 176, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (222, '张三', '15685996827', 'az7756@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 177, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (223, '张三', '15685996828', 'az7757@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 178, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (224, '张三', '15685996829', 'az7758@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 179, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (225, '张三', '15685996830', 'az7759@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 180, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (226, '张三', '15685996831', 'az7760@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 181, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (227, '张三', '15685996832', 'az7761@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 182, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (228, '张三', '15685996833', 'az7762@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 183, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (229, '张三', '15685996834', 'az7763@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 184, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (230, '张三', '15685996835', 'az7764@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 185, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (231, '张三', '15685996836', 'az7765@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 186, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (232, '张三', '15685996837', 'az7766@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 187, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (233, '张三', '15685996838', 'az7767@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 188, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (234, '张三', '15685996839', 'az7768@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 189, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (235, '张三', '15685996840', 'az7769@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 190, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (236, '张三', '15685996841', 'az7770@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 191, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (237, '张三', '15685996842', 'az7771@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 192, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (238, '张三', '15685996843', 'az7772@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 193, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (239, '张三', '15685996844', 'az7773@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 194, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (240, '张三', '15685996845', 'az7774@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 195, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (241, '张三', '15685996846', 'az7775@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 196, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (242, '张三', '15685996847', 'az7776@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 197, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (243, '张三', '15685996848', 'az7777@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 198, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (244, '张三', '15685996849', 'az7778@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 199, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (245, '张三', '15685996850', 'az7779@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 200, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (246, '张三', '15685996851', 'az7780@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 201, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (247, '张三', '15685996852', 'az7781@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 202, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (248, '张三', '15685996853', 'az7782@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 203, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (249, '张三', '15685996854', 'az7783@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 204, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (250, '张三', '15685996855', 'az7784@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 205, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (251, '张三', '15685996856', 'az7785@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 206, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (252, '张三', '15685996857', 'az7786@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 207, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (253, '张三', '15685996858', 'az7787@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 208, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (254, '张三', '15685996859', 'az7788@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 209, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (255, '张三', '15685996860', 'az7789@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 210, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (256, '张三', '15685996861', 'az7790@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 211, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (257, '张三', '15685996862', 'az7791@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 212, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (258, '张三', '15685996863', 'az7792@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 213, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (259, '张三', '15685996864', 'az7793@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 214, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (260, '张三', '15685996865', 'az7794@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 215, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (261, '张三', '15685996866', 'az7795@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 216, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (262, '张三', '15685996867', 'az7796@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 217, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (263, '张三', '15685996868', 'az7797@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 218, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (264, '张三', '15685996869', 'az7798@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 219, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (265, '张三', '15685996870', 'az7799@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 220, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (266, '张三', '15685996871', 'az7800@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 221, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (267, '张三', '15685996872', 'az7801@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 222, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (268, '张三', '15685996873', 'az7802@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 223, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (269, '张三', '15685996874', 'az7803@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 224, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (270, '张三', '15685996875', 'az7804@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 225, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (271, '张三', '15685996876', 'az7805@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 226, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (272, '张三', '15685996877', 'az7806@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 227, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (273, '张三', '15685996878', 'az7807@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 228, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (274, '张三', '15685996879', 'az7808@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 229, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (275, '张三', '15685996880', 'az7809@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 230, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (276, '张三', '15685996881', 'az7810@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 231, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (277, '张三', '15685996882', 'az7811@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 232, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (278, '张三', '15685996883', 'az7812@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 233, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (279, '张三', '15685996884', 'az7813@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 234, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (280, '张三', '15685996885', 'az7814@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 235, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (281, '张三', '15685996886', 'az7815@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 236, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (282, '张三', '15685996887', 'az7816@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 237, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (283, '张三', '15685996888', 'az7817@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 238, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (284, '张三', '15685996889', 'az7818@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 239, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (285, '张三', '15685996890', 'az7819@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 240, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (286, '张三', '15685996891', 'az7820@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 241, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (287, '张三', '15685996892', 'az7821@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 242, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (288, '张三', '15685996893', 'az7822@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 243, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (289, '张三', '15685996894', 'az7823@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 244, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (290, '张三', '15685996895', 'az7824@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 245, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (291, '张三', '15685996896', 'az7825@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 246, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (292, '张三', '15685996897', 'az7826@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 247, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (293, '张三', '15685996898', 'az7827@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 248, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (294, '张三', '15685996899', 'az7828@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 249, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (295, '张三', '15685996900', 'az7829@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 250, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (296, '张三', '15685996901', 'az7830@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 251, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (297, '张三', '15685996902', 'az7831@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 252, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (298, '张三', '15685996903', 'az7832@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 253, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (299, '张三', '15685996904', 'az7833@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 254, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (300, '张三', '15685996905', 'az7834@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 255, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (301, '张三', '15685996906', 'az7835@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 256, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (302, '张三', '15685996907', 'az7836@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 257, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (303, '张三', '15685996908', 'az7837@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 258, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (304, '张三', '15685996909', 'az7838@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 259, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (305, '张三', '15685996910', 'az7839@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 260, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (306, '张三', '15685996911', 'az7840@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 261, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (307, '张三', '15685996912', 'az7841@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 262, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (308, '张三', '15685996913', 'az7842@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 263, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (309, '张三', '15685996914', 'az7843@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 264, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (310, '张三', '15685996915', 'az7844@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 265, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (311, '张三', '15685996916', 'az7845@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 266, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (312, '张三', '15685996917', 'az7846@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 267, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (313, '张三', '15685996918', 'az7847@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 268, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (314, '张三', '15685996919', 'az7848@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 269, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (315, '张三', '15685996920', 'az7849@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 270, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (316, '张三', '15685996921', 'az7850@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 271, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (317, '张三', '15685996922', 'az7851@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 272, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (318, '张三', '15685996923', 'az7852@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 273, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (319, '张三', '15685996924', 'az7853@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 274, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (320, '张三', '15685996925', 'az7854@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 275, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (321, '张三', '15685996926', 'az7855@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 276, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (322, '张三', '15685996927', 'az7856@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 277, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (323, '张三', '15685996928', 'az7857@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 278, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (324, '张三', '15685996929', 'az7858@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 279, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (325, '张三', '15685996930', 'az7859@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 280, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (326, '张三', '15685996931', 'az7860@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 281, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (327, '张三', '15685996932', 'az7861@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 282, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (328, '张三', '15685996933', 'az7862@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 283, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (329, '张三', '15685996934', 'az7863@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 284, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (330, '张三', '15685996935', 'az7864@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 285, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (331, '张三', '15685996936', 'az7865@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 286, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (332, '张三', '15685996937', 'az7866@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 287, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (333, '张三', '15685996938', 'az7867@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 288, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (334, '张三', '15685996939', 'az7868@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 289, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (335, '张三', '15685996940', 'az7869@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 290, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (336, '张三', '15685996941', 'az7870@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 291, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (337, '张三', '15685996942', 'az7871@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 292, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (338, '张三', '15685996943', 'az7872@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 293, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (339, '张三', '15685996944', 'az7873@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 294, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (340, '张三', '15685996945', 'az7874@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 295, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (341, '张三', '15685996946', 'az7875@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 296, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (342, '张三', '15685996947', 'az7876@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 297, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (343, '张三', '15685996948', 'az7877@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 298, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (344, '张三', '15685996949', 'az7878@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 299, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (345, '张三', '15685996950', 'az7879@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 300, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (346, '张三', '15685996951', 'az7880@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 301, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (347, '张三', '15685996952', 'az7881@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 302, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (348, '张三', '15685996953', 'az7882@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 303, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (349, '张三', '15685996954', 'az7883@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 304, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (350, '张三', '15685996955', 'az7884@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 305, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (351, '张三', '15685996956', 'az7885@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 306, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (352, '张三', '15685996957', 'az7886@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 307, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (353, '张三', '15685996958', 'az7887@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 308, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (354, '张三', '15685996959', 'az7888@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 309, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (355, '张三', '15685996960', 'az7889@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 310, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (356, '张三', '15685996961', 'az7890@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 311, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (357, '张三', '15685996962', 'az7891@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 312, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (358, '张三', '15685996963', 'az7892@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 313, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (359, '张三', '15685996964', 'az7893@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 314, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (360, '张三', '15685996965', 'az7894@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 315, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (361, '张三', '15685996966', 'az7895@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 316, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (362, '张三', '15685996967', 'az7896@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 317, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (363, '张三', '15685996968', 'az7897@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 318, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (364, '张三', '15685996969', 'az7898@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 319, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (365, '张三', '15685996970', 'az7899@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 320, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (366, '张三', '15685996971', 'az7900@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 321, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (367, '张三', '15685996972', 'az7901@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 322, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (368, '张三', '15685996973', 'az7902@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 323, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (369, '张三', '15685996974', 'az7903@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 324, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (370, '张三', '15685996975', 'az7904@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 325, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (371, '张三', '15685996976', 'az7905@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 326, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (372, '张三', '15685996977', 'az7906@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 327, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (373, '张三', '15685996978', 'az7907@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 328, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (374, '张三', '15685996979', 'az7908@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 329, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (375, '张三', '15685996980', 'az7909@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 330, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (376, '张三', '15685996981', 'az7910@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 331, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (377, '张三', '15685996982', 'az7911@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 332, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (378, '张三', '15685996983', 'az7912@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 333, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (379, '张三', '15685996984', 'az7913@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 334, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (380, '张三', '15685996985', 'az7914@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 335, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (381, '张三', '15685996986', 'az7915@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 336, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (382, '张三', '15685996987', 'az7916@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 337, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (383, '张三', '15685996988', 'az7917@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 338, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (384, '张三', '15685996989', 'az7918@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 339, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (385, '张三', '15685996990', 'az7919@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 340, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (386, '张三', '15685996991', 'az7920@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 341, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (387, '张三', '15685996992', 'az7921@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 342, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (388, '张三', '15685996993', 'az7922@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 343, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (389, '张三', '15685996994', 'az7923@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 344, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (390, '张三', '15685996995', 'az7924@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 345, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (391, '张三', '15685996996', 'az7925@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 346, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (392, '张三', '15685996997', 'az7926@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 347, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (393, '张三', '15685996998', 'az7927@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 348, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (394, '张三', '15685996999', 'az7928@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 349, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (395, '张三', '15685997000', 'az7929@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 350, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (396, '张三', '15685997001', 'az7930@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 351, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (397, '张三', '15685997002', 'az7931@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 352, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (398, '张三', '15685997003', 'az7932@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 353, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (399, '张三', '15685997004', 'az7933@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 354, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (400, '张三', '15685997005', 'az7934@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 355, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (401, '张三', '15685997006', 'az7935@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 356, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (402, '张三', '15685997007', 'az7936@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 357, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (403, '张三', '15685997008', 'az7937@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 358, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (404, '张三', '15685997009', 'az7938@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 359, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (405, '张三', '15685997010', 'az7939@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 360, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (406, '张三', '15685997011', 'az7940@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 361, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (407, '张三', '15685997012', 'az7941@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 362, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (408, '张三', '15685997013', 'az7942@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 363, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (409, '张三', '15685997014', 'az7943@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 364, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (410, '张三', '15685997015', 'az7944@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 365, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (411, '张三', '15685997016', 'az7945@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 366, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (412, '张三', '15685997017', 'az7946@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 367, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (413, '张三', '15685997018', 'az7947@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 368, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (414, '张三', '15685997019', 'az7948@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 369, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (415, '张三', '15685997020', 'az7949@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 370, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (416, '张三', '15685997021', 'az7950@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 371, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (417, '张三', '15685997022', 'az7951@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 372, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (418, '张三', '15685997023', 'az7952@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 373, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (419, '张三', '15685997024', 'az7953@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 374, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (420, '张三', '15685997025', 'az7954@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 375, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (421, '张三', '15685997026', 'az7955@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 376, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (422, '张三', '15685997027', 'az7956@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 377, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (423, '张三', '15685997028', 'az7957@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 378, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (424, '张三', '15685997029', 'az7958@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 379, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (425, '张三', '15685997030', 'az7959@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 380, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (426, '张三', '15685997031', 'az7960@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 381, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (427, '张三', '15685997032', 'az7961@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 382, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (428, '张三', '15685997033', 'az7962@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 383, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (429, '张三', '15685997034', 'az7963@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 384, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (430, '张三', '15685997035', 'az7964@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 385, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (431, '张三', '15685997036', 'az7965@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 386, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (432, '张三', '15685997037', 'az7966@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 387, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (433, '张三', '15685997038', 'az7967@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 388, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (434, '张三', '15685997039', 'az7968@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 389, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (435, '张三', '15685997040', 'az7969@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 390, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (436, '张三', '15685997041', 'az7970@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 391, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (437, '张三', '15685997042', 'az7971@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 392, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (438, '张三', '15685997043', 'az7972@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 393, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (439, '张三', '15685997044', 'az7973@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 394, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (440, '张三', '15685997045', 'az7974@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 395, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (441, '张三', '15685997046', 'az7975@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 396, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (442, '张三', '15685997047', 'az7976@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 397, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (443, '张三', '15685997048', 'az7977@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 398, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (444, '张三', '15685997049', 'az7978@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 399, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (445, '张三', '15685997050', 'az7979@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 400, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (446, '张三', '15685997051', 'az7980@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 401, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (447, '张三', '15685997052', 'az7981@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 402, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (448, '张三', '15685997053', 'az7982@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 403, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (449, '张三', '15685997054', 'az7983@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 404, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (450, '张三', '15685997055', 'az7984@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 405, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (451, '张三', '15685997056', 'az7985@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 406, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (452, '张三', '15685997057', 'az7986@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 407, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (453, '张三', '15685997058', 'az7987@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 408, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (454, '张三', '15685997059', 'az7988@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 409, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (455, '张三', '15685997060', 'az7989@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 410, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (456, '张三', '15685997061', 'az7990@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 411, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (457, '张三', '15685997062', 'az7991@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 412, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (458, '张三', '15685997063', 'az7992@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 413, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (459, '张三', '15685997064', 'az7993@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 414, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (460, '张三', '15685997065', 'az7994@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 415, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (461, '张三', '15685997066', 'az7995@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 416, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (462, '张三', '15685997067', 'az7996@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 417, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (463, '张三', '15685997068', 'az7997@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 418, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (464, '张三', '15685997069', 'az7998@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 419, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (465, '张三', '15685997070', 'az7999@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 420, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (466, '张三', '15685997071', 'az8000@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 421, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (467, '张三', '15685997072', 'az8001@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 422, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (468, '张三', '15685997073', 'az8002@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 423, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (469, '张三', '15685997074', 'az8003@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 424, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (470, '张三', '15685997075', 'az8004@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 425, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (471, '张三', '15685997076', 'az8005@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 426, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (472, '张三', '15685997077', 'az8006@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 427, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (473, '张三', '15685997078', 'az8007@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 428, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (474, '张三', '15685997079', 'az8008@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 429, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (475, '张三', '15685997080', 'az8009@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 430, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (476, '张三', '15685997081', 'az8010@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 431, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (477, '张三', '15685997082', 'az8011@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 432, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (478, '张三', '15685997083', 'az8012@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 433, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (479, '张三', '15685997084', 'az8013@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 434, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (480, '张三', '15685997085', 'az8014@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 435, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (481, '张三', '15685997086', 'az8015@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 436, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (482, '张三', '15685997087', 'az8016@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 437, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (483, '张三', '15685997088', 'az8017@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 438, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (484, '张三', '15685997089', 'az8018@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 439, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (485, '张三', '15685997090', 'az8019@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 440, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (486, '张三', '15685997091', 'az8020@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 441, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (487, '张三', '15685997092', 'az8021@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 442, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (488, '张三', '15685997093', 'az8022@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 443, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (489, '张三', '15685997094', 'az8023@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 444, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (490, '张三', '15685997095', 'az8024@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 445, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (491, '张三', '15685997096', 'az8025@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 446, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (492, '张三', '15685997097', 'az8026@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 447, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (493, '张三', '15685997098', 'az8027@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 448, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (494, '张三', '15685997099', 'az8028@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 449, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (495, '张三', '15685997100', 'az8029@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 450, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (496, '张三', '15685997101', 'az8030@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 451, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (497, '张三', '15685997102', 'az8031@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 452, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (498, '张三', '15685997103', 'az8032@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 453, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (499, '张三', '15685997104', 'az8033@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 454, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (500, '张三', '15685997105', 'az8034@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 455, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (501, '张三', '15685997106', 'az8035@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 456, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (502, '张三', '15685997107', 'az8036@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 457, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (503, '张三', '15685997108', 'az8037@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 458, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (504, '张三', '15685997109', 'az8038@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 459, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (505, '张三', '15685997110', 'az8039@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 460, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (506, '张三', '15685997111', 'az8040@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 461, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (507, '张三', '15685997112', 'az8041@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 462, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (508, '张三', '15685997113', 'az8042@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 463, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (509, '张三', '15685997114', 'az8043@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 464, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (510, '张三', '15685997115', 'az8044@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 465, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (511, '张三', '15685997116', 'az8045@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 466, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (512, '张三', '15685997117', 'az8046@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 467, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (513, '张三', '15685997118', 'az8047@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 468, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (514, '张三', '15685997119', 'az8048@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 469, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (515, '张三', '15685997120', 'az8049@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 470, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (516, '张三', '15685997121', 'az8050@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 471, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (517, '张三', '15685997122', 'az8051@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 472, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (518, '张三', '15685997123', 'az8052@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 473, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (519, '张三', '15685997124', 'az8053@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 474, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (520, '张三', '15685997125', 'az8054@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 475, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (521, '张三', '15685997126', 'az8055@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 476, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (522, '张三', '15685997127', 'az8056@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 477, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (523, '张三', '15685997128', 'az8057@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 478, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (524, '张三', '15685997129', 'az8058@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 479, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (525, '张三', '15685997130', 'az8059@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 480, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (526, '张三', '15685997131', 'az8060@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 481, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (527, '张三', '15685997132', 'az8061@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 482, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (528, '张三', '15685997133', 'az8062@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 483, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (529, '张三', '15685997134', 'az8063@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 484, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (530, '张三', '15685997135', 'az8064@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 485, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (531, '张三', '15685997136', 'az8065@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 486, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (532, '张三', '15685997137', 'az8066@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 487, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (533, '张三', '15685997138', 'az8067@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 488, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (534, '张三', '15685997139', 'az8068@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 489, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (535, '张三', '15685997140', 'az8069@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 490, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (536, '张三', '15685997141', 'az8070@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 491, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (537, '张三', '15685997142', 'az8071@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 492, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (538, '张三', '15685997143', 'az8072@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 493, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (539, '张三', '15685997144', 'az8073@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 494, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (540, '张三', '15685997145', 'az8074@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 495, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (541, '张三', '15685997146', 'az8075@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 496, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (542, '张三111', '15685997147', 'az8076@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 497, '2020-12-26 14:58:03', '2020-12-26 14:58:09', NULL, '41924961b7c14ddd8669c2f1553a51b6');
INSERT INTO `eprs_contacts_contact` VALUES (548, '张三', '15685996653', 'az7582@qq.com', 1, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 3, '2020-12-28 08:56:04', '2020-12-28 08:56:15', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (549, '张三', '15685996653', 'az7582@qq.com', 2, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 3, '2020-12-28 08:57:02', '2020-12-28 08:57:08', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (550, '张三', '15685996653', 'az7582@qq.com', 0, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 3, '2020-12-28 08:57:28', '2020-12-28 08:57:29', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (551, '张三', '15685996653', 'az7582@qq.com', 2, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 3, '2020-12-28 09:31:09', '2020-12-28 09:31:17', NULL, NULL);
INSERT INTO `eprs_contacts_contact` VALUES (552, '村上春树', '13111111111', '', 1, '', '', '', 89, '', '', 0, 0, '2020-12-30 15:28:14', NULL, '41924961b7c14ddd8669c2f1553a51b6', '');
INSERT INTO `eprs_contacts_contact` VALUES (553, '测试测试从', '13111111111', '17886332@qq.com', 1, '', '', '', 89, '', '', 0, 0, '2020-12-30 15:28:52', '2020-12-30 16:58:38', '41924961b7c14ddd8669c2f1553a51b6', '41924961b7c14ddd8669c2f1553a51b6');
INSERT INTO `eprs_contacts_contact` VALUES (554, '张三', '15685996653', 'az7582@qq.com', 2, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 3, '2020-12-30 17:22:40', '2020-12-30 17:22:40', '41924961b7c14ddd8669c2f1553a51b6', NULL);
INSERT INTO `eprs_contacts_contact` VALUES (555, '张三', '15685996653', 'az7582@qq.com', 2, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 3, '2020-12-30 17:23:30', '2020-12-30 17:23:30', '41924961b7c14ddd8669c2f1553a51b6', NULL);
INSERT INTO `eprs_contacts_contact` VALUES (556, '长春市', '13111111111', '', 0, '', '', '', 90, '', '', 0, 0, '2020-12-31 10:04:36', NULL, '41924961b7c14ddd8669c2f1553a51b6', '');
INSERT INTO `eprs_contacts_contact` VALUES (557, '擦擦擦', '13111111111', '', 0, '', '', '', 94, '', '', 0, 0, '2020-12-31 14:28:14', NULL, '41924961b7c14ddd8669c2f1553a51b6', '');
INSERT INTO `eprs_contacts_contact` VALUES (558, '张三1231', '15685996653', 'az7582@qq.com', 2, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 3, '2020-12-31 14:32:13', '2020-12-31 14:32:12', '41924961b7c14ddd8669c2f1553a51b6', NULL);
INSERT INTO `eprs_contacts_contact` VALUES (559, '张三12', '15685996653', 'az7582@qq.com', 2, '5343968521', '18866669999', '山东省德州市开发区', 96, '易罐', '技术部', 1, 3, '2020-12-31 14:32:45', '2020-12-31 14:32:43', '41924961b7c14ddd8669c2f1553a51b6', '9a55362d336146c59357792d1c6f0ab8');
INSERT INTO `eprs_contacts_contact` VALUES (560, '张三1231', '15685996653', 'az7582@qq.com', 2, '5343968521', '18866669999', '山东省德州市开发区', 89, '易罐', '技术部', 1, 3, '2021-01-08 14:35:10', '2021-01-08 14:35:10', '9a55362d336146c59357792d1c6f0ab8', NULL);
INSERT INTO `eprs_contacts_contact` VALUES (563, '转账', '18888888896', '', 1, '', '', '', 96, '', '', 0, 0, '2021-01-12 16:46:21', '2021-01-12 16:46:27', '9a55362d336146c59357792d1c6f0ab8', '9a55362d336146c59357792d1c6f0ab8');

-- ----------------------------
-- Table structure for eprs_contacts_logs
-- ----------------------------
DROP TABLE IF EXISTS `eprs_contacts_logs`;
CREATE TABLE `eprs_contacts_logs`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `user_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `username` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `modules` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块接口',
  `operation` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户操作',
  `time` int(0) NULL DEFAULT NULL COMMENT '响应时间',
  `method` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2312 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通讯录日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of eprs_contacts_logs
-- ----------------------------
INSERT INTO `eprs_contacts_logs` VALUES (2291, '9a55362d336146c59357792d1c6f0ab8', '%E9%80%9A%E8%AE%AF%E9%80%9A%E7%AE%A1%E7%90%86%E5%91%98', '动态菜单服务', '查询菜单树', 93, 'cn.org.bachelor.demo.web.controller.BaseMenuController.findOrganizationTree()', '[]', '127.0.0.1', '2021-02-02 15:48:41');
INSERT INTO `eprs_contacts_logs` VALUES (2292, '9a55362d336146c59357792d1c6f0ab8', '%E9%80%9A%E8%AE%AF%E9%80%9A%E7%AE%A1%E7%90%86%E5%91%98', '动态菜单服务', '查询菜单树', 5, 'cn.org.bachelor.demo.web.controller.BaseMenuController.findOrganizationTree()', '[]', '127.0.0.1', '2021-02-02 15:53:57');
INSERT INTO `eprs_contacts_logs` VALUES (2293, '9a55362d336146c59357792d1c6f0ab8', '%E9%80%9A%E8%AE%AF%E9%80%9A%E7%AE%A1%E7%90%86%E5%91%98', '通讯录服务', '通讯录多条件查询', 74, 'cn.org.bachelor.demo.web.controller.ContactController.selectByParams()', '[{\"bName\":\"\",\"groupId\":0,\"phone\":\"\"},1,10]', '127.0.0.1', '2021-02-02 16:05:50');
INSERT INTO `eprs_contacts_logs` VALUES (2294, '9a55362d336146c59357792d1c6f0ab8', '%E9%80%9A%E8%AE%AF%E9%80%9A%E7%AE%A1%E7%90%86%E5%91%98', '通讯录服务', '通讯录多条件查询', 14, 'cn.org.bachelor.demo.web.controller.ContactController.selectByParams()', '[{\"bName\":\"\",\"groupId\":0,\"phone\":\"\"},2,10]', '127.0.0.1', '2021-02-02 16:06:24');
INSERT INTO `eprs_contacts_logs` VALUES (2295, '9a55362d336146c59357792d1c6f0ab8', '%E9%80%9A%E8%AE%AF%E9%80%9A%E7%AE%A1%E7%90%86%E5%91%98', '通讯录服务', '通讯录多条件查询', 7, 'cn.org.bachelor.demo.web.controller.ContactController.selectByParams()', '[{\"bName\":\"\",\"groupId\":0,\"phone\":\"\"},3,10]', '127.0.0.1', '2021-02-02 16:06:26');
INSERT INTO `eprs_contacts_logs` VALUES (2296, '9a55362d336146c59357792d1c6f0ab8', '%E9%80%9A%E8%AE%AF%E9%80%9A%E7%AE%A1%E7%90%86%E5%91%98', '通讯录服务', '通讯录多条件查询', 7, 'cn.org.bachelor.demo.web.controller.ContactController.selectByParams()', '[{\"bName\":\"\",\"groupId\":0,\"phone\":\"\"},4,10]', '127.0.0.1', '2021-02-02 16:06:27');
INSERT INTO `eprs_contacts_logs` VALUES (2297, '9a55362d336146c59357792d1c6f0ab8', '%E9%80%9A%E8%AE%AF%E9%80%9A%E7%AE%A1%E7%90%86%E5%91%98', '通讯录服务', '通讯录多条件查询', 6, 'cn.org.bachelor.demo.web.controller.ContactController.selectByParams()', '[{\"bName\":\"\",\"groupId\":0,\"phone\":\"\"},1,10]', '127.0.0.1', '2021-02-02 16:06:28');
INSERT INTO `eprs_contacts_logs` VALUES (2298, '9a55362d336146c59357792d1c6f0ab8', '%E9%80%9A%E8%AE%AF%E9%80%9A%E7%AE%A1%E7%90%86%E5%91%98', '通讯录服务', '通讯录多条件查询', 12, 'cn.org.bachelor.demo.web.controller.ContactController.selectByParams()', '[{\"bName\":\"\",\"groupId\":0,\"phone\":\"\"},1,10]', '127.0.0.1', '2021-02-02 16:06:51');
INSERT INTO `eprs_contacts_logs` VALUES (2299, '9a55362d336146c59357792d1c6f0ab8', '%E9%80%9A%E8%AE%AF%E9%80%9A%E7%AE%A1%E7%90%86%E5%91%98', '通讯录服务', '通讯录多条件查询', 8, 'cn.org.bachelor.demo.web.controller.ContactController.selectByParams()', '[{\"bName\":\"\",\"groupId\":0,\"phone\":\"\"},1,10]', '127.0.0.1', '2021-02-02 16:07:55');
INSERT INTO `eprs_contacts_logs` VALUES (2300, '9a55362d336146c59357792d1c6f0ab8', '%E9%80%9A%E8%AE%AF%E9%80%9A%E7%AE%A1%E7%90%86%E5%91%98', '通讯录组织机构相关接口服务', '查询组织机构树', 13, 'cn.org.bachelor.demo.web.controller.OrganizationController.findOrganizationTree()', '[]', '127.0.0.1', '2021-02-02 16:07:55');
INSERT INTO `eprs_contacts_logs` VALUES (2301, '9a55362d336146c59357792d1c6f0ab8', '%E9%80%9A%E8%AE%AF%E9%80%9A%E7%AE%A1%E7%90%86%E5%91%98', '通讯录服务', '通讯录多条件查询', 5, 'cn.org.bachelor.demo.web.controller.ContactController.selectByParams()', '[{\"bName\":\"\",\"groupId\":0,\"phone\":\"\"},1,10]', '127.0.0.1', '2021-02-02 16:08:02');
INSERT INTO `eprs_contacts_logs` VALUES (2302, '9a55362d336146c59357792d1c6f0ab8', '%E9%80%9A%E8%AE%AF%E9%80%9A%E7%AE%A1%E7%90%86%E5%91%98', '通讯录服务', '通讯录多条件查询', 6, 'cn.org.bachelor.demo.web.controller.ContactController.selectByParams()', '[{\"bName\":\"\",\"groupId\":0,\"phone\":\"\"},1,10]', '127.0.0.1', '2021-02-02 16:08:03');
INSERT INTO `eprs_contacts_logs` VALUES (2303, '9a55362d336146c59357792d1c6f0ab8', '%E9%80%9A%E8%AE%AF%E9%80%9A%E7%AE%A1%E7%90%86%E5%91%98', '通讯录组织机构相关接口服务', '查询组织机构树', 4, 'cn.org.bachelor.demo.web.controller.OrganizationController.findOrganizationTree()', '[]', '127.0.0.1', '2021-02-02 16:08:26');
INSERT INTO `eprs_contacts_logs` VALUES (2304, '9a55362d336146c59357792d1c6f0ab8', '%E9%80%9A%E8%AE%AF%E9%80%9A%E7%AE%A1%E7%90%86%E5%91%98', '通讯录服务', '通讯录多条件查询', 8, 'cn.org.bachelor.demo.web.controller.ContactController.selectByParams()', '[{\"bName\":\"\",\"groupId\":0,\"phone\":\"\"},1,10]', '127.0.0.1', '2021-02-02 16:08:26');
INSERT INTO `eprs_contacts_logs` VALUES (2305, '9a55362d336146c59357792d1c6f0ab8', '%E9%80%9A%E8%AE%AF%E9%80%9A%E7%AE%A1%E7%90%86%E5%91%98', '通讯录组织机构相关接口服务', '查询组织机构树', 6, 'cn.org.bachelor.demo.web.controller.OrganizationController.findOrganizationTree()', '[]', '127.0.0.1', '2021-02-02 16:09:40');
INSERT INTO `eprs_contacts_logs` VALUES (2306, '9a55362d336146c59357792d1c6f0ab8', '%E9%80%9A%E8%AE%AF%E9%80%9A%E7%AE%A1%E7%90%86%E5%91%98', '通讯录服务', '通讯录多条件查询', 19, 'cn.org.bachelor.demo.web.controller.ContactController.selectByParams()', '[{\"bName\":\"\",\"groupId\":0,\"phone\":\"\"},1,10]', '127.0.0.1', '2021-02-02 16:09:40');
INSERT INTO `eprs_contacts_logs` VALUES (2307, '9a55362d336146c59357792d1c6f0ab8', '%E9%80%9A%E8%AE%AF%E9%80%9A%E7%AE%A1%E7%90%86%E5%91%98', '通讯录服务', '通讯录多条件查询', 7, 'cn.org.bachelor.demo.web.controller.ContactController.selectByParams()', '[{\"bName\":\"\",\"groupId\":0,\"phone\":\"\"},1,10]', '127.0.0.1', '2021-02-02 16:10:30');
INSERT INTO `eprs_contacts_logs` VALUES (2308, '9a55362d336146c59357792d1c6f0ab8', '%E9%80%9A%E8%AE%AF%E9%80%9A%E7%AE%A1%E7%90%86%E5%91%98', '通讯录组织机构相关接口服务', '查询组织机构树', 4, 'cn.org.bachelor.demo.web.controller.OrganizationController.findOrganizationTree()', '[]', '127.0.0.1', '2021-02-02 16:10:30');
INSERT INTO `eprs_contacts_logs` VALUES (2309, '9a55362d336146c59357792d1c6f0ab8', '%E9%80%9A%E8%AE%AF%E9%80%9A%E7%AE%A1%E7%90%86%E5%91%98', '通讯录组织机构相关接口服务', '查询组织机构树', 4, 'cn.org.bachelor.demo.web.controller.OrganizationController.findOrganizationTree()', '[]', '127.0.0.1', '2021-02-02 16:10:41');
INSERT INTO `eprs_contacts_logs` VALUES (2310, '9a55362d336146c59357792d1c6f0ab8', '%E9%80%9A%E8%AE%AF%E9%80%9A%E7%AE%A1%E7%90%86%E5%91%98', '通讯录服务', '通讯录多条件查询', 12, 'cn.org.bachelor.demo.web.controller.ContactController.selectByParams()', '[{\"bName\":\"\",\"groupId\":0,\"phone\":\"\"},1,10]', '127.0.0.1', '2021-02-02 16:10:41');
INSERT INTO `eprs_contacts_logs` VALUES (2311, '9a55362d336146c59357792d1c6f0ab8', '%E9%80%9A%E8%AE%AF%E9%80%9A%E7%AE%A1%E7%90%86%E5%91%98', '通讯录服务', '通讯录删除', 43, 'cn.org.bachelor.demo.web.controller.ContactController.delete()', '[561]', '127.0.0.1', '2021-02-02 16:10:52');

-- ----------------------------
-- Table structure for eprs_contacts_organization
-- ----------------------------
DROP TABLE IF EXISTS `eprs_contacts_organization`;
CREATE TABLE `eprs_contacts_organization`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `b_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织机构名称',
  `parent_id` int(0) NULL DEFAULT 0 COMMENT '父机构',
  `b_status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否有效：默认为是（1），否（0）',
  `sort_num` int(0) NOT NULL DEFAULT 0 COMMENT '排序号：数字越大越排在前面，默认为0',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `duty_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '值班电话',
  `leading_person` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主管负责人（非必填）',
  `leading_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人联系电话',
  `fax` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '传真',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `add_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加时间:申请时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `add_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '添加人id',
  `update_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通讯录组织机构' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of eprs_contacts_organization
-- ----------------------------
INSERT INTO `eprs_contacts_organization` VALUES (1, '撒擦擦擦', 0, 0, 0, '这', 'string', '高飞', '15888888888', 'string', 'string', '2020-12-24 15:34:16', '2020-12-30 14:57:53', '1', 'admin');
INSERT INTO `eprs_contacts_organization` VALUES (2, '高飞飞', 0, 1, 1, '这是', '1', '高飞飞', '15878888888', '这是二份传真', '详详嘻嘻的描述', '2020-12-24 15:34:25', '2020-12-30 14:57:54', '3', '2');
INSERT INTO `eprs_contacts_organization` VALUES (3, '飞凡1', 0, 0, 1, '这是一', 'string', '飞凡11', '15868888888', '这是三份传真', '详详嘻嘻的描述', '2020-12-25 12:55:04', '2020-12-26 10:44:21', 'string', '9a55362d336146c59357792d1c6f0ab8');
INSERT INTO `eprs_contacts_organization` VALUES (4, 'string', 0, 0, 1, '这是一份', 'string', 'string', '15858888888', '这是四份传真', '详详嘻嘻的描述', '2020-12-25 12:55:04', '2020-12-26 10:44:21', 'string', '9a55362d336146c59357792d1c6f0ab8');
INSERT INTO `eprs_contacts_organization` VALUES (5, '高飞', 1, 0, 1, '这是一份详', 'string', '高飞', '15848888888', '这是五份传真', '详详细细的描述', '2020-12-25 12:55:04', '2020-12-30 14:57:38', 'string', 'string');
INSERT INTO `eprs_contacts_organization` VALUES (8, 'string', 1, 0, 1, '这是一份详细地', 'string', 'string', 'string', 'string', 'string', '2020-12-25 12:55:04', '2020-12-30 14:57:40', 'string', 'string');
INSERT INTO `eprs_contacts_organization` VALUES (9, 'string', 0, 0, 1, '这是一份详细地址', 'string', 'string', 'string', 'string', 'string', '2020-12-25 12:55:04', '2020-12-26 10:44:22', 'string', 'string');
INSERT INTO `eprs_contacts_organization` VALUES (10, 'string', 1, 0, 1, 'string', 'string', 'string', 'string', 'string', 'string', '2020-12-25 12:55:04', '2020-12-30 14:57:41', 'string', 'string');
INSERT INTO `eprs_contacts_organization` VALUES (12, 'string', 1, 0, 1, 'string', 'string', 'string', 'string', 'string', 'string', '2020-12-25 12:55:04', '2020-12-30 14:57:42', 'string', 'string');
INSERT INTO `eprs_contacts_organization` VALUES (13, 'string', 0, 0, 1, 'string', 'string', 'string', 'string', 'string', 'string', '2020-12-25 12:55:04', '2020-12-26 10:44:22', 'string', 'string');
INSERT INTO `eprs_contacts_organization` VALUES (15, 'string', 2, 0, 0, 'string', 'string', 'string', 'string', 'string', 'string', '2020-12-25 12:55:04', '2020-12-30 14:57:43', 'string', 'string');
INSERT INTO `eprs_contacts_organization` VALUES (16, 'string', 2, 0, 0, 'string', 'string', 'string', 'string', 'string', 'string', '2020-12-25 12:55:04', '2020-12-30 14:57:44', 'string', 'string');
INSERT INTO `eprs_contacts_organization` VALUES (17, 'string', 5, 0, 0, 'string', 'string', 'string', 'string', 'string', 'string', '2020-12-25 12:55:04', '2020-12-30 14:58:01', 'string', 'string');
INSERT INTO `eprs_contacts_organization` VALUES (18, 'string', 2, 0, 0, 'string', 'string', 'string', 'string', 'string', 'string', '2020-12-25 12:55:04', '2020-12-30 14:57:46', 'string', 'string');
INSERT INTO `eprs_contacts_organization` VALUES (19, 'string', 2, 0, 0, 'string', 'string', 'string', 'string', 'string', 'string', '2020-12-25 12:55:04', '2020-12-30 14:57:46', 'string', 'string');
INSERT INTO `eprs_contacts_organization` VALUES (20, 'string', 2, 0, 0, 'string', 'string', 'string', 'string', 'string', 'string', '2020-12-25 12:55:04', '2020-12-30 14:57:47', 'string', 'string');
INSERT INTO `eprs_contacts_organization` VALUES (21, 'string', 2, 0, 0, 'string', 'string', 'string', 'string', 'string', 'string', '2020-12-25 12:55:04', '2020-12-30 14:57:47', 'string', 'string');
INSERT INTO `eprs_contacts_organization` VALUES (22, 'string', 0, 0, 0, 'string', 'string', 'strin', 'string', 'string', 'string', '2020-12-25 12:55:04', '2020-12-25 12:55:04', 'string', '9a55362d336146c59357792d1c6f0ab8');
INSERT INTO `eprs_contacts_organization` VALUES (89, '北京大学', 0, 1, 3, '易罐', '5343968521', '张三11', '15685996653', NULL, NULL, '2020-12-26 14:58:03', '2020-12-31 09:09:37', 'admin', '9a55362d336146c59357792d1c6f0ab8');
INSERT INTO `eprs_contacts_organization` VALUES (90, '附属小学', 92, 1, 0, '易罐', '5343968521', '张三', '15685996653', NULL, NULL, '2020-12-31 09:25:01', '2020-12-31 11:43:39', 'admin', NULL);
INSERT INTO `eprs_contacts_organization` VALUES (91, '附属中学', 89, 1, 0, '易罐', '5343968521', '张三', '15685996653', NULL, NULL, '2020-12-31 09:25:16', '2020-12-31 11:43:36', 'admin', NULL);
INSERT INTO `eprs_contacts_organization` VALUES (92, '负数小小学', 95, 1, 0, '易罐', '5343968521', '张三', '15685996653', NULL, NULL, '2020-12-31 09:25:22', '2020-12-31 11:43:08', 'admin', '41924961b7c14ddd8669c2f1553a51b6');
INSERT INTO `eprs_contacts_organization` VALUES (93, '附属小学', 89, 0, 0, '', '', '阿瓦达', '13111111111', '', '', '2020-12-31 14:27:39', '2020-12-31 14:27:39', '41924961b7c14ddd8669c2f1553a51b6', '');
INSERT INTO `eprs_contacts_organization` VALUES (94, '警校', 93, 0, 0, '', '', '阿瓦达', '13111111111', '', '', '2020-12-31 14:27:42', '2020-12-31 14:27:43', '41924961b7c14ddd8669c2f1553a51b6', '');
INSERT INTO `eprs_contacts_organization` VALUES (95, '卫校', 93, 0, 0, '', '', '', '', '', '', '2020-12-31 14:40:11', NULL, '41924961b7c14ddd8669c2f1553a51b6', '');
INSERT INTO `eprs_contacts_organization` VALUES (96, '测试', 3, 0, 1, '', '', '问问看1', '', '', '', '2021-01-07 10:57:50', '2021-01-07 10:58:16', '9a55362d336146c59357792d1c6f0ab8', '9a55362d336146c59357792d1c6f0ab8');
INSERT INTO `eprs_contacts_organization` VALUES (97, '撒擦擦擦', 0, 0, 0, '这', 'string', '高飞', '15888888888', 'string', 'string', '2021-01-08 22:42:15', '2021-01-08 22:42:18', '1', 'admin');
INSERT INTO `eprs_contacts_organization` VALUES (98, '撒擦擦擦', 97, 0, 0, '这', 'string', '高飞', '15888888888', 'string', 'string', '2021-01-08 22:42:22', '2021-01-08 22:42:24', '1', 'admin');
INSERT INTO `eprs_contacts_organization` VALUES (99, '1233', 97, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2021-01-08 22:42:29', '2021-01-08 22:42:30', NULL, NULL);
INSERT INTO `eprs_contacts_organization` VALUES (100, '4244', 89, 0, 0, '', '', '', '', '', '', '2021-01-12 13:56:56', NULL, '9a55362d336146c59357792d1c6f0ab8', '');

SET FOREIGN_KEY_CHECKS = 1;
