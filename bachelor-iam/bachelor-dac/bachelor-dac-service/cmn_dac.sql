/*
 Navicat Premium Data Transfer

 Source Server         : 封闭园区-测试
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : 10.0.139.239:3306
 Source Schema         : eprs_acm

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 16/12/2022 15:53:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cmn_dac_area
-- ----------------------------
DROP TABLE IF EXISTS `cmn_dac_area`;
CREATE TABLE `cmn_dac_area`  (
  `ID` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `NAME` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '区域名称',
  `CODE` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '区域编码',
  `PARENT_CODE` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父区域',
  `IS_DELETED` tinyint(1) NOT NULL DEFAULT 1 COMMENT '删除标记（0:不可用 1:可用）',
  `UPDATE_USER` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
  `UPDATE_TIME` datetime(0) NOT NULL COMMENT '更新时间',
  `CREATE_USER` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cmn_dac_area_user
-- ----------------------------
DROP TABLE IF EXISTS `cmn_dac_area_user`;
CREATE TABLE `cmn_dac_area_user`  (
  `ID` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `AREA_ID` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '区域ID',
  `AREA_NAME` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '区域名称',
  `AREA_CODE` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '区域编码',
  `USER_CODE` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `USER_NAME` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `ORG_NAME` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机构名称',
  `CREATE_USER` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cmn_dac_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `cmn_dac_dictionary`;
CREATE TABLE `cmn_dac_dictionary`  (
  `type_code` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据类型',
  `type_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据类型名称',
  `parent_type_code` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属父类型',
  `data_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据类型',
  `data_order` int(2) NULL DEFAULT NULL COMMENT '排序字段',
  PRIMARY KEY (`type_code`, `data_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
