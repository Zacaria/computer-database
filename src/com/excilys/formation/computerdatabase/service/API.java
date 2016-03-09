package com.excilys.formation.computerdatabase.service;

import java.util.ArrayList;

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
	
	public static ArrayList<Company> getCompanies(){
		consoleLogger.info("access");
		return new CompanyDAO().findAll();
	}
	
	public static ArrayList<Computer> getComputers(){
		consoleLogger.info("access");
		return new ComputerDAO().findAll();
	}
	
	public static Computer getComputer(String pId){
		consoleLogger.info("access");
		try {
			int id = Integer.parseInt(pId);
			
			return new ComputerDAO().findById(id);			
		} catch (NumberFormatException e) {
			consoleLogger.error(e.getClass().getName() +" : "+ e.getMessage());
			//HTMLLogger.error(e.getClass().getName() +" : "+ e.getMessage());
			return null;
		}
	}
	
	public static int createComputer(String name, String introduced, String discontinued, String pCompanyId){
		consoleLogger.info("access");
		Integer companyId = null;
		
		if(pCompanyId != null){
			try {
				companyId = Integer.parseInt(pCompanyId);
				
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
		Integer computerId = null;
		try {
			computerId = Integer.parseInt(id);
			
		} catch (NumberFormatException e) {
			consoleLogger.error(e.getClass().getName() +" : "+ e.getMessage());
			//HTMLLogger.error(e.getClass().getName() +" : "+ e.getMessage());
			return 0;
		}
		return new ComputerDAO().delete(computerId);
	}
	
	public static int updateComputer(String id, String name, String introduced, String discontinued, String pCompanyId){
		consoleLogger.info("access");
		Integer companyId = null;
		Integer computerId = null;
		
		if(id != null){
			try {
				computerId = Integer.parseInt(id);
				
			} catch (NumberFormatException e) {
				consoleLogger.error(e.getClass().getName() +" : "+ e.getMessage());
				//HTMLLogger.error(e.getClass().getName() +" : "+ e.getMessage());
				return 0;
			}
		}
		if(pCompanyId != null){
			try {
				companyId = Integer.parseInt(pCompanyId);
				
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
