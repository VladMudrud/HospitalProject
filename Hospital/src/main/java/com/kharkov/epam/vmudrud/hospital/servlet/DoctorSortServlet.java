package com.kharkov.epam.vmudrud.hospital.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.db.entity.User;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;

@WebServlet(urlPatterns = { "/doctorsSort" })
public class DoctorSortServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(DoctorSortServlet.class);

	private static final String ERROR_STRING = "errorString";

	public DoctorSortServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("doGet metod in DoctorSortServlet servlet is working");
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("doPost metod in DoctorSortServlet servlet is working");
		HttpSession session = request.getSession();
		session.setAttribute(ERROR_STRING, null);

		User loginedUser = MyUtils.getLoginedUser(session);
		if (loginedUser == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		if (!loginedUser.getRole().equals("admin")) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		request.setAttribute("user", loginedUser);
		response.sendRedirect(request.getContextPath() + "/doctorList" + "?sort=" + request.getParameter("sort"));
	}

}