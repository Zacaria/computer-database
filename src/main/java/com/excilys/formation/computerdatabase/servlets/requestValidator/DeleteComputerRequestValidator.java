package com.excilys.formation.computerdatabase.servlets.requestValidator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdatabase.servlets.requestDTO.DeleteComputerDTO;
import com.excilys.formation.computerdatabase.servlets.requestDTO.RequestDTO;

public class DeleteComputerRequestValidator implements RequestValidator {
  private static final Logger LOGGER =
      LoggerFactory.getLogger("com.excilys.formation.computerdatabase");

  @Override
  public List<String> validatePost(RequestDTO requestDTO, List<String> errors) {
    DeleteComputerDTO dto = (DeleteComputerDTO) requestDTO;

    if (dto.getIds() == null || dto.getIds().isEmpty()) {
      errors.add("There was no id to delete");
    }

    if (!errors.isEmpty()) {
      LOGGER.error(errors.toString());
    }

    return errors;
  }

}
