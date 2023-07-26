package com.melo.gatewaycenter.domain.manage.repository;

import com.melo.gatewaycenter.domain.manage.model.vo.GatewayServerDetailVO;
import com.melo.gatewaycenter.domain.manage.model.vo.GatewayServerVO;

import java.util.List;

public interface IConfigManageRepository {

    List<GatewayServerVO> listGatewayServer();

    boolean registerGatewayServerNode(String groupId, String gatewayId, String gatewayName, String gatewayAddress, Integer available);

    GatewayServerDetailVO detailGatewayServer(String gatewayId, String gatewayAddress);

    boolean updateGatewayStatus(String gatewayId, String gatewayAddress, Integer available);
}
