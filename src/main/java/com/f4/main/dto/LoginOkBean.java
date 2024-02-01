package com.f4.main.dto;

public class LoginOkBean {
	private String id;
	private String pw;
	private int bnr;
	
	public int getBnr() {
		return bnr;
	}
	public void setBnr(int bnr) {
		this.bnr = bnr;
	}
	public static final int OK = 0;
	public static final int NOT_ID = 1;
	public static final int NOT_PW = 2;
	public static final int ERROR = -1;
	
	public void setId(String id) {
		this.id = id;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getId() {
		return id;
	}
	public String getPw() {
		return pw;
	}
	
	public int loginOk(MemberDTO dto) {
		try {
			if (dto != null) {
				if (dto.getPw().trim().equals(pw)) {
					return OK;
				}else {
					return NOT_PW;
				}
			}else {
				return NOT_ID;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
}
