package com.excilys.formation.computerdatabase.servlets.requestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;

import com.excilys.formation.computerdatabase.dataBinders.dto.ComputerDTO;
import com.excilys.formation.computerdatabase.dataBinders.dto.PageDTO;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.model.ComputerFields;
import com.excilys.formation.computerdatabase.model.SelectOptions;
import com.excilys.formation.computerdatabase.service.ComputerService;
import com.excilys.formation.computerdatabase.servlets.requestDTO.DashboardDTO;
import com.excilys.formation.computerdatabase.servlets.requestDTO.RequestDTO;
import com.excilys.formation.computerdatabase.ui.Pager;
import com.excilys.formation.computerdatabase.util.StringChecker;

@Controller
public class DashboardRequestMapper implements RequestMapper<RequestDTO> {

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

  private ComputerService cs;

  public DashboardRequestMapper() {

  }

  public DashboardRequestMapper(ComputerService cs) {
    this.cs = cs;
  }

  @SuppressWarnings("unchecked")
  @Override
  public RequestDTO getToDTO(HttpServletRequest request) {
    int page;
    int range;
    String orderByDir = request.getParameter(DIR_PARAM);
    String search = request.getParameter(SEARCH_PARAM);
    String orderByColumn;

    if (StringChecker.isNullOrEmpty(request.getParameter(PAGE_PARAM))
        || !StringChecker.isNumber(request.getParameter(PAGE_PARAM))) {
      page = DEFAULT_PAGE;
    } else {
      page = Integer.parseInt(request.getParameter(PAGE_PARAM));
    }

    if (StringChecker.isNullOrEmpty(request.getParameter(RANGE_PARAM))
        || !StringChecker.isNumber(request.getParameter(RANGE_PARAM))) {
      range = DEFAULT_RANGE;
    } else {
      range = Integer.parseInt(request.getParameter(RANGE_PARAM));
    }

    if (!StringChecker.isNullOrEmpty(request.getParameter(COLUMN_PARAM))
        && ComputerFields.contains(request.getParameter(COLUMN_PARAM))) {
      /**
       * We're not injecting directly the parameter received from GET.
       * First we check if it corresponds to a list of columns defined in an enumeration.
       */
      orderByColumn = request.getParameter(COLUMN_PARAM);
    } else {
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
          computer -> ComputerDTO.builder(computer).build());
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

    PageDTO<Computer> computers = pager.getPage(options);

    return new DashboardDTO(page, pager, computers, options);
  }

  @Override
  public RequestDTO fromDTO(RequestDTO dto) {
    throw new UnsupportedOperationException();
  }
}
