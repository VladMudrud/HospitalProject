package com.kharkov.epam.vmudrud.hospital.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.command.Command;
import com.kharkov.epam.vmudrud.hospital.controller.CommandContainer;
import com.kharkov.epam.vmudrud.hospital.db.MedicalCardController;
import com.kharkov.epam.vmudrud.hospital.db.entity.MedicalCard;
import com.kharkov.epam.vmudrud.hospital.db.entity.User;
import com.kharkov.epam.vmudrud.hospital.exception.AppException;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;


@WebServlet(urlPatterns = { "/patientView" })
public class PatientViewServlet extends HttpServlet {
	

	private static final long serialVersionUID = 1718695257049236456L;
	
	private static final Logger log = Logger.getLogger(PatientViewServlet.class);

	private static final String ERROR_STRING = "errorString";

	private static final String SUCCESS_STRING = "successString";

    public PatientViewServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.info("doGet metod in patient view servlet is working");
        HttpSession session = request.getSession();
        User loginedUser = MyUtils.getLoginedUser(session);
        if (loginedUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        if (!loginedUser.getRole().equals("doctor")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        Integer id = Integer.valueOf(request.getParameter("id"));
    	log.trace("id ==> " + id);
        if (id == null) {
            response.sendRedirect(request.getContextPath() + "/myPatients");
            return;
        }
        request.setAttribute("user", loginedUser);
        MedicalCardController medicalCardController = null;
        MedicalCard medicalCard = new MedicalCard();
        try {
        	medicalCardController = new MedicalCardController();
        	medicalCard = medicalCardController.getEntityByPatientId(id);
        } catch (SQLException e) {
            log.error("Problem with MySql server");
        	session.setAttribute(ERROR_STRING, "Problem with MySql server:" + e.getMessage());
        } finally {
			try {
				medicalCardController.returnConnectionInPool();
			} catch (SQLException e) {
	            log.error("Problem with returning connection to the poll");
	        	session.setAttribute(ERROR_STRING, "Problem with returning connection to the poll");
			}
		}
        request.setAttribute(ERROR_STRING, session.getAttribute(ERROR_STRING));
        request.setAttribute(SUCCESS_STRING, session.getAttribute(SUCCESS_STRING));
        request.setAttribute("medicalCard", medicalCard);
        session.setAttribute(SUCCESS_STRING, null);
        session.setAttribute(ERROR_STRING, null);

        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/views/myPatientView.jsp");
        dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("doPost metod in patient view servlet is working");
        HttpSession session = request.getSession();
    	session.setAttribute(ERROR_STRING, null);
    	session.setAttribute(SUCCESS_STRING, null);
        User loginedUser = MyUtils.getLoginedUser(session);

        if (loginedUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        if (!loginedUser.getRole().equals("doctor")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        if (request.getParameter("id")== null) {
            log.error("patient id is NULL");
        	session.setAttribute(ERROR_STRING, "patient id is NULL");
            response.sendRedirect(request.getContextPath() + "/myPatients");
            return;
        }
        if ((request.getParameter("diagnosis") == null || request.getParameter("diagnosis").equals("Unknown") || request.getParameter("diagnosis").equals("")) && request.getParameter("type").equals("dishcharge")) {
            log.error("diagnosis is NULL");
        	session.setAttribute(ERROR_STRING, "Diagnosis is unknown, we cannot discharge the patient");
            response.sendRedirect(request.getContextPath() + "/patientView" + "?id=" + request.getParameter("id"));
            return;
        }
		String commandName = request.getParameter("type");
		Command command = CommandContainer.get(commandName);
		try {
			command.execute(request, response);
        	session.setAttribute(SUCCESS_STRING, "Operation successful");
		} catch (AppException e) {
        	session.setAttribute(ERROR_STRING, e.getMessage());
	    	log.error("An error has occurred:" + e.getMessage());
		}
		if (!request.getParameter("type").equals("dishcharge")) {
            response.sendRedirect(request.getContextPath() + "/patientView" + "?id=" + request.getParameter("id"));
		} else {
            response.sendRedirect(request.getContextPath() + "/myPatients");
            return;
		}
	}

}
