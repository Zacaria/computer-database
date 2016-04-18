package com.excilys.formation.computerdatabase.persistence;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.computerdatabase.dataBinders.mapper.CompanyMapper;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.SelectOptions;

@Repository("CompanyDAO")
public class CompanyDAO implements ICompanyDAO {

  private final static Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

  private final static String FIELDS = "company.id as company_id, company.name as company_name";

  private JdbcTemplate jdbcTemplate;

  public CompanyDAO() {
  }

  private final String countQuery = "SELECT COUNT(*) as count from `computer-database-db`.company;";

  @Override
  public int count() {
    LOGGER.info("counting in " + this.getClass().getSimpleName());

    Integer count = this.jdbcTemplate.queryForObject(countQuery, Integer.class);

    return count != null ? count : 0;
  }

  private final String findAllQuery = "SELECT " + FIELDS + " FROM `computer-database-db`.company;";

  @Override
  public List<Company> find() {
    LOGGER.info("finding in " + this.getClass().getSimpleName());

    List<Company> companies = this.jdbcTemplate.query(findAllQuery, (ResultSet rs, int rowNum) -> {
      return new CompanyMapper().mapRow(rs, rowNum);
    });

    return companies;
  }

  private final String findWithRangeQuery =
      "SELECT " + FIELDS + " FROM `computer-database-db`.company limit ?, ?;";

  @Override
  public List<Company> find(SelectOptions options) {
    LOGGER.info("finding in " + this.getClass().getSimpleName());
    LOGGER.debug("With params " + options);

    List<Company> companies = this.jdbcTemplate.query(findWithRangeQuery,
        new Object[] { options.getOffset(), options.getRange() }, (ResultSet rs, int rowNum) -> {
          return new CompanyMapper().mapRow(rs, rowNum);
        });

    return companies;
  }

  private final String findByIdQuery =
      "SELECT " + FIELDS + " FROM `computer-database-db`.company WHERE id = ? ;";

  @Override
  public Company find(Long id) {
    LOGGER.info("finding in " + this.getClass().getSimpleName());
    LOGGER.debug("With params " + id);

    Company company = this.jdbcTemplate.queryForObject(findByIdQuery, new Object[] { id },
        (ResultSet rs, int rowNum) -> {
          return new CompanyMapper().mapRow(rs, rowNum);
        });
    
    return company;
  }

  private final String deleteQuery = "DELETE FROM `computer-database-db`.company WHERE id = ?;";

  @Override
  public boolean delete(Long id) {
    LOGGER.info("deleting in " + this.getClass().getSimpleName());
    LOGGER.debug("With params " + id);

    int affectedRows = this.jdbcTemplate.update(deleteQuery, id);

    return affectedRows != 0 ? true : false;
  }
  
  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }
}
