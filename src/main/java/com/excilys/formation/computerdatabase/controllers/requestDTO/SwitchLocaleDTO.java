package com.excilys.formation.computerdatabase.controllers.requestDTO;

public class SwitchLocaleDTO {
  private String locale;
  private String code;
  
  public SwitchLocaleDTO(String locale, String code) {
    this.locale = locale;
    this.code = code;
  }
  public String getLocale() {
    return locale;
  }
  public String getCode() {
    return code;
  }  
}
