package com.kharkov.epam.vmudrud.hospital.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.command.Command;
import com.kharkov.epam.vmudrud.hospital.db.PatientController;
import com.kharkov.epam.vmudrud.hospital.db.entity.Patient;
import com.kharkov.epam.vmudrud.hospital.db.entity.User;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;

public class DishchargeCommand extends Command {

	private static final long serialVersionUID = -1752612121842879082L;

	private static final Logger log = Logger.getLogger(DishchargeCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
        HttpSession session = request.getSession();
        User loginedUser = MyUtils.getLoginedUser(session);
        Integer id = Integer.valueOf(request.getParameter("id"));
        request.setAttribute("user", loginedUser);
        String errorString = null;
        Patient patient = new Patient();
        PatientController patientController = null;

        try {
        	patientController = new PatientController();
        	patient = patientController.getEntityById(id);
        	patient.setStatus("Discharged from the hospital");
        	patient = patientController.updateStatus(patient);
        } catch (SQLException e) {
            log.error("Problem with MySql server");
            errorString = "Problem with MySql server";
        } finally {
			try {
				patientController.returnConnectionInPool();
			} catch (SQLException e) {
	            log.error("Problem with returning connection to the poll");
	            errorString = "Problem with MySql connection";
			}
		}
        request.setAttribute("errorString", errorString);
        return "/patientView";
	}

}
