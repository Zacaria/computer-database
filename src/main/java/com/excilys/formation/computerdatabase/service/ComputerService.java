package com.excilys.formation.computerdatabase.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdatabase.exceptions.DAOException;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.model.Page;
import com.excilys.formation.computerdatabase.model.SelectOptions;
import com.excilys.formation.computerdatabase.persistence.ComputerDAO;
import com.excilys.formation.computerdatabase.persistence.ComputerDAOable;
import com.excilys.formation.computerdatabase.persistence.ConnectionFactory;

/**
 * This is a Singleton.
 * Implemented with an inner class Holder.
 * The holder is initialized only on the first call.
 * This is Initialization-on-demand holder idiom.
 * As the holder is a class, it's access is serial, i.e non-concurrent, i.e thread-safe.
 * See JLS class initialization specification.
 * @author Zacaria
 *
 */
public class ComputerService implements Servable<Computer> {
  
  public static final ThreadLocal<Connection> localConnection = new ThreadLocal<>();
  
  private static final Logger LOGGER =
      LoggerFactory.getLogger("com.excilys.formation.computerdatabase");

  private final ConnectionFactory connectionFactory;

  private final ComputerDAOable cdao;

  private ComputerService() {
    this.cdao = ComputerDAO.INSTANCE;
    this.connectionFactory = ConnectionFactory.getInstance();
//    this.localConnection = new ThreadLocal<>();
  }

  /**
   * This is initialized only when getInstance is called.
   * Which makes the Singleton lazy.
   * @author Zacaria
   *
   */
  private static class ComputerServiceHolder {
    private final static ComputerService instance = new ComputerService();
  }

  public static ComputerService getInstance() {
    return ComputerServiceHolder.instance;
  }

  @Override
  public int count() {
    LOGGER.info("count " + this.getClass());
    
    this.initConnection();

    int count = 0;
    try {
      count = this.cdao.count();
    } catch (DAOException e) {
      LOGGER.error(e.getMessage());
    } finally {
      this.closeConnection();
    }

    return count;
  }

  @Override
  public int count(SelectOptions options) {
    LOGGER.info("count with options" + this.getClass());
    
    this.initConnection();

    int count = 0;
    try {
      count = this.cdao.count(options);
    } catch (DAOException e) {
      LOGGER.error(e.getMessage());
    } finally {
      this.closeConnection();
    }

    return count;
  }

  @Override
  public Page<Computer> get(SelectOptions options) {
    LOGGER.info("get options " + this.getClass());

    this.initConnection();
    
    Page<Computer> computerPage = null;
    try {
      computerPage = new Page<>(options.getOffset(), this.cdao.find(options), this.cdao.count());
    } catch (DAOException e) {
      LOGGER.error(e.getMessage());
    } finally {
      this.closeConnection();
    }

    return computerPage;
  }

  @Override
  public Computer get(Long id) {
    LOGGER.info("get id " + this.getClass());
    
    this.initConnection();
    
    Computer result = null;
    try {
      result = this.cdao.find(id);
    } catch (DAOException e) {
      LOGGER.error(e.getMessage());
    } finally {
      this.closeConnection();
    }
    
    return result;
  }

  @Override
  public Long create(Computer computer) {
    LOGGER.info("create " + this.getClass());
    LOGGER.debug(computer.toString());
    
    this.initConnection();
    
    if (computer.getName() == null || computer.getName().isEmpty()) {
      LOGGER.error("ERROR Insert : Could not create an unnamed computer !");
      return null;
    }
    
    Long result = null;
    try {
      result = this.cdao.create(computer);
    } catch (DAOException e) {
      LOGGER.error(e.getMessage());
    } finally {
      this.closeConnection();
    }
    
    return result;
  }

  @Override
  public boolean delete(Long id) {
    LOGGER.info("delete " + this.getClass());
    if(id == null){
      return false;
    }
    
    LOGGER.debug("Delete Computer with id " + id);

    this.initConnection();
    
    boolean result = false;
    try {
      result = this.cdao.delete(id);
    } catch (DAOException e) {
      LOGGER.error("Delete : failed, no rows affected");
    } finally {
      this.closeConnection();
    }

    return result;
  }

  @Override
  public Computer update(Computer computer) {
    LOGGER.info("update " + this.getClass());
    LOGGER.debug(computer.toString());

    // Illegal
    if (computer.getName() == null || computer.getName().isEmpty()) {
      LOGGER.error("ERROR Update : Could not update to an unnamed computer !");
      return null;
    }
    if (computer.getCompany().getId() == null) {
      LOGGER.error("ERROR Update : Could not update to a computer without it's company !!");
      return null;
    }
    
    this.initConnection();
    
    Computer result = null;

    try {
      result = this.cdao.update(computer);
    } catch (DAOException e) {
      LOGGER.error(e.getMessage());
    } finally {
      this.closeConnection();
    }
    
    return result;
  }
  
  /**
   * Those to functions areNot in the interface.
   * It would be dangerous to expose that.
   */
  
  /**
   * Sets the connection object in the LocalThread and returns it.
   * @return
   */
  private Connection initConnection() {
    Connection connection = this.connectionFactory.getConnection();

    ComputerService.localConnection.set(connection);

    return connection;
  }
  /**
   * Closes the connection.
   * And cleans the LocalThread
   * @param connection
   */
  private void closeConnection(){
    this.connectionFactory.closeConnection(ComputerService.localConnection.get());
    ComputerService.localConnection.remove();
  }
}