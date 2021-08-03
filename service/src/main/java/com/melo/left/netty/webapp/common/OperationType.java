package com.melo.left.netty.webapp.common;

import com.alibaba.csp.sentinel.util.function.Predicate;
import com.melo.left.netty.webapp.common.auth.AuthOperation;
import com.melo.left.netty.webapp.common.auth.AuthOperationResult;
import com.melo.left.netty.webapp.common.keepalive.KeepaliveOperation;
import com.melo.left.netty.webapp.common.keepalive.KeepaliveOperationResult;
import com.melo.left.netty.webapp.common.order.OrderOperaion;
import com.melo.left.netty.webapp.common.order.OrderOperationResult;
import lombok.Getter;

@Getter
public enum OperationType {

    AUTH(1, AuthOperation.class, AuthOperationResult.class),
    KEEPALIVE(2, KeepaliveOperation.class, KeepaliveOperationResult.class),
    ORDER(3, OrderOperaion.class, OrderOperationResult.class),
    ;

    private int opCode;
    private Class<? extends Operation> operationClazz;
    private Class<? extends OperationResult> operationResultClazz;

    OperationType(int opCode, Class<? extends Operation> operationClazz, Class<? extends OperationResult> responseClass) {
        this.opCode = opCode;
        this.operationClazz = operationClazz;
        this.operationResultClazz = responseClass;
    }


    public static OperationType fromOpCode(int type) {
        return getOperationType(requestType -> requestType.getOpCode() == type);
    }

    public static OperationType fromOperation(Operation operation) {
        return getOperationType(requestType -> requestType.getOperationClazz() == operation.getClass());
    }

    private static OperationType getOperationType(Predicate<OperationType> predicate) {
        for (OperationType operationType : values()) {
            if (predicate.test(operationType)) {
                return operationType;
            }
        }

        throw new AssertionError("no found type");
    }
}
