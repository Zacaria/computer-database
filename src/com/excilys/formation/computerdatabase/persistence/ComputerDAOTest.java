package com.excilys.formation.computerdatabase.persistence;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.util.DateConverter;

public class ComputerDAOTest {
	
	//TODO : refactor mocks
	
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
		ArrayList<Computer> computers = cdao.findAll();
		assertNotNull(computers);
		assertEquals(Computer.class, computers.get(0).getClass());
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
	@Test
	public void createTest(){
		Computer computer = new Computer();
		computer.setName("toto");
		computer.setIntroduced(DateConverter.stringToDate("2000-01-01"));
		computer.setDiscontinued(DateConverter.stringToDate("2000-01-05"));
		computer.setCompanyId(11);
		
		//Need to add findLast
		int insertedId = this.cdao.create(computer);
		
		Computer lastComputer = this.cdao.findLast();
		
		assertTrue(insertedId == lastComputer.getId());
		
		computer.setCompanyId(100);
		assertEquals(0, this.cdao.create(computer));
		
		
		computer.setCompanyId(11);
		computer.setName(null);
		assertEquals(0, this.cdao.create(computer));
	}
	
	@Test
	public void deleteTest(){
		
		Computer computer = new Computer();
		computer.setName("toto");
		computer.setIntroduced(DateConverter.stringToDate("2000-01-01"));
		computer.setDiscontinued(DateConverter.stringToDate("2000-01-05"));
		computer.setCompanyId(11);
		
		int newId = this.cdao.create(computer);
		
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
		computer.setCompanyId(11);
		
		int newId = this.cdao.create(computer);
		
		System.out.println("New Id : " + newId);
		
		computer.setId(newId);
		
		int affectedRows = this.cdao.update(computer);
		
		assertEquals(1, affectedRows);
		
		computer.setCompanyId(1200);
		
		affectedRows = this.cdao.update(computer);
		
		assertEquals(0, affectedRows);
		
		//assume that this id deosn't exist
		computer.setId(100000);
		
		affectedRows = this.cdao.update(computer);
		
		assertEquals(0, affectedRows);
		
		this.cdao.delete(newId);
		
	}

}
