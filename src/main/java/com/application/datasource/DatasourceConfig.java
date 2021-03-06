//package com.application.datasource;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DatasourceConfig {
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.h2")
//    public DataSource getDatasourceConfig() {
//        return DataSourceBuilder.create().build();
//    }
////    @Bean(name = "h2DataSource")
////    @ConfigurationProperties(prefix = "spring.datasource.h2")
////    public DataSource h2DataSource() {
////        return new DataSource(new EmbeddedDataSourceConfiguration());
////    }
//}
