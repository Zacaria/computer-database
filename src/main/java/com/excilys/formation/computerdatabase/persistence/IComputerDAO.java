package com.excilys.formation.computerdatabase.persistence;

import com.excilys.formation.computerdatabase.exceptions.DAOException;
import com.excilys.formation.computerdatabase.model.Computer;

public interface IComputerDAO extends IDAO<Computer> {
  boolean deleteWithCompanyId(Long id) throws DAOException;
}
