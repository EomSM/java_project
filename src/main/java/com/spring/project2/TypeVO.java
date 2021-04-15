package com.spring.project2;
public class TypeVO {
	private String tid;
	private int drinking;
	private int smoking;
	private int religion;
	private int height;
	private int place;
	private int contact;
	private int wage;
	
	
	
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public int getDrinking() {
		return drinking;
	}
	public void setDrinking(int drinking) {
		this.drinking = drinking;
	}
	public int getSmoking() {
		return smoking;
	}
	public void setSmoking(int smoking) {
		this.smoking = smoking;
	}
	public int getReligion() {
		return religion;
	}
	public void setReligion(int religion) {
		this.religion = religion;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getPlace() {
		return place;
	}
	public void setPlace(int place) {
		this.place = place;
	}
	public int getContact() {
		return contact;
	}
	public void setContact(int contact) {
		this.contact = contact;
	}
	public int getWage() {
		return wage;
	}
	public void setWage(int wage) {
		this.wage = wage;
	}
	@Override
	public String toString() {
		return "TypeVO [tid=" + tid + ", drinking=" + drinking + ", smoking=" + smoking + ", religion=" + religion
				+ ", height=" + height + ", place=" + place + ", contact=" + contact + ", wage=" + wage + "]";
	}
}
