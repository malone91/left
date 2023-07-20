package com.melo.gateway.socket;

import com.melo.gateway.session.Configuration;
import com.melo.gateway.session.defaults.DefaultGatewaySessionFactory;
import com.melo.gateway.socket.handlers.AuthorizationHandler;
import com.melo.gateway.socket.handlers.GatewayServerHandler;
import com.melo.gateway.socket.handlers.ProtocolDataHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class GatewayChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final Configuration configuration;
    private final DefaultGatewaySessionFactory gatewaySessionFactory;

    public GatewayChannelInitializer(Configuration configuration, DefaultGatewaySessionFactory gatewaySessionFactory) {
        this.configuration = configuration;
        this.gatewaySessionFactory = gatewaySessionFactory;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpRequestDecoder());
        //注意 如果写成了decoder就不会返回结果了 一直等待
        pipeline.addLast(new HttpResponseEncoder());
        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
        pipeline.addLast(new GatewayServerHandler(configuration));
        pipeline.addLast(new AuthorizationHandler(configuration));
        pipeline.addLast(new ProtocolDataHandler(gatewaySessionFactory));
    }
}
