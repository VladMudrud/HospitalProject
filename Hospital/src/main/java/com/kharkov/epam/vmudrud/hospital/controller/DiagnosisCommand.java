package com.kharkov.epam.vmudrud.hospital.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.command.Command;
import com.kharkov.epam.vmudrud.hospital.db.MedicalCardController;
import com.kharkov.epam.vmudrud.hospital.db.entity.MedicalCard;
import com.kharkov.epam.vmudrud.hospital.db.entity.User;
import com.kharkov.epam.vmudrud.hospital.exception.AppException;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;

public class DiagnosisCommand extends Command {


	private static final long serialVersionUID = 6144238497126539260L;

	private static final Logger log = Logger.getLogger(DiagnosisCommand.class);

	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws AppException {
        HttpSession session = request.getSession();
        User loginedUser = MyUtils.getLoginedUser(session);
        Integer id = Integer.valueOf(request.getParameter("id"));
        request.setAttribute("user", loginedUser);
        MedicalCardController medicalCardController = null;
        MedicalCard medicalCard = new MedicalCard();
        try {
        	medicalCardController = new MedicalCardController();
        	medicalCard = medicalCardController.getEntityByPatientId(id);
        	medicalCard.setDiagnosis(request.getParameter("diagnosis"));
        	medicalCardController.update(medicalCard);
        } catch (SQLException e) {
            log.error("Problem with MySql server");
            throw new AppException(e.getMessage());
        } finally {
			try {
				medicalCardController.returnConnectionInPool();
			} catch (SQLException e) {
	            log.error("Problem with returning connection to the poll");
	            throw new AppException("Problem with returning connection to the poll");
			}
		}
        request.setAttribute("medicalCard", medicalCard);
        return "/patientView";
	}



}
