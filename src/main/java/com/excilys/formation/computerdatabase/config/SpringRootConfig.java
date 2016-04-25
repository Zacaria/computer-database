package com.excilys.formation.computerdatabase.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.jolbox.bonecp.BoneCPDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@EnableWebMvc
@EnableTransactionManagement
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = "com.excilys.formation.computerdatabase")
@PropertySource("classpath:application.properties")
public class SpringRootConfig extends WebMvcConfigurerAdapter {

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

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
  }

  @Bean
  public InternalResourceViewResolver viewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setViewClass(JstlView.class);
    viewResolver.setPrefix("/WEB-INF/views/");
    viewResolver.setSuffix(".jsp");
    return viewResolver;
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
  public HikariDataSource dataSource() {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setPoolName(poolName);
    dataSource.setDataSourceClassName(driverClass);
    dataSource.setMaximumPoolSize(maxSize);
    dataSource.setIdleTimeout(idleTimeout);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    dataSource.setJdbcUrl(jdbcUrl);

    return dataSource;
  }

  @Bean
  public ReloadableResourceBundleMessageSource messageSource() {
    ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();

    source.setDefaultEncoding("UTF-8");
    source.setBasenames("classpath:locale/messages", "classpath:locale/pages");

    return source;
  }

  @Bean
  public LocaleResolver localeResolver() {
    CookieLocaleResolver resolver = new CookieLocaleResolver();
    resolver.setDefaultLocale(new Locale("en"));
    resolver.setCookieName("localeCookie");
    resolver.setCookieMaxAge(4800);

    return resolver;
  }

  @Bean
  public LocalValidatorFactoryBean validator() {
    LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

    validator.setValidationMessageSource(messageSource());

    return validator;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
    localeInterceptor.setParamName("lang");
    registry.addInterceptor(localeInterceptor);

    HttpRequestInterceptor httpInterceptor = new HttpRequestInterceptor();
    registry.addInterceptor(httpInterceptor);
  }

  @Override
  public Validator getValidator() {
    return validator();
  }
}
