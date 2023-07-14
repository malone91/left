package com.melo.gateway;

import com.melo.mapping.HttpStatement;
import com.melo.session.Configuration;
import com.melo.session.GatewaySession;

import java.util.HashMap;
import java.util.Map;

public class MapperRegistry {

    private final Configuration configuration;

    public MapperRegistry(Configuration configuration) {
        this.configuration = configuration;
    }

    private final Map<String, MapperProxyFactory> knownMapperMap = new HashMap<>();

    public IGenericReference getMapper(String uri, GatewaySession gatewaySession) {
        final MapperProxyFactory mapperProxyFactory = knownMapperMap.get(uri);
        if (mapperProxyFactory == null) {
            throw new RuntimeException("uri" + uri + " is unknown to the registry");
        }
        return mapperProxyFactory.newInstance(gatewaySession);
    }

    public void addMapper(HttpStatement httpStatement) {
        String uri = httpStatement.getUri();
        if (hasMapper(uri)) {
            throw new RuntimeException("uri" + uri + " has already registry");
        }
        knownMapperMap.put(uri, new MapperProxyFactory(uri));
        configuration.addHttpStatement(httpStatement);
    }

    public <T> boolean hasMapper(String uri) {
        return knownMapperMap.containsKey(uri);
    }
}
