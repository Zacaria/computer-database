package com.excilys.formation.computerdatabase.api.endpoints;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.computerdatabase.api.endpoints.dto.ResponseDTO;
import com.excilys.formation.computerdatabase.api.endpoints.dto.ResponseStatus;
import com.excilys.formation.computerdatabase.controllers.util.Pager;
import com.excilys.formation.computerdatabase.dataBinders.dto.CompanyDTO;
import com.excilys.formation.computerdatabase.service.ICompanyService;

@RestController("restCompany")
@RequestMapping("/company")
public class Company implements InitializingBean {

  @Autowired
  private ICompanyService es;

  private Pager<com.excilys.formation.computerdatabase.model.Company> pager;

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  protected ResponseDTO get() {
    return new ResponseDTO(ResponseStatus.SUCCESS.getValue(), this.pager.getPage());
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
  protected ResponseDTO getOne(@PathVariable Long id) {
    return new ResponseDTO(ResponseStatus.SUCCESS.getValue(), new CompanyDTO(this.es.get(id)));
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    this.pager = new Pager<com.excilys.formation.computerdatabase.model.Company>(this.es.count(),
        (options) -> this.es.get(options), company -> new CompanyDTO(company));
  }
}
