package com.excilys.formation.computerdatabase.dataBinders.dto;

public interface DTOable<T> {
	DTO getDTO(T plain);
}
