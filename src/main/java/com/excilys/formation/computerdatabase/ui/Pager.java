package com.excilys.formation.computerdatabase.ui;

import com.excilys.formation.computerdatabase.dataBinders.dto.DTOable;
import com.excilys.formation.computerdatabase.dataBinders.dto.PageDTO;
import com.excilys.formation.computerdatabase.model.Page;
import com.excilys.formation.computerdatabase.model.SelectOptions;

public class Pager<T> {
  private PageDTO<T> data;
  private int range = 10;
  private int totalPage;
  private int currentPageNumber = 1;
  private int total;
  /**
   * The class in charge of fetching data.
   */
  private IPager<T> dataRetreiver;
  /**
   * The class in charge of getting a plain model into a DTO.
   */
  private DTOable<T> dtoizer;

  public Pager() {

  }

  public Pager(int count, IPager<T> p, DTOable<T> dtoizer) {
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

    this.setTotal(this.data.getTotal());
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

  public void setRange(int range) {
    this.range = range;
    this.setTotalPage();
  }

  public int getRange() {
    return this.range;
  }

  private void setTotal(int total) {
    this.total = total;
    this.setTotalPage();
  }
  
  public int getTotal(){
    return this.total;
  }

}
