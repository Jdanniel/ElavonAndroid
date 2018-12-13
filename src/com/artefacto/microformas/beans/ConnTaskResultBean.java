package com.artefacto.microformas.beans;

public class ConnTaskResultBean {

	private int success;
	private String status;
	
	public ConnTaskResultBean(){
		success = 0;
		status = "";
	}
	
	public int getSuccess() {
		return success;
	}
	public void setSuccess(int success) {
		this.success = success;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
