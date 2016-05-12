package com.excilys.formation.computerdatabase.webapp.controllers;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.excilys.formation.computerdatabase.config.SpringRootConfig;
import com.excilys.formation.computerdatabase.webapp.config.SecurityConfig;

public class ServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class[] { SpringRootConfig.class, SecurityConfig.class };
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[] { SpringRootConfig.class };
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] { "/" };
  }

  @Override
  protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
    DispatcherServlet serv = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);

    serv.setThrowExceptionIfNoHandlerFound(true);

    return serv;
  }
}
