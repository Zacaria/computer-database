package com.excilys.formation.computerdatabase.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Servlet implementation class NotFound404
 */
@RequestMapping("/404")
public class NotFound404 {

  /**
   * @see HttpServlet#HttpServlet()
   */
  public NotFound404() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  @RequestMapping(method=RequestMethod.GET)
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/404.jsp");

    view.forward(request, response);
  }
}
