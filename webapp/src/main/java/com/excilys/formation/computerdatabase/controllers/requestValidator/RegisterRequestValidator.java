package com.excilys.formation.computerdatabase.controllers.requestValidator;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.formation.computerdatabase.controllers.requestDTO.RegisterDTO;
import com.excilys.formation.computerdatabase.service.UserService;

@Component
public class RegisterRequestValidator implements Validator {
  
  @Resource(name = "UserService")
  private UserService us;

  @Override
  public void validate(Object target, Errors errors) {
    RegisterDTO dto = (RegisterDTO) target;
    
    if (us.get(dto.getUsername()) != null) {
      errors.reject("Duplicate.Register.user");
    }
  }
  
  @Override
  public boolean supports(Class<?> clazz) {
    return RegisterDTO.class.equals(clazz);
  }

}
