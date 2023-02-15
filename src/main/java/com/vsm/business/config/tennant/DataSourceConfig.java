package com.vsm.business.config.tennant;

import javax.sql.DataSource;

import com.vsm.business.domain.ProcessInfo;
import com.vsm.business.domain.Tennant;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

//@Configuration
//@EnableJpaRepositories(
//    basePackages = "com.vsm.business.repository",
//    transactionManagerRef = "transcationManager",
//    entityManagerFactoryRef = "entityManager")
//@EnableElasticsearchRepositories("com.vsm.business.repository.search")
//@EnableTransactionManagement
public class DataSourceConfig {

    @Autowired
    private TenantHelper tenantHelper;


    @Bean
    @Primary
    @Autowired
    public DataSource dataSource() {
        DataSourceRouting routingDataSource = new DataSourceRouting();
        routingDataSource.initDatasource(tenantHelper.getAllTenant(), defaultDataSource());
        return routingDataSource;
    }

    public DataSource defaultDataSource() {
        Properties props = new Properties();
        props.setProperty("poolName", "vsmtest_1");
        props.setProperty("autoCommit", "False");
        props.setProperty("driverClassName", "org.postgresql.Driver");
        props.setProperty("jdbcUrl", "jdbc:postgresql://14.162.145.33:5432/vsmtest?currentSchema=vsmtest_1");
        props.setProperty("username", "vsmtest");
        props.setProperty("password", "vsmtest");
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

    @Bean(name = "entityManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
        EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource()).packages("com.vsm.business.domain")
            .build();
    }

    @Bean(name = "transcationManager")
    public JpaTransactionManager transactionManager(
        @Autowired @Qualifier("entityManager") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return new JpaTransactionManager(entityManagerFactoryBean.getObject());
    }
}
