package com.excilys.formation.computerdatabase.persistence;

import java.util.ArrayList;

public interface Findable<T> {
	ArrayList<T> findAll();
	T findById(int id);
}
