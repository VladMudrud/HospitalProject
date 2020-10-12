package com.kharkov.epam.vmudrud.hospital.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.db.entity.Staff;

public class StaffController extends AbstractController<Staff, Integer> {
	
	private static final Logger log = Logger.getLogger(StaffController.class);

	public StaffController() throws SQLException {
		super();
	}
	
	public StaffController(ConnectionPool connectionPool, Connection connection) throws SQLException {
		super(connectionPool, connection);
	}

	@Override
	public List<Staff> getAll() throws SQLException {
		log.info("this empty");
		return null;
	}

	@Override
	public Staff update(Staff entity) throws SQLException {
		log.info("this empty");
		return null;
	}

	@Override
	public Staff getEntityById(Integer id) throws SQLException {
		PreparedStatement pstm=null;
		try {
			pstm = getPrepareStatement(Query.SELECT_STAFF_BY_ID.value());
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				Staff staff = new Staff();
				UserController userController = new UserController(getConnectionPool(), getConnection());
				Integer userId = rs.getInt("user_id");
				staff.setId(id);
				staff.setUser(userController.getEntityById(userId));
				return staff;
			} 
        } catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException();
		} finally {
			closePrepareStatement(pstm);
		}
		log.error("Cann't find the patient");	
		throw new NoSuchElementException();
	}

	@Override
	public boolean delete(Integer id) throws SQLException {
		log.info("this empty");
		return false;
	}

	@Override
	public boolean create(Staff entity) throws SQLException {
		log.info("this empty");
		return false;
	}

	public Staff getEntityByUserId(Integer id) throws SQLException {
		PreparedStatement pstm=null;
		try {
			pstm = getPrepareStatement(Query.SELECT_STAFF_BY_USER_ID.value());
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				Staff staff = new Staff();
				UserController userController = new UserController(getConnectionPool(), getConnection());
				Integer userId = rs.getInt("user_id");
				staff.setId(id);
				staff.setUser(userController.getEntityById(userId));
				return staff;
			} 
        } catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException();
		} finally {
			closePrepareStatement(pstm);
		}
		log.error("Cann't find the patient");	
		throw new NoSuchElementException();
	}

}
