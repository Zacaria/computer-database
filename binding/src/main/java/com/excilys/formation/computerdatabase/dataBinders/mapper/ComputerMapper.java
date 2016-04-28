package com.excilys.formation.computerdatabase.dataBinders.mapper;

import java.sql.ResultSet;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Computer;

public class ComputerMapper implements RowMapper<Computer> {
  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerMapper.class);

  private final static String COMPUTER_ID = "computer_id";
  private final static String COMPUTER_NAME = "computer_name";
  private final static String COMPUTER_INTRODUCED = "introduced";
  private final static String COMPUTER_DISCONTINUED = "discontinued";

  /**
   * Maps a ResultSet into an Object
   */
  @Override
  public Computer mapRow(ResultSet rs, int rowNum) {
    Long id = null;
    String name = null;
    LocalDate introduced = null;
    LocalDate discontinued = null;
    Company company = null;

    Computer computer = null;

    try {
      id = rs.getLong(COMPUTER_ID);
      if (rs.getString(COMPUTER_NAME) != null) {
        name = rs.getString(COMPUTER_NAME);
      }
      if (rs.getTimestamp(COMPUTER_INTRODUCED) != null) {
        introduced = rs.getDate(COMPUTER_INTRODUCED).toLocalDate();
      }
      if (rs.getTimestamp(COMPUTER_DISCONTINUED) != null) {
        discontinued = rs.getDate(COMPUTER_DISCONTINUED).toLocalDate();
      }

      CompanyMapper companyMapper = new CompanyMapper();
      company = companyMapper.mapRow(rs, rowNum);

      computer = Computer.builder(name).id(id).introduced(introduced).discontinued(discontinued)
          .company(company).build();

    } catch (Exception e) {
      LOGGER.error("mapping computer error " + e.getMessage());
    }

    return computer;
  }
}
