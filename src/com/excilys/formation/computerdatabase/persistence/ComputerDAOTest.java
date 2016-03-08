package com.excilys.formation.computerdatabase.persistence;

import static org.junit.Assert.*;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.util.DateConverter;

public class ComputerDAOTest {
	
	ComputerDAO cdao;

	@Before
	public void setUp() throws Exception {
		this.cdao = new ComputerDAO();				
	}

	@After
	public void tearDown() throws Exception {
		this.cdao = null;
	}

	@Test
	public void findByIdTest() {
		//testing some values of the db assuming these are the same that the inital sql file
		
		Computer computerMockFull = new Computer();
		
		computerMockFull.setId(12);
		computerMockFull.setName("Apple III");
		computerMockFull.setIntroduced(DateConverter.stringToDate("1980-05-01"));
		computerMockFull.setDiscontinued(DateConverter.stringToDate("1984-04-01"));
		computerMockFull.setCompanyId(1);
		
		assertEquals(computerMockFull, this.cdao.findById(12));
		
		Computer computerMockDateNull = new Computer();
		
		computerMockDateNull.setId(13);
		computerMockDateNull.setName("Apple Lisa");
		computerMockDateNull.setIntroduced(null);
		computerMockDateNull.setDiscontinued(null);
		computerMockDateNull.setCompanyId(1);
		
		assertEquals("This didn't work with a date null", computerMockDateNull, this.cdao.findById(13));
		
		Computer computerMockDateZero = new Computer();
		
		computerMockDateZero.setId(63);
		computerMockDateZero.setName("TX-2");
		//computerMockDateZero.setIntroduced(DateConverter.stringToDate("0"));
		computerMockDateZero.setDiscontinued(null);
		computerMockDateZero.setCompanyId(11);
		
		assertEquals(computerMockDateZero, this.cdao.findById(63));
		
	}

}
