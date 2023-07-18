package com.melo.gateway.socket.agreement;

import com.alibaba.fastjson2.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestParser {

    private final FullHttpRequest request;

    public RequestParser(FullHttpRequest request) {
        this.request = request;
    }

    public Map<String, Object> parse() {
        String contentType = getContentType();
        HttpMethod method = request.method();
        if (HttpMethod.GET.equals(method)) {
            Map<String, Object> parameterMap = new HashMap<>();
            QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
            decoder.parameters().forEach((key, value) -> parameterMap.put(key, value.get(0)));
            return parameterMap;
        } else if (HttpMethod.POST.equals(method)) {
            switch (contentType) {
                case "multipart/form-data":
                    Map<String, Object> parameterMap = new HashMap<>();
                    HttpPostRequestDecoder postRequestDecoder = new HttpPostRequestDecoder(request);
                    postRequestDecoder.offer(request);
                    postRequestDecoder.getBodyHttpDatas().forEach(data -> {
                        Attribute attr = (Attribute) data;
                        try {
                            parameterMap.put(data.getName(), attr.getValue());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    return parameterMap;
                case "application/json":
                    ByteBuf byteBuf = request.content().copy();
                    if (byteBuf.isReadable()) {
                        return JSON.parseObject(byteBuf.toString(StandardCharsets.UTF_8));
                    }
                    break;
                default:
                    throw new RuntimeException("未实现的协议类型 Content-Type " + contentType);
            }
        }
        throw new RuntimeException("未实现的请求类型 HttpMethod " + method);
    }

    private String getContentType() {
        Optional<Map.Entry<String, String>> any = request.headers().entries().stream().filter(
                val -> "Content-Type".equals(val.getKey())
        ).findAny();
        Map.Entry<String, String> entry = any.orElse(null);
        if (entry == null) {
            throw new RuntimeException("Content-Type can't be null");
        }
        String contentType = entry.getValue();
        int idx = contentType.indexOf(";");
        if (idx > 0) {
            return contentType.substring(0, idx);
        } else {
            return contentType;
        }
    }
}
