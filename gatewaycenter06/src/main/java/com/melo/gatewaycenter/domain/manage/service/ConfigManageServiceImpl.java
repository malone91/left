package com.melo.gatewaycenter.domain.manage.service;

import cn.hutool.core.collection.CollectionUtil;
import com.melo.gatewaycenter.application.IConfigManageService;
import com.melo.gatewaycenter.domain.manage.model.aggregates.ApplicationSystemRichInfo;
import com.melo.gatewaycenter.domain.manage.model.po.SystemInterfacePo;
import com.melo.gatewaycenter.domain.manage.model.vo.GatewayServerDetailVO;
import com.melo.gatewaycenter.domain.manage.model.vo.GatewayServerVO;
import com.melo.gatewaycenter.domain.manage.repository.IConfigManageRepository;
import com.melo.gatewaycenter.domain.register.model.vo.ApplicationInterfaceMethodVO;
import com.melo.gatewaycenter.domain.register.model.vo.ApplicationInterfaceVO;
import com.melo.gatewaycenter.domain.register.model.vo.ApplicationSystemVO;
import com.melo.gatewaycenter.infrastructure.common.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public ApplicationSystemRichInfo richDetail(String gatewayId, String systemId) {
        List<String> systemIdList = new ArrayList<>();
        if (StringUtils.isBlank(systemId)) {
            systemIdList = configManageRepository.listSystemId(gatewayId);
        } else {
            systemIdList.add(systemId);
        }
        if (CollectionUtil.isEmpty(systemIdList)) {
            return null;
        }
        List<ApplicationSystemVO> systemVOList = configManageRepository.listSystem(systemIdList);
        if (CollectionUtil.isEmpty(systemVOList)) {
            return null;
        }
        List<ApplicationInterfaceVO> interfaceVOS = configManageRepository.listInterface(systemIdList);
        if (CollectionUtil.isEmpty(interfaceVOS)) {
            return null;
        }
        Map<String, List<ApplicationInterfaceVO>> systemInterfacesMap = interfaceVOS.stream().collect(Collectors.groupingBy(ApplicationInterfaceVO::getSystemId));
        List<SystemInterfacePo> params = new ArrayList<>();
        for (ApplicationInterfaceVO vo : interfaceVOS) {
            SystemInterfacePo po = new SystemInterfacePo();
            po.setSystemId(vo.getSystemId());
            po.setInterfaceId(vo.getInterfaceId());
            params.add(po);
        }
        List<ApplicationInterfaceMethodVO> listMethod = configManageRepository.listMethod(params);
        if (CollectionUtil.isEmpty(listMethod)) {
            return null;
        }
        Map<String, List<ApplicationInterfaceMethodVO>> systemInterfaceMethodMap = listMethod.stream().collect(Collectors.groupingBy(ApplicationInterfaceMethodVO::systemInterfaceKey));

        for (ApplicationSystemVO systemVO : systemVOList) {
            List<ApplicationInterfaceVO> applicationInterfaceVOS = systemInterfacesMap.get(systemVO.getSystemId());
            if (CollectionUtil.isEmpty(applicationInterfaceVOS)) {
                continue;
            }
            systemVO.setInterfaceList(applicationInterfaceVOS);
            for (ApplicationInterfaceVO interfaceVO : applicationInterfaceVOS) {
                interfaceVO.setMethodList(systemInterfaceMethodMap.get(interfaceVO.getSystemId() + interfaceVO.getInterfaceId()));
            }
        }
        return new ApplicationSystemRichInfo(gatewayId, systemVOList);
    }

    @Override
    public String queryGatewayDistribution(String systemId) {
        return configManageRepository.queryGatewayDistribution(systemId);
    }
}
