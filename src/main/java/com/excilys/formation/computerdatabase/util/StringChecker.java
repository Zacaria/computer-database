package com.excilys.formation.computerdatabase.util;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringChecker {
  private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+$");

  private StringChecker() {
  }

  public static boolean isNullOrEmpty(String s) {
    if (s == null || s.isEmpty()) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean isNumber(String s) {
    if (isNullOrEmpty(s)) {
      return false;
    }

    if (!NUMBER_PATTERN.matcher(s.trim()).matches()) {
      return false;
    }
    return true;
  }
  
  public static boolean isDate(String s) {
    if (DateConverter.stringToDate(s.trim()) == null) {
      return false;
    }
    return true;
  }
}
