package com.melo.gatewaycenter.infrastructure.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GatewayDistributionMapper {

    List<String> list();

    String queryGatewayDistribution(String systemId);
}
