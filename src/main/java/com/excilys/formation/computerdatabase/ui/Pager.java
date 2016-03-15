package com.excilys.formation.computerdatabase.ui;

import com.excilys.formation.computerdatabase.dataBinders.dto.PageDTO;
import com.excilys.formation.computerdatabase.model.Page;

public class Pager<T> {
	private PageDTO<T> data;
	//private int offset = 0;
	private int range = 10;
	private int totalPage;
	private int currentPageNumber = 1;
	private int total;
	private Pageable<T> dataRetreiver;
	
	public Pager(){
		
	}
	
	public Pager(int count, Pageable<T> p){
		this.total = count;
		this.dataRetreiver = p;
	}
	
	public PageDTO<T> next(){
		return this.getPage(++this.currentPageNumber);
	}
	
	public PageDTO<T> previous(){
		return this.getPage(--this.currentPageNumber);
	}
	
	public PageDTO<T> getPage(){
		return this.getPage(this.currentPageNumber);
	}
	
	public PageDTO<T> getPage(int number){
		
		if(number < 1){
			number = 1;
		}
		
		this.data = this.dataRetreiver.get((number * this.range) - this.range, this.range);
		
		this.currentPageNumber = number;
		
		return this.data;
	}
	
	public int getCurrentPageNumber(){
		return this.currentPageNumber;
	}

	public int getTotalPage() {
		return this.totalPage;
	}
	
	public void setRange(int range){
		this.range = range;
	}
	
	public int getRange(){
		return this.range;
	}
}
