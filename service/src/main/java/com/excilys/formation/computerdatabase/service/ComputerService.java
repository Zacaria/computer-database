package com.excilys.formation.computerdatabase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.model.Page;
import com.excilys.formation.computerdatabase.model.SelectOptions;
import com.excilys.formation.computerdatabase.persistence.IComputerDAO;

@Service("ComputerService")
public class ComputerService implements IService<Computer> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);

  @Autowired
  private IComputerDAO cdao;

  public ComputerService() {

  }

  @Override
  public int count() {
    LOGGER.info("count " + this.getClass());

    return this.cdao.count();
  }

  @Override
  public int count(SelectOptions options) {
    LOGGER.info("count with options" + this.getClass());

    return this.cdao.count(options);
  }

  @Override
  public Page<Computer> get(SelectOptions options) {
    LOGGER.info("get options " + this.getClass());

    return new Page<>(options.getOffset(), this.cdao.find(options), this.cdao.count());
  }

  @Override
  public Computer get(Long id) {
    LOGGER.info("get id " + this.getClass());

    return this.cdao.find(id);
  }

  @Override
  @Transactional
  public Long create(Computer computer) {
    LOGGER.info("create " + this.getClass());
    LOGGER.debug(computer.toString());

    if (computer.getName() == null || computer.getName()
      .isEmpty()) {
      LOGGER.error("ERROR Insert : Could not create an unnamed computer !");
      return null;
    }

    return this.cdao.create(computer);
  }

  @Override
  @Transactional
  public boolean delete(Long id) {
    LOGGER.info("delete " + this.getClass());
    if (id == null) {
      return false;
    }

    LOGGER.debug("Delete Computer with id " + id);

    return this.cdao.delete(id);
  }

  @Override
  @Transactional
  public Computer update(Computer computer) {
    LOGGER.info("update " + this.getClass());
    LOGGER.debug(computer.toString());

    // Illegal
    if (computer.getName() == null || computer.getName()
      .isEmpty()) {
      LOGGER.error("ERROR Update : Could not update to an unnamed computer !");
      return null;
    }
    if (computer.getCompany()
      .getId() == null) {
      LOGGER.error("ERROR Update : Could not update to a computer without it's company !!");
      return null;
    }

    return this.cdao.update(computer);
  }
}