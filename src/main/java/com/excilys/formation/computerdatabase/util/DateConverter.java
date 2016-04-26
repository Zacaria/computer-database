package com.excilys.formation.computerdatabase.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateConverter {
  private static final Logger LOGGER = LoggerFactory.getLogger(DateConverter.class);

  /*
   * Matches bisextiles dates Format : yyyy-MM-dd Still needs to be verfied to
   * Check if it is inside the mysql timestamp range
   */
  public static final String MYSQL_TIMESTAMP_REGEX =
      "^(?:(?:(?:(?:(?:[1-9]\\d)(?:0[48]|[2468][048]|[13579][26])|(?:(?:[2468][048]|[13579][26])00))(-)(?:0?2\\1(?:29)))|(?:(?:[1-9]\\d{3})(-)(?:(?:(?:0?[13578]|1[02])\\2(?:31))|(?:(?:0?[13-9]|1[0-2])\\2(?:29|30))|(?:(?:0?[1-9])|(?:1[0-2]))\\2(?:0?[1-9]|1\\d|2[0-8])))))$";

  /**
   * Matches a date or blank string
   */
  public static final String MYSQL_TIMESTAMP_REGEX_OR_EMPTY = MYSQL_TIMESTAMP_REGEX + "|";

  private static final Pattern MYSQL_TIMESTAMP_PATTERN = Pattern.compile(MYSQL_TIMESTAMP_REGEX);
  private static final Pattern MYSQL_TIMESTAMP_PATTERN_OR_EMPTY =
      Pattern.compile(MYSQL_TIMESTAMP_REGEX_OR_EMPTY);

  private static final LocalDate TIMESTAMP_BEGIN = LocalDate.of(1970, 01, 01);
  private static final LocalDate TIMESTAMP_END = LocalDate.of(2038, 01, 19);

  private DateConverter() {

  }

  public static LocalDate stringToDate(String s) {
    if (!MYSQL_TIMESTAMP_PATTERN.matcher(s).matches()) {
      return null;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate date = LocalDate.parse(s, formatter);

    if (date.isAfter(TIMESTAMP_BEGIN) && date.isBefore(TIMESTAMP_END)) {
      return date;
    } else {
      LOGGER.error("Parsed date was out of timestamp range : " + s);
      return null;
    }
  }

  public static LocalDate stringToFrDate(String s) {
    LOGGER.debug("stringToFrDate : " + s);
    return stringToDate(frToEnDate(s));
  }

  public static String frToEnDate(String s) {
    // Test if the string has at least the right length.
    if (s.length() == 10) {
      return s.substring(6) + "-" + s.substring(3, 5) + "-" + s.substring(0, 2);
    }
    return s;
  }

  public static String enToFrDate(String s) {
    // Test if the string has at least the right length.
    if (s.length() == 10) {
      return s.substring(8) + "-" + s.substring(5, 7) + "-" + s.substring(0, 4);
    }
    return null;
  }
}
