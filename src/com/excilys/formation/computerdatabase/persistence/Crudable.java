package com.excilys.formation.computerdatabase.persistence;

public interface Crudable<T> extends Findable<T> {
	Integer update(T toUpdate);
	Integer create(T toCreate);
	boolean delete(Integer id);
}
