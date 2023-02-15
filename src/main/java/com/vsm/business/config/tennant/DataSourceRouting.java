package com.vsm.business.config.tennant;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.vsm.business.domain.Tennant;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

//@Component
public class DataSourceRouting extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContextHolder.getTenant();
    }

    public void initDatasource(Tennant[] lstTennant,
                               DataSource defaultDataSource) {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        for (Tennant tennant : lstTennant){
            dataSourceMap.put(tennant.getTennantCode(), buildDataSource(tennant));
        }
        this.setTargetDataSources(dataSourceMap);
        this.setDefaultTargetDataSource(defaultDataSource);
    }

    public DataSource buildDataSource(Tennant tennant) {
        Properties props = new Properties();
        props.setProperty("poolName", tennant.getId() + tennant.getDatabaseSchemaName());
        props.setProperty("autoCommit", "False");
        props.setProperty("driverClassName", "org.postgresql.Driver");
        props.setProperty("jdbcUrl", tennant.getDatabaseUrl());
        props.setProperty("username", tennant.getDatabaseUsermame());
        props.setProperty("password", tennant.getDatabasePassword());
        props.setProperty("maximumPoolSize", "10");
        props.setProperty("minimumIdle", "2");
        props.setProperty("dataSource.cachePrepStmts","true");
        props.setProperty("dataSource.prepStmtCacheSize", "256");
        props.setProperty("dataSource.prepStmtCacheSqlLimit", "2048");
        props.setProperty("dataSource.useServerPrepStmts","true");

        HikariConfig config = new HikariConfig(props);
        HikariDataSource ds = new HikariDataSource(config);
        return ds;
    }
}
