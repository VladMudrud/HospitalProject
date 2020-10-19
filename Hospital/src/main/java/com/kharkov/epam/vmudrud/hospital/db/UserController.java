package com.kharkov.epam.vmudrud.hospital.db;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.db.entity.User;
import com.kharkov.epam.vmudrud.hospital.exception.AppException;
import com.kharkov.epam.vmudrud.hospital.utils.PasswordHash;

public class UserController extends AbstractController<User, Integer> {
	
	private static final Logger log = Logger.getLogger(UserController.class);


    public UserController() throws SQLException {
		super();
	}
    
    public UserController(ConnectionPool connectionPool, Connection connection) throws SQLException {
		super(connectionPool, connection);
	}
    
	@Override
    public List<User> getAll() throws SQLException {
        List<User> lst = new LinkedList<>();
        PreparedStatement ps = null;
        try {
            ps = getPrepareStatement(Query.SELECT_ALL_USERS.value());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) { 
                User user = new User();
                user.setId(rs.getInt(1));
                user.setLogin(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setRole(rs.getString(4));
                lst.add(user);
            }
        } catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException("Can not execute query");
        } finally {
			closePrepareStatement(ps);
        }
        return lst;
    }

	@Override
	public User update(User entity) throws SQLException {
		log.info("this empty");
		return null;
	}
	
	public User getUserByLoginAndPassword(String login, String password) throws SQLException, AppException {
		PreparedStatement pstm=null;
		try {
			pstm = getPrepareStatement(Query.SELECT_USER_BY_LOGIN_AND_PASSWORD.value());
			pstm.setString(1, login);
			try {
				password=PasswordHash.Hash(password);
			} catch (NoSuchAlgorithmException e) {
				log.error("Problem with hash function");	
				throw new AppException("Problem with hash function");
			}
			pstm.setString(2, password);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				Integer idReal = rs.getInt("id");
				String loginReal = rs.getString("login");
				String passwordReal = rs.getString("password");
				String roleReal = rs.getString("role");
				User user = new User();
				user.setId(idReal);
				user.setLogin(loginReal);
				user.setPassword(passwordReal);
				user.setRole(roleReal);
				return user;
			} 
        } catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException("Can not execute query");
		} finally {
			closePrepareStatement(pstm);
		}
		log.error("Cann't find the user");	
		throw new AppException("Cann't find the user");
	}


	@Override
	public User getEntityById(Integer id) throws SQLException {
		PreparedStatement pstm=null;
		try {
			pstm = getPrepareStatement(Query.SELECT_USER_BY_ID.value());
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				String login = rs.getString("login");
				String password = rs.getString("password");
				String role = rs.getString("role");
				User user = new User();
				user.setId(id);
				user.setLogin(login);
				user.setPassword(password);
				user.setRole(role);

				return user;
			} 
        } catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException("Can not execute query");
		} finally {
			closePrepareStatement(pstm);
		}
		log.error("Cann't find the user");	
		throw new SQLException("Cann't find the user");
	}

	@Override
	public boolean delete(Integer id) throws SQLException {
		log.info("this empty");
		return false;
	}

	@Override
	public boolean create(User entity) throws SQLException {
		PreparedStatement ps;
		try {
			ps = getPrepareStatement(Query.INSERT_USER.value());
			ps.setString(1, entity.getLogin());
			try {
				entity.setPassword(PasswordHash.Hash(entity.getPassword()));
			} catch (NoSuchAlgorithmException e) {
				log.error("Problem with hash function");	
				throw new SQLException("Problem with hash function");
			}
			ps.setString(2, entity.getPassword());
			ps.setString(3, entity.getRole());
			ps.execute();
			return true;
		} catch (SQLIntegrityConstraintViolationException e) {
			log.error("Dublicate login", e);	
			throw new SQLIntegrityConstraintViolationException("Dublicate login, please try another one");
		} catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException("Can not execute query", e);
		} 
		
	}
	


}