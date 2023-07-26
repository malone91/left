package com.melo.gatewaycenter.infrastructure.mapper;

import com.melo.gatewaycenter.infrastructure.po.GatewayServer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GatewayServerMapper {

    List<GatewayServer> list();
}
