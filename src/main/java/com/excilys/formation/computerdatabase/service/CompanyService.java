package com.excilys.formation.computerdatabase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdatabase.dataBinders.dto.CompanyDTO;
import com.excilys.formation.computerdatabase.dataBinders.dto.PageDTO;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Page;
import com.excilys.formation.computerdatabase.persistence.CompanyDAO;
import com.excilys.formation.computerdatabase.persistence.Crudable;

public class CompanyService {

	private static final Logger LOGGER = LoggerFactory.getLogger("com.excilys.formation.computerdatabase");

	private final Crudable<Company> cdao;

	private CompanyService() {
		this.cdao = CompanyDAO.INSTANCE;
	}

	private static class CompanyServiceHolder {
		private final static CompanyService instance = new CompanyService();
	}

	public static CompanyService getInstance() {
		return CompanyServiceHolder.instance;
	}

	public PageDTO<Company> get(int from, int max) {
		LOGGER.info("access");

		Page<Company> companyPage = new Page<>(from, this.cdao.find(from, max), this.cdao.count());
		
		PageDTO<Company> companies = new PageDTO<>(companyPage, company -> new CompanyDTO(company));

		return companies;
	}

	public Company get(Long id) {
		LOGGER.info("access");

		return this.cdao.find(id);
	}
	
	public int count(){
		return this.cdao.count();
	}
}
