package com.excilys.formation.computerdatabase.persistence;

import java.util.List;
import java.util.function.Supplier;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.QCompany;
import com.excilys.formation.computerdatabase.model.SelectOptions;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

@Repository("CompanyDAO")
@Transactional
public class CompanyDAO implements ICompanyDAO {

  private final static Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

  private final static QCompany qCompany = QCompany.company;

  @Autowired
  private SessionFactory sessionFactory;

  // Generates a queryFactory with a fresh session at each call.
  private Supplier<HibernateQueryFactory> queryFactory =
      () -> new HibernateQueryFactory(sessionFactory.getCurrentSession());

  public CompanyDAO() {
  }

  @Override
  public int count() {
    LOGGER.info("counting in " + this.getClass()
      .getSimpleName());

    Integer count = (int) queryFactory.get()
      .selectFrom(QCompany.company)
      .fetchCount();

    return count != null ? count : 0;
  }

  @Override
  public List<Company> find() {
    LOGGER.info("finding in " + this.getClass()
      .getSimpleName());

    List<Company> companies = queryFactory.get()
      .selectFrom(qCompany)
      .fetch();

    return companies;
  }

  @Override
  public List<Company> find(SelectOptions options) {
    LOGGER.info("finding in " + this.getClass()
      .getSimpleName());
    LOGGER.debug("With params " + options);

    List<Company> companies = queryFactory.get()
      .selectFrom(qCompany)
      .offset(options.getOffset())
      .limit(options.getRange())
      .fetch();

    return companies;
  }

  @Override
  public Company find(Long id) {
    LOGGER.info("finding in " + this.getClass()
      .getSimpleName());
    LOGGER.debug("With params " + id);

    Company company = queryFactory.get()
      .selectFrom(qCompany)
      .where(qCompany.id.eq(id))
      .fetchOne();

    return company;
  }

  @Override
  public boolean delete(Long id) {
    LOGGER.info("deleting in " + this.getClass()
      .getSimpleName());
    LOGGER.debug("With params " + id);

    int affectedRows = (int) queryFactory.get()
      .delete(qCompany)
      .where(qCompany.id.eq(id))
      .execute();

    return affectedRows != 0 ? true : false;
  }
}
