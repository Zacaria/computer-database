package com.excilys.formation.computerdatabase.servlets.util;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger("com.excilys.formation.computerdatabase");
	private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+$");
	
//	private List<ProcessedParam> params;
//	private List<ProcessedParam> errors;

	public ParamValidator() {

	}

	public int getInt(HttpServletRequest request, String field, int defaultInt) {
		int result;

		if (request.getParameter(field) != null && NUMBER_PATTERN.matcher(request.getParameter(field)).matches()) {
			result = Integer.parseInt(request.getParameter(field));
		} else {
			result = defaultInt;
		}

		return result;
	}

	public Long getLong(HttpServletRequest request, String field) {
		Long result = null;

		if (request.getParameter(field) != null && NUMBER_PATTERN.matcher(request.getParameter(field)).matches()) {
			result = Long.parseLong(request.getParameter(field));
		}

		return result;
	}

	public String getString(HttpServletRequest request, String field) {
		return request.getParameter(field).trim();
	}
	
	private boolean isNullOrEmpty(String s){
		if(s == null || s.isEmpty()){
			
		}
		return s == null || s.isEmpty();
	}
	
	private boolean isInt(String s){
		return NUMBER_PATTERN.matcher(s).matches() ? true : false;
	}
}
