package com.melo.left.test.controller;

import com.melo.api.QueryService;
import com.melo.api.model.ProgramLanguage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("dubbo")
public class DubboTestController {

    @Autowired
    private QueryService queryService;

    @GetMapping("get")
    public ProgramLanguage get() {
        return queryService.select();
    }
}
