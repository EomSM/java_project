package com.spring.project2;

public class MessageVO {

	@Override // 용이한 확인을 위해 toString 사용
	public String toString() {
		return "MessageVO [MID=" + MID + ", IDSEND=" + IDSEND + ", IDRE=" + IDRE + ", CONTENT=" + CONTENT + ", MTIME="
				+ MTIME + "]";
	}

	private int MID; // DB의 컬럼명과 일치
	private String IDSEND;
	private String IDRE;
	private String CONTENT;
	private String MTIME;

	public int getMID() {
		return MID;
	}

	public void setMID(int mID) {
		MID = mID;
	}

	public String getIDSEND() {
		return IDSEND;
	}

	public void setIDSEND(String iDSEND) {
		IDSEND = iDSEND;
	}

	public String getIDRE() {
		return IDRE;
	}

	public void setIDRE(String iDRE) {
		IDRE = iDRE;
	}

	public String getCONTENT() {
		return CONTENT;
	}

	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}

	public String getMTIME() {
		return MTIME;
	}

	public void setMTIME(String mTIME) {
		MTIME = mTIME;
	}

}
