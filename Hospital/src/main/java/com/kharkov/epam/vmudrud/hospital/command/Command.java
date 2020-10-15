package com.kharkov.epam.vmudrud.hospital.command;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kharkov.epam.vmudrud.hospital.exception.AppException;


public abstract class Command implements Serializable {	
	private static final long serialVersionUID = 8879403039606311780L;

	public abstract String execute(HttpServletRequest request, HttpServletResponse response)
			throws AppException;
	
	
	@Override
	public final String toString() {
		return getClass().getSimpleName();
	}

}