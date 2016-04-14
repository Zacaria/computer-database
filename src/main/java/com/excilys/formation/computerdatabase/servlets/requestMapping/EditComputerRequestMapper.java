package com.excilys.formation.computerdatabase.servlets.requestMapping;

import javax.servlet.http.HttpServletRequest;

import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.servlets.requestDTO.EditComputerDTO;
import com.excilys.formation.computerdatabase.servlets.requestDTO.RequestDTO;

public class EditComputerRequestMapper implements RequestMapper<Computer>{
  private static final String COMPANY_ID_PARAM = "companyId";
  private static final String COMPUTER_ID_PARAM = "id";
  private static final String COMPUTER_NAME_PARAM = "name";
  private static final String COMPUTER_INTRODUCED_PARAM = "introduced";
  private static final String COMPUTER_DISCONTINUED_PARAM = "discontinued";
  
  @Override
  public RequestDTO getToDTO(HttpServletRequest request) {
    String id = request.getParameter(COMPUTER_ID_PARAM);
    
    return new EditComputerDTO(id);
  }
  
  @Override
  public RequestDTO postToDTO(HttpServletRequest request) {
    String id = request.getParameter(COMPUTER_ID_PARAM);
    String companyId = request.getParameter(COMPANY_ID_PARAM);
    String name = request.getParameter(COMPUTER_NAME_PARAM);
    String introduced = request.getParameter(COMPUTER_INTRODUCED_PARAM);
    String discontinued = request.getParameter(COMPUTER_DISCONTINUED_PARAM);
    
    return new EditComputerDTO(id, companyId, name, introduced, discontinued);
  }
  
  @Override
  public Computer fromDTO(RequestDTO requestDTO) {
    EditComputerDTO dto = (EditComputerDTO) requestDTO;
    
    // Safe strings with prepared queries.
    
    return Computer.builder(dto.getName())
        .id(Long.parseLong(dto.getId()))
        .introduced(dto.getIntroduced())
        .discontinued(dto.getDiscontinued())
        .company(Company.builder(Long.parseLong(dto.getCompanyId())).build())
        .build();
  }
}
