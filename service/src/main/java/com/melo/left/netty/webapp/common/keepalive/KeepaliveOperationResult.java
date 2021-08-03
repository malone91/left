package com.melo.left.netty.webapp.common.keepalive;

import com.melo.left.netty.webapp.common.OperationResult;
import lombok.Data;

@Data
public class KeepaliveOperationResult extends OperationResult {

    private final long time;
}
