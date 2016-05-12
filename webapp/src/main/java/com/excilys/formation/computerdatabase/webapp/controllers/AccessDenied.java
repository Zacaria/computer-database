package com.excilys.formation.computerdatabase.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/403")
public class AccessDenied {

  @RequestMapping(method = RequestMethod.GET)
  protected String doGet() {

    return "403";
  }
}
