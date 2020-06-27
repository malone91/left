package com.melo.left.routermanager;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class RouterTable {

    //key 接口名，value 路由集合
    ConcurrentHashMap<String, CopyOnWriteArraySet<Router>>
            rt = new ConcurrentHashMap<>();

    public Set<Router> get(String iface) {
        return rt.get(iface);
    }

    public void remove(Router router) {
        Set<Router> set = rt.get(router.getIface());
        if (set != null) {
            set.remove(router);
        }
    }

    public void add(Router router) {
        Set<Router> set = rt.computeIfAbsent(
                router.getIface(), r -> new CopyOnWriteArraySet<Router>()

        );
        set.add(router);
    }
}
