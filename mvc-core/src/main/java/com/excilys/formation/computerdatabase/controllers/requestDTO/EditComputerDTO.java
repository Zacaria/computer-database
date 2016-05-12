package com.excilys.formation.computerdatabase.controllers.requestDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.excilys.formation.computerdatabase.dataBinders.dto.ComputerDTO;
import com.excilys.formation.computerdatabase.util.PageDTO;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.util.StringChecker;

public class EditComputerDTO implements IRequestDTO {

  @NotNull(message = "{NotNull.EditCommputerDTO.id}")
  @Pattern(message = "{Pattern.EditCommputerDTO.id}", regexp = StringChecker.NUMBER_REGEXP)
  private String id;

  @NotNull(message = "{NotNull.EditCommputerDTO.companyId}")
  @Pattern(message = "{Pattern.EditCommputerDTO.companyId}", regexp = StringChecker.NUMBER_REGEXP)
  private String companyId;

  @NotNull(message = "{NotEmpty.EditComputerDTO.name}")
  @Size(min = 1, max = 60, message = "{Size.EditComputerDTO.name}")
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

  public void setId(String id) {
    this.id = id;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setIntroduced(String introduced) {
    this.introduced = introduced;
  }

  public void setDiscontinued(String discontinued) {
    this.discontinued = discontinued;
  }
}
