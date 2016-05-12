package com.excilys.formation.computerdatabase.api.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.computerdatabase.api.endpoints.dto.ResponseDTO;
import com.excilys.formation.computerdatabase.api.endpoints.dto.ResponseStatus;
import com.excilys.formation.computerdatabase.controllers.requestDTO.AddComputerDTO;
import com.excilys.formation.computerdatabase.controllers.requestMapping.AddComputerRequestMapper;
import com.excilys.formation.computerdatabase.controllers.requestValidator.AddComputerRequestValidator;
import com.excilys.formation.computerdatabase.service.IComputerService;

@RestController("restAddComputer")
@RequestMapping("/computer/add")
public class AddComputer {
  private static final Logger LOGGER = LoggerFactory.getLogger(AddComputer.class);
  
  @Autowired
  private AddComputerRequestValidator validator;
  
  @Autowired
  private IComputerService cs;
  
  /**
   * All parameters are mandatory
   * {
   *    "name": "",
   *    "introduced": "",
   *    "discontinued": "",
   *    "companyId": ""
   * }
   * @param dto
   * @param bindingResult
   * @return
   */
  @RequestMapping(method = RequestMethod.POST, produces = "application/json")
  protected ResponseDTO post(@RequestBody @Validated AddComputerDTO dto,
      BindingResult bindingResult) {
    if(!bindingResult.hasErrors()){
      validator.validate(dto, bindingResult);
    }
    
    AddComputerRequestMapper mapper = new AddComputerRequestMapper();
    // check errors.
    if (bindingResult.hasErrors()) {
      LOGGER.error(bindingResult.getAllErrors()
        .toString());
    } else {
      LOGGER.debug(mapper.fromDTO(dto)
        .toString());
      cs.create(mapper.fromDTO(dto));
    }
    
    return new ResponseDTO(ResponseStatus.SUCCESS.getValue(), dto);
  }
}
