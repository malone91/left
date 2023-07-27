package com.melo.gatewaycenter.infrastructure.common;

public enum ResponseCode {

    SUCCESS("0","成功"),
    FAIL("1","失败"),
    ILLEGAL_PARAM("2","非法参数"),
    DUPLICATE("3","主键冲突"),
    NO_UPDATE("4","SQL操作无更新");

    private String code;
    private String info;

    ResponseCode(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
