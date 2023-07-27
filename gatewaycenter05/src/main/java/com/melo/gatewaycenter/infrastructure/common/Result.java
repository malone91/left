package com.melo.gatewaycenter.infrastructure.common;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;
    private String info;
    private T t;

    public Result(String code, String info, T t) {
        this.code = code;
        this.info = info;
        this.t = t;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getInfo() {
        return info;
    }

    public T getT() {
        return t;
    }
}
