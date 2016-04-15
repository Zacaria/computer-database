package com.excilys.formation.computerdatabase.util;

import java.util.regex.Pattern;

public class StringChecker {
  // Validates positive integers
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
