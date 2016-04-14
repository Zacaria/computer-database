package com.excilys.formation.computerdatabase.servlets.requestDTO;

import com.excilys.formation.computerdatabase.dataBinders.dto.ComputerDTO;
import com.excilys.formation.computerdatabase.dataBinders.dto.PageDTO;
import com.excilys.formation.computerdatabase.model.Company;

public class EditComputerDTO extends RequestDTO {

  private String id;
  private String companyId;
  private String name;
  private String introduced;
  private String discontinued;

  private PageDTO<Company> companies;
  private ComputerDTO computer;

  public EditComputerDTO() {

  }

  public EditComputerDTO(String id) {
    this.id = id;
  }

  public EditComputerDTO(String id, String companyId, String name, String introduced,
      String discontinued) {
    this.id = id;
    this.companyId = companyId;
    this.name = name;
    this.introduced = introduced;
    this.discontinued = discontinued;
  }

  public String getId() {
    return id;
  }

  public PageDTO<Company> getCompanies() {
    return companies;
  }

  public void setCompanies(PageDTO<Company> companies) {
    this.companies = companies;
  }

  public ComputerDTO getComputer() {
    return computer;
  }

  public void setComputer(ComputerDTO computer) {
    this.computer = computer;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getName() {
    return name;
  }

  public String getIntroduced() {
    return introduced;
  }

  public String getDiscontinued() {
    return discontinued;
  }
}
