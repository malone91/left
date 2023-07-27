package com.melo.gateway.core.socket.agreement;

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

    public String getUri() {
        String uri = request.uri();
        int idx = uri.indexOf("?");
        uri = idx > 0 ? uri.substring(0, idx) : uri;
        if ("/favicon.ico".equals(uri)) {
            return null;
        }
        return uri;
    }

    public Map<String, Object> parse() {
        HttpMethod method = request.method();
        if (HttpMethod.GET.equals(method)) {
            Map<String, Object> parameterMap = new HashMap<>();
            QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
            decoder.parameters().forEach((key, val) -> parameterMap.put(key, val.get(0)));
            return parameterMap;
        } else if (HttpMethod.POST.equals(method)) {
            String contentType = getContentType();
            switch (contentType) {
                case "multipart/form-data":
                    Map<String, Object> paramterMap = new HashMap<>();
                    HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(request);
                    decoder.offer(request);
                    decoder.getBodyHttpDatas().forEach(data -> {
                        Attribute attribute = (Attribute) data;
                        try {
                            paramterMap.put(data.getName(), attribute.getValue());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    return paramterMap;
                case "application/json":
                    ByteBuf copy = request.content().copy();
                    if (copy.isReadable()) {
                        String content = copy.toString(StandardCharsets.UTF_8);
                        return JSON.parseObject(content);
                    }
                    break;
                case "none":
                    return new HashMap<>();
                default:
                    throw new RuntimeException("未实现的 contentType " + contentType);
            }
        }
        throw new RuntimeException("未实现的 HttpMethod " + method);
    }

    private String getContentType() {
        Optional<Map.Entry<String, String>> optional = request.headers().entries().stream().filter(
                val -> "Content-Type".equals(val.getKey())
        ).findAny();
        Map.Entry<String, String> entry = optional.orElse(null);
        if (entry == null) {
            return "none";
        }
        String contentType = entry.getValue();
        int idx = contentType.indexOf(";");
        return idx > 0 ? contentType.substring(0, idx) : contentType;
    }
}
