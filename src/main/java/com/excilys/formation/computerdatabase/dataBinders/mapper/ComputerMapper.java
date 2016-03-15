package com.excilys.formation.computerdatabase.dataBinders.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.computerdatabase.model.Computer;

public class ComputerMapper implements Mapable<Computer>{
	private static final Logger LOGGER = LogManager.getLogger("com.excilys.formation.computerdatabase");
	
	private final static String COMPUTER_ID = "computer_id";
	private final static String COMPUTER_NAME = "computer_name";
	private final static String COMPUTER_INTRODUCED = "introduced";
	private final static String COMPUTER_DISCONTINUED = "discontinued";
	
	/**
	 * Maps a ResultSet into an Object
	 */
	@Override
	public Computer map(ResultSet rs) {
		Computer computer = new Computer();
		
		try {
			computer.setId(rs.getLong(COMPUTER_ID));
			if(rs.getString(COMPUTER_NAME) != null){
				computer.setName(rs.getString(COMPUTER_NAME));
			}
			if(rs.getTimestamp(COMPUTER_INTRODUCED) != null){
				computer.setIntroduced(rs.getDate(COMPUTER_INTRODUCED).toLocalDate());
			}
			if(rs.getTimestamp(COMPUTER_DISCONTINUED) != null){
				computer.setDiscontinued(rs.getDate(COMPUTER_DISCONTINUED).toLocalDate());
			}
			
			CompanyMapper companyMapper = new CompanyMapper();
			computer.setCompany(companyMapper.map(rs));
			
		} catch (Exception e) {
			LOGGER.error("mapping computer error");
		}
		
		return computer;
	}
	
	@Override
	public List<Computer> mapList(ResultSet rs) {
		List<Computer> computers = new ArrayList<>();
		
		Computer computer = null;
		try {
			while(rs.next()){
				computer = this.map(rs);
				
				computers.add(computer);
			}
		} catch (SQLException e) {
			LOGGER.error("list mapping computer error");
		}
		return computers;
	}
	
	/**
	 * Merges the right computer into the left computer
	 * It means that each null value of the left will be replaced by the corresponding one in the right
	 * @param left receptacle
	 * @param right input
	 * @return nothing, the modification is operated by reference
	 */
	public void merge(Computer left, Computer right){
		if(left.getId() == null){
			left.setId(right.getId());
		}
		if(left.getName() == null){
			left.setName(right.getName());
		}
		if(left.getIntroduced() == null){
			left.setIntroduced(right.getIntroduced());
		}
		if(left.getDiscontinued() == null){
			left.setDiscontinued(right.getDiscontinued());
		}
		if(left.getCompany() == null){
			left.setCompany(right.getCompany());
		}
	}

}
