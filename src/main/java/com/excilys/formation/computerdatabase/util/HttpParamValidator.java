package com.excilys.formation.computerdatabase.util;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class HttpParamValidator {
	private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+$");
	
	private HttpParamValidator(){
		
	}
	
	public static Long getLong(HttpServletRequest request, String field){
		Long result = null;
		
		if (request.getParameter(field) != null
				&& NUMBER_PATTERN.matcher(request.getParameter(field)).matches()) {
			result = Long.parseLong(request.getParameter(field));
		}
		
		return result;
	}
	
	public static String getString(HttpServletRequest request, String field){
		return request.getParameter(field).trim();
	}
}
