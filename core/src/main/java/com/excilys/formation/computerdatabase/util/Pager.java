package com.excilys.formation.computerdatabase.util;

import com.excilys.formation.computerdatabase.util.DTOable;
import com.excilys.formation.computerdatabase.util.PageDTO;
import com.excilys.formation.computerdatabase.model.Page;
import com.excilys.formation.computerdatabase.model.SelectOptions;

/**
 * Holds the pagination logic.
 * And also holds the state of the pagination.
 * This is designed to be session-scoped
 * 
 * @author Zacaria
 *
 * @param <T>
 */
public class Pager<T> {
  private PageDTO<T> data;
  private int range = 10;
  private int totalPage;
  private int currentPageNumber = 1;
  private int total;
  /**
   * The class in charge of fetching data.
   */
  private Pageable<T> dataRetreiver;
  /**
   * The class in charge of getting a plain model into a DTO.
   */
  private DTOable<T> dtoizer;

  public Pager() {

  }

  public Pager(int count, Pageable<T> p, DTOable<T> dtoizer) {
    this.total = count;
    this.dataRetreiver = p;
    this.setTotalPage();
    this.dtoizer = dtoizer;
  }

  public PageDTO<T> getPage() {
    return this.getPage(SelectOptions.builder().page(this.currentPageNumber).build());
  }

  public PageDTO<T> getPage(SelectOptions options) {
    Page<T> page = this.dataRetreiver.get(options);
    this.data = new PageDTO<>(page, this.dtoizer);
    this.range = options.getRange();
    this.currentPageNumber = options.getPage();

    return this.data;
  }

  public int getCurrentPageNumber() {
    return this.currentPageNumber;
  }

  public int getTotalPage() {
    return this.totalPage;
  }

  private void setTotalPage() {
    this.totalPage = (this.total + this.range - 1) / this.range;
  }

  public int getRange() {
    return this.range;
  }

  public int getTotal() {
    return this.total;
  }
  
  public void setRange(int range) {
    this.range = range;
  }
}
