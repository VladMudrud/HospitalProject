package com.kharkov.epam.vmudrud.hospital.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.db.UserController;
import com.kharkov.epam.vmudrud.hospital.db.entity.User;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;

@WebFilter(filterName = "cookieFilter", urlPatterns = { "/*" })
public class CookieFilter implements Filter {

	private static final Logger log = Logger.getLogger(CookieFilter.class);

	public CookieFilter() {
		log.info("Cookie filter constructor initialize");
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		log.info("Filter init");
	}

	@Override
	public void destroy() {
		log.info("Filter destroy");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		User userInSession = MyUtils.getLoginedUser(session);
		if (userInSession != null) {
			session.setAttribute("COOKIE_CHECKED", "CHECKED");
			chain.doFilter(request, response);
			return;
		}
		Connection conn = MyUtils.getStoredConnection(request);
		String checked = (String) session.getAttribute("COOKIE_CHECKED");
		if (checked == null && conn != null) {
			String userId;
			try {
				userId = MyUtils.getUserIdInCookie(req);
			} catch (ServletException e) {
				log.info("No id in cookie");
				userId = null;
			}
			UserController userController = null;
			try {
				userController = new UserController();
				User user = userController.getEntityById(Integer.parseInt(userId));
				MyUtils.storeLoginedUser(session, user);
			} catch (SQLException | NumberFormatException e) {
				log.info("Problems with cookie or no cookie");
			} finally {
				try {
					userController.returnConnectionInPool();
				} catch (SQLException e) {
					log.info("Connection pool not returned");
					throw new ServletException();
				}
			}
			session.setAttribute("COOKIE_CHECKED", "CHECKED");
		}
		chain.doFilter(request, response);
	}

}
