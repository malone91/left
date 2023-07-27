package com.melo.gateway.core.datasource;

import com.melo.gateway.core.session.Configuration;

public interface DataSourceFactory {

    void setProperties(Configuration configuration, String uri);

    DataSource getDataSource();
}
