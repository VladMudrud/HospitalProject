package com.kharkov.epam.vmudrud.hospital.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.command.Command;
import com.kharkov.epam.vmudrud.hospital.exception.AppException;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;

public class LogoutCommand extends Command {

	private static final long serialVersionUID = 2034071455112264830L;

	private static final Logger log = Logger.getLogger(LogoutCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
		HttpSession session = request.getSession();
		log.info("User: " + MyUtils.getLoginedUser(session) + " ==> logout");
		MyUtils.storeLoginedUser(session, null);
		MyUtils.deleteUserCookie(response);
		return "/login";
	}

}
