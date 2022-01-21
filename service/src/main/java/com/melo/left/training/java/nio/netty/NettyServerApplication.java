package com.melo.left.training.java.nio.netty;

/**
 * sb -u http://localhost:8808/test -c 40 -N 30
 * wrk -c 40 -d30 http://localhost:8808/test
 *
 * 43140   (RPS: 1185.3)                   CreateFile() Error: 5
 * Status 200:    43041
 * Status 303:    99
 *
 * RPS: 1322.8 (requests/second)
 * Max: 824ms
 * Min: 0ms
 * Avg: 6.1ms
 *
 *   50%   below 1ms
 *   60%   below 2ms
 *   70%   below 5ms
 *   80%   below 8ms
 *   90%   below 14ms
 *   95%   below 21ms
 *   98%   below 32ms
 *   99%   below 44ms
 * 99.9%   below 263ms
 */
public class NettyServerApplication {

    public static void main(String[] args) {
        HttpServer server = new HttpServer(false, 8808);
        try {
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
