package com.excilys.formation.computerdatabase.config;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);
  
  @Configuration
  @Order(1)
  public static class RestApiConfigurationAdapter extends WebSecurityConfigurerAdapter {
      protected void configure(HttpSecurity http) throws Exception {
          http
              .antMatcher("/api/**")
              .csrf().disable()
              .httpBasic();
      }
  }
  
  @Configuration
  @Order(2)
  public static class WebConfigurationAdapter extends WebSecurityConfigurerAdapter {
    @Resource(name = "UserService")
    private UserDetailsService us;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

      http.authorizeRequests()
        .antMatchers("/resources/**", "/404", "/403")
        .permitAll()
        .antMatchers("/lang")
        .permitAll()
        .antMatchers("/register", "/login")
        .anonymous()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/login")
        .permitAll()
        .and()
        .exceptionHandling()
        .accessDeniedPage("/403");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsServiceBean())
        .passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
      return this.us;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
    }
  }
  
}
