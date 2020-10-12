package com.kharkov.epam.vmudrud.hospital.db;

public enum Query {
	
	SELECT_ALL_USERS("SELECT * FROM user"),
	SELECT_ALL_MEDICAL_CARD("SELECT * FROM `medical_card`"),
	SELECT_ALL_MY_MEDICAL_CARD("SELECT mc.id, mc.diagnosis, mc.patient_id  "
			+ "FROM `medical card` mc join patient p on mc.patient_id=p.id join doctor d on p.doctor_id=d.id WHERE d.id=? and p.status!='Discharged from the hospital'"),
	SELECT_ALL_PATIENTS("SELECT * FROM patient WHERE status!='Discharged from the hospital'"),
	SELECT_ALL_THERAPY("SELECT * FROM therapy WHERE status='in progress'"),
	SELECT_ALL_THERAPY_NO_OPERATION("SELECT * FROM therapy WHERE status='in progress' and type!='operation'"),

	UPDATE_MEDICAL_CART("UPDATE `medical card` SET `diagnosis` = ? WHERE (`patient_id` = ?)"),
	UPDATE_PACIENT_STATUS("UPDATE patient SET `status` = ? WHERE (`id` = ?)"),
	SELECT_ALL_MY_PATIENTS("SELECT * FROM patient WHERE doctor_id=? and status!='Discharged from the hospital'"),

	INSERT_USER("INSERT INTO user (login, password, role) VALUES (?, ?, ?)"),
	INSERT_THERAPY("INSERT INTO `hospital`.`therapy` (`title`, `type`, `status`, `medical card_id`) VALUES (?, ?, ?, ?)"),
	SELECT_USER_BY_ID("SELECT * FROM user WHERE id=?"),
	SELECT_MEDICAL_CARD_BY_ID("SELECT mc.id, mc.diagnosis, mc.patient_id FROM `medical card` mc join patient p on mc.patient_id=p.id WHERE p.id=?"),

	SELECT_PATIENT_BY_ID("SELECT * FROM patient WHERE id=?;"),
	SELECT_STAFF_BY_USER_ID("SELECT s.id, s.user_id FROM staff s join `user` u on s.user_id=u.id WHERE u.id=?"),
	SELECT_THERAPY_BY_ID("SELECT * FROM therapy WHERE id=?"),
	SELECT_STAFF_BY_ID("SELECT * FROM staff WHERE id=?"),
	SELECT_USER_BY_LOGIN_AND_PASSWORD("SELECT * FROM `user` WHERE login=? and `password`=?"),
	DONE_THERAPY("UPDATE `hospital`.`therapy` SET `status` = 'done', `staff_id` = ? WHERE (`id` = ?)");
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