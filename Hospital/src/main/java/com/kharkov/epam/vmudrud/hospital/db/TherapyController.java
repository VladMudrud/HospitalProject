package com.kharkov.epam.vmudrud.hospital.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.db.entity.Staff;
import com.kharkov.epam.vmudrud.hospital.db.entity.Therapy;

public class TherapyController extends AbstractController<Therapy, Integer> {
	
	private static final Logger log = Logger.getLogger(TherapyController.class);

	public TherapyController(ConnectionPool connectionPool, Connection connection) throws SQLException {
		super(connectionPool, connection);
	}
	public TherapyController() throws SQLException {
		super();
	}
	@Override
	public List<Therapy> getAll() throws SQLException {
		PreparedStatement pstm=null;
        List<Therapy> lst = new LinkedList<>();
		try {
			pstm = getPrepareStatement(Query.SELECT_ALL_THERAPY.value());
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				Therapy therapy = new Therapy();
				MedicalCardController medicalCardController  = new MedicalCardController(getConnectionPool(), getConnection());
				StaffController staffController  = new StaffController(getConnectionPool(), getConnection());

				Integer id =rs.getInt("id");
				String title = rs.getString("title");
				String type = rs.getString("type");
				String status = rs.getString("status");
				Integer medicalCardId = rs.getInt("medical card_id");
				Integer staffId = rs.getInt("staff_id");
				therapy.setId(id);
				therapy.setTitle(title);
				therapy.setType(type);
				therapy.setStatus(status);
				therapy.setMedicalCard(medicalCardController.getEntityById(medicalCardId));
				if (staffId == 0) {
					therapy.setStaff(null);
				} else {
				therapy.setStaff(staffController.getEntityById(staffId)); }
                lst.add(therapy);
			} 
        } catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException("Can not execute query");
		} finally {
			closePrepareStatement(pstm);
		}
        return lst;
	}
	@Override
	public Therapy update(Therapy entity) throws SQLException {
		log.info("this empty");
		return null;
	}
	@Override
	public Therapy getEntityById(Integer id) throws SQLException {
		PreparedStatement pstm=null;
		try {
			pstm = getPrepareStatement(Query.SELECT_THERAPY_BY_ID.value());
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				Therapy therapy = new Therapy();
				MedicalCardController medicalCardController = new MedicalCardController(getConnectionPool(), getConnection());
				StaffController staffController = new StaffController(getConnectionPool(), getConnection());
				String title = rs.getString("title");
				String type = rs.getString("type");
				String status = rs.getString("status");
				Integer medicalCardInteger = rs.getInt("medical card_id");
				Integer staffId = rs.getInt("staff_id");
				therapy.setId(id);
				therapy.setTitle(title);
				therapy.setType(type);
				therapy.setStatus(status);
				therapy.setMedicalCard(medicalCardController.getEntityById(medicalCardInteger));
				if (staffId != 0) {
				therapy.setStaff(staffController.getEntityById(staffId));
				}
				return therapy;
			} 
        } catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException("Can not execute query");
		} finally {
			closePrepareStatement(pstm);
		}
		log.error("Cann't find the therapy");	
		throw new SQLException("Cann't find the therapy");
	}
	@Override
	public boolean delete(Integer id) throws SQLException {
		log.info("this empty");
		return false;
	}
	@Override
	public boolean create(Therapy entity) throws SQLException {
		PreparedStatement ps;
		try {
			ps = getPrepareStatement(Query.INSERT_THERAPY.value());
			ps.setString(1, entity.getTitle());
			ps.setString(2, entity.getType());
			ps.setString(3, entity.getStatus());
			ps.setInt(4, entity.getMedicalCard().getId());
			ps.execute();
			return true;
		} catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException("Can not execute query");
		}
	}
	public void done(Therapy entity, Staff staff) throws SQLException {
		PreparedStatement ps;
		try {
			ps = getPrepareStatement(Query.DONE_THERAPY.value());
			ps.setInt(1, staff.getId());
			ps.setInt(2, entity.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException("Can not execute query");
		}
	}
	public List<Therapy> getAllNoOperation() throws SQLException {
		PreparedStatement pstm=null;
        List<Therapy> lst = new LinkedList<>();
		try {
			pstm = getPrepareStatement(Query.SELECT_ALL_THERAPY_NO_OPERATION.value());
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				Therapy therapy = new Therapy();
				MedicalCardController medicalCardController  = new MedicalCardController(getConnectionPool(), getConnection());
				StaffController staffController  = new StaffController(getConnectionPool(), getConnection());

				Integer id =rs.getInt("id");
				String title = rs.getString("title");
				String type = rs.getString("type");
				String status = rs.getString("status");
				Integer medicalCardId = rs.getInt("medical card_id");
				Integer staffId = rs.getInt("staff_id");
				therapy.setId(id);
				therapy.setTitle(title);
				therapy.setType(type);
				therapy.setStatus(status);
				therapy.setMedicalCard(medicalCardController.getEntityById(medicalCardId));
				if (staffId == 0) {
					therapy.setStaff(null);
				} else {
				therapy.setStaff(staffController.getEntityById(staffId)); }
                lst.add(therapy);
			} 
        } catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException("Can not execute query");
		} finally {
			closePrepareStatement(pstm);
		}
        return lst;
	}
}
