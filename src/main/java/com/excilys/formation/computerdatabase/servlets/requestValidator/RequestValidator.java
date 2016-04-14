package com.excilys.formation.computerdatabase.servlets.requestValidator;

import java.util.List;

import com.excilys.formation.computerdatabase.servlets.requestDTO.RequestDTO;

public interface RequestValidator {
  default List<String> validateGet(RequestDTO requestDTO, List<String> errors) {
    throw new UnsupportedOperationException();
  }

  default List<String> validatePost(RequestDTO requestDTO, List<String> errors) {
    throw new UnsupportedOperationException();
  }
}
