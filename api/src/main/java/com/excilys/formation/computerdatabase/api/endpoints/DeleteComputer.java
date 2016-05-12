package com.excilys.formation.computerdatabase.api.endpoints;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.computerdatabase.api.endpoints.dto.ResponseDTO;
import com.excilys.formation.computerdatabase.api.endpoints.dto.ResponseStatus;
import com.excilys.formation.computerdatabase.controllers.requestDTO.DeleteComputerDTO;
import com.excilys.formation.computerdatabase.controllers.requestValidator.DeleteComputerRequestValidator;
import com.excilys.formation.computerdatabase.service.IComputerService;

@RestController("restDeleteComputer")
@RequestMapping("/computer/delete")
public class DeleteComputer {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(DeleteComputer.class);
  
  @Autowired
  private IComputerService cs;

  @Autowired
  private DeleteComputerRequestValidator validator;
  
  /**
   * {
   *    "ids": []
   * }
   * @param dto
   * @param bindingResult
   * @param request
   * @return
   */
  @RequestMapping(method = RequestMethod.POST, produces = "application/json")
  protected ResponseDTO doPost(@RequestBody DeleteComputerDTO dto, BindingResult bindingResult, HttpServletRequest request) {
    
    LOGGER.debug(dto.toString());
    
    //FIXME : validate id format
    
    validator.validate(dto, bindingResult);

    if (!bindingResult.hasErrors()) {
      dto.getIds()
        .stream()
        .forEach(this.cs::delete);
      //set code to 200
    } else {
      //set code to 500
      LOGGER.debug(bindingResult.getAllErrors().toString());
    }
    
    return new ResponseDTO(ResponseStatus.SUCCESS.getValue(), dto);
  }
  
}
