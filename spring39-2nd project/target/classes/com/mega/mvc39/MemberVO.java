package com.mega.mvc39;

public class MemberVO {

	String mid;
	String pw;
	String name;
	int birthdate;
	String email;
	String tel;
	String kakaotalk;
	String gender;
	
	private MatchVO MatchVO; //쿼리로 join할 VO
	

	@Override
	public String toString() {
		return "MemberVO [mid=" + mid + ", pw=" + pw + ", name=" + name + ", birthdate=" + birthdate + ", email="
				+ email + ", tel=" + tel + ", kakaotalk=" + kakaotalk + ", gender=" + gender + "]";
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(int birthdate) {
		this.birthdate = birthdate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getKakaotalk() {
		return kakaotalk;
	}
	public void setKakaotalk(String kakaotalk) {
		this.kakaotalk = kakaotalk;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
}