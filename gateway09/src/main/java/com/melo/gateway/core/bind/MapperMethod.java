package com.melo.gateway.core.bind;

import com.melo.gateway.core.mapping.HttpCommandType;
import com.melo.gateway.core.session.Configuration;
import com.melo.gateway.core.session.GatewaySession;

import java.lang.reflect.Method;
import java.util.Map;

public class MapperMethod {

    private String methodName;
    private final HttpCommandType httpCommandType;

    public MapperMethod(String uri, Method method, Configuration configuration) {
        this.methodName = configuration.getHttpStatement(uri).getMethodName();
        this.httpCommandType = configuration.getHttpStatement(uri).getCommandType();
    }

    public Object execute(GatewaySession session, Map<String, Object> params) {
        Object result = null;
        switch (httpCommandType) {
            case GET:
                result = session.get(methodName, params);
                break;
            case POST:
                result = session.post(methodName, params);
                break;
            case PUT:
                break;
            case DELETE:
                break;
            default:
                throw new RuntimeException("未知的请求方式" + httpCommandType);
        }
        return result;
    }
}
