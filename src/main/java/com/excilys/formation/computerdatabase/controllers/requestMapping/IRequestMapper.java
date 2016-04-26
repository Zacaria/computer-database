package com.excilys.formation.computerdatabase.controllers.requestMapping;

import javax.servlet.http.HttpServletRequest;

import com.excilys.formation.computerdatabase.controllers.requestDTO.IRequestDTO;

public interface IRequestMapper<T> {
  default IRequestDTO getToDTO(HttpServletRequest request) {
    throw new UnsupportedOperationException();
  }

  default IRequestDTO postToDTO(HttpServletRequest request) {
    throw new UnsupportedOperationException();
  }

  default T fromDTO(IRequestDTO requestDTO) {
    throw new UnsupportedOperationException();
  }
}
