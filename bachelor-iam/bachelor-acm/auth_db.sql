/*
 Date: 26/01/2021 10:16:17
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cmn_acm_menu
-- ----------------------------
INSERT INTO `cmn_acm_menu` VALUES ('1', '控制台', 'menu.home', '/', 'el-icon-mobile-phone', 'i', '', '控制台', 0, '2018-12-11 11:41:44', '');
INSERT INTO `cmn_acm_menu` VALUES ('2', '角色管理', 'menu.role', '/roles', 'fa fa-user-circle-o', 'i', NULL, '角色管理', 0, '2018-12-11 11:41:44', '');
INSERT INTO `cmn_acm_menu` VALUES ('31', '角色管理', 'menu.roles', '/roles', 'fa fa-user-circle-o', 'i', '', '角色管理', 0, '2018-12-11 11:46:14', '');
INSERT INTO `cmn_acm_menu` VALUES ('5', '子系统列表', 'menu.subsystem', '/subsystem', 'fa fa-user-circle-o', 'i', NULL, '子系统列表', 0, '2018-12-11 11:41:44', '');

-- ----------------------------
-- Table structure for cmn_acm_obj_domain
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_obj_domain`;
CREATE TABLE `cmn_acm_obj_domain`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '域名称',
  `CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '域编码',
  `IS_SYS` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否为系统默认',
  `SEQ_ORDER` decimal(2, 0) NOT NULL COMMENT '排序',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `CODE`(`CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '要有初始化数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cmn_acm_obj_domain
-- ----------------------------
INSERT INTO `cmn_acm_obj_domain` VALUES ('1', '用户', 'user', b'0', 1, '2018-10-29 09:51:24', 'system');

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '要有初始化数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cmn_acm_obj_operation
-- ----------------------------
INSERT INTO `cmn_acm_obj_operation` VALUES ('1', '创建', 'create', 'POST', b'1', '2018-10-17 00:00:00', 'system');
INSERT INTO `cmn_acm_obj_operation` VALUES ('2', '更新', 'update', 'PUT', b'1', '2018-10-17 00:00:00', 'system');
INSERT INTO `cmn_acm_obj_operation` VALUES ('3', '查询', 'query', 'GET', b'1', '2018-10-17 00:00:00', 'system');
INSERT INTO `cmn_acm_obj_operation` VALUES ('4', '删除', 'delete', 'DELETE', b'1', '2018-10-17 00:00:00', 'system');
INSERT INTO `cmn_acm_obj_operation` VALUES ('5', '列表', 'list', 'GET', b'1', '2019-02-01 15:50:00', 'system');
INSERT INTO `cmn_acm_obj_operation` VALUES ('6', '撤回', 'withdraw', 'PUT', b'1', '2019-02-01 15:50:00', 'system');
INSERT INTO `cmn_acm_obj_operation` VALUES ('7', '批量', 'batch', 'POST', b'1', '2019-02-01 15:50:00', 'system');

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
  `IS_SYS` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否为系统默认',
  `SEQ_ORDER` int(0) NOT NULL DEFAULT 0 COMMENT '排序',
  `DEF_AUTH_OP` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'c' COMMENT '默认权限行为',
  `COMMENT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `NAME`(`NAME`) USING BTREE,
  UNIQUE INDEX `CODE`(`CODE`) USING BTREE,
  INDEX `URI`(`URI`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cmn_acm_obj_permission
-- ----------------------------
INSERT INTO `cmn_acm_obj_permission` VALUES ('1', '创建用户', 'post:/manage/user', '/manage/user', 'create', 'i', 'user', b'0', 0, 'c', NULL, '2018-10-17 00:00:00', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('10', '错误测试', 'get:/exception/{type}/{msg}', '/exception/{type}/{msg}', 'query', 'i', 'role', b'0', 0, 'c', NULL, '2018-11-08 12:01:06', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('11', '获取组织机构', 'get:/orgs', '/orgs', 'query', 'i', 'sys', b'0', 0, 'c', NULL, '2018-11-14 18:14:46', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('12', '获取用户', 'get:/users', '/users', 'query', 'i', 'sys', b'0', 0, 'c', NULL, '2018-11-14 18:15:31', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('13', '获得全部权限', 'get:/permissions', '/permissions', 'list', 'i', 'sys', b'0', 1, 'c', NULL, '2018-11-15 18:15:00', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('14', '获取用户角色', 'get:/roles/{userCode}', '/roles/{userCode}', 'query', 'i', 'role', b'0', 0, 'c', NULL, '2018-12-21 15:37:11', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('15', '设置角色的权限', 'post:/role_permission/{role}', '/role_permission/{role}', 'post', 'i', 'sys', b'0', 3, 'c', NULL, '2018-11-17 18:15:00', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('16', '获得用户的权限', 'get:/user_permission/{user}', '/user_permission/{user}', 'query', 'i', 'sys', b'0', 4, 'c', NULL, '2018-11-18 18:15:00', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('17', '获得全部菜单', 'get:/menus', '/menus', 'list', 'i', 'sys', b'0', 5, 'c', NULL, '2018-11-19 18:15:00', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('18', '获得角色的菜单', 'get:/role_menu/{role}', '/role_menu/{role}', 'query', 'i', 'sys', b'0', 6, 'c', NULL, '2018-11-20 18:15:00', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('19', '设置角色的菜单', 'post:/role_menu/{role}', '/role_menu/{role}', 'create', 'i', 'sys', b'0', 7, 'c', NULL, '2018-11-21 18:15:00', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('2', '修改密码', 'put:/user/chagePw', '/user/chagePw', 'update', 'i', 'user', b'0', 1, 'c', NULL, '2018-10-22 15:53:22', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('20', '获得用户的菜单', 'get:/user_menu/{user}', '/user_menu/{user}', 'query', 'i', 'sys', b'0', 8, 'c', NULL, '2018-11-22 18:15:00', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('21', '将用户从角色中删除', 'put:/role_user/{roleCode}', '/role_user/{roleCode}', 'update', 'i', 'sys', b'0', 9, 'c', NULL, '2018-11-23 18:15:00', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('22', '为角色增加用户', 'post:/role_user/{roleCode}', '/role_user/{roleCode}', 'create', 'i', 'sys', b'0', 10, 'c', NULL, '2018-11-24 18:15:00', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('23', '获取角色下的用户', 'get:/role_user/{roleCode}', '/role_user/{roleCode}', 'query', 'i', 'sys', b'0', 11, 'c', NULL, '2018-11-25 18:15:00', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('24', '修改角色', 'put:/role', '/role', 'update', 'i', 'sys', b'0', 12, 'c', NULL, '2018-11-26 18:15:00', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('25', '获得角色的权限', 'get:/role_permission/{role}', '/role_permission/{role}', 'query', 'i', 'role', b'0', 0, 'c', NULL, '2018-11-14 18:42:49', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('3', '删除用户', 'delete:/manage/user', '/manage/user', 'delete', 'i', 'user', b'0', 2, 'c', NULL, '2018-10-17 00:00:00', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('4', '修改用户', 'put:/manage/user', '/manage/user', 'update', 'i', 'user', b'0', 3, 'c', NULL, '2018-10-17 00:00:00', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('5', '查询用户', 'get:/manage/user', '/manage/user', 'query', 'i', 'user', b'0', 4, 'c', NULL, '2018-10-17 00:00:00', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('6', '删除角色', 'delete:/role/{roleID}', '/role/{roleID}', 'delete', 'i', 'role', b'0', 0, 'c', NULL, '2018-11-05 22:33:12', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('7', '角色查询', 'get:/roles', '/roles', 'get', 'i', 'role', b'0', 0, 'c', NULL, '2018-11-08 12:01:06', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('8', '获取单个角色', 'get:/role/{roleID}', '/role/{roleID}', 'query', 'i', 'role', b'0', 0, 'c', NULL, '2018-11-08 12:01:06', 'system');
INSERT INTO `cmn_acm_obj_permission` VALUES ('9', '创建角色', 'post:/role', '/role', 'create', 'i', 'role', b'0', 0, 'c', NULL, '2018-11-08 12:01:06', 'system');

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cmn_acm_role
-- ----------------------------
INSERT INTO `cmn_acm_role` VALUES ('1', '系统管理员', 'sys_admin', 'sys', '2018-11-05 22:25:48', 'system');

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cmn_acm_role_permission
-- ----------------------------
INSERT INTO `cmn_acm_role_permission` VALUES ('1', 'sys_admin', 'post:/manage/user', '/manage/user', 'create', '2018-10-17 00:00:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('10', 'sys_admin', 'post:/role_menu/{role}', '/role_menu/{role}', 'create', '2018-11-21 18:15:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('11', 'sys_admin', 'put:/user/chagePw', '/user/chagePw', 'update', '2018-10-22 15:53:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('12', 'sys_admin', 'get:/user_menu/{user}', '/user_menu/{user}', 'query', '2018-11-22 18:15:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('13', 'sys_admin', 'put:/role_user/{roleCode}', '/role_user/{roleCode}', 'update', '2018-11-23 18:15:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('14', 'sys_admin', 'post:/role_user/{roleCode}', '/role_user/{roleCode}', 'create', '2018-11-24 18:15:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('15', 'sys_admin', 'get:/role_user/{roleCode}', '/role_user/{roleCode}', 'query', '2018-11-25 18:15:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('16', 'sys_admin', 'put:/role', '/role', 'update', '2018-11-26 18:15:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('17', 'sys_admin', 'get:/role_permission/{role}', '/role_permission/{role}', 'query', '2018-11-14 18:42:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('18', 'sys_admin', 'delete:/manage/user', '/manage/user', 'delete', '2018-10-17 00:00:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('19', 'sys_admin', 'put:/manage/user', '/manage/user', 'update', '2018-10-17 00:00:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('2', 'sys_admin', 'get:/exception/{type}/{msg}', '/exception/{type}/{msg}', 'query', '2018-11-08 12:01:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('20', 'sys_admin', 'get:/manage/user', '/manage/user', 'query', '2018-10-17 00:00:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('200', 'sys_admin', 'get:/user_permission/{user}', '/user_permission/{user}', 'query', '2018-11-21 18:15:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('201', 'sys_admin', 'get:/roles/{userCode}', '/roles/{userCode}', 'query', '2018-11-21 18:15:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('21', 'sys_admin', 'delete:/role/{roleID}', '/role/{roleID}', 'delete', '2018-11-05 22:33:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('22', 'sys_admin', 'get:/roles', '/roles', 'query', '2018-11-08 12:01:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('23', 'sys_admin', 'get:/role/{roleID}', '/role/{roleID}', 'query', '2018-11-08 12:01:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('24', 'sys_admin', 'post:/role', '/role', 'create', '2018-11-08 12:01:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('3', 'sys_admin', 'get:/orgs', '/orgs', 'query', '2018-11-14 18:14:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('4', 'sys_admin', 'get:/users', '/users', 'query', '2018-11-14 18:15:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('5', 'sys_admin', 'get:/permissions', '/permissions', 'query', '2018-11-15 18:15:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('54', 'sys_admin', 'get:/dictionary/{diccode}', '/dictionary/{diccode}', 'query', '2019-01-18 10:44:39', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('6', 'sys_admin', 'post:/role_permission/{role}', '/role_permission/{role}', 'create', '2018-11-17 18:15:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('8', 'sys_admin', 'get:/menus', '/menus', 'query', '2018-11-19 18:15:00', 'system');
INSERT INTO `cmn_acm_role_permission` VALUES ('9', 'sys_admin', 'get:/role_menu/{role}', '/role_menu/{role}', 'query', '2018-11-20 18:15:00', 'system');

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
  UNIQUE INDEX `USER_CODE`(`USER_CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cmn_acm_user_permission
-- ----------------------------
INSERT INTO `cmn_acm_user_permission` VALUES ('1', 'liuzhuo', 'put:/manage/user', 'manage/user', 'create', '2018-10-22 19:24:47', 'system');

-- ----------------------------
-- Table structure for cmn_acm_user_role
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_user_role`;
CREATE TABLE `cmn_acm_user_role`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `USER_ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `USER_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ROLE_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `USER_CODE`(`USER_CODE`, `ROLE_CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cmn_acm_user_role
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
