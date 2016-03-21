package com.excilys.formation.computerdatabase.ui;

import com.excilys.formation.computerdatabase.model.Page;

/**
 * Functionnal Interface made to get Pages
 * @author Zacaria
 *
 * @param <T> The type of the elements in the Page
 */
public interface Pageable<T> {
	Page<T> get(int from, int max);
}
