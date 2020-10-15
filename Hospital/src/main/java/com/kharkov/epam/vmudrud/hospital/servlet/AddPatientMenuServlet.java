package com.kharkov.epam.vmudrud.hospital.servlet;

import java.io.IOException;

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
import com.kharkov.epam.vmudrud.hospital.db.entity.User;
import com.kharkov.epam.vmudrud.hospital.exception.AppException;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;


@WebServlet(urlPatterns = { "/adminAddPatientMenu" })
public class AddPatientMenuServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(AddPatientMenuServlet.class);
	
	private static final String ERROR_STRING = "errorString";
	
	private static final String SUCCESS_STRING = "successString";

    public AddPatientMenuServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.info("doGet metod in AddPatientMenuServlet servlet is working");
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
         request.setAttribute(ERROR_STRING, session.getAttribute(ERROR_STRING));
         request.setAttribute(SUCCESS_STRING, session.getAttribute(SUCCESS_STRING));
         session.setAttribute(ERROR_STRING, null);
     	 session.setAttribute(SUCCESS_STRING, null);
         RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/views" + "/adminMenuAddPatient.jsp");
         dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.info("doPost metod in AddPatientMenuServlet servlet is working");
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
        String commandName = request.getParameter("command");
     	Command command = CommandContainer.get(commandName);
        String forward = "/adminAddPatientMenu";
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
