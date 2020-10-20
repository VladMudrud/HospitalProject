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

import com.kharkov.epam.vmudrud.hospital.db.MedicalCardController;
import com.kharkov.epam.vmudrud.hospital.db.UserController;
import com.kharkov.epam.vmudrud.hospital.db.entity.MedicalCard;
import com.kharkov.epam.vmudrud.hospital.db.entity.User;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;

@WebServlet(urlPatterns = { "/myPatients" })
public class MyPatientsServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(MyPatientsServlet.class);

	private static final long serialVersionUID = 1L;

	private static final String ERROR_STRING = "errorString";

	public MyPatientsServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("doGet metod in myPatients servlet is working");
		HttpSession session = request.getSession();
		User loginedUser = MyUtils.getLoginedUser(session);

		if (loginedUser == null || !loginedUser.getRole().equals("doctor")) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		String sort = request.getParameter("sort");
		if (sort == null) {
			sort = "alphabet";
		}
		request.setAttribute("user", loginedUser);
		List<MedicalCard> list = null;
		MedicalCardController medicalCardController = null;
		UserController userController = null;
		try {
			medicalCardController = new MedicalCardController();
			userController = new UserController();
			list = medicalCardController.getAllMyMedicalCardSorted(userController.getDoctorByUserId(loginedUser.getId()), sort);
		} catch (SQLException e) {
			log.error("Problem with MySql server");
			session.setAttribute(ERROR_STRING, "Problem with MySql server:" + e.getMessage());
		} finally {
			try {
				medicalCardController.returnConnectionInPool();
				userController.returnConnectionInPool();
			} catch (SQLException e) {
				log.error("Problem with returning connection to the poll");
				session.setAttribute(ERROR_STRING, "Problem with returning connection to the poll");
			}
		}
		request.setAttribute(ERROR_STRING, session.getAttribute(ERROR_STRING));
		request.setAttribute("patientList", list);
		session.setAttribute(ERROR_STRING, null);
		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/views/doctorMenuMyPatients.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("doPost metod in myPatients servlet is working");
		HttpSession session = request.getSession();
		session.setAttribute(ERROR_STRING, null);
		User loginedUser = MyUtils.getLoginedUser(session);
		if (loginedUser == null || !loginedUser.getRole().equals("doctor")) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		String patientId = request.getParameter("id");
		if (patientId == null) {
			doGet(request, response);
			log.error("patient is NUll");
			return;
		}
		request.setAttribute("user", loginedUser);
		response.sendRedirect(request.getContextPath() + "/patientView" + "?id=" + patientId);
	}

}
