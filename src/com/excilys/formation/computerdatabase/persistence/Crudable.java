package com.excilys.formation.computerdatabase.persistence;

public interface Crudable<T> extends Findable<T> {
	/**
	 * 
	 * @param toUpdate the new object to persist
	 * @return the number of affected rows
	 */
	int update(T toUpdate);
	
	/**
	 * 
	 * @param toCreate the new object to persist
	 * @return the generated if
	 */
	long create(T toCreate);
	
	/**
	 * 
	 * @param id the id of the row to delete
	 * @return the number of affected rows
	 */
	int delete(long id);
}
