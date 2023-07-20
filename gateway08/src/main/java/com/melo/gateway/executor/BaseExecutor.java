package com.melo.gateway.executor;

import com.melo.gateway.datasource.Connection;
import com.melo.gateway.executor.result.SessionResult;
import com.melo.gateway.mapping.HttpStatement;
import com.melo.gateway.session.Configuration;
import com.melo.gateway.type.SimpleTypeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public abstract class BaseExecutor implements Executor {

    private final Logger logger = LoggerFactory.getLogger(BaseExecutor.class);

    protected Configuration configuration;
    protected Connection connection;

    public BaseExecutor(Configuration configuration, Connection connection) {
        this.configuration = configuration;
        this.connection = connection;
    }

    @Override
    public SessionResult exec(HttpStatement httpStatement, Map<String, Object> params) throws Exception {
        String methodName = httpStatement.getMethodName();
        String parameterType = httpStatement.getParameterType();
        String[] parameterTypes = {parameterType};
        Object[] args = SimpleTypeRegistry.isSimpleType(parameterType) ? params.values().toArray() : new Object[]{params};
        logger.info("execute method {} args {}", methodName, args);

        try {
            Object data = doExec(methodName, parameterTypes, args);
            return SessionResult.buildSuccess(data);
        } catch (Exception e) {
            return SessionResult.buildFail(e.getMessage());
        }
    }

    protected abstract Object doExec(String methodName, String[] parameterTypes, Object[] args);
}
