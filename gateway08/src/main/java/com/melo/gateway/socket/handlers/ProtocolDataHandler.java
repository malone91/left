package com.melo.gateway.socket.handlers;

import com.melo.gateway.executor.result.SessionResult;
import com.melo.gateway.session.GatewaySession;
import com.melo.gateway.session.defaults.DefaultGatewaySessionFactory;
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

import java.util.Map;

public class ProtocolDataHandler extends BaseHandler<FullHttpRequest> {

    private final Logger logger = LoggerFactory.getLogger(ProtocolDataHandler.class);

    private final DefaultGatewaySessionFactory gatewaySessionFactory;

    public ProtocolDataHandler(DefaultGatewaySessionFactory gatewaySessionFactory) {
        this.gatewaySessionFactory = gatewaySessionFactory;
    }

    @Override
    protected void session(ChannelHandlerContext ctx, Channel channel, FullHttpRequest request) {
        logger.info("gateway receive uri {} method {}", request.uri(), request.method());
        try {
            RequestParser requestParser = new RequestParser(request);
            String uri = requestParser.getUri();
            if (uri == null) {
                return;
            }
            Map<String, Object> args = requestParser.parse();

            GatewaySession gatewaySession = gatewaySessionFactory.openSession(uri);
            SessionResult sessionResult = gatewaySession.getMapper().$invoke(args);
            GatewayResultMessage resultMessage = "0".equals(sessionResult.getCode()) ?
                    GatewayResultMessage.buildSuccess(sessionResult.getData()) :
                    GatewayResultMessage.buildFail(sessionResult.getCode(), "网关协议调用失败");
            DefaultHttpResponse response = new ResponseParser().parse(resultMessage);
            ctx.writeAndFlush(response);
        } catch (Exception e) {
            DefaultHttpResponse response = new ResponseParser().parse(GatewayResultMessage.buildFail(AgreementConstants.ResponseCode._502.getCode(), "网关协议调用失败" + e.getMessage()));
            ctx.writeAndFlush(response);
        }
    }
}
