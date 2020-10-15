package com.kharkov.epam.vmudrud.hospital.servlet;

import java.io.IOException;
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


@WebServlet(urlPatterns = { "/header" })
public class HeaderServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(HeaderServlet.class);

	private static final String ERROR_STRING = "errorString";

    public HeaderServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.info("doGet metod in header servlet is working");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.info("doPost metod in header servlet is working");
		HttpSession session = request.getSession();
    	session.setAttribute(ERROR_STRING, null);
        User loginedUser = MyUtils.getLoginedUser(session);
        if (loginedUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String commandName = request.getParameter("command");
     	Command command = CommandContainer.get(commandName);
        String forward = "" ;
		try {
			forward = command.execute(request, response);
		} catch (AppException e) {
        	session.setAttribute(ERROR_STRING, e.getMessage());
	    	log.error("An error has occurred:" + e.getMessage());
		}
        response.sendRedirect(request.getContextPath() + forward);
	}

}
