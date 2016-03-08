package com.excilys.formation.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.formation.computerdatabase.mapper.CompanyMapper;
import com.excilys.formation.computerdatabase.model.Company;

public class CompanyDAO extends AbstractDAO implements Findable<Company>{
	
	/*public CompanyDAO(){
		super();
	}*/

	@Override
	public ArrayList<Company> findAll() {
		String query = "SELECT * FROM company;";
		Connection connection = this.getConnection();
				
		ArrayList<Company> companies = null;
		
		try (Statement statement = connection.createStatement()){
			
			ResultSet resultSet = statement.executeQuery(query);
			
			//No linkedList because it is slower for read
			companies = new ArrayList<>();
			
			CompanyMapper mapper = new CompanyMapper();
			
			Company company;
			while(resultSet.next()){
				company = mapper.map(resultSet);
				
				companies.add(company);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(connection);
		}
		
		return companies;
	}

	@Override
	public Company findById(int id) {

		String query = "SELECT * FROM company WHERE id = ? ;";
		Connection connection = this.getConnection();
		Company company = null;
		
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
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
			this.closeConnection(connection);
		}
		
		return company;
	}
	
}
