package com.excilys.formation.computerdatabase.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = "com.excilys.formation.computerdatabase.config, "
    + "com.excilys.formation.computerdatabase.controllers, "
    + "com.excilys.formation.computerdatabase.service,"
    + "com.excilys.formation.computerdatabase.persistence ")
@PropertySource("classpath:application.properties")
public class SpringRootConfig {

  @Value("${hikari.driver}")
  private String driverClass;

  @Value("${hikari.url}")
  private String jdbcUrl;

  @Value("${hikari.username}")
  private String username;

  @Value("${hikari.password}")
  private String password;

  @Value("${hikari.maxSize}")
  private Integer maxSize;

  @Value("${hikari.idleTimeout}")
  private Long idleTimeout;

  @Value("${hikari.poolName}")
  private String poolName;

  // Helps converting strings of property file into numbers for the properties
  // file
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  public DataSourceTransactionManager transactionManager() {
    DataSourceTransactionManager manager = new DataSourceTransactionManager();

    manager.setDataSource(dataSource());

    return manager;
  }

  @Bean
  public HikariDataSource dataSource() {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setPoolName(poolName);
    dataSource.setDataSourceClassName(driverClass);
    dataSource.setMaximumPoolSize(maxSize);
    dataSource.setIdleTimeout(idleTimeout);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    dataSource.setJdbcUrl(jdbcUrl);
    dataSource.addDataSourceProperty("zeroDateTimeBehavior", "convertToNull");

    return dataSource;
  }

}
