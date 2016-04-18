package com.excilys.formation.computerdatabase.dataBinders.mapper;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

public interface IMapper<T> extends RowMapper<T>{
  /**
   * Map a resultSet object into a target Object
   * @param rs input
   * @return the target implemented object
   */
  T map(ResultSet rs, int rowNum);

  /**
   * Map a resultSet object into a list of Objects 
   * @param rs intput
   * @return the target implemented object list
   */
//  List<T> mapList(ResultSet rs);
}
