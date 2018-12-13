package com.artefacto.microformas.beans;

public class UploadImageBean {
	
	private boolean succesfulSend;
	private int ConnStatus;
	private boolean succelfulInfo;
	private String desc;
	
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isSuccelfulInfo() {
		return succelfulInfo;
	}

	public void setSuccelfulInfo(boolean succelfulInfo) {
		this.succelfulInfo = succelfulInfo;
	}

	public boolean isSuccesfulSend() {
		return succesfulSend;
	}

	public void setSuccesfulSend(boolean succesfulSend) {
		this.succesfulSend = succesfulSend;
	}
	
	public int getConnStatus() {
		return ConnStatus;
	}

	public void setConnStatus(int connStatus) {
		ConnStatus = connStatus;
	}
}
