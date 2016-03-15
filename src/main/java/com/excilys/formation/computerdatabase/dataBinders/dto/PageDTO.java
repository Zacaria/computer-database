package com.excilys.formation.computerdatabase.dataBinders.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.excilys.formation.computerdatabase.model.Page;

public class PageDTO<E> {
	private final int total;
	private final List<DTO> elements;
	private final int currentOffset;
	private final int size;
	
	public PageDTO(Page<E> page, DTOable<E> dtoizer){
		this.total = page.getTotal();
		this.currentOffset = page.getCurrentOffset();
		this.size = page.getSize();
		
		List<DTO> elements = new ArrayList<>();
		Iterator<E> iterator = page.getElements().iterator();
		while(iterator.hasNext()){
			elements.add(dtoizer.getDTO(iterator.next()));
		}
		
		this.elements = elements;
	}

	public List<DTO> getElements() {
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
