package com.excilys.formation.computerdatabase.controllers.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ControllerAdvice
public class ExceptionsHandler {
  private static final String ATTR_ERROR = "errors";

  @ExceptionHandler({ NoHandlerFoundException.class, NotFoundException.class })
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String notFound(NotFoundException e, final Model model, HttpServletRequest request) {

    model.addAttribute(ATTR_ERROR, e);

    return "404";
  }

  @ExceptionHandler(Exception.class)
  public String globalErrorHandler(Exception e, final Model model) throws Exception {

    if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
      throw e;
    }

    model.addAttribute(ATTR_ERROR, e);

    return "500";
  }
}
