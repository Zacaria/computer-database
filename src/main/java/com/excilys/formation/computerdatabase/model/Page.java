package com.excilys.formation.computerdatabase.model;

import java.util.List;

public final class Page<T> {
	private final int total;
	private final List<T> elements;
	private final int currentOffset;
	private final int size;
	
	public Page(int offset, List<T> data, int total){
		this.currentOffset = offset;
		this.elements = data;
		this.size = this.elements.size();
		this.total = total;
	}

	public List<T> getElements() {
		return elements;
	}

	public int getCurrentOffset() {
		return currentOffset;
	}

	public int getSize() {
		return size;
	}
	
	public int getTotal(){
		return total;
	}
}
