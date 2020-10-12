package com.kharkov.epam.vmudrud.hospital.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.db.ConnectionPool;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;

 
 
@WebFilter(filterName = "jdbcFilter", urlPatterns = { "/*" })
public class JDBCFilter implements Filter {
 
	private static final Logger log = Logger.getLogger(JDBCFilter.class);
	
    public JDBCFilter() {
  	  log.info("JDBC filter constructor initialize");

    }
 
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    	log.info("Filter init");
    }
 
    @Override
    public void destroy() {
    	log.info("Filter destroy");
    }
 
    // Check the target of the request is a servlet?
    private boolean needJDBC(HttpServletRequest request) {
        // Servlet Url-pattern: /spath/*
        // 
        // => /spath
        String servletPath = request.getServletPath();
        // => /abc/mnp
        String pathInfo = request.getPathInfo();
 
        String urlPattern = servletPath;
 
        if (pathInfo != null) {
            // => /spath/*
            urlPattern = servletPath + "/*";
        }
 
        // Key: servletName.
        // Value: ServletRegistration
        Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext()
                .getServletRegistrations();
 
        // Collection of all servlet.
        Collection<? extends ServletRegistration> values = servletRegistrations.values();
        for (ServletRegistration sr : values) {
            Collection<String> mappings = sr.getMappings();
            if (mappings.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
 
        HttpServletRequest req = (HttpServletRequest) request;
 
        // Only open connections for the special requests.
        // (For example, the path to the servlet, JSP, ..)
        // 
        // Avoid open connection for commons request.
        // (For example: image, css, javascript,... )
        // 
        if (this.needJDBC(req)) {
 
        	log.info("Open Connection for: "  + req.getServletPath()); 
            Connection conn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                // Create a Connection.
            	conn = connectionPool.getConnection();
                // Set outo commit to false.
                conn.setAutoCommit(false);
 
                // Store Connection object in attribute of request.
                MyUtils.storeConnection(request, conn);
 
                // Allow request to go forward
                // (Go to the next filter or target)
                chain.doFilter(request, response);
 
                // Invoke the commit() method to complete the transaction with the DB.
                connectionPool.commit(conn);
            } catch (SQLException | NamingException e) {
            	log.error("Problem with filter");
                try {
					connectionPool.rollback(conn);
	            	throw new ServletException();
				} catch (SQLException e1) {
	            	log.error("Cann't rollback");
	            	throw new ServletException();
				}
            } finally {
            	try {
					connectionPool.returnConnection(conn);
				} catch (SQLException e) {
	            	log.error("Cann't return connection");
	            	throw new ServletException();
				}
            }
        }
        // With commons requests (images, css, html, ..)
        // No need to open the connection.
        else {
            // Allow request to go forward
            // (Go to the next filter or target)
            chain.doFilter(request, response);
        }
 
    }
 
}