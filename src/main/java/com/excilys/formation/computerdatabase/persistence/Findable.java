package com.excilys.formation.computerdatabase.persistence;

import java.util.List;

/**
 * @author excilys
 *
 * @param <T>
 */
public interface Findable<T> {
	
	int count();
	List<T> findAll();
	/**
	 * Returns a Page
	 * @param from is position of the first result
	 * @param count is the maximum number of results
	 * @return
	 */
	List<T> findWithRange(int from, int max);
	T findById(Long id);
}
