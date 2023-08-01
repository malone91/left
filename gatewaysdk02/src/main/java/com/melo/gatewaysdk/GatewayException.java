package com.melo.gatewaysdk;

public class GatewayException extends RuntimeException{

    public GatewayException(String msg) {
        super(msg);
    }

    public GatewayException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
