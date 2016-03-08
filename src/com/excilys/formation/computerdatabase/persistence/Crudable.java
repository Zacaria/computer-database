package com.excilys.formation.computerdatabase.persistence;

public interface Crudable<T> extends Findable<T> {
	int update(T toUpdate);
	int create(T toCreate);
	boolean delete(Integer id);
}
