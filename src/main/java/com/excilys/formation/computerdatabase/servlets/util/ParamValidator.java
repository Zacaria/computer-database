package com.excilys.formation.computerdatabase.servlets.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdatabase.util.DateConverter;

/**
 * In order to keep a track of errors, the methods are not static.
 * @author Zacaria
 *
 */
public class ParamValidator {
  private static final Logger LOGGER =
      LoggerFactory.getLogger("com.excilys.formation.computerdatabase");
  private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+$");

  private List<String> errors;

  public ParamValidator() {
    this.errors = new LinkedList<>();
  }

  /**
   * The int value can have default parameter.
   * @param request
   * @param field
   * @param defaultInt
   * @return
   */
  public int getInt(HttpServletRequest request, String field, int defaultInt) {
    int result;

    if (this.isNumber(field, request.getParameter(field))) {
      result = Integer.parseInt(request.getParameter(field));
    } else {
      result = defaultInt;
    }

    return result;
  }

  public Long getLong(HttpServletRequest request, String field) {
    Long result = null;

    if (this.isNumber(field, request.getParameter(field))) {
      result = Long.parseLong(request.getParameter(field));
    }

    return result;
  }

  public List<Long> getLongs(HttpServletRequest request, String field) {
    String serialIds = request.getParameter("selection");
    if (this.isNullOrEmpty(field, serialIds)) {
      return null;
    }

    List<String> stringIds = Arrays.asList(serialIds.split(","));

    List<Long> ids = stringIds.stream().filter(s -> this.isNumber("id", s)).map(Long::parseLong)
        .collect(Collectors.toCollection(ArrayList::new));
    return ids;
  }

  public String getString(HttpServletRequest request, String field, boolean required) {
    String result = request.getParameter(field);
    LOGGER.debug("Get url String of " + field + " : " + result);
    if (required && this.isNullOrEmpty(field, result)) {
      return null;
    }
    return result;
  }

  public LocalDate getDate(HttpServletRequest request, String field) {
    LocalDate result = null;

    if (!request.getParameter(field).isEmpty() && this.isDate(field, request.getParameter(field))) {
      result = DateConverter.stringToDate(request.getParameter(field));
    }

    return result;
  }

  private boolean isNullOrEmpty(String name, String s) {
    if (s == null || s.isEmpty()) {
      this.errors.add("The given parameter \"" + name + "\" was null or empty");
      return true;
    } else {
      return false;
    }
  }

  private boolean isNumber(String name, String s) {
    if (this.isNullOrEmpty(name, s)) {
      return false;
    }

    if (!NUMBER_PATTERN.matcher(s.trim()).matches()) {
      this.errors.add("The given parameter \"" + name + "\" with value \"" + s
          + "\" could not be converted into a number");
      return false;
    }
    return true;
  }

  private boolean isDate(String name, String s) {

    if (DateConverter.stringToDate(s.trim()) == null) {
      this.errors.add("The given parameter \"" + name + "\" with value \"" + s
          + "\" could not be converted into a date");
      return false;
    }
    return true;
  }

  public List<String> getErrors() {
    return errors;
  }

}
