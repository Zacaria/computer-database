package com.excilys.formation.computerdatabase.persistence;

import java.util.ArrayList;

/**
 * @author excilys
 *
 * @param <T>
 */
public interface Findable<T> {
	ArrayList<T> findAll();
	T findById(int id);
	T findLast();
}
