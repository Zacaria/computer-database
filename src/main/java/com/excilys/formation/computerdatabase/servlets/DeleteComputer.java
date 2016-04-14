package com.excilys.formation.computerdatabase.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdatabase.service.ComputerService;
import com.excilys.formation.computerdatabase.servlets.requestDTO.DeleteComputerDTO;
import com.excilys.formation.computerdatabase.servlets.requestMapping.DeleteComputerRequestMapper;
import com.excilys.formation.computerdatabase.servlets.requestValidator.DeleteComputerRequestValidator;

/**
 * Servlet implementation class deleteComputer
 */
@WebServlet("/deleteComputer")
public class DeleteComputer extends HttpServlet {
  private static final Logger LOGGER =
      LoggerFactory.getLogger("com.excilys.formation.computerdatabase");
  private static final long serialVersionUID = 1L;

  private static final String ATTR_MESSAGES = "messages";
  private static final String ATTR_SUCCESS = "success";
  private static final String ATTR_ERROR = "errors";

  private final ComputerService cs;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public DeleteComputer() {
    super();
    this.cs = ComputerService.getInstance();
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
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
}
