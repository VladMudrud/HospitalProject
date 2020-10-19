package com.kharkov.epam.vmudrud.hospital.db.entity;

public class Therapy extends Entity {

	private static final long serialVersionUID = 8608317432976022426L;

	private String title;

	private String type;

	private String status;

	private MedicalCard medicalCard;

	private Staff staff;

	public Therapy() {
	}

	public Therapy(Integer id, String title, String type, String status, MedicalCard medicalCard, Staff staff) {
		this.id = id;
		this.title = title;
		this.type = type;
		this.status = status;
		this.medicalCard = medicalCard;
		this.staff = staff;

	}

	public Therapy(Integer id, String title, String type, String status, MedicalCard medicalCard) {
		this.id = id;
		this.title = title;
		this.type = type;
		this.status = status;
		this.medicalCard = medicalCard;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MedicalCard getMedicalCard() {
		return medicalCard;
	}

	public void setMedicalCard(MedicalCard medicalCard) {
		this.medicalCard = medicalCard;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Therapy [" + "id=" + id + ", title=" + title + ", type=" + type + ", status=" + status
				+ ", medicalCard=" + medicalCard + ", staff=" + staff + "]";
	}

}
