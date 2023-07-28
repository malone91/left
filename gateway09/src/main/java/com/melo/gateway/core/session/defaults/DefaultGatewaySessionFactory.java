package com.melo.gateway.core.session.defaults;

import com.melo.gateway.core.datasource.DataSource;
import com.melo.gateway.core.datasource.DataSourceFactory;
import com.melo.gateway.core.datasource.unpooled.UnpooledDataSourceFactory;
import com.melo.gateway.core.executor.Executor;
import com.melo.gateway.core.session.Configuration;
import com.melo.gateway.core.session.GatewaySession;
import com.melo.gateway.core.session.GatewaySessionFactory;

public class DefaultGatewaySessionFactory implements GatewaySessionFactory {

    private final Configuration configuration;

    public DefaultGatewaySessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public GatewaySession openSession(String uri) {
        DataSourceFactory dataSourceFactory = new UnpooledDataSourceFactory();
        dataSourceFactory.setProperties(configuration, uri);
        DataSource dataSource = dataSourceFactory.getDataSource();
         Executor executor = configuration.newExecutor(dataSource.getConnection());
        return new DefaultGatewaySession(configuration, uri, executor);
    }
}
