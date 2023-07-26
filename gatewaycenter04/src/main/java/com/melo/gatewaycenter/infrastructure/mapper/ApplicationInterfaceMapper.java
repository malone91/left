package com.melo.gatewaycenter.infrastructure.mapper;

import com.melo.gatewaycenter.infrastructure.po.ApplicationInterface;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplicationInterfaceMapper {

    void insert(ApplicationInterface applicationInterface);
}
