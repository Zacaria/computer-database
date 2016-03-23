package com.excilys.formation.computerdatabase.dataBinders.dto;

import com.excilys.formation.computerdatabase.model.Computer;

public class ComputerDTO implements DTO {
  private long id;
  private String name;
  private String introduced;
  private String discontinued;
  private long companyId;
  private String companyName;

  public ComputerDTO(Computer computer) {
    this.id = computer.getId();
    this.name = computer.getName() != null ? computer.getName() : null;
    this.introduced = computer.getIntroduced() != null ? computer.getIntroduced().toString() : null;
    this.discontinued =
        computer.getDiscontinued() != null ? computer.getDiscontinued().toString() : null;

    if (computer.getCompany() != null) {
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (companyId ^ (companyId >>> 32));
    result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
    result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
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
    if (!(obj instanceof ComputerDTO)) {
      return false;
    }
    ComputerDTO other = (ComputerDTO) obj;
    if (companyId != other.companyId) {
      return false;
    }
    if (companyName == null) {
      if (other.companyName != null) {
        return false;
      }
    } else if (!companyName.equals(other.companyName)) {
      return false;
    }
    if (discontinued == null) {
      if (other.discontinued != null) {
        return false;
      }
    } else if (!discontinued.equals(other.discontinued)) {
      return false;
    }
    if (id != other.id) {
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

  @Override
  public String toString() {
    return "ComputerDTO [id=" + id + ", name=" + name + ", introduced=" + introduced
        + ", discontinued=" + discontinued + ", companyId=" + companyId + ", companyName="
        + companyName + "]";
  }
}
