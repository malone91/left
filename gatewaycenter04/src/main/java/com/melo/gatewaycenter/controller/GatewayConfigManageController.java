package com.melo.gatewaycenter.controller;

import com.melo.gatewaycenter.application.IConfigManageService;
import com.melo.gatewaycenter.domain.manage.model.vo.GatewayServerVO;
import com.melo.gatewaycenter.infrastructure.common.ResponseCode;
import com.melo.gatewaycenter.infrastructure.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/melo/admin/cofig")
public class GatewayConfigManageController {

    @Resource
    private IConfigManageService configManageService;

    @GetMapping(value = "listConfig", produces = "application/json;charset=utf-8")
    public Result<List<GatewayServerVO>> list() {
        try {
            List<GatewayServerVO> gatewayServerVOS = configManageService.listGatewayServer();
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), gatewayServerVOS);
        } catch (Exception e) {
            return new Result<>(ResponseCode.FAIL.getCode(), e.getMessage(), null);
        }
    }

    @PostMapping(value = "registerGateway")
    public Result<Boolean> registerGateway(@RequestParam String groupId, @RequestParam String gatewayId, @RequestParam String gatewayName,
                                           @RequestParam String gatewayAddress) {
        try {
            boolean done = configManageService.registerGatewayServerNode(groupId, gatewayId, gatewayName, gatewayAddress);
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), done);
        } catch (Exception e) {
            return new Result<>(ResponseCode.FAIL.getCode(), e.getMessage(), null);
        }
    }

}
