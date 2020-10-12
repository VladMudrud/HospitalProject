package com.kharkov.epam.vmudrud.hospital.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kharkov.epam.vmudrud.hospital.command.Command;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;

public class LogoutCommand extends Command {

	private static final long serialVersionUID = 2034071455112264830L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
        MyUtils.storeLoginedUser(session, null);
        MyUtils.deleteUserCookie(response);
        return "/login";
	}

}