package com.excilys.formation.computerdatabase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Page;
import com.excilys.formation.computerdatabase.persistence.CompanyDAO;
import com.excilys.formation.computerdatabase.persistence.Crudable;

public class CompanyService implements Servable<Company>{

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

	@Override
	public Page<Company> get(int offset, int limit) {
		LOGGER.info("access");

		Page<Company> companyPage = new Page<>(offset, this.cdao.find(offset, limit), this.cdao.count());
		
		return companyPage;
	}

	@Override
	public Company get(Long id) {
		LOGGER.info("access");

		return this.cdao.find(id);
	}
	
	@Override
	public int count(){
		return this.cdao.count();
	}
}
