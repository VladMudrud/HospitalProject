package com.kharkov.epam.vmudrud.hospital.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

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

	public List<Doctor> getAllOrderedWithCount(String sort) throws SQLException {
		PreparedStatement pstm=null;
        List<Doctor> lst = new LinkedList<>();
        sort=doctorSort(sort);
		try {
			pstm = getPrepareStatement(Query.SELECT_ALL_DOCTORS_WITH_COUNT.value() + sort);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				Doctor doctor = new Doctor();
				CategoryController categoryController  = new CategoryController(getConnectionPool(), getConnection());
				StaffController staffController  = new StaffController(getConnectionPool(), getConnection());

				Integer id =rs.getInt("id");
				String firstName = rs.getString("first_name");
				String secondName = rs.getString("second_name");
				Date date = rs.getDate("age");
				String gender = rs.getString("gender");
				Integer staffId = rs.getInt("staff_id");
				Integer categoryId = rs.getInt("category_id");
				Integer count = rs.getInt("NumberOfPatiens");
				doctor.setId(id);
				doctor.setFirstName(firstName);
				doctor.setSecondName(secondName);
				doctor.setAge(date);
				doctor.setGender(gender);
				doctor.setStaff(staffController.getEntityById(staffId));
				doctor.setCategory(categoryController.getEntityById(categoryId));
				doctor.setNumberOfPatients(count);
                lst.add(doctor);
			} 
        } catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException();
		} finally {
			closePrepareStatement(pstm);
		}
        return lst;
	}

	private String doctorSort(String sort) {
		if (sort.equals("alphabet")) {
			return " ORDER BY d.first_name";
		}
		if (sort.equals("category")) {
			return " ORDER BY c.title";
		}
		if (sort.equals("patientCount")) {
			return " ORDER BY NumberOfPatiens";
		}
		else {
			return " ORDER BY d.first_name";
		}
	}

}
