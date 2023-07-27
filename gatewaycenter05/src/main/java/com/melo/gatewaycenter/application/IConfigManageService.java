package com.melo.gatewaycenter.application;

import com.melo.gatewaycenter.domain.manage.model.aggregates.ApplicationSystemRichInfo;
import com.melo.gatewaycenter.domain.manage.model.vo.GatewayServerVO;

import java.util.List;

public interface IConfigManageService {

    List<GatewayServerVO> listGatewayServer();

    boolean registerGatewayServerNode(String groupId, String gatewayId, String gatewayName, String gatewayAddress);

    ApplicationSystemRichInfo richDetail(String gatewayId);
}
