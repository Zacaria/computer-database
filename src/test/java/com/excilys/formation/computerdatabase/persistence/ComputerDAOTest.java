package com.excilys.formation.computerdatabase.persistence;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.util.DateConverter;

public class ComputerDAOTest {
	
	private ComputerDAO cdao;
	private CompanyDAO companyDao;
	
	/**
	 * Naming : computerMock{id}{specificity}
	 */
	private Computer computerMock12Full;
	private Computer computerMock13DateNull;
	private Computer computerMock63DateZero;
	private Computer computerMockNullToCUD;
	
	

	@Before
	public void setUp() throws Exception {
		this.cdao = new ComputerDAO();
		this.companyDao = new CompanyDAO();
		
		this.computerMock12Full = new Computer();		
		this.computerMock12Full.setId(12l);
		this.computerMock12Full.setName("Apple III");
		this.computerMock12Full.setIntroduced(DateConverter.stringToDate("1980-05-01"));
		this.computerMock12Full.setDiscontinued(DateConverter.stringToDate("1984-04-01"));
		this.computerMock12Full.setCompany(new Company(1l, "Apple Inc."));
		
		this.computerMock13DateNull = new Computer();		
		this.computerMock13DateNull.setId(13l);
		this.computerMock13DateNull.setName("Apple Lisa");
		this.computerMock13DateNull.setIntroduced(null);
		this.computerMock13DateNull.setDiscontinued(null);
		this.computerMock13DateNull.setCompany(new Company(1l, "Apple Inc."));
		
		this.computerMock63DateZero = new Computer();		
		this.computerMock63DateZero.setId(63l);
		this.computerMock63DateZero.setName("TX-2");
		this.computerMock63DateZero.setIntroduced(DateConverter.stringToDate("0"));
		this.computerMock63DateZero.setDiscontinued(null);
		this.computerMock63DateZero.setCompany(this.companyDao.findById(11l));
		
		this.computerMockNullToCUD = new Computer();
		this.computerMockNullToCUD.setName("toto");
		this.computerMockNullToCUD.setIntroduced(DateConverter.stringToDate("2000-01-01"));
		this.computerMockNullToCUD.setDiscontinued(DateConverter.stringToDate("2000-01-05"));
		this.computerMockNullToCUD.setCompany(new Company(11l, "Lincoln Laboratory"));
	}

	@After
	public void tearDown() throws Exception {
		this.cdao = null;
		
		this.computerMock12Full = null;
		this.computerMock13DateNull = null;
		this.computerMock63DateZero = null;
		this.computerMockNullToCUD = null;
	}
	
	@Test
	public void findAllTest(){
		List<Computer> computers = cdao.findAll();
		assertNotNull(computers);
		assertEquals(Computer.class, computers.get(0).getClass());
	}

	@Test
	public void findByIdTest() {
		//testing some values of the db assuming these are the same that the inital sql file
		
		Computer computerMockFull = new Computer();
		
		computerMockFull.setId(12l);
		computerMockFull.setName("Apple III");
		computerMockFull.setIntroduced(DateConverter.stringToDate("1980-05-01"));
		computerMockFull.setDiscontinued(DateConverter.stringToDate("1984-04-01"));
		computerMockFull.setCompany(new Company(1l, "Apple Inc."));
		
		assertEquals(computerMockFull, this.cdao.findById(12l));
				
		assertEquals("This didn't work with a date null", this.computerMock13DateNull, this.cdao.findById(13l));
		
		assertEquals(this.computerMock63DateZero, this.cdao.findById(63l));
	}
	
	@Test
	public void createTest(){
		
		//Need to add findLast
		Long insertedId = this.cdao.create(this.computerMockNullToCUD);
		
		Computer lastComputer = this.cdao.findLast();
		
		assertEquals(insertedId, lastComputer.getId());
		
		this.cdao.delete(lastComputer.getId());
		
		
		this.computerMockNullToCUD.setCompany(new Company(100l, "Missingno"));
		assertNull("should not insert a computer with a company that doesn't exist", this.cdao.create(this.computerMockNullToCUD));
		
		
		this.computerMockNullToCUD.setCompany(new Company(1l, "Apple Inc."));
		this.computerMockNullToCUD.setName(null);
		assertNull(this.cdao.create(this.computerMockNullToCUD));
	}
	
	@Test
	public void deleteTest(){
		
		long newId = this.cdao.create(this.computerMockNullToCUD);
		
		Computer last = this.cdao.findById(newId);
		
		int affectedRows = this.cdao.delete(last.getId());
		
		Computer deleted = this.cdao.findById(last.getId());
		
		assertNull(deleted);
		assertEquals(1, affectedRows);
	}
	
	@Test
	public void updateTest(){
		
		long newId = this.cdao.create(this.computerMockNullToCUD);
		
		System.out.println("New Id : " + newId);
		
		this.computerMockNullToCUD.setId(newId);
		
		int affectedRows = this.cdao.update(this.computerMockNullToCUD);
		
		assertEquals(1, affectedRows);
		
		this.computerMockNullToCUD.setCompany(new Company(100l, "Lincoln Laboratory"));
		
		affectedRows = this.cdao.update(this.computerMockNullToCUD);
		
		assertEquals(0, affectedRows);
		
		//assume that this id deosn't exist
		this.computerMockNullToCUD.setId(100000l);
		
		affectedRows = this.cdao.update(this.computerMockNullToCUD);
		
		assertEquals(0, affectedRows);
		
		this.cdao.delete(newId);
		
	}

}
