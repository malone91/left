package com.melo.gateway.center.api;

import com.melo.gateway.center.domain.model.ApiData;

import java.util.List;

public interface IApiService {

    List<ApiData> getHttpStatementList();
}
