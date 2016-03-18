package com.excilys.formation.computerdatabase.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdatabase.dataBinders.dto.ComputerDTO;
import com.excilys.formation.computerdatabase.dataBinders.dto.PageDTO;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.model.Page;
import com.excilys.formation.computerdatabase.persistence.ComputerDAO;
import com.excilys.formation.computerdatabase.persistence.Crudable;


/**
 * This is a Singleton.
 * Implemented with an inner class Holder.
 * The holder is initialized only on the first call.
 * This is Initialization-on-demand holder idiom.
 * As the holder is a class, it's access is serial, i.e non-concurrent, i.e thread-safe.
 * See JLS class initialization specification.
 * @author Zacaria
 *
 */
public class ComputerService{
	private static final Logger LOGGER = LoggerFactory.getLogger("com.excilys.formation.computerdatabase");

	private final Crudable<Computer> cdao;

	private ComputerService() {
		this.cdao = ComputerDAO.INSTANCE;
	}

	/**
	 * This is initialized only when getInstance is called.
	 * Which makes the Singleton lazy.
	 * @author Zacaria
	 *
	 */
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


	public boolean delete(Long id) {
		LOGGER.info("access");
		return this.cdao.delete(id);
	}

	public Computer update(Computer computer) {
		LOGGER.info("access");

		return this.cdao.update(computer);
	}
}