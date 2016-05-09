package com.excilys.formation.computerdatabase.config;

public enum SupportedLocale {

  EN_EN("en"), FR_FR("fr");

  private String value;

  SupportedLocale(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  public static boolean contains(String test) {

    for (SupportedLocale l : SupportedLocale.values()) {
      if (l.getValue()
        .equals(test)) {
        return true;
      }
    }

    return false;
  }
}
