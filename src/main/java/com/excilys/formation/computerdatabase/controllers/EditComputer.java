package com.excilys.formation.computerdatabase.controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.formation.computerdatabase.controllers.requestDTO.EditComputerDTO;
import com.excilys.formation.computerdatabase.controllers.requestMapping.EditComputerRequestMapper;
import com.excilys.formation.computerdatabase.controllers.requestValidator.EditComputerRequestValidator;
import com.excilys.formation.computerdatabase.dataBinders.dto.CompanyDTO;
import com.excilys.formation.computerdatabase.dataBinders.dto.ComputerDTO;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.service.IService;
import com.excilys.formation.computerdatabase.ui.Pager;
import com.excilys.formation.computerdatabase.util.StringChecker;

/**
 * Servlet implementation class EditComputer
 */
@Controller
@RequestMapping("/computer")
public class EditComputer implements InitializingBean {
  private static final Logger LOGGER = LoggerFactory.getLogger(EditComputer.class);

  private static final String ATTR_RESULT = "data";
  private static final String ATTR_MESSAGES = "messages";
  private static final String ATTR_SUCCESS = "success";
  private static final String ATTR_ERROR = "errors";
  private static final String ATTR_DTO = "dto";
  private static final String MODEL_ATTRIBUTE = "editComputerForm";

  @Autowired
  private IService<Computer> cs;

  @Autowired
  private IService<Company> es;

  @Autowired
  private EditComputerRequestValidator validator;

  private Pager<Company> pager;

  public EditComputer() {
  }

  @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
  protected String doGet(RedirectAttributes attr, final Model model,
      @PathVariable("id") final String id) {

    EditComputerDTO dto = new EditComputerDTO(id);

    if (id == null || !StringChecker.isNumber(id) || this.cs.get(Long.parseLong(id)) == null) {
      attr.addFlashAttribute("pout", "lawl");
      return "redirect:/dashboard";
    }

    dto.setCompanies(this.pager.getPage());
    dto.setComputer(ComputerDTO.builder(this.cs.get(Long.parseLong(id))).build());

    Map<String, Object> result = new HashMap<>();
    result.put(ATTR_DTO, dto);

    model.addAttribute(ATTR_RESULT, result);
    model.addAttribute(MODEL_ATTRIBUTE, new EditComputerDTO());

    return "editComputer";
  }

  @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
  protected String doPost(@ModelAttribute("editComputerForm") @Validated EditComputerDTO dto,
      BindingResult bindingResult, final Model model) {

    validator.validate(dto, bindingResult);

    Map<String, Object> result = new HashMap<>();
    EditComputerRequestMapper mapper = new EditComputerRequestMapper();

    if (bindingResult.hasErrors()) {
      LOGGER.error(bindingResult.getAllErrors().toString());
      dto.setComputer(ComputerDTO.builder(this.cs.get(Long.parseLong(dto.getId()))).build());
    } else {
      dto.setComputer(ComputerDTO.builder(cs.update(mapper.fromDTO(dto))).build());
      model.addAttribute(ATTR_SUCCESS, true);
    }

    dto.setCompanies(this.pager.getPage());

    result.put(ATTR_DTO, dto);
    model.addAttribute(ATTR_RESULT, result);

    return "editComputer";
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    this.pager = new Pager<Company>(this.es.count(), (options) -> this.es.get(options),
        company -> new CompanyDTO(company));

    // FIXME : This is ugly and hard code.
    this.pager.setRange(100);

  }
}
