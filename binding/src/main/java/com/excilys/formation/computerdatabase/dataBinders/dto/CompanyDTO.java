package com.excilys.formation.computerdatabase.dataBinders.dto;

import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.util.IDTO;

public class CompanyDTO implements IDTO {
  private long id;
  private String name;

  public CompanyDTO(Company company) {
    this.id = company.getId();
    this.name = company.getName() != null ? company.getName() : null;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
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
    if (!(obj instanceof CompanyDTO)) {
      return false;
    }
    CompanyDTO other = (CompanyDTO) obj;
    if (id != other.id) {
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

  @Override
  public String toString() {
    return "CompanyDTO [id=" + id + ", name=" + name + "]";
  }
}
