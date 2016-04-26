package com.excilys.formation.computerdatabase.controllers.requestDTO;

import com.excilys.formation.computerdatabase.dataBinders.dto.PageDTO;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.model.SelectOptions;
import com.excilys.formation.computerdatabase.ui.Pager;

public class DashboardDTO implements IRequestDTO {
  private int page;
  private Pager<Computer> pager;
  private PageDTO<Computer> computers;
  private SelectOptions options;

  public DashboardDTO() {

  }

  public DashboardDTO(int page, Pager<Computer> pager, PageDTO<Computer> computers,
      SelectOptions options) {
    this.page = page;
    this.pager = pager;
    this.computers = computers;
    this.options = options;
  }

  public int getPage() {
    return page;
  }

  public Pager<Computer> getPager() {
    return pager;
  }

  public PageDTO<Computer> getComputers() {
    return computers;
  }

  public SelectOptions getOptions() {
    return options;
  }

  @Override
  public String toString() {
    return "DashboardDTO [page=" + page + ", pager=" + pager + ", computers=" + computers
        + ", options=" + options + "]";
  }
}
