package com.melo.gatewaycenter.infrastructure.mapper;

import com.melo.gatewaycenter.infrastructure.po.ApplicationSystem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApplicationSystemMapper {

    void insert(ApplicationSystem system);

    List<ApplicationSystem> listBySystemIdList(List<String> list);
}
