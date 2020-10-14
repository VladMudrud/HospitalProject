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

import com.kharkov.epam.vmudrud.hospital.db.PatientController;
import com.kharkov.epam.vmudrud.hospital.db.entity.Patient;
import com.kharkov.epam.vmudrud.hospital.db.entity.User;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;
 
 
@WebServlet(urlPatterns = { "/patientList" })
public class PatientListServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(PatientListServlet.class);

    private static final long serialVersionUID = 1L;
 
    public PatientListServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
    	log.info("doGet metod in patient list servlet is working");
        HttpSession session = request.getSession();
        User loginedUser = MyUtils.getLoginedUser(session);      
        if (loginedUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        } 
        String role = loginedUser.getRole();
        if (!(role.equals("doctor") || role.equals("admin"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.setAttribute("user", loginedUser);
        String sort = request.getParameter("sort");
        if (sort==null) {
        	sort="alphabet";
        }
        String errorString = null;
        List<Patient> list = null;
        PatientController patientController = null;
        try {
        	patientController = new PatientController();
            list = patientController.getAllOrdered(sort);
        } catch (SQLException e) {
            log.error("Problem with MySql server");
            errorString = "Problem with MySql server";
        } finally {
			try {
				patientController.returnConnectionInPool();
			} catch (SQLException e) {
	            log.error("Problem with returning connection to the poll");
	            throw new ServletException();
			}
		}
        request.setAttribute("errorString", errorString);
        request.setAttribute("patientList", list);
        String menu = getMenuByRole(role);
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/views" + menu);
        dispatcher.forward(request, response);
    }
 
    
    private String getMenuByRole(String role) {
    	if (role.equals("admin")) {
    		return "/adminMenuPathients.jsp";
    	} else {
			return "/doctorMenu.jsp";
		}
	}

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.sendRedirect(request.getContextPath() + "/patientList" + "?sort=" + request.getParameter("sort"));
    }
 
}
