package com.melo.gateway.bind;

import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import org.apache.dubbo.rpc.service.GenericService;
import org.objectweb.asm.Type;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GenericReferenceProxyFactory {

    private final GenericService genericService;
    private final Map<String, IGenericReference> genericReferenceCache = new ConcurrentHashMap<>();

    public GenericReferenceProxyFactory(GenericService genericService) {
        this.genericService = genericService;
    }

    public IGenericReference newInstance(String method) {
        return genericReferenceCache.computeIfAbsent(method, k->{
            GenericReferenceProxy proxy = new GenericReferenceProxy(genericService, method);
            InterfaceMaker maker = new InterfaceMaker();
            maker.add(new Signature(method, Type.getType(String.class), new Type[]{Type.getType(String.class)}), null);
            Class<?> inferfaceClass = maker.create();
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Object.class);
            enhancer.setInterfaces(new Class[]{IGenericReference.class, inferfaceClass});
            enhancer.setCallback(proxy);
            return (IGenericReference) enhancer.create();
        });
    }
}
