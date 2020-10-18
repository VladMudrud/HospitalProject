package com.kharkov.epam.vmudrud.hospital.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kharkov.epam.vmudrud.hospital.command.Command;
import com.kharkov.epam.vmudrud.hospital.db.entity.User;
import com.kharkov.epam.vmudrud.hospital.exception.AppException;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;

public class AdminPatientSorting extends Command{

	private static final long serialVersionUID = -4980372816618205329L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        HttpSession session = request.getSession();
        User loginedUser = MyUtils.getLoginedUser(session);
        request.setAttribute("user", loginedUser);
        return "/adminAddDoctorToPatientMenu" + "?sort=" + request.getParameter("sort");
	}

}
