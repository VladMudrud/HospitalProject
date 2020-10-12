package com.kharkov.epam.vmudrud.hospital.db.entity;

import java.util.NoSuchElementException;

import org.apache.log4j.Logger;


public class User extends Entity {

	private static final long serialVersionUID = -6889321625149495388L;
	
	private static final Logger log = Logger.getLogger(User.class);

	private String login;
	
	private String password;
	
	private String role;
	
	public User() {}
	
	public User(Integer id, String login, String password, String role) {
		if (!contains(role)) 
		{
		log.error("Role doesn't exist");
		throw new NoSuchElementException();
		}
		this.id=id;
		this.login=login;
		this.password=password;
		this.setRole(role);
	}
	
	private static boolean contains(String role) {
	    for (Role c : Role.values()) {
	        if (c.value().equals(role)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public String getLogin() {
		return login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "User ["  + "id=" + id  + ", login=" + login + ", password=" + password + ", role=" + role + "]";
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}