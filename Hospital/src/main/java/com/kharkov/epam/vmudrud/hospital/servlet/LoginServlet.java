package com.kharkov.epam.vmudrud.hospital.servlet;

import java.io.IOException;
import java.sql.SQLException;

import java.util.NoSuchElementException;

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
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;
 

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
 
	private static final long serialVersionUID = 5595561946481284666L;
	
	private static final Logger log = Logger.getLogger(LoginServlet.class);


	public LoginServlet() {
        super();
    }
 
    // Show Login page.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        // Forward to /WEB-INF/views/loginView.jsp
        // (Users can not access directly into JSP pages placed in WEB-INF)
    	log.info("doGet metod in login servlet is working");
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/index.jsp");
 
        dispatcher.forward(request, response);
 
    }
 
    // When the user enters userName & password, and click Submit.
    // This method will be executed.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	log.info("doPost metod in login servlet is working");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String rememberMeStr = request.getParameter("rememberMe");
        boolean remember = "Y".equals(rememberMeStr);
 
        User user = null;
        boolean hasError = false;
        String errorString = null;
 
        if (login == null || password == null || login.length() == 0 || password.length() == 0) {
            hasError = true;
            errorString = "No date to login";
        	log.info("SQL problems");

        } else {
        	UserController userController=null;
            try {
                // Find the user in the DB.
				userController = new UserController();
                user = userController.getUserByLoginAndPassword(login, password);
            } catch (SQLException e) {
                hasError = true;
                errorString = "Some problems on server";
            	log.error("SQL problems");
            } catch (NoSuchElementException ex) {
                hasError = true;
                errorString = "User Name or password invalid";
            	log.error("Ivalid user login or password");
			}
            finally {
				try {
					userController.returnConnectionInPool();
				} catch (SQLException e) {
	            	log.info("Connection pool not returned");
	            	throw new ServletException();
				}
			}
            
        }
        // If error, forward to /WEB-INF/views/login.jsp
        if (hasError) {
            user = new User();
            user.setLogin(login);
            user.setPassword(password);
 
            // Store information in request attribute, before forward.
            request.setAttribute("errorString", errorString);
            request.setAttribute("user", user);
 
            // Forward to /WEB-INF/views/login.jsp
            RequestDispatcher dispatcher //
                    = this.getServletContext().getRequestDispatcher("/index.jsp");
 
            dispatcher.forward(request, response);
        }
        // If no error
        // Store user information in Session
        // And redirect to userInfo page.
        else {
            HttpSession session = request.getSession();
            MyUtils.storeLoginedUser(session, user);
 
            // If user checked "Remember me".
            if (remember) {
                MyUtils.storeUserCookie(response, user);
            }
            // Else delete cookie.
            else {
                MyUtils.deleteUserCookie(response);
            }
 
            whichRoleUser(request, response, user);
        }
    }
    
    protected void whichRoleUser(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
    	String roleString = user.getRole();
    	if (roleString.equals("doctor")) {
        	log.info("user is doctor");
            response.sendRedirect(request.getContextPath() +  "/patientList" + "?sort=alphabet");
            return;
    	}
    	if (roleString.equals("admin")) {
        	log.info("user is admin");
            response.sendRedirect(request.getContextPath() + "/TherapyServlet");
            return;
    	}
    	if (roleString.equals("nurse")) {
        	log.info("user is nurse");
            response.sendRedirect(request.getContextPath() + "/therapyNurse");
            return;
    	}
    	log.error("user is unknown");
    	throw new ServletException();
 
    }
 
}
