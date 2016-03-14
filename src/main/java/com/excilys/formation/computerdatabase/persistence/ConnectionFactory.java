package com.excilys.formation.computerdatabase.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.computerdatabase.exceptions.DBConnectionException;

/**
 * This is a Thread-Safe Singleton It Provides an instance of ConnectionFactory
 * Which generates instances of Connections
 * 
 * @author excilys
 *
 */
public class ConnectionFactory {

	private final static Logger LOGGER = LogManager.getLogger("com.excilys.formation.computerdatabase");

	private String url;
	private String database;
	private String options;

	private String user;
	private String password;

	private ConnectionFactory() {
		Properties prop = new Properties();
		String propFilePublic = "db.public.properties";
		String propFilePrivate = "db.private.properties";

		InputStream publicPropStream = getClass().getClassLoader().getResourceAsStream(propFilePublic);
		InputStream privatePropStream = getClass().getClassLoader().getResourceAsStream(propFilePrivate);
		try {
			prop.load(publicPropStream);

			this.url = prop.getProperty("url");
			this.database = prop.getProperty("database");
			this.options = prop.getProperty("options");

			prop.load(privatePropStream);

			this.user = prop.getProperty("user");
			this.password = prop.getProperty("password");

		} catch (IOException e) {
			throw new DBConnectionException(e);
		}

	}

	/**
	 * Only loaded on first call to getInstance
	 * 
	 * @author excilys
	 *
	 */
	private static class ConnectionFactoryHolder {
		private final static ConnectionFactory instance = new ConnectionFactory();
	}

	public static ConnectionFactory getInstance() {
		return ConnectionFactoryHolder.instance;
	}

	public Connection getConnection() {
		Connection connection;

		try {
			// Register JDBC Driver
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(this.url + this.database + "?" + this.options, this.user,
					this.password);
		} catch (SQLException | ClassNotFoundException e) {
			throw new DBConnectionException(e);
		}

		return connection;
	}

	public void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (Exception e) {
			LOGGER.error("Connection did not close properly !");
			throw new DBConnectionException("Connection did not close properly !");
		}
	}
}