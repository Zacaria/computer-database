package com.excilys.formation.computerdatabase.service;

import com.excilys.formation.computerdatabase.model.User;

public interface IUserService {
  int count();

  User get(Long id);

  User get(String name);

  Long create(User c);

  boolean delete(Long id);
}
