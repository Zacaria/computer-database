package com.excilys.formation.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

import com.excilys.formation.computerdatabase.mapper.ComputerMapper;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Computer;

public class ComputerDAO extends AbstractDAO implements Crudable<Computer>{

	@Override
	public ArrayList<Computer> findAll() {
		String query = "SELECT * FROM computer;";
		Connection connection = this.getConnection();
				
		ArrayList<Computer> computers = null;
		
		Statement statement = null;
		
		try {
			statement = connection.createStatement();
			
			ResultSet resultSet = statement.executeQuery(query);
			ComputerMapper mapper = new ComputerMapper();
			
			//No linkedList because it is slower for read
			computers = new ArrayList<>();
			
			Computer computer;
			while(resultSet.next()){
				computer = mapper.map(resultSet);
				
				computers.add(computer);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(statement != null){
				try {
					statement.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			this.closeConnection(connection);
		}
		
		return computers;
	}

	@Override
	public Computer findById(int id) {
		
		String query = "SELECT * FROM computer WHERE id = ? ;";
		Connection connection = this.getConnection();
		
		Computer computer = null;
		
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			ComputerMapper mapper = new ComputerMapper();
			
			if(resultSet.first()){
				computer = mapper.map(resultSet);
				
			}
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			if(statement != null){
				try {
					statement.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			this.closeConnection(connection);
		}
		
		return computer;
	}

	/*@Override
	public int update(Computer toUpdate) {
		//TODO : handle id not found
		//String query = "SELECT * FROM computer WHERE id = ? ;";
		String query = "UPDATE computer SET name = WHERE id = ?";
		Connection connection = this.getConnection();
		
		Computer computer = null;
		
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			ComputerMapper mapper = new ComputerMapper();
			
			if(resultSet.first()){
				computer = mapper.map(resultSet);
				
			}
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			if(statement != null){
				try {
					statement.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			this.closeConnection(connection);
		}
		
		return id;
		return null;
		throw new ();
	}*/

	@Override
	public int create(Computer toCreate) {
		// TODO handle already exist
		// TODO handle no company with this id found
		String query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);";
		Connection connection = this.getConnection();
		PreparedStatement statement = null;
		
		//Check if the company exists
		CompanyDAO companyDao = new CompanyDAO();
		Company company = companyDao.findById(toCreate.getCompanyId());
		
		try {
			if(company == null){
				throw new Exception("ERROR : the referenced company does not exist");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int affectedRows = 0;
		
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, toCreate.getName());
			statement.setDate(2, toCreate.getIntroduced());
			statement.setDate(3, toCreate.getDiscontinued());
			statement.setInt(4, toCreate.getCompanyId());
			
			affectedRows = statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return affectedRows;
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int update(Computer toUpdate) {
		// TODO Auto-generated method stub
		return 0;
	}

}
