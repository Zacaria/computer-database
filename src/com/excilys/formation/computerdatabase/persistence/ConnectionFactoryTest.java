package com.excilys.formation.computerdatabase.persistence;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConnectionFactoryTest {
	private ConnectionFactory factory;

	@Before
	public void setUp() throws Exception {
		factory = ConnectionFactory.getInstance();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getConnectionTest() {
		Connection cnx = factory.getConnection();
		
		try {
			assertTrue(cnx.isValid(1000));
			cnx.close();
			
			assertTrue(cnx.isClosed());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
