package com.melo.gateway.core.datasource.unpooled;

import com.melo.gateway.core.datasource.DataSource;
import com.melo.gateway.core.datasource.DataSourceFactory;
import com.melo.gateway.core.datasource.DataSourceType;
import com.melo.gateway.core.session.Configuration;

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
