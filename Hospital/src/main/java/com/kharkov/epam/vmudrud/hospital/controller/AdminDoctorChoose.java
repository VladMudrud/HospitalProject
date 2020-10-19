package com.kharkov.epam.vmudrud.hospital.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kharkov.epam.vmudrud.hospital.command.Command;
import com.kharkov.epam.vmudrud.hospital.db.entity.User;
import com.kharkov.epam.vmudrud.hospital.exception.AppException;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;

public class AdminDoctorChoose extends Command {

	private static final long serialVersionUID = 3285898121351558006L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
		HttpSession session = request.getSession();
		User loginedUser = MyUtils.getLoginedUser(session);
		request.setAttribute("user", loginedUser);
		return "/adminDoctorChoose" + "?sort=alphabet";
	}

}
