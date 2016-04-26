package com.excilys.formation.computerdatabase.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.formation.computerdatabase.exceptions.DAOException;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.service.CompanyService;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration
public class CompanyDAOTest {
  // private final ConnectionFactory connectionFactory;

  // @Autowired
  private CompanyDAO cdao;

  // @Autowired
  private CompanyService service;

  // @Autowired
  private DataSource datasource;

  private ApplicationContext appContext;

  public CompanyDAOTest() {
    // this.connectionFactory = ConnectionFactory.getInstance();
  }

  @Before
  public void setUp() throws Exception {
    // cdao = CompanyDAO.INSTANCE;
    this.appContext = new ClassPathXmlApplicationContext("app-context.xml");
    System.out.println(appContext);
    this.service = (CompanyService) appContext.getBean("CompanyService");
    this.cdao = (CompanyDAO) appContext.getBean("CompanyDAO");
    this.datasource = (DataSource) appContext.getBean("dataSource");

    System.out.println(this.cdao);

    /**
     * init the connection in the ThreadLocal
     */
    // this.service = CompanyService.getInstance();
    // ConnectionFactory.initLocalConnection(this.connectionFactory.getConnection());
  }

  @After
  public void tearDown() throws Exception {
    ((AbstractApplicationContext) appContext).close();
    // cdao = null;
    // this.connectionFactory.closeLocalConnection();
  }

  @Test
  public void countTest() throws DAOException {
    int count = cdao.count();
    assertEquals(true, count > 0);
  }

  @Test
  public void findAllTest() throws DAOException, SQLException {
    List<Company> companies = cdao.find();
    assertNotNull(companies);
    assertEquals(Company.class, companies.get(0).getClass());
  }

  // I need DBUnit to test that :(
  @Ignore
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
