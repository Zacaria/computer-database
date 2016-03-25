package com.excilys.formation.computerdatabase.servlets;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdatabase.dataBinders.dto.ComputerDTO;
import com.excilys.formation.computerdatabase.dataBinders.dto.PageDTO;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.model.ComputerFields;
import com.excilys.formation.computerdatabase.model.SelectOptions;
import com.excilys.formation.computerdatabase.service.ComputerService;
import com.excilys.formation.computerdatabase.servlets.util.ParamValidator;
import com.excilys.formation.computerdatabase.ui.Pager;

/**
 * Servlet implementation class Dashboard.
 */
@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
  private static final Logger LOGGER =
      LoggerFactory.getLogger("com.excilys.formation.computerdatabase");
  private static final long serialVersionUID = 1L;

  private static final String PAGE_PARAM = "p";
  private static final String RANGE_PARAM = "r";
  private static final String COLUMN_PARAM = "col";
  private static final String DIR_PARAM = "asc";
  private static final String SEARCH_PARAM = "s";
  private static final int DEFAULT_PAGE = 1;
  private static final int DEFAULT_RANGE = 10;

  /**
   * Possible values are found in the SQL query.
   * computer_id | computer_name | introduced | discontinued | company_id | company_name
   */
  private static final String DEFAULT_ORDER_BY = ComputerFields.ID_ALIAS.getValue(); 

  private final ComputerService cs;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public Dashboard() {
    super();
    this.cs = ComputerService.getInstance();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @SuppressWarnings("unchecked")
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    LOGGER.info("access to : " + request.getRequestURL() + " " + request.getQueryString());

    ParamValidator validator = new ParamValidator();

    int page = validator.getInt(request, PAGE_PARAM, DEFAULT_PAGE);
    int range = validator.getInt(request, RANGE_PARAM, DEFAULT_RANGE);
    String orderByDir = validator.getString(request, DIR_PARAM, false);
    String search = validator.getString(request, SEARCH_PARAM, false);
    
    /**
     * We're not injecting directly the parameter received from GET.
     * First we check if it corresponds to a list of columns defined in an enumeration.
     * 
     */
    String orderByColumn = validator.getString(request, COLUMN_PARAM, false);
    if (!ComputerFields.contains(orderByColumn)) {
      orderByColumn = DEFAULT_ORDER_BY;
    }

    /**
     * Init the pager in the client's session if it doesn't exist.
     */
    Pager<Computer> pager = null;

    HttpSession session = request.getSession();

    if (session.getAttribute("pager") == null
        || !(session.getAttribute("pager") instanceof Pager)) {
      pager = new Pager<>(this.cs.count(), (options) -> this.cs.get(options),
          computer -> new ComputerDTO(computer));
      session.setAttribute("pager", pager);
    } else {

      // Here's the unchecked, safe enough.
      pager = (Pager<Computer>) session.getAttribute("pager");
    }
    pager.setRange(range);

    SelectOptions options = SelectOptions.builder().range(range).search(search)
        .orderBy(orderByColumn).asc(orderByDir).build();

    /**
     * Determine the page we need.
     * I need to know how much computers there is before asking any page.
     */
    int count = this.cs.count(options);
    int totalPage;

    totalPage = (count + range - 1) / range;

    page = page > totalPage ? totalPage : page;

    /**
     * Get that page and give it to the view.
     */

    options.setPage(page);
//    options.computeOffset();

    PageDTO<Computer> computers = pager.getPage(options);

    request.setAttribute("count", count);
    request.setAttribute("computers", computers);
    request.setAttribute("totalPage", totalPage);
    request.setAttribute("range", range);
    request.setAttribute("current", page);

    List<String> errors = validator.getErrors();
    if (session.getAttribute("errors") != null) {
      errors.addAll((Collection<? extends String>) session.getAttribute("errors"));
    }

    request.setAttribute("errors", validator.getErrors());
    RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard.jsp");
    view.forward(request, response);
  }
}
