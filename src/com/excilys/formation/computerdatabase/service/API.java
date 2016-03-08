package com.excilys.formation.computerdatabase.service;

import java.util.ArrayList;

import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.persistence.CompanyDAO;

/**
 * This class is a facade exposing the capabilities of the API
 * @author excilys
 *
 */
public class API {
	
	//The API calls the objects
	public static ArrayList<Company> getCompanies(){
		CompanyDAO cdao = new CompanyDAO();
		return cdao.findAll();
	}
	
}
