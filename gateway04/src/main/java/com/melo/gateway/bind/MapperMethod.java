package com.melo.gateway.bind;

import com.melo.gateway.mapping.HttpCommandType;
import com.melo.gateway.session.Configuration;
import com.melo.gateway.session.GatewaySession;

import java.lang.reflect.Method;

public class MapperMethod {

    private String methodName;
    public final HttpCommandType commandType;

    public MapperMethod(String uri, Method method, Configuration configuration) {
        this.methodName = configuration.getHttpStatement(uri).getMethodName();
        this.commandType = configuration.getHttpStatement(uri).getCommandType();
    }

    public Object execute(GatewaySession session, Object args) {
        Object result = null;
        switch (commandType) {
            case GET:
                result = session.get(methodName, args);
                break;
            case POST:
                break;
            case PUT:
                break;
            case DELETE:
                break;
            default:
                throw new RuntimeException("unknown method " + commandType);
        }
        return result;
    }
}
