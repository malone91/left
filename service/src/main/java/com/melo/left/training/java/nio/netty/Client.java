package com.melo.left.training.java.nio.netty;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 网关就是管道加过滤器
 * 用户、业务系统、移动端访问，请求量大
 * inbound 接入
 * outbound 高性能高效率低延迟访问backend拿回数据加工处理返回给用户
 * 现在是一个字符串，未来是一个后端返回的系统，这就是网关的一个原型
 * inbound outbound都是过滤器，管道
 * 一个进程可以多个端口，多个进程可绑定一个端口
 * Nginx多进程，一个端口
 *
 * 需要驱动力，内在的，外在的，逃避？
 *
 * 业务网关，流量网关
 * API six
 */
public class Client {
    public static void main(String[] args) {
        get("http://localhost:8808/test");
    }

    private static void get(String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");

        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            System.out.println(EntityUtils.toString(entity));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
