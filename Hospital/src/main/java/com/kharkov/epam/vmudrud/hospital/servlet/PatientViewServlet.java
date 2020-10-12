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
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;


@WebServlet(urlPatterns = { "/patientView" })
public class PatientViewServlet extends HttpServlet {
	

	private static final long serialVersionUID = 1718695257049236456L;
	private static final Logger log = Logger.getLogger(PatientViewServlet.class);


    public PatientViewServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.info("doGet metod in patient view servlet is working");
        HttpSession session = request.getSession();
        User loginedUser = MyUtils.getLoginedUser(session);
        if (loginedUser == null || !loginedUser.getRole().equals("doctor")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        Integer id = Integer.valueOf(request.getParameter("id"));
        if (id == null) {
            response.sendRedirect(request.getContextPath() + "/myPatients");
            return;
        }
        request.setAttribute("user", loginedUser);
        String errorString = request.getParameter("error");
        MedicalCardController medicalCardController = null;
        MedicalCard medicalCard = new MedicalCard();
        try {
        	medicalCardController = new MedicalCardController();
        	medicalCard = medicalCardController.getEntityById(id);
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
         
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/views/myPatientView.jsp");
        dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("doPost metod in patient view servlet is working");
        HttpSession session = request.getSession();
        User loginedUser = MyUtils.getLoginedUser(session);

        if (loginedUser == null || !loginedUser.getRole().equals("doctor")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        if (request.getParameter("id")== null) {
            log.error("patient id is NULL");
            response.sendRedirect(request.getContextPath() + "/myPatients");
            return;
        }

        if ((request.getParameter("diagnosis") == null || request.getParameter("diagnosis").equals("Unknown") || request.getParameter("diagnosis").equals("")) && request.getParameter("type").equals("dishcharge")) {
            log.error("diagnosis is NULL");
            String errorString="Diagnosis is unknown, we cannot discharge the patient";
            response.sendRedirect(request.getContextPath() + "/patientView" + "?id=" + request.getParameter("id") + "&error=" + errorString);
            return;
        }
		// extract command name from the request
		String commandName = request.getParameter("type");
		// obtain command object by its name
		Command command = CommandContainer.get(commandName);

		// execute command and get forward address
		command.execute(request, response);
		if (!request.getParameter("type").equals("dishcharge")) {
		doGet(request, response);
		} else {
            response.sendRedirect(request.getContextPath() + "/myPatients");
            return;
		}
	}

}
