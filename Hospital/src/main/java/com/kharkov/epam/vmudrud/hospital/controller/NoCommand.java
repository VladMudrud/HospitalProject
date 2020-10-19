package com.kharkov.epam.vmudrud.hospital.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.command.Command;
import com.kharkov.epam.vmudrud.hospital.exception.AppException;

public class NoCommand extends Command {

	private static final long serialVersionUID = -1203621351252872138L;

	private static final Logger log = Logger.getLogger(NoCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
		log.error("command doesn't exist");
		return "/views/errorPage.jsp";
	}

}
