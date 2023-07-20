package com.melo.gateway.bind;

import com.melo.gateway.executor.result.SessionResult;

import java.util.Map;

public interface IGenericReference {

    SessionResult $invoke(Map<String, Object> params);
}
