package com.melo.gateway.center.infrastructure.repository;

import cn.hutool.core.collection.CollectionUtil;
import com.melo.gateway.center.domain.model.ApiData;
import com.melo.gateway.center.domain.repository.IApiRepository;
import com.melo.gateway.center.infrastructure.mapper.HttpStatementMapper;
import com.melo.gateway.center.infrastructure.po.HttpStatement;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class ApiRepository implements IApiRepository {

    @Resource
    private HttpStatementMapper httpStatementMapper;

    @Override
    public List<ApiData> getHttpStatementList() {
        List<HttpStatement> httpStatementList = httpStatementMapper.list();
        if (CollectionUtil.isEmpty(httpStatementList)) {
            return new ArrayList<>();
        }
        List<ApiData> list = new ArrayList<>();
        for (HttpStatement httpStatement : httpStatementList) {
            ApiData apiData = new ApiData();
            apiData.setApplication(httpStatement.getApplication());
            apiData.setInterfaceName(httpStatement.getInterfaceName());
            apiData.setMethodName(httpStatement.getMethodName());
            apiData.setParameterType(httpStatement.getParameterType());
            apiData.setUri(httpStatement.getUri());
            apiData.setHttpCommandType(httpStatement.getHttpCommandType());
            apiData.setAuth(httpStatement.getAuth());
            list.add(apiData);
        }
        return list;
    }
}
