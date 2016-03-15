package com.excilys.formation.computerdatabase.ui;

import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.computerdatabase.mapper.Page;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.service.CompanyService;
import com.excilys.formation.computerdatabase.service.ComputerService;

public class ConsoleUI {

	private static final Logger LOGGER = LogManager.getLogger("com.excilys.formation.computerdatabase.console");
	private static final Pattern ID_PATTERN = Pattern.compile("^\\d+$");
	private static final ComputerService COMPUTER_SERVICE = ComputerService.getInstance();
	private static final CompanyService COMPANY_SERVICE = CompanyService.getInstance();

	private static int companiesOffset = 0;
	private static int computerOffset = 0;
	private static int defaultMax = 10;

	public static void main(String[] args) {
		Options options = ConsoleConfig.getConfig();

		CommandLineParser parser = new DefaultParser();

		try {
			CommandLine cmd = parser.parse(options, args);

			if (cmd.hasOption("h") || cmd.getOptions().length == 0) {
				handleMenu(options);

			} else if (cmd.hasOption("e")) {
				handleShowCompanies(cmd);
			} else if (cmd.hasOption("c") && !cmd.hasOption("id")) {
				handleShowComputers(cmd);
			} else if (cmd.hasOption("c") && cmd.hasOption("id")) {
				handleShowComputer(cmd);
			} else if (cmd.hasOption("new")) {
				handleNewComputer(cmd);
			} else if (cmd.hasOption("up")) {
				handleUpdateComputer(cmd);
			} else if (cmd.hasOption("del")) {
				handleDeleteComputer(cmd);
			}
		} catch (UnrecognizedOptionException e) {
			LOGGER.error("\n ERROR : Unrecognized option " + e.getOption());
		} catch (ParseException e) {
			LOGGER.error("The program encountered some problem :(");
		}
	}

	public static void handleMenu(Options options) {

		// TODO : tell about syntax and mandatory args
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("ant", options);
	}

	public static void handleShowCompanies(CommandLine cmd) {
		Page<Company> companies = COMPANY_SERVICE.get(companiesOffset, defaultMax);

		Iterator<Company> iterator = companies.getElements().iterator();
		while (iterator.hasNext()) {
			Company company = iterator.next();

			LOGGER.info(company.toString());
		}
	}

	public static void handleShowComputers(CommandLine cmd) {
		Scanner sc = new Scanner(System.in);

		// Problem, I load everything
		//Page<Computer> computers = COMPUTER_SERVICE.get(computerOffset, defaultMax);
		Pager<Computer> pager = new Pager<>(COMPUTER_SERVICE.count(), (offset, max) -> COMPUTER_SERVICE.get(offset, max));
		

		int command = 4;

		do {
			System.out.println("Page " + pager.getCurrentPageNumber() + " of " + pager.getTotalPage());
			
			Iterator<Computer> iterator = pager.getPage().getElements().iterator();
			
			if (command == 4) {
				iterator = pager.previous().getElements().iterator();
			} else if (command == 6) {
				iterator = pager.next().getElements().iterator();
			}

			
			while (iterator.hasNext()) {
				Computer computer = iterator.next();

				System.out.println(computer.toString());
			}

			System.out.println("Hit 4 to previous page or hit 6 to next ! \nExit with 9");
			// if(sc.hasNextInt()){
			command = sc.nextInt();
			/*
			 * } else { System.out.println("wow !"); }
			 */

		} while (command != 9);
		sc.close();

		System.out.println("Computer database existing, bye !");
	}

	public static void handleShowComputer(CommandLine cmd) {
		if (!cmd.hasOption("id") || cmd.getOptionValue("id") == null) {
			LOGGER.info("Please specify the id of the computer you want to see !");
		}

		if (!ID_PATTERN.matcher(cmd.getOptionValue("id")).matches()) {
			LOGGER.info("Please specify ids as numbers");
			return;
		}

		Long id = Long.parseLong(cmd.getOptionValue("id"));

		Computer computer = COMPUTER_SERVICE.get(id);
		if (computer != null) {
			System.out.println(computer);
		} else {
			System.out.println("We found nothing with this id");
		}
	}

	public static void handleNewComputer(CommandLine cmd) {
		Company company = null;
		Computer computer = null;
		if(cmd.hasOption("com")){
			if(!ID_PATTERN.matcher(cmd.getOptionValue("com")).matches()){
				LOGGER.info("Please specify the company id as a number");
				return;
			}
			
			Long companyId = Long.parseLong(cmd.getOptionValue("com"));
			
			company = COMPANY_SERVICE.get(companyId);
		}
		computer = new Computer(cmd.getOptionValue("name"), cmd.getOptionValue("intro"),
				cmd.getOptionValue("disco"), company); 
		
		Long newId = COMPUTER_SERVICE.create(computer);

		LOGGER.info("New computer created with id " + newId);
	}

	public static void handleUpdateComputer(CommandLine cmd) {
		if (!cmd.hasOption("id") || cmd.getOptionValue("id") == null) {
			LOGGER.info("Please specify the id of the computer you want to update !");
			return;
		}

		if (!ID_PATTERN.matcher(cmd.getOptionValue("id")).matches()
				&& (!cmd.hasOption("com") || !ID_PATTERN.matcher(cmd.getOptionValue("com")).matches())) {
			LOGGER.info("Please specify ids as numbers");
			return;
		}

		Long id = Long.parseLong(cmd.getOptionValue("id"));
		Long comId = Long.parseLong(cmd.getOptionValue("com"));

		Computer computer = COMPUTER_SERVICE.get(id);
		Company company = COMPANY_SERVICE.get(comId);

		computer = COMPUTER_SERVICE.update(new Computer(id, cmd.getOptionValue("name"),
				cmd.getOptionValue("intro"), cmd.getOptionValue("disco"), company));

		LOGGER.info("computer updated : " + computer);
	}

	public static void handleDeleteComputer(CommandLine cmd) {
		// TODO : Say : Are you sure ? this operation is irreversible !

		if (!cmd.hasOption("id") || cmd.getOptionValue("id") == null) {
			LOGGER.error("Please specify the id of the computer you want to delete !");
		}
		
		if (!ID_PATTERN.matcher(cmd.getOptionValue("id")).matches()) {
			LOGGER.info("Please specify ids as numbers");
			return;
		}

		boolean success = COMPUTER_SERVICE.delete(cmd.getOptionValue("id"));

		if (success) {
			LOGGER.info("computer deleted !");
		} else {
			LOGGER.info("Nothing happened");
		}

	}

}
