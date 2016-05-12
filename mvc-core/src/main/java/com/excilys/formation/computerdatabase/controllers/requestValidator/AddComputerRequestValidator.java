package com.excilys.formation.computerdatabase.controllers.requestValidator;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.formation.computerdatabase.controllers.requestDTO.AddComputerDTO;
import com.excilys.formation.computerdatabase.util.DateConverter;
import com.excilys.formation.computerdatabase.util.StringChecker;

@Component
public class AddComputerRequestValidator implements Validator {

  @Override
  public void validate(Object target, Errors errors) {
    AddComputerDTO dto = (AddComputerDTO) target;

    if (!StringChecker.isNullOrEmpty(dto.getIntroduced())) {
      if ("fr".equals(LocaleContextHolder.getLocale()
        .toString())) {
        dto.setIntroduced(DateConverter.frToEnDate(dto.getIntroduced()));
      }

      if (!StringChecker.isDate(dto.getIntroduced())) {
        errors.rejectValue("introduced", "Invalid.Date");
      }
    }

    if (!StringChecker.isNullOrEmpty(dto.getDiscontinued())) {
      if ("fr".equals(LocaleContextHolder.getLocale()
        .toString())) {
        dto.setDiscontinued(DateConverter.frToEnDate(dto.getDiscontinued()));
      }
      if (!StringChecker.isDate(dto.getDiscontinued())) {
        errors.rejectValue("discontinued", "Invalid.Date");
      }
    }
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return AddComputerDTO.class.equals(clazz);
  }
}
