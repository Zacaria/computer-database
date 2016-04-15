package com.excilys.formation.computerdatabase.persistence;

import java.sql.Date;
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

import com.excilys.formation.computerdatabase.dataBinders.mapper.ComputerMapper;
import com.excilys.formation.computerdatabase.exceptions.DAOException;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.model.SelectOptions;

/**
 * The enum Singleton is not vulnerable against Reflection API.
 * 
 * @author Zacaria
 *
 */
@Repository("ComputerDAO")
public class ComputerDAO implements IComputerDAO {

  private final static Logger LOGGER =
      LoggerFactory.getLogger(ComputerDAO.class);

  private final static String FIELDS =
      "computer.id as computer_id, computer.name as computer_name, introduced, discontinued, company_id";

  @Autowired
  private DataSource dataSource;

  public ComputerDAO() {
  }

  private final String countQuery =
      "SELECT COUNT(*) as total from `computer-database-db`.computer;";

  @Override
  public int count() throws DAOException {

    int count = 0;

    try (Statement statement = this.dataSource.getConnection().createStatement()) {

      ResultSet resultSet = statement.executeQuery(countQuery);

      if (resultSet.first()) {
        count = (int) resultSet.getInt("total");
      }

    } catch (SQLException e) {
      throw new DAOException(e);
    }

    return count;
  }

  private final String countQueryWithOptions = "SELECT COUNT(*) as total, " + FIELDS
      + " FROM `computer-database-db`.computer " + " WHERE computer.name LIKE ?;";

  @Override
  public int count(SelectOptions options) throws DAOException {

    int count = 0;

    try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement(countQueryWithOptions)) {

      statement.setString(1, options.getSearch());

      LOGGER.debug(options.toString());

      ResultSet resultSet = statement.executeQuery();

      if (resultSet.first()) {
        count = (int) resultSet.getInt("total");
      }

    } catch (SQLException e) {
      throw new DAOException(e);
    }

    return count;
  }

  private final String findAllQuery = "SELECT " + FIELDS
      + " , company.id as company_id, company.name as company_name FROM `computer-database-db`.computer left join `computer-database-db`.company on computer.company_id = company.id;";

  @Override
  public List<Computer> find() throws DAOException {

    List<Computer> computers = null;

    try (Statement statement = this.dataSource.getConnection().createStatement()) {

      ResultSet resultSet = statement.executeQuery(findAllQuery);
      ComputerMapper mapper = new ComputerMapper();

      computers = mapper.mapList(resultSet);

    } catch (SQLException e) {
      throw new DAOException(e);
    }

    return computers;
  }

  private final String findWithRangeQuery =
      "SELECT computer.id as computer_id, computer.name as computer_name, introduced, discontinued, company.id as company_id, company.name as company_name "
          + " FROM `computer-database-db`.computer "
          + " LEFT JOIN `computer-database-db`.company ON computer.company_id = company.id "
          + " WHERE computer.name LIKE ?" + " ORDER BY %s %s" + " LIMIT ?, ?";

  @Override
  public List<Computer> find(SelectOptions options) throws DAOException {
    LOGGER.debug(options.toString());

    List<Computer> computers = null;

    /**
     * Considering that the SelectOptions class is safe,
     * The following line remains safe against sql inject.
     */
    String sql = String.format(findWithRangeQuery, options.getOrderBy(), options.getAsc());
    try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement(sql)) {

      statement.setString(1, options.getSearch());
      statement.setInt(2, options.getOffset());
      statement.setInt(3, options.getRange());

      ResultSet resultSet = statement.executeQuery();
      ComputerMapper mapper = new ComputerMapper();

      computers = mapper.mapList(resultSet);

      LOGGER.debug("executed : " + statement);

    } catch (SQLException e) {
      throw new DAOException(e);
    }

    return computers;
  }

  private final String findByIdQuery = "SELECT " + FIELDS
      + ", company.id as company_id, company.name as company_name FROM `computer-database-db`.computer left join `computer-database-db`.company on computer.company_id = company.id where computer.id = ?;";

  @Override
  public Computer find(Long id) throws DAOException {
    Computer computer = null;

    try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement(findByIdQuery)) {
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();
      ComputerMapper mapper = new ComputerMapper();

      if (resultSet.first()) {
        computer = mapper.map(resultSet);
      } else {
        LOGGER.info("Computer not found with id " + id);
        return null;
      }
    } catch (SQLException e) {
      throw new DAOException(e);
    }

    return computer;
  }

  private final String insertQuery =
      "INSERT INTO `computer-database-db`.computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);";

  @Override
  public Long create(Computer computer) throws DAOException {
    LOGGER.debug("Creating this computer : " + computer);

    Long newId = null;

    try (PreparedStatement statement =
        this.dataSource.getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

      // FIXME : this needs a statement mapper.
      statement.setString(1, computer.getName());
      statement.setDate(2,
          computer.getIntroduced() != null ? Date.valueOf(computer.getIntroduced()) : null);
      statement.setDate(3,
          computer.getDiscontinued() != null ? Date.valueOf(computer.getDiscontinued()) : null);

      if (computer.getCompany() != null) {
        statement.setLong(4, computer.getCompany().getId());
      } else {
        statement.setNull(4, java.sql.Types.INTEGER);
      }

      int affectedRows = statement.executeUpdate();
      if (affectedRows == 0) {
        LOGGER.error("Error Insert : failed, no rows affected");
        return null;
      }
      ResultSet generatedId = statement.getGeneratedKeys();
      if (generatedId.next()) {
        newId = generatedId.getLong(1);
      }
    } catch (SQLException e) {
      throw new DAOException(e);
    }

    return newId;
  }

  private final String deleteQuery = "DELETE FROM `computer-database-db`.computer WHERE id = ?;";

  @Override
  public boolean delete(Long id) throws DAOException {

    int affectedRows = 0;
    try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement(deleteQuery)) {
      statement.setLong(1, id);
      affectedRows = statement.executeUpdate();
    } catch (SQLException e) {
      throw new DAOException(e);
    }

    return affectedRows != 0 ? true : false;
  }

  private final String deleteWithCompanyIdQuery =
      "DELETE FROM `computer-database-db`.computer WHERE company_id = ?;";

  @Override
  public boolean deleteWithCompanyId(Long id) throws DAOException {

    int affectedRows = 0;

    try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement(deleteWithCompanyIdQuery)) {
      statement.setLong(1, id);

      affectedRows = statement.executeUpdate();
    } catch (SQLException e) {
      throw new DAOException(e);
    }

    return affectedRows != 0 ? true : false;
  }

  private final String updateQuery =
      "UPDATE `computer-database-db`.computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";

  @Override
  public Computer update(Computer computer) throws DAOException {

    int affectedRows = 0;

    try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement(updateQuery)) {

      LOGGER.debug("Trying to update computer with this : " + computer);

      statement.setString(1, computer.getName());

      statement.setDate(2,
          computer.getIntroduced() != null ? Date.valueOf(computer.getIntroduced()) : null);
      statement.setDate(3,
          computer.getDiscontinued() != null ? Date.valueOf(computer.getDiscontinued()) : null);

      statement.setLong(4, computer.getCompany().getId());
      statement.setLong(5, computer.getId());

      affectedRows = statement.executeUpdate();

      if (affectedRows == 0) {
        LOGGER.error("ERROR Update : failed, no rows affected");
        return null;
      }

    } catch (SQLException e) {
      throw new DAOException(e);
    }

    return computer;
  }
  
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

}