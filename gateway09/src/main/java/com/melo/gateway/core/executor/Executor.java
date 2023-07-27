package com.melo.gateway.core.executor;

import com.melo.gateway.core.executor.result.SessionResult;
import com.melo.gateway.core.mapping.HttpStatement;

import java.util.Map;

public interface Executor {

    SessionResult exec(HttpStatement httpStatement, Map<String, Object> params) throws Exception;
}
