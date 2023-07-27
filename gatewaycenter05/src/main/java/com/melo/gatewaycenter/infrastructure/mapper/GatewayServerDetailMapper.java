package com.melo.gatewaycenter.infrastructure.mapper;

import com.melo.gatewaycenter.infrastructure.po.GatewayServerDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GatewayServerDetailMapper {

    void insert(GatewayServerDetail detail);

    GatewayServerDetail query(GatewayServerDetail detail);

    boolean updateStatus(GatewayServerDetail detail);
}
