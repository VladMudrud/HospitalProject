package com.kharkov.epam.vmudrud.hospital.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.db.entity.Doctor;
import com.kharkov.epam.vmudrud.hospital.db.entity.User;

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
		PreparedStatement pstm=null;
		try {
			pstm = getPrepareStatement(Query.SELECT_DOCTOR_BY_ID.value());
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				String firstName = rs.getString("first_name");
				String secondName = rs.getString("second_name");
				Date date =rs.getDate("age");
				String gender = rs.getString("gender");
				Integer staffId = rs.getInt("staff_id");
				Integer categoryId= rs.getInt("category_id");
				Doctor doctor = new Doctor();
				StaffController staffController = new StaffController(getConnectionPool(), getConnection());
				CategoryController categoryController = new CategoryController(getConnectionPool(), getConnection());
				doctor.setId(id);
				doctor.setFirstName(firstName);
				doctor.setSecondName(secondName);
				doctor.setAge(date);
				doctor.setGender(gender);
				doctor.setStaff(staffController.getEntityById(staffId));
				doctor.setCategory(categoryController.getEntityById(categoryId));
				return doctor;
			} 
        } catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException();
		} finally {
			closePrepareStatement(pstm);
		}
		log.error("Cann't find the user");	
		throw new NoSuchElementException();
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
