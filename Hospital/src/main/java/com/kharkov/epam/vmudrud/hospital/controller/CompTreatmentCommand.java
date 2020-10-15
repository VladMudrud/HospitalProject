package com.kharkov.epam.vmudrud.hospital.controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.command.Command;
import com.kharkov.epam.vmudrud.hospital.db.ConnectionPool;
import com.kharkov.epam.vmudrud.hospital.db.StaffController;
import com.kharkov.epam.vmudrud.hospital.db.TherapyController;
import com.kharkov.epam.vmudrud.hospital.db.entity.Staff;
import com.kharkov.epam.vmudrud.hospital.db.entity.Therapy;
import com.kharkov.epam.vmudrud.hospital.db.entity.User;
import com.kharkov.epam.vmudrud.hospital.exception.AppException;
import com.kharkov.epam.vmudrud.hospital.utils.MyUtils;

public class CompTreatmentCommand extends Command {


	private static final long serialVersionUID = -8343920846779789396L;


	private static final Logger log = Logger.getLogger(CompTreatmentCommand.class);

	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws AppException {
        HttpSession session = request.getSession();
        User loginedUser = MyUtils.getLoginedUser(session);
        Integer id = Integer.valueOf(request.getParameter("id"));
        request.setAttribute("user", loginedUser);
        TherapyController therapyController = null;
    	StaffController staffController = null;
        Therapy therapy = new Therapy();
        Staff staff = new Staff();
        try {
        	ConnectionPool connectionPool =ConnectionPool.getInstance();
        	Connection connection = connectionPool.getConnection();
        	therapyController = new TherapyController(connectionPool, connection);
        	staffController = new StaffController(connectionPool, connection);
        	therapy = therapyController.getEntityById(id);
        	staff = staffController.getEntityByUserId(loginedUser.getId());
        	therapyController.done(therapy, staff);
        } catch (SQLException | NamingException e) {
            log.error("Problem with MySql server");
            throw new AppException(e.getMessage());
        } finally {
			try {
				therapyController.returnConnectionInPool();
			} catch (SQLException e) {
	            log.error("Problem with returning connection to the poll");
	            throw new AppException("Problem with returning connection to the poll");
			}
		}
        request.setAttribute("medicalCard", therapy);
        return "/therapy";
	}

}
