package com.melo.gateway.center.domain.service;

import com.melo.gateway.center.api.IApiService;
import com.melo.gateway.center.domain.model.ApiData;
import com.melo.gateway.center.domain.repository.IApiRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ApiServiceImpl implements IApiService {

    @Resource
    private IApiRepository apiRepository;

    @Override
    public List<ApiData> getHttpStatementList() {
        return apiRepository.getHttpStatementList();
    }
}
