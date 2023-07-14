package com.melo.gateway;

import com.melo.mapping.HttpCommandType;
import com.melo.session.Configuration;
import com.melo.session.GatewaySession;

import java.lang.reflect.Method;

public class MapperMethod {

    private String uri;
    public final HttpCommandType commandType;

    public MapperMethod(String uri, Method method, Configuration configuration) {
        this.uri = uri;
        this.commandType = configuration.getHttpStatement(uri).getCommandType();
    }

    public Object execute(GatewaySession session, Object args) {
        Object result = null;
        switch (commandType) {
            case GET:
                result = session.get(uri, args);
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
