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
import com.kharkov.epam.vmudrud.hospital.exception.AppException;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;

@WebServlet(urlPatterns = { "/therapyNurse" })
public class TherapyNurse extends HttpServlet {

	private static final Logger log = Logger.getLogger(TherapyNurse.class);

	private static final long serialVersionUID = 1L;

	private static final String ERROR_STRING = "errorString";

	public TherapyNurse() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("doGet metod in therapyNurse servlet is working");
		HttpSession session = request.getSession();
		User loginedUser = MyUtils.getLoginedUser(session);

		if (loginedUser == null || !loginedUser.getRole().equals("nurse")) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		request.setAttribute("user", loginedUser);
		List<Therapy> list = null;
		TherapyController therapyController = null;
		try {
			therapyController = new TherapyController();
			list = therapyController.getAllNoOperation();
		} catch (SQLException e) {
			log.error("Problem with MySql server");
			session.setAttribute(ERROR_STRING, "Problem with MySql server:" + e.getMessage());
		} finally {
			try {
				therapyController.returnConnectionInPool();
			} catch (SQLException e) {
				log.error("Problem with returning connection to the poll");
				session.setAttribute(ERROR_STRING, "Problem with returning connection to the poll");
			}
		}
		request.setAttribute(ERROR_STRING, session.getAttribute(ERROR_STRING));
		request.setAttribute("therapyList", list);
		session.setAttribute(ERROR_STRING, null);

		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/views/nurseMenu.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("doPost metod in therapyNurse servlet is working");
		HttpSession session = request.getSession();
		session.setAttribute(ERROR_STRING, null);
		User loginedUser = MyUtils.getLoginedUser(session);
		if (loginedUser == null || !loginedUser.getRole().equals("nurse")) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		if (request.getParameter("id") == null) {
			log.error("patient id is NULL");
			response.sendRedirect(request.getContextPath() + "/therapyNurse");
			return;
		}
		String commandName = request.getParameter("type");
		Command command = CommandContainer.get(commandName);
		try {
			command.execute(request, response);
		} catch (AppException e) {
			log.error("An error has occurred:" + e.getMessage());
			session.setAttribute(ERROR_STRING, e.getMessage());
		}
		doGet(request, response);
	}

}
