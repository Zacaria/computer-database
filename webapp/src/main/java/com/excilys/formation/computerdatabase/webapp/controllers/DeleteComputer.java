package com.excilys.formation.computerdatabase.webapp.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.formation.computerdatabase.controllers.requestDTO.DeleteComputerDTO;
import com.excilys.formation.computerdatabase.controllers.requestMapping.DeleteComputerRequestMapper;
import com.excilys.formation.computerdatabase.controllers.requestValidator.DeleteComputerRequestValidator;
import com.excilys.formation.computerdatabase.service.ComputerService;
import com.excilys.formation.computerdatabase.service.IComputerService;

/**
 * Servlet implementation class deleteComputer
 */
@Controller
@RequestMapping("/deleteComputer")
public class DeleteComputer {
  private static final String ATTR_MESSAGES = "messages";
  private static final String ATTR_SUCCESS = "success";
  private static final String ATTR_ERROR = "errors";
  private static final String SUCCESS_MESSAGE = "DeleteComputer.success";

  @Autowired
  private IComputerService cs;

  @Autowired
  private DeleteComputerRequestValidator validator;

  public DeleteComputer() {
  }

  @RequestMapping(method = RequestMethod.POST)
  protected String doPost(RedirectAttributes attr,
      @ModelAttribute("deleteComputerForm") DeleteComputerDTO dto, BindingResult bindingResult,
      HttpServletRequest request) {

    Map<String, Object> messages = new HashMap<>();

    DeleteComputerRequestMapper mapper = new DeleteComputerRequestMapper();

    dto = (DeleteComputerDTO) mapper.postToDTO(request);

    validator.validate(dto, bindingResult);

    if (!bindingResult.hasErrors()) {
      dto.getIds()
        .stream()
        .forEach(this.cs::delete);
      messages.put(ATTR_SUCCESS, SUCCESS_MESSAGE);
    } else {
      messages.put(ATTR_ERROR, bindingResult.getAllErrors());
    }

    attr.addFlashAttribute(ATTR_MESSAGES, messages);
    return "redirect:computer";
  }
}
