package com.excilys.formation.computerdatabase.ui;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;

import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.service.API;

public class ConsoleUI {

	//Need -h -i -c
	
	public static void main(String[] args) {		
		Options options = ConsoleConfig.getConfig();
		
		CommandLineParser parser = new DefaultParser();
		
		try {
			CommandLine cmd = parser.parse(options, args);
			
			if(cmd.hasOption("h") || cmd.hasOption("help") || cmd.getOptions().length == 0){
				System.out.println("\n This is the help message, fend yourself :p");
				
			} else if (cmd.hasOption("c") || cmd.hasOption("companies")){
				ArrayList<Company> companies = API.getCompanies();
				
				Iterator<Company> iterator = companies.iterator();
				while(iterator.hasNext()){
					Company company = iterator.next();
					
					System.out.println(company.toString());
				}
			}
		} catch (UnrecognizedOptionException e){
			System.out.println("\n ERROR : Unrecognized option " + e.getOption());
		} catch (ParseException e) {
			System.out.println("The program encountered some problem :(");
			e.printStackTrace();
		} 
				
	}
	
	
	
	

}
