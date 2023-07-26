package com.melo.gateway.center.infrastructure.mapper;

import com.melo.gateway.center.infrastructure.po.HttpStatement;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HttpStatementMapper {

    List<HttpStatement> list();
}
