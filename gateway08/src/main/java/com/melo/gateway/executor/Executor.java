package com.melo.gateway.executor;

import com.melo.gateway.executor.result.SessionResult;
import com.melo.gateway.mapping.HttpStatement;

import java.util.Map;

public interface Executor {

    SessionResult exec(HttpStatement httpStatement, Map<String, Object> params) throws Exception;
}
