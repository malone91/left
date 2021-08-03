package com.melo.left.netty.webapp.common.auth;

import com.melo.left.netty.webapp.common.OperationResult;
import lombok.Data;

@Data
public class AuthOperationResult extends OperationResult {

    private final boolean passAuth;
}
