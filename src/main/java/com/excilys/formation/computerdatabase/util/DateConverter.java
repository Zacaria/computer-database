package com.excilys.formation.computerdatabase.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateConverter {
	public static Date stringToDate(String s){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = new Date(formatter.parse(s).getTime());
		} catch (ParseException e){
			System.out.println("Please check the provided date format, it should be yyyy-MM-dd");
			return null;
		}
		
		return date;
	}
}
