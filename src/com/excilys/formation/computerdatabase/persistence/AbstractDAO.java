package com.excilys.formation.computerdatabase.persistence;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractDAO {
	//protected Connection connection = null;
	private String cnxString = "jdbc:mysql://localhost/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private String user = "root";
	private String pass = "root";
	
	
	public Connection getConnection(){
		Connection connection = null;
		try {
			// Register JDBC Driver
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(this.cnxString, user, pass);
		} catch (SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public void closeConnection(Connection connection){
		try {
			if(connection != null){
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
