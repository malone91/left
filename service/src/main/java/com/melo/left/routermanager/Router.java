package com.melo.left.routermanager;

public final class Router {

    private final String ip;
    private final Integer port;
    private final String iface;

    public Router(String ip, Integer port, String iface) {
        this.ip = ip;
        this.port = port;
        this.iface = iface;
    }

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    public String getIface() {
        return iface;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Router) {
            Router r = (Router) obj;
            return iface.equals(r.iface)
                    && ip.equals(r.ip)
                    && port.equals(r.port);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }
}
