package com.excilys.formation.computerdatabase.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Page;
import com.excilys.formation.computerdatabase.model.SelectOptions;
import com.excilys.formation.computerdatabase.persistence.ICompanyDAO;
import com.excilys.formation.computerdatabase.persistence.IComputerDAO;

@Service("CompanyService")
public class CompanyService implements IService<Company> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

  @Resource(name = "CompanyDAO")
  private ICompanyDAO cdao;

  @Resource(name = "ComputerDAO")
  private IComputerDAO computerDAO;

  public CompanyService() {
  }

  @Override
  public Page<Company> get(SelectOptions options) {
    LOGGER.info("get with options" + this.getClass());

    return new Page<>(options.getOffset(), this.cdao.find(options), this.cdao.count());
  }

  @Override
  public Company get(Long id) {
    LOGGER.info("get id" + this.getClass());

    return this.cdao.find(id);
  }

  @Override
  public int count() {
    LOGGER.info("count " + this.getClass());

    return this.cdao.count();
  }

  @Override
  @Transactional
  public boolean delete(Long id) {
    LOGGER.info("delete " + this.getClass());

    boolean success = false;

    success = this.computerDAO.deleteWithCompanyId(id);

    success = this.cdao.delete(id);

    return success;
  }
}
