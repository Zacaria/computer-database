package com.excilys.formation.computerdatabase.util;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DateConverterTest {
//	private LocalDate mock;
	private String[] datesMocks = { 
			"3000", "abd", "1990-58-90", "2000-02-29", 	"2004-02-29", 
			"2004/02/29", "2010-03-15", "2038-02-29", "1950-04-04", 
			"2015-06-32"
	};
	private Object[] results = {
			null, null, null, LocalDate.of(2000, 02, 29), LocalDate.of(2004, 02, 29),
			null, LocalDate.of(2010, 03, 15), null, null, 
			null
	};

	@Before
	public void setUp() throws Exception {
//		this.mock = LocalDate.of(1990, 5, 31);
	}

	@After
	public void tearDown() throws Exception {
//		this.mock = null;
	}

	@Test
	public void stringToDateTest() {
		
		for(int i = 0; i < datesMocks.length; i++){
			assertEquals(results[i], DateConverter.stringToDate(datesMocks[i]));
		}
		
		/*LocalDate toTest = DateConverter.stringToDate("1990-05-31");

		assertEquals(this.mock, toTest);
		assertNotEquals(LocalDate.now(), toTest);

		toTest = DateConverter.stringToDate(datesMocks[0]);
		assertNull(toTest);
		
		toTest = DateConverter.stringToDate("abd");
		toTest = DateConverter.stringToDate("1990-58-90");*/
		
	}

}
