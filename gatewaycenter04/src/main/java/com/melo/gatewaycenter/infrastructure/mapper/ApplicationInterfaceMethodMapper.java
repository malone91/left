package com.melo.gatewaycenter.infrastructure.mapper;

import com.melo.gatewaycenter.infrastructure.po.ApplicationInterfaceMethod;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplicationInterfaceMethodMapper {

    void insert(ApplicationInterfaceMethod method);
}
