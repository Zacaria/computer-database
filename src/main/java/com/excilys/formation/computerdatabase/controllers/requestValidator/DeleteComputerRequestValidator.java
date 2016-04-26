package com.excilys.formation.computerdatabase.controllers.requestValidator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdatabase.controllers.requestDTO.DeleteComputerDTO;
import com.excilys.formation.computerdatabase.controllers.requestDTO.IRequestDTO;

public class DeleteComputerRequestValidator implements IRequestValidator {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(DeleteComputerRequestValidator.class);

  @Override
  public List<String> validatePost(IRequestDTO requestDTO, List<String> errors) {
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
