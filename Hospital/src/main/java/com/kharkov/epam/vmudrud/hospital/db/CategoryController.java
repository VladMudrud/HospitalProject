package com.kharkov.epam.vmudrud.hospital.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		log.info("this empty");
		return null;
	}

	@Override
	public Category update(Category entity) throws SQLException {
		log.info("this empty");
		return null;
	}

	@Override
	public Category getEntityById(Integer id) throws SQLException {
		PreparedStatement pstm=null;
		try {
			pstm = getPrepareStatement(Query.SELECT_CATEGORY_BY_ID.value());
			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				String title = rs.getString("title");
				Category category = new Category();
				category.setId(id);
				category.setTitle(title);
				return category;
			} 
        } catch (SQLException e) {
			log.error("Can not execute query", e);	
			throw new SQLException("Can not execute query");
		} finally {
			closePrepareStatement(pstm);
		}
		log.error("Cann't find the category");	
		throw new SQLException("Cann't find the category");
	}

	@Override
	public boolean delete(Integer id) throws SQLException {
		log.info("this empty");
		return false;
	}

	@Override
	public boolean create(Category entity) throws SQLException {
		log.info("this empty");
		return false;
	}

}
