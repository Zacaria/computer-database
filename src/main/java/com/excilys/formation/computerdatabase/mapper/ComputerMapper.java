package com.excilys.formation.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.computerdatabase.model.Computer;

public class ComputerMapper implements Mapable<Computer>{

	
	/**
	 * Maps a ResultSet into an Object
	 */
	@Override
	public Computer map(ResultSet rs) {
		Computer computer = new Computer();
		
		try {
			computer.setId(rs.getLong("id"));
			if(rs.getString("name") != null){
				computer.setName(rs.getString("name"));
			}
			if(rs.getTimestamp("introduced") != null){
				computer.setIntroduced(rs.getDate("introduced").toLocalDate());
			}
			if(rs.getTimestamp("discontinued") != null){
				computer.setDiscontinued(rs.getDate("discontinued").toLocalDate());
			}
			
			//because of the db constraints, the foreign key cannot be null
			computer.setCompanyId(rs.getLong("company_id"));		
			
			
		} catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
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
		if(left.getCompanyId() == null){
			left.setCompanyId(right.getCompanyId());
		}
	}

}
