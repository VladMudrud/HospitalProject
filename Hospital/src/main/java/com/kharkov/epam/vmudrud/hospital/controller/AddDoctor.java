package com.kharkov.epam.vmudrud.hospital.controller;

import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.command.Command;
import com.kharkov.epam.vmudrud.hospital.db.DoctorController;
import com.kharkov.epam.vmudrud.hospital.db.entity.Category;
import com.kharkov.epam.vmudrud.hospital.db.entity.Doctor;
import com.kharkov.epam.vmudrud.hospital.db.entity.User;
import com.kharkov.epam.vmudrud.hospital.exception.AppException;

public class AddDoctor extends Command {

	 
	private static final Logger log = Logger.getLogger(AddDoctor.class);
		
	private static final long serialVersionUID = 4578945386631647803L;
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
		try {
			validateDoctorDateFromRequest(request);
		} catch (AppException e) {
			log.error("An error has occurred:" + e.getMessage());
			throw new AppException(e.getMessage());
		}
		User user = buildUserFromRequest(request);
		Doctor doctor = buildDoctorFromRequest(request);
		DoctorController doctorController = null;
		try {
			doctorController = new DoctorController();
			doctorController.transactionDoctorCreate(doctor, user);
        } catch (SQLException e) {
            log.error("Problem with MySql", e);
            throw new AppException(e.getMessage());
        } finally {
			try {
				doctorController.returnConnectionInPool();
			} catch (SQLException e) {
	            log.error("Problem with returning connection to the poll");
	            throw new AppException("Problem with returning connection to the poll");
			}
		} 
		return "/adminAddDoctorMenu";
	}

	private Doctor buildDoctorFromRequest(HttpServletRequest request) {
		Doctor doctor = new Doctor();
		doctor.setFirstName(request.getParameter("firstName"));
		doctor.setSecondName(request.getParameter("secondName"));
		doctor.setAge(Date.valueOf(request.getParameter("dateOfBirth")));
		doctor.setGender(request.getParameter("gender"));
		doctor.setCategory(new Category());
		doctor.getCategory().setTitle(request.getParameter("category"));
		return doctor;
	}

	private User buildUserFromRequest(HttpServletRequest request) {
		User user = new User();
		user.setLogin(request.getParameter("login"));
		user.setPassword(request.getParameter("password"));
		user.setRole("doctor");
		return user;
	}

	private void validateDoctorDateFromRequest(HttpServletRequest request) throws AppException {
		if (request.getParameter("login") == null || request.getParameter("login").isEmpty()) {
			throw new AppException("Please input login correctly");
		}
		if (request.getParameter("password") == null || request.getParameter("password").isEmpty()) {
			throw new AppException("Please input password correctly");
		}
		if (request.getParameter("firstName") == null || request.getParameter("firstName").isEmpty()) {
			throw new AppException("Please input first name correctly");
		}
		if (request.getParameter("secondName") == null || request.getParameter("secondName").isEmpty()) {
			throw new AppException("Please input second name correctly");
		}
		if (request.getParameter("dateOfBirth") == null || request.getParameter("dateOfBirth").isEmpty()) {
			throw new AppException("Please input date of birth correctly");
		}
		try {
		Date.valueOf(request.getParameter("dateOfBirth"));
		} catch (IllegalArgumentException e) {
			throw new AppException("Please input date of birth correctly");
		}
		
	}

}
