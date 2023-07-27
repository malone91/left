package com.melo.gateway.core.bind;

import com.melo.gateway.core.mapping.HttpStatement;
import com.melo.gateway.core.session.GatewaySession;
import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import org.objectweb.asm.Type;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapperProxyFactory {

    private String uri;

    public MapperProxyFactory(String uri) {
        this.uri = uri;
    }

    private final Map<String, IGenericReference> genericReferenceCache = new ConcurrentHashMap<>();

    public IGenericReference newInstance(GatewaySession gatewaySession) {
        return genericReferenceCache.computeIfAbsent(uri, k -> {
            HttpStatement httpStatement = gatewaySession.getConfiguration().getHttpStatement(uri);
            MapperProxy mapperProxy = new MapperProxy(gatewaySession, uri);
            InterfaceMaker maker = new InterfaceMaker();
            maker.add(new Signature(httpStatement.getMethodName(), Type.getType(String.class), new Type[]{Type.getType(String.class)}), null);
            Class<?> interfaceClass = maker.create();
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Object.class);
            enhancer.setInterfaces(new Class[]{IGenericReference.class, interfaceClass});
            enhancer.setCallback(mapperProxy);
            return (IGenericReference) enhancer.create();
        });
    }
}
