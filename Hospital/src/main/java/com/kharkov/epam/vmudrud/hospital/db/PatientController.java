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

import com.kharkov.epam.vmudrud.hospital.db.entity.Patient;

public class PatientController extends AbstractController<Patient, Integer> {
	
	private static final Logger log = Logger.getLogger(PatientController.class);

	public PatientController() throws SQLException {
		super();
	}
	
	public PatientController(ConnectionPool connectionPool, Connection connection) throws SQLException {
		super(connectionPool, connection);
	}
	

	@Override
	public List<Patient> getAll() throws SQLException {
		PreparedStatement pstm=null;
        List<Patient> lst = new LinkedList<>();
		try {
			pstm = getPrepareStatement(Query.SELECT_ALL_PATIENTS.value());
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				Patient patient = new Patient();
				DoctorController doctorController = new DoctorController(getConnectionPool(), getConnection());
				Integer id =rs.getInt("id");
				String firstName = rs.getString("first_name");
				String secondName = rs.getString("second_name");
				Date date = rs.getDate("age");
				String gender = rs.getString("gender");
				String status = rs.getString("status");
				Integer doctorIdInteger = rs.getInt("doctor_id");
				patient.setId(id);
				patient.setFirstName(firstName);
				patient.setSecondName(secondName);
				patient.setAge(date);
				patient.setGender(gender);
				patient.setStatus(status);
				patient.setDoctor(doctorController.getEntityById(doctorIdInteger));
                lst.add(patient);
			} 
        } catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException();
		} finally {
			closePrepareStatement(pstm);
		}
        return lst;
	}

	@Override
	public Patient update(Patient entity) throws SQLException {
		log.info("this empty");
		return null;
	}

	@Override
	public Patient getEntityById(Integer id) throws SQLException {
		PreparedStatement pstm=null;
		try {
			pstm = getPrepareStatement(Query.SELECT_PATIENT_BY_ID.value());
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				Patient patient = new Patient();
				DoctorController doctorController = new DoctorController(getConnectionPool(), getConnection());
				String firstName = rs.getString("first_name");
				String secondName = rs.getString("second_name");
				Date date = rs.getDate("age");
				String gender = rs.getString("gender");
				String status = rs.getString("status");
				Integer doctorIdInteger = rs.getInt("doctor_id");
				patient.setId(id);
				patient.setFirstName(firstName);
				patient.setSecondName(secondName);
				patient.setAge(date);
				patient.setGender(gender);
				patient.setStatus(status);
				patient.setDoctor(doctorController.getEntityById(doctorIdInteger));
				return patient;
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
	public boolean create(Patient entity) throws SQLException {
		log.info("this empty");
		return false;
	}

	public List<Patient> getAllMyPatients(Integer id) throws SQLException {
		PreparedStatement pstm=null;
        List<Patient> lst = new LinkedList<>();
		try {
			pstm = getPrepareStatement(Query.SELECT_ALL_MY_PATIENTS.value());
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				Patient patient = new Patient();
				DoctorController doctorController = new DoctorController(getConnectionPool(), getConnection());
				Integer idPatient =rs.getInt("id");
				String firstName = rs.getString("first_name");
				String secondName = rs.getString("second_name");
				Date date = rs.getDate("age");
				String gender = rs.getString("gender");
				String status = rs.getString("status");
				Integer doctorIdInteger = rs.getInt("doctor_id");
				patient.setId(idPatient);
				patient.setFirstName(firstName);
				patient.setSecondName(secondName);
				patient.setAge(date);
				patient.setGender(gender);
				patient.setStatus(status);
				patient.setDoctor(doctorController.getEntityById(doctorIdInteger));
                lst.add(patient);
			} 
        } catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException();
		} finally {
			closePrepareStatement(pstm);
		}
        return lst;
	}

	public Patient updateStatus(Patient entity) throws SQLException {
		PreparedStatement ps;
		try {
			ps = getPrepareStatement(Query.UPDATE_PACIENT_STATUS.value());
			ps.setString(1, entity.getStatus());
			ps.setInt(2, entity.getId());
			ps.execute();
			return entity;
		} catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException();
		}
	}

	public List<Patient> getAllOrdered(String sort) throws SQLException {
		PreparedStatement pstm=null;
        List<Patient> lst = new LinkedList<>();
        sort=patientSort(sort);
		try {
			pstm = getPrepareStatement(Query.SELECT_ALL_PATIENTS.value() + sort);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				Patient patient = new Patient();
				DoctorController doctorController = new DoctorController(getConnectionPool(), getConnection());
				Integer id =rs.getInt("id");
				String firstName = rs.getString("first_name");
				String secondName = rs.getString("second_name");
				Date date = rs.getDate("age");
				String gender = rs.getString("gender");
				String status = rs.getString("status");
				Integer doctorIdInteger = rs.getInt("doctor_id");
				patient.setId(id);
				patient.setFirstName(firstName);
				patient.setSecondName(secondName);
				patient.setAge(date);
				patient.setGender(gender);
				patient.setStatus(status);
				if (doctorIdInteger != 0) {
					patient.setDoctor(doctorController.getEntityById(doctorIdInteger));
				} else {
					patient.setDoctor(null);
				}
                lst.add(patient);
			} 
        } catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException();
		} finally {
			closePrepareStatement(pstm);
		}
        return lst;
	}

	private String patientSort(String sort)
	{		
		if (sort.equals("alphabet")) {
			return "ORDER BY first_name";
		}
		if (sort.equals("date")) {
			return "ORDER BY age";
		} else {
			return "ORDER BY first_name";
		}
	}

}
