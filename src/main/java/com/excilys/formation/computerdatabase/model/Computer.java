package com.excilys.formation.computerdatabase.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.excilys.formation.computerdatabase.util.DateConverter;

@Entity
@Table(schema = "computer-database-db", name = "computer")
public class Computer {

  @Id
  @GeneratedValue
  private Long id;

  @Column
  private String name;

  @Column
  private LocalDate introduced;

  @Column
  private LocalDate discontinued;

  @ManyToOne
  @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "fk_computer_company_1") )
  private Company company;

  public Computer() {
  }

  public static Builder builder(String name) {
    return new Computer.Builder(name);
  }

  public static class Builder {
    private Computer instance = new Computer();

    public Builder(String name) {
      instance.name = name;
    }

    public Builder id(Long id) {
      instance.id = id;
      return this;
    }

    public Builder name(String name) {
      instance.name = name;
      return this;
    }

    public Builder introduced(LocalDate introduced) {
      instance.introduced = introduced;
      return this;
    }

    public Builder introduced(String introduced) {
      instance.introduced = DateConverter.stringToDate(introduced);
      return this;
    }

    public Builder discontinued(LocalDate discontinued) {
      instance.discontinued = discontinued;
      return this;
    }

    public Builder discontinued(String discontinued) {
      instance.discontinued = DateConverter.stringToDate(discontinued);
      return this;
    }

    public Builder company(Company company) {
      instance.company = company;
      return this;
    }

    public Computer build() {
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

  public LocalDate getIntroduced() {
    return introduced;
  }

  public void setIntroduced(LocalDate introduced) {
    this.introduced = introduced;
  }

  public void setIntroduced(String introduced) {
    this.introduced = DateConverter.stringToDate(introduced);
  }

  public LocalDate getDiscontinued() {
    return discontinued;
  }

  public void setDiscontinued(LocalDate discontinued) {
    this.discontinued = discontinued;
  }

  public void setDiscontinued(String discontinued) {
    this.discontinued = DateConverter.stringToDate(discontinued);
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  @Override
  public String toString() {
    return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced
        + ", discontinued=" + discontinued + ", company=" + company + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((company == null) ? 0 : company.hashCode());
    result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
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
    if (!(obj instanceof Computer)) {
      return false;
    }
    Computer other = (Computer) obj;
    if (company == null) {
      if (other.company != null) {
        return false;
      }
    } else if (!company.equals(other.company)) {
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
}
