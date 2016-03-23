package com.excilys.formation.computerdatabase.model;

/**
 * This list corresponds to aliases in the companyDAO SQL query
 * @author Zacaria
 *
 */
public enum ComputerFields {
  ID("computer_id"), NAME("computer_name"), INTRODUCED("introduced"), DISCONTINUED(
      "discontinued"), COMPANY_ID("company_id"), COMPANY_NAME("company_name");

  private String value;

  ComputerFields(String s) {
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
