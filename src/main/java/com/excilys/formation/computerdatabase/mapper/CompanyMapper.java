package com.excilys.formation.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	@Override
	public List<Company> mapList(ResultSet rs) {
		List<Company> companies = new ArrayList<>();
		Company company = null;
		try {
			while(rs.next()){
				company = this.map(rs);
				
				companies.add(company);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return companies;
	}
	
	

}
