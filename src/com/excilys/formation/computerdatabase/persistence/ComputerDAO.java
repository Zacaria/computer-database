package com.excilys.formation.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import com.excilys.formation.computerdatabase.mapper.ComputerMapper;
import com.excilys.formation.computerdatabase.model.Computer;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class ComputerDAO extends AbstractDAO implements Crudable<Computer>{

	@Override
	public ArrayList<Computer> findAll() {
		String query = "SELECT * FROM `computer-database-db`.computer;";
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
	public Computer findById(long id) {
		
		String query = "SELECT * FROM `computer-database-db`.computer WHERE id = ? ;";
		Connection connection = this.getConnection();
		
		Computer computer = null;
		
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			ComputerMapper mapper = new ComputerMapper();
			
			if(resultSet.first()){
				computer = mapper.map(resultSet);
			} else {
				throw new NoSuchElementException("No computer was found with the id " + id);
			}
		} catch (NoSuchElementException e){
			System.out.println(e.getMessage());
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
	
	@Override
	public Computer findLast() {
		
		String query = "SELECT * FROM `computer-database-db`.computer GROUP BY ID DESC LIMIT 1;";
		Connection connection = this.getConnection();
		
		Computer computer = null;
		
		Statement statement = null;
		
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
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

	@Override
	public long create(Computer toCreate) {
		String query = "INSERT INTO `computer-database-db`.computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);";
		Connection connection = this.getConnection();
		PreparedStatement statement = null;
		
		long newId = 0;
		
		try {
			if(toCreate.getName() == null){
				throw new Exception("ERROR Insert : Could not create an unnamed computer !");
			}
			if(toCreate.getCompanyId() == null){
				throw new Exception("ERROR Insert : Could not create a computer without it's company !!");
			}
			
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, toCreate.getName());
			statement.setDate(2, toCreate.getIntroduced());
			statement.setDate(3, toCreate.getDiscontinued());
			statement.setLong(4, toCreate.getCompanyId());
			
			int affectedRows = statement.executeUpdate();
			if(affectedRows == 0){
				throw new SQLException("Error Insert : failed, no rows affected");
			}
			ResultSet generatedId = statement.getGeneratedKeys();
			if(generatedId.next()){
				//auto unboxing
				//use that because id is never null
				newId = (long) generatedId.getLong(1);
			}
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("ERROR Insert : the referenced company does not exist");
			return 0;
			//e.printStackTrace();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e){
			System.out.println(e.getMessage());
			return 0;
		}
		
		return newId;
	}

	@Override
	public int delete(long id) {
		String query = "DELETE FROM `computer-database-db`.computer WHERE id = ?;";
		Connection connection = this.getConnection();
		PreparedStatement statement = null;
		
		Computer oldVersion = this.findById(id);
		if(oldVersion == null){
			return 0;
		}
		
		int affectedRows = 0;
		
		try {
			statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			
			affectedRows = statement.executeUpdate();
			
			if(affectedRows == 0){
				throw new SQLException("ERROR Delete : failed, no rows affected");
			}
						
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return affectedRows;
	}

	@Override
	public int update(Computer toUpdate) {
		//TODO : update from existing data
		
		String query = "UPDATE `computer-database-db`.computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";
		Connection connection = this.getConnection();
		PreparedStatement statement = null;
		
		int affectedRows = 0;
		
		System.out.println(toUpdate);
		Computer oldVersion = this.findById(toUpdate.getId());
		if(oldVersion == null){
			return 0;
		}
		
		try {
			//TODO : test if id not found
			ComputerMapper mapper = new ComputerMapper();
			mapper.merge(toUpdate, oldVersion);
			
			if(toUpdate.getName() == null){
				throw new Exception("ERROR Insert : Could not update to an unnamed computer !");
			}
			if(toUpdate.getCompanyId() == null){
				throw new Exception("ERROR Insert : Could not update to a computer without it's company !!");
			}
			
			statement = connection.prepareStatement(query);
			statement.setString(1, toUpdate.getName());
			statement.setDate(2, toUpdate.getIntroduced());
			statement.setDate(3, toUpdate.getDiscontinued());
			statement.setLong(4, toUpdate.getCompanyId());
			statement.setLong(5, toUpdate.getId());
			
			
			affectedRows = statement.executeUpdate();
			
			if(affectedRows == 0){
				throw new SQLException("ERROR Update : failed, no rows affected");
			}
						
		} catch (MySQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR Update : the referenced company does not exist");
			return 0;
			//e.printStackTrace();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return 0;
		} catch (Exception e){
			System.out.println(e.getMessage());
			return 0;
		}
		
		return affectedRows;
	}

}
