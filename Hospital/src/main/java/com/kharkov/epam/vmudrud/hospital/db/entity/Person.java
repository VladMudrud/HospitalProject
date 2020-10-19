package com.kharkov.epam.vmudrud.hospital.db.entity;

import java.sql.Date;

public class Person extends Entity {

	private static final long serialVersionUID = 6323921691656439659L;

	protected String firstName;

	protected String secondName;

	protected Date age;

	protected String gender;

	public Person() {
	}

	public Person(String firstName, String secondName, Date age, String gender) {
		this.firstName = firstName;
		this.secondName = secondName;
		this.age = age;
		this.gender = gender;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public void setAge(Date age) {
		this.age = age;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public String getGender() {
		return gender;
	}

	public Date getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", secondName=" + secondName + ", age=" + age + ", gender=" + gender
				+ "]";
	}

}
