package com.excilys.formation.computerdatabase.servlets.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdatabase.util.DateConverter;

/**
 * In order to keep a track of errors, the methods are not static.
 * @author Zacaria
 *
 */
public class ParamValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger("com.excilys.formation.computerdatabase");
	private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+$");

	// private List<ProcessedParam> params;
	// private List<ProcessedParam> errors;
	private List<String> errors;

	public ParamValidator() {
		this.errors = new ArrayList<>();
	}

	/**
	 * The int value can have default parameter.
	 * @param request
	 * @param field
	 * @param defaultInt
	 * @return
	 */
	public int getInt(HttpServletRequest request, String field, int defaultInt) {
		int result;

		if (this.isNumber(request.getParameter(field))) {
			result = Integer.parseInt(request.getParameter(field));
		} else {
			result = defaultInt;
		}

		return result;
	}

	public Long getLong(HttpServletRequest request, String field) {
		Long result = null;

		if (this.isNumber(request.getParameter(field))) {
			result = Long.parseLong(request.getParameter(field));
		}

		return result;
	}

	public String getString(HttpServletRequest request, String field) {
		return request.getParameter(field).trim();
	}

	public LocalDate getDate(HttpServletRequest request, String field) {
		LocalDate result = null;

		if (!request.getParameter(field).isEmpty() && this.isDate(request.getParameter(field))) {
			result = DateConverter.stringToDate(request.getParameter(field));
		}
		
		return result;
	}

	private boolean isNullOrEmpty(String s) {
		if (s == null || s.isEmpty()) {
			this.errors.add("The given parameter \"" + s + "\" was null or empty");
			return true;
		} else {
			return false;
		}
	}

	private boolean isNumber(String s) {
		if (this.isNullOrEmpty(s)) {
			return false;
		}

		if (!NUMBER_PATTERN.matcher(s.trim()).matches()) {
			this.errors.add("The given parameter \"" + s + "\" could not be parsed into a number");
			return false;
		}
		return true;
	}

	private boolean isDate(String s) {
		
		if (DateConverter.stringToDate(s.trim()) == null) {
			this.errors.add("The given parameter \"" + s + "\" could not be parsed into a date");
			return false;
		}
		return true;
	}

	public List<String> getErrors() {
		return errors;
	}
	
}
