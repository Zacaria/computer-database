package com.excilys.formation.computerdatabase.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.formation.computerdatabase.service.ComputerService;
import com.excilys.formation.computerdatabase.servlets.requestDTO.DashboardDTO;
import com.excilys.formation.computerdatabase.servlets.requestMapping.DashboardRequestMapper;

/**
 * Servlet implementation class Dashboard.
 */
@WebServlet("/dashboard")
@Controller
public class Dashboard extends HttpServlet {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(Dashboard.class);
  private static final long serialVersionUID = 1L;

  private static final String ATTR_RESULT = "data";
  private static final String ATTR_DTO = "dto";
  private static final String ATTR_MESSAGES = "messages";

  @Autowired
  private ComputerService cs;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public Dashboard() {
    super();
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    WebApplicationContext springContext =
        WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
    AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
    beanFactory.autowireBean(this);
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

    DashboardRequestMapper mapper = new DashboardRequestMapper(this.cs);

    DashboardDTO dto = (DashboardDTO) mapper.getToDTO(request);

    HttpSession session = request.getSession();

    if (session.getAttribute(ATTR_MESSAGES) != null) {
      messages
          .putAll((Map<? extends String, ? extends Object>) session.getAttribute(ATTR_MESSAGES));
      session.setAttribute(ATTR_MESSAGES, null);
    }

    result.put(ATTR_DTO, dto);
    result.put(ATTR_MESSAGES, messages);

    request.setAttribute(ATTR_RESULT, result);

    RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard.jsp");
    view.forward(request, response);
  }

  public void setCs(ComputerService cs) {
    this.cs = cs;
  }
}
