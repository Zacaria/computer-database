package com.excilys.formation.computerdatabase.webapp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.computerdatabase.config.SupportedLocale;
import com.excilys.formation.computerdatabase.controllers.requestDTO.SwitchLocaleDTO;

@RestController
@RequestMapping("/lang")
public class SwitchLocale {

  @RequestMapping(method = RequestMethod.POST, produces = "application/json")
  protected SwitchLocaleDTO doPost(@RequestParam(value = "lang", defaultValue = "en") String lang) {
    if (!SupportedLocale.contains(lang)) {
      lang = "en";
    }
    return new SwitchLocaleDTO(lang, "200");
  }
}