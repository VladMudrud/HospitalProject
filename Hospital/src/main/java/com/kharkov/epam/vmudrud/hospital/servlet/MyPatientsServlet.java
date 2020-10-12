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
import com.kharkov.epam.vmudrud.hospital.db.entity.MedicalCard;
import com.kharkov.epam.vmudrud.hospital.db.entity.User;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;
 
 
@WebServlet(urlPatterns = { "/myPatients" })
public class MyPatientsServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(MyPatientsServlet.class);

    private static final long serialVersionUID = 1L;
 
    public MyPatientsServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
    	log.info("doGet metod in patient list servlet is working");
        HttpSession session = request.getSession();
        User loginedUser = MyUtils.getLoginedUser(session);
        
        if (loginedUser == null || !loginedUser.getRole().equals("doctor")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String sort = request.getParameter("sort");
        if (sort==null) {
        	sort="alphabet";
        }
        request.setAttribute("user", loginedUser);
        String errorString = null;
        List<MedicalCard> list = null;
        MedicalCardController medicalCardController = null;
        try {
        	medicalCardController = new MedicalCardController();
            list = medicalCardController.getAllMyMedicalCardSorted(loginedUser.getId(), sort);
        } catch (SQLException e) {
            log.error("Problem with MySql server");
            errorString = "Problem with MySql server";
        } finally {
			try {
				medicalCardController.returnConnectionInPool();
			} catch (SQLException e) {
	            log.error("Problem with returning connection to the poll");
	            errorString = "Problem with MySql connection";
			}
		}
        request.setAttribute("errorString", errorString);
        request.setAttribute("patientList", list);
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/views/doctorMenuMyPatients.jsp");
        dispatcher.forward(request, response);
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	log.info("doPost metod in patient list servlet is working");
        HttpSession session = request.getSession();
        User loginedUser = MyUtils.getLoginedUser(session);
        if (loginedUser == null || !loginedUser.getRole().equals("doctor")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        String patientId= request.getParameter("id");
        if (patientId == null) {
            doGet(request, response);
            log.error("patient is NUll");
            return;
        }
        request.setAttribute("user", loginedUser);
        response.sendRedirect(request.getContextPath() + "/patientView" + "?id=" + patientId);
    }
 
}
