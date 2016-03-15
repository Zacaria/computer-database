package com.excilys.formation.computerdatabase.servlets;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.computerdatabase.mapper.Page;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.service.ComputerService;
import com.excilys.formation.computerdatabase.ui.Pager;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

	private static final Pattern ID_PATTERN = Pattern.compile("^\\d+$");
	private static final long serialVersionUID = 1L;

	private final ComputerService cs;
	private Pager<Computer> pager;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Dashboard() {
		super();
		this.cs = ComputerService.getInstance();
		
		this.pager = new Pager<>(this.cs.count(), (offset, max) -> this.cs.get(offset, max));
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int page = 1;

		if (request.getParameter("p") != null && ID_PATTERN.matcher(request.getParameter("p")).matches()) {
			page = Integer.parseInt(request.getParameter("p"));
		}

		int count = this.cs.count();

		Page<Computer> computers = this.pager.getPage(page);

		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard.jsp");

		request.setAttribute("count", count);
		request.setAttribute("computers", computers);

		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
