package com.melo.left.netty.webapp.common.auth;

import com.melo.left.netty.webapp.common.Operation;
import com.melo.left.netty.webapp.common.OperationResult;
import lombok.Data;

@Data
public class AuthOperation extends Operation {

    private final String userName;
    private final String password;
    @Override
    public OperationResult execute() {
        if ("admin".equalsIgnoreCase(this.userName)) {
            AuthOperationResult orderResponse = new AuthOperationResult(true);
            return orderResponse;
        }

        return new AuthOperationResult(false);
    }
}
