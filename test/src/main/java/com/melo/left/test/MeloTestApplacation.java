package com.melo.left.test;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@EnableDubbo
@SpringBootApplication
public class MeloTestApplacation {

    public static void main(String[] args) {
        SpringApplication.run(MeloTestApplacation.class, args);
    }

    @GetMapping("melo")
    public String hello() {
        return "melo";
    }
}
