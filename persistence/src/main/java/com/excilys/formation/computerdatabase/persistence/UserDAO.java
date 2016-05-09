package com.excilys.formation.computerdatabase.persistence;

import java.util.List;
import java.util.function.Supplier;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.computerdatabase.model.QUser;
import com.excilys.formation.computerdatabase.model.User;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

@Repository("UserDAO")
@Transactional
public class UserDAO implements IUserDAO {

  private final static Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);

  private final static QUser qUser = QUser.user;

  @Autowired
  private SessionFactory sessionFactory;

  // Generates a queryFactory with a fresh session at each call.
  private Supplier<HibernateQueryFactory> queryFactory =
      () -> new HibernateQueryFactory(sessionFactory.getCurrentSession());

  public UserDAO() {
  }

  @Override
  public int count() {
    LOGGER.info("counting in " + this.getClass()
      .getSimpleName());

    Integer count = (int) queryFactory.get()
      .selectFrom(QUser.user)
      .fetchCount();

    return count != null ? count : 0;
  }

  @Override
  public List<User> find() {
    LOGGER.info("finding in " + this.getClass()
      .getSimpleName());

    List<User> users = queryFactory.get()
      .selectFrom(qUser)
      .fetch();

    return users;
  }

  @Override
  public User find(Long id) {

    LOGGER.info("finding in " + this.getClass()
      .getSimpleName());
    LOGGER.debug("With id " + id);

    User user = queryFactory.get()
      .selectFrom(qUser)
      .where(qUser.id.eq(id))
      .fetchOne();

    return user;
  }

  @Override
  public User find(String username) {
    LOGGER.info("finding in " + this.getClass()
      .getSimpleName());
    LOGGER.debug("With username " + username);

    User user = queryFactory.get()
      .selectFrom(qUser)
      .where(qUser.username.eq(username))
      .fetchOne();

    return user;
  }

  @Override
  public Long create(User user) {
    LOGGER.debug("Creating this user : " + user);

    return (Long) sessionFactory.getCurrentSession()
      .save(user);
  }

  @Override
  public boolean delete(Long id) {

    int affectedRows = (int) queryFactory.get()
      .delete(qUser)
      .where(qUser.id.eq(id))
      .execute();

    return affectedRows != 0 ? true : false;
  }
}
