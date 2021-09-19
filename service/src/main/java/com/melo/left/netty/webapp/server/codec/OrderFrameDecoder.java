package com.melo.left.netty.webapp.server.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class OrderFrameDecoder extends LengthFieldBasedFrameDecoder {

    public OrderFrameDecoder() {
        super(Integer.MAX_VALUE, 1, 2, 2, 2);
    }
}
