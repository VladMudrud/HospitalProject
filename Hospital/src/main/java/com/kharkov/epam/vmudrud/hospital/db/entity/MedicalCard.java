package com.kharkov.epam.vmudrud.hospital.db.entity;

public class MedicalCard extends Entity {

	private static final long serialVersionUID = -5025159158228130744L;

	private String diagnosis;

	private Patient patient;

	public MedicalCard() {
	}

	public MedicalCard(Integer id, String diagnosis, Patient patient) {
		this.id = id;
		this.diagnosis = diagnosis;
		this.patient = patient;
	}

	public MedicalCard(Integer id, Patient patient) {
		this.id = id;
		this.patient = patient;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Override
	public String toString() {
		return "MedicalCard [" + "id=" + id + ", diagnosis=" + diagnosis + ", patient=" + patient + "]";
	}

}
