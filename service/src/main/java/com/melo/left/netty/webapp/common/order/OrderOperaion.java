package com.melo.left.netty.webapp.common.order;

import com.melo.left.netty.webapp.common.Operation;
import com.melo.left.netty.webapp.common.OperationResult;
import lombok.Data;

@Data
public class OrderOperaion extends Operation {

    private int tableId;
    private String dish;

    public OrderOperaion(int tableId, String dish) {
        this.tableId = tableId;
        this.dish = dish;
    }

    @Override
    public OperationResult execute() {
        System.out.println("order's executing startup with order request: " + toString());
        System.out.println("order's executing completed");
        OrderOperationResult orderResponse = new OrderOperationResult(tableId, dish, true);
        return orderResponse;
    }
}
