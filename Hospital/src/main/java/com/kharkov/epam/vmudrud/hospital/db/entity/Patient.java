package com.kharkov.epam.vmudrud.hospital.db.entity;

import java.sql.Date;

public class Patient extends Person {

	private static final long serialVersionUID = -2652401904591000516L;
	
	private Doctor doctor;

	String status;
	
	public Patient() {}
	
	public Patient(Integer id, String firstName, String secondName, Date age,  String gender, String status, Doctor doctor) {
		this.id=id;
		this.firstName=firstName;
		this.secondName=secondName;
		this.age=age;
		this.gender=gender;
		this.status=status;
		this.doctor=doctor;
		

	}
	
	public Patient(Integer id, String firstName, String secondName, Date age,  String gender, String status) {
		this.id=id;
		this.firstName=firstName;
		this.secondName=secondName;
		this.age=age;
		this.gender=gender;
		this.status=status;

	}

	public Doctor getDoctor() {
		return doctor;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	@Override
	public String toString() {
		return "Patient [" + "id=" + id + ", firstName=" + firstName + ", secondName=" + secondName + ", age="  + age + ", gender="+ gender  + ", status="+ status + ",  doctor=" + doctor +"]";
	}
}
