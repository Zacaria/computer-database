package com.excilys.formation.computerdatabase.api.endpoints.dto;

public enum ResponseStatus {
  SUCCESS("200"), NOT_FOUND("404"), ERROR("500");
  
  private String value;

  private ResponseStatus(String value) {
    this.value = value;
  }
  
  public String getValue() {
    return this.value;
  }
}
