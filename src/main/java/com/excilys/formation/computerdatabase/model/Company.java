package com.excilys.formation.computerdatabase.model;

public class Company {
	private Long id;
	private String name;

	public Company() {
	}

	public Company(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public static Builder builder() {
		return new Company.Builder();
	}

	public static class Builder{
		private Company instance = new Company();
		
		public Builder(){
		}
		
		public Builder id(Long id){
			instance.id = id;
			return this;
		}
		
		public Builder name(String name){
			instance.name = name;
			return this;
		}
		
		public Company build(){
			return instance;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Company " + name + " has id " + id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Company)) {
			return false;
		}
		Company other = (Company) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
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

}
