package com.excilys.formation.computerdatabase.ui;

import com.excilys.formation.computerdatabase.mapper.Page;

public interface Pageable<T> {
	Page<T> get(int from, int max);
}
