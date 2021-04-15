package com.spring.project2;

public class ChartVO {
	
	@Override
	public String toString() {
		return "ChartVO [TID=" + TID + ", DRINKING=" + DRINKING + ", SMOKING=" + SMOKING + ", RELIGION=" + RELIGION
				+ ", HEIGHT=" + HEIGHT + ", PLACE=" + PLACE + ", CONTACT=" + CONTACT + ", WAGE=" + WAGE + ", GENDER="
				+ GENDER + "]";
	}

	String TID;
	int DRINKING;
	int SMOKING;
	int RELIGION;
	int HEIGHT;
	int PLACE;
	int CONTACT;
	int WAGE;
	String GENDER;

	public String getTID() {
		return TID;
	}

	public void setTID(String tID) {
		TID = tID;
	}

	public int getDRINKING() {
		return DRINKING;
	}

	public void setDRINKING(int dRINKING) {
		DRINKING = dRINKING;
	}

	public int getSMOKING() {
		return SMOKING;
	}

	public void setSMOKING(int sMOKING) {
		SMOKING = sMOKING;
	}

	public int getRELIGION() {
		return RELIGION;
	}

	public void setRELIGION(int rELIGION) {
		RELIGION = rELIGION;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public void setHEIGHT(int hEIGHT) {
		HEIGHT = hEIGHT;
	}

	public int getPLACE() {
		return PLACE;
	}

	public void setPLACE(int pLACE) {
		PLACE = pLACE;
	}

	public int getCONTACT() {
		return CONTACT;
	}

	public void setCONTACT(int cONTACT) {
		CONTACT = cONTACT;
	}

	public int getWAGE() {
		return WAGE;
	}

	public void setWAGE(int wAGE) {
		WAGE = wAGE;
	}

	public String getGENDER() {
		return GENDER;
	}

	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}

}
