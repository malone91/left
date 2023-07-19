package com.melo.gateway.socket.handlers;

import com.melo.gateway.bind.IGenericReference;
import com.melo.gateway.session.GatewaySession;
import com.melo.gateway.session.defaults.DefaultGatewaySessionFactory;
import com.melo.gateway.socket.BaseHandler;
import com.melo.gateway.socket.agreement.RequestParser;
import com.melo.gateway.socket.agreement.ResponseParser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class GatewayServerHandler extends BaseHandler<FullHttpRequest> {

    private final Logger logger = LoggerFactory.getLogger(GatewayServerHandler.class);

    private final DefaultGatewaySessionFactory gatewaySessionFactory;

    public GatewayServerHandler(DefaultGatewaySessionFactory gatewaySessionFactory) {
        this.gatewaySessionFactory = gatewaySessionFactory;
    }

    @Override
    protected void session(ChannelHandlerContext ctx, Channel channel, FullHttpRequest request) {
        logger.info("receive gateway uri {} method {}", request.uri(), request.method());
        RequestParser requestParser = new RequestParser(request);
        String uri = requestParser.getUri();
        if (uri == null) {
            return;
        }
        Map<String, Object> args = requestParser.parse();

        GatewaySession gatewaySession = gatewaySessionFactory.openSession(uri);
        IGenericReference genericReference = gatewaySession.getMapper();
        Object result = genericReference.$invoke(args);
        channel.writeAndFlush(new ResponseParser().parse(result));
    }
}
