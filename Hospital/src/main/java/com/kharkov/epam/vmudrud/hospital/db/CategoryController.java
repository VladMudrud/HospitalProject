package com.kharkov.epam.vmudrud.hospital.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.kharkov.epam.vmudrud.hospital.db.entity.Category;

public class CategoryController extends AbstractController<Category, Integer> {
	
	private static final Logger log = Logger.getLogger(CategoryController.class);

	public CategoryController() throws SQLException {
		super();
	}
	
	public CategoryController(ConnectionPool connectionPool, Connection connection) throws SQLException {
		super(connectionPool, connection);
	}

	@Override
	public List<Category> getAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category update(Category entity) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category getEntityById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean create(Category entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
