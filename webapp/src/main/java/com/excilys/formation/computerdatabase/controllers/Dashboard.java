package com.excilys.formation.computerdatabase.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.formation.computerdatabase.controllers.requestDTO.DashboardDTO;
import com.excilys.formation.computerdatabase.controllers.requestMapping.DashboardRequestMapper;

/**
 * Servlet implementation class Dashboard.
 */
@Controller
@RequestMapping({"/", "/dashboard"})
public class Dashboard {
  
  private static final Logger LOGGER =
      LoggerFactory.getLogger(Dashboard.class);

  private static final String ATTR_RESULT = "data";
  private static final String ATTR_DTO = "dto";
  private static final String ATTR_MESSAGES = "messages";

  @Autowired
  private DashboardRequestMapper mapper;

  public Dashboard() {
  }

  @SuppressWarnings("unchecked")
  @RequestMapping(method = RequestMethod.GET)
  protected String doGet(final Model model, HttpServletRequest request,
      HttpServletResponse response) {

    Map<String, Object> result = new HashMap<>();
    Map<String, Object> messages = new HashMap<>();

    DashboardDTO dto = (DashboardDTO) mapper.getToDTO(request);

    HttpSession session = request.getSession();

    if (session.getAttribute(ATTR_MESSAGES) != null) {
      messages
          .putAll((Map<? extends String, ? extends Object>) session.getAttribute(ATTR_MESSAGES));
      session.setAttribute(ATTR_MESSAGES, null);
    }

    result.put(ATTR_DTO, dto);
    result.put(ATTR_MESSAGES, messages);

    request.setAttribute(ATTR_RESULT, result);

    model.addAttribute(ATTR_RESULT, result);

    return "dashboard";
  }
}
