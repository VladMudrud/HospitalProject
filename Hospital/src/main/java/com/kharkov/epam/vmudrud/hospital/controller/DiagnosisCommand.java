package com.kharkov.epam.vmudrud.hospital.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.command.Command;
import com.kharkov.epam.vmudrud.hospital.db.MedicalCardController;
import com.kharkov.epam.vmudrud.hospital.db.entity.MedicalCard;
import com.kharkov.epam.vmudrud.hospital.db.entity.User;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;

public class DiagnosisCommand extends Command {


	private static final long serialVersionUID = 6144238497126539260L;

	private static final Logger log = Logger.getLogger(DiagnosisCommand.class);

	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
        HttpSession session = request.getSession();
        User loginedUser = MyUtils.getLoginedUser(session);
        Integer id = Integer.valueOf(request.getParameter("id"));
        request.setAttribute("user", loginedUser);
        String errorString = null;
        MedicalCardController medicalCardController = null;
        MedicalCard medicalCard = new MedicalCard();

        try {
        	medicalCardController = new MedicalCardController();
        	medicalCard = medicalCardController.getEntityById(id);
        	medicalCard.setDiagnosis(request.getParameter("diagnosis"));
        	medicalCardController.update(medicalCard);
        } catch (SQLException e) {
            log.error("Problem with MySql server");
            errorString = "Problem with MySql server";
        } finally {
			try {
				medicalCardController.returnConnectionInPool();
			} catch (SQLException e) {
	            log.error("Problem with returning connection to the poll");
	            errorString = "Problem with MySql connection";
			}
		}
        request.setAttribute("errorString", errorString);
        request.setAttribute("medicalCard", medicalCard);
        return "/patientView";
	}



}
