package com.excilys.formation.computerdatabase.persistence;

import static org.junit.Assert.*;

import java.util.List;

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
		computerMockFull.setCompanyId(1l);
		
		assertEquals(computerMockFull, this.cdao.findById(12l));
		
		Computer computerMockDateNull = new Computer();
		
		computerMockDateNull.setId(13l);
		computerMockDateNull.setName("Apple Lisa");
		computerMockDateNull.setIntroduced(null);
		computerMockDateNull.setDiscontinued(null);
		computerMockDateNull.setCompanyId(1l);
		
		assertEquals("This didn't work with a date null", computerMockDateNull, this.cdao.findById(13l));
		
		Computer computerMockDateZero = new Computer();
		
		computerMockDateZero.setId(63l);
		computerMockDateZero.setName("TX-2");
		computerMockDateZero.setIntroduced(DateConverter.stringToDate("0"));
		computerMockDateZero.setDiscontinued(null);
		computerMockDateZero.setCompanyId(11l);
		
		assertEquals(computerMockDateZero, this.cdao.findById(63l));
	}
	@Test
	public void createTest(){
		Computer computer = new Computer();
		computer.setName("toto");
		computer.setIntroduced(DateConverter.stringToDate("2000-01-01"));
		computer.setDiscontinued(DateConverter.stringToDate("2000-01-05"));
		computer.setCompanyId(11l);
		
		//Need to add findLast
		long insertedId = this.cdao.create(computer);
		
		Computer lastComputer = this.cdao.findLast();
		
		assertTrue(insertedId == lastComputer.getId());
		
		this.cdao.delete(lastComputer.getId());
		
		
		computer.setCompanyId(100l);
		assertNull(this.cdao.create(computer));
		
		
		computer.setCompanyId(11l);
		computer.setName(null);
		assertNull(this.cdao.create(computer));
	}
	
	@Test
	public void deleteTest(){
		
		Computer computer = new Computer();
		computer.setName("toto");
		computer.setIntroduced(DateConverter.stringToDate("2000-01-01"));
		computer.setDiscontinued(DateConverter.stringToDate("2000-01-05"));
		computer.setCompanyId(11l);
		
		long newId = this.cdao.create(computer);
		
		Computer last = this.cdao.findById(newId);
		
		int affectedRows = this.cdao.delete(last.getId());
		
		Computer deleted = this.cdao.findById(last.getId());
		
		assertNull(deleted);
		assertEquals(1, affectedRows);
	}
	
	@Test
	public void updateTest(){
		
		Computer computer = new Computer();
		computer.setName("toto");
		computer.setIntroduced(DateConverter.stringToDate("2000-02-01"));
		computer.setDiscontinued(DateConverter.stringToDate("2000-01-05"));
		computer.setCompanyId(11l);
		
		long newId = this.cdao.create(computer);
		
		System.out.println("New Id : " + newId);
		
		computer.setId(newId);
		
		int affectedRows = this.cdao.update(computer);
		
		assertEquals(1, affectedRows);
		
		computer.setCompanyId(1200l);
		
		affectedRows = this.cdao.update(computer);
		
		assertEquals(0, affectedRows);
		
		//assume that this id deosn't exist
		computer.setId(100000l);
		
		affectedRows = this.cdao.update(computer);
		
		assertEquals(0, affectedRows);
		
		this.cdao.delete(newId);
		
	}

}
