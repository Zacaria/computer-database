package com.excilys.formation.computerdatabase.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.formation.computerdatabase.dataBinders.dto.CompanyDTO;
import com.excilys.formation.computerdatabase.dataBinders.dto.PageDTO;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.service.CompanyService;
import com.excilys.formation.computerdatabase.service.ComputerService;
import com.excilys.formation.computerdatabase.servlets.requestDTO.AddComputerDTO;
import com.excilys.formation.computerdatabase.servlets.requestMapping.AddComputerRequestMapper;
import com.excilys.formation.computerdatabase.servlets.requestValidator.AddComputerRequestValidator;
import com.excilys.formation.computerdatabase.ui.Pager;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/addComputer")
@Controller
public class AddComputer extends HttpServlet {
  private static final Logger LOGGER =
      LoggerFactory.getLogger("com.excilys.formation.computerdatabase");
  private static final long serialVersionUID = 1L;

  private static final String ATTR_RESULT = "data";
  private static final String ATTR_MESSAGES = "messages";
  private static final String ATTR_SUCCESS = "success";
  private static final String ATTR_ERROR = "errors";
  private static final String ATTR_COMPANIES = "companies";

  @Autowired
  private ComputerService cs;

  @Autowired
  private CompanyService es;  

  private Pager<Company> pager;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public AddComputer() {
    super();
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    WebApplicationContext springContext =
        WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
    AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
    beanFactory.autowireBean(this);
    
    this.pager = new Pager<Company>(this.es.count(), (options) -> this.es.get(options),
        company -> new CompanyDTO(company));

    // FIXME : This is ugly and hard code,
    // I am considering that there will never be more than 100 companies.
    this.pager.setRange(100);
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    LOGGER.info(request.getMethod() + " access to : " + request.getRequestURL() + " "
        + request.getQueryString());

    Map<String, Object> result = new HashMap<>();

    PageDTO<Company> companies = this.pager.getPage();

    result.put(ATTR_COMPANIES, companies);

    request.setAttribute(ATTR_RESULT, result);

    RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/addComputer.jsp");

    view.forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    LOGGER.info(request.getMethod() + " access to : " + request.getRequestURL() + " "
        + request.getQueryString());

    Map<String, Object> messages = new HashMap<>();
    boolean success = false;

    AddComputerRequestMapper mapper = new AddComputerRequestMapper();

    // map request into a DTO.
    AddComputerDTO dto = (AddComputerDTO) mapper.postToDTO(request);

    List<String> errors = new LinkedList<>();
    // validate the dto.
    errors = new AddComputerRequestValidator().validatePost(dto, errors);

    // check errors.
    if (errors.isEmpty()) {
      cs.create(mapper.fromDTO(dto));
      success = true;
    }

    // send results.
    messages.put(ATTR_ERROR, errors);
    messages.put(ATTR_SUCCESS, success);

    request.setAttribute(ATTR_MESSAGES, messages);

    doGet(request, response);
  }
  
  public void setCs(ComputerService cs) {
    this.cs = cs;
  }
  
  public void setEs(CompanyService es) {
    this.es = es;
  }
}
