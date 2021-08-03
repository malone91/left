package com.melo.left.netty.webapp.common;

import lombok.Data;

@Data
public class MessageHeader {

    private int version = 1;
    private int opCode;
    private long streamId;
}
