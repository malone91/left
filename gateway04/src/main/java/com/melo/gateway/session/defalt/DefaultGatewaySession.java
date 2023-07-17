package com.melo.gateway.session.defalt;

import com.melo.gateway.bind.IGenericReference;
import com.melo.gateway.datasource.Connection;
import com.melo.gateway.datasource.DataSource;
import com.melo.gateway.session.Configuration;
import com.melo.gateway.session.GatewaySession;

public class DefaultGatewaySession implements GatewaySession {

    private Configuration configuration;
    public String uri;
    public DataSource dataSource;

    public DefaultGatewaySession(Configuration configuration, String uri, DataSource dataSource) {
        this.configuration = configuration;
        this.uri = uri;
        this.dataSource = dataSource;
    }

    @Override
    public Object get(String method, Object parameter) {
        Connection connection = dataSource.getConnection();
        return connection.execute(method, new String[]{"java.lang.String"}, new String[]{"str"}, new Object[]{parameter});
    }

    @Override
    public IGenericReference getMapper() {
        return configuration.getMapper(uri, this);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
