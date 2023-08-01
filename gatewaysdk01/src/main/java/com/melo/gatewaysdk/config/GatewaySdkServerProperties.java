package com.melo.gatewaysdk.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("api-gateway-sdk")
public class GatewaySdkServerProperties {

    private String address;
    private String systemId;
    private String systemName;
    private String systemRegistry;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemRegistry() {
        return systemRegistry;
    }

    public void setSystemRegistry(String systemRegistry) {
        this.systemRegistry = systemRegistry;
    }
}
