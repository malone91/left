package com.melo.gateway.datasource.unpooled;

import com.melo.gateway.datasource.DataSource;
import com.melo.gateway.datasource.DataSourceFactory;
import com.melo.gateway.datasource.DataSourceType;
import com.melo.gateway.session.Configuration;

public class UnpooledDataSourceFactory implements DataSourceFactory {

    private UnpooledDataSource dataSource;

    public UnpooledDataSourceFactory() {
        this.dataSource = new UnpooledDataSource();
    }

    @Override
    public void setProperties(Configuration configuration, String uri) {
        this.dataSource.setConfiguration(configuration);
        this.dataSource.setDataSourceType(DataSourceType.DUBBO);
        this.dataSource.setHttpStatement(configuration.getHttpStatement(uri));
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
