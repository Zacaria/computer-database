package com.excilys.formation.computerdatabase.servlets;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.computerdatabase.dataBinders.dto.PageDTO;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.service.CompanyService;
import com.excilys.formation.computerdatabase.service.ComputerService;
import com.excilys.formation.computerdatabase.ui.Pager;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {
	private static final Logger LOGGER = LogManager.getLogger("com.excilys.formation.computerdatabase");
	private static final long serialVersionUID = 1L;
	private static final Pattern INT_PATTERN = Pattern.compile("^\\d+$");

	private final ComputerService cs;
	private final CompanyService es;

	private Pager<Company> pager;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddComputer() {
		super();
		this.cs = ComputerService.getInstance();
		this.es = CompanyService.getInstance();

		this.pager = new Pager<Company>(this.es.count(), (offset, max) -> this.es.get(offset, max));

		// FIXME : This is ugly and hard code
		this.pager.setRange(100);

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		LOGGER.info("access to : " + request.getRequestURL() + " " + request.getQueryString());

		PageDTO<Company> companies = this.pager.getPage();

		request.setAttribute("companies", companies);

		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/addComputer.jsp");

		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		LOGGER.info("access to : " + request.getRequestURL() + " " + request.getQueryString());
		
		Long companyId = null;
		
		if (request.getParameter("companyId") != null && INT_PATTERN.matcher(request.getParameter("companyId")).matches()) {
			companyId = Long.parseLong(request.getParameter("companyId"));
		}
		
		Computer computer = Computer
				.builder()
				.name(request.getParameter("name"))
				.introduced(request.getParameter("introduced"))
				.discontinued(request.getParameter("discontinued"))
				.company(Company.builder().id(companyId).build())
				.build();
		
		Long newId = cs.create(computer);
		
		System.out.println(request.getParameter("companyId"));
		System.out.println(request.getParameter("name"));
		
		doGet(request, response);
	}

}
