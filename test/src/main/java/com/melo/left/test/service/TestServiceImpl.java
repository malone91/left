package com.melo.left.test.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gome.scot.dcs.api.bangke.model.BangkeProjectInfoPO;
import com.gome.scot.dcs.api.bangke.model.HandleBangkeProjectVO;
import com.gome.scot.dcs.api.bangke.service.BangkeProjectInfoService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Reference
    private BangkeProjectInfoService bangkeProjectInfoService;

    @Override
    public HandleBangkeProjectVO test() {
        BangkeProjectInfoPO po = new BangkeProjectInfoPO();
        return bangkeProjectInfoService.distribute(po);
    }
}
