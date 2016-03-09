package com.excilys.formation.computerdatabase.model;

import java.sql.Date;

import com.excilys.formation.computerdatabase.util.DateConverter;

public class Computer {
	private Integer id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Integer companyId;
	
	public Computer() {
	}

	public Computer(Integer id, String name, Date introduced, Date discontinued, Integer companyId) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
	}
	
	public Computer(Integer id, String name, String introduced, String discontinued, Integer companyId){
		this.id = id;
		this.name = name;
		if(introduced != null){
			this.introduced = DateConverter.stringToDate(introduced);
		}
		if(discontinued != null){
			this.discontinued = DateConverter.stringToDate(discontinued);
		}
		this.companyId = companyId;
	}
	
	public Computer(String name, String introduced, String discontinued, Integer companyId){
		this.name = name;
		if(introduced != null){
			this.introduced = DateConverter.stringToDate(introduced);
		}
		if(discontinued != null){
			this.discontinued = DateConverter.stringToDate(discontinued);
		}
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company_id=" + companyId + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Computer)) {
			return false;
		}
		Computer other = (Computer) obj;
		if (companyId == null) {
			if (other.companyId != null) {
				return false;
			}
		} else if (!companyId.equals(other.companyId)) {
			return false;
		}
		if (discontinued == null) {
			if (other.discontinued != null) {
				return false;
			}
		} else if (!discontinued.equals(other.discontinued)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (introduced == null) {
			if (other.introduced != null) {
				return false;
			}
		} else if (!introduced.equals(other.introduced)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getIntroduced() {
		return introduced;
	}
	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}
	public Date getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer company_id) {
		this.companyId = company_id;
	}
}
