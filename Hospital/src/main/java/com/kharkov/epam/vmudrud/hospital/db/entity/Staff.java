package com.kharkov.epam.vmudrud.hospital.db.entity;

public class Staff extends Entity {

	private static final long serialVersionUID = 6692635249276206035L;

	private User user;

	public Staff() {
	}

	public Staff(Integer id, User user) {
		this.id = id;
		this.user = user;

	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "Staff [" + "id=" + id + ", user=" + user + "]";
	}

}
