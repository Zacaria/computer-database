package com.excilys.formation.computerdatabase.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConnectionException extends RuntimeException{
	
	private final static Logger LOGGER = LoggerFactory.getLogger("com.excilys.formation.computerdatabase");

	/**
	 * Auto-generated ID
	 */
	private static final long serialVersionUID = 5837460717702535013L;

	public DBConnectionException(String message){		
		super(message);
		LOGGER.error(message);
	}
	
	public DBConnectionException(String message, Throwable cause){
		super(message, cause);
		LOGGER.error(message + cause.getMessage());
	}
	
	public DBConnectionException(Throwable cause){
		super(cause);
		LOGGER.error(cause.getMessage());
	}
}
