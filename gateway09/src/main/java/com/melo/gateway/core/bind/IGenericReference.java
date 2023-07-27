package com.melo.gateway.core.bind;

import com.melo.gateway.core.executor.result.SessionResult;

import java.util.Map;

public interface IGenericReference {

    SessionResult $invoke(Map<String, Object> params);
}
