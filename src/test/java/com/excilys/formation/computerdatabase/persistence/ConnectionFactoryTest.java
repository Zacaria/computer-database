package com.excilys.formation.computerdatabase.persistence;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionFactoryTest {
  private final static Logger LOGGER =
      LoggerFactory.getLogger("com.excilys.formation.computerdatabase");
  private ConnectionFactory factory;

  @Before
  public void setUp() throws Exception {
    factory = ConnectionFactory.getInstance();
  }

  @After
  public void tearDown() throws Exception {
    this.factory = null;
  }

  @Test
  public void getConnectionTest() {
    Connection cnx = factory.getConnection();

    try {
      assertTrue(cnx.isValid(1000));
      LOGGER.debug("Connection test success");
      cnx.close();

      assertTrue(cnx.isClosed());
    } catch (SQLException e) {
      LOGGER.error("failed test connection " + e.getMessage());
    }
  }

}
