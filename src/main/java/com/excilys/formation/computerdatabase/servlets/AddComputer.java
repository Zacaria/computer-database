package com.excilys.formation.computerdatabase.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdatabase.dataBinders.dto.CompanyDTO;
import com.excilys.formation.computerdatabase.dataBinders.dto.PageDTO;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.service.CompanyService;
import com.excilys.formation.computerdatabase.service.ComputerService;
import com.excilys.formation.computerdatabase.servlets.util.ParamValidator;
import com.excilys.formation.computerdatabase.ui.Pager;
import com.excilys.formation.computerdatabase.util.DateConverter;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {
	private static final Logger LOGGER = LoggerFactory.getLogger("com.excilys.formation.computerdatabase");
	private static final long serialVersionUID = 1L;
	private static final Pattern INT_PATTERN = Pattern.compile("^\\d+$");
	
	private static final String COMPANY_ID_PARAM = "companyId";
	private static final String COMPUTER_NAME_PARAM = "name";
	private static final String COMPUTER_INTRODUCED_PARAM = "introduced";
	private static final String COMPUTER_DISCONTINUED_PARAM = "discontinued";

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

		this.pager = new Pager<Company>(this.es.count(),
				(offset, max) -> this.es.get(offset, max),
				company -> new CompanyDTO(company));

		// FIXME : This is ugly and hard code
		this.pager.setRange(100);

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LOGGER.info(request.getMethod() + " access to : " + request.getRequestURL() + " " + request.getQueryString());

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

		LOGGER.info(request.getMethod() + " access to : " + request.getRequestURL() + " " + request.getQueryString());
		
		ParamValidator validator = new ParamValidator();
		

		Long companyId = validator.getLong(request, COMPANY_ID_PARAM);
		String name = validator.getString(request, COMPUTER_NAME_PARAM);
		LocalDate introduced = validator.getDate(request, COMPUTER_INTRODUCED_PARAM);
		LocalDate discontinued = validator.getDate(request, COMPUTER_DISCONTINUED_PARAM);

		if(validator.getErrors().isEmpty()){
			// Safe strings with prepared queries
			Computer computer = Computer.builder(name).introduced(introduced)
					.discontinued(discontinued).company(Company.builder(companyId).build()).build();

			cs.create(computer);
		}

		request.setAttribute("success", validator.getErrors().isEmpty() ? true : false);
		request.setAttribute("errors", validator.getErrors());

		doGet(request, response);
	}

}
