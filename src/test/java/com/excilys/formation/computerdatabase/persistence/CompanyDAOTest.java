package com.excilys.formation.computerdatabase.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.formation.computerdatabase.exceptions.DAOException;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.service.CompanyService;

public class CompanyDAOTest {
  private final ConnectionFactory connectionFactory;
  
  private CompanyDAO cdao;
  private CompanyService service;  
  
  public CompanyDAOTest() {
    this.connectionFactory = ConnectionFactory.getInstance();
  }
  
  @Before
  public void setUp() throws Exception {
    cdao = CompanyDAO.INSTANCE;
    
    /**
     * init the connection in the ThreadLocal
     */
    this.service = CompanyService.getInstance();
    ConnectionFactory.initLocalConnection(this.connectionFactory.getConnection());
  }

  @After
  public void tearDown() throws Exception {
    cdao = null;
    this.connectionFactory.closeLocalConnection();
  }

  @Test
  public void findAllTest() throws DAOException, SQLException {
//    assertTrue(this.connection.get().isValid(1000));
    List<Company> companies = cdao.find();
    assertNotNull(companies);
    assertEquals(Company.class, companies.get(0).getClass());
  }

  @Test
  public void findByIdTest() throws DAOException {
    Company validMock = Company.builder(1l).name("Apple Inc.").build();
    validMock.setId(1l);
    validMock.setName("Apple Inc.");

    Company errorMock = Company.builder(0l).name("whatever").build();
    errorMock.setId(0l);
    errorMock.setName("whatever");

    Company fromDb = cdao.find(1l);
    assertEquals(validMock, fromDb);
    assertNotEquals(errorMock, fromDb);
  }
}
