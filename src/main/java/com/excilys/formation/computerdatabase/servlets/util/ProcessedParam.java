package com.excilys.formation.computerdatabase.servlets.util;

public class ProcessedParam<T> {
	private final String name;
	private final String plain;
	private T value;
	private boolean success = false;
	private String message;
	
	public ProcessedParam(String name, String plain) {
		this.name = name;
		this.plain = plain;
	}
	
}
