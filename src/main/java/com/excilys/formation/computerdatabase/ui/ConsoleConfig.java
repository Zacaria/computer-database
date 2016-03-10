package com.excilys.formation.computerdatabase.ui;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class ConsoleConfig {
	private ConsoleConfig(){
		
	}
	
	public static Options getConfig(){
		Options options = new Options();
		
		Option help = Option
				.builder("h")
				.longOpt("help")
				.desc("print this message")
				.build();
		
		//Categories
		Option companies = Option
				.builder("e")
				.longOpt("companies")
				.desc("Lists the companies")
				.build(); 
		
		Option computers = Option
				.builder("C")
				.longOpt("computers")
				.desc("Lists the computers")
				.build();
		
		Option computer = Option
				.builder("c")
				.longOpt("computer")
				.desc("Details a computer, id option mandatory")
				.build();
		
		//Enablers
		Option newParam = new Option("new", "enables the creation of computer");
		Option del = new Option("del", "enables the deletion of computer");
		Option updateParam = new Option("up", "enables the update of computer");
		
		//Data
		Option id = new Option("id", true, "id of the computer to retrieve");
		Option name = new Option("name", true, "name of the computer to retrieve");
		Option introduced = new Option("intro", true, "yyyy-MM-dd date of the introduction of the computer");
		Option discontinued = new Option("disco", true, "yyyy-MM-dd date of the discontinuation of the computer");
		Option company = new Option("com", true, "id of the company of the computer");
		
		
		options.addOption(help);
		
		options.addOption(companies);
		
		options.addOption(computers);
		options.addOption(computer);
		
		
		options.addOption(newParam);
		options.addOption(del);
		options.addOption(updateParam);
		
		options.addOption(id);
		options.addOption(name);
		options.addOption(introduced);
		options.addOption(discontinued);
		options.addOption(company);
		
		return options;
	}
}
