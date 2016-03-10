package com.excilys.formation.computerdatabase.ui;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.Computer;
import com.excilys.formation.computerdatabase.service.CompanyService;
import com.excilys.formation.computerdatabase.service.ComputerService;

public class ConsoleUI {
	
	private static final Logger consoleLogger = LogManager.getLogger("com.excilys.formation.computerdatabase.console");
	private static final ComputerService computerService = ComputerService.getInstance();
	private static final CompanyService companyService = CompanyService.getInstance();
	
	public static void main(String[] args) {		
		Options options = ConsoleConfig.getConfig();
		
		CommandLineParser parser = new DefaultParser();
		
		try {
			CommandLine cmd = parser.parse(options, args);
			
			if(cmd.hasOption("h") || cmd.getOptions().length == 0){				
				handleMenu(options);
				
			} else if (cmd.hasOption("e")){
				handleShowCompanies(cmd);				
			} else if (cmd.hasOption("C")){
				handleShowComputers(cmd);
			} else if (cmd.hasOption("c")){
				handleShowComputer(cmd);			
			} else if (cmd.hasOption("new")){
				handleNewComputer(cmd);				
			} else if (cmd.hasOption("up")){
				handleUpdateComputer(cmd);
			} else if (cmd.hasOption("del")){
				handleDeleteComputer(cmd);
			}
		} catch (UnrecognizedOptionException e){
			consoleLogger.error("\n ERROR : Unrecognized option " + e.getOption());
		} catch (ParseException e) {
			consoleLogger.error("The program encountered some problem :(");
		}
	}
	
	public static void handleMenu(Options options){
		
		//tell about syntax and mandatory args
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("ant", options);
	}
	
	public static void handleShowCompanies(CommandLine cmd){
		List<Company> companies = companyService.getCompanies();
		
		Iterator<Company> iterator = companies.iterator();
		while(iterator.hasNext()){
			Company company = iterator.next();
			
			consoleLogger.info(company.toString());
		}
	}
	
	public static void handleShowComputers(CommandLine cmd){
		Scanner sc = new Scanner(System.in);
		
		//Problem, I load everything
		List<Computer> computers = computerService.getComputers();
		Pager<Computer> pager = new Pager<>(computers);
		
		int command = 0;
		
		do{
			System.out.println("Page " + pager.getCurrentPageNumber() + " of " + pager.getTotalPage());
			
			if(command == 4){
				pager.previous();
			} else if (command == 6){
				pager.next();
			}
			List<Computer> page = pager.getCurrentPage();
			
			Iterator<Computer> iterator = page.iterator();
			while(iterator.hasNext()){
				Computer computer = iterator.next();
				
				System.out.println(computer.toString());
			}
			
			System.out.println("Hit 4 to previous page or hit 6 to next ! \nExit with 9");
			command = sc.nextInt();
		}while(command != 9);
		
		
		System.out.println("Computer database existing, bye !");
	}
	
	public static void handleShowComputer(CommandLine cmd){
		if(!cmd.hasOption("id") || cmd.getOptionValue("id") == null){
			consoleLogger.error("Please specify the id of the computer you want to see !");
		}
		Computer computer = computerService.getComputer(cmd.getOptionValue("id"));
		if(computer != null){
			consoleLogger.info(computer);
		}
	}
	
	public static void handleNewComputer(CommandLine cmd){
		Long newId = computerService.createComputer(
				cmd.getOptionValue("name"),
				cmd.getOptionValue("intro"), 
				cmd.getOptionValue("disco"),
				cmd.getOptionValue("com"));
		
		consoleLogger.info("New computer created with id " + newId);
	}
	
	public static void handleUpdateComputer(CommandLine cmd){
		if(!cmd.hasOption("id") || cmd.getOptionValue("id") == null){
			consoleLogger.error("Please specify the id of the computer you want to update !");
		}
		
		int affectedRows = computerService.updateComputer(
				cmd.getOptionValue("id"),
				cmd.getOptionValue("name"),
				cmd.getOptionValue("intro"), 
				cmd.getOptionValue("disco"),
				cmd.getOptionValue("com"));
		
		consoleLogger.info(affectedRows + " computer updated !");
	}
	
	public static void handleDeleteComputer(CommandLine cmd){
		//Are you sure, this operation is irreversible !
		
		if(!cmd.hasOption("id") || cmd.getOptionValue("id") == null){
			consoleLogger.error("Please specify the id of the computer you want to delete !");
		}
		
		int affectedRows = computerService.deleteComputer(cmd.getOptionValue("id"));
		
		consoleLogger.info(affectedRows + " computer deleted !");
	}
	
	
	
	

}
