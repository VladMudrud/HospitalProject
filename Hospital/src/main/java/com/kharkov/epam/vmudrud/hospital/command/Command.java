package com.kharkov.epam.vmudrud.hospital.command;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class Command implements Serializable {	
	private static final long serialVersionUID = 8879403039606311780L;

	public abstract String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException;
	
	
	@Override
	public final String toString() {
		return getClass().getSimpleName();
	}

}