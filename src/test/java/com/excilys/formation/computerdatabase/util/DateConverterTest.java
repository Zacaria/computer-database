package com.excilys.formation.computerdatabase.util;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DateConverterTest {
	LocalDate mock;

	@Before
	public void setUp() throws Exception {
		this.mock = LocalDate.of(1990, 5, 31);
	}

	@After
	public void tearDown() throws Exception {
		this.mock = null;
	}

	@Test
	public void stringToDateTest() {
		LocalDate toTest = DateConverter.stringToDate("1990-05-31");
		
		assertEquals(this.mock, toTest);
		assertNotEquals(LocalDate.now(), toTest);
		
		LocalDate wrong1 = DateConverter.stringToDate("3000");
		LocalDate wrong2 = DateConverter.stringToDate("abd");
		LocalDate wrong3 = DateConverter.stringToDate("1990-58-90");
		assertNull(wrong1);
		assertNull(wrong2);
		assertNull(wrong3);
	}

}
