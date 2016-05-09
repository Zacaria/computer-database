package com.excilys.formation.computerdatabase.service;

import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.model.Page;
import com.excilys.formation.computerdatabase.model.SelectOptions;

public interface IComputerService {
  int count();

  int count(SelectOptions options);

  Page<Computer> get(SelectOptions options);

  Computer get(Long id);

  Long create(Computer c);

  boolean delete(Long id);

  Computer update(Computer c);
}
