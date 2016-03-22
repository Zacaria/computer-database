package com.excilys.formation.computerdatabase.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class ComputerService implements Servable<Computer>{
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

	@Override
	public int count() {
		LOGGER.info("access");

		int count = this.cdao.count();

		return count;
	}

	@Override
	public Page<Computer> get(int offset, int limit) {
		LOGGER.info("access");

		Page<Computer> computerPage = new Page<>(offset, this.cdao.find(offset, limit), this.cdao.count());

		return computerPage;
	}

	@Override
	public Computer get(Long id) {
		LOGGER.info("access");
		return this.cdao.find(id);
	}

	@Override
	public Long create(Computer computer) {
		LOGGER.info("access");
		LOGGER.debug(computer.toString());
		if (computer.getName() == null || computer.getName().isEmpty()) {
			LOGGER.error("ERROR Insert : Could not create an unnamed computer !");
			return null;
		}
		if (computer.getCompany().getId() == null) {
			LOGGER.error("ERROR Insert : Could not create a computer without it's company !!");
			return null;
		}
		return this.cdao.create(computer);
	}

	@Override
	public boolean delete(Long id) {
		LOGGER.info("access");
		LOGGER.debug("Delete Computer with id " + id);
		return this.cdao.delete(id);
	}

	@Override
	public Computer update(Computer computer) {
		LOGGER.info("access");
		LOGGER.debug(computer.toString());
		
		//Illegal
		if (computer.getName() == null || computer.getName().isEmpty()) {
			LOGGER.error("ERROR Update : Could not update to an unnamed computer !");
			return null;
		}
		if (computer.getCompany().getId() == null) {
			LOGGER.error("ERROR Update : Could not update to a computer without it's company !!");
			return null;
		}

		return this.cdao.update(computer);
	}
}