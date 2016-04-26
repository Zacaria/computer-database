package com.excilys.formation.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.formation.computerdatabase.dataBinders.mapper.ComputerMapper;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.model.SelectOptions;

/**
 * 
 * @author Zacaria
 *
 */
@Repository("ComputerDAO")
public class ComputerDAO implements IComputerDAO {

  private final static Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

  private final static String FIELDS =
      "computer.id as computer_id, computer.name as computer_name, introduced, discontinued, company_id";

  private JdbcTemplate jdbcTemplate;

  public ComputerDAO() {
  }

  private final String countQuery =
      "SELECT COUNT(id) as total from `computer-database-db`.computer;";

  @Override
  public int count() {
    LOGGER.info("counting in " + this.getClass().getSimpleName());

    Integer count = this.jdbcTemplate.queryForObject(countQuery, Integer.class);

    return count != null ? count : 0;
  }

  private final String countQueryWithOptions =
      "SELECT COUNT(id) as total FROM `computer-database-db`.computer "
          + " WHERE computer.name LIKE ?;";

  @Override
  public int count(SelectOptions options) {

    LOGGER.debug(options.toString());

    Integer count = this.jdbcTemplate.queryForObject(countQueryWithOptions,
        new Object[] { options.getSearch() }, Integer.class);

    return count != null ? count : 0;
  }

  private final String findAllQuery = "SELECT " + FIELDS
      + " , company.id as company_id, company.name as company_name FROM `computer-database-db`.computer left join `computer-database-db`.company on computer.company_id = company.id;";

  @Override
  public List<Computer> find() {

    List<Computer> computers = this.jdbcTemplate.query(findAllQuery, 
        (ResultSet rs, int rowNum) -> new ComputerMapper().mapRow(rs, rowNum));

    return computers;
  }

  private final String findWithRangeQuery =
      "SELECT computer.id as computer_id, computer.name as computer_name, introduced, discontinued, company.id as company_id, company.name as company_name "
          + " FROM `computer-database-db`.computer "
          + " LEFT JOIN `computer-database-db`.company ON computer.company_id = company.id "
          + " WHERE computer.name LIKE ?" + " ORDER BY %s %s" + " LIMIT ?, ?";

  @Override
  public List<Computer> find(SelectOptions options) {
    LOGGER.debug(options.toString());

    /**
     * Considering that the SelectOptions class is safe,
     * The following line remains safe against sql inject.
     */
    String sql = String.format(findWithRangeQuery, options.getOrderBy(), options.getAsc());

    List<Computer> computers = this.jdbcTemplate.query(sql,
        new Object[] { options.getSearch(), options.getOffset(), options.getRange() },
        (ResultSet rs, int rowNum) -> new ComputerMapper().mapRow(rs, rowNum));

    return computers;
  }

  private final String findByIdQuery = "SELECT " + FIELDS
      + ", company.id as company_id, company.name as company_name FROM `computer-database-db`.computer left join `computer-database-db`.company on computer.company_id = company.id where computer.id = ?;";

  @Override
  public Computer find(Long id) {
    Computer computer = null;
    
    /*
     * This block catches when there is no result
     */
    try {
      computer = this.jdbcTemplate.queryForObject(findByIdQuery, new Object[] { id },
          (ResultSet rs, int rowNum) ->  new ComputerMapper().mapRow(rs, rowNum));
    } catch (EmptyResultDataAccessException e){
      return null;
    }
    
    return computer;
  }

  private final String insertQuery =
      "INSERT INTO `computer-database-db`.computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);";

  /**
   * All this stuff to insert and get the last inserted id in only one query.
   */
  @Override
  public Long create(Computer computer) {
    LOGGER.debug("Creating this computer : " + computer);

    KeyHolder keyHolder = new GeneratedKeyHolder();

    this.jdbcTemplate.update(new PreparedStatementCreator() {

      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement(insertQuery, new String[] { "id" });
        ps.setString(1, computer.getName());
        ps.setDate(2,
            computer.getIntroduced() != null ? Date.valueOf(computer.getIntroduced()) : null);
        ps.setDate(3,
            computer.getDiscontinued() != null ? Date.valueOf(computer.getDiscontinued()) : null);

        if (computer.getCompany() != null) {
          ps.setLong(4, computer.getCompany().getId());
        } else {
          ps.setNull(4, java.sql.Types.INTEGER);
        }
        return ps;
      }
    }, keyHolder);

    return keyHolder.getKey().longValue();
  }

  private final String deleteQuery = "DELETE FROM `computer-database-db`.computer WHERE id = ?;";

  @Override
  public boolean delete(Long id) {

    int affectedRows = this.jdbcTemplate.update(deleteQuery, new Object[] { id });

    return affectedRows != 0 ? true : false;
  }

  private final String deleteWithCompanyIdQuery =
      "DELETE FROM `computer-database-db`.computer WHERE company_id = ?;";

  @Override
  public boolean deleteWithCompanyId(Long id) {

    int affectedRows = this.jdbcTemplate.update(deleteWithCompanyIdQuery, new Object[] { id });

    return affectedRows != 0 ? true : false;
  }

  private final String updateQuery =
      "UPDATE `computer-database-db`.computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";

  @Override
  public Computer update(Computer computer) {

    int affectedRows =
        this.jdbcTemplate.update(updateQuery,
            new Object[] { computer.getName(), computer.getIntroduced() != null
                ? Date.valueOf(computer.getIntroduced()) : null,
        computer.getDiscontinued() != null ? Date.valueOf(computer.getDiscontinued()) : null,
        computer.getCompany().getId(), computer.getId() });

    if (affectedRows == 0) {
      LOGGER.error("ERROR Update : failed, no rows affected");
      return null;
    }

    return computer;
  }

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

}