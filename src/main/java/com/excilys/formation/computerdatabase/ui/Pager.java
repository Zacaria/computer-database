package com.excilys.formation.computerdatabase.ui;

import com.excilys.formation.computerdatabase.mapper.Page;

public class Pager<T> {
	private Page<T> data;
	private int offset = 0;
	private int range = 10;
	private int totalPage;
	private int currentPageNumber = 1;
	
	
	public Pager(Page<T> data){
		this.data = data;
		this.totalPage = this.data.getTotal() / this.range + 1;
	}
	
	public int next(){
		this.offset = this.offset + this.range < this.data.getTotal()
				? this.offset + this.range
				: this.offset;
		
		return this.offset;
	}
	
	public int previous(){
		this.offset = this.offset - this.range > 0
				? this.offset - this.range
				: 0;
		
		return this.offset;
	}
	
	public Page<T> getCurrentPage(){
		this.currentPageNumber = this.offset / this.range + 1;
		
		return this.data;
	}
	
	public void setCurrentPage(Page<T> data){
		this.data = data;
		this.totalPage = this.data.getTotal() / this.range + 1;
	}
	
	public int getCurrentPageNumber(){
		return this.currentPageNumber;
	}

	public int getTotalPage() {
		return this.totalPage;
	}
	
	
}
