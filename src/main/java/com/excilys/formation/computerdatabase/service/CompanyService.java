package com.excilys.formation.computerdatabase.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.computerdatabase.mapper.Page;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.persistence.CompanyDAO;

public class CompanyService {
	
	private static final Logger consoleLogger = LogManager.getLogger("com.excilys.formation.computerdatabase");
	
	private CompanyService(){
		
	}
	
	private static class CompanyServiceHolder {
		private final static CompanyService instance = new CompanyService();
	}
	
	public static CompanyService getInstance(){
		return CompanyServiceHolder.instance;
	}
	
	
	
	public Page<Company> getCompanies(int from, int max){
		consoleLogger.info("access");
		
		CompanyDAO cdao = new CompanyDAO();
		
		Page<Company> companies = new Page<>(from, cdao.findWithRange(from, max), cdao.count());
		
		return companies;
	}
	
	public Company getCompany(String pId){
		consoleLogger.info("access");
		try {
			Long id = Long.parseLong(pId);
			
			return new CompanyDAO().findById(id);			
		} catch (NumberFormatException e) {
			consoleLogger.error(e.getClass().getName() +" : "+ e.getMessage());
			return null;
		}
	}

}
