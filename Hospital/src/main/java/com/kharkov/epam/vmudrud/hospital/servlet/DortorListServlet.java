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

import com.kharkov.epam.vmudrud.hospital.db.DoctorController;
import com.kharkov.epam.vmudrud.hospital.db.entity.Doctor;
import com.kharkov.epam.vmudrud.hospital.db.entity.User;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;


@WebServlet(urlPatterns = { "/doctorList" })
public class DortorListServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = Logger.getLogger(DortorListServlet.class);

	private static final String ERROR_STRING = "errorString";

    public DortorListServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.info("doGet metod in doctorList servlet is working");
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
        request.setAttribute(ERROR_STRING, session.getAttribute(ERROR_STRING));
        request.setAttribute("doctorList", list);
        session.setAttribute(ERROR_STRING, null);

        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/views" + "/adminDoctorListMenu.jsp");
        dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
    	session.setAttribute(ERROR_STRING, null);
		doGet(request, response);
	}

}
