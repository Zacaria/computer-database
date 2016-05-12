package com.excilys.formation.computerdatabase.controllers.requestValidator;

import java.util.List;

import com.excilys.formation.computerdatabase.controllers.requestDTO.IRequestDTO;

public interface IRequestValidator {
  default List<String> validateGet(IRequestDTO requestDTO, List<String> errors) {
    throw new UnsupportedOperationException();
  }

  default List<String> validatePost(IRequestDTO requestDTO, List<String> errors) {
    throw new UnsupportedOperationException();
  }
}
