package com.excilys.formation.computerdatabase.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.excilys.formation.computerdatabase.service.ComputerService;
import com.excilys.formation.computerdatabase.servlets.util.ParamValidator;

/**
 * Servlet implementation class deleteComputer
 */
@WebServlet("/deleteComputer")
public class DeleteComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final ComputerService cs;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComputer() {
        super();
        this.cs = ComputerService.getInstance();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		ParamValidator validator = new ParamValidator();
		
		List<Long> ids = validator.getLongs(request, "selection");
		
		ids
			.stream()
			.forEach(this.cs::delete);
		
		HttpSession session = request.getSession();
		session.setAttribute("success", validator.getErrors().isEmpty() ? true : false);
		session.setAttribute("errors", validator.getErrors());
		response.sendRedirect("dashboard");
	}
}
