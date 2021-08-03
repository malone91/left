package com.melo.left.netty.webapp.common.keepalive;

import com.melo.left.netty.webapp.common.Operation;
import com.melo.left.netty.webapp.common.OperationResult;
import lombok.Data;

@Data
public class KeepaliveOperation extends Operation {

    private long time;

    public KeepaliveOperation() {
        this.time = System.nanoTime();
    }

    @Override
    public OperationResult execute() {
        KeepaliveOperationResult orderResponse = new KeepaliveOperationResult(time);
        return orderResponse;
    }
}
