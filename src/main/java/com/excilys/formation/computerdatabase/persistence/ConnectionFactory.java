package com.excilys.formation.computerdatabase.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdatabase.exceptions.DBConnectionException;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

/**
 * This Factory is a Singleton.
 * It's role is to provide Connection instances.
 * 
 * @author Zacaria
 *
 */
public class ConnectionFactory {

	private final static Logger LOGGER = LoggerFactory.getLogger("com.excilys.formation.computerdatabase");

	private String url;
	private String database;
	private String options;

	private String user;
	private String password;
	
	private BoneCP connectionPool = null;

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
			
			// Register JDBC Driver.
			Class.forName(prop.getProperty("driver"));
			
			BoneCPConfig config = new BoneCPConfig();
			
			config.setJdbcUrl(this.url + "?" + this.options);
			config.setUsername(this.user);
			config.setPassword(this.password);
			
			config.setMinConnectionsPerPartition(5);
			config.setMaxConnectionsPerPartition(10);
			config.setPartitionCount(2);
			
			this.connectionPool = new BoneCP(config);

		} catch (IOException | ClassNotFoundException | SQLException e) {
			throw new DBConnectionException(e);
		}
	}

	/**
	 * Only loaded on first call to getInstance.
	 * 
	 * @author Zacaria
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
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			throw new DBConnectionException(e);
		}

		return connection;
	}

	public void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new DBConnectionException("Connection did not close properly !");
		}
	}
}