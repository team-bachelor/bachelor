/*
 Navicat Premium Data Transfer

 Source Server         : 121.42.229.106dy_yjtxl
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 121.42.229.106:3316
 Source Schema         : dy_yjtxl

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 29/12/2020 09:41:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cmn_acm_menu
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_menu`;
CREATE TABLE `cmn_acm_menu`
(
    `ID`          varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT 'ID',
    `NAME`        varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '菜单名称',
    `CODE`        varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '菜单编码',
    `URI`         varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单定位',
    `ICON`        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标路径',
    `TYPE`        varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '菜单类型',
    `PARENT_ID`   varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '父级ID',
    `COMMENT`     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '说明',
    `SEQ_ORDER`   decimal(2, 0)                                           NOT NULL COMMENT '排序',
    `UPDATE_TIME` datetime(0)                                             NOT NULL COMMENT '更新时间',
    `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '更新人',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for cmn_acm_obj_domain
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_obj_domain`;
CREATE TABLE `cmn_acm_obj_domain`
(
    `ID`          varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
    `NAME`        varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '域名称',
    `CODE`        varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '域编码',
    `IS_SYS`      bit(1)                                                 NOT NULL DEFAULT b'0' COMMENT '是否系统默认',
    `SEQ_ORDER`   decimal(2, 0)                                          NOT NULL COMMENT '排序',
    `UPDATE_TIME` datetime(0)                                            NOT NULL COMMENT '更新时间',
    `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `CODE` (`CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '要有初始化数据'
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for cmn_acm_obj_operation
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_obj_operation`;
CREATE TABLE `cmn_acm_obj_operation`
(
    `ID`          varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
    `NAME`        varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作名称',
    `CODE`        varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作编码',
    `METHOD`      varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'GET' COMMENT '对应的HTTP METHOD',
    `IS_SYS`      bit(1)                                                 NOT NULL DEFAULT b'0' COMMENT '是否系统默认',
    `UPDATE_TIME` datetime(0)                                            NOT NULL COMMENT '更新时间',
    `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `CODE` (`CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '要有初始化数据'
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for cmn_acm_obj_permission
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_obj_permission`;
CREATE TABLE `cmn_acm_obj_permission`
(
    `ID`          varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT 'ID',
    `NAME`        varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '对象名称',
    `CODE`        varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '对象编码',
    `URI`         varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象定位',
    `OPERATE`     varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '对象操作',
    `TYPE`        varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '对象类型',
    `DOMAIN_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '所属域编码',
    `IS_SYS`      bit(1)                                                 NOT NULL DEFAULT b'0' COMMENT '是否系统默认',
    `SEQ_ORDER`   int(2)                                                  NOT NULL DEFAULT 0 COMMENT '排序',
    `DEF_AUTH_OP` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci   NOT NULL DEFAULT 'c' COMMENT '默认权限行为',
    `COMMENT`     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '说明',
    `UPDATE_TIME` datetime(0)                                             NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '更新人',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `NAME` (`NAME`) USING BTREE,
    UNIQUE INDEX `CODE` (`CODE`) USING BTREE,
    INDEX `URI` (`URI`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for cmn_acm_org_menu
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_org_menu`;
CREATE TABLE `cmn_acm_org_menu`
(
    `ID`          varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
    `ORG_CODE`    varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组织机构编码',
    `MENU_CODE`   varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单编码',
    `UPDATE_TIME` datetime(0)                                            NOT NULL COMMENT '更新时间',
    `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for cmn_acm_org_permission
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_org_permission`;
CREATE TABLE `cmn_acm_org_permission`
(
    `ID`          varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT 'ID',
    `ORG_CODE`    varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '机构编码',
    `OBJ_CODE`    varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象编码',
    `OBJ_URI`     varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象定位',
    `OBJ_OPERATE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '对象操作',
    `UPDATE_TIME` datetime(0)                                             NOT NULL COMMENT '更新时间',
    `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '更新人',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `ORG_CODE` (`ORG_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for cmn_acm_role
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_role`;
CREATE TABLE `cmn_acm_role`
(
    `ID`          varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
    `NAME`        varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
    `CODE`        varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
    `ORG_CODE`    varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织机构编码',
    `UPDATE_TIME` datetime(0)                                            NOT NULL COMMENT '更新时间',
    `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `CODE` (`CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for cmn_acm_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_role_menu`;
CREATE TABLE `cmn_acm_role_menu`
(
    `ID`          varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
    `ROLE_CODE`   varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
    `MENU_CODE`   varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单编码',
    `UPDATE_TIME` datetime(0)                                            NOT NULL COMMENT '更新时间',
    `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for cmn_acm_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_role_permission`;
CREATE TABLE `cmn_acm_role_permission`
(
    `ID`          varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT 'ID',
    `ROLE_CODE`   varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '角色编码',
    `OBJ_CODE`    varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '对象编码',
    `OBJ_URI`     varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象定位',
    `OBJ_OPERATE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '对象操作',
    `UPDATE_TIME` datetime(0)                                             NOT NULL COMMENT '更新时间',
    `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '更新人',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `ROLE_CODE` (`ROLE_CODE`, `OBJ_CODE`) USING BTREE,
    INDEX `OBJ_CODE` (`OBJ_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for cmn_acm_user_menu
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_user_menu`;
CREATE TABLE `cmn_acm_user_menu`
(
    `ID`          varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
    `USER_CODE`   varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户编码',
    `MENU_CODE`   varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单编码',
    `UPDATE_TIME` datetime(0)                                            NOT NULL COMMENT '更新时间',
    `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for cmn_acm_user_permission
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_user_permission`;
CREATE TABLE `cmn_acm_user_permission`
(
    `ID`          varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT 'ID',
    `USER_CODE`   varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户编码',
    `OBJ_CODE`    varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '对象编码',
    `OBJ_URI`     varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对象定位',
    `OBJ_OPERATE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '对象操作',
    `UPDATE_TIME` datetime(0)                                             NOT NULL COMMENT '更新时间',
    `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '更新人',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `USER_CODE` (`USER_CODE`, `OBJ_CODE`) USING BTREE,
    INDEX `OBJ_CODE` (`OBJ_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for cmn_acm_user_role
-- ----------------------------
DROP TABLE IF EXISTS `cmn_acm_user_role`;
CREATE TABLE `cmn_acm_user_role`
(
    `ID`          varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
    `USER_ID`     varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户系统ID',
    `USER_CODE`   varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户编码',
    `ROLE_CODE`   varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
    `UPDATE_TIME` datetime(0)                                            NOT NULL COMMENT '更新时间',
    `UPDATE_USER` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新人',
    PRIMARY KEY (`ID`) USING BTREE,
    UNIQUE INDEX `USER_CODE` (`USER_CODE`, `ROLE_CODE`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for eprs_contacts_contact
-- ----------------------------
DROP TABLE IF EXISTS `eprs_contacts_contact`;
CREATE TABLE `eprs_contacts_contact`
(
    `id`           int(11)                                                       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `b_name`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '姓名',
    `phone`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '手机号',
    `email`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '邮箱',
    `gender`       tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '性别1男2女0保密',
    `office_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '办公电话',
    `home_phone`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '家庭电话',
    `home_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '家庭地址',
    `group_id`     int(11)                                                       NOT NULL COMMENT '所属分类id book_group表的id',
    `office_name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '工作单位',
    `department`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '部门',
    `b_status`     tinyint(1)                                                    NOT NULL DEFAULT 1 COMMENT '是否有效：默认为是（1），否（0）',
    `sort_num`     int(11)                                                       NOT NULL DEFAULT 0 COMMENT '排序号：数字越大越排在前面，默认为0',
    `add_time`     timestamp(0)                                                  NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    `update_time`  timestamp(0)                                                  NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `add_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '添加人id',
    `update_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '修改人id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 552
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '通讯录'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for eprs_contacts_logs
-- ----------------------------
DROP TABLE IF EXISTS `eprs_contacts_logs`;
CREATE TABLE `eprs_contacts_logs`
(
    `id`        bigint(20)                                               NOT NULL AUTO_INCREMENT,
    `user_id`   char(32) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT NULL COMMENT '用户id',
    `username`  varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
    `modules`   varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '模块接口',
    `operation` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '用户操作',
    `time`      int(11)                                                  NULL DEFAULT NULL COMMENT '响应时间',
    `method`    varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '请求方法',
    `params`    varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求参数',
    `ip`        varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT 'IP地址',
    `add_time`  datetime(0)                                              NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 940
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '通讯录日志'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for eprs_contacts_organization
-- ----------------------------
DROP TABLE IF EXISTS `eprs_contacts_organization`;
CREATE TABLE `eprs_contacts_organization`
(
    `id`             int(11)                                                       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `b_name`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '组织机构名称',
    `parent_id`      int(11)                                                       NULL     DEFAULT NULL COMMENT '父机构',
    `b_status`       tinyint(1)                                                    NOT NULL DEFAULT 1 COMMENT '是否有效：默认为是（1），否（0）',
    `sort_num`       int(11)                                                       NOT NULL DEFAULT 0 COMMENT '排序号：数字越大越排在前面，默认为0',
    `address`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '地址',
    `duty_phone`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '值班电话',
    `leading_person` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '主管负责人（非必填）',
    `leading_phone`  varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '负责人联系电话',
    `fax`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '传真',
    `description`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '描述',
    `add_time`       timestamp(0)                                                  NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间:申请时间',
    `update_time`    timestamp(0)                                                  NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `add_id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '添加人id',
    `update_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '修改人id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 90
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '通讯录组织机构'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`
(
    `id`        bigint(20)                                              NOT NULL AUTO_INCREMENT,
    `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
    `password`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
    `user_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户类型',
    `enabled`   int(11)                                                 NULL DEFAULT NULL COMMENT '是否可用',
    `real_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
    `qq`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'qq',
    `email`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
    `address`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
    `tel`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
