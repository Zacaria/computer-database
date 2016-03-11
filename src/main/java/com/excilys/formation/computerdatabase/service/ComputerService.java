package com.excilys.formation.computerdatabase.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.computerdatabase.mapper.Page;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.persistence.CompanyDAO;
import com.excilys.formation.computerdatabase.persistence.ComputerDAO;

public class ComputerService {
	
	private static final Logger consoleLogger = LogManager.getLogger("com.excilys.formation.computerdatabase");
	
	private ComputerService (){
		
	}
	
	private static class ComputerServiceHolder {
		private final static ComputerService instance = new ComputerService();
	}
	
	public static ComputerService getInstance(){
		return ComputerServiceHolder.instance;
	}
	
	public int countComputers(){
		consoleLogger.info("access");
		
		int count = new ComputerDAO().count();
		
		return count;
	}
	
	public Page<Computer> getComputers(int from, int max){
		consoleLogger.info("access");
		
		ComputerDAO cdao = new ComputerDAO();
		
		
		Page<Computer> computers = new Page<>(from, cdao.findWithRange(from, max), cdao.count());
		//List<Computer> computers = new ComputerDAO().findAll();
		
		
		return computers;
	}
	
	public Computer getComputer(String pId){
		consoleLogger.info("access");
		try {
			Long id = Long.parseLong(pId);
			
			return new ComputerDAO().findById(id);			
		} catch (NumberFormatException e) {
			consoleLogger.error(e.getClass().getName() +" : "+ e.getMessage());
			return null;
		}
	}
	
	public Long createComputer(String name, String introduced, String discontinued, String pCompanyId){
		consoleLogger.info("access");
		Long companyId = null;
		
		if(pCompanyId != null){
			try {
				companyId = Long.parseLong(pCompanyId);
				
			} catch (NumberFormatException e) {
				consoleLogger.error(e.getClass().getName() +" : "+ e.getMessage());
				return 0l;
			}
		}
		
		Company company = new CompanyDAO().findById(companyId);
		
		
		Computer computer = new Computer(name, introduced, discontinued, company);
		
		return new ComputerDAO().create(computer);
	}
	
	public int deleteComputer(String id){
		consoleLogger.info("access");
		Long computerId = null;
		try {
			computerId = Long.parseLong(id);
			
		} catch (NumberFormatException e) {
			consoleLogger.error(e.getClass().getName() +" : "+ e.getMessage());
			return 0;
		}
		return new ComputerDAO().delete(computerId);
	}
	
	public int updateComputer(String id, String name, String introduced, String discontinued, String pCompanyId){
		consoleLogger.info("access");
		Long companyId = null;
		Long computerId = null;
		
		if(id != null){
			try {
				computerId = Long.parseLong(id);
				
			} catch (NumberFormatException e) {
				consoleLogger.error(e.getClass().getName() +" : "+ e.getMessage());
				return 0;
			}
		}
		if(pCompanyId != null){
			try {
				companyId = Long.parseLong(pCompanyId);
				
			} catch (NumberFormatException e) {
				consoleLogger.error(e.getClass().getName() +" : "+ e.getMessage());
				return 0;
			}
		}
		
		Company company = new CompanyDAO().findById(companyId);
		
		Computer computer = new Computer(computerId, name, introduced, discontinued, company);
		
		return new ComputerDAO().update(computer);
	}
}
