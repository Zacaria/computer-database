package com.excilys.formation.computerdatabase.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.computerdatabase.dataBinders.dto.ComputerDTO;
import com.excilys.formation.computerdatabase.dataBinders.dto.PageDTO;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.model.Page;
import com.excilys.formation.computerdatabase.persistence.ComputerDAO;
import com.excilys.formation.computerdatabase.persistence.Crudable;

public class ComputerService {

	private static final Logger LOGGER = LogManager.getLogger("com.excilys.formation.computerdatabase");

	private final Crudable<Computer> cdao;

	private ComputerService() {
		this.cdao = new ComputerDAO();
	}

	private static class ComputerServiceHolder {
		private final static ComputerService instance = new ComputerService();
	}

	public static ComputerService getInstance() {
		return ComputerServiceHolder.instance;
	}

	public int count() {
		LOGGER.info("access");

		int count = this.cdao.count();

		return count;
	}

	public PageDTO<Computer> get(int from, int max) {
		LOGGER.info("access");

		Page<Computer> computerPage = new Page<>(from, this.cdao.find(from, max), this.cdao.count());
		
		PageDTO<Computer> computers = new PageDTO<>(computerPage, computer -> new ComputerDTO(computer));

		return computers;
	}

	public Computer get(Long id) {
		LOGGER.info("access");
		return this.cdao.find(id);
	}

	public Long create(Computer computer) {
		LOGGER.info("access");
		return this.cdao.create(computer);
	}

	public boolean delete(String id) {
		LOGGER.info("access");
		Long computerId = null;
		try {
			computerId = Long.parseLong(id);

		} catch (NumberFormatException e) {
			LOGGER.error(e.getClass().getName() + " : " + e.getMessage());
			return false;
		}
		return this.cdao.delete(computerId);
	}

	public Computer update(Computer computer) {
		LOGGER.info("access");

		return this.cdao.update(computer);
	}
}