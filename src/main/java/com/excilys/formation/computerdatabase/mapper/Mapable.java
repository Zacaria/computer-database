package com.excilys.formation.computerdatabase.mapper;

import java.sql.ResultSet;

public interface Mapable<T> {
	T map(ResultSet rs);
}
