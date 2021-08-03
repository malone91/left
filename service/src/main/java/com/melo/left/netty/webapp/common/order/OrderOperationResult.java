package com.melo.left.netty.webapp.common.order;

import com.melo.left.netty.webapp.common.OperationResult;
import lombok.Data;

@Data
public class OrderOperationResult extends OperationResult {

    private final int tableId;
    private final String dish;
    private final boolean complete;

}
