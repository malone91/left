package com.melo.gatewaycenter.infrastructure.repository;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.melo.gatewaycenter.domain.manage.model.po.SystemInterfacePo;
import com.melo.gatewaycenter.domain.manage.model.vo.GatewayServerDetailVO;
import com.melo.gatewaycenter.domain.manage.model.vo.GatewayServerVO;
import com.melo.gatewaycenter.domain.manage.repository.IConfigManageRepository;
import com.melo.gatewaycenter.domain.register.model.vo.ApplicationInterfaceMethodVO;
import com.melo.gatewaycenter.domain.register.model.vo.ApplicationInterfaceVO;
import com.melo.gatewaycenter.domain.register.model.vo.ApplicationSystemVO;
import com.melo.gatewaycenter.infrastructure.mapper.*;
import com.melo.gatewaycenter.infrastructure.po.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConfigManageRepository implements IConfigManageRepository {

    @Resource
    public GatewayServerMapper gatewayServerMapper;
    @Resource
    private GatewayServerDetailMapper gatewayServerDetailMapper;
    @Resource
    private GatewayDistributionMapper gatewayDistributionMapper;
    @Resource
    private ApplicationSystemMapper applicationSystemMapper;
    @Resource
    private ApplicationInterfaceMapper applicationInterfaceMapper;
    @Resource
    private ApplicationInterfaceMethodMapper applicationInterfaceMethodMapper;

    @Override
    public List<GatewayServerVO> listGatewayServer() {
        List<GatewayServer> list = gatewayServerMapper.list();
        List<GatewayServerVO> result = new ArrayList<>();
        if (CollectionUtil.isEmpty(list)) {
            return result;
        }
        for (GatewayServer server : list) {
            GatewayServerVO vo = new GatewayServerVO();
            vo.setGroupId(server.getGroupId());
            vo.setGroupName(server.getGroupName());
            result.add(vo);
        }
        return result;
    }

    @Override
    public boolean registerGatewayServerNode(String groupId, String gatewayId, String gatewayName, String gatewayAddress, Integer available) {
        GatewayServerDetail detail = new GatewayServerDetail();
        detail.setGroupId(groupId);
        detail.setGatewayId(gatewayId);
        detail.setGatewayName(gatewayName);
        detail.setGatewayAddress(gatewayAddress);
        detail.setStatus(available);
        gatewayServerDetailMapper.insert(detail);
        return true;
    }

    @Override
    public GatewayServerDetailVO detailGatewayServer(String gatewayId, String gatewayAddress) {
        GatewayServerDetail detail = new GatewayServerDetail();
        detail.setGatewayId(gatewayId);
        detail.setGatewayAddress(gatewayAddress);
        GatewayServerDetail serverDetail = gatewayServerDetailMapper.query(detail);
        if (serverDetail == null) {
            return null;
        }
        GatewayServerDetailVO vo = new GatewayServerDetailVO();
        vo.setGatewayId(serverDetail.getGatewayId());
        vo.setGatewayName(serverDetail.getGatewayName());
        vo.setGatewayAddress(serverDetail.getGatewayAddress());
        vo.setStatus(serverDetail.getStatus());
        return vo;
    }

    @Override
    public boolean updateGatewayStatus(String gatewayId, String gatewayAddress, Integer available) {
        GatewayServerDetail detail = new GatewayServerDetail();
        detail.setGatewayId(gatewayId);
        detail.setGatewayAddress(gatewayAddress);
        detail.setStatus(available);
        return gatewayServerDetailMapper.updateStatus(detail);
    }

    @Override
    public List<String> listSystemId(String gatewayId) {
        return gatewayDistributionMapper.list();
    }

    @Override
    public List<ApplicationSystemVO> listSystem(List<String> systemIdList) {
        List<ApplicationSystem> applicationSystems = applicationSystemMapper.listBySystemIdList(systemIdList);
        List<ApplicationSystemVO> list = new ArrayList<>();
        if (CollectionUtil.isEmpty(applicationSystems)) {
            return list;
        }
        for (ApplicationSystem system : applicationSystems) {
            ApplicationSystemVO convert = Convert.convert(ApplicationSystemVO.class, system);
            list.add(convert);
        }
        return list;
    }

    @Override
    public List<ApplicationInterfaceVO> listInterface(List<String> systemIdList) {
        List<ApplicationInterface> list = applicationInterfaceMapper.list(systemIdList);
        List<ApplicationInterfaceVO> result = new ArrayList<>();
        if (CollectionUtil.isEmpty(list)) {
            return result;
        }
        for (ApplicationInterface applicationInterface : list) {
            ApplicationInterfaceVO convert = Convert.convert(ApplicationInterfaceVO.class, applicationInterface);
            result.add(convert);
        }
        return result;
    }

    @Override
    public List<ApplicationInterfaceMethodVO> listMethod(List<SystemInterfacePo> params) {
        List<ApplicationInterfaceMethodVO> result = new ArrayList<>();
        List<ApplicationInterfaceMethod> list = applicationInterfaceMethodMapper.list(params);
        if (CollectionUtil.isEmpty(list)) {
            return result;
        }
        for (ApplicationInterfaceMethod interfaceMethod : list) {
            ApplicationInterfaceMethodVO convert = Convert.convert(ApplicationInterfaceMethodVO.class, interfaceMethod);
            result.add(convert);
        }
        return result;
    }

    @Override
    public String queryGatewayDistribution(String systemId) {
        return gatewayDistributionMapper.queryGatewayDistribution(systemId);
    }
}
