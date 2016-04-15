package com.excilys.formation.computerdatabase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.computerdatabase.exceptions.DAOException;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.model.Page;
import com.excilys.formation.computerdatabase.model.SelectOptions;
import com.excilys.formation.computerdatabase.persistence.ComputerDAOable;

@Service("ComputerService")
public class ComputerService implements Servable<Computer> {
  
  private static final Logger LOGGER =
      LoggerFactory.getLogger("com.excilys.formation.computerdatabase");

  @Autowired
  private ComputerDAOable cdao;

  public ComputerService() {
    
  }

  @Override
  public int count() {
    LOGGER.info("count " + this.getClass());

    int count = 0;
    try {
      count = this.cdao.count();
    } catch (DAOException e) {
      LOGGER.error(e.getMessage());
    }

    return count;
  }

  @Override
  public int count(SelectOptions options) {
    LOGGER.info("count with options" + this.getClass());

    int count = 0;
    try {
      count = this.cdao.count(options);
    } catch (DAOException e) {
      LOGGER.error(e.getMessage());
    } 

    return count;
  }

  @Override
  public Page<Computer> get(SelectOptions options) {
    LOGGER.info("get options " + this.getClass());
  
    Page<Computer> computerPage = null;
    try {
      computerPage = new Page<>(options.getOffset(), this.cdao.find(options), this.cdao.count());
    } catch (DAOException e) {
      LOGGER.error(e.getMessage());
    }

    return computerPage;
  }

  @Override
  public Computer get(Long id) {
    LOGGER.info("get id " + this.getClass());
    
    Computer result = null;
    try {
      result = this.cdao.find(id);
    } catch (DAOException e) {
      LOGGER.error(e.getMessage());
    }
    
    return result;
  }

  @Override
  @Transactional
  public Long create(Computer computer) {
    LOGGER.info("create " + this.getClass());
    LOGGER.debug(computer.toString());
  
    if (computer.getName() == null || computer.getName().isEmpty()) {
      LOGGER.error("ERROR Insert : Could not create an unnamed computer !");
      return null;
    }
    
    Long result = null;
    try {
      result = this.cdao.create(computer);
    } catch (DAOException e) {
      LOGGER.error(e.getMessage());
    }
    
    return result;
  }

  @Override
  @Transactional
  public boolean delete(Long id) {
    LOGGER.info("delete " + this.getClass());
    if(id == null){
      return false;
    }
    
    LOGGER.debug("Delete Computer with id " + id);
   
    boolean result = false;
    try {
      result = this.cdao.delete(id);
    } catch (DAOException e) {
      LOGGER.error("Delete : failed, no rows affected");
    }

    return result;
  }

  @Override
  @Transactional
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
  
    Computer result = null;

    try {
      result = this.cdao.update(computer);
    } catch (DAOException e) {
      LOGGER.error(e.getMessage());
    }
    
    return result;
  }

  public void setCdao(ComputerDAOable cdao) {
    this.cdao = cdao;
  }
}