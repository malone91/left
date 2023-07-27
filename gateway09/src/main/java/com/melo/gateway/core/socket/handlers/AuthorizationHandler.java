package com.melo.gateway.core.socket.handlers;

import com.melo.gateway.core.mapping.HttpStatement;
import com.melo.gateway.core.session.Configuration;
import com.melo.gateway.core.socket.BaseHandler;
import com.melo.gateway.core.socket.agreement.AgreementConstants;
import com.melo.gateway.core.socket.agreement.GatewayResultMessage;
import com.melo.gateway.core.socket.agreement.ResponseParser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthorizationHandler extends BaseHandler<FullHttpRequest> {

    private final Logger logger = LoggerFactory.getLogger(AuthorizationHandler.class);

    private final Configuration configuration;

    public AuthorizationHandler(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void session(ChannelHandlerContext ctx, Channel channel, FullHttpRequest request) {
        logger.info("gateway receive uri {} method {}", request.uri(), request.method());
        try {
            HttpStatement httpStatement = channel.attr(AgreementConstants.HTTP_STATEMENT).get();
            if (httpStatement.isAuth()) {
                String uId = request.headers().get("uId");
                String token = request.headers().get("token");
                if (StringUtils.isBlank(token)) {
                    DefaultHttpResponse response = new ResponseParser().parse(GatewayResultMessage.buildFail(AgreementConstants.ResponseCode._400.getCode(), "非法的token"));
                    ctx.writeAndFlush(response);
                    return;
                }
                boolean validate = configuration.authValidate(uId, token);
                if (validate) {
                    request.retain();
                    ctx.fireChannelRead(request);
                } else {
                    //鉴权失败
                    DefaultHttpResponse response = new ResponseParser().parse(GatewayResultMessage.buildFail(AgreementConstants.ResponseCode._403.getCode(), "无权访问该接口"));
                    ctx.writeAndFlush(response);
                }
            } else {
                //不鉴权
                request.retain();
                ctx.fireChannelRead(request);
            }
        } catch (Exception e) {
            DefaultHttpResponse response = new ResponseParser().parse(GatewayResultMessage.buildFail(AgreementConstants.ResponseCode._500.getCode(), "鉴权异常"));
            ctx.writeAndFlush(response);
        }
    }
}
