package com.excilys.formation.computerdatabase.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.computerdatabase.mapper.Page;
import com.excilys.formation.computerdatabase.model.Company;
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

	public Page<Company> get(int from, int max) {
		LOGGER.info("access");

		Page<Company> companies = new Page<>(from, this.cdao.find(from, max), this.cdao.count());

		return companies;
	}

	// TODO : Remove this !
	public Company get(String pId) {
		LOGGER.info("access");
		try {
			Long id = Long.parseLong(pId);

			return this.cdao.find(id);
		} catch (NumberFormatException e) {
			LOGGER.error(e.getClass().getName() + " : " + e.getMessage());
			return null;
		}
	}

	public Company get(Long id) {
		LOGGER.info("access");

		return this.cdao.find(id);
	}
}
