package com.excilys.formation.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdatabase.dataBinders.mapper.CompanyMapper;
import com.excilys.formation.computerdatabase.exceptions.DAOException;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.SelectOptions;
import com.excilys.formation.computerdatabase.service.CompanyService;

public enum CompanyDAO implements CompanyDAOable {
  INSTANCE;

  private final static Logger LOGGER =
      LoggerFactory.getLogger("com.excilys.formation.computerdatabase");

  private final static String FIELDS = "company.id as company_id, company.name as company_name";

  CompanyDAO() {}

  private final String countQuery = "SELECT COUNT(*) as count from `computer-database-db`.company;";

  @Override
  public int count() throws DAOException {
    Connection connection = CompanyService.localConnection.get();

    int count = 0;

    Statement statement;
    try {
      statement = connection.createStatement();
      
      ResultSet resultSet = statement.executeQuery(countQuery);

      if (resultSet.first()) {
        count = resultSet.getInt("count");
      }

    } catch (SQLException e) {
      throw new DAOException(e);
    }
    
    return count;
  }

  private final String findAllQuery = "SELECT " + FIELDS + " FROM `computer-database-db`.company;";

  @Override
  public List<Company> find() throws DAOException {
    Connection connection = CompanyService.localConnection.get();

    List<Company> companies = null;

    try (Statement statement = connection.createStatement()){
      ResultSet resultSet = statement.executeQuery(findAllQuery);

      CompanyMapper mapper = new CompanyMapper();

      companies = mapper.mapList(resultSet);
    } catch (SQLException e) {
      throw new DAOException(e);
    }  

    return companies;
  }

  private final String findWithRangeQuery =
      "SELECT " + FIELDS + " FROM `computer-database-db`.company limit ?, ?;";

  @Override
  public List<Company> find(SelectOptions options) throws DAOException {
    Connection connection = CompanyService.localConnection.get();
    List<Company> companies = null;

    try (PreparedStatement statement = connection.prepareStatement(findWithRangeQuery)) {
      statement.setLong(1, options.getOffset());
      statement.setLong(2, options.getRange());
      ResultSet resultSet = statement.executeQuery();
      CompanyMapper mapper = new CompanyMapper();

      companies = mapper.mapList(resultSet);

    } catch (SQLException e) {
      throw new DAOException(e);
    }

    return companies;
  }

  private final String findByIdQuery =
      "SELECT " + FIELDS + " FROM `computer-database-db`.company WHERE id = ? ;";

  @Override
  public Company find(Long id) throws DAOException {
    Connection connection = CompanyService.localConnection.get();
    Company company = null;

    try (PreparedStatement statement = connection.prepareStatement(findByIdQuery)) {
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();
      CompanyMapper mapper = new CompanyMapper();

      if (resultSet.first()) {
        company = mapper.map(resultSet);
      }
    } catch (SQLException e) {
      throw new DAOException(e);
    }

    return company;
  }

  private final String deleteQuery = "DELETE FROM `computer-database-db`.company WHERE id = ?;";

  @Override
  public boolean delete(Long id) throws DAOException {
    Connection connection = CompanyService.localConnection.get();

    int affectedRows = 0;
    
    try (PreparedStatement statement = connection.prepareStatement(deleteQuery)){
      
      statement.setLong(1, id);

      affectedRows = statement.executeUpdate();
    } catch (SQLException e) {
      throw new DAOException(e);
    }   

    return affectedRows != 0 ? true : false;
  }
}
