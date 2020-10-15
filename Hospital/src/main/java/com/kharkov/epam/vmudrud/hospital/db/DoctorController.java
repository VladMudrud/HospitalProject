package com.kharkov.epam.vmudrud.hospital.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.db.entity.Category;
import com.kharkov.epam.vmudrud.hospital.db.entity.Doctor;
import com.kharkov.epam.vmudrud.hospital.db.entity.Staff;
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
		PreparedStatement pstm = null;
		try {
			pstm = getPrepareStatement(Query.SELECT_DOCTOR_BY_ID.value());
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				String firstName = rs.getString("first_name");
				String secondName = rs.getString("second_name");
				Date date = rs.getDate("age");
				String gender = rs.getString("gender");
				Integer staffId = rs.getInt("staff_id");
				Integer categoryId = rs.getInt("category_id");
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
			throw new SQLException("Can not execute query");
		} finally {
			closePrepareStatement(pstm);
		}
		log.error("Cann't find the doctor");
		throw new SQLException("Cann't find the doctor");
	}

	@Override
	public boolean delete(Integer id) throws SQLException {
		log.info("this empty");
		return false;
	}

	@Override
	public boolean create(Doctor entity) throws SQLException {
		PreparedStatement ps;
		try {
			ps = getPrepareStatement(Query.INSERT_DOCTOR.value());
			ps.setString(1, entity.getFirstName());
			ps.setString(2, entity.getSecondName());
			ps.setDate(3, entity.getAge());
			ps.setString(4, entity.getGender());
			ps.setInt(5, entity.getStaff().getId());
			ps.setInt(6, entity.getCategory().getId());
			ps.execute();
			return true;
		} catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException("Can not execute query", e);
		}
	}

	public List<Doctor> getAllOrderedWithCount(String sort) throws SQLException {
		PreparedStatement pstm = null;
		List<Doctor> lst = new LinkedList<>();
		sort = doctorSort(sort);
		try {
			pstm = getPrepareStatement(Query.SELECT_ALL_DOCTORS_WITH_COUNT.value() + sort);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				Doctor doctor = new Doctor();
				CategoryController categoryController = new CategoryController(getConnectionPool(), getConnection());
				StaffController staffController = new StaffController(getConnectionPool(), getConnection());

				Integer id = rs.getInt("id");
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
			throw new SQLException("Can not execute query");
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
		} else {
			return " ORDER BY d.first_name";
		}
	}

	public boolean transactionDoctorCreate(Doctor doctor, User user) throws SQLException {
		try {
			getConnection().setAutoCommit(false);
			UserController userController = new UserController(getConnectionPool(), getConnection());
			if (!userController.create(user)) {
				throw new SQLException();
			}
			StaffController staffController = new StaffController(getConnectionPool(), getConnection());
			Staff staff = new Staff();
			Integer userId = getLastInsertId();
			user.setId(userId);
			staff.setUser(user);
			if (!staffController.create(staff)) {
				throw new SQLException();
			}
			Integer staffId = getLastInsertId();
			staff.setId(staffId);
			CategoryController categoryController = new CategoryController(getConnectionPool(), getConnection());
			Category category = categoryController.getEntityByTitle(doctor.getCategory().getTitle());
			DoctorController doctorController = new DoctorController(getConnectionPool(), getConnection());
			doctor.setStaff(staff);
			doctor.setCategory(category);
			if (!doctorController.create(doctor)) {
				throw new SQLException();
			}
			getConnection().commit();
			getConnection().setAutoCommit(true);
		} catch (SQLIntegrityConstraintViolationException e) {
			getConnection().rollback();
			getConnection().setAutoCommit(true);
			log.error("Can not execute transaction, rollback...", e);
			log.error("Dublicate login");	
			throw new SQLIntegrityConstraintViolationException("Dublicate login, please try another one");
		} catch (SQLException e) {
			getConnection().rollback();
			getConnection().setAutoCommit(true);
			log.error("Can not execute transaction, rollback...", e);
			throw new SQLException("Can not execute transaction");
		}
		return true;

	}

	private Integer getLastInsertId() throws SQLException {
		PreparedStatement pstm = null;
		try {
			pstm = getPrepareStatement(Query.SELECT_LAST_ID.value());
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				return rs.getInt("LAST_INSERT_ID()");
			}
		} catch (SQLException e) {
			log.error("Can not execute query", e);
			throw new SQLException("Can not execute query");
		}
		log.error("Cann't find the id");
		throw new SQLException("Cann't find the id");
	}
}
