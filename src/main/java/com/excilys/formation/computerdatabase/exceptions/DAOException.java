package com.excilys.formation.computerdatabase.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOException extends Exception{
  
  private final static Logger LOGGER =
      LoggerFactory.getLogger("com.excilys.formation.computerdatabase");
  
  /**
   * Auto generated serial
   */
  private static final long serialVersionUID = 2080993202423139483L;
  
  public DAOException(String message) {
    super(message);
    LOGGER.error(message);
  }

  public DAOException(String message, Throwable cause) {
    super(message, cause);
    LOGGER.error(message + cause.getMessage());
  }

  public DAOException(Throwable cause) {
    super(cause);
    LOGGER.error(cause.getMessage());
  }

}
