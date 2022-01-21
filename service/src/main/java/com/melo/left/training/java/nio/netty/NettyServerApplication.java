package com.melo.left.training.java.nio.netty;

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
