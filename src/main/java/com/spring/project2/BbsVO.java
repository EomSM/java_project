package com.spring.project2;

public class BbsVO {
	
	int bid;
	String btitle;
	String bcontent;
	String bdate;
	String bwriter;
	
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getBtitle() {
		return btitle;
	}
	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}
	public String getBcontent() {
		return bcontent;
	}
	public void setBcontent(String bcontent) {
		this.bcontent = bcontent;
	}
	public String getBdate() {
		return bdate;
	}
	public void setBdate(String bdate) {
		this.bdate = bdate;
	}
	public String getBwriter() {
		return bwriter;
	}
	public void setBwriter(String bwriter) {
		this.bwriter = bwriter;
	}
	
	@Override
	public String toString() {
		return "BbsVO [bid=" + bid + ", btitle=" + btitle + ", bcontent=" + bcontent + ", bdate=" + bdate + ", bwriter="
				+ bwriter + "]";
	}
	
	
}
