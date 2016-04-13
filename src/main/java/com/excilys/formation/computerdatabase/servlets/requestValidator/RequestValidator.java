package com.excilys.formation.computerdatabase.servlets.requestValidator;

import java.util.List;

import com.excilys.formation.computerdatabase.servlets.requestDTO.RequestDTO;

public interface RequestValidator {
  List<String> validate(RequestDTO requestDTO, List<String> errors);
}
