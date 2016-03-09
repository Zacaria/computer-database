package com.excilys.formation.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.computerdatabase.model.Company;

public class CompanyMapper implements Mapable<Company>{

	@Override
	public Company map(ResultSet rs) {
		Company company = new Company();
		
		try {
			company.setId(rs.getLong("id"));
			if(rs.getString("name") != null){
				company.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}

}
