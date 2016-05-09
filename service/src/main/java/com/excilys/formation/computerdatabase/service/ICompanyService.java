package com.excilys.formation.computerdatabase.service;

import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Page;
import com.excilys.formation.computerdatabase.model.SelectOptions;

public interface ICompanyService {
  int count();

  Page<Company> get(SelectOptions options);

  Company get(Long id);

  boolean delete(Long id);
}
