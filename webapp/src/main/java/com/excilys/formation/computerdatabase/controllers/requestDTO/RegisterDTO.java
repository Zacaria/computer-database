package com.excilys.formation.computerdatabase.controllers.requestDTO;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class RegisterDTO implements IRequestDTO {
  
  @NotEmpty(message = "{NotEmpty.Register.username}")
  @Size(min = 4, max = 60, message = "{Size.Register.username}")
  private String username;
  
  @NotEmpty(message = "{NotEmpty.Register.password}")
  @Size(min = 4, max = 1024, message = "{Size.Register.password}")
  private String password;

  public RegisterDTO() {

  }
  
  public RegisterDTO(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
