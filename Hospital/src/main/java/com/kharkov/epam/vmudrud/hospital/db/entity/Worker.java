package com.kharkov.epam.vmudrud.hospital.db.entity;

import java.sql.Date;

public class Worker extends Person {

	private static final long serialVersionUID = 2271748994800824236L;

	private Staff staff;
	
	public Worker() {}
	
	public Worker(Integer id, String firstName, String secondName, Date age,  String gender, Staff staff) {
		this.id=id;
		this.firstName=firstName;
		this.secondName=secondName;
		this.age=age;
		this.gender=gender;
		this.staff=staff;
	}
	
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	
	public Staff getStaff() {
		return staff;
	}
	
	@Override
	public String toString() {
		return "Worker [" + "id=" + id + ", firstName=" + firstName + ", secondName=" + secondName + ", age="  + age + ", gender="+ gender + ", staff=" + staff +"]";
	}
	
	
}
