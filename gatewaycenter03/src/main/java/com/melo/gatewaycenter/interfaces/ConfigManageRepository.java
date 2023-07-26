package com.melo.gatewaycenter.interfaces;

import cn.hutool.core.collection.CollectionUtil;
import com.melo.gatewaycenter.domain.manage.model.vo.GatewayServerDetailVO;
import com.melo.gatewaycenter.domain.manage.model.vo.GatewayServerVO;
import com.melo.gatewaycenter.domain.manage.repository.IConfigManageRepository;
import com.melo.gatewaycenter.infrastructure.mapper.GatewayServerDetailMapper;
import com.melo.gatewaycenter.infrastructure.mapper.GatewayServerMapper;
import com.melo.gatewaycenter.infrastructure.po.GatewayServer;
import com.melo.gatewaycenter.infrastructure.po.GatewayServerDetail;
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
}
