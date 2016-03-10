package com.excilys.formation.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Found at : http://thecodersbreakfast.net/index.php?post/2008/02/25/26-de-la-bonne-implementation-du-singleton-en-java

/**
 * This is a Thread-Safe Singleton
 * It Provides an instance of ConnectionFactory
 * Which generates instances of Connections
 * @author excilys
 *
 */
public class ConnectionFactory {
	
	private static String cnxString = "jdbc:mysql://localhost/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static String user = "root";
	private static String pass = "root";
	
	private ConnectionFactory(){
		
	}
	
	private static class ConnectionFactoryHolder {
		private final static ConnectionFactory instance = new ConnectionFactory();
	}
	
	public static synchronized ConnectionFactory getInstance(){
		return ConnectionFactoryHolder.instance;
	}
	
	public Connection getConnection(){
		Connection connection = null;
		try {
			// Register JDBC Driver
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(ConnectionFactory.cnxString, ConnectionFactory.user, ConnectionFactory.pass);
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
