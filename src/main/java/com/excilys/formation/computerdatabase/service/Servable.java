package com.excilys.formation.computerdatabase.service;

import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.model.Page;

/**
 * Presents the contract of Services.
 * @author Zacaria
 *
 */
public interface Servable<T> {
	int count();
	
	Page<T> get(int offset, int limit);
	
	T get(Long id);
	
	default Long create(T t){
		throw new UnsupportedOperationException();
	}
	
	default boolean delete(Long id){
		throw new UnsupportedOperationException();
	}
	
	default Computer update(T t){
		throw new UnsupportedOperationException();
	}
}
