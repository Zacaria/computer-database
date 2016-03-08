package com.excilys.formation.computerdatabase.ui;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class ConsoleConfig {
	public static Options getConfig(){
		Options options = new Options();
	
		Option help1 = new Option("h", "print this message");
		Option help2 = new Option("help", "print this message");
		
		Option companies1 = new Option("c", "shows the companies");
		Option companies2 = new Option("companies", "shows the companies");
		
		options.addOption(help1);
		options.addOption(help2);
		
		options.addOption(companies1);
		options.addOption(companies2);
		
		return options;
	}
}
