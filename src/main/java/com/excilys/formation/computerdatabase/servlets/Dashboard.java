package com.excilys.formation.computerdatabase.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdatabase.dataBinders.dto.ComputerDTO;
import com.excilys.formation.computerdatabase.dataBinders.dto.PageDTO;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.service.ComputerService;
import com.excilys.formation.computerdatabase.servlets.util.ParamValidator;
import com.excilys.formation.computerdatabase.ui.Pager;

/**
 * Servlet implementation class Dashboard.
 */
@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
	private static final Logger LOGGER = LoggerFactory.getLogger("com.excilys.formation.computerdatabase");
	private static final long serialVersionUID = 1L;

	private static final String PAGE_PARAM = "p";
	private static final String RANGE_PARAM = "r";
	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_RANGE = 10;

	private final ComputerService cs;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Dashboard() {
		super();
		this.cs = ComputerService.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		LOGGER.info("access to : " + request.getRequestURL() + " " + request.getQueryString());

		ParamValidator validator = new ParamValidator();

		int page = validator.getInt(request, PAGE_PARAM, DEFAULT_PAGE);
		int range = validator.getInt(request, RANGE_PARAM, DEFAULT_RANGE);
		int totalPage;

		/**
		 * Init the pager in the client's session if it doesn't exist.
		 */
		Pager<Computer> pager = null;

		HttpSession session = request.getSession();

		if (session.getAttribute("pager") == null || !(session.getAttribute("pager") instanceof Pager)) {
			pager = new Pager<>(this.cs.count(), (offset, max) -> this.cs.get(offset, max),
					computer -> new ComputerDTO(computer));
			session.setAttribute("pager", pager);
		} else {

			// Here's the unchecked, safe enough.
			pager = (Pager<Computer>) session.getAttribute("pager");
		}
		pager.setRange(range);

		/**
		 * Determine the page we need.
		 * I need to know how much computers there is before asking any page.
		 */
		int count = this.cs.count();

		totalPage = (count + range - 1) / range;

		page = page > totalPage ? totalPage : page;

		/**
		 * Get that page and give it to the view.
		 */
		PageDTO<Computer> computers = pager.getPage(page);

		request.setAttribute("count", count);
		request.setAttribute("computers", computers);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("range", range);
		request.setAttribute("current", page);

		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard.jsp");
		view.forward(request, response);
	}
}
