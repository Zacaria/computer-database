package com.excilys.formation.computerdatabase.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.formation.computerdatabase.service.ComputerService;
import com.excilys.formation.computerdatabase.servlets.requestDTO.DeleteComputerDTO;
import com.excilys.formation.computerdatabase.servlets.requestMapping.DeleteComputerRequestMapper;
import com.excilys.formation.computerdatabase.servlets.requestValidator.DeleteComputerRequestValidator;

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
  private ComputerService cs;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public DeleteComputer() {
    super();
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  @RequestMapping(method = RequestMethod.POST)
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
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

    session.setAttribute(ATTR_MESSAGES, messages);
    response.sendRedirect("dashboard");
  }

  public void setCs(ComputerService cs) {
    this.cs = cs;
  }
}
