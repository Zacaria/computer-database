package com.excilys.formation.computerdatabase.ui;

import com.excilys.formation.computerdatabase.dataBinders.dto.DTOable;
import com.excilys.formation.computerdatabase.dataBinders.dto.PageDTO;
import com.excilys.formation.computerdatabase.model.Page;

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

	public PageDTO<T> next() {
		return this.getPage(++this.currentPageNumber);
	}

	public PageDTO<T> previous() {
		return this.getPage(--this.currentPageNumber);
	}

	public PageDTO<T> getPage() {
		return this.getPage(this.currentPageNumber);
	}

	public PageDTO<T> getPage(int pageNum) {

		if (pageNum < 1) {
			pageNum = 1;
		}

		Page<T> page = this.dataRetreiver.get((pageNum * this.range) - this.range, this.range);
		this.data = new PageDTO<>(page, this.dtoizer);

		this.setTotal(this.data.getTotal());
		this.currentPageNumber = pageNum;

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

}
