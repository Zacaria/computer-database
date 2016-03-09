package com.excilys.formation.computerdatabase.ui;

import java.util.ArrayList;
import java.util.List;

public class Pager<T> {
	private ArrayList<T> data;
	private int offset = 0;
	private int range = 10;
	private int totalPage;
	private int currentPageNumber = 1;
	
	
	public Pager(ArrayList<T> data){
		this.data = data;
		this.totalPage = this.data.size() / this.range + 1;
		
		System.out.println(data.size());
	}
	
	public ArrayList<T> next(){
		this.offset = this.offset + this.range < this.data.size() 
				? this.offset + this.range
				: this.offset;
		
		
		return this.getCurrentPage();
	}
	
	public ArrayList<T> previous(){
		this.offset = this.offset - this.range > 0
				? this.offset - this.range
				: 0;
		
		return this.getCurrentPage();
	}
	
	public ArrayList<T> getCurrentPage(){
		ArrayList<T> sublist = new ArrayList<T>(this.data.subList(this.offset, this.offset + this.range));
		
		this.currentPageNumber = this.offset / this.range + 1;
		
		return sublist;
	}
	
	public int getCurrentPageNumber(){
		return this.currentPageNumber;
	}

	public int getTotalPage() {
		return this.totalPage;
	}
	
	
}
