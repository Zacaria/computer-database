package com.excilys.formation.computerdatabase.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.excilys.formation.computerdatabase.model.User;
import com.excilys.formation.computerdatabase.persistence.IUserDAO;
import com.excilys.formation.computerdatabase.util.StringChecker;

@Service("UserService")
public class UserService implements IUserService, UserDetailsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  @Resource(name = "UserDAO")
  private IUserDAO udao;

  public UserService() {
  }

  @Override
  public int count() {
    LOGGER.info("count " + this.getClass()
      .getSimpleName());

    return this.udao.count();
  }

  @Override
  public User get(Long id) {
    LOGGER.info("get by id " + this.getClass()
      .getSimpleName());

    return this.udao.find(id);
  }

  @Override
  public User get(String name) {
    LOGGER.info("get by name " + this.getClass()
      .getSimpleName());

    return this.udao.find(name);
  }

  @Override
  public Long create(User user) {
    LOGGER.info("create " + this.getClass()
      .getSimpleName());
    LOGGER.debug(user.toString());

    if (StringChecker.isNullOrEmpty(user.getUsername())
        || StringChecker.isNullOrEmpty(user.getPassword())) {
      LOGGER.error("ERROR Insert : a user field was empty !");
      return null;
    }

    return this.udao.create(user);
  }

  @Override
  public boolean delete(Long id) {
    LOGGER.info("delete " + this.getClass()
      .getSimpleName());
    if (id == null) {
      return false;
    }

    LOGGER.debug("Delete user with id " + id);

    return this.udao.delete(id);
  }

  @Override
  public User loadUserByUsername(String username) {
    return get(username);
    // return null;
  }
}
