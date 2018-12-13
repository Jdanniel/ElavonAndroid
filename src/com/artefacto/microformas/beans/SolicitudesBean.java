package com.artefacto.microformas.beans;

public class SolicitudesBean {
	private int 	connStatus;
	private String	type;
	private String 	idAr;
	private String 	idStatusAr;
	
	public int getConnStatus() {
		return connStatus;
	}
	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIdAr() {
		return idAr;
	}
	public void setIdAr(String idAr) {
		this.idAr = idAr;
	}
	public String getIdStatusAr() {
		return idStatusAr;
	}
	public void setIdStatusAr(String idStatusAr) {
		this.idStatusAr = idStatusAr;
	}
}