package com.excilys.formation.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.excilys.formation.computerdatabase.mapper.CompanyMapper;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Computer;

public class CompanyDAO implements Findable<Company>{
	private final static String fields = "company.id as company_id, company.name as company_name";
	
	private final String countQuery = "SELECT COUNT(*) as count from `computer-database-db`.company;";
	private final static String findAllQuery = "SELECT " + CompanyDAO.fields + " FROM `computer-database-db`.company;";
	private final static String findWithRangeQuery = "SELECT " + CompanyDAO.fields + " FROM `computer-database-db`.company limit ?, ?;";
	private final static String findByIdQuery = "SELECT " + CompanyDAO.fields + " FROM `computer-database-db`.company WHERE id = ? ;";
	
	private ConnectionFactory connectionFactory;
	
	public CompanyDAO() {
		this.connectionFactory = ConnectionFactory.getInstance();
	}
	
	@Override
	public int count(){
		Connection connection = connectionFactory.getConnection();
		
		int count = 0;
		
		try (Statement statement = connection.createStatement()){
			
			ResultSet resultSet = statement.executeQuery(countQuery);
			
			if(resultSet.first()){
				count = resultSet.getInt("count");
			}			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionFactory.closeConnection(connection);
		}
		
		return count;
	}
	
	@Override
	public List<Company> findAll() {
		Connection connection = connectionFactory.getConnection();
				
		List<Company> companies = null;
		
		try (Statement statement = connection.createStatement()){
			
			ResultSet resultSet = statement.executeQuery(findAllQuery);
					
			CompanyMapper mapper = new CompanyMapper();
			
			companies = mapper.mapList(resultSet);			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionFactory.closeConnection(connection);
		}
		
		return companies;
	}
	
	@Override
	public List<Company> findWithRange(int from, int max){
		Connection connection = connectionFactory.getConnection();
		List<Company> companies = null;
		
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(findWithRangeQuery);
			statement.setLong(1, from);
			statement.setLong(2, max);
			ResultSet resultSet = statement.executeQuery();
			CompanyMapper mapper = new CompanyMapper();
			
			companies = mapper.mapList(resultSet);
			
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
		
		return companies;
	}

	@Override
	public Company findById(Long id) {
		Connection connection = connectionFactory.getConnection();
		Company company = null;
		
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(findByIdQuery);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			CompanyMapper mapper = new CompanyMapper();
			
			if(resultSet.first()){
				company = mapper.map(resultSet);
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
		
		return company;
	}

	@Override
	public Company findLast() {
		throw new UnsupportedOperationException();
	}
	
}
