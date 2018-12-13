package com.example.demo.globel;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@MapperScan(basePackages = "com.example.demo.dao")
public class DataSourceConfig {
    @Bean(initMethod="init",destroyMethod="close")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() throws SQLException {
        DruidDataSource dataSource = (DruidDataSource) DataSourceBuilder.create().type(DruidDataSource.class).build();
        dataSource.setInitialSize(10);
        dataSource.setMaxActive(25);
        dataSource.setMinIdle(10);
        dataSource.setMaxWait(60000);

        dataSource.setValidationQuery("select 1");
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(2520000);
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(1800);
        dataSource.setLogAbandoned(true);
        dataSource.setFilters("mergeStat");
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager testTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Order(0)
    public SqlSessionFactory testSqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
        return bean.getObject();
    }

}
