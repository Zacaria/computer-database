package com.excilys.formation.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.NoSuchElementException;

import com.excilys.formation.computerdatabase.mapper.ComputerMapper;
import com.excilys.formation.computerdatabase.model.Computer;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class ComputerDAO implements Crudable<Computer>{
	
	
	private final static String fields = "computer.id as computer_id, computer.name as computer_name, introduced, discontinued, company_id";
	
	private final String countQuery = "SELECT COUNT(*) as total from `computer-database-db`.computer;";
	private final String findAllQuery = "SELECT " + ComputerDAO.fields + " , company.id as company_id, company.name as company_name FROM `computer-database-db`.computer left join `computer-database-db`.company on computer.company_id = company.id;";
	private final String findWithRangeQuery = "SELECT " + ComputerDAO.fields + ", company.id as company_id, company.name as company_name FROM `computer-database-db`.computer left join `computer-database-db`.company on computer.company_id = company.id limit ?, ?;";
	private final String findByIdQuery = "SELECT " + ComputerDAO.fields + ", company.id as company_id, company.name as company_name FROM `computer-database-db`.computer left join `computer-database-db`.company on computer.company_id = company.id where computer.id = ?;";
	private final String findLastQuery = "SELECT " + ComputerDAO.fields + ", company.id as company_id, company.name as company_name FROM `computer-database-db`.computer left join `computer-database-db`.company on computer.company_id = company.id GROUP BY computer.id DESC LIMIT 1;";
	private final String insertQuery = "INSERT INTO `computer-database-db`.computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);";
	private final String updateQuery = "UPDATE `computer-database-db`.computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";
	private final String deleteQuery = "DELETE FROM `computer-database-db`.computer WHERE id = ?;";
	
	private ConnectionFactory connectionFactory;
	
	public ComputerDAO(){
		this.connectionFactory = ConnectionFactory.getInstance();
	}
	
	@Override
	public int count(){
		Connection connection = connectionFactory.getConnection();
		
		int count = 0;
		
		try (Statement statement = connection.createStatement()){
			
			ResultSet resultSet = statement.executeQuery(countQuery);
			
			if(resultSet.first()){
				count = (int) resultSet.getInt("total");
			}			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionFactory.closeConnection(connection);
		}
		
		return count;
	}

	@Override
	public List<Computer> findAll() {
		Connection connection = connectionFactory.getConnection();
				
		List<Computer> computers = null;
		
		try (Statement statement = connection.createStatement()){
			
			ResultSet resultSet = statement.executeQuery(findAllQuery);
			ComputerMapper mapper = new ComputerMapper();
			
			computers = mapper.mapList(resultSet);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionFactory.closeConnection(connection);
		}
		
		return computers;
	}
	
	@Override
	public List<Computer> findWithRange(int from, int max){
		Connection connection = connectionFactory.getConnection();
				
		List<Computer> computers = null;
		
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(findWithRangeQuery);
			statement.setInt(1, from);
			statement.setInt(2, max);
			
			ResultSet resultSet = statement.executeQuery();
			ComputerMapper mapper = new ComputerMapper();
			
			computers = mapper.mapList(resultSet);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionFactory.closeConnection(connection);
		}
		
		return computers;
	}

	@Override
	public Computer findById(Long id) {
		Connection connection = connectionFactory.getConnection();
		
		Computer computer = null;
		
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(findByIdQuery);
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
			connectionFactory.closeConnection(connection);
		}
		
		return computer;
	}
	
	@Override
	public Computer findLast() {
		Connection connection = connectionFactory.getConnection();
		
		Computer computer = null;
		
		Statement statement = null;
		
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(findLastQuery);
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
			connectionFactory.closeConnection(connection);
		}
		
		return computer;
	}

	@Override
	public Long create(Computer toCreate) {
		Connection connection = connectionFactory.getConnection();
		PreparedStatement statement = null;
		
		Long newId = null;
		
		try {
			if(toCreate.getName() == null){
				throw new Exception("ERROR Insert : Could not create an unnamed computer !");
			}
			if(toCreate.getCompany().getId() == null){
				throw new Exception("ERROR Insert : Could not create a computer without it's company !!");
			}
			
			statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, toCreate.getName());
			statement.setDate(2, Date.valueOf(toCreate.getIntroduced()));
			statement.setDate(3, Date.valueOf(toCreate.getDiscontinued()));
			statement.setLong(4, toCreate.getCompany().getId());
			
			int affectedRows = statement.executeUpdate();
			if(affectedRows == 0){
				throw new SQLException("Error Insert : failed, no rows affected");
			}
			ResultSet generatedId = statement.getGeneratedKeys();
			if(generatedId.next()){
				newId = generatedId.getLong(1);
			}
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("ERROR Insert : the referenced company does not exist");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e){
			System.out.println(e.getMessage());
		} finally {
			if(statement != null){
				try {
					statement.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			connectionFactory.closeConnection(connection);
		}
		
		return newId;
	}

	@Override
	public int delete(Long id) {
		Connection connection = connectionFactory.getConnection();
		PreparedStatement statement = null;
		
		Computer oldVersion = this.findById(id);
		if(oldVersion == null){
			return 0;
		}
		
		int affectedRows = 0;
		
		try {
			statement = connection.prepareStatement(deleteQuery);
			statement.setLong(1, id);
			
			affectedRows = statement.executeUpdate();
			
			if(affectedRows == 0){
				throw new SQLException("ERROR Delete : failed, no rows affected");
			}
						
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			if(statement != null){
				try {
					statement.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			connectionFactory.closeConnection(connection);
		}
		
		return affectedRows;
	}

	@Override
	public int update(Computer toUpdate) {
		Connection connection = connectionFactory.getConnection();
		PreparedStatement statement = null;
		
		int affectedRows = 0;
		
		System.out.println(toUpdate);
		Computer oldVersion = this.findById(toUpdate.getId());
		if(oldVersion == null){
			return 0;
		}
		
		try {
			ComputerMapper mapper = new ComputerMapper();
			mapper.merge(toUpdate, oldVersion);
			
			if(toUpdate.getName() == null){
				throw new Exception("ERROR Insert : Could not update to an unnamed computer !");
			}
			if(toUpdate.getCompany().getId() == null){
				throw new Exception("ERROR Insert : Could not update to a computer without it's company !!");
			}
			
			statement = connection.prepareStatement(updateQuery);
			statement.setString(1, toUpdate.getName());
			statement.setDate(2, Date.valueOf(toUpdate.getIntroduced()));
			statement.setDate(3, Date.valueOf(toUpdate.getDiscontinued()));
			statement.setLong(4, toUpdate.getCompany().getId());
			statement.setLong(5, toUpdate.getId());
			
			
			affectedRows = statement.executeUpdate();
			
			if(affectedRows == 0){
				throw new SQLException("ERROR Update : failed, no rows affected");
			}
						
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("ERROR Update : the referenced company does not exist");
			return 0;
			//e.printStackTrace();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return 0;
		} catch (Exception e){
			System.out.println(e.getMessage());
			return 0;
		} finally {
			if(statement != null){
				try {
					statement.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			connectionFactory.closeConnection(connection);
		}
		
		return affectedRows;
	}

}
