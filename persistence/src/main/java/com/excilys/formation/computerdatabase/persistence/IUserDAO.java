package com.excilys.formation.computerdatabase.persistence;

import com.excilys.formation.computerdatabase.model.User;

public interface IUserDAO extends IDAO<User> {
  User find(String username);
}
