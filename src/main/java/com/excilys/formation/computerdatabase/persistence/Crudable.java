package com.excilys.formation.computerdatabase.persistence;

import java.util.List;

import com.excilys.formation.computerdatabase.model.SelectOptions;

public interface Crudable<T> {
  int count();

  default int count(SelectOptions options) {
    throw new UnsupportedOperationException();
  }

  /**
   * Find all tuples for the given model.
   * @return
   */
  List<T> find();

  /**
   * Returns a Page.
   * 
   * @param from is position of the first result.
   * @param count is the maximum number of results.
   * @return 
   */
  List<T> find(SelectOptions options);

  /**
   * 
   * @param id.
   * @return The object or null if no tuple with this id was found.
   */
  T find(Long id);

  /**
   * 
   * @param toUpdate the new object to persist.
   * @return the number of affected rows.
   */
  default T update(T toUpdate) {
    throw new UnsupportedOperationException();
  }

  /**
   * 
   * @param toCreate the new object to persist.
   * @return the generated if.
   */
  default Long create(T toCreate) {
    throw new UnsupportedOperationException();
  }

  /**
   * 
   * @param id the id of the row to delete.
   * @return the number of affected rows.
   */
  default boolean delete(Long id) {
    throw new UnsupportedOperationException();
  }
}
