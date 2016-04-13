package com.excilys.formation.computerdatabase.dataBinders.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.servlets.requestDTO.RequestDTO;

public class CompanyMapper implements Mapable<Company> {

  private final static Logger LOGGER =
      LoggerFactory.getLogger("com.excilys.formation.computerdatabase");

  private final static String COMPANY_ID = "company_id";
  private final static String COMPANY_NAME = "company_name";

  @Override
  public Company map(ResultSet rs) {
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

  @Override
  public List<Company> mapList(ResultSet rs) {
    List<Company> companies = new ArrayList<>();
    Company company = null;
    try {
      while (rs.next()) {
        company = this.map(rs);

        companies.add(company);
      }
    } catch (SQLException e) {
      LOGGER.error("list mapping company error");
    }

    return companies;
  }
}
