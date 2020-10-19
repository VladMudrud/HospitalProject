package com.kharkov.epam.vmudrud.hospital.db.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable {

	private static final long serialVersionUID = 846625610086236L;

	protected Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String toString() {
		return "Person [id=" + id + "]";
	}
}
