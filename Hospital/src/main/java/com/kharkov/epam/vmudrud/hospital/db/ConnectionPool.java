package com.kharkov.epam.vmudrud.hospital.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.*;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class ConnectionPool {
	
private static final Logger log = Logger.getLogger(ConnectionPool.class);
	
	private static ConnectionPool instance;
	
	public static synchronized ConnectionPool getInstance() {
		if (instance == null)
			instance = new ConnectionPool();
		return instance;
	}
	
	public Connection getConnection() throws NamingException, SQLException{
        Context ctx;
        Connection c = null;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/connPool");
            c = ds.getConnection();
        } catch (NamingException e) {
			log.error("Problem with Context", e);	
			throw new NamingException();
        } catch (SQLException e) {
			log.error("Problem with getConnection", e);	
			throw new SQLException();
        }
        return c;
    }

	

	private ConnectionPool() {}


	public void commit(Connection con) throws SQLException {
		try {
			con.commit();
		} catch (SQLException ex) {
			log.error("Problem with commit", ex);	
			throw new SQLException();
		}
	}

	public void rollback(Connection con) throws SQLException {
		try {
			con.rollback();
		} catch (SQLException ex) {
			log.error("Problem with rollback", ex);	
			throw new SQLException();
		}
	}

	public void returnConnection(Connection con) throws SQLException {
		try {
			con.close();
		} catch (SQLException ex) {
			log.error("Problem with rollback", ex);	
			throw new SQLException();
		}
		
	}
	
	
}
