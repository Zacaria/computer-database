package com.excilys.formation.computerdatabase.persistence;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.formation.computerdatabase.model.Company;

public class CompanyDAOTest {
	CompanyDAO cdao;

	@Before
	public void setUp() throws Exception {
		cdao = new CompanyDAO();
	}

	@After
	public void tearDown() throws Exception {
		cdao = null;
	}

	@Test
	public void findAllTest(){		
		ArrayList<Company> companies = cdao.findAll();
		assertNotNull(companies);
		assertEquals(Company.class, companies.get(0).getClass());
	}
	
	@Test
	public void findByIdTest(){
		Company validMock = new Company();
		validMock.setId(1);
		validMock.setName("Apple Inc.");
		
		Company errorMock = new Company();
		errorMock.setId(0);
		errorMock.setName("whatever");
		
		Company fromDb = cdao.findById(1);
		assertEquals(validMock, fromDb);
		assertNotEquals(errorMock, fromDb);
	}
}
