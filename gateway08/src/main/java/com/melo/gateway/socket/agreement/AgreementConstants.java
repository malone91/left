package com.melo.gateway.socket.agreement;

import com.melo.gateway.mapping.HttpStatement;
import io.netty.util.AttributeKey;

public class AgreementConstants {

    public static final AttributeKey<HttpStatement> HTTP_STATEMENT = AttributeKey.valueOf("HttpStatement");

    public enum ResponseCode {
        _200("200", "访问成功"),
        _400("400", "参数类型不匹配"),
        _403("403", "拒绝访问"),
        _404("404", "找不到资源"),
        _500("500", "服务端错误"),
        _502("502", "网关错误");

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
}
