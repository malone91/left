package com.melo.gateway.executor;

import com.melo.gateway.executor.result.GatewayResult;
import com.melo.gateway.mapping.HttpStatement;

import java.util.Map;

public interface Executor {

    GatewayResult exec(HttpStatement httpStatement, Map<String, Object> params) throws Exception;
}
