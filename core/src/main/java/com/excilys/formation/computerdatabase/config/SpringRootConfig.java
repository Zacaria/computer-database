package com.excilys.formation.computerdatabase.config;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = "com.excilys.formation.computerdatabase.config, "
    + "com.excilys.formation.computerdatabase.webapp, "
    + "com.excilys.formation.computerdatabase.controllers, "
    + "com.excilys.formation.computerdatabase.api,"
    + "com.excilys.formation.computerdatabase.service,"
    + "com.excilys.formation.computerdatabase.persistence ")
@PropertySource("classpath:application.properties")
public class SpringRootConfig {

  private final static Logger LOGGER = LoggerFactory.getLogger(SpringRootConfig.class);

  {
    LOGGER.debug("init");
  }

  @Value("${hbm.hbm2ddl.auto}")
  private String hbm2ddl;

  @Value("${hbm.dialect}")
  private String dialect;

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

  // Helps converting strings of property file into numbers.
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  @Autowired
  public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
    HibernateTransactionManager txManager = new HibernateTransactionManager();
    txManager.setSessionFactory(sessionFactory);

    return txManager;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    sessionFactory
      .setPackagesToScan(new String[] { "com.excilys.formation.computerdatabase.model" });
    sessionFactory.setHibernateProperties(hibernateProperties());

    return sessionFactory;
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
    dataSource.addDataSourceProperty("url", jdbcUrl);

    return dataSource;
  }

  public Properties hibernateProperties() {
    return new Properties() {
      {
        setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
        setProperty("hibernate.dialect", dialect);
        setProperty("hibernate.globally_quoted_identifiers", "true");
        setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
        setProperty("hibernate.cache.use_second_level_cache", "true");
        setProperty("hibernate.cache.use_query_cache", "true");
      }
    };
  }
}
