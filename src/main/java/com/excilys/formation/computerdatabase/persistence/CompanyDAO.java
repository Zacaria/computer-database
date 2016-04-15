package com.excilys.formation.computerdatabase.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.computerdatabase.dataBinders.mapper.CompanyMapper;
import com.excilys.formation.computerdatabase.exceptions.DAOException;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.SelectOptions;

@Repository("CompanyDAO")
public class CompanyDAO implements ICompanyDAO {

  private final static Logger LOGGER =
      LoggerFactory.getLogger(CompanyDAO.class);

  private final static String FIELDS = "company.id as company_id, company.name as company_name";

  private final String countQuery = "SELECT COUNT(*) as count from `computer-database-db`.company;";
  
  @Autowired
  private DataSource dataSource;
  
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }
  
  public CompanyDAO() { }

  @Override
  public int count() throws DAOException {
    LOGGER.info("counting in " + this.getClass().getSimpleName());
//    Connection connection = ;
    
    int count = 0;

    Statement statement;
    try {
      statement = this.dataSource.getConnection().createStatement();

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
    LOGGER.info("finding in " + this.getClass().getSimpleName());
//    Connection connection = ConnectionFactory.getLocalConnection();

    List<Company> companies = null;

    try (Statement statement = this.dataSource.getConnection().createStatement()) {
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
    LOGGER.info("finding in " + this.getClass().getSimpleName());
    LOGGER.debug("With params " + options);
//    Connection connection = ConnectionFactory.getLocalConnection();
    List<Company> companies = null;

    try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement(findWithRangeQuery)) {
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
    LOGGER.info("finding in " + this.getClass().getSimpleName());
    LOGGER.debug("With params " + id);

//    Connection connection = ConnectionFactory.getLocalConnection();
    Company company = null;

    try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement(findByIdQuery)) {
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
    LOGGER.info("deleting in " + this.getClass().getSimpleName());
    LOGGER.debug("With params " + id);

//    Connection connection = ConnectionFactory.getLocalConnection();

    int affectedRows = 0;

    try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement(deleteQuery)) {

      statement.setLong(1, id);

      affectedRows = statement.executeUpdate();
    } catch (SQLException e) {
      throw new DAOException(e);
    }

    return affectedRows != 0 ? true : false;
  }
}
