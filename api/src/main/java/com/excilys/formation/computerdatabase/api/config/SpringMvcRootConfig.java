package com.excilys.formation.computerdatabase.api.config;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
public class SpringMvcRootConfig extends WebMvcConfigurerAdapter {

  private final static Logger LOGGER = LoggerFactory.getLogger(SpringMvcRootConfig.class);

  {
    LOGGER.debug("init-mvc");
  }

  @Bean
  public LocalValidatorFactoryBean validator() {
    LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

    validator.setValidationMessageSource(messageSource());

    return validator;
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
