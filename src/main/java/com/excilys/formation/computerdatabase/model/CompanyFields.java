package com.excilys.formation.computerdatabase.model;

public enum CompanyFields {
  ID("id"), ID_ALIAS("company_id"), NAME("name"), NAME_ALIAS("company_name");

  private String value;

  private CompanyFields(String s) {
    this.value = s;
  }

  public String getValue() {
    return this.value;
  }

  public static boolean contains(String test) {

    for (ComputerFields c : ComputerFields.values()) {
      if (c.getValue().equals(test)) {
        return true;
      }
    }

    return false;
  }
}
