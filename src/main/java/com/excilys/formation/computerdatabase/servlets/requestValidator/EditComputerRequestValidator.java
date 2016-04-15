package com.excilys.formation.computerdatabase.servlets.requestValidator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.service.ComputerService;
import com.excilys.formation.computerdatabase.servlets.requestDTO.EditComputerDTO;
import com.excilys.formation.computerdatabase.servlets.requestDTO.IRequestDTO;
import com.excilys.formation.computerdatabase.util.StringChecker;

public class EditComputerRequestValidator implements IRequestValidator {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(EditComputerRequestValidator.class);

  private ComputerService cs;

  public EditComputerRequestValidator() {

  }

  public EditComputerRequestValidator(ComputerService cs) {
    this.cs = cs;
  }

  @Override
  public List<String> validateGet(IRequestDTO requestDTO, List<String> errors) {

    EditComputerDTO dto = (EditComputerDTO) requestDTO;

    if (StringChecker.isNullOrEmpty(dto.getId()) || !StringChecker.isNumber(dto.getId())) {
      errors.add("The given id was not a number");
    } else {
      Computer computerModel = this.cs.get(Long.parseLong(dto.getId()));
      if (computerModel == null) {
        errors.add("The given id has no computer");
      }
    }

    if (!errors.isEmpty()) {
      LOGGER.error(errors.toString());
    }

    return errors;
  }

  @Override
  public List<String> validatePost(IRequestDTO requestDTO, List<String> errors) {
    EditComputerDTO dto = (EditComputerDTO) requestDTO;

    if (!StringChecker.isNullOrEmpty(dto.getId()) && !StringChecker.isNumber(dto.getId())) {
      errors.add("The id was not a number");
    }
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

    if (!errors.isEmpty()) {
      LOGGER.error(errors.toString());
    }

    return errors;
  }

}
