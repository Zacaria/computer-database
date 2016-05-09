package com.excilys.formation.computerdatabase.persistence;

import java.util.List;
import java.util.function.Supplier;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.model.QCompany;
import com.excilys.formation.computerdatabase.model.QComputer;
import com.excilys.formation.computerdatabase.model.SelectOptions;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

/**
 * 
 * @author Zacaria
 *
 */
@Repository("ComputerDAO")
@Transactional
public class ComputerDAO implements IComputerDAO {

  private final static Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

  private final static QComputer qComputer = QComputer.computer;

  @Autowired
  private SessionFactory sessionFactory;

  // Generates a queryFactory with a fresh session at each call.
  private Supplier<HibernateQueryFactory> queryFactory =
      () -> new HibernateQueryFactory(sessionFactory.getCurrentSession());

  public ComputerDAO() {
  }

  @Override
  public int count() {
    LOGGER.info("counting in " + this.getClass()
      .getSimpleName());

    Integer count = (int) queryFactory.get()
      .selectFrom(qComputer)
      .fetchCount();

    return count != null ? count : 0;
  }

  @Override
  public int count(SelectOptions options) {
    LOGGER.info("counting in " + this.getClass()
      .getSimpleName());
    LOGGER.debug(options.toString());

    Integer count = (int) queryFactory.get()
      .selectFrom(qComputer)
      .leftJoin(qComputer.company, QCompany.company)
      .where(qComputer.name.like(options.getSearch()).or(QCompany.company.name.like(options.getSearch())))
      .fetchCount();

    return count != null ? count : 0;
  }

  @Override
  public List<Computer> find() {

    List<Computer> computers = queryFactory.get()
      .selectFrom(qComputer)
      .fetch();

    return computers;
  }

  @Override
  public List<Computer> find(SelectOptions options) {
    LOGGER.debug(options.toString());

    PathBuilder<QComputer> orderBy = new PathBuilder<>(QComputer.class, "computer");

    OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(
        options.isAsc() ? Order.ASC : Order.DESC, orderBy.getString(options.getOrderBy()));

    List<Computer> computers = queryFactory.get()
      .selectFrom(qComputer)
      .leftJoin(qComputer.company, QCompany.company)
      .where(qComputer.name.like(options.getSearch()).or(QCompany.company.name.like(options.getSearch())))
      .limit(options.getRange())
      .offset(options.getOffset())
      .orderBy(orderSpecifier)
      .fetch();

    return computers;
  }

  @Override
  public Computer find(Long id) {

    Computer computer = queryFactory.get()
      .selectFrom(qComputer)
      .where(qComputer.id.eq(id))
      .fetchOne();

    return computer;
  }

  @Override
  public Long create(Computer computer) {
    LOGGER.debug("Creating this computer : " + computer);

    return (Long) sessionFactory.getCurrentSession()
      .save(computer);
  }

  @Override
  public boolean delete(Long id) {

    int affectedRows = (int) queryFactory.get()
      .delete(qComputer)
      .where(qComputer.id.eq(id))
      .execute();

    return affectedRows != 0 ? true : false;
  }

  @Override
  public boolean deleteWithCompanyId(Long id) {

    int affectedRows = (int) queryFactory.get()
      .delete(qComputer)
      .where(qComputer.company.id.eq(id))
      .execute();

    return affectedRows != 0 ? true : false;
  }

  @Override
  public Computer update(Computer computer) {

    int affectedRows = (int) queryFactory.get()
      .update(qComputer)
      .where(qComputer.id.eq(computer.getId()))
      .set(qComputer.name, computer.getName())
      .set(qComputer.introduced, computer.getIntroduced())
      .set(qComputer.discontinued, computer.getDiscontinued())
      .set(qComputer.company, computer.getCompany())
      .execute();

    if (affectedRows == 0) {
      LOGGER.error("ERROR Update : failed, no rows affected");
      return null;
    }

    return computer;
  }
}