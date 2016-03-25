package com.excilys.formation.computerdatabase.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
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

  private final static Logger LOGGER =
      LoggerFactory.getLogger("com.excilys.formation.computerdatabase");
  
  public final static ThreadLocal<Connection> local = new ThreadLocal<>();

  private BoneCP connectionPool = null;

  private ConnectionFactory() {
    Properties prop = new Properties();
    String propFilePublic = "db.public.properties";
    String propFilePrivate = "db.private.properties";

    InputStream publicPropStream = getClass().getClassLoader().getResourceAsStream(propFilePublic);
    InputStream privatePropStream =
        getClass().getClassLoader().getResourceAsStream(propFilePrivate);
    try {
      prop.load(publicPropStream);

      String url = prop.getProperty("url");
      String database = prop.getProperty("database");
      String options = prop.getProperty("options");

      prop.load(privatePropStream);

      String user = prop.getProperty("user");
      String password = prop.getProperty("password");

      // Register JDBC Driver.
      Class.forName(prop.getProperty("driver"));

      BoneCPConfig config = new BoneCPConfig();

      config.setJdbcUrl(url + database + "?" + options);
      config.setUsername(user);
      config.setPassword(password);

      /**
       * Tomcat default max threads : 200
       * Mysql default max parallel conection : 151
       * 
       * So set the total pool to : partitions * maxPerPool < 151 = 150 
       */
      
      config.setMinConnectionsPerPartition(5);
      config.setMaxConnectionsPerPartition(30);

      /**
       * Each partition has a number of connections.
       * The number of partitions allows better concurrency efficiency on delivering connection.
       */
      config.setPartitionCount(5);

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
  
  public static void initLocalConnection(Connection connection){
    ConnectionFactory.local.set(connection);
  }
  
  public static Connection getLocalConnection(){
    return ConnectionFactory.local.get();
  }

  public Connection getConnection() {
    Connection connection;
    
    LOGGER.debug("Requesting new db connection");

    try {
      connection = connectionPool.getConnection();
    } catch (SQLException e) {
      throw new DBConnectionException("Open connection failed", e);
    }
    
    return connection;
  }

  public void closeLocalConnection() {
    try {
      ConnectionFactory.local.get().close();
      ConnectionFactory.local.remove();
      LOGGER.debug("Db connection closed");
    } catch (SQLException e) {
      throw new DBConnectionException("Connection did not close properly !", e);
    }
  }
}