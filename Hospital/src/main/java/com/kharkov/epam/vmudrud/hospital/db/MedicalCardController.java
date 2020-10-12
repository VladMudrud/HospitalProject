package com.kharkov.epam.vmudrud.hospital.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.db.entity.MedicalCard;

public class MedicalCardController extends AbstractController<MedicalCard, Integer> {
	
	private static final Logger log = Logger.getLogger(MedicalCardController.class);

	public MedicalCardController(ConnectionPool connectionPool, Connection connection) throws SQLException {
		super(connectionPool, connection);
	}
	public MedicalCardController() throws SQLException {
		super();
	}
	@Override
	public List<MedicalCard> getAll() throws SQLException {
		PreparedStatement pstm=null;
        List<MedicalCard> lst = new LinkedList<>();
		try {
			pstm = getPrepareStatement(Query.SELECT_ALL_MEDICAL_CARD.value());
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				MedicalCard medicalCard = new MedicalCard();
				PatientController patientController = new PatientController(getConnectionPool(), getConnection());
				Integer id =rs.getInt("id");
				String diagnosis = rs.getString("diagnosis");
				Integer patientId = rs.getInt("patient_id");
				medicalCard.setId(id);
				medicalCard.setDiagnosis(diagnosis);
				medicalCard.setPatient(patientController.getEntityById(patientId));
                lst.add(medicalCard);
			} 
        } catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException();
		} finally {
			closePrepareStatement(pstm);
		}
        return lst;
	}
	public List<MedicalCard> getAllMyMedicalCard(Integer id) throws SQLException {
		PreparedStatement pstm=null;
        List<MedicalCard> lst = new LinkedList<>();
		try {
			pstm = getPrepareStatement(Query.SELECT_ALL_MY_MEDICAL_CARD.value());
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				MedicalCard medicalCard = new MedicalCard();
				PatientController patientController = new PatientController(getConnectionPool(), getConnection());
				Integer idMedicalCard =rs.getInt("id");
				String diagnosis = rs.getString("diagnosis");
				Integer patientId = rs.getInt("patient_id");
				medicalCard.setId(idMedicalCard);
				medicalCard.setDiagnosis(diagnosis);
				medicalCard.setPatient(patientController.getEntityById(patientId));
                lst.add(medicalCard);
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
	public MedicalCard update(MedicalCard entity) throws SQLException {
		PreparedStatement pstm=null;
		try {
			pstm = getPrepareStatement(Query.UPDATE_MEDICAL_CART.value());
			pstm.setString(1, entity.getDiagnosis());
			pstm.setInt(2, entity.getPatient().getId());
			pstm.executeUpdate();
        } catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException();
		} finally {
			closePrepareStatement(pstm);
		}
		return entity;
	}
	@Override
	public MedicalCard getEntityById(Integer id) throws SQLException {
		PreparedStatement pstm=null;
		try {
			pstm = getPrepareStatement(Query.SELECT_MEDICAL_CARD_BY_ID.value());
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				MedicalCard medicalCard = new MedicalCard();
				PatientController patientController = new PatientController(getConnectionPool(), getConnection());
				String diagnosis = rs.getString("diagnosis");
				Integer patient_id = rs.getInt("patient_id");
				medicalCard.setId(id);
				medicalCard.setDiagnosis(diagnosis);
				medicalCard.setPatient(patientController.getEntityById(patient_id));
				return medicalCard;
			} 
        } catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException();
		} finally {
			closePrepareStatement(pstm);
		}
		log.error("Cann't find the medical card");	
		throw new NoSuchElementException();
	}
	@Override
	public boolean delete(Integer id) throws SQLException {
		return false;
	}
	@Override
	public boolean create(MedicalCard entity) throws SQLException {
		return false;
	}
	public List<MedicalCard> getAllMyMedicalCardSorted(Integer id, String sort) throws SQLException {
		PreparedStatement pstm=null;
        List<MedicalCard> lst = new LinkedList<>();
        sort=patientSort(sort);
		try {
			pstm = getPrepareStatement(Query.SELECT_ALL_MY_MEDICAL_CARD.value() +sort);
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				MedicalCard medicalCard = new MedicalCard();
				PatientController patientController = new PatientController(getConnectionPool(), getConnection());
				Integer idMedicalCard =rs.getInt("id");
				String diagnosis = rs.getString("diagnosis");
				Integer patientId = rs.getInt("patient_id");
				medicalCard.setId(idMedicalCard);
				medicalCard.setDiagnosis(diagnosis);
				medicalCard.setPatient(patientController.getEntityById(patientId));
                lst.add(medicalCard);
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
			return "ORDER BY p.first_name";
		}
		if (sort.equals("date")) {
			return "ORDER BY p.age";
		} else {
			return "ORDER BY p.first_name";
		}
	}
}