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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.formation.computerdatabase.controllers.requestDTO.DeleteComputerDTO;
import com.excilys.formation.computerdatabase.controllers.requestMapping.DeleteComputerRequestMapper;
import com.excilys.formation.computerdatabase.controllers.requestValidator.DeleteComputerRequestValidator;
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

  @Autowired
  private IService<Computer> cs;

  public DeleteComputer() {
  }

  @RequestMapping(method = RequestMethod.POST)
  protected String doPost(RedirectAttributes attr, HttpServletRequest request) {
    LOGGER.info(request.getMethod() + " access to : " + request.getRequestURL() + " "
        + request.getQueryString());

    Map<String, Object> messages = new HashMap<>();
    boolean success = false;

    DeleteComputerRequestMapper mapper = new DeleteComputerRequestMapper();

    DeleteComputerDTO dto = (DeleteComputerDTO) mapper.postToDTO(request);

    List<String> errors = new LinkedList<>();
    // validate the dto
    errors = new DeleteComputerRequestValidator().validatePost(dto, errors);

    if (errors.isEmpty()) {
      dto.getIds().stream().forEach(this.cs::delete);
      success = true;
    }

    HttpSession session = request.getSession();

    messages.put(ATTR_ERROR, errors);
    messages.put(ATTR_SUCCESS, success);

    attr.addFlashAttribute(ATTR_MESSAGES, messages);
    return "dashboard";
  }

  public void setCs(ComputerService cs) {
    this.cs = cs;
  }
}
