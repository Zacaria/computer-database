package com.excilys.formation.computerdatabase.controllers.requestValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
 
import com.excilys.formation.computerdatabase.controllers.requestDTO.EditComputerDTO;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.service.ComputerService;
import com.excilys.formation.computerdatabase.util.DateConverter;
import com.excilys.formation.computerdatabase.util.StringChecker;

@Component
public class EditComputerRequestValidator implements Validator {

  @Autowired
  private ComputerService cs;

  public EditComputerRequestValidator() {

  }

  @Override
  public void validate(Object target, Errors errors) {
    EditComputerDTO dto = (EditComputerDTO) target;

    if (!StringChecker.isNullOrEmpty(dto.getId()) && !StringChecker.isNumber(dto.getId())) {

    } else {
      Computer computerModel = this.cs.get(Long.parseLong(dto.getId()));
      if (computerModel == null) {
        errors.reject("NotFound.Computer");
      }
    }

    if (!StringChecker.isNullOrEmpty(dto.getIntroduced())) {
      if ("fr".equals(LocaleContextHolder.getLocale().toString())){
        dto.setIntroduced(DateConverter.frToEnDate(dto.getIntroduced()));
      }
      
      if (!StringChecker.isDate(dto.getIntroduced())) {
        errors.rejectValue("introduced", "Invalid.Date");
      }
    }
    
    if (!StringChecker.isNullOrEmpty(dto.getDiscontinued())){
      if ("fr".equals(LocaleContextHolder.getLocale().toString())){
        dto.setDiscontinued(DateConverter.frToEnDate(dto.getDiscontinued()));
      }
      if( !StringChecker.isDate(dto.getDiscontinued())) {
        errors.rejectValue("discontinued", "Invalid.Date");
      }
    }
  }

  /**
   * This Validator validates only EditComputerDTO instances
   */
  @Override
  public boolean supports(Class<?> clazz) {
    return EditComputerDTO.class.equals(clazz);
  }
}
