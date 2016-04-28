package com.excilys.formation.computerdatabase.util;

import com.excilys.formation.computerdatabase.util.IDTO;

/**
 * Functional interface.
 * Take a model, returns its DTO version.
 * @author Zacaria
 *
 * @param <T> The model to transform.
 */
public interface DTOable<T> {
  IDTO getDTO(T plain);
}
