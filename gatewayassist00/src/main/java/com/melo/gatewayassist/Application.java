package com.melo.gatewayassist;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@Configurable
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Application {

    /**
     INSERT INTO `api-gateway`.`gateway_server_detail` (`id`, `group_id`, `gateway_id`, `gateway_name`, `gateway_address`, `status`, `create_time`, `update_time`) VALUES ('17', '11', 'api-gateway-test', '支付网关', '127.0.0.1:7399', '1', '2023-07-28 18:21:39', '2023-07-28 18:21:39');
     INSERT INTO `api-gateway`.`gateway_distribution` (`id`, `group_id`, `gateway_id`, `system_id`, `system_name`, `create_time`, `update_time`) VALUES ('2', '11', 'api-gateway-test', 'api-gateway-test', '订单', '2023-07-28 16:53:41', '2023-07-28 16:53:44');
     INSERT INTO `api-gateway`.`application_system` (`id`, `system_id`, `system_name`, `system_type`, `system_registry`, `create_time`, `update_time`) VALUES ('4', 'api-gateway-test', '网关测试系统', 'RPC', 'zookeeper://127.0.0.1:2181', '2023-07-26 15:10:49', '2023-07-26 15:10:49');
     INSERT INTO `api-gateway`.`application_interface_method` (`id`, `system_id`, `interface_id`, `method_id`, `method_name`, `parameter_type`, `uri`, `http_command_type`, `auth`, `create_time`, `update_time`) VALUES ('5', 'api-gateway-test', 'com.melo.rpc.HelloService', 'add', '插入方法', 'com.melo.dto.XReq', '/melo/add', 'POST', '1', '2023-07-26 15:12:28', '2023-07-26 15:12:28');
     INSERT INTO `api-gateway`.`application_interface` (`id`, `system_id`, `interface_id`, `interface_name`, `interface_version`, `create_time`, `update_time`) VALUES ('4', 'api-gateway-test', 'com.melo.rpc.HelloService', '活动平台', '1.0.0', '2023-07-26 15:12:07', '2023-07-26 15:12:07');

     curl --location --request POST 'http://localhost:7399/melo/add' \
     --header 'uId: melo' \
     --header 'token: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtZWxvIiwiZXhwIjoxNjkxMDY1MTg5LCJpYXQiOjE2OTA0NjAzODksImtleSI6InBlbGEifQ.QIRguI7Ecj7kO-TQI5QKzvAtpowsjh6ZPkbgt41tBj4' \
     --header 'x-debug: true' \
     --header 'Content-Type: application/json' \
     --data-raw '{
     "uid": "999",
     "name": "tt"
     }'
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
