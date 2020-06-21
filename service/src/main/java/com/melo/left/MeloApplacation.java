package com.melo.left;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@ImportResource("classpath:dubbo/dubbo-provider.xml")
@SpringBootApplication
public class MeloApplacation {

    public static void main(String[] args) {
        //SpringApplication.run(MeloApplacation.class, args);
    }

    @GetMapping("melo")
    public String hello() {
        return "melo";
    }
}