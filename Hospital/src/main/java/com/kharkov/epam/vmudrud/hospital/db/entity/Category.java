package com.kharkov.epam.vmudrud.hospital.db.entity;

public class Category extends Entity {

	private static final long serialVersionUID = 8635612657338977418L;

	
	private String title;

	public Category() {}
	
	public Category(Integer id,String title) {
		this.id=id;
		this.title=title;

	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
		return "Category ["  + "id=" + id  + ", title=" + title  + "]";
	}
		
}
