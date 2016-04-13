package com.excilys.formation.computerdatabase.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdatabase.servlets.requestDTO.DashboardDTO;
import com.excilys.formation.computerdatabase.servlets.requestMapping.DashboardRequestMapper;

/**
 * Servlet implementation class Dashboard.
 */
@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
  private static final Logger LOGGER =
      LoggerFactory.getLogger("com.excilys.formation.computerdatabase");
  private static final long serialVersionUID = 1L;

  private static final String ATTR_RESULT = "data";
  private static final String ATTR_DTO = "dto";
  private static final String ATTR_MESSAGES = "messages";
//  private static final String ATTR_ERROR = "errors";

  /**
   * @see HttpServlet#HttpServlet()
   */
  public Dashboard() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @SuppressWarnings("unchecked")
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    LOGGER.info("access to : " + request.getRequestURL() + " " + request.getQueryString());

    Map<String, Object> result = new HashMap<>();
    Map<String, Object> messages = new HashMap<>();
    
    DashboardRequestMapper mapper = new DashboardRequestMapper();
    
    DashboardDTO dto = (DashboardDTO) mapper.toDTO(request);
       
    HttpSession session = request.getSession();

    if (session.getAttribute(ATTR_MESSAGES) != null) {
      messages.putAll((Map<? extends String, ? extends Object>) session.getAttribute(ATTR_MESSAGES));
      session.setAttribute(ATTR_MESSAGES, null);
    }
    
    result.put(ATTR_DTO, dto);
    result.put(ATTR_MESSAGES, messages);
    
    request.setAttribute(ATTR_RESULT, result);

    RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard.jsp");
    view.forward(request, response);
  }
}
