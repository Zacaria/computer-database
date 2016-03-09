package com.excilys.formation.computerdatabase.mapper;

import java.sql.Date;
import java.sql.ResultSet;

import com.excilys.formation.computerdatabase.model.Computer;

public class ComputerMapper implements Mapable<Computer>{

	@Override
	public Computer map(ResultSet rs) {
		Computer computer = new Computer();
		
		try {
			computer.setId(rs.getInt("id"));
			if(rs.getString("name") != null){
				computer.setName(rs.getString("name"));
			}
			if(rs.getTimestamp("introduced") != null){
				computer.setIntroduced(new Date(rs.getTimestamp("introduced").getTime()));
			}
			if(rs.getTimestamp("discontinued") != null){
				computer.setDiscontinued(new Date(rs.getTimestamp("discontinued").getTime()));
			}
			
			//because of the db constraints, the foreign key cannot be null
			computer.setCompanyId(rs.getInt("company_id"));		
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return computer;
	}
	
	/**
	 * Merges the right computer into the left computer
	 * It means that each null value of the left will be replaced by the corresponding one in the right
	 * @param left receptacle
	 * @param right input
	 * @return
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
		if(left.getCompanyId() == null){
			left.setCompanyId(right.getCompanyId());
		}
	}

}
