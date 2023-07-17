package com.melo.gateway.mapping;

/**
 * 网关接口的对象映射
 */
public class HttpStatement {

    private String application;
    private String interfaceName;
    private String methodName;
    private String uri;
    private HttpCommandType commandType;

    public HttpStatement(String application, String interfaceName, String methodName, String uri, HttpCommandType commandType) {
        this.application = application;
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.uri = uri;
        this.commandType = commandType;
    }

    public String getApplication() {
        return application;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getUri() {
        return uri;
    }

    public HttpCommandType getCommandType() {
        return commandType;
    }
}
