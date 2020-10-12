package com.kharkov.epam.vmudrud.hospital.db.entity;

public enum Role {
	
	ADMIN("admin"),
	DOCTOR("doctor"),
	NURSE("nurse");
	
	private String value;

	Role(String value) {
		this.value = value;
	}
	

	public boolean equalsTo(String name) {
		return value.equals(name);
	}

	public String value() {
		return value;
	}
}
