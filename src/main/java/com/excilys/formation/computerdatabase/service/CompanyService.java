package com.excilys.formation.computerdatabase.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdatabase.exceptions.DAOException;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Page;
import com.excilys.formation.computerdatabase.model.SelectOptions;
import com.excilys.formation.computerdatabase.persistence.CompanyDAO;
import com.excilys.formation.computerdatabase.persistence.CompanyDAOable;
import com.excilys.formation.computerdatabase.persistence.ComputerDAO;
import com.excilys.formation.computerdatabase.persistence.ComputerDAOable;
import com.excilys.formation.computerdatabase.persistence.ConnectionFactory;

public class CompanyService implements Servable<Company> {

//  public static final ThreadLocal<Connection> localConnection = new ThreadLocal<>();

  private static final Logger LOGGER =
      LoggerFactory.getLogger("com.excilys.formation.computerdatabase");

  private final ConnectionFactory connectionFactory;

  private final CompanyDAOable cdao;
  private final ComputerDAOable computerDAO;

  private CompanyService() {
    this.cdao = CompanyDAO.INSTANCE;
    this.computerDAO = ComputerDAO.INSTANCE;
    this.connectionFactory = ConnectionFactory.getInstance();
  }

  private static class CompanyServiceHolder {
    private final static CompanyService instance = new CompanyService();
  }

  public static CompanyService getInstance() {
    return CompanyServiceHolder.instance;
  }

  @Override
  public Page<Company> get(SelectOptions options) {
    LOGGER.info("get with options" + this.getClass());
    
    ConnectionFactory.initLocalConnection(this.connectionFactory.getConnection());

    Page<Company> companyPage = null;
    
    try{
      companyPage =
          new Page<>(options.getOffset(), this.cdao.find(options), this.cdao.count());
      
    } catch (DAOException e){
      LOGGER.error("failed");
    } finally {
      this.connectionFactory.closeLocalConnection();
    }

    return companyPage;
  }

  @Override
  public Company get(Long id) {
    LOGGER.info("get id" + this.getClass());

    ConnectionFactory.initLocalConnection(this.connectionFactory.getConnection());

    Company result = null;
    try {
      result = this.cdao.find(id);
    } catch (DAOException e) {
      LOGGER.error("failed get id");
    } finally {
      this.connectionFactory.closeLocalConnection();
    }
    
    return result;
  }

  @Override
  public int count() {
    LOGGER.info("count " + this.getClass());

    ConnectionFactory.initLocalConnection(this.connectionFactory.getConnection());

    int result = 0;
    try {
      result = this.cdao.count();
    } catch (DAOException e) {
      LOGGER.error("count failed");
    } finally {
      this.connectionFactory.closeLocalConnection();
    }
    
    return result;
  }

  @Override
  public boolean delete(Long id) {
    LOGGER.info("delete " + this.getClass());

    Connection connection = this.connectionFactory.getConnection();
    
    ConnectionFactory.initLocalConnection(connection);
    
    // no try with resource because we need to rollback in the catch.
    
    boolean success = false;
    try {

      connection.setAutoCommit(false);

      success = this.computerDAO.deleteWithCompanyId(id);

      success = this.cdao.delete(id);

      connection.commit();

    } catch (DAOException | SQLException e) {
      LOGGER.error("Delete : failed, no rows affected", e);
      try {
        connection.rollback();
      } catch (SQLException e1) {
        LOGGER.error("Rollback failed :(", e1);
      }
    } finally {
      this.connectionFactory.closeLocalConnection();
    }

    return success;
  }
}
