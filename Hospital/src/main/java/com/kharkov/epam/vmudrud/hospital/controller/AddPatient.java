package com.kharkov.epam.vmudrud.hospital.controller;

import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.command.Command;
import com.kharkov.epam.vmudrud.hospital.db.PatientController;
import com.kharkov.epam.vmudrud.hospital.db.entity.Patient;
import com.kharkov.epam.vmudrud.hospital.exception.AppException;

public class AddPatient extends Command {

 
	private static final Logger log = Logger.getLogger(AddPatient.class);
	
	private static final long serialVersionUID = 4578945386631647803L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
		try {
			validatePatientDateFromRequest(request);
		} catch (AppException e) {
			log.error("An error has occurred:" + e.getMessage());
			throw new AppException(e.getMessage());
		}
		Patient patient = buildPatientFromRequest(request);
		PatientController patientController = null;
		try {
			patientController = new PatientController();
			patientController.transactionPatientCreate(patient);
        } catch (SQLException e) {
            log.error("Problem with MySql");
            throw new AppException(e.getMessage());
        } finally {
			try {
				patientController.returnConnectionInPool();
			} catch (SQLException e) {
	            log.error("Problem with returning connection to the poll");
	            throw new AppException("Problem with returning connection to the poll");
			}
		} 
		return "/adminAddPatientMenu";
        
	}

	private Patient buildPatientFromRequest(HttpServletRequest request) {
		Patient patient = new Patient();
		patient.setFirstName(request.getParameter("firstName"));
		patient.setSecondName(request.getParameter("secondName"));
		patient.setAge(Date.valueOf(request.getParameter("dateOfBirth")));
		patient.setGender(request.getParameter("gender"));
		patient.setStatus("Hospital treatment");
		return patient;
	}
	
	private void validatePatientDateFromRequest(HttpServletRequest request) throws AppException  {
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
