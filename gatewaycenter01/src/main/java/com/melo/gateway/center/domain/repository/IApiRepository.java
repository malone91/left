package com.melo.gateway.center.domain.repository;

import com.melo.gateway.center.domain.model.ApiData;

import java.util.List;

public interface IApiRepository {

    List<ApiData> getHttpStatementList();
}
