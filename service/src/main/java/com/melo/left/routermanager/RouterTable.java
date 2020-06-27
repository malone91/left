package com.melo.left.routermanager;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 本质上来讲，
 * 父子进程的地址空间以及数据都是要隔离的，
 * 使用 Copy-on-Write
 * 更多地体现的是一种延时策略，
 * 只有在真正需要复制的时候才复制，
 * 而不是提前复制好，
 * 同时 Copy-on-Write 还支持按需复制，
 * 所以 Copy-on-Write
 * 在操作系统领域是能够提升性能的。
 * 相比较而言，
 * Java 提供的 Copy-on-Write 容器，
 * 由于在修改的同时会复制整个容器，
 * 所以在提升读操作性能的同时，
 * 是以内存复制为代价的。这里你会发现，
 * 同样是应用 Copy-on-Write，
 * 不同的场景，对性能的影响是不同的。
 *
 * 下面我们再来思考 Router 该如何设计，服务提供方的每一次上线、下线都会更新路由信息，
 * 这时候你有两种选择。一种是通过更新 Router 的一个状态位来标识，如果这样做，
 * 那么所有访问该状态位的地方都需要同步访问，这样很影响性能。
 * 另外一种就是采用 Immutability 模式，每次上线、下线都创建新的 Router 对象或者删除对应的 Router 对象。
 * 由于上线、下线的频率很低，所以后者是最好的选择。
 */
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
