package com.melo.gatewaycenter.domain.manage.service;

import com.melo.gatewaycenter.application.IConfigManageService;
import com.melo.gatewaycenter.domain.manage.model.vo.GatewayServerDetailVO;
import com.melo.gatewaycenter.domain.manage.model.vo.GatewayServerVO;
import com.melo.gatewaycenter.domain.manage.repository.IConfigManageRepository;
import com.melo.gatewaycenter.infrastructure.common.Constants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConfigManageServiceImpl implements IConfigManageService {

    @Resource
    private IConfigManageRepository configManageRepository;

    @Override
    public List<GatewayServerVO> listGatewayServer() {
        return configManageRepository.listGatewayServer();
    }

    @Override
    public boolean registerGatewayServerNode(String groupId, String gatewayId, String gatewayName, String gatewayAddress) {
        GatewayServerDetailVO gatewayServer = configManageRepository.detailGatewayServer(gatewayId, gatewayAddress);
        if (gatewayServer == null) {
            try {
                return configManageRepository.registerGatewayServerNode(groupId, gatewayId, gatewayName, gatewayAddress, Constants.GatewayStatus.available);
            } catch (Exception e) {
                return configManageRepository.updateGatewayStatus(gatewayId, gatewayAddress, Constants.GatewayStatus.available);
            }
        } else {
            return configManageRepository.updateGatewayStatus(gatewayId, gatewayAddress, Constants.GatewayStatus.available);
        }
    }
}
