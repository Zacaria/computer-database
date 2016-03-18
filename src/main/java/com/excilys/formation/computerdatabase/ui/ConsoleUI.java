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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdatabase.dataBinders.dto.CompanyDTO;
import com.excilys.formation.computerdatabase.dataBinders.dto.ComputerDTO;
import com.excilys.formation.computerdatabase.dataBinders.dto.DTO;
import com.excilys.formation.computerdatabase.dataBinders.dto.PageDTO;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.service.CompanyService;
import com.excilys.formation.computerdatabase.service.ComputerService;

public class ConsoleUI {

	private static final Logger LOGGER = LoggerFactory.getLogger("com.excilys.formation.computerdatabase");
	private static final Pattern ID_PATTERN = Pattern.compile("^\\d+$");
	private static final ComputerService COMPUTER_SERVICE = ComputerService.getInstance();
	private static final CompanyService COMPANY_SERVICE = CompanyService.getInstance();

	private static Pager<Computer> computerPager;
	private static Pager<Company> companyPager;
	private static int defaultMax = 10;

	public static void main(String[] args) {
		Options options = ConsoleConfig.getConfig();

		CommandLineParser parser = new DefaultParser();

		computerPager = new Pager<>(COMPUTER_SERVICE.count(), (offset, max) -> COMPUTER_SERVICE.get(offset, max));
		companyPager = new Pager<>(COMPANY_SERVICE.count(), (offset, max) -> COMPANY_SERVICE.get(offset, max));

		computerPager.setRange(defaultMax);

		// FIXME : We always display all companies
		companyPager.setRange(50);

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
		PageDTO<Company> companies = companyPager.getPage(1);

		Iterator<DTO> iterator = companies.getElements().iterator();
		while (iterator.hasNext()) {
			CompanyDTO company = (CompanyDTO) iterator.next();

			System.out.println(company.toString());
		}
	}

	public static void handleShowComputers(CommandLine cmd) {
		Scanner sc = new Scanner(System.in);

		int command = 4;

		do {
			System.out.println("Page " + computerPager.getCurrentPageNumber() + " of " + computerPager.getTotalPage());

			Iterator<DTO> iterator = computerPager.getPage().getElements().iterator();

			if (command == 4) {
				iterator = computerPager.previous().getElements().iterator();
			} else if (command == 6) {
				iterator = computerPager.next().getElements().iterator();
			}

			while (iterator.hasNext()) {
				ComputerDTO computer = (ComputerDTO) iterator.next();

				System.out.println(computer.toString());
			}

			//FIXME : NextInt is not safe enough
			System.out.println("Hit 4 to previous page or hit 6 to next ! \nExit with 9");
			
			command = sc.nextInt();

		} while (command != 9);
		sc.close();

		System.out.println("Computer database existing, bye !");
	}

	public static void handleShowComputer(CommandLine cmd) {
		if (!cmd.hasOption("id") || cmd.getOptionValue("id") == null) {
			System.out.println("Please specify the id of the computer you want to see !");
		}

		if (!ID_PATTERN.matcher(cmd.getOptionValue("id")).matches()) {
			System.out.println("Please specify ids as numbers");
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
		if (cmd.hasOption("com")) {
			if (!ID_PATTERN.matcher(cmd.getOptionValue("com")).matches()) {
				System.out.println("Please specify the company id as a number");
				return;
			}

			Long companyId = Long.parseLong(cmd.getOptionValue("com"));

			company = COMPANY_SERVICE.get(companyId);
		}
		computer = Computer.builder(cmd.getOptionValue("name")).introduced(cmd.getOptionValue("intro"))
				.discontinued(cmd.getOptionValue("disco")).company(company).build();

		Long newId = COMPUTER_SERVICE.create(computer);

		System.out.println("New computer created with id " + newId);
	}

	public static void handleUpdateComputer(CommandLine cmd) {
		if (!cmd.hasOption("id") || cmd.getOptionValue("id") == null) {
			System.out.println("Please specify the id of the computer you want to update !");
			return;
		}

		if (!ID_PATTERN.matcher(cmd.getOptionValue("id")).matches()
				&& (!cmd.hasOption("com") || !ID_PATTERN.matcher(cmd.getOptionValue("com")).matches())) {
			System.out.println("Please specify ids as numbers");
			return;
		}

		Long id = Long.parseLong(cmd.getOptionValue("id"));

		// Get the actual computer to update it.
		Computer computer = COMPUTER_SERVICE.get(id);

		if (computer == null) {
			System.out.println("No computer was found with this id : " + id);
			return;
		}

		// Only change the given parameters, keep the other ones.
		if (cmd.hasOption("name")) {
			computer.setName(cmd.getOptionValue("name").trim());
		}
		if (cmd.hasOption("intro")) {
			computer.setIntroduced(cmd.getOptionValue("intro").trim());
		}
		if (cmd.hasOption("disco")) {
			computer.setDiscontinued(cmd.getOptionValue("disco").trim());
		}
		if (cmd.hasOption("com")) {
			Long comId = Long.parseLong(cmd.getOptionValue("com").trim());

			Company company = COMPANY_SERVICE.get(comId);
			computer.setCompany(company);
		}

		computer = COMPUTER_SERVICE.update(computer);

		System.out.println("computer updated : " + computer);
	}

	public static void handleDeleteComputer(CommandLine cmd) {
		Scanner scanner = new Scanner(System.in);

		if (!cmd.hasOption("id") || cmd.getOptionValue("id") == null) {
			System.out.println("Please specify the id of the computer you want to delete !");
			return;
		}

		if (!ID_PATTERN.matcher(cmd.getOptionValue("id")).matches()) {
			System.out.println("Please specify ids as numbers");
			return;
		}
		Long computerId = Long.parseLong(cmd.getOptionValue("id"));

		System.out.println("This operation is irreversible ? y/N :");

		String answer = scanner.next("^\\w$");
		System.out.println(answer);
		if (answer.charAt(0) == 'y') {
			boolean success = COMPUTER_SERVICE.delete(computerId);

			System.out.println(success ? "Computer deleted !" : "Nothing happened");

		} else {
			System.out.println("Delete Cancelled");
		}
	}

}
