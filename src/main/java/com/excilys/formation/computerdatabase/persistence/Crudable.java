package com.excilys.formation.computerdatabase.persistence;

import java.util.List;

public interface Crudable<T> {
	int count();

	List<T> find();

	/**
	 * Returns a Page
	 * 
	 * @param from
	 *            is position of the first result
	 * @param count
	 *            is the maximum number of results
	 * @return
	 */
	List<T> find(int from, int max);

	T find(Long id);

	/**
	 * 
	 * @param toUpdate
	 *            the new object to persist
	 * @return the number of affected rows
	 */
	T update(T toUpdate);

	/**
	 * 
	 * @param toCreate
	 *            the new object to persist
	 * @return the generated if
	 */
	Long create(T toCreate);

	/**
	 * 
	 * @param id
	 *            the id of the row to delete
	 * @return the number of affected rows
	 */
	boolean delete(Long id);
}
