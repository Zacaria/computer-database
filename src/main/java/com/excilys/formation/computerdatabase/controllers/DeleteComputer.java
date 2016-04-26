package com.excilys.formation.computerdatabase.controllers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.formation.computerdatabase.controllers.requestDTO.DeleteComputerDTO;
import com.excilys.formation.computerdatabase.controllers.requestDTO.EditComputerDTO;
import com.excilys.formation.computerdatabase.controllers.requestMapping.DeleteComputerRequestMapper;
import com.excilys.formation.computerdatabase.controllers.requestValidator.DeleteComputerRequestValidator;
import com.excilys.formation.computerdatabase.controllers.requestValidator.EditComputerRequestValidator;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.service.ComputerService;
import com.excilys.formation.computerdatabase.service.IService;

/**
 * Servlet implementation class deleteComputer
 */
@Controller
@RequestMapping("/deleteComputer")
public class DeleteComputer {
  private static final Logger LOGGER = LoggerFactory.getLogger(DeleteComputer.class);

  private static final String ATTR_MESSAGES = "messages";
  private static final String ATTR_SUCCESS = "success";
  private static final String ATTR_ERROR = "errors";
  private static final String SUCCESS_MESSAGE = "DeleteComputer.success";

  @Autowired
  private IService<Computer> cs;

  @Autowired
  private DeleteComputerRequestValidator validator;

  public DeleteComputer() {
  }

  @RequestMapping(method = RequestMethod.POST)
  protected String doPost(RedirectAttributes attr,
      @ModelAttribute("deleteComputerForm") DeleteComputerDTO dto,
      BindingResult bindingResult, HttpServletRequest request) {
    LOGGER.debug(dto.toString());

    Map<String, Object> messages = new HashMap<>();

    DeleteComputerRequestMapper mapper = new DeleteComputerRequestMapper();

    dto = (DeleteComputerDTO) mapper.postToDTO(request);

    validator.validate(dto, bindingResult);

    if (!bindingResult.hasErrors()) {
      dto.getIds().stream().forEach(this.cs::delete);
      messages.put(ATTR_SUCCESS, SUCCESS_MESSAGE);
    } else {
      messages.put(ATTR_ERROR, bindingResult.getAllErrors());
    }

    attr.addFlashAttribute(ATTR_MESSAGES, messages);
    return "redirect:dashboard";
  }

  public void setCs(ComputerService cs) {
    this.cs = cs;
  }
}
