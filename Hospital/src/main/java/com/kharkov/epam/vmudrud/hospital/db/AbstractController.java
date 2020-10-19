package com.kharkov.epam.vmudrud.hospital.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

public abstract class AbstractController<E, K> {

	private static final Logger log = Logger.getLogger(AbstractController.class);

	private Connection connection;
	private ConnectionPool connectionPool;

	public AbstractController() throws SQLException {
		connectionPool = ConnectionPool.getInstance();
		try {
			connection = connectionPool.getConnection();
		} catch (NamingException | SQLException e) {
			log.error("Something wrong with Connection", e);
			throw new SQLException();
		}
	}

	public AbstractController(ConnectionPool connectionPool, Connection connection) throws SQLException {
		this.connectionPool = connectionPool;
		this.connection = connection;
	}

	public Connection getConnection() {
		return connection;
	}

	public ConnectionPool getConnectionPool() {
		return connectionPool;
	}

	public abstract List<E> getAll() throws SQLException;

	public abstract E update(E entity) throws SQLException;

	public abstract E getEntityById(K id) throws SQLException;

	public abstract boolean delete(K id) throws SQLException;

	public abstract boolean create(E entity) throws SQLException;

	public void returnConnectionInPool() throws SQLException {
		connectionPool.returnConnection(connection);
	}

	public PreparedStatement getPrepareStatement(String sql) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
		} catch (SQLException e) {
			log.error("Can not prepapre statement", e);
			throw new SQLException();
		}

		return ps;
	}

	public void closePrepareStatement(PreparedStatement ps) throws SQLException {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				log.error("Can not close prepapre statement", e);
				throw new SQLException();
			}
		}
	}
}
