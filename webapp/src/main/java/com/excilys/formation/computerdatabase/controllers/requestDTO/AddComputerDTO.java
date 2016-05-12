package com.excilys.formation.computerdatabase.controllers.requestDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.excilys.formation.computerdatabase.util.StringChecker;

public class AddComputerDTO implements IRequestDTO {

  @NotNull(message = "{NotNull.AddCommputerDTO.companyId}")
  @Pattern(message = "{Pattern.AddCommputerDTO.companyId}", regexp = StringChecker.NUMBER_REGEXP)
  private String companyId;

  @NotEmpty(message = "{NotEmpty.AddComputerDTO.name}")
  @Size(min = 1, max = 60, message = "{Size.AddComputerDTO.name}")
  private String name;

  @NotNull
  private String introduced;

  @NotNull
  private String discontinued;

  public AddComputerDTO() {

  }

  public AddComputerDTO(String companyId, String name, String introduced, String discontinued) {
    this.companyId = companyId;
    this.name = name;
    this.introduced = introduced;
    this.discontinued = discontinued;
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