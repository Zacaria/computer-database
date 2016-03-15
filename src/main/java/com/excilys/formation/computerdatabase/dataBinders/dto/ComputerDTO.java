package com.excilys.formation.computerdatabase.dataBinders.dto;

import com.excilys.formation.computerdatabase.model.Computer;

public class ComputerDTO extends DTO {
	private long id;
	private String name;
	private String introduced;
	private String discontinued;
	private long companyId;
	private String companyName;

	public ComputerDTO(Computer computer){
		this.id = computer.getId();
		this.name = computer.getName() != null ? computer.getName() : null;
		this.introduced = computer.getIntroduced() != null ? computer.getIntroduced().toString() : null;
		this.discontinued = computer.getDiscontinued() != null ? computer.getDiscontinued().toString() : null;
		
		if(computer.getCompany() != null){
			this.companyId = computer.getCompany().getId();
			this.companyName = computer.getCompany() != null ? computer.getCompany().getName() : null;
		}
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public long getCompanyId() {
		return companyId;
	}

	public String getCompanyName() {
		return companyName;
	}
}
