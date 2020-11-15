/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 127.0.0.1:3306
 Source Schema         : pluto-plan

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 15/11/2020 19:29:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`
(
    `id`               bigint unsigned          NOT NULL COMMENT 'id',
    `type`             int                                                            DEFAULT '1' COMMENT '日志类型',
    `title`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   DEFAULT '' COMMENT '日志标题',
    `service_id`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    DEFAULT '' COMMENT '服务id',
    `remote_addr`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   DEFAULT '' COMMENT '操作IP地址',
    `user_agent`       varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT '' COMMENT '用户代理',
    `request_uri`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   DEFAULT '' COMMENT '请求URI',
    `method`           varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    DEFAULT '' COMMENT '操作方式',
    `params`           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '操作提交的数据',
    `time`             bigint                                                         DEFAULT NULL COMMENT '执行时间',
    `exception`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '异常信息',
    `is_deleted`       int(1) unsigned zerofill NOT NULL                              DEFAULT '0' COMMENT '是否删除：0-否，1-是',
    `create_user_id`   bigint unsigned                                                DEFAULT NULL COMMENT '创建用户id',
    `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    DEFAULT '' COMMENT '创建用户名称',
    `create_time`      datetime                                                       DEFAULT NULL COMMENT '创建时间',
    `update_user_id`   bigint unsigned                                                DEFAULT NULL COMMENT '更新用户id',
    `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    DEFAULT '' COMMENT '更新用户名称',
    `update_time`      datetime                                                       DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `sys_log_request_uri` (`request_uri`) USING BTREE,
    KEY `sys_log_type` (`type`) USING BTREE,
    KEY `idx_sys_log_title` (`title`) USING BTREE,
    KEY `create_user_id` (`create_user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='日志表';

-- ----------------------------
-- Table structure for sys_oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `sys_oauth_client_details`;
CREATE TABLE `sys_oauth_client_details`
(
    `client_id`               varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端ID',
    `resource_ids`            varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '资源ID集合,多个资源时用逗号(,)分隔',
    `client_name`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端名称',
    `client_secret`           varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端密匙',
    `scope`                   varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端申请的权限范围',
    `authorized_grant_types`  varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '客户端支持的grant_type',
    `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '重定向URI',
    `authorities`             varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔',
    `access_token_validity`   int                                                           NOT NULL COMMENT '访问令牌有效时间值(单位:秒)',
    `refresh_token_validity`  int                                                           NOT NULL COMMENT '更新令牌有效时间值(单位:秒)',
    `additional_information`  varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         DEFAULT NULL COMMENT '预留字段',
    `autoapprove`             varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '用户是否自动Approval操作',
    `is_deleted`              tinyint(1) unsigned zerofill                                  NOT NULL DEFAULT '0' COMMENT '是否删除 1-是 0 否',
    `create_user_id`          bigint unsigned                                                        DEFAULT NULL COMMENT '创建用户Id',
    `create_user_name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci            DEFAULT '' COMMENT '创建用户名称',
    `create_time`             datetime                                                               DEFAULT NULL COMMENT '创建时间',
    `update_user_id`          bigint unsigned                                                        DEFAULT NULL COMMENT '更新用户Id',
    `update_user_name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci            DEFAULT '' COMMENT '更新用户名称',
    `update_time`             datetime                                                               DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='客户端信息表';

-- ----------------------------
-- Records of sys_oauth_client_details
-- ----------------------------
BEGIN;
INSERT INTO `sys_oauth_client_details`
VALUES ('admin', NULL, '', 'admin', 'all', 'authorization_code,refresh_token,password,mobile', NULL, NULL, 3600, 36000,
        NULL, '1', 0, NULL, '', NULL, NULL, '', NULL);
INSERT INTO `sys_oauth_client_details`
VALUES ('my_test_demo', NULL, 'my_test_demo', 'my_test_demo', 'demo', 'password,refresh_token', NULL, NULL, 3600, 36000,
        NULL, '1', 0, NULL, '', NULL, NULL, '', NULL);
INSERT INTO `sys_oauth_client_details`
VALUES ('swagger', NULL, 'swagger', 'swagger', 'swagger', 'password', NULL, NULL, 3600, 36000, NULL, '1', 0, NULL, '',
        NULL, NULL, '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_base
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_base`;
CREATE TABLE `sys_user_base`
(
    `id`               bigint unsigned                                               NOT NULL COMMENT '用户Id',
    `user_name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL COMMENT '用户名',
    `password`         varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '登录密码',
    `mobile`           varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci            DEFAULT NULL COMMENT '移动电话号码',
    `email`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci           DEFAULT NULL COMMENT '邮箱',
    `id_card`          char(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci               DEFAULT '' COMMENT '身份证号码',
    `is_locked`        int(1) unsigned zerofill                                      NOT NULL DEFAULT '0' COMMENT '是否锁定  1 是 0 否',
    `is_enabled`       int(1) unsigned zerofill                                      NOT NULL DEFAULT '0' COMMENT '是否启用  1 是 0 否 ',
    `last_login_time`  datetime                                                               DEFAULT NULL COMMENT '最后登录时间',
    `is_deleted`       int(1) unsigned zerofill                                      NOT NULL DEFAULT '0' COMMENT '是否删除 1 是 0 否 ',
    `create_user_id`   bigint unsigned                                                        DEFAULT NULL COMMENT '创建用户Id',
    `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci            DEFAULT '' COMMENT '创建用户名称',
    `create_time`      datetime                                                               DEFAULT NULL COMMENT '创建时间',
    `update_user_id`   bigint unsigned                                                        DEFAULT NULL COMMENT '更新用户Id',
    `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci            DEFAULT '' COMMENT '更新用户名称',
    `update_time`      datetime                                                               DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='登录信息表';

-- ----------------------------
-- Records of sys_user_base
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_base`
VALUES (1, 'me', '$2a$10$M3jJ13yamQkrxLPC2zC/lOtVruBkVxMQ/MJKiiRRb8wrsAvxx5AYW', '16601770650', '247474571@qq.com',
        '111111111111111111', 0, 0, '2020-08-13 15:26:46', 0, 1, 'my123', '2020-08-12 16:47:57', NULL, '', NULL);
INSERT INTO `sys_user_base`
VALUES (2, 'myuser', '$2a$10$M3jJ13yamQkrxLPC2zC/lOtVruBkVxMQ/MJKiiRRb8wrsAvxx5AYW', '16601770650', '247474571@qq.com',
        '111111111111111111', 0, 0, '2020-08-14 17:14:27', 0, 0, 'string', '2020-08-14 17:14:27', 0, 'string',
        '2020-08-14 17:14:27');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_client
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_client`;
CREATE TABLE `sys_user_client`
(
    `id`               bigint unsigned                        NOT NULL COMMENT '主键',
    `client_id`        varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端appid',
    `user_id`          bigint                                 NOT NULL COMMENT '用户id',
    `note`             varchar(255) COLLATE utf8mb4_general_ci                      DEFAULT NULL COMMENT '备注',
    `is_deleted`       tinyint(1) unsigned zerofill           NOT NULL              DEFAULT '0' COMMENT '是否删除 0-未删除，1-已删除',
    `create_user_id`   bigint unsigned                                              DEFAULT NULL COMMENT '创建用户Id',
    `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT '' COMMENT '创建用户名称',
    `create_time`      datetime                                                     DEFAULT NULL COMMENT '创建时间',
    `update_user_id`   bigint unsigned                                              DEFAULT NULL COMMENT '更新用户Id',
    `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT '' COMMENT '更新用户名称',
    `update_time`      datetime                                                     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='客户端用户信息表';

-- ----------------------------
-- Records of sys_user_client
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_client`
VALUES (1, 'my_test_demo', 1, NULL, 0, NULL, '', NULL, NULL, '', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
