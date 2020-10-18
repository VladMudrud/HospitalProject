package com.kharkov.epam.vmudrud.hospital.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.command.Command;
import com.kharkov.epam.vmudrud.hospital.db.PatientController;
import com.kharkov.epam.vmudrud.hospital.db.entity.Doctor;
import com.kharkov.epam.vmudrud.hospital.db.entity.Patient;
import com.kharkov.epam.vmudrud.hospital.db.entity.User;
import com.kharkov.epam.vmudrud.hospital.exception.AppException;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;

public class ConfirmDoctorCommand extends Command {
	
	private static final long serialVersionUID = -8343926846779789396L;
	
	private static final Logger log = Logger.getLogger(CompTreatmentCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
		HttpSession session = request.getSession();
        User loginedUser = MyUtils.getLoginedUser(session);
        request.setAttribute("user", loginedUser);
        PatientController patientController = null;
        Patient entity = new Patient();
        entity.setId(Integer.valueOf((String) session.getAttribute("idPatient")));
        entity.setDoctor(new Doctor());
        entity.getDoctor().setId(Integer.valueOf(request.getParameter("doctor")));
        try {
        	patientController = new PatientController();
        	patientController.updateDoctor(entity);
        	session.setAttribute("idPatient", null);
        } catch (SQLException e) {
            log.error("Problem with MySql server");
            throw new AppException(e.getMessage());
        } finally {
			try {
				patientController.returnConnectionInPool();
			} catch (SQLException e) {
	            log.error("Problem with returning connection to the poll");
	            throw new AppException("Problem with returning connection to the poll");
			}
		}
		return "/adminAddDoctorToPatientMenu";
	}

}
