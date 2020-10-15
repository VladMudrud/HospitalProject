package com.kharkov.epam.vmudrud.hospital.utils;


import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.db.entity.User;
  
public class MyUtils {
	
	private MyUtils() {}
	
	private static final Logger log = Logger.getLogger(MyUtils.class);

    public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";
 
    private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";
 
    public static void storeConnection(ServletRequest request, Connection conn) {
        request.setAttribute(ATT_NAME_CONNECTION, conn);
    }
 
    public static Connection getStoredConnection(ServletRequest request) {
        return (Connection) request.getAttribute(ATT_NAME_CONNECTION);
    }
 
    public static void storeLoginedUser(HttpSession session, User loginedUser) {
        session.setAttribute("loginedUser", loginedUser);
    }
 
    public static User getLoginedUser(HttpSession session) {
    	return (User) session.getAttribute("loginedUser");
    }
 
    public static void storeUserCookie(HttpServletResponse response, User user) {
		log.info("Store user cookie");	
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, user.getId().toString());
        cookieUserName.setMaxAge(24 * 60 * 60);
        response.addCookie(cookieUserName);
    }
 
    public static String getUserIdInCookie(HttpServletRequest request) throws ServletException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ATT_NAME_USER_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
		log.info("No cookie");	
        throw new ServletException("Cookie is null");

    }
    public static void deleteUserCookie(HttpServletResponse response) {
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);
        cookieUserName.setMaxAge(0);
        response.addCookie(cookieUserName);
		log.info("Cookies deleted");	

    }
    
    
 
}