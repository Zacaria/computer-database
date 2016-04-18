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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jolbox.bonecp.BoneCPDataSource;

@EnableWebMvc
@EnableTransactionManagement
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan(basePackages = "com.excilys.formation.computerdatabase")
@PropertySource("classpath:application.properties")
public class SpringRootConfig extends WebMvcConfigurerAdapter {

  @Value("${bonecp.driver}")
  private String driverClass;

  @Value("${bonecp.url}")
  private String jdbcUrl;

  @Value("${bonecp.username}")
  private String username;

  @Value("${bonecp.password}")
  private String password;

  @Value("${bonecp.max-active}")
  private Integer maxConnectionsPerPartition;

  @Value("${bonecp.min-active}")
  private Integer minConnectionsPerPartition;

  @Value("${bonecp.partitions}")
  private Integer partitionCount;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
  }
  
  // Helps converting strings of property file into numbers
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
  public BoneCPDataSource dataSource() {
    BoneCPDataSource dataSource = new BoneCPDataSource();

    dataSource.setDriverClass(driverClass);
    dataSource.setJdbcUrl(jdbcUrl);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    dataSource.setMaxConnectionsPerPartition(maxConnectionsPerPartition);
    dataSource.setMinConnectionsPerPartition(minConnectionsPerPartition);
    dataSource.setPartitionCount(partitionCount);

    return dataSource;
  }
}
