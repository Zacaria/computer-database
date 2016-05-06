package com.excilys.formation.computerdatabase.controllers;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.formation.computerdatabase.controllers.requestDTO.RegisterDTO;
import com.excilys.formation.computerdatabase.controllers.requestMapping.RegisterRequestMapper;
import com.excilys.formation.computerdatabase.controllers.requestValidator.RegisterRequestValidator;
import com.excilys.formation.computerdatabase.service.UserService;

@Controller
@RequestMapping("/register")
public class Register {
  private static final Logger LOGGER = LoggerFactory.getLogger(Register.class);

  private static final String MODEL_ATTRIBUTE = "registerForm";
  private static final String ATTR_SUCCESS = "success";

  @Resource(name = "UserService")
  private UserService us;

  @Autowired
  private RegisterRequestValidator validator;

  public Register() {

  }

  @RequestMapping(method = RequestMethod.GET)
  protected String doGet(final Model model, HttpServletRequest request,
      HttpServletResponse response) {

    model.addAttribute(MODEL_ATTRIBUTE, new RegisterDTO());

    return "register";
  }

  @RequestMapping(method = RequestMethod.POST)
  protected String doPost(@ModelAttribute(MODEL_ATTRIBUTE) @Validated RegisterDTO dto,
      BindingResult bindingResult, final Model model) {

    RegisterRequestMapper mapper = new RegisterRequestMapper();

    validator.validate(dto, bindingResult);

    if (bindingResult.hasErrors()) {
      LOGGER.error(bindingResult.getAllErrors()
        .toString());
      model.addAttribute(ATTR_SUCCESS, false);
    } else {
      us.create(mapper.fromDTO(dto));
      model.addAttribute(MODEL_ATTRIBUTE, new RegisterDTO());
      model.addAttribute(ATTR_SUCCESS, true);
    }

    return "register";
  }
}
