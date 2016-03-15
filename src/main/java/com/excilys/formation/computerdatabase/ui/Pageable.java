package com.excilys.formation.computerdatabase.ui;

import com.excilys.formation.computerdatabase.dataBinders.dto.PageDTO;

/**
 * Functionnal Interface made to get Pages
 * @author Zacaria
 *
 * @param <T> The type of the elements in the Page
 */
public interface Pageable<T> {
	PageDTO<T> get(int from, int max);
}
