package com.excilys.formation.computerdatabase.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Page not found")
public class NotFoundException extends RuntimeException {

  /**
   * Generated Id
   */
  private static final long serialVersionUID = -8984855464634415207L;
  
  private String errCode;

  public NotFoundException(String errCode) {
    this.errCode = errCode;
  }

  public String getErrCode() {
    return errCode;
  }
}