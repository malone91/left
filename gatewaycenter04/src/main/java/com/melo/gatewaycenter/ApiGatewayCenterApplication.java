package com.melo.gatewaycenter;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Configurable
@SpringBootApplication
public class ApiGatewayCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayCenterApplication.class, args);
    }
}
