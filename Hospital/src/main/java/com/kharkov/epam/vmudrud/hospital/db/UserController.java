package com.kharkov.epam.vmudrud.hospital.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.db.entity.User;

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
			throw new SQLException();
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
	
	public User getUserByLoginAndPassword(String login, String password) throws SQLException {
		PreparedStatement pstm=null;
		try {
			pstm = getPrepareStatement(Query.SELECT_USER_BY_LOGIN_AND_PASSWORD.value());
			pstm.setString(1, login);
			pstm.setNString(2, password);
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
			throw new SQLException();
		} finally {
			closePrepareStatement(pstm);
		}
		log.error("Cann't find the user");	
		throw new NoSuchElementException();
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
	public boolean create(User entity) throws SQLException {
		PreparedStatement ps;
		try {
			ps = getPrepareStatement(Query.INSERT_USER.value());
			ps.setString(1, entity.getLogin());
			ps.setString(2, entity.getPassword());
			ps.setString(3, entity.getRole());
			ps.execute();
			return true;
		} catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException();
		}
	}
	


}