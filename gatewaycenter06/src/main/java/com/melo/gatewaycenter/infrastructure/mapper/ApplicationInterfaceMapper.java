package com.melo.gatewaycenter.infrastructure.mapper;

import com.melo.gatewaycenter.infrastructure.po.ApplicationInterface;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApplicationInterfaceMapper {

    void insert(ApplicationInterface applicationInterface);

    List<ApplicationInterface> list(List<String> systemIdList);
}
