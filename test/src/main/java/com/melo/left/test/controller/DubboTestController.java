package com.melo.left.test.controller;

import com.melo.left.test.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("dubbo")
public class DubboTestController {

    @Autowired
    private TestService testService;


}
