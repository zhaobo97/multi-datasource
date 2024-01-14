package com.zhaobo.multidstx.conf;


import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.zhaobo.multidstx.mapper.db1", sqlSessionTemplateRef = "template1")
public class SqlSessionTemplateConfigDb1 {

    @Bean
    public DataSource ds1(@Value("${ds1.url}") String url,
                          @Value("${ds1.username}") String username,
                          @Value("${ds1.password}") String password,
                          @Value("${ds1.driver-class-name}") String driver) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory factory1(DataSource ds1) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(ds1);
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate template1(SqlSessionFactory factory1){
        return new SqlSessionTemplate(factory1);
    }

    @Bean
    public PlatformTransactionManager transactionManager1(@Qualifier("ds1") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
