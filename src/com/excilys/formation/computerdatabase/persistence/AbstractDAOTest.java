package com.excilys.formation.computerdatabase.persistence;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AbstractDAOTest {
	AbstractDAO adao;

	@Before
	public void setUp() throws Exception {
		adao = new CompanyDAO();
	}

	@After
	public void tearDown() throws Exception {
		adao = null;
	}

	@Test
	public void getConnectionTest() {
		assertEquals(com.mysql.jdbc.JDBC4Connection.class, adao.getConnection().getClass());
	}
}
