package com.excilys.formation.computerdatabase.exceptions;

public class DBConnectionException extends RuntimeException{

	/**
	 * Auto-generated ID
	 */
	private static final long serialVersionUID = 5837460717702535013L;

	public DBConnectionException(String message){
		super(message);
	}
	
	public DBConnectionException(String message, Throwable cause){
		super(message, cause);
	}
	
	public DBConnectionException(Throwable cause){
		super(cause);
	}
}
