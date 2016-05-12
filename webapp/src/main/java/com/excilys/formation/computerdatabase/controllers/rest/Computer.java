package com.excilys.formation.computerdatabase.controllers.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.computerdatabase.controllers.rest.dto.ResponseDTO;
import com.excilys.formation.computerdatabase.controllers.rest.dto.ResponseStatus;
import com.excilys.formation.computerdatabase.service.IComputerService;

@RestController("restComputer")
@RequestMapping("/api/computer")
public class Computer {
  
private static final Logger LOGGER = LoggerFactory.getLogger(Computer.class);
  
  @Autowired
  private IComputerService cs;
  
//  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
//  protected ResponseDTO get() {
//    return new ResponseDTO(ResponseStatus.SUCCESS, );
//  }
  
  @RequestMapping(value="/{id}", method = RequestMethod.GET, produces = "application/json")
  protected ResponseDTO get(@PathVariable Long id) {
    return new ResponseDTO(ResponseStatus.SUCCESS.getValue(), this.cs.get(id));
  }

}
