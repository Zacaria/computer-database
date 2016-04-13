package com.excilys.formation.computerdatabase.dataBinders.dto;

import com.excilys.formation.computerdatabase.model.Computer;

public class ComputerDTO implements DTO {
  private long id;
  private String name;
  private String introduced;
  private String discontinued;
  private long companyId;
  private String companyName;

  public ComputerDTO() {

  }

  public static Builder builder() {
    return new ComputerDTO.Builder();
  }

  public static Builder builder(Computer computer) {
    return new ComputerDTO.Builder(computer);
  }

  public static class Builder {
    private ComputerDTO instance = new ComputerDTO();

    public Builder() {
    }

    public Builder(Computer computer) {
      instance.id = computer.getId();
      instance.name = computer.getName() != null ? computer.getName() : null;
      instance.introduced =
          computer.getIntroduced() != null ? computer.getIntroduced().toString() : null;
      instance.discontinued =
          computer.getDiscontinued() != null ? computer.getDiscontinued().toString() : null;

      if (computer.getCompany() != null) {
        instance.companyId = computer.getCompany().getId();
        instance.companyName =
            computer.getCompany() != null ? computer.getCompany().getName() : null;
      }
    }

    public Builder id(long id) {
      instance.id = id;
      return this;
    }

    public Builder name(String name) {
      instance.name = name;
      return this;
    }

    public Builder introduced(String introduced) {
      instance.introduced = introduced;
      return this;
    }

    public Builder discontinued(String discontinued) {
      instance.discontinued = discontinued;
      return this;
    }

    public Builder companyId(long companyId) {
      instance.companyId = companyId;
      return this;
    }

    public Builder companyName(String compayName) {
      instance.companyName = compayName;
      return this;
    }

    public ComputerDTO build() {
      return instance;
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
