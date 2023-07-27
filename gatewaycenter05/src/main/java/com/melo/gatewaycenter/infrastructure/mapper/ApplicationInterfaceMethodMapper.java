package com.melo.gatewaycenter.infrastructure.mapper;

import com.melo.gatewaycenter.domain.manage.model.po.SystemInterfacePo;
import com.melo.gatewaycenter.infrastructure.po.ApplicationInterfaceMethod;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApplicationInterfaceMethodMapper {

    void insert(ApplicationInterfaceMethod method);

    List<ApplicationInterfaceMethod> list(List<SystemInterfacePo> params);
}
