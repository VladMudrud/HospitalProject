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
import com.kharkov.epam.vmudrud.hospital.db.TherapyController;
import com.kharkov.epam.vmudrud.hospital.db.entity.Therapy;
import com.kharkov.epam.vmudrud.hospital.db.entity.User;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;
 
 
@WebServlet(urlPatterns = { "/therapy" })
public class TherapyServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(TherapyServlet.class);

    private static final long serialVersionUID = 1L;
 
    public TherapyServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
    	log.info("doGet metod in therapy servlet is working");
        HttpSession session = request.getSession();
        User loginedUser = MyUtils.getLoginedUser(session);
        if (loginedUser == null || !loginedUser.getRole().equals("doctor") &&  !loginedUser.getRole().equals("nurse")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.setAttribute("user", loginedUser);
        String errorString = null;
        List<Therapy> list = null;
        TherapyController therapyController = null;
        try {
        	therapyController = new TherapyController();
            list = therapyController.getAll();
        } catch (SQLException e) {
            log.error("Problem with MySql server");
            errorString = "Problem with MySql server";
            throw new ServletException();
        } finally {
			try {
				therapyController.returnConnectionInPool();
			} catch (SQLException e) {
	            log.error("Problem with returning connection to the poll");
	            throw new ServletException();
			}
		}
        request.setAttribute("errorString", errorString);
        request.setAttribute("therapyList", list);
        RequestDispatcher dispatcher = null;
        if (loginedUser.getRole().equals("doctor")) {
        dispatcher = request.getServletContext()
                .getRequestDispatcher("/views/doctorMenuTherapy.jsp"); }
        else {
        dispatcher = request.getServletContext()
                .getRequestDispatcher("/therapyNurse");
        }
        dispatcher.forward(request, response);
    }
 
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	log.info("doPost metod in therapy servlet is working");
    	HttpSession session = request.getSession();
        User loginedUser = MyUtils.getLoginedUser(session);
        if (loginedUser == null || !loginedUser.getRole().equals("doctor") && !loginedUser.getRole().equals("nurse")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        if (request.getParameter("id")== null) {
            log.error("patient id is NULL");
            response.sendRedirect(request.getContextPath() + "/myPatients");
            return;
        }
        String commandName = request.getParameter("type");
     	Command command = CommandContainer.get(commandName);
        command.execute(request, response);
        response.sendRedirect(request.getContextPath() + "/therapy");
    }
    
}
