package com.melo.gateway.center.infrastructure.mapper;

import com.melo.gateway.center.infrastructure.po.HttpStatement;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 CREATE TABLE `http_statement` (
 `id` bigint NOT NULL AUTO_INCREMENT,
 `application` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '应用名称',
 `interface_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '服务接口；RPC、其他',
 `method_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT ' 服务方法；RPC#method',
 `parameter_type` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '参数类型(RPC 限定单参数注册)；new String[]{"java.lang.String"}、new String[]{"cn.bugstack.gateway.rpc.dto.XReq"}',
 `uri` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '网关接口',
 `http_command_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '接口类型；GET、POST、PUT、DELETE',
 `auth` int NOT NULL DEFAULT '0' COMMENT 'true = 1是、false = 0否',
 `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
 `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

 INSERT INTO `http_statement` (application,interface_name,method_name,parameter_type,uri,http_command_type,auth,create_time,update_time)
 VALUES
 (
 'api-gateway-test',
 'com.melo.rpc.HelloService',
 'hello',
 'java.lang.String',
 '/melo/hello',
 'GET',
 0,
 NOW(),
 NOW()
 );

 INSERT INTO `http_statement` (application,interface_name,method_name,parameter_type,uri,http_command_type,auth,create_time,update_time)
 VALUES
 (
 'api-gateway-test',
 'com.melo.rpc.HelloService',
 'add',
 'com.melo.dto.XReq',
 '/melo/add',
 'POST',
 1,
 NOW(),
 NOW()
 );
 */
@Mapper
public interface HttpStatementMapper {

    List<HttpStatement> list();
}
