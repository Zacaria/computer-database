package com.excilys.formation.computerdatabase.dataBinders.mapper;

import java.sql.ResultSet;
import java.util.List;

public interface Mapable<T> {
  /**
   * Map a resultSet object into a target Object
   * @param rs input
   * @return the target implemented object
   */
  T map(ResultSet rs);

  /**
   * Map a resultSet object into a list of Objects 
   * @param rs intput
   * @return the target implemented object list
   */
  List<T> mapList(ResultSet rs);
}
