package com.excilys.formation.computerdatabase.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	
	public List<Company> getCompanies(){
		consoleLogger.info("access");
		return new CompanyDAO().findAll();
	}

}
