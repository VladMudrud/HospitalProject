package com.kharkov.epam.vmudrud.hospital.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.log4j.Logger;

 
@WebFilter(filterName = "encodingFilter", urlPatterns = { "/*" })
public class EncodingFilter implements Filter {
 
  private static final Logger log = Logger.getLogger(EncodingFilter.class);

	
  public EncodingFilter() {      
	  log.info("Encoding filter constructor initialize");
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
      request.setCharacterEncoding("UTF-8");
      response.setCharacterEncoding("UTF-8");
      log.info("Character encoding seting");
      chain.doFilter(request, response);
  }
 
}
