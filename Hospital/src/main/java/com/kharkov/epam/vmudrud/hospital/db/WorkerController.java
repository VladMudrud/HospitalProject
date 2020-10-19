package com.kharkov.epam.vmudrud.hospital.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.db.entity.Worker;

public class WorkerController extends AbstractController<Worker, Integer> {

	private static final Logger log = Logger.getLogger(WorkerController.class);

	public WorkerController(ConnectionPool connectionPool, Connection connection) throws SQLException {
		super(connectionPool, connection);
	}

	public WorkerController() throws SQLException {
		super();
	}

	@Override
	public List<Worker> getAll() throws SQLException {
		log.info("this empty");
		return null;
	}

	@Override
	public Worker update(Worker entity) throws SQLException {
		log.info("this empty");
		return null;
	}

	@Override
	public Worker getEntityById(Integer id) throws SQLException {
		log.info("this empty");
		return null;
	}

	@Override
	public boolean delete(Integer id) throws SQLException {
		log.info("this empty");
		return false;
	}

	@Override
	public boolean create(Worker entity) throws SQLException {
		log.info("this empty");
		return false;
	}

}
