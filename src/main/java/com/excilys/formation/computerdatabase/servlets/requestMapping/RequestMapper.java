package com.excilys.formation.computerdatabase.servlets.requestMapping;

import javax.servlet.http.HttpServletRequest;

import com.excilys.formation.computerdatabase.servlets.requestDTO.RequestDTO;

public interface RequestMapper<T> {
  RequestDTO toDTO(HttpServletRequest request);
  
  T fromDTO(RequestDTO dto);
}
