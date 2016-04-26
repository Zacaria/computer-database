package com.excilys.formation.computerdatabase.dataBinders.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.computerdatabase.model.Company;

public class CompanyMapper implements RowMapper<Company> {

  private final static Logger LOGGER =
      LoggerFactory.getLogger(CompanyMapper.class);

  private final static String COMPANY_ID = "company_id";
  private final static String COMPANY_NAME = "company_name";

  @Override
  public Company mapRow(ResultSet rs, int rowNum) {
    Long id = null;
    String name = null;

    Company company = null;

    try {
      if (rs.getLong(COMPANY_ID) != 0) {

        id = rs.getLong(COMPANY_ID);
        name = rs.getString(COMPANY_NAME);

        company = Company.builder(id).name(name).build();
      }
    } catch (SQLException e) {
      LOGGER.error("mapping company error");
    }
    return company;
  }
}
