package com.excilys.formation.computerdatabase.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.persistence.CompanyDAO;
import com.excilys.formation.computerdatabase.persistence.ComputerDAO;

/**
 * This class is a facade exposing the capabilities of the API
 * @author excilys
 *
 */
public class API {
	
	private static final Logger consoleLogger = LogManager.getLogger("com.excilys.formation.computerdatabase.console");
	//private static final Logger HTMLLogger = LogManager.getLogger("com.excilys.formation.computerdatabase.html");
	
	public static List<Company> getCompanies(){
		consoleLogger.info("access");
		return new CompanyDAO().findAll();
	}
	
	public static List<Computer> getComputers(){
		consoleLogger.info("access");
		return new ComputerDAO().findAll();
	}
	
	public static Computer getComputer(String pId){
		consoleLogger.info("access");
		try {
			long id = Long.parseLong(pId);
			
			return new ComputerDAO().findById(id);			
		} catch (NumberFormatException e) {
			consoleLogger.error(e.getClass().getName() +" : "+ e.getMessage());
			//HTMLLogger.error(e.getClass().getName() +" : "+ e.getMessage());
			return null;
		}
	}
	
	public static long createComputer(String name, String introduced, String discontinued, String pCompanyId){
		consoleLogger.info("access");
		Long companyId = null;
		
		if(pCompanyId != null){
			try {
				companyId = Long.parseLong(pCompanyId);
				
			} catch (NumberFormatException e) {
				consoleLogger.error(e.getClass().getName() +" : "+ e.getMessage());
				//HTMLLogger.error(e.getClass().getName() +" : "+ e.getMessage());
				return 0;
			}
		}
		
		
		Computer computer = new Computer(name, introduced, discontinued, companyId);
		
		return new ComputerDAO().create(computer);
	}
	
	public static int deleteComputer(String id){
		consoleLogger.info("access");
		Long computerId = null;
		try {
			computerId = Long.parseLong(id);
			
		} catch (NumberFormatException e) {
			consoleLogger.error(e.getClass().getName() +" : "+ e.getMessage());
			//HTMLLogger.error(e.getClass().getName() +" : "+ e.getMessage());
			return 0;
		}
		return new ComputerDAO().delete(computerId);
	}
	
	public static int updateComputer(String id, String name, String introduced, String discontinued, String pCompanyId){
		consoleLogger.info("access");
		Long companyId = null;
		Long computerId = null;
		
		if(id != null){
			try {
				computerId = Long.parseLong(id);
				
			} catch (NumberFormatException e) {
				consoleLogger.error(e.getClass().getName() +" : "+ e.getMessage());
				//HTMLLogger.error(e.getClass().getName() +" : "+ e.getMessage());
				return 0;
			}
		}
		if(pCompanyId != null){
			try {
				companyId = Long.parseLong(pCompanyId);
				
			} catch (NumberFormatException e) {
				consoleLogger.error(e.getClass().getName() +" : "+ e.getMessage());
				//HTMLLogger.error(e.getClass().getName() +" : "+ e.getMessage());
				return 0;
			}
		}
		
		
		Computer computer = new Computer(computerId, name, introduced, discontinued, companyId);
		
		return new ComputerDAO().update(computer);
	}
}
