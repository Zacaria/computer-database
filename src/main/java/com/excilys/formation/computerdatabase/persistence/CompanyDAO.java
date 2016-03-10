package com.excilys.formation.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.excilys.formation.computerdatabase.mapper.CompanyMapper;
import com.excilys.formation.computerdatabase.model.Company;

public class CompanyDAO implements Findable<Company>{
	
	private ConnectionFactory connectionFactory;
	
	public CompanyDAO() {
		this.connectionFactory = ConnectionFactory.getInstance();
	}
	
	@Override
	public List<Company> findAll() {
		String query = "SELECT * FROM `computer-database-db`.company;";
		Connection connection = connectionFactory.getConnection();
				
		List<Company> companies = null;
		
		try (Statement statement = connection.createStatement()){
			
			ResultSet resultSet = statement.executeQuery(query);
					
			CompanyMapper mapper = new CompanyMapper();
			
			//No linkedList because it is slower for read
			companies = mapper.mapList(resultSet);			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionFactory.closeConnection(connection);
		}
		
		return companies;
	}

	@Override
	public Company findById(Long id) {

		String query = "SELECT * FROM `computer-database-db`.company WHERE id = ? ;";
		Connection connection = connectionFactory.getConnection();
		Company company = null;
		
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(query);
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
