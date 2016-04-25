package com.excilys.formation.computerdatabase.servlets.requestDTO;

public class AddComputerDTO implements IRequestDTO {
  private String companyId;
  private String name;
  private String introduced;
  private String discontinued;
  
  public AddComputerDTO(){
    
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
}