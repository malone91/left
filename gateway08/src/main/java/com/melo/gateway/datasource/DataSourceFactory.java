package com.melo.gateway.datasource;

import com.melo.gateway.session.Configuration;

public interface DataSourceFactory {

    void setProperties(Configuration configuration, String uri);

    DataSource getDataSource();
}
