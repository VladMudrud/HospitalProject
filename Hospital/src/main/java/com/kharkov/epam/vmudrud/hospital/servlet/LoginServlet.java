package com.kharkov.epam.vmudrud.hospital.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.db.UserController;
import com.kharkov.epam.vmudrud.hospital.db.entity.User;
import com.kharkov.epam.vmudrud.hospital.exception.AppException;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;
 

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
 
	private static final long serialVersionUID = 5595561946481284666L;
	
	private static final Logger log = Logger.getLogger(LoginServlet.class);

	private static final String ERROR_STRING = "errorString";

	public LoginServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	log.info("doGet metod in login servlet is working");
    	HttpSession session = request.getSession();
        request.setAttribute(ERROR_STRING, session.getAttribute(ERROR_STRING));
        session.setAttribute(ERROR_STRING, null);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
 
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	log.info("doPost metod in login servlet is working");
    	HttpSession session = request.getSession();
    	session.setAttribute(ERROR_STRING, null);
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String rememberMeStr = request.getParameter("rememberMe");
        boolean remember = "Y".equals(rememberMeStr);
        User user = null;
        boolean hasError = false;
        if (login == null || password == null || login.length() == 0 || password.length() == 0) {
            hasError = true;
        	session.setAttribute(ERROR_STRING, "No date to login");
        	log.info("No date to login");
        } else {
        	UserController userController=null;
            try {
				userController = new UserController();
                user = userController.getUserByLoginAndPassword(login, password);
            } catch (SQLException e) {
                hasError = true;
            	session.setAttribute(ERROR_STRING, "Problem with MySql server:" + e.getMessage());
            	log.error("SQL problems");
            } catch (AppException ex) {
                hasError = true;
            	session.setAttribute(ERROR_STRING, "User Name or Password invalid");
            	log.error("Ivalid user login or password");
			}
            finally {
				try {
					userController.returnConnectionInPool();
				} catch (SQLException e) {
	            	log.info("Connection pool not returned");
	            	session.setAttribute(ERROR_STRING, "Connection pool not returned");
				}
			}
            
        }
        if (hasError) {
            user = new User();
            request.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/login");
        }
        else {
            session = request.getSession();
            MyUtils.storeLoginedUser(session, user);
            if (remember) {
                MyUtils.storeUserCookie(response, user);
            }
            else {
                MyUtils.deleteUserCookie(response);
            }
            whichRoleUserAndRedirect(request, response, user);
        }
    }
    
    protected void whichRoleUserAndRedirect(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
    	String roleString = user.getRole();
    	if (roleString.equals("doctor")) {
        	log.info("user is doctor");
            response.sendRedirect(request.getContextPath() +  "/patientList" + "?sort=alphabet");
            return;
    	}
    	if (roleString.equals("admin")) {
        	log.info("user is admin");
            response.sendRedirect(request.getContextPath() + "/patientList" + "?sort=alphabet");
            return;
    	}
    	if (roleString.equals("nurse")) {
        	log.info("user is nurse");
            response.sendRedirect(request.getContextPath() + "/therapyNurse");
            return;
    	}
        String errorStringPage = "User has unknown role";
        request.setAttribute(ERROR_STRING, errorStringPage);
    	log.error("user is unknown");
        response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
 
    }
 
}
