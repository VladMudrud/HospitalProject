package com.kharkov.epam.vmudrud.hospital.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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


@WebServlet(urlPatterns = { "/adminAddDoctorToPatientMenu" })
public class AddDoctorToPatientMenu extends HttpServlet {
       
	private static final Logger log = Logger.getLogger(AddDoctorToPatientMenu.class);

    private static final long serialVersionUID = 1L;
    
	private static final String ERROR_STRING = "errorString";
	
	private static final String SUCCESS_STRING = "successString";

    public AddDoctorToPatientMenu() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.info("doGet metod in adminAddDoctorToPatientMenu servlet is working");
        HttpSession session = request.getSession();
        session.setAttribute("idPatient", null);
        User loginedUser = MyUtils.getLoginedUser(session);
        if (loginedUser == null || !loginedUser.getRole().equals("admin")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String sort = request.getParameter("sort");
        if (sort==null) {
        	sort="alphabet";
        }
        request.setAttribute("user", loginedUser);
        List<MedicalCard> list = null;
        MedicalCardController medicalCardController = null;
        try {
        	medicalCardController = new MedicalCardController();
            list = medicalCardController.getAllMyMedicalCardSortedWithoutDoctor(sort);
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
        request.setAttribute("medicalCardList", list);
        session.setAttribute(ERROR_STRING, null);
        request.setAttribute(SUCCESS_STRING, null);

        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/views/adminPatientToDoctorMenu.jsp");
        dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("doPost metod in adminAddDoctorToPatientMenu servlet is working");
		HttpSession session = request.getSession();
		session.setAttribute(ERROR_STRING, null);
		session.setAttribute(SUCCESS_STRING, null);
		User loginedUser = MyUtils.getLoginedUser(session);
		if (loginedUser == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		String role = loginedUser.getRole();
		if (!role.equals("admin")) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		request.setAttribute("user", loginedUser);
		String id = getPatientId(request);
		String commandName = request.getParameter("command");
		Command command = CommandContainer.get(commandName);
		String forward = "/adminAddDoctorToPatientMenu";
		try {
			forward = command.execute(request, response);
		} catch (AppException e) {
			log.error("An error has occurred:" + e.getMessage());
			session.setAttribute(ERROR_STRING, e.getMessage());
		}
		response.sendRedirect(request.getContextPath() + forward + id);
	}


	private String getPatientId(HttpServletRequest request) {
		String id = request.getParameter("idPatient");
		if (id != null) {
			request.getSession().setAttribute("idPatient", id);
		return "&idPatient=" + id;  
		} else {
			return "";
		}
	}

}
