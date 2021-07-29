package com.melo.left;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.management.ManagementFactory;

@EnableSwagger2
@RestController
@ImportResource("classpath:dubbo/dubbo-provider.xml")
@SpringBootApplication
public class MeloApplacation {

    public static void main(String[] args) {
        SpringApplication.run(MeloApplacation.class, args);
        String ipAndHostName = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println("pid is " + ipAndHostName);
    }

    @GetMapping("melo")
    public String hello() {
        return "melo";
    }


    @RequestMapping(value = "/test0")
    public String test0() {
        ThreadLocal<Byte[]> localVariable = new ThreadLocal<Byte[]>();
        // 为线程添加变量
        localVariable.set(new Byte[4096*1024]);
        return "success";
    }
}
