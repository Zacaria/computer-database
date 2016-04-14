package com.excilys.formation.computerdatabase.dataBinders.dto;

/**
 * Functional interface.
 * Take a model, returns its DTO version.
 * @author Zacaria
 *
 * @param <T> The model to transform.
 */
public interface DTOable<T> {
  DTO getDTO(T plain);
}
