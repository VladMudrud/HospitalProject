package com.kharkov.epam.vmudrud.hospital.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.db.entity.Doctor;

public class DoctorController extends AbstractController<Doctor, Integer> {

	private static final Logger log = Logger.getLogger(DoctorController.class);

	
	public DoctorController(ConnectionPool connectionPool, Connection connection) throws SQLException {
		super(connectionPool, connection);
	}
	
	public DoctorController() throws SQLException {
		super();
	}

	@Override
	public List<Doctor> getAll() throws SQLException {
		log.info("this empty");
		return null;
	}

	@Override
	public Doctor update(Doctor entity) throws SQLException {
		log.info("this empty");
		return null;
	}

	@Override
	public Doctor getEntityById(Integer id) throws SQLException {
		log.info("this empty");
		return null;
	}

	@Override
	public boolean delete(Integer id) throws SQLException {
		log.info("this empty");
		return false;
	}

	@Override
	public boolean create(Doctor entity) throws SQLException {
		log.info("this empty");
		return false;
	}

}
