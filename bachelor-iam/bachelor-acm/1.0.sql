/*
 Date: 20/02/2021 09:18:59
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
-- Table structure for cmn_auth_objects
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_objects`;
CREATE TABLE `cmn_auth_objects`  (
  `ID` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `NAME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象名称',
  `CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对象编码',
  `URI` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象定位',
  `OPERATE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象操作',
  `TYPE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象类型',
  `DOMAIN_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属域编码',
  `SEQ_ORDER` int(2) NOT NULL DEFAULT 0 COMMENT '排序',
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
  `ORG_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织机构编码',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `CODE`(`CODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cmn_auth_user_permission
-- ----------------------------
DROP TABLE IF EXISTS `cmn_auth_user_permission`;
CREATE TABLE `cmn_auth_user_permission`  (
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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
