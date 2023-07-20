package com.melo.gateway.socket.agreement;

import com.alibaba.fastjson.JSON;
import io.netty.handler.codec.http.*;

public class ResponseParser {

    public DefaultHttpResponse parse(GatewayResultMessage result) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.content().writeBytes(JSON.toJSONString(result).getBytes());
        HttpHeaders headers = response.headers();
        headers.add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON + "; charset=UTF-8");
        headers.add(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        headers.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        headers.add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        headers.add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS, "*");
        headers.add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE");
        headers.add(HttpHeaderNames.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        return response;
    }
}
