package com.kharkov.epam.vmudrud.hospital.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.command.Command;
import com.kharkov.epam.vmudrud.hospital.db.ConnectionPool;
import com.kharkov.epam.vmudrud.hospital.db.MedicalCardController;
import com.kharkov.epam.vmudrud.hospital.db.TherapyController;
import com.kharkov.epam.vmudrud.hospital.db.entity.Therapy;
import com.kharkov.epam.vmudrud.hospital.db.entity.User;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;

public class TherapyCommand extends Command {


	private static final long serialVersionUID = -4771248333475442473L;
	
	private static final Logger log = Logger.getLogger(TherapyCommand.class);


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
        User loginedUser = MyUtils.getLoginedUser(session);
        request.setAttribute("user", loginedUser);
        Integer medicalCardId=Integer.valueOf(request.getParameter("medicalCardId"));
        String errorString = null;
        TherapyController therapyController = null;
        MedicalCardController medicalCardController = null;
        Therapy therapy = new Therapy();
        therapy.setTitle(request.getParameter("title"));
        therapy.setType(request.getParameter("therapyType"));
        therapy.setStatus("in progress");
        try {
        	ConnectionPool connectionPool = ConnectionPool.getInstance();
        	Connection connection = connectionPool.getConnection();
        	therapyController = new TherapyController(connectionPool, connection);
        	medicalCardController = new MedicalCardController(connectionPool, connection);
        	therapy.setMedicalCard(medicalCardController.getEntityById(medicalCardId));
        	therapyController.create(therapy);
        } catch (SQLException | NamingException e) {
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
        return "/patientView";
	}

}
