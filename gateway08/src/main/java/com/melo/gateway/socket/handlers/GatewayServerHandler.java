package com.melo.gateway.socket.handlers;

import com.melo.gateway.mapping.HttpStatement;
import com.melo.gateway.session.Configuration;
import com.melo.gateway.socket.BaseHandler;
import com.melo.gateway.socket.agreement.AgreementConstants;
import com.melo.gateway.socket.agreement.GatewayResultMessage;
import com.melo.gateway.socket.agreement.RequestParser;
import com.melo.gateway.socket.agreement.ResponseParser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatewayServerHandler extends BaseHandler<FullHttpRequest> {

    private final Logger logger = LoggerFactory.getLogger(GatewayServerHandler.class);

    private final Configuration configuration;

    public GatewayServerHandler(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void session(ChannelHandlerContext ctx, Channel channel, FullHttpRequest request) {
        logger.info("gateway receive uri {} method {}", request.uri(), request.method());
        try {
            RequestParser requestParser = new RequestParser(request);
            String uri = requestParser.getUri();

            HttpStatement httpStatement = configuration.getHttpStatement(uri);
            channel.attr(AgreementConstants.HTTP_STATEMENT).set(httpStatement);

            //放行
            request.retain();
            ctx.fireChannelRead(request);
        } catch (Exception e) {
            DefaultHttpResponse response = new ResponseParser().parse(GatewayResultMessage.buildFail(AgreementConstants.ResponseCode._500.getCode(), AgreementConstants.ResponseCode._500.getInfo()));
            ctx.writeAndFlush(response);
        }
    }
}
