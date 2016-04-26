package com.excilys.formation.computerdatabase.service;

import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.model.Page;
import com.excilys.formation.computerdatabase.model.SelectOptions;

/**
 * Presents the contract of Services.
 * @author Zacaria
 *
 */
public interface IService<T> {
  int count();

  default int count(SelectOptions options) {
    throw new UnsupportedOperationException();
  }

  Page<T> get(SelectOptions options);

  T get(Long id);

  default Long create(T t) {
    throw new UnsupportedOperationException();
  }

  default boolean delete(Long id) {
    throw new UnsupportedOperationException();
  }

  default Computer update(T t) {
    throw new UnsupportedOperationException();
  }
}
