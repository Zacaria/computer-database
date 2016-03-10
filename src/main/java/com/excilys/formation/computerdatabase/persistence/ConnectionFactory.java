package com.excilys.formation.computerdatabase.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//Found at : http://thecodersbreakfast.net/index.php?post/2008/02/25/26-de-la-bonne-implementation-du-singleton-en-java

/**
 * This is a Thread-Safe Singleton
 * It Provides an instance of ConnectionFactory
 * Which generates instances of Connections
 * @author excilys
 *
 */
public class ConnectionFactory{
	
	private static String url;
	private static String database;
	private static String options;
	
	private static String user;
	private static String password;
	
	private ConnectionFactory() {
		Properties prop = new Properties();
		String propFilePublic = "db.public.properties";
		String propFilePrivate = "db.private.properties";
		
		InputStream publicPropStream = getClass().getClassLoader().getResourceAsStream(propFilePublic);
		InputStream privatePropStream = getClass().getClassLoader().getResourceAsStream(propFilePrivate);
		try{
			
			if(publicPropStream != null && privatePropStream != null){
				prop.load(publicPropStream);
				
				ConnectionFactory.url = prop.getProperty("url");
				ConnectionFactory.database = prop.getProperty("database");
				ConnectionFactory.options = prop.getProperty("options");
				
				prop.load(privatePropStream);
				
				ConnectionFactory.user = prop.getProperty("user");
				ConnectionFactory.password = prop.getProperty("password");
				
			} else {
				//throw new RuntimeErrorException("property file " + propFileName + " not found")
				throw new FileNotFoundException("property file " + propFilePublic + " not found");
			}
			
		} catch(FileNotFoundException e){
			throw new RuntimeException();
		} catch (IOException e) {
			throw new RuntimeException();
		}
		
	}
	
	/**
	 * Only loaded on first call to getInstance
	 * @author excilys
	 *
	 */
	private static class ConnectionFactoryHolder {
		private final static ConnectionFactory instance = new ConnectionFactory();
	}
	
	public static ConnectionFactory getInstance(){
		return ConnectionFactoryHolder.instance;
	}
	
	public Connection getConnection(){
		
		Connection connection = null;
		try {
			
			
			// Register JDBC Driver
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					ConnectionFactory.url + ConnectionFactory.database + "?" + ConnectionFactory.options, 
					ConnectionFactory.user, 
					ConnectionFactory.password
					);
		} catch (SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException();
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