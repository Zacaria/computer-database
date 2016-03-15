package com.excilys.formation.computerdatabase.ui;

import com.excilys.formation.computerdatabase.mapper.Page;

public class Pager<T> {
	private Page<T> data;
	private int offset = 0;
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
	
	public Page<T> next(){
		return this.getPage(++this.currentPageNumber);
	}
	
	public Page<T> previous(){
		return this.getPage(--this.currentPageNumber);
	}
	
	public Page<T> getPage(){
		return this.getPage(this.currentPageNumber);
	}
	
	public Page<T> getPage(int number){
		
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
}
