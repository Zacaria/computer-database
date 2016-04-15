package com.excilys.formation.computerdatabase.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.computerdatabase.exceptions.DAOException;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Page;
import com.excilys.formation.computerdatabase.model.SelectOptions;
import com.excilys.formation.computerdatabase.persistence.ICompanyDAO;
import com.excilys.formation.computerdatabase.persistence.IComputerDAO;

@Service("CompanyService")
public class CompanyService implements IService<Company> {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(CompanyService.class);

  @Resource(name = "CompanyDAO")
  private ICompanyDAO cdao;

  @Resource(name = "ComputerDAO")
  private IComputerDAO computerDAO;

  public CompanyService() {
  }

  @Override
  public Page<Company> get(SelectOptions options) {
    LOGGER.info("get with options" + this.getClass());

    Page<Company> companyPage = null;

    try {
      companyPage = new Page<>(options.getOffset(), this.cdao.find(options), this.cdao.count());

    } catch (DAOException e) {
      LOGGER.error("failed");
    }

    return companyPage;
  }

  @Override
  public Company get(Long id) {
    LOGGER.info("get id" + this.getClass());

    Company result = null;
    try {
      result = this.cdao.find(id);
    } catch (DAOException e) {
      LOGGER.error("failed get id");
    }

    return result;
  }

  @Override
  public int count() {
    LOGGER.info("count " + this.getClass());

    int result = 0;
    try {
      result = this.cdao.count();
    } catch (DAOException e) {
      LOGGER.error("count failed");
    }

    return result;
  }

  @Override
  @Transactional
  public boolean delete(Long id) {
    LOGGER.info("delete " + this.getClass());

    boolean success = false;
    try {
      success = this.computerDAO.deleteWithCompanyId(id);

      success = this.cdao.delete(id);

    } catch (DAOException e) {
      LOGGER.error("Delete : failed, no rows affected", e);
    }

    return success;
  }

  public void setCdao(ICompanyDAO cdao) {
    this.cdao = cdao;
  }

  public void setComputerDAO(IComputerDAO computerDAO) {
    this.computerDAO = computerDAO;
  }

}
