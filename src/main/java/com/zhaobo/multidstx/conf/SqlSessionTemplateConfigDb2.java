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
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.zhaobo.multidstx.mapper.db2", sqlSessionTemplateRef = "template2")
public class SqlSessionTemplateConfigDb2 {
    @Bean
    public DataSource ds2(@Value("${ds2.url}") String url,
                          @Value("${ds2.username}") String username,
                          @Value("${ds2.password}") String password,
                          @Value("${ds2.driver-class-name}") String driver) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory factory2(DataSource ds2) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(ds2);
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate template2(SqlSessionFactory factory2){
        return new SqlSessionTemplate(factory2);
    }

    @Bean
    public PlatformTransactionManager transactionManager2(@Qualifier("ds2") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

}
