package com.excilys.formation.computerdatabase.servlets.requestMapping;

import javax.servlet.http.HttpServletRequest;

import com.excilys.formation.computerdatabase.dataBinders.dto.ComputerDTO;
import com.excilys.formation.computerdatabase.dataBinders.dto.DTO;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.servlets.requestDTO.AddComputerDTO;
import com.excilys.formation.computerdatabase.servlets.requestDTO.RequestDTO;

public class AddComputerRequestMapper implements RequestMapper {

  private static final String COMPANY_ID_PARAM = "companyId";
  private static final String COMPUTER_NAME_PARAM = "name";
  private static final String COMPUTER_INTRODUCED_PARAM = "introduced";
  private static final String COMPUTER_DISCONTINUED_PARAM = "discontinued";

  @Override
  public RequestDTO toDTO(HttpServletRequest request) {

    String companyId = request.getParameter(COMPANY_ID_PARAM);
    String name = request.getParameter(COMPUTER_NAME_PARAM);
    String introduced = request.getParameter(COMPUTER_INTRODUCED_PARAM);
    String discontinued = request.getParameter(COMPUTER_DISCONTINUED_PARAM);
    
    return new AddComputerDTO(companyId, name, introduced, discontinued);
  }

  @Override
  public Computer fromDTO(RequestDTO requestDTO) {
    AddComputerDTO dto = (AddComputerDTO) requestDTO;

    return Computer.builder(dto.getName())
        .introduced(dto.getIntroduced())
        .discontinued(dto.getDiscontinued())
        .company(Company.builder(Long.parseLong(dto.getCompanyId())).build())
        .build();
  }
}
