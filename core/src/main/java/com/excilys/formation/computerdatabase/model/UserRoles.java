package com.excilys.formation.computerdatabase.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRoles implements GrantedAuthority {
  ROLE_USER,
  ROLE_ADMIN;

  public String getAuthority() {
      return name();
  }
}
