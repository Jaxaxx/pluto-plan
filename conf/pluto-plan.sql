/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.0.110
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 192.168.0.110:3306
 Source Schema         : pluto-plan

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 17/11/2020 21:33:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_auth_client
-- ----------------------------
DROP TABLE IF EXISTS `sys_auth_client`;
CREATE TABLE `sys_auth_client` (
  `client_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端ID',
  `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '资源ID集合,多个资源时用逗号(,)分隔',
  `client_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端名称',
  `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端密匙',
  `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端申请的权限范围',
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '客户端支持的grant_type',
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '重定向URI',
  `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔',
  `access_token_validity` int NOT NULL DEFAULT '7200' COMMENT '访问令牌有效时间值(单位:秒)',
  `refresh_token_validity` int NOT NULL DEFAULT '36000' COMMENT '更新令牌有效时间值(单位:秒)',
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '预留字段',
  `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户是否自动Approval操作',
  `is_deleted` tinyint(1) unsigned zerofill NOT NULL DEFAULT '0' COMMENT '是否删除 1-是 0 否',
  `create_user_id` bigint unsigned DEFAULT NULL COMMENT '创建用户Id',
  `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建用户名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint unsigned DEFAULT NULL COMMENT '更新用户Id',
  `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新用户名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='客户端信息表';

-- ----------------------------
-- Records of sys_auth_client
-- ----------------------------
BEGIN;
INSERT INTO `sys_auth_client` VALUES ('admin', NULL, '管理员', 'admin', 'all', 'authorization_code,refresh_token,password,sms', NULL, NULL, 3600, 36000, NULL, '1', 0, NULL, '', NULL, NULL, '', NULL);
INSERT INTO `sys_auth_client` VALUES ('my_test_demo', NULL, 'my_test_demo', 'my_test_demo', 'demo', 'authorization_code,refresh_token,password,sms,client_credentials', NULL, NULL, 3600, 36000, NULL, '1', 0, NULL, '', NULL, NULL, '', NULL);
INSERT INTO `sys_auth_client` VALUES ('swagger', NULL, 'swagger', 'swagger', 'swagger', 'password', NULL, NULL, 3600, 36000, NULL, '1', 0, NULL, '', NULL, NULL, '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint unsigned NOT NULL COMMENT 'id',
  `type` int DEFAULT '1' COMMENT '日志类型',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '日志标题',
  `service_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '服务id',
  `remote_addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作IP地址',
  `user_agent` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户代理',
  `request_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求URI',
  `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作方式',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '操作提交的数据',
  `time` bigint DEFAULT NULL COMMENT '执行时间',
  `exception` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '异常信息',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) unsigned zerofill NOT NULL DEFAULT '0' COMMENT '是否删除：0-否，1-是',
  `create_user_id` bigint unsigned DEFAULT NULL COMMENT '创建用户id',
  `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建用户名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint unsigned DEFAULT NULL COMMENT '更新用户id',
  `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新用户名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sys_log_request_uri` (`request_uri`) USING BTREE,
  KEY `sys_log_type` (`type`) USING BTREE,
  KEY `idx_sys_log_title` (`title`) USING BTREE,
  KEY `create_user_id` (`create_user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='日志表';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
BEGIN;
INSERT INTO `sys_log` VALUES (1328548476895014913, 0, '新增用户', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '', 161, NULL, NULL, 0, 1, 'me', '2020-11-17 12:00:15', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328549823430176770, 0, '新增用户', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '', 72, NULL, NULL, 0, 1, 'me', '2020-11-17 12:05:36', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328571066221035522, 9, '新增用户', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test2\"}', 77, '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test2\' for key \'sys_user_base.idx_user_name\'\r\n### The error may exist in com/mine/upmsx/mapper/SysUserBaseMapper.java (best guess)\r\n### The error may involve com.mine.upmsx.mapper.SysUserBaseMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_user_base  ( id, user_name, password, mobile, email,     is_deleted, create_user_id, create_user_name, create_time )  VALUES  ( ?, ?, ?, ?, ?,     ?, ?, ?, ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test2\' for key \'sys_user_base.idx_user_name\'\n; Duplicate entry \'test2\' for key \'sys_user_base.idx_user_name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test2\' for key \'sys_user_base.idx_user_name\'', NULL, 0, 1, 'me', '2020-11-17 13:30:01', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328574722345472002, 0, '新增用户', '/pluto-upmsx', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test3\"}', 96, NULL, NULL, 0, 1, 'me', '2020-11-17 13:44:32', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328575181512708098, 9, '新增用户', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test3\"}', 115, '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\r\n### The error may exist in com/mine/upmsx/mapper/SysUserBaseMapper.java (best guess)\r\n### The error may involve com.mine.upmsx.mapper.SysUserBaseMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_user_base  ( id, user_name, password, mobile, email,     is_deleted, create_user_id, create_user_name, create_time )  VALUES  ( ?, ?, ?, ?, ?,     ?, ?, ?, ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\n; Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'', NULL, 0, 1, 'me', '2020-11-17 13:46:22', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328575185652486146, 9, '新增用户', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test3\"}', 70, '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\r\n### The error may exist in com/mine/upmsx/mapper/SysUserBaseMapper.java (best guess)\r\n### The error may involve com.mine.upmsx.mapper.SysUserBaseMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_user_base  ( id, user_name, password, mobile, email,     is_deleted, create_user_id, create_user_name, create_time )  VALUES  ( ?, ?, ?, ?, ?,     ?, ?, ?, ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\n; Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'', NULL, 0, 1, 'me', '2020-11-17 13:46:23', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328575188190040065, 9, '新增用户', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test3\"}', 71, '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\r\n### The error may exist in com/mine/upmsx/mapper/SysUserBaseMapper.java (best guess)\r\n### The error may involve com.mine.upmsx.mapper.SysUserBaseMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_user_base  ( id, user_name, password, mobile, email,     is_deleted, create_user_id, create_user_name, create_time )  VALUES  ( ?, ?, ?, ?, ?,     ?, ?, ?, ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\n; Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'', NULL, 0, 1, 'me', '2020-11-17 13:46:23', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328575190735982594, 9, '新增用户', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test3\"}', 71, '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\r\n### The error may exist in com/mine/upmsx/mapper/SysUserBaseMapper.java (best guess)\r\n### The error may involve com.mine.upmsx.mapper.SysUserBaseMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_user_base  ( id, user_name, password, mobile, email,     is_deleted, create_user_id, create_user_name, create_time )  VALUES  ( ?, ?, ?, ?, ?,     ?, ?, ?, ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\n; Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'', NULL, 0, 1, 'me', '2020-11-17 13:46:24', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328575193349033985, 9, '新增用户', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test3\"}', 71, '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\r\n### The error may exist in com/mine/upmsx/mapper/SysUserBaseMapper.java (best guess)\r\n### The error may involve com.mine.upmsx.mapper.SysUserBaseMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_user_base  ( id, user_name, password, mobile, email,     is_deleted, create_user_id, create_user_name, create_time )  VALUES  ( ?, ?, ?, ?, ?,     ?, ?, ?, ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\n; Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'', NULL, 0, 1, 'me', '2020-11-17 13:46:24', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328591576510160897, 0, 'password登录', '/auth', '127.0.0.1', 'PostmanRuntime/7.26.8', '/oauth/token', 'POST', '', NULL, NULL, 'password', 0, NULL, 'N/A', '2020-11-17 14:51:31', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328592232847433729, 9, '新增用户', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test3\"}', 127, '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\r\n### The error may exist in com/mine/upmsx/mapper/SysUserBaseMapper.java (best guess)\r\n### The error may involve com.mine.upmsx.mapper.SysUserBaseMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_user_base  ( id, user_name, password, mobile, email,     is_deleted, create_user_id, create_user_name, create_time )  VALUES  ( ?, ?, ?, ?, ?,     ?, ?, ?, ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\n; Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'', NULL, 0, NULL, 'N/A', '2020-11-17 14:54:07', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328593349601828866, 9, '新增用户', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test3\"}', 25265, '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\r\n### The error may exist in com/mine/upmsx/mapper/SysUserBaseMapper.java (best guess)\r\n### The error may involve com.mine.upmsx.mapper.SysUserBaseMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_user_base  ( id, user_name, password, mobile, email,     is_deleted, create_user_id, create_user_name, create_time )  VALUES  ( ?, ?, ?, ?, ?,     ?, ?, ?, ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\n; Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'', NULL, 0, 1, 'me', '2020-11-17 14:58:33', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328593506540101634, 0, '登录', '/auth', '127.0.0.1', 'PostmanRuntime/7.26.8', '/oauth/token', 'POST', '', NULL, NULL, 'password', 0, 1, 'me', '2020-11-17 14:59:11', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328600901588787201, 0, '登录', '/auth', '127.0.0.1', 'PostmanRuntime/7.26.8', '/oauth/token', 'POST', '', NULL, NULL, 'password', 0, 1, 'me', '2020-11-17 15:28:34', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328609850937483266, 0, '登录', '/auth', '127.0.0.1', 'PostmanRuntime/7.26.8', '/oauth/token', 'POST', '', NULL, NULL, 'password', 0, 1, 'me', '2020-11-17 16:04:07', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328610999543439362, 0, '登录', '/auth', '127.0.0.1', 'PostmanRuntime/7.26.8', '/oauth/token', 'POST', '', NULL, NULL, 'password', 0, 1, 'me', '2020-11-17 16:08:41', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328612932324859905, 0, '登录', '/auth', '127.0.0.1', 'PostmanRuntime/7.26.8', '/oauth/token', 'POST', '', NULL, NULL, 'password', 0, 1, 'me', '2020-11-17 16:16:22', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328628030812708865, 9, '用户注册', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test3\"}', 253, '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\r\n### The error may exist in com/mine/upmsx/mapper/SysUserBaseMapper.java (best guess)\r\n### The error may involve com.mine.upmsx.mapper.SysUserBaseMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_user_base  ( id, user_name, password, mobile, email,     is_deleted, create_user_id, create_user_name, create_time )  VALUES  ( ?, ?, ?, ?, ?,     ?, ?, ?, ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\n; Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'', NULL, 0, NULL, 'N/A', '2020-11-17 17:16:22', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328628431922388993, 9, '用户注册', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test3\"}', 79, '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\r\n### The error may exist in com/mine/upmsx/mapper/SysUserBaseMapper.java (best guess)\r\n### The error may involve com.mine.upmsx.mapper.SysUserBaseMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_user_base  ( id, user_name, password, mobile, email,     is_deleted, create_user_id, create_user_name, create_time )  VALUES  ( ?, ?, ?, ?, ?,     ?, ?, ?, ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\n; Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'', NULL, 0, NULL, 'N/A', '2020-11-17 17:17:58', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328629981696745473, 9, '用户注册', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test3\"}', 347123, '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\r\n### The error may exist in com/mine/upmsx/mapper/SysUserBaseMapper.java (best guess)\r\n### The error may involve com.mine.upmsx.mapper.SysUserBaseMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_user_base  ( id, user_name, password, mobile, email,     is_deleted, create_user_id, create_user_name, create_time )  VALUES  ( ?, ?, ?, ?, ?,     ?, ?, ?, ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\n; Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'', NULL, 0, NULL, 'N/A', '2020-11-17 17:24:07', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328636546134745090, 9, '用户注册', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test3\"}', 245, '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\r\n### The error may exist in com/mine/upmsx/mapper/SysUserBaseMapper.java (best guess)\r\n### The error may involve com.mine.upmsx.mapper.SysUserBaseMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_user_base  ( id, user_name, password, mobile, email,     is_deleted, create_user_id, create_user_name, create_time )  VALUES  ( ?, ?, ?, ?, ?,     ?, ?, ?, ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\n; Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'', NULL, 0, NULL, 'N/A', '2020-11-17 17:50:12', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328636694474694657, 9, '用户注册', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test3\"}', 7798, '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\r\n### The error may exist in com/mine/upmsx/mapper/SysUserBaseMapper.java (best guess)\r\n### The error may involve com.mine.upmsx.mapper.SysUserBaseMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_user_base  ( id, user_name, password, mobile, email,     is_deleted, create_user_id, create_user_name, create_time )  VALUES  ( ?, ?, ?, ?, ?,     ?, ?, ?, ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\n; Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'', NULL, 0, NULL, 'N/A', '2020-11-17 17:50:47', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328636797063176194, 9, '用户注册', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test3\"}', 22859, '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\r\n### The error may exist in com/mine/upmsx/mapper/SysUserBaseMapper.java (best guess)\r\n### The error may involve com.mine.upmsx.mapper.SysUserBaseMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_user_base  ( id, user_name, password, mobile, email,     is_deleted, create_user_id, create_user_name, create_time )  VALUES  ( ?, ?, ?, ?, ?,     ?, ?, ?, ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\n; Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'', NULL, 0, NULL, 'N/A', '2020-11-17 17:51:12', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328637486111911937, 9, '用户注册', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test3\"}', 41306, '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\r\n### The error may exist in com/mine/upmsx/mapper/SysUserBaseMapper.java (best guess)\r\n### The error may involve com.mine.upmsx.mapper.SysUserBaseMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_user_base  ( id, user_name, password, mobile, email,     is_deleted, create_user_id, create_user_name, create_time )  VALUES  ( ?, ?, ?, ?, ?,     ?, ?, ?, ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\n; Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'', NULL, 0, NULL, 'N/A', '2020-11-17 17:53:56', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328637759035273217, 9, '用户注册', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test3\"}', 2956, '\r\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\r\n### The error may exist in com/mine/upmsx/mapper/SysUserBaseMapper.java (best guess)\r\n### The error may involve com.mine.upmsx.mapper.SysUserBaseMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_user_base  ( id, user_name, password, mobile, email,     is_deleted, create_user_id, create_user_name, create_time )  VALUES  ( ?, ?, ?, ?, ?,     ?, ?, ?, ? )\r\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'\n; Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test3\' for key \'sys_user_base.idx_user_name\'', NULL, 0, NULL, 'N/A', '2020-11-17 17:55:01', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328639083063709698, 9, '用户注册', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test32\"}', 36040, '\r\n### Error updating database.  Cause: java.sql.SQLException: Field \'client_id\' doesn\'t have a default value\r\n### The error may exist in com/mine/upmsx/mapper/SysUserClientMapper.java (best guess)\r\n### The error may involve com.mine.upmsx.mapper.SysUserClientMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_user_client  ( id,  user_id,   create_user_id, create_user_name, create_time )  VALUES  ( ?,  ?,   ?, ?, ? )\r\n### Cause: java.sql.SQLException: Field \'client_id\' doesn\'t have a default value\n; Field \'client_id\' doesn\'t have a default value; nested exception is java.sql.SQLException: Field \'client_id\' doesn\'t have a default value', NULL, 0, NULL, 'N/A', '2020-11-17 18:00:17', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328639249766322177, 9, '用户注册', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test32\"}', 31589, '\r\n### Error updating database.  Cause: java.sql.SQLException: Field \'client_id\' doesn\'t have a default value\r\n### The error may exist in com/mine/upmsx/mapper/SysUserClientMapper.java (best guess)\r\n### The error may involve com.mine.upmsx.mapper.SysUserClientMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_user_client  ( id,  user_id,   create_user_id, create_user_name, create_time )  VALUES  ( ?,  ?,   ?, ?, ? )\r\n### Cause: java.sql.SQLException: Field \'client_id\' doesn\'t have a default value\n; Field \'client_id\' doesn\'t have a default value; nested exception is java.sql.SQLException: Field \'client_id\' doesn\'t have a default value', NULL, 0, NULL, 'N/A', '2020-11-17 18:00:57', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328639516909932546, 9, '用户注册', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test32\"}', 60914, '\r\n### Error updating database.  Cause: java.sql.SQLException: Field \'client_id\' doesn\'t have a default value\r\n### The error may exist in com/mine/upmsx/mapper/SysUserClientMapper.java (best guess)\r\n### The error may involve com.mine.upmsx.mapper.SysUserClientMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO sys_user_client  ( id,  user_id,   create_user_id, create_user_name, create_time )  VALUES  ( ?,  ?,   ?, ?, ? )\r\n### Cause: java.sql.SQLException: Field \'client_id\' doesn\'t have a default value\n; Field \'client_id\' doesn\'t have a default value; nested exception is java.sql.SQLException: Field \'client_id\' doesn\'t have a default value', NULL, 0, NULL, 'N/A', '2020-11-17 18:02:00', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328639819814178817, 9, '用户注册', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test32\"}', 2, 'Failed to decode basic authentication token', NULL, 0, NULL, 'N/A', '2020-11-17 18:03:13', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328639864626122753, 0, '用户注册', '/pluto-upmsx', '127.0.0.1', 'PostmanRuntime/7.26.8', '/sysUserBase', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test32\"}', 5671, NULL, NULL, 0, NULL, 'N/A', '2020-11-17 18:03:23', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328671169454006273, 0, '登录', '/auth', '192.168.0.106', 'PostmanRuntime/7.26.5', '/oauth/token', 'POST', '', NULL, NULL, 'password', 0, 1, 'me', '2020-11-17 20:07:47', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328687921304723457, 0, '登录', '/auth', '192.168.0.106', 'PostmanRuntime/7.26.5', '/oauth/token', 'POST', '', NULL, NULL, 'password', 0, 1, 'me', '2020-11-17 21:14:21', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328688069778890753, 0, '登录', '/auth', '192.168.0.106', 'PostmanRuntime/7.26.5', '/oauth/token', 'POST', '', NULL, NULL, 'password', 0, 1, 'me', '2020-11-17 21:14:56', NULL, '', NULL);
INSERT INTO `sys_log` VALUES (1328691540401848322, 9, '用户注册', '', '127.0.0.1', 'Apache-HttpClient/4.5.12 (Java/11.0.8)', '/user/base/sign', 'POST', '{  \"email\": \"test2@test.com\",  \"mobile\": \"18801234567\",  \"password\": \"123456\",  \"userName\": \"test32\"}', 13805, '\n### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test32\' for key \'sys_user_base.idx_user_name\'\n### The error may exist in com/mine/upmsx/mapper/SysUserBaseMapper.java (best guess)\n### The error may involve com.mine.upmsx.mapper.SysUserBaseMapper.insert-Inline\n### The error occurred while setting parameters\n### SQL: INSERT INTO sys_user_base  ( id, user_name, password, mobile, email,     is_deleted, create_user_id, create_user_name, create_time )  VALUES  ( ?, ?, ?, ?, ?,     ?, ?, ?, ? )\n### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test32\' for key \'sys_user_base.idx_user_name\'\n; Duplicate entry \'test32\' for key \'sys_user_base.idx_user_name\'; nested exception is java.sql.SQLIntegrityConstraintViolationException: Duplicate entry \'test32\' for key \'sys_user_base.idx_user_name\'', NULL, 0, NULL, 'N/A', '2020-11-17 21:28:44', NULL, '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_base
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_base`;
CREATE TABLE `sys_user_base` (
  `id` bigint unsigned NOT NULL COMMENT '用户Id',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录密码',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '移动电话号码',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `id_card` char(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '身份证号码',
  `is_locked` tinyint(1) unsigned zerofill NOT NULL DEFAULT '0' COMMENT '是否锁定  1 是 0 否',
  `is_enabled` tinyint(1) unsigned zerofill NOT NULL DEFAULT '1' COMMENT '是否启用  1 是 0 否 ',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `is_deleted` tinyint(1) unsigned zerofill NOT NULL DEFAULT '0' COMMENT '是否删除 1 是 0 否 ',
  `create_user_id` bigint unsigned DEFAULT NULL COMMENT '创建用户Id',
  `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建用户名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint unsigned DEFAULT NULL COMMENT '更新用户Id',
  `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新用户名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_user_name` (`user_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='登录信息表';

-- ----------------------------
-- Records of sys_user_base
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_base` VALUES (1, 'me', '$2a$10$M3jJ13yamQkrxLPC2zC/lOtVruBkVxMQ/MJKiiRRb8wrsAvxx5AYW', '16601770650', '247474571@qq.com', '111111111111111111', 0, 1, '2020-11-17 21:14:00', 0, 1, 'my123', '2020-08-12 16:47:57', NULL, '', NULL);
INSERT INTO `sys_user_base` VALUES (1328546298880999425, 'swagger', '$2a$10$43GrfVkCwrVWT56SmWKha.CDJcapo5ooD1q4D61OwsNx/fkiWO4lm', '13312345678', 'swagger@swagger.com', '', 0, 1, NULL, 0, 1, 'me', '2020-11-17 11:51:36', NULL, '', NULL);
INSERT INTO `sys_user_base` VALUES (1328639841175769090, 'test32', '$2a$10$JVUS.DMzKZUqkJVTSDbzKuLSv7HGsjyWWJ.DqMqY6ANlHPiWPecW2', '18801234567', 'test2@test.com', '', 0, 1, NULL, 0, NULL, 'N/A', '2020-11-17 18:03:18', NULL, '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_client
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_client`;
CREATE TABLE `sys_user_client` (
  `id` bigint unsigned NOT NULL COMMENT '主键',
  `client_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端appid',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) unsigned zerofill NOT NULL DEFAULT '0' COMMENT '是否删除 0-未删除，1-已删除',
  `create_user_id` bigint unsigned DEFAULT NULL COMMENT '创建用户Id',
  `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建用户名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint unsigned DEFAULT NULL COMMENT '更新用户Id',
  `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新用户名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='客户端用户信息表';

-- ----------------------------
-- Records of sys_user_client
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_client` VALUES (1, 'my_test_demo', 1, NULL, 0, NULL, '', NULL, NULL, '', NULL);
INSERT INTO `sys_user_client` VALUES (2, 'swagger', 1, NULL, 0, NULL, '', NULL, NULL, '', NULL);
INSERT INTO `sys_user_client` VALUES (1328639864571596801, 'my_test_demo', 1328639841175769090, NULL, 0, NULL, 'N/A', '2020-11-17 18:03:23', NULL, '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_info`;
CREATE TABLE `sys_user_info` (
  `id` bigint unsigned NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `id_card` char(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '身份证号码',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '真实姓名',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '昵称',
  `gender` int(10) unsigned zerofill NOT NULL DEFAULT '0000000000' COMMENT '性别：1-男 2-女 0-未知',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `wechat` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '微信号',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像图片 uri',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '移动电话号码',
  `province` int DEFAULT NULL COMMENT '地址：省',
  `city` int DEFAULT NULL COMMENT '地址：市',
  `district` int DEFAULT NULL COMMENT '地址：区',
  `address` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '地址：具体地址',
  `note` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_user_id` bigint unsigned DEFAULT NULL COMMENT '创建人id',
  `create_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建用户名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint unsigned DEFAULT NULL COMMENT '更新人id',
  `update_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '更新用户名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户信息表';

-- ----------------------------
-- Records of sys_user_info
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
