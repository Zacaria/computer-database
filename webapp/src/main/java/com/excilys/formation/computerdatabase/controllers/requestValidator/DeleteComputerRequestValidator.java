package com.excilys.formation.computerdatabase.controllers.requestValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.formation.computerdatabase.controllers.requestDTO.DeleteComputerDTO;

@Component
public class DeleteComputerRequestValidator implements Validator {

  @Override
  public void validate(Object target, Errors errors) {
    DeleteComputerDTO dto = (DeleteComputerDTO) target;

    if (dto.getIds() == null || dto.getIds()
      .isEmpty()) {
      errors.reject("DeleteComputer.Id.Empty", "There was no id to delete");
    }
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return DeleteComputerDTO.class.equals(clazz);
  }

}
