/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : test-a

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 14/08/2020 15:22:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `access_token_validity` int(0) DEFAULT NULL,
  `refresh_token_validity` int(0) DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('admin', NULL, '$2a$10$M3jJ13yamQkrxLPC2zC/lOtVruBkVxMQ/MJKiiRRb8wrsAvxx5AYW', 'all', 'authorization_code,refresh_token,password,mobile', NULL, NULL, 3600, 36000, NULL, '1');
INSERT INTO `oauth_client_details` VALUES ('admin1', NULL, '$2a$10$M3jJ13yamQkrxLPC2zC/lOtVruBkVxMQ/MJKiiRRb8wrsAvxx5AYW', 'all', 'authorization_code,refresh_token,password,mobile', NULL, NULL, 3600, 36000, NULL, '1');

-- ----------------------------
-- Table structure for sys_user_base
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_base`;
CREATE TABLE `sys_user_base`  (
  `id` bigint unsigned NOT NULL COMMENT '用户Id',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录密码',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '移动电话号码',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `id_card` char(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '身份证号码',
  `is_locked` int unsigned NOT NULL COMMENT '是否锁定  1 是 0 否',
  `is_enabled` int unsigned NOT NULL COMMENT '是否启用  1 是 0 否 ',
  `last_login_time` datetime(0) DEFAULT NULL COMMENT '最后登录时间',
  `is_deleted` int unsigned NOT NULL COMMENT '是否删除 1 是 0 否 ',
  `create_user_id` bigint unsigned COMMENT '创建用户Id',
  `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建用户名称',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint unsigned COMMENT '修改用户Id',
  `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新用户名称',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '登录信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_base
-- ----------------------------
INSERT INTO `sys_user_base` VALUES (1293469257280847873, 'myuser', '$2a$10$M3jJ13yamQkrxLPC2zC/lOtVruBkVxMQ/MJKiiRRb8wrsAvxx5AYW', '13012345678', 'jax0910@163.com', '111111111111111111', 0, 0, '2020-08-13 15:26:46', 0, 1, 'my123', '2020-08-12 16:47:57', NULL, '', NULL);

SET FOREIGN_KEY_CHECKS = 1;
