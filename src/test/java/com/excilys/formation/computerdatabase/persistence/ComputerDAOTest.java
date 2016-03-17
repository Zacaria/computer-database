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
	private Computer computerMockFull;
	
	

	@Before
	public void setUp() throws Exception {
		this.cdao = new ComputerDAO();
		this.companyDao = new CompanyDAO();
		
		this.computerMock12Full = Computer
				.builder("Apple III")
				.id(12l)
				.introduced("1980-05-01")
				.discontinued("1984-04-01")
				.company(Company.builder(1l).name("Apple Inc.").build())
				.build();
		
		this.computerMock13DateNull = Computer
				.builder("Apple Lisa")
				.id(13l)
				.company(Company.builder(1l).name("Apple Inc.").build())
				.build();
		
		this.computerMock63DateZero = Computer
				.builder("TX-2")
				.id(63l)
				.introduced("0")
				.company(Company.builder(11l).name("Lincoln Laboratory").build())
				.build();
		
		this.computerMockNullToCUD = Computer
				.builder("toto")
				.id(63l)
				.introduced("2000-01-01")
				.discontinued("2000-01-05")
				.company(Company.builder(11l).name("Lincoln Laboratory").build())
				.build();
		
		this.computerMockFull = Computer
				.builder("Apple III")
				.id(12l)
				.introduced("1980-05-01")
				.discontinued("1984-04-01")
				.company(Company.builder(1l).name("Apple Inc.").build())
				.build();
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
		List<Computer> computers = cdao.find();
		assertNotNull(computers);
		assertEquals(Computer.class, computers.get(0).getClass());
	}

	@Test
	public void findByIdTest() {
		//testing some values of the db assuming these are the same that the inital sql file
		
		
		assertEquals(this.computerMockFull, this.cdao.find(12l));
				
		assertEquals("This didn't work with a date null", this.computerMock13DateNull, this.cdao.find(13l));
		
		assertEquals(this.computerMock63DateZero, this.cdao.find(63l));
	}
	
	@Test
	public void createTest(){
		
		//Need to add findLast
		Long insertedId = this.cdao.create(this.computerMockNullToCUD);
		
		assertTrue(insertedId != null);
		
		this.cdao.delete(insertedId);
		
		
		this.computerMockNullToCUD.setCompany(Company.builder(100l).name("Missingno").build());
		assertNull("should not insert a computer with a company that doesn't exist", this.cdao.create(this.computerMockNullToCUD));
		
		
		this.computerMockNullToCUD.setCompany(Company.builder(1l).name("Apple Inc.").build());
		this.computerMockNullToCUD.setName(null);
		assertNull(this.cdao.create(this.computerMockNullToCUD));
	}
	
	@Test
	public void deleteTest(){
		
		long newId = this.cdao.create(this.computerMockNullToCUD);
		
		Computer last = this.cdao.find(newId);
		
		boolean success = this.cdao.delete(last.getId());
		
		Computer deleted = this.cdao.find(last.getId());
		
		assertNull(deleted);
		assertTrue(success);
	}
	
	@Test
	public void updateTest(){
		
		long newId = this.cdao.create(this.computerMockNullToCUD);
		
		System.out.println("New Id : " + newId);
		
		this.computerMockNullToCUD.setId(newId);
		
		Computer computer = this.cdao.update(this.computerMockNullToCUD);
		
		assertEquals(computer, this.computerMockNullToCUD);
		
		this.computerMockNullToCUD.setCompany(Company.builder(100l).name("Lincoln Laboratory").build());
		
		computer = this.cdao.update(this.computerMockNullToCUD);
		
		assertNotEquals(computer, this.computerMockNullToCUD);
		
		//assume that this id deosn't exist
		this.computerMockNullToCUD.setId(100000l);
		
		computer = this.cdao.update(this.computerMockNullToCUD);
		
		assertNotEquals(computer, this.computerMockNullToCUD);
		
		this.cdao.delete(newId);
		
	}

}
