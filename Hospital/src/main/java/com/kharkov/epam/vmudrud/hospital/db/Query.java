package com.kharkov.epam.vmudrud.hospital.db;

public enum Query {

	SELECT_ALL_USERS("SELECT * FROM user"), SELECT_ALL_MEDICAL_CARD("SELECT * FROM `medical_card`"),
	SELECT_ALL_MY_MEDICAL_CARD("SELECT mc.id, mc.diagnosis, mc.patient_id  "
			+ "FROM `medical card` mc join patient p on mc.patient_id=p.id join doctor d on p.doctor_id=d.id WHERE d.id=? and p.status!='Discharged from the hospital'"),
	SELECT_ALL_PATIENT_WITHOUT_DOCTOR_MEDICAL_CARD("SELECT mc.id, mc.diagnosis, mc.patient_id  "
			+ "FROM `medical card` mc join patient p on mc.patient_id=p.id WHERE p.doctor_id is null and p.status!='Discharged from the hospital'"),
	SELECT_ALL_PATIENTS("SELECT * FROM patient WHERE status!='Discharged from the hospital'"),
	SELECT_ALL_THERAPY("SELECT * FROM therapy WHERE status='in progress'"),
	SELECT_ALL_THERAPY_NO_OPERATION("SELECT * FROM therapy WHERE status='in progress' and type!='operation'"),

	UPDATE_MEDICAL_CART("UPDATE `medical card` SET `diagnosis` = ? WHERE (`patient_id` = ?)"),
	UPDATE_PACIENT_STATUS("UPDATE patient SET `status` = ? WHERE (`id` = ?)"),
	UPDATE_PATIENT_DOCTOR_ID("UPDATE `hospital`.`patient` SET `doctor_id` = ? WHERE (`id` = ?)"),
	SELECT_ALL_MY_PATIENTS("SELECT * FROM patient WHERE doctor_id=? and status!='Discharged from the hospital'"),

	INSERT_USER("INSERT INTO user (login, password, role) VALUES (?, ?, ?)"),
	INSERT_THERAPY(
			"INSERT INTO `hospital`.`therapy` (`title`, `type`, `status`, `medical card_id`) VALUES (?, ?, ?, ?)"),
	SELECT_USER_BY_ID("SELECT * FROM user WHERE id=?"),
	SELECT_CATEGORY_BY_TITLE("SELECT id FROM category WHERE title=?"),

	SELECT_CATEGORY_BY_ID("SELECT * FROM category WHERE id=?"),
	SELECT_ALL_DOCTORS_WITH_COUNT(
			"SELECT d.id, d.first_name, d.second_name, d.age, d.gender, d.staff_id, d.category_id, (SELECT count(id) FROM patient WHERE patient.doctor_id=d.id) as NumberOfPatiens FROM doctor d join category c on d.category_id=c.id"),
	SELECT_DOCTOR_BY_ID("SELECT * FROM doctor WHERE id=?"),
	SELECT_MEDICAL_CARD_BY_ID(
			"SELECT mc.id, mc.diagnosis, mc.patient_id FROM `medical card` mc join patient p on mc.patient_id=p.id WHERE mc.id=?"),
	INSERT_PATIENT(
			"INSERT INTO `hospital`.`patient` (`first_name`, `second_name`, `age`, `gender`, `status`) VALUES (?, ?, ?, ?, ?)"),
	SELECT_PATIENT_BY_ID("SELECT * FROM patient WHERE id=?;"),
	SELECT_STAFF_BY_USER_ID("SELECT s.id, s.user_id FROM staff s join `user` u on s.user_id=u.id WHERE u.id=?"),
	SELECT_THERAPY_BY_ID("SELECT * FROM therapy WHERE id=?"), SELECT_STAFF_BY_ID("SELECT * FROM staff WHERE id=?"),
	SELECT_USER_BY_LOGIN_AND_PASSWORD("SELECT * FROM `user` WHERE login=? and `password`=?"),
	DONE_THERAPY("UPDATE `hospital`.`therapy` SET `status` = 'done', `staff_id` = ? WHERE (`id` = ?)"),
	SELECT_MEDICAL_CARD_BY_PATIENT_ID(
			"SELECT mc.id, mc.diagnosis, mc.patient_id FROM `medical card` mc join patient p on mc.patient_id=p.id WHERE p.id=?"),
	SELECT_LAST_ID("SELECT LAST_INSERT_ID()"),
	INSERT_MEDICAL_CARD("INSERT INTO `hospital`.`medical card` (`patient_id`) VALUES (?);"),
	INSERT_STAFF("INSERT INTO `hospital`.`staff` (`user_id`) VALUES (?)"), INSERT_DOCTOR(
			"INSERT INTO `hospital`.`doctor` (`first_name`, `second_name`, `age`, `gender`, `staff_id`, `category_id`) VALUES (?, ?, ?, ?, ?, ?)"),
	SELECT_DOCTOR_ID_BY_USER_ID("Select d.id FROM doctor d join staff s on d.staff_id=s.id join `user` u on s.user_id=u.id WHERE u.id=?" ),
	SELECT_COUNT_OF_OPERATIONS("SELECT count(id) as count FROM therapy WHERE `type` = 'operation' and `status` = 'in progress'" );

	private String value;

	Query(String value) {
		this.value = value;
	}

	public boolean equalsTo(String name) {
		return value.equals(name);
	}

	public String value() {
		return value;
	}

}
