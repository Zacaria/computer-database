package com.excilys.formation.computerdatabase.persistence;

import java.util.List;

/**
 * @author excilys
 *
 * @param <T>
 */
public interface Findable<T> {
	List<T> findAll();
	T findById(Long id);
	T findLast();
}
