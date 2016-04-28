package com.excilys.formation.computerdatabase.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.formation.computerdatabase.controllers.requestDTO.AddComputerDTO;
import com.excilys.formation.computerdatabase.controllers.requestMapping.AddComputerRequestMapper;
import com.excilys.formation.computerdatabase.controllers.requestValidator.AddComputerRequestValidator;
import com.excilys.formation.computerdatabase.controllers.util.Pager;
import com.excilys.formation.computerdatabase.dataBinders.dto.CompanyDTO;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.service.IService;

/**
 * Servlet implementation class AddComputer
 */
@Controller
@RequestMapping("/computer")
public class AddComputer implements InitializingBean {
  private static final Logger LOGGER = LoggerFactory.getLogger(AddComputer.class);

  private static final String ATTR_RESULT = "data";
  private static final String ATTR_COMPANIES = "companies";
  private static final String ATTR_SUCCESS = "success";
  private static final String MODEL_ATTRIBUTE = "addComputerForm";

  @Autowired
  private IService<Computer> cs;

  @Autowired
  private IService<Company> es;

  @Autowired
  private AddComputerRequestValidator validator;

  private Pager<Company> pager;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public AddComputer() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @RequestMapping(value = "/add", method = RequestMethod.GET)
  protected String doGet(final Model model, HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    Map<String, Object> result = new HashMap<>();

    result.put(ATTR_COMPANIES, this.pager.getPage());

    model.addAttribute(ATTR_RESULT, result);
    model.addAttribute(MODEL_ATTRIBUTE, new AddComputerDTO());

    return "addComputer";
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  protected String doPost(@ModelAttribute(MODEL_ATTRIBUTE) @Validated AddComputerDTO dto,
      BindingResult bindingResult, final Model model) {

    validator.validate(dto, bindingResult);

    AddComputerRequestMapper mapper = new AddComputerRequestMapper();
    // check errors.
    if (bindingResult.hasErrors()) {
      LOGGER.error(bindingResult.getAllErrors().toString());
    } else {
      LOGGER.debug(mapper.fromDTO(dto).toString());
      cs.create(mapper.fromDTO(dto));
      model.addAttribute(MODEL_ATTRIBUTE, new AddComputerDTO());
      model.addAttribute(ATTR_SUCCESS, true);
    }

    Map<String, Object> result = new HashMap<>();

    result.put(ATTR_COMPANIES, this.pager.getPage());

    model.addAttribute(ATTR_RESULT, result);

    return "addComputer";
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    this.pager = new Pager<Company>(this.es.count(), (options) -> this.es.get(options),
        company -> new CompanyDTO(company));
  }
}
