package com.mega.mvc39;

public class MatchVO {
	String mid;
	int drink;
	int smoke;
	int believe;
	int tall;
	String mbti;
	int place;
	int contact;
	
	
	
	
	@Override
	public String toString() {
		return "MatchVO [mid=" + mid + ", drink=" + drink + ", smoke=" + smoke + ", believe=" + believe + ", tall="
				+ tall + ", mbti=" + mbti + ", place=" + place + ", contact=" + contact + "]";
	}
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public int getDrink() {
		return drink;
	}
	public void setDrink(int drink) {
		this.drink = drink;
	}
	public int getSmoke() {
		return smoke;
	}
	public void setSmoke(int smoke) {
		this.smoke = smoke;
	}
	public int getBelieve() {
		return believe;
	}
	public void setBelieve(int believe) {
		this.believe = believe;
	}
	public int getTall() {
		return tall;
	}
	public void setTall(int tall) {
		this.tall = tall;
	}
	public String getMbti() {
		return mbti;
	}
	public void setMbti(String mbti) {
		this.mbti = mbti;
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
	
	

}
