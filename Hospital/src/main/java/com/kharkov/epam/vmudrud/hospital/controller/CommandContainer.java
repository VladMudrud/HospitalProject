package com.kharkov.epam.vmudrud.hospital.controller;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.command.Command;


public class CommandContainer {
	
	private static final Logger log = Logger.getLogger(CommandContainer.class);
	
	private static Map<String, Command> commands = new TreeMap<String, Command>();
	
	static {
		commands.put("diagnosis", new DiagnosisCommand());
		commands.put("therapy", new TherapyCommand());
		commands.put("dishcharge", new DishchargeCommand());
		commands.put("compTreatment", new CompTreatmentCommand());
		commands.put("Logout", new LogoutCommand());
		commands.put("Add patient", new AddPatient());

	}


	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			log.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand"); 
		}
		
		return commands.get(commandName);
	}
	
}