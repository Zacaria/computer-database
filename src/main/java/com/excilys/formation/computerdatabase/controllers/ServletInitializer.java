package com.excilys.formation.computerdatabase.controllers;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.excilys.formation.computerdatabase.config.SpringRootConfig;

public class ServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class[] { SpringRootConfig.class };
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
     return new Class[] { SpringRootConfig.class };
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] { "/" };
  }
}
