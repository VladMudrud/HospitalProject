package com.kharkov.epam.vmudrud.hospital.db.entity;

import java.sql.Date;

public class Doctor extends Person {

	private static final long serialVersionUID = 5954982601443017804L;
	
	private Staff staff;
	
	private Category category;

	
	public Doctor() {}
	
	public Doctor(Integer id, String firstName, String secondName, Date age,  String gender, Staff staff, Category category) {
		this.id=id;
		this.firstName=firstName;
		this.secondName=secondName;
		this.age=age;
		this.gender=gender;
		this.staff=staff;
		this.category=category;

	}
	
	public Doctor(Integer id, String firstName, String secondName, Date age,  String gender, String phone, String address, Staff staff) {
		this.id=id;
		this.firstName=firstName;
		this.secondName=secondName;
		this.age=age;
		this.gender=gender;
		this.staff=staff;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public Staff getStaff() {
		return staff;
	}
	
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	@Override
	public String toString() {
		return "Doctor ["+  "id=" + id  + ", firstName=" + firstName + ", secondName=" + secondName + ", age="  + age + ", gender="+ gender + ", staff=" + staff + ", category=" + category +"]";
	}
	

}
