package com.kharkov.epam.vmudrud.hospital.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
import com.kharkov.epam.vmudrud.hospital.db.DoctorController;
import com.kharkov.epam.vmudrud.hospital.db.entity.Doctor;
import com.kharkov.epam.vmudrud.hospital.db.entity.User;
import com.kharkov.epam.vmudrud.hospital.exception.AppException;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;


@WebServlet(urlPatterns = { "/adminDoctorChoose" })
public class DoctorChoosePatient extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private static final Logger log = Logger.getLogger(DoctorChoosePatient.class);
    
	private static final String ERROR_STRING = "errorString";
	
	private static final String SUCCESS_STRING = "successString";

    public DoctorChoosePatient() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.info("doGet metod in adminDoctorChoose servlet is working");
        HttpSession session = request.getSession();
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
        String sort = request.getParameter("sort");
        if (sort==null) {
        	sort="alphabet";
        }
        request.setAttribute("sort", sort);
        request.setAttribute("idPatient", session.getAttribute("idPatient"));
        if (request.getAttribute("idPatient") == null) {
            response.sendRedirect(request.getContextPath() + "/adminAddDoctorToPatientMenu");
            return;
        }
        log.info(request.getAttribute("idPatient"));
        List<Doctor> list = null;
        DoctorController doctorController = null;
        try {
            doctorController = new DoctorController();
            list = doctorController.getAllOrderedWithCount(sort);
        } catch (SQLException e) {
            log.error("Problem with MySql server");
        	session.setAttribute(ERROR_STRING, "Problem with MySql server:" + e.getMessage());
        } finally {
			try {
				doctorController.returnConnectionInPool();
			} catch (SQLException e) {
	            log.error("Problem with returning connection to the poll");
	        	session.setAttribute(ERROR_STRING, "Problem with returning connection to the poll");
			}
		}
        request.setAttribute(SUCCESS_STRING, session.getAttribute(SUCCESS_STRING));
        request.setAttribute(ERROR_STRING, session.getAttribute(ERROR_STRING));
        request.setAttribute("doctorList", list);
        session.setAttribute(ERROR_STRING, null);
        request.setAttribute(SUCCESS_STRING, null);

        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/views" + "/adminChooseDoctorMenu.jsp");
        dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("doPost metod in DoctorChoosePatient servlet is working");
		HttpSession session = request.getSession();
		session.setAttribute(ERROR_STRING, null);
		session.setAttribute(SUCCESS_STRING, null);
		User loginedUser = MyUtils.getLoginedUser(session);
		
		if (loginedUser == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		String role = loginedUser.getRole();
		Map map = request.getParameterMap();
		if (!role.equals("admin")) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		request.setAttribute("user", loginedUser);
		String commandName = request.getParameter("command");
		Command command = CommandContainer.get(commandName);
		String forward = "/adminDoctorChoose" + "?sort=" + request.getAttribute("sort") + "&idPatient=" + request.getAttribute("idPatient");
		try {
			forward = command.execute(request, response);
			session.setAttribute(SUCCESS_STRING, "Operation successful");
		} catch (AppException e) {
			log.error("An error has occurred:" + e.getMessage());
			session.setAttribute(ERROR_STRING, e.getMessage());
		}
		response.sendRedirect(request.getContextPath() + forward);
	}

}
