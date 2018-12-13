package com.artefacto.microformas.beans;

public class CambiosStatusBean {
	private String idStatusIni;
	private String idStatusFin;
	private int connStatus;
	
	public String getIdStatusIni() {
		return idStatusIni;
	}
	public void setIdStatusIni(String idStatusIni) {
		this.idStatusIni = idStatusIni;
	}
	public String getIdStatusFin() {
		return idStatusFin;
	}
	public void setIdStatusFin(String idStatusFin) {
		this.idStatusFin = idStatusFin;
	}
	public int getConnStatus() {
		return connStatus;
	}
	public void setConnStatus(int connStatus) {
		this.connStatus = connStatus;
	}
}
