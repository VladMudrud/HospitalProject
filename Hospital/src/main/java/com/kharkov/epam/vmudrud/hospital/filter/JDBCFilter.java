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
 
    private boolean needJDBC(HttpServletRequest request) {

        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        String urlPattern = servletPath;
        if (pathInfo != null) {
            urlPattern = servletPath + "/*";
        }
 
        Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext()
                .getServletRegistrations();
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
        if (this.needJDBC(req)) {
 
        	log.info("Open Connection for: "  + req.getServletPath()); 
            Connection conn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
            	conn = connectionPool.getConnection();
                conn.setAutoCommit(false);
                MyUtils.storeConnection(request, conn);
                chain.doFilter(request, response);
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
        else {
            chain.doFilter(request, response);
        }
 
    }
 
}