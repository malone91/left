DROP TABLE IF EXISTS `application_interface`;
CREATE TABLE `application_interface` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `system_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '系统标识',
  `interface_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '接口标识',
  `interface_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '接口名称',
  `interface_version` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '接口版本',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx` (`system_id`,`interface_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of application_interface
-- ----------------------------
BEGIN;
INSERT INTO `application_interface` VALUES (1, 'api-gateway-test', 'cn.bugstack.gateway.rpc.IActivityBooth', 'sayHi', 'v1.0.0', '2022-11-13 13:13:00', '2022-11-13 13:13:00');
COMMIT;

-- ----------------------------
-- Table structure for application_interface_method
-- ----------------------------
DROP TABLE IF EXISTS `application_interface_method`;
CREATE TABLE `application_interface_method` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `system_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '系统标识',
  `interface_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '接口标识',
  `method_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '方法标识',
  `method_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '方法名称',
  `parameter_type` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '参数类型；(RPC 限定单参数注册)；new String[]{"java.lang.String"}、new String[]{"cn.bugstack.gateway.rpc.dto.XReq"}',
  `uri` varchar(126) COLLATE utf8_bin DEFAULT NULL COMMENT '网关接口',
  `http_command_type` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '接口类型；GET、POST、PUT、DELETE',
  `auth` int(4) DEFAULT NULL COMMENT 'true = 1是、false = 0否',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx` (`system_id`,`interface_id`,`method_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of application_interface_method
-- ----------------------------
BEGIN;
INSERT INTO `application_interface_method` VALUES (1, 'api-gateway-test', 'cn.bugstack.gateway.rpc.IActivityBooth', 'sayHi', '测试方法', 'java.lang.String', '/wg/activity/sayHi', 'GET', 0, '2022-11-13 13:16:52', '2022-11-13 13:16:52');
INSERT INTO `application_interface_method` VALUES (2, 'api-gateway-test', 'cn.bugstack.gateway.rpc.IActivityBooth', 'insert', '插入方法', 'cn.bugstack.gateway.rpc.dto.XReq', '/wg/activity/insert', 'POST', 1, '2022-11-13 13:16:52', '2022-11-13 13:16:52');
COMMIT;

-- ----------------------------
-- Table structure for application_system
-- ----------------------------
DROP TABLE IF EXISTS `application_system`;
CREATE TABLE `application_system` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `system_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '系统标识',
  `system_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '系统名称',
  `system_type` varchar(4) COLLATE utf8_bin DEFAULT NULL COMMENT '系统类型；RPC、HTTP',
  `system_registry` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '注册中心',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_systemId` (`system_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of application_system
-- ----------------------------
BEGIN;
INSERT INTO `application_system` VALUES (1, 'lottery-api', '抽奖API系统', 'RPC', '127.0.0.1', '2022-11-13 13:10:03', '2022-11-13 13:10:03');
INSERT INTO `application_system` VALUES (3, 'api-gateway-test', '网关测试系统', 'RPC', '127.0.0.1', '2022-11-13 13:12:54', '2022-11-13 13:12:54');
COMMIT;