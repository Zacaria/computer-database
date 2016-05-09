package com.excilys.formation.computerdatabase.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(schema = "computer-database-db", name = "users")
public class User implements UserDetails {

  /**
   * Generated serial
   */
  private static final long serialVersionUID = 7230707041185867894L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String username;

  @Column
  private String password;

  public User() {

  }

  public static Builder builder() {
    return new User.Builder();
  }

  public static class Builder {
    private User instance = new User();

    public Builder id(Long id) {
      instance.id = id;
      return this;
    }

    public Builder username(String username) {
      instance.username = username;
      return this;
    }

    public Builder password(String password) {
      instance.password = password;
      return this;
    }

    public User build() {
      return instance;
    }
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((password == null) ? 0 : password.hashCode());
    result = prime * result + ((username == null) ? 0 : username.hashCode());
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
    if (!(obj instanceof User)) {
      return false;
    }
    User other = (User) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (password == null) {
      if (other.password != null) {
        return false;
      }
    } else if (!password.equals(other.password)) {
      return false;
    }
    if (username == null) {
      if (other.username != null) {
        return false;
      }
    } else if (!username.equals(other.username)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    // Not supported
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    // Not supported
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    // Not supported
    return true;
  }

  @Override
  public boolean isEnabled() {
    // Not supported
    return true;
  }

}
