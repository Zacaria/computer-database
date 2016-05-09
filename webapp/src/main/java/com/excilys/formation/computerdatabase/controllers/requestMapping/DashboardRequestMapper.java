package com.excilys.formation.computerdatabase.controllers.requestMapping;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.computerdatabase.controllers.requestDTO.DashboardDTO;
import com.excilys.formation.computerdatabase.controllers.requestDTO.IRequestDTO;
import com.excilys.formation.computerdatabase.controllers.util.Pager;
import com.excilys.formation.computerdatabase.dataBinders.dto.ComputerDTO;
import com.excilys.formation.computerdatabase.util.PageDTO;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.model.ComputerFields;
import com.excilys.formation.computerdatabase.model.SelectOptions;
import com.excilys.formation.computerdatabase.service.ComputerService;
import com.excilys.formation.computerdatabase.util.StringChecker;

@Component
public class DashboardRequestMapper implements IRequestMapper<IRequestDTO> {

  private final static Logger LOGGER = LoggerFactory.getLogger(DashboardRequestMapper.class);

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
  private static final String DEFAULT_ORDER_BY = ComputerFields.NAME.getValue();

  @Autowired
  private ComputerService cs;

  public DashboardRequestMapper() {

  }

  @Override
  public IRequestDTO getToDTO(HttpServletRequest request) {
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

    SelectOptions options = SelectOptions.builder()
      .range(range)
      .search(search)
      .orderBy(orderByColumn)
      .asc(orderByDir)
      .build();

    /**
     * Determine the page we need.
     * I need to know how much computers there is before asking any page.
     */
    int count = this.cs.count(options);
    int totalPage = (count + range - 1) / range;

    page = page > totalPage ? totalPage : page;

    /**
     * Get that page and give it to the view.
     */
    options.setPage(page);

    Pager<Computer> pager = new Pager<>(this.cs.count(options), opts -> this.cs.get(opts),
        computer -> ComputerDTO.builder(computer)
          .build());

    PageDTO<Computer> computers = pager.getPage(options);

    return new DashboardDTO(page, pager, computers, options);
  }

  @Override
  public IRequestDTO fromDTO(IRequestDTO dto) {
    throw new UnsupportedOperationException();
  }
}
