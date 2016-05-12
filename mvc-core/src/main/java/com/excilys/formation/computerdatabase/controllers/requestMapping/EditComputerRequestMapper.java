package com.excilys.formation.computerdatabase.controllers.requestMapping;

import javax.servlet.http.HttpServletRequest;

import com.excilys.formation.computerdatabase.controllers.requestDTO.EditComputerDTO;
import com.excilys.formation.computerdatabase.controllers.requestDTO.IRequestDTO;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Computer;

public class EditComputerRequestMapper implements IRequestMapper<Computer> {
  private static final String COMPANY_ID_PARAM = "companyId";
  private static final String COMPUTER_ID_PARAM = "id";
  private static final String COMPUTER_NAME_PARAM = "name";
  private static final String COMPUTER_INTRODUCED_PARAM = "introduced";
  private static final String COMPUTER_DISCONTINUED_PARAM = "discontinued";

  @Override
  public IRequestDTO getToDTO(HttpServletRequest request) {
    String id = request.getParameter(COMPUTER_ID_PARAM);

    return new EditComputerDTO(id);
  }

  @Override
  public IRequestDTO postToDTO(HttpServletRequest request) {
    String id = request.getParameter(COMPUTER_ID_PARAM);
    String companyId = request.getParameter(COMPANY_ID_PARAM);
    String name = request.getParameter(COMPUTER_NAME_PARAM);
    String introduced = request.getParameter(COMPUTER_INTRODUCED_PARAM);
    String discontinued = request.getParameter(COMPUTER_DISCONTINUED_PARAM);

    return new EditComputerDTO(id, companyId, name, introduced, discontinued);
  }

  @Override
  public Computer fromDTO(IRequestDTO requestDTO) {
    EditComputerDTO dto = (EditComputerDTO) requestDTO;

    // Safe strings with prepared queries.

    return Computer.builder(dto.getName())
      .id(Long.parseLong(dto.getId()))
      .introduced(dto.getIntroduced())
      .discontinued(dto.getDiscontinued())
      .company(Company.builder(Long.parseLong(dto.getCompanyId()))
        .build())
      .build();
  }
}
