package com.excilys.formation.computerdatabase.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.computerdatabase.dataBinders.dto.CompanyDTO;
import com.excilys.formation.computerdatabase.dataBinders.dto.PageDTO;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Page;
import com.excilys.formation.computerdatabase.persistence.CompanyDAO;
import com.excilys.formation.computerdatabase.persistence.Crudable;

public class CompanyService {

	private static final Logger LOGGER = LogManager.getLogger("com.excilys.formation.computerdatabase");

	private final Crudable<Company> cdao;

	private CompanyService() {
		this.cdao = new CompanyDAO();
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

	// TODO : Remove this !
	/*public Company get(String pId) {
		LOGGER.info("access");
		try {
			Long id = Long.parseLong(pId);

			return this.cdao.find(id);
		} catch (NumberFormatException e) {
			LOGGER.error(e.getClass().getName() + " : " + e.getMessage());
			return null;
		}
	}*/

	public Company get(Long id) {
		LOGGER.info("access");

		return this.cdao.find(id);
	}
	
	public int count(){
		return this.cdao.count();
	}
}
