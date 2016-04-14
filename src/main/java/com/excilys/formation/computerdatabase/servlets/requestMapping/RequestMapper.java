package com.excilys.formation.computerdatabase.servlets.requestMapping;

import javax.servlet.http.HttpServletRequest;

import com.excilys.formation.computerdatabase.servlets.requestDTO.RequestDTO;

public interface RequestMapper<T> {
  default RequestDTO getToDTO(HttpServletRequest request) {
    throw new UnsupportedOperationException();
  }

  default RequestDTO postToDTO(HttpServletRequest request) {
    throw new UnsupportedOperationException();
  }

  default T fromDTO(RequestDTO requestDTO) {
    throw new UnsupportedOperationException();
  }
}
