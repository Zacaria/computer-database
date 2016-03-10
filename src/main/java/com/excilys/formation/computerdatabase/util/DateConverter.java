package com.excilys.formation.computerdatabase.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class DateConverter {
	private DateConverter (){
		
	}
	
	public static LocalDate stringToDate(String s){
		//http://stackoverflow.com/questions/22061723/regex-date-validation-for-yyyy-mm-dd
		Pattern dateFormat = Pattern.compile("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$");
		
		if(!dateFormat.matcher(s).matches()){
			return null;
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(s, formatter);
		
		return date;
	}
}
