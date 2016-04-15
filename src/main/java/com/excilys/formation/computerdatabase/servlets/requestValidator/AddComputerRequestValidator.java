package com.excilys.formation.computerdatabase.servlets.requestValidator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdatabase.servlets.requestDTO.AddComputerDTO;
import com.excilys.formation.computerdatabase.servlets.requestDTO.RequestDTO;
import com.excilys.formation.computerdatabase.util.StringChecker;

public class AddComputerRequestValidator implements RequestValidator {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(AddComputerRequestValidator.class);
  
  @Override
  public List<String> validatePost(RequestDTO requestDTO, List<String> errors) {
    // TODO : catch cast error ?
    AddComputerDTO dto = (AddComputerDTO) requestDTO;

    if (StringChecker.isNullOrEmpty(dto.getName())) {
      errors.add("computer DTO name was empty");
    }
    if (!StringChecker.isNullOrEmpty(dto.getIntroduced())
        && !StringChecker.isDate(dto.getIntroduced())) {
      errors.add("Dto introdate could not be converted into a date");
    }
    if (!StringChecker.isNullOrEmpty(dto.getDiscontinued())
        && !StringChecker.isDate(dto.getDiscontinued())) {
      errors.add("Dto discodate could not be converted into a date");
    }
    if (!StringChecker.isNullOrEmpty(dto.getCompanyId())
        && !StringChecker.isNumber(dto.getCompanyId())) {
      errors.add("The company id was not a number");
    }
    
    if(!errors.isEmpty()){
      LOGGER.error(errors.toString());
    }
    
    return errors;
  }
}
