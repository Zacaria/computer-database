package com.excilys.formation.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.computerdatabase.model.Company;

public class CompanyMapper implements Mapable<Company>{
	
	private final static String COMPANY_ID = "company_id";
	private final static String COMPANY_NAME= "company_name";
	

	@Override
	public Company map(ResultSet rs) {
		Company company = null;
		
		try {
			if(rs.getLong(COMPANY_ID) != 0){
				company = new Company();
				company.setId(rs.getLong(COMPANY_ID));
				if(rs.getString(COMPANY_NAME) != null){
					company.setName(rs.getString(COMPANY_NAME));
				}
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
