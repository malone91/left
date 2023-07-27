package com.melo.gatewaycenter.domain.manage.repository;

import com.melo.gatewaycenter.domain.manage.model.po.SystemInterfacePo;
import com.melo.gatewaycenter.domain.manage.model.vo.GatewayServerDetailVO;
import com.melo.gatewaycenter.domain.manage.model.vo.GatewayServerVO;
import com.melo.gatewaycenter.domain.register.model.vo.ApplicationInterfaceMethodVO;
import com.melo.gatewaycenter.domain.register.model.vo.ApplicationInterfaceVO;
import com.melo.gatewaycenter.domain.register.model.vo.ApplicationSystemVO;

import java.util.List;

public interface IConfigManageRepository {

    List<GatewayServerVO> listGatewayServer();

    boolean registerGatewayServerNode(String groupId, String gatewayId, String gatewayName, String gatewayAddress, Integer available);

    GatewayServerDetailVO detailGatewayServer(String gatewayId, String gatewayAddress);

    boolean updateGatewayStatus(String gatewayId, String gatewayAddress, Integer available);

    List<String> listSystemId(String gatewayId);

    List<ApplicationSystemVO> listSystem(List<String> systemIdList);

    List<ApplicationInterfaceVO> listInterface(List<String> systemIdList);

    List<ApplicationInterfaceMethodVO> listMethod(List<SystemInterfacePo> params);
}
