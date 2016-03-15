package com.excilys.formation.computerdatabase.dataBinders.dto;

import com.excilys.formation.computerdatabase.model.Company;

public class CompanyDTO extends DTO{
	private long id;
	private String name;
	
	public CompanyDTO(Company company){
		this.id = company.getId();
		this.name = company.getName() != null ? company.getName() : null;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
