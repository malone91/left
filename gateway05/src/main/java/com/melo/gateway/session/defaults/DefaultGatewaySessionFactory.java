package com.melo.gateway.session.defaults;

import com.melo.gateway.datasource.DataSource;
import com.melo.gateway.datasource.DataSourceFactory;
import com.melo.gateway.datasource.unpooled.UnpooledDataSourceFactory;
import com.melo.gateway.session.Configuration;
import com.melo.gateway.session.GatewaySession;
import com.melo.gateway.session.GatewaySessionFactory;

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
        return new DefaultGatewaySession(configuration, uri, dataSource);
    }
}
