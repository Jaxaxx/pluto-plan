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

 Date: 24/08/2020 18:09:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`
(
    `client_id`               varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `resource_ids`            varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL,
    `client_secret`           varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL,
    `scope`                   varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL,
    `authorized_grant_types`  varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL,
    `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL,
    `authorities`             varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL,
    `access_token_validity`   int(0)                                                         DEFAULT NULL,
    `refresh_token_validity`  int(0)                                                         DEFAULT NULL,
    `additional_information`  varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `autoapprove`             varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL,
    PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details`
VALUES ('admin', NULL, '$2a$10$M3jJ13yamQkrxLPC2zC/lOtVruBkVxMQ/MJKiiRRb8wrsAvxx5AYW', 'all',
        'authorization_code,refresh_token,password,mobile', NULL, NULL, 3600, 36000, NULL, '1');
INSERT INTO `oauth_client_details`
VALUES ('admin1', NULL, '$2a$10$M3jJ13yamQkrxLPC2zC/lOtVruBkVxMQ/MJKiiRRb8wrsAvxx5AYW', 'all',
        'authorization_code,refresh_token,password,mobile', NULL, NULL, 3600, 36000, NULL, '1');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`
(
    `id`               bigint unsigned          NOT NULL COMMENT 'id',
    `type`             int(0)                                                         DEFAULT 1 COMMENT '日志类型',
    `title`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT '' COMMENT '日志标题',
    `service_id`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   DEFAULT '' COMMENT '服务id',
    `remote_addr`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT '' COMMENT '操作IP地址',
    `user_agent`       varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '用户代理',
    `request_uri`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT '' COMMENT '请求URI',
    `method`           varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   DEFAULT '' COMMENT '操作方式',
    `params`           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '操作提交的数据',
    `time`             bigint(0)                                                      DEFAULT NULL COMMENT '执行时间',
    `exception`        text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '异常信息',
    `is_deleted`       int(1) UNSIGNED ZEROFILL NOT NULL                              DEFAULT 0 COMMENT '是否删除：0-否，1-是',
    `create_user_id`   bigint unsigned COMMENT '创建用户id',
    `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   DEFAULT '' COMMENT '创建用户名称',
    `create_time`      datetime(0)                                                    DEFAULT NULL COMMENT '创建时间',
    `update_user_id`   bigint unsigned COMMENT '更新用户id',
    `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   DEFAULT '' COMMENT '更新用户名称',
    `update_time`      datetime(0)                                                    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `sys_log_request_uri` (`request_uri`) USING BTREE,
    INDEX `sys_log_type` (`type`) USING BTREE,
    INDEX `idx_sys_log_title` (`title`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '日志表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log`
VALUES (1297759228548435970, 0, '新增用户', '/upmsx', '127.0.0.1', 'PostmanRuntime/7.26.3', '/sysUserBase', 'POST', '', 218,
        NULL, 0, 1293469257280847873, 'myuser', '2020-08-24 12:54:46', NULL, '', NULL);
INSERT INTO `sys_log`
VALUES (1297759441958817793, 0, '新增用户', '/upmsx', '127.0.0.1', 'PostmanRuntime/7.26.3', '/sysUserBase', 'POST', '', 20,
        NULL, 0, 1293469257280847873, 'myuser', '2020-08-24 12:55:37', NULL, '', NULL);
INSERT INTO `sys_log`
VALUES (1297770753753477121, 0, '新增用户', '/upmsx', '127.0.0.1', 'PostmanRuntime/7.26.3', '/sysUserBase', 'POST', '', 29,
        NULL, 0, 1293469257280847873, 'myuser', '2020-08-24 13:40:34', NULL, '', NULL);
INSERT INTO `sys_log`
VALUES (1297776207875403777, 1, '新增用户', '/upmsx', '127.0.0.1', 'PostmanRuntime/7.26.3', '/sysUserBase', 'POST', '', 29,
        NULL, 0, 1293469257280847873, 'me', '2020-08-24 14:02:14', NULL, '', NULL);
INSERT INTO `sys_log`
VALUES (1297778758179962881, 0, '新增用户', '/upmsx', '127.0.0.1', 'PostmanRuntime/7.26.3', '/sysUserBase', 'POST', '', 547,
        NULL, 0, 1293469257280847873, 'me', '2020-08-24 14:12:22', NULL, '', NULL);
INSERT INTO `sys_log`
VALUES (1297788633542889473, 0, '新增用户', '/upmsx', '127.0.0.1', 'PostmanRuntime/7.26.3', '/sysUserBase', 'POST', '', 210,
        NULL, 0, 1293469257280847873, 'me', '2020-08-24 14:51:37', NULL, '', NULL);
INSERT INTO `sys_log`
VALUES (1297788979040292865, 0, '新增用户', '/upmsx', '127.0.0.1', 'PostmanRuntime/7.26.3', '/sysUserBase', 'POST', '', 30,
        NULL, 0, 1293469257280847873, 'me', '2020-08-24 14:52:59', NULL, '', NULL);
INSERT INTO `sys_log`
VALUES (1297797499261317121, 0, '新增用户', '/upmsx', '127.0.0.1', 'PostmanRuntime/7.26.3', '/sysUserBase', 'POST', '',
        1314, NULL, 0, 1293469257280847873, 'me', '2020-08-24 15:26:50', NULL, '', NULL);
INSERT INTO `sys_log`
VALUES (1297797941072523266, 0, '新增用户', '/upmsx', '127.0.0.1', 'PostmanRuntime/7.26.3', '/sysUserBase', 'POST', '',
        1348, NULL, 0, 1293469257280847873, 'me', '2020-08-24 15:28:36', NULL, '', NULL);

-- ----------------------------
-- Table structure for sys_user_base
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_base`;
CREATE TABLE `sys_user_base`
(
    `id`               bigint unsigned                                               NOT NULL COMMENT '用户Id',
    `user_name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci           DEFAULT NULL COMMENT '用户名',
    `password`         varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录密码',
    `mobile`           varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci           DEFAULT NULL COMMENT '移动电话号码',
    `email`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci           DEFAULT NULL COMMENT '邮箱',
    `id_card`          char(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci              DEFAULT '' COMMENT '身份证号码',
    `is_locked`        int(1) UNSIGNED ZEROFILL                                      NOT NULL DEFAULT 0 COMMENT '是否锁定  1 是 0 否',
    `is_enabled`       int(1) UNSIGNED ZEROFILL                                      NOT NULL DEFAULT 0 COMMENT '是否启用  1 是 0 否 ',
    `last_login_time`  datetime(0)                                                            DEFAULT NULL COMMENT '最后登录时间',
    `is_deleted`       int(1) UNSIGNED ZEROFILL                                      NOT NULL DEFAULT 0 COMMENT '是否删除 1 是 0 否 ',
    `create_user_id`   bigint unsigned COMMENT '创建用户Id',
    `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci           DEFAULT '' COMMENT '创建用户名称',
    `create_time`      datetime(0)                                                            DEFAULT NULL COMMENT '创建时间',
    `update_user_id`   bigint unsigned COMMENT '更新用户Id',
    `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci           DEFAULT '' COMMENT '更新用户名称',
    `update_time`      datetime(0)                                                            DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '登录信息表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_base
-- ----------------------------
INSERT INTO `sys_user_base`
VALUES (1293469257280847873, 'me', '$2a$10$M3jJ13yamQkrxLPC2zC/lOtVruBkVxMQ/MJKiiRRb8wrsAvxx5AYW', '16601770650',
        '247474571@qq.com', '111111111111111111', 0, 0, '2020-08-13 15:26:46', 0, 1, 'my123', '2020-08-12 16:47:57',
        NULL, '', NULL);
INSERT INTO `sys_user_base`
VALUES (1293469257280847888, 'myuser', '$2a$10$M3jJ13yamQkrxLPC2zC/lOtVruBkVxMQ/MJKiiRRb8wrsAvxx5AYW', '16601770650',
        '247474571@qq.com', '111111111111111111', 0, 0, '2020-08-14 17:14:27', 0, 0, 'string', '2020-08-14 17:14:27', 0,
        'string', '2020-08-14 17:14:27');

SET FOREIGN_KEY_CHECKS = 1;
